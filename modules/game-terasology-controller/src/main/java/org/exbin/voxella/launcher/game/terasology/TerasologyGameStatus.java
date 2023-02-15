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
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Terasology game status.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class TerasologyGameStatus {

    private long versionsTimestamp;
    private long newsTimestamp;
    private long componentTimestamp;

    public TerasologyGameStatus() {
    }

    public void loadFromFile(File versionsFile) {

    }

    public void saveToFile(File versionsFile) {

    }

    public long getVersionsTimestamp() {
        return versionsTimestamp;
    }

    public void setVersionsTimestamp(long versionsTimestamp) {
        this.versionsTimestamp = versionsTimestamp;
    }

    public long getNewsTimestamp() {
        return newsTimestamp;
    }

    public void setNewsTimestamp(long newsTimestamp) {
        this.newsTimestamp = newsTimestamp;
    }

    public long getComponentTimestamp() {
        return componentTimestamp;
    }

    public void setComponentTimestamp(long componentTimestamp) {
        this.componentTimestamp = componentTimestamp;
    }
}
