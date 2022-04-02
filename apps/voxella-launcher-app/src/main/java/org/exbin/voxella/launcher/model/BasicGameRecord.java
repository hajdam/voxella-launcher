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
import org.exbin.voxella.launcher.api.GameController;
import org.exbin.voxella.launcher.api.GameRecord;
import org.exbin.voxella.launcher.api.GameState;

/**
 * Game record.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class BasicGameRecord implements GameRecord {

    private GameState state = GameState.DEFAULT;
    private String name;
    private ImageIcon icon;
    private JComponent component;
    private JComponent optionsComponent = null;
    private GameController controller;
    private String version = "";

    public BasicGameRecord(String name, GameController controller, JComponent component, ImageIcon icon) {
        this.name = name;
        this.controller = controller;
        this.component = component;
        this.icon = icon;
    }

    @Nonnull
    @Override
    public GameState getState() {
        return state;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public ImageIcon getIcon() {
        return icon;
    }

    @Nonnull
    public GameController getController() {
        return controller;
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
    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
