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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Terasology game versions.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class TerasologyGameVersions {

    private final List<GameVersion> gameVersion = new ArrayList<>();

    public TerasologyGameVersions() {
    }

    public List<GameVersion> getGameVersion() {
        return gameVersion;
    }

    public void loadFromFile(File versionsFile) {

    }

    public void saveToFile(File versionsFile) {

    }

    public static class GameVersion {

        private Variant variant;
        /**
         * TODO: Separate to numbers.
         */
        private String version;

        public Variant getVariant() {
            return variant;
        }

        public void setVariant(Variant variant) {
            this.variant = variant;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public enum Variant {
        RELEASE,
        ALPHA,
        /**
         * Release candidate.
         */
        RC
    }
}
