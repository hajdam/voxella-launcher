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

import com.vdurmont.semver4j.Semver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Terasology game versions.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class TerasologyGameVersions {

    private final List<TerasologyGameVersion> gameVersion = new ArrayList<>();

    public TerasologyGameVersions() {
    }

    @Nonnull
    public List<TerasologyGameVersion> getGameVersion() {
        return gameVersion;
    }

    public void loadFromFile(File versionsFile) {
        try {
            gameVersion.clear();
            Stream<String> lines = Files.lines(versionsFile.toPath());
            lines.forEach((t) -> {
                Semver version = new Semver(t, Semver.SemverType.IVY);
                gameVersion.add(new TerasologyGameVersion(TerasologyGameVersion.Variant.RELEASE, version));
            });
        } catch (IOException ex) {
            
        }
    }

    public void saveToFile(File versionsFile) {
        
    }
}
