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
package org.exbin.voxella.launcher.utils.handler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Handler for close control panel.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public interface CloseControlHandler {

    void controlActionPerformed();

    public interface CloseControlService extends OkCancelService {

        void performCloseClick();

        @Nonnull
        CloseControlEnablementListener createEnablementListener();
    }

    public interface CloseControlEnablementListener {

        void closeActionEnabled(boolean enablement);
    }
}
