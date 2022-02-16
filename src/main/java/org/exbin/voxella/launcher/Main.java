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
package org.exbin.voxella.launcher;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JFrame;
import org.exbin.voxella.launcher.gui.LauncherPanel;
import org.exbin.voxella.launcher.model.Launcher;

/**
 * Voxella Launcher main window.
 *
 * @version 0.1.0 2022/02/16
 * @author ExBin Project (http://exbin.org)
 */
@ParametersAreNonnullByDefault
public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            JFrame applicationFrame = new JFrame();
            applicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            final Launcher launcher = new Launcher();
            final LauncherPanel mainPanel = new LauncherPanel();

//            applicationFrame.setTitle(mainPanel.getFrameTitle()); // NOI18N
//            applicationFrame.setIconImage(new javax.swing.ImageIcon(getClass().getResource(mainPanel.getFrameIconPath())).getImage());
            applicationFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    launcher.save();
                    System.exit(0);
                }
            });

            applicationFrame.add(mainPanel, BorderLayout.CENTER);
            applicationFrame.setSize(new java.awt.Dimension(806, 548));
            applicationFrame.setLocationRelativeTo(null);
            applicationFrame.setVisible(true);
            launcher.performUpdate();
        });
    }
}
