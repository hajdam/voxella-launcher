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
package org.exbin.voxella.launcher.gui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import org.exbin.voxella.launcher.model.LanguageRecord;
import org.exbin.voxella.launcher.model.StartWithMode;
import org.exbin.voxella.launcher.model.ThemeRecord;

/**
 * Options panel.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class OptionsPanel extends javax.swing.JPanel {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("org/exbin/voxella/launcher/gui/resources/OptionsPanel");

    private Action aboutAction;
    private Action openLogsAction;
    private Action checkForUpdatesAction;
    private ThemeChangeListener themeChangeListener;
    private LanguageChangeListener languageChangeListener;

    public OptionsPanel() {
        initComponents();
        init();
    }

    private void init() {
        languageComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                DefaultListCellRenderer renderer = (DefaultListCellRenderer) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                LanguageRecord record = (LanguageRecord) value;
                StringBuilder builder = new StringBuilder();
                String code = record.getCode();
                if (code != null) {
                    builder.append(code).append(": ");
                }
                builder.append(record.getName());
                String alt = record.getAlt();
                if (alt != null) {
                    builder.append(alt);
                }
                renderer.setText(builder.toString());
                ImageIcon flag = record.getFlag();
                if (flag != null) {
                    renderer.setIcon(flag);
                }
                return renderer;
            }
        });
        languageComboBox.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                LanguageRecord record = (LanguageRecord) e.getItem();
                if (languageChangeListener != null) {
                    languageChangeListener.languageChanged(record);
                }
            }
        });
        themeComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                DefaultListCellRenderer renderer = (DefaultListCellRenderer) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                ThemeRecord record = (ThemeRecord) value;
                renderer.setText(record.getName());
                return renderer;
            }
        });
        themeComboBox.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ThemeRecord record = (ThemeRecord) e.getItem();
                if (themeChangeListener != null) {
                    themeChangeListener.themeChanged(record);
                }
            }
        });

        DefaultComboBoxModel<String> startWithModel = (DefaultComboBoxModel<String>) startWithComboBox.getModel();
        startWithModel.addElement(resourceBundle.getString("startWith.gamesOrBrowse"));
        startWithModel.addElement(resourceBundle.getString("startWith.alwaysGamesTab"));
        startWithModel.addElement(resourceBundle.getString("startWith.alwaysBrowseTab"));
        startWithModel.addElement(resourceBundle.getString("startWith.AlwaysNewsTab"));
    }

    public void setLanguages(Collection<LanguageRecord> languages) {
        for (LanguageRecord language : languages) {
            languageComboBox.addItem(language);
        }
    }

    public void setThemes(Collection<ThemeRecord> themes) {
        for (ThemeRecord theme : themes) {
            themeComboBox.addItem(theme);
        }
    }

    public void setSelectedTheme(String themeClass) {
        for (int i = 0; i < themeComboBox.getItemCount(); i++) {
            ThemeRecord record = themeComboBox.getItemAt(i);
            if (themeClass.equals(record.getClassName())) {
                themeComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setSelectedLanguage(String locale) {
        for (int i = 0; i < languageComboBox.getItemCount(); i++) {
            LanguageRecord record = languageComboBox.getItemAt(i);
            if (locale.equals(record.getLocale())) {
                languageComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Nonnull
    public StartWithMode getSelectedStartWithMode() {
        return StartWithMode.values()[startWithComboBox.getSelectedIndex()];
    }

    public void setSelectedStartWithMode(StartWithMode startWithMode) {
        startWithComboBox.setSelectedIndex(startWithMode.ordinal());
    }

    public boolean getCheckForUpdate() {
        return checkForUpdateCheckBox.isSelected();
    }

    public void setCheckForUpdate(boolean updateMode) {
        checkForUpdateCheckBox.setSelected(updateMode);
    }

    public void setThemeChangeListener(ThemeChangeListener themeChangeListener) {
        this.themeChangeListener = themeChangeListener;
    }

    public void setLanguageChangeListener(LanguageChangeListener languageChangeListener) {
        this.languageChangeListener = languageChangeListener;
    }

    public void setAboutAction(Action aboutAction) {
        this.aboutAction = aboutAction;
        aboutButton.setEnabled(true);
    }

    public void setOpenLogsAction(Action openLogsAction) {
        this.openLogsAction = openLogsAction;
        logsButton.setEnabled(true);
    }

    public void setCheckForUpdatesAction(Action checkForUpdatesAction) {
        this.checkForUpdatesAction = checkForUpdatesAction;
        checkForUpdatesButton.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        launcherOoptionsPanel = new javax.swing.JPanel();
        actionsPanel = new javax.swing.JPanel();
        themeLabel = new javax.swing.JLabel();
        themeComboBox = new javax.swing.JComboBox<>();
        languageLabel = new javax.swing.JLabel();
        languageComboBox = new javax.swing.JComboBox<>();
        checkForUpdateCheckBox = new javax.swing.JCheckBox();
        startWithLabel = new javax.swing.JLabel();
        startWithComboBox = new javax.swing.JComboBox<>();
        checkForUpdatesButton = new javax.swing.JButton();
        logsButton = new javax.swing.JButton();
        aboutButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        launcherOoptionsPanel.setAutoscrolls(true);

        javax.swing.GroupLayout launcherOoptionsPanelLayout = new javax.swing.GroupLayout(launcherOoptionsPanel);
        launcherOoptionsPanel.setLayout(launcherOoptionsPanelLayout);
        launcherOoptionsPanelLayout.setHorizontalGroup(
            launcherOoptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        launcherOoptionsPanelLayout.setVerticalGroup(
            launcherOoptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        add(launcherOoptionsPanel, java.awt.BorderLayout.CENTER);

        themeLabel.setText(resourceBundle.getString("themeLabel.text")); // NOI18N

        languageLabel.setText(resourceBundle.getString("languageLabel.text")); // NOI18N

        checkForUpdateCheckBox.setSelected(true);
        checkForUpdateCheckBox.setText(resourceBundle.getString("checkForUpdateCheckBox.text")); // NOI18N

        startWithLabel.setText(resourceBundle.getString("startWithLabel.text")); // NOI18N

        checkForUpdatesButton.setText(resourceBundle.getString("checkFrUpdatesButton.text")); // NOI18N
        checkForUpdatesButton.setEnabled(false);
        checkForUpdatesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkForUpdatesButtonActionPerformed(evt);
            }
        });

        logsButton.setText(resourceBundle.getString("logsButton.text")); // NOI18N
        logsButton.setEnabled(false);
        logsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logsButtonActionPerformed(evt);
            }
        });

        aboutButton.setText(resourceBundle.getString("aboutButton.text")); // NOI18N
        aboutButton.setEnabled(false);
        aboutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout actionsPanelLayout = new javax.swing.GroupLayout(actionsPanel);
        actionsPanel.setLayout(actionsPanelLayout);
        actionsPanelLayout.setHorizontalGroup(
            actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsPanelLayout.createSequentialGroup()
                        .addComponent(checkForUpdatesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aboutButton))
                    .addGroup(actionsPanelLayout.createSequentialGroup()
                        .addGroup(actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(themeLabel)
                            .addComponent(themeComboBox, 0, 341, Short.MAX_VALUE)
                            .addComponent(languageLabel)
                            .addComponent(languageComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkForUpdateCheckBox)
                            .addComponent(startWithLabel)
                            .addComponent(startWithComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 679, Short.MAX_VALUE)))
                .addContainerGap())
        );
        actionsPanelLayout.setVerticalGroup(
            actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(themeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(themeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(languageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkForUpdateCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startWithLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startWithComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
                .addGroup(actionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aboutButton)
                    .addComponent(checkForUpdatesButton)
                    .addComponent(logsButton))
                .addContainerGap())
        );

        add(actionsPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed
        aboutAction.actionPerformed(evt);
    }//GEN-LAST:event_aboutButtonActionPerformed

    private void logsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logsButtonActionPerformed
        openLogsAction.actionPerformed(evt);
    }//GEN-LAST:event_logsButtonActionPerformed

    private void checkForUpdatesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkForUpdatesButtonActionPerformed
        checkForUpdatesAction.actionPerformed(evt);
    }//GEN-LAST:event_checkForUpdatesButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboutButton;
    private javax.swing.JPanel actionsPanel;
    private javax.swing.JCheckBox checkForUpdateCheckBox;
    private javax.swing.JButton checkForUpdatesButton;
    private javax.swing.JComboBox<LanguageRecord> languageComboBox;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JPanel launcherOoptionsPanel;
    private javax.swing.JButton logsButton;
    private javax.swing.JComboBox<String> startWithComboBox;
    private javax.swing.JLabel startWithLabel;
    private javax.swing.JComboBox<ThemeRecord> themeComboBox;
    private javax.swing.JLabel themeLabel;
    // End of variables declaration//GEN-END:variables

    public interface ThemeChangeListener {

        void themeChanged(@Nonnull ThemeRecord themeRecord);
    }

    public interface LanguageChangeListener {

        void languageChanged(@Nonnull LanguageRecord languageRecord);
    }
}
