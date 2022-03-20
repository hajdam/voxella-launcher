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
package org.exbin.voxella.launcher.model;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * Game record.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class GameRecord {

    private GameState state = GameState.DEFAULT;
    private String name;
    private ImageIcon icon;
    private JComponent component;
    private JComponent optionsComponent = null;
    private String version = "";

    public GameRecord(String name, JComponent component, ImageIcon icon) {
        this.name = name;
        this.component = component;
        this.icon = icon;
    }

    @Nonnull
    public GameState getState() {
        return state;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public ImageIcon getIcon() {
        return icon;
    }

    @Nonnull
    public JComponent getComponent() {
        return component;
    }

    public void setComponent(JComponent component) {
        this.component = component;
    }

    @Nonnull
    public Optional<JComponent> getOptionsComponent() {
        return Optional.ofNullable(optionsComponent);
    }

    public void setOptionsComponent(JComponent optionsComponent) {
        this.optionsComponent = optionsComponent;
    }

    @Nonnull
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public enum GameState {
        DEFAULT,
        UPDATE_NEEDED,
        NO_RECORD,
        NO_PING
    }
}
