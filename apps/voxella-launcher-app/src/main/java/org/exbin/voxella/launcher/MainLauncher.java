/*
 * Copyright (C) ExBin Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.voxella.launcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import org.apache.commons.lang3.SystemUtils;
import org.exbin.voxella.launcher.preferences.Preferences;
import org.exbin.voxella.launcher.api.VoxellaLauncher;
import org.exbin.voxella.launcher.preferences.PropertiesPreferences;

/**
 * Main launcher class.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class MainLauncher implements VoxellaLauncher {

    private static final String WORK_DIRECTORY_NAME = ".voxella-launcher";
    private static final String SETTINGS_FILENAME = "launcher.cfg";
    private static final String VERSION = "0.1";

    public static final String PREFERENCES_VERSION = "version";
    public static final String PREFERENCES_LANGUAGE = "language";
    public static final String PREFERENCES_THEME = "themeClass";
    public static final String PREFERENCES_CHECK_FOR_UPDATES = "checkForUpdates";
    public static final String PREFERENCES_START_WITH = "startWith";

    private Preferences preferences;
    private final File executionDir;
    private final File workDir;
    private File currentLogFile;

    private static MainLauncher instance = null;

    private MainLauncher() {
        File executionDirFile;
        try {
            executionDirFile = new File(MainLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            executionDirFile = new File("");
        }
        executionDir = executionDirFile;
        File workDirFile = new File(executionDirFile, WORK_DIRECTORY_NAME);
        if (workDirFile.exists() && workDirFile.canWrite()) {
            workDir = workDirFile;
        } else {
            workDir = new File(getUserConfigurationDirectory(), WORK_DIRECTORY_NAME);
        }
    }

    public static synchronized MainLauncher getInstance() {
        if (instance == null) {
            instance = new MainLauncher();
        }
        return instance;
    }

    public void startLogging(String applicationName, String applicationVersion) {
        FileOutputStream fileStream;
        try {
            currentLogFile = getCurrentLogFile();
            currentLogFile.createNewFile();
            fileStream = new FileOutputStream(currentLogFile);
            Logger logger = Logger.getGlobal();
            StreamHandler streamHandler = new StreamHandler(fileStream, new SimpleFormatter());
            logger.addHandler(streamHandler);
            logger.info(applicationName + " - version " + applicationVersion);
            streamHandler.flush();
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(MainLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void performUpdate() {

    }

    @Nonnull
    public Preferences loadSettings() {
        File settingsFile = new File(workDir, SETTINGS_FILENAME);
        preferences = new PropertiesPreferences(settingsFile);
        if (settingsFile.canWrite()) {
            preferences.sync();
        } else {
            workDir.mkdirs();
            preferences.put(PREFERENCES_VERSION, VERSION);
            preferences.flush();
        }
        return preferences;
    }

    public void saveSettings() {
        preferences.flush();
    }

    @Nonnull
    private File getUserConfigurationDirectory() {
        return SystemUtils.getUserHome();
    }

    @Nonnull
    @Override
    public File getWorkDir() {
        return workDir;
    }

    @Nonnull
    public File getCurrentLogFile() {
        File logsDir = new File(workDir, "logs");
        logsDir.mkdirs();
        return new File(logsDir, "current.log");
    }
}
