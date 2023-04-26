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
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

/**
 * Terasology game versions.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
@Immutable
public class TerasologyGameVersion {

    private final Variant variant;
    private final Semver version;

    public TerasologyGameVersion(Variant variant, Semver version) {
        this.variant = variant;
        this.version = version;
    }

    @Nonnull
    public Variant getVariant() {
        return variant;
    }

    @Nonnull
    public Semver getVersion() {
        return version;
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
