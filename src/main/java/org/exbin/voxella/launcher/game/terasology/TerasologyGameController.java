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
package org.exbin.voxella.launcher.game.terasology;

import javax.annotation.ParametersAreNonnullByDefault;
import org.exbin.voxella.launcher.api.GameController;

/**
 * Terasology game controller.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class TerasologyGameController implements GameController {

    @Override
    public void runGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void performUpdate() {
        
    }

    public void performDownload() {
        
    }
}
