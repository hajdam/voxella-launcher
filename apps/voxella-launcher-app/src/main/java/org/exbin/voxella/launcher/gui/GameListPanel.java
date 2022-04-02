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

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import org.exbin.voxella.launcher.api.ProgressReporter;
import org.exbin.voxella.launcher.game.terasology.TerasologyGameController;
import org.exbin.voxella.launcher.game.terasology.TerasologyGameComponent;
import org.exbin.voxella.launcher.game.terasology.TerasologyGameOptionsComponent;
import org.exbin.voxella.launcher.model.BasicGameRecord;

/**
 * Games list panel.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class GameListPanel extends javax.swing.JPanel {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("org/exbin/voxella/launcher/gui/resources/GameListPanel");

    private boolean busyMode = false;
    private Action optionsAction;
    private Action launchAction;
    private JComponent activeComponent;
    private final TerasologyGameController terasologyGameController;

    public GameListPanel() {
        terasologyGameController = new TerasologyGameController("Terasology-latest");
        initComponents();
        init();
    }

    private void init() {
        activeComponent = noGameSelectedLabel;
        gamesList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                DefaultListCellRenderer renderer = (DefaultListCellRenderer) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                BasicGameRecord record = (BasicGameRecord) value;
                renderer.setText(record.getName());
                renderer.setIcon(record.getIcon());
                return renderer;
            }
        });
        gamesList.addListSelectionListener((event) -> {
            int index = gamesList.getSelectedIndex();

            JComponent targetComponent;
            BasicGameRecord record = null;
            if (index >= 0) {
                record = gamesList.getModel().getElementAt(index);
                targetComponent = record.getComponent();
            } else {
                targetComponent = noGameSelectedLabel;
            }

            if (activeComponent != targetComponent) {
                gameInfoPanel.remove(activeComponent);
                gameInfoPanel.add(targetComponent, BorderLayout.CENTER);
                activeComponent = targetComponent;
                gameInfoPanel.revalidate();
                gameInfoPanel.repaint();
                updateState();
            }
        });

        DefaultListModel<BasicGameRecord> gameRecordsModel = new DefaultListModel<>();
        ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/game/terasology/resources/gooey_star_48.png"));
        BasicGameRecord record = new BasicGameRecord("Terasology Test", terasologyGameController, new TerasologyGameComponent(), icon);
        record.setOptionsComponent(new TerasologyGameOptionsComponent());
        gameRecordsModel.addElement(record);
        gamesList.setModel(gameRecordsModel);
        gamesList.setSelectedIndex(0);
    }

    public void setOptionsAction(Action optionsAction) {
        this.optionsAction = optionsAction;
    }

    public void setLaunchAction(Action launchAction) {
        this.launchAction = launchAction;
    }

    public void setProgressReporter(ProgressReporter progressReporter) {
        terasologyGameController.setProgressReporter(progressReporter);
    }

    @Nonnull
    public BasicGameRecord getSelectedGame() {
        return Objects.requireNonNull(gamesList.getSelectedValue());
    }

    @Override
    public void updateUI() {
        super.updateUI();

        if (gamesList != null) {
            ListModel<BasicGameRecord> model = gamesList.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                BasicGameRecord gameRecord = model.getElementAt(i);
                SwingUtilities.updateComponentTreeUI(gameRecord.getComponent());
                Optional<JComponent> optionsComponent = gameRecord.getOptionsComponent();
                if (optionsComponent.isPresent()) {
                    SwingUtilities.updateComponentTreeUI(optionsComponent.get());
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gamesListScrollPane = new javax.swing.JScrollPane();
        gamesList = new javax.swing.JList<>();
        filterLabel = new javax.swing.JLabel();
        filterTextField = new javax.swing.JTextField();
        addGameButton = new javax.swing.JButton();
        gameInfoPanel = new javax.swing.JPanel();
        noGameSelectedLabel = new javax.swing.JLabel();
        launchButton = new javax.swing.JButton();
        optionsButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();

        gamesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        gamesListScrollPane.setViewportView(gamesList);

        filterLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/resources/images/open_icon_library/view-filter.png"))); // NOI18N

        filterTextField.setEditable(false);

        addGameButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/resources/images/open_icon_library/16x16/actions/edit-add-2.png"))); // NOI18N
        addGameButton.setText(resourceBundle.getString("addGameButton.text")); // NOI18N
        addGameButton.setToolTipText("");
        addGameButton.setEnabled(false);

        gameInfoPanel.setLayout(new java.awt.BorderLayout());

        noGameSelectedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noGameSelectedLabel.setText(resourceBundle.getString("noGameSelectedLabel.text")); // NOI18N
        noGameSelectedLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gameInfoPanel.add(noGameSelectedLabel, java.awt.BorderLayout.CENTER);

        launchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/resources/images/open_icon_library/16x16/actions/arrow-right.png"))); // NOI18N
        launchButton.setText(resourceBundle.getString("launchButton.text")); // NOI18N
        launchButton.setEnabled(false);
        launchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchButtonActionPerformed(evt);
            }
        });

        optionsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/resources/images/open_icon_library/16x16/actions/configure-3.png"))); // NOI18N
        optionsButton.setText(resourceBundle.getString("optionsButton.text")); // NOI18N
        optionsButton.setEnabled(false);
        optionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsButtonActionPerformed(evt);
            }
        });

        removeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/resources/images/open_icon_library/16x16/actions/edit-delete-2.png"))); // NOI18N
        removeButton.setText(resourceBundle.getString("removeButton.text")); // NOI18N
        removeButton.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gamesListScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(filterLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filterTextField)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gameInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addGameButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 277, Short.MAX_VALUE)
                        .addComponent(removeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(optionsButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(launchButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(filterTextField)
                            .addComponent(filterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gamesListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                    .addComponent(gameInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addGameButton)
                    .addComponent(launchButton)
                    .addComponent(optionsButton)
                    .addComponent(removeButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void optionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsButtonActionPerformed
        optionsAction.actionPerformed(evt);
    }//GEN-LAST:event_optionsButtonActionPerformed

    private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchButtonActionPerformed
        launchAction.actionPerformed(evt);
    }//GEN-LAST:event_launchButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addGameButton;
    private javax.swing.JLabel filterLabel;
    private javax.swing.JTextField filterTextField;
    private javax.swing.JPanel gameInfoPanel;
    private javax.swing.JList<BasicGameRecord> gamesList;
    private javax.swing.JScrollPane gamesListScrollPane;
    private javax.swing.JButton launchButton;
    private javax.swing.JLabel noGameSelectedLabel;
    private javax.swing.JButton optionsButton;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables

    public void setBusyMode(boolean busyMode) {
        this.busyMode = busyMode;
        updateState();
    }

    private void updateState() {
        int index = gamesList.getSelectedIndex();
        BasicGameRecord record = index >= 0 ? gamesList.getModel().getElementAt(index) : null;
        boolean isSelected = index >= 0;
        addGameButton.setEnabled(!busyMode);
        launchButton.setEnabled(!busyMode && isSelected);
        optionsButton.setEnabled(!busyMode && isSelected && record.getOptionsComponent().isPresent());
//                removeButton.setEnabled(!busyMode && isSelected);
    }
}