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
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Operation progress panel.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class OperationProgressPanel extends javax.swing.JPanel {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("org/exbin/voxella/launcher/gui/resources/OperationProgressPanel");

    private boolean cancellable = false;
    private ActionListener cancelAction;

    public OperationProgressPanel() {
        initComponents();
        init();
    }

    private void init() {
    }

    public void setCancellable(boolean cancellable) {
        if (this.cancellable != cancellable) {
            this.cancellable = cancellable;
            if (cancellable) {
                add(cancelPanel, BorderLayout.EAST);
            } else {
                remove(cancelPanel);
            }
            invalidate();
        }
    }

    public void setCancelAction(ActionListener cancelAction) {
        this.cancelAction = cancelAction;
    }

    public void setProgress(int progress) {
        operationProgressBar.setValue(progress);
    }

    public void setProgressText(String text) {
        operationProgressBar.setString(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelPanel = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        progressBarPanel = new javax.swing.JPanel();
        operationProgressBar = new javax.swing.JProgressBar();

        cancelButton.setText(resourceBundle.getString("cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout cancelPanelLayout = new javax.swing.GroupLayout(cancelPanel);
        cancelPanel.setLayout(cancelPanelLayout);
        cancelPanelLayout.setHorizontalGroup(
            cancelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelPanelLayout.createSequentialGroup()
                .addComponent(cancelButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cancelPanelLayout.setVerticalGroup(
            cancelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cancelPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cancelButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setLayout(new java.awt.BorderLayout());

        operationProgressBar.setMaximum(1000);
        operationProgressBar.setString(resourceBundle.getString("operationProgressBar.text")); // NOI18N
        operationProgressBar.setStringPainted(true);

        javax.swing.GroupLayout progressBarPanelLayout = new javax.swing.GroupLayout(progressBarPanel);
        progressBarPanel.setLayout(progressBarPanelLayout);
        progressBarPanelLayout.setHorizontalGroup(
            progressBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressBarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(operationProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        progressBarPanelLayout.setVerticalGroup(
            progressBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressBarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(operationProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(progressBarPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if (cancelAction != null) {
            cancelAction.actionPerformed(evt);
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel cancelPanel;
    private javax.swing.JProgressBar operationProgressBar;
    private javax.swing.JPanel progressBarPanel;
    // End of variables declaration//GEN-END:variables

}
