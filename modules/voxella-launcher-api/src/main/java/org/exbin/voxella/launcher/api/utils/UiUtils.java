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
package org.exbin.voxella.launcher.api.utils;

import java.awt.Color;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.UIManager;

/**
 * UI utility methods.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class UiUtils {

    private UiUtils() {
    }

    public static boolean isDarkUI() {
        Color backgroundColor = UIManager.getColor("TextArea.background");
        if (backgroundColor == null) {
            return false;
        }

        int medium = (backgroundColor.getRed() + backgroundColor.getBlue() + backgroundColor.getGreen()) / 3;
        return medium < 96;
    }
}
