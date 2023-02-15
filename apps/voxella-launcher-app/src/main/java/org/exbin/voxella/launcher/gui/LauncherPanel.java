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
package org.exbin.voxella.launcher.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Main launcher panel.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class LauncherPanel extends javax.swing.JPanel {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("org/exbin/voxella/launcher/gui/resources/LauncherPanel");
    private final JComponent[] tabs = new JComponent[4];
    private final List<JPanel> tabWrappers = new ArrayList<>();
    private int activeTab = -1;
    private JComponent userStatusPanel;
    private JComponent progressStatusPanel;

    public LauncherPanel() {
        initComponents();
        init();
    }

    private void init() {
        tabs[1] = new JLabel("Browse");

        for (JComponent tab : tabs) {
            tabWrappers.add(new JPanel(new BorderLayout()));
        }

        tabbedPane.addChangeListener((e) -> {
            if (activeTab >= 0) {
                tabWrappers.get(activeTab).remove(tabs[activeTab]);
            }
            activeTab = tabbedPane.getSelectedIndex();
            JComponent tab = tabs[activeTab];
            if (tab != null) {
                tabWrappers.get(activeTab).add(tab);
            }
        });
        addTab("gamesTab", tabWrappers.get(0));
        addTab("browseTab", tabWrappers.get(1));
        addTab("newsTab", tabWrappers.get(2));
        addTab("optionsTab", tabWrappers.get(3));
        tabbedPane.setTabComponentAt(0, new TabComponent(tabbedPane, 0));
    }

    private void addTab(String tabCode, JComponent component) {
        tabbedPane.addTab(resourceBundle.getString(tabCode + ".title"), new ImageIcon(getClass().getResource(resourceBundle.getString(tabCode + ".icon"))), component, resourceBundle.getString(tabCode + ".toolTip"));
    }

    public void setGameListComponent(JComponent component) {
        tabs[0] = component;
        if (activeTab == 0) {
            tabWrappers.get(activeTab).add(tabs[activeTab]);
        }
    }

    public void setNewsComponent(JComponent component) {
        tabs[2] = component;
    }

    public void setOptionsComponent(JComponent component) {
        tabs[3] = component;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusWrapperPanel = new javax.swing.JPanel();
        headerSeparator = new javax.swing.JSeparator();
        tabbedPane = new javax.swing.JTabbedPane();

        setLayout(new java.awt.BorderLayout());

        statusWrapperPanel.setLayout(new java.awt.BorderLayout());
        statusWrapperPanel.add(headerSeparator, java.awt.BorderLayout.NORTH);

        add(statusWrapperPanel, java.awt.BorderLayout.PAGE_END);

        tabbedPane.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        add(tabbedPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator headerSeparator;
    private javax.swing.JPanel statusWrapperPanel;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateUI() {
        super.updateUI();
        if (tabs != null) {
            for (JComponent tab : tabs) {
                SwingUtilities.updateComponentTreeUI(tab);
            }
        }
        if (tabWrappers != null) {
            for (JPanel tabWrapper : tabWrappers) {
                SwingUtilities.updateComponentTreeUI(tabWrapper);
            }
        }
    }

    public void setUserStatusPanel(JComponent statusPanel) {
        if (this.userStatusPanel != null) {
            statusWrapperPanel.remove(this.userStatusPanel);
        }
        this.userStatusPanel = statusPanel;
        statusWrapperPanel.add(statusPanel, BorderLayout.WEST);
        statusWrapperPanel.revalidate();
    }

    public void setProgressStatusPanel(@Nullable JComponent statusPanel) {
        if (this.progressStatusPanel != null) {
            statusWrapperPanel.remove(this.progressStatusPanel);
        }
        this.progressStatusPanel = statusPanel;
        if (statusPanel != null) {
            statusWrapperPanel.add(statusPanel, BorderLayout.CENTER);
        }
        statusWrapperPanel.revalidate();
        statusWrapperPanel.repaint();
    }
}
