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

import java.awt.Color;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.JLabel;
import org.exbin.voxella.launcher.utils.BareBonesBrowserLaunch;
import org.exbin.voxella.launcher.utils.WindowUtils;

/**
 * About launcher panel.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class AboutPanel extends javax.swing.JPanel {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("org/exbin/voxella/launcher/gui/resources/AboutPanel");

    public AboutPanel() {
        initComponents();
        init();
    }

    private void init() {
        boolean darkMode = WindowUtils.isDarkUI();
        if (darkMode) {
            aboutHeaderPanel.setBackground(Color.BLACK);
            appTitleLabel.setForeground(Color.WHITE);
            appDescLabel.setForeground(Color.WHITE);
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

        aboutHeaderPanel = new javax.swing.JPanel();
        headerSeparator = new javax.swing.JSeparator();
        aboutHeaderTitlePanel = new javax.swing.JPanel();
        appDescLabel = new javax.swing.JLabel();
        appTitleLabel = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        javax.swing.JLabel nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        javax.swing.JLabel versionLabel = new javax.swing.JLabel();
        versionTextField = new javax.swing.JTextField();
        javax.swing.JLabel vendorLabel = new javax.swing.JLabel();
        vendorTextField = new javax.swing.JTextField();
        javax.swing.JLabel appLicenseLabel = new javax.swing.JLabel();
        licenseTextField = new javax.swing.JTextField();
        javax.swing.JLabel homepageLabel = new javax.swing.JLabel();
        appHomepageLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        aboutHeaderPanel.setBackground(new java.awt.Color(255, 255, 255));
        aboutHeaderPanel.setLayout(new java.awt.BorderLayout());
        aboutHeaderPanel.add(headerSeparator, java.awt.BorderLayout.SOUTH);

        aboutHeaderTitlePanel.setOpaque(false);

        appDescLabel.setForeground(java.awt.Color.black);
        appDescLabel.setText(resourceBundle.getString("appDescLabel.text")); // NOI18N

        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize()+4));
        appTitleLabel.setForeground(java.awt.Color.black);
        appTitleLabel.setText(resourceBundle.getString("appTitleLabel.text")); // NOI18N

        javax.swing.GroupLayout aboutHeaderTitlePanelLayout = new javax.swing.GroupLayout(aboutHeaderTitlePanel);
        aboutHeaderTitlePanel.setLayout(aboutHeaderTitlePanelLayout);
        aboutHeaderTitlePanelLayout.setHorizontalGroup(
            aboutHeaderTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 743, Short.MAX_VALUE)
            .addGroup(aboutHeaderTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(aboutHeaderTitlePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(aboutHeaderTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(appDescLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(appTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        aboutHeaderTitlePanelLayout.setVerticalGroup(
            aboutHeaderTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
            .addGroup(aboutHeaderTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(aboutHeaderTitlePanelLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(appTitleLabel)
                    .addGap(7, 7, 7)
                    .addComponent(appDescLabel)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        aboutHeaderPanel.add(aboutHeaderTitlePanel, java.awt.BorderLayout.CENTER);

        add(aboutHeaderPanel, java.awt.BorderLayout.PAGE_START);

        nameLabel.setFont(nameLabel.getFont().deriveFont(nameLabel.getFont().getStyle() | java.awt.Font.BOLD));
        nameLabel.setText(resourceBundle.getString("nameLabel.text")); // NOI18N

        nameTextField.setEditable(false);
        nameTextField.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        nameTextField.setBorder(null);

        versionLabel.setFont(versionLabel.getFont().deriveFont(versionLabel.getFont().getStyle() | java.awt.Font.BOLD));
        versionLabel.setText(resourceBundle.getString("versionLabel.text")); // NOI18N

        versionTextField.setEditable(false);
        versionTextField.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        versionTextField.setBorder(null);

        vendorLabel.setFont(vendorLabel.getFont().deriveFont(vendorLabel.getFont().getStyle() | java.awt.Font.BOLD));
        vendorLabel.setText(resourceBundle.getString("vendorLabel.text")); // NOI18N

        vendorTextField.setEditable(false);
        vendorTextField.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        vendorTextField.setBorder(null);

        appLicenseLabel.setFont(appLicenseLabel.getFont().deriveFont(appLicenseLabel.getFont().getStyle() | java.awt.Font.BOLD));
        appLicenseLabel.setText(resourceBundle.getString("appLicenseLabel.text")); // NOI18N

        licenseTextField.setEditable(false);
        licenseTextField.setFont(new java.awt.Font("Dialog 12", 1, 12)); // NOI18N
        licenseTextField.setBorder(null);

        homepageLabel.setFont(homepageLabel.getFont().deriveFont(homepageLabel.getFont().getStyle() | java.awt.Font.BOLD));
        homepageLabel.setText(resourceBundle.getString("homepageLabel.text")); // NOI18N

        appHomepageLabel.setForeground(java.awt.Color.blue);
        appHomepageLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HashMap<TextAttribute, Object> attribs = new HashMap<TextAttribute, Object>();
        attribs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
        appHomepageLabel.setFont(appHomepageLabel.getFont().deriveFont(attribs));
        appHomepageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                appHomepageLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vendorLabel)
                    .addComponent(homepageLabel)
                    .addComponent(appLicenseLabel)
                    .addComponent(versionLabel)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                    .addComponent(vendorTextField)
                    .addComponent(licenseTextField)
                    .addComponent(appHomepageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(versionTextField))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(versionLabel)
                    .addComponent(versionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vendorLabel)
                    .addComponent(vendorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(appLicenseLabel)
                    .addComponent(licenseTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(homepageLabel)
                    .addComponent(appHomepageLabel))
                .addContainerGap(393, Short.MAX_VALUE))
        );

        add(mainPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void appHomepageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appHomepageLabelMouseClicked
        if (!evt.isPopupTrigger()) {
            String targetURL = ((JLabel) evt.getSource()).getText();
            BareBonesBrowserLaunch.openDesktopURL(targetURL);
        }
    }//GEN-LAST:event_appHomepageLabelMouseClicked

    public void setAboutInfo(AboutInfo aboutInfo) {
        nameTextField.setText(aboutInfo.name);
        versionTextField.setText(aboutInfo.version);
        vendorTextField.setText(aboutInfo.vendor);
        licenseTextField.setText(aboutInfo.license);
        appHomepageLabel.setText(aboutInfo.homepage);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aboutHeaderPanel;
    private javax.swing.JPanel aboutHeaderTitlePanel;
    private javax.swing.JLabel appDescLabel;
    private javax.swing.JLabel appHomepageLabel;
    private javax.swing.JLabel appTitleLabel;
    private javax.swing.JSeparator headerSeparator;
    private javax.swing.JTextField licenseTextField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField vendorTextField;
    private javax.swing.JTextField versionTextField;
    // End of variables declaration//GEN-END:variables

    public static class AboutInfo {

        public String name;
        public String version;
        public String vendor;
        public String license;
        public String homepage;
    }
}
