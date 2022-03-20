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

import java.awt.Component;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Options component.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class TerasologyGameOptionsComponent extends javax.swing.JPanel {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("org/exbin/voxella/launcher/game/terasology/resources/TerasologyGameOptionsComponent");

    private final Map<JavaHeapSize, String> heapSizes = new HashMap<>();

    public TerasologyGameOptionsComponent() {
        initComponents();
        init();
    }

    private void init() {
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        char decimalSeparator = format.getDecimalFormatSymbols().getDecimalSeparator();
        for (JavaHeapSize heapSize : JavaHeapSize.values()) {
            switch (heapSize) {
                case NOT_USED: {
                    heapSizes.put(heapSize, "Not used");
                    break;
                }
                default: {
                    String labelKey = heapSize.getLabelKey();
                    String text;
                    if (labelKey.startsWith("heapsize_mb_")) {
                        text = labelKey.substring(12) + " MB";
                    } else {
                        text = labelKey.substring(12).replace('_', decimalSeparator) + " GB";
                    }
                    heapSizes.put(heapSize, text);
                }
            }
        }

        initMemoryComboBox.setModel(new DefaultComboBoxModel<>(JavaHeapSize.values()));
        initMemoryComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(heapSizes.get((JavaHeapSize) value));
                return component;
            }
        });
        maximumMemoryComboBox.setModel(new DefaultComboBoxModel<>(JavaHeapSize.values()));
        maximumMemoryComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(heapSizes.get((JavaHeapSize) value));
                return component;
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        engineVersionLabel = new javax.swing.JLabel();
        engineVersionComboBox = new javax.swing.JComboBox<>();
        javaRuntimeLabel = new javax.swing.JLabel();
        javaRuntimeComboBox = new javax.swing.JComboBox<>();
        initMemoryLabel = new javax.swing.JLabel();
        initMemoryComboBox = new javax.swing.JComboBox<>();
        maximumMemoryLabel = new javax.swing.JLabel();
        maximumMemoryComboBox = new javax.swing.JComboBox<>();
        extraOptionsPanel = new javax.swing.JPanel();
        javaArgsLabel = new javax.swing.JLabel();
        javaArgsScrollPane = new javax.swing.JScrollPane();
        javaArgsTextArea = new javax.swing.JTextArea();
        gameArgsLabel = new javax.swing.JLabel();
        gameArgsScrollPane = new javax.swing.JScrollPane();
        gameArgsTextArea = new javax.swing.JTextArea();
        loggingLevelLabel = new javax.swing.JLabel();
        loggingLevelComboBox = new javax.swing.JComboBox<>();

        engineVersionLabel.setText("Engine Version");

        javaRuntimeLabel.setText("Java Runtime");

        initMemoryLabel.setText("Init Game Memory");

        maximumMemoryLabel.setText("Maximum Game Memory");

        extraOptionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Advanced Options"));

        javaArgsLabel.setText("Java Arguments");

        javaArgsTextArea.setColumns(20);
        javaArgsTextArea.setRows(5);
        javaArgsScrollPane.setViewportView(javaArgsTextArea);

        gameArgsLabel.setText("Game Arguments");

        gameArgsTextArea.setColumns(20);
        gameArgsTextArea.setRows(5);
        gameArgsScrollPane.setViewportView(gameArgsTextArea);

        loggingLevelLabel.setText("Logging Level");

        loggingLevelComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ERROR", "WARN", "INFO", "DEBUG", "TRACE" }));
        loggingLevelComboBox.setSelectedIndex(2);

        javax.swing.GroupLayout extraOptionsPanelLayout = new javax.swing.GroupLayout(extraOptionsPanel);
        extraOptionsPanel.setLayout(extraOptionsPanelLayout);
        extraOptionsPanelLayout.setHorizontalGroup(
            extraOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(extraOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(extraOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(javaArgsScrollPane)
                    .addComponent(gameArgsScrollPane)
                    .addGroup(extraOptionsPanelLayout.createSequentialGroup()
                        .addGroup(extraOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(javaArgsLabel)
                            .addComponent(gameArgsLabel)
                            .addComponent(loggingLevelLabel)
                            .addComponent(loggingLevelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        extraOptionsPanelLayout.setVerticalGroup(
            extraOptionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(extraOptionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(javaArgsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(javaArgsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gameArgsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gameArgsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loggingLevelLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loggingLevelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(extraOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(engineVersionLabel)
                                    .addComponent(javaRuntimeLabel)
                                    .addComponent(maximumMemoryLabel))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(engineVersionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(javaRuntimeComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(initMemoryLabel)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(initMemoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(maximumMemoryComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 376, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(engineVersionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(engineVersionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(javaRuntimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(javaRuntimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(initMemoryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(initMemoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(maximumMemoryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(maximumMemoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(extraOptionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> engineVersionComboBox;
    private javax.swing.JLabel engineVersionLabel;
    private javax.swing.JPanel extraOptionsPanel;
    private javax.swing.JLabel gameArgsLabel;
    private javax.swing.JScrollPane gameArgsScrollPane;
    private javax.swing.JTextArea gameArgsTextArea;
    private javax.swing.JComboBox<JavaHeapSize> initMemoryComboBox;
    private javax.swing.JLabel initMemoryLabel;
    private javax.swing.JLabel javaArgsLabel;
    private javax.swing.JScrollPane javaArgsScrollPane;
    private javax.swing.JTextArea javaArgsTextArea;
    private javax.swing.JComboBox<String> javaRuntimeComboBox;
    private javax.swing.JLabel javaRuntimeLabel;
    private javax.swing.JComboBox<String> loggingLevelComboBox;
    private javax.swing.JLabel loggingLevelLabel;
    private javax.swing.JComboBox<JavaHeapSize> maximumMemoryComboBox;
    private javax.swing.JLabel maximumMemoryLabel;
    // End of variables declaration//GEN-END:variables
}