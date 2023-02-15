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
package org.exbin.voxella.launcher.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.ImageIcon;

/**
 * Language record.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class LanguageRecord {

    private final String name;
    private final String locale;
    @Nullable
    private final String code;
    @Nullable
    private final String alt;
    @Nullable
    private final ImageIcon flag;

    public LanguageRecord(String name, String locale, @Nullable String code, @Nullable String alt, @Nullable ImageIcon flag) {
        this.name = name;
        this.locale = locale;
        this.code = code;
        this.alt = alt;
        this.flag = flag;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getLocale() {
        return locale;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    @Nullable
    public String getAlt() {
        return alt;
    }

    @Nullable
    public ImageIcon getFlag() {
        return flag;
    }
}
