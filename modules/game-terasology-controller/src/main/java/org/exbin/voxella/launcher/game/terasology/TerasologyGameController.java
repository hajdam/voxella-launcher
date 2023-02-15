/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.voxella.launcher.game.terasology;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.voxella.launcher.api.GameController;
import org.exbin.voxella.launcher.api.ProgressReporter;
import org.exbin.voxella.launcher.game.terasology.util.DownloadException;
import org.exbin.voxella.launcher.game.terasology.util.DownloadUtils;
import org.exbin.voxella.launcher.game.terasology.util.FileUtils;
import org.exbin.voxella.launcher.api.ProgressReporter.ProgressListener;
import org.exbin.voxella.launcher.api.VoxellaLauncher;

/**
 * Terasology game controller.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class TerasologyGameController implements GameController {

    private static final Logger logger = Logger.getLogger(TerasologyGameController.class.getName());

    private VoxellaLauncher launcher;
    private ProgressReporter progressReporter;
    private String gameDirName;
    private String engineVersion = "5.3.0";

    public TerasologyGameController(String gameDirName) {
        this.gameDirName = gameDirName;
    }

    @Override
    public void attachLauncher(VoxellaLauncher launcher) {
        this.launcher = launcher;
    }

    @Override
    public void runGame() {
        if (!gameExists()) {
            File gameDirectory = getGameDirectory();
            gameDirectory.mkdirs();
            performDownload();
        }
        performRun();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setProgressReporter(ProgressReporter progressReporter) {
        this.progressReporter = progressReporter;
    }

    public void performUpdate() {

    }

    public void performDownload() throws DownloadException {
        try {
            File gameDirectory = getGameDirectory();
            URL downloadUrl = new URL("https://github.com/MovingBlocks/Terasology/releases/download/v" + engineVersion + "/TerasologyOmega.zip");
            final long contentLength = DownloadUtils.getContentLength(downloadUrl);
            final long availableSpace = gameDirectory.getParentFile().getUsableSpace();

            if (availableSpace < contentLength) {
                throw new DownloadException("Insufficient space for downloading package");
            }

            final Path gameZip = gameDirectory.toPath().resolve("TerasologyOmega.zip");
            Files.deleteIfExists(gameZip);
            ProgressListener progressListener = progressReporter.startOperation("Downloading game file", ProgressReporter.CancellableOperation.CANCELLABLE);
            boolean fileDownloaded = false;
            try {
                DownloadUtils.downloadToFile(downloadUrl, gameZip, progressListener).get();
                if (!progressListener.isCancelled()) {
                    fileDownloaded = true;
                    logger.log(Level.INFO, "Finished downloading package: {}", "TerasologyOmega.zip");
                }
            } finally {
                progressReporter.endOperation(progressListener);
            }

            if (fileDownloaded) {
                ProgressListener extractProgressListener = progressReporter.startOperation("Extracting game file", ProgressReporter.CancellableOperation.NON_CANCELLABLE);
                try {
                    FileUtils.extractZipTo(gameZip, gameDirectory.toPath());
                    Files.deleteIfExists(gameZip);
                } finally {
                    progressReporter.endOperation(extractProgressListener);
                }
            }
        } catch (IOException | InterruptedException | ExecutionException ex) {
            throw new DownloadException("Download failed", ex);
        }
    }

    @Nonnull
    public String getGameDirName() {
        return gameDirName;
    }

    public void performRun() {
        File gameDirectory = getGameDirectory();
//        Launcher launcher = Launcher.getInstance();
//        TerasologyGamePreferences preferences = new TerasologyGamePreferences();
//        preferences.loadFrom(gameDirectory);

        try {
            GameStarter starter = new GameStarter(
                    new Installation(gameDirectory.toPath()), gameDirectory.toPath(),
                    JavaHeapSize.NOT_USED, JavaHeapSize.NOT_USED,
                    new ArrayList<>(), new ArrayList<>(), Level.SEVERE
            );
            starter.call();
            System.exit(0);
        } catch (IOException ex) {
            throw new RuntimeException("Game run failed", ex);
        }
    }

    public boolean gameExists() {
        File gameDirectory = getGameDirectory();
        return gameDirectory.exists() && new File(gameDirectory, "libs/engine-" + engineVersion + ".jar").exists();
    }

    @Nonnull
    public String getEngineVersion() {
        return engineVersion;
    }

    @Nonnull
    public File getGameDirectory() {
        return new File(launcher.getWorkDir(), gameDirName);
    }
}
