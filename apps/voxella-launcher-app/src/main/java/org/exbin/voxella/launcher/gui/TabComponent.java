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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

/**
 * Tab component with support for changes count.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class TabComponent extends JComponent {

    private final JTabbedPane tabbedPane;
    private final int index;
    private int changesCount = 1;
    private final Color changesCountBg = new Color(225, 225, 47);

    public TabComponent(JTabbedPane tabbedPane, int index) {
        this.tabbedPane = tabbedPane;
        this.index = index;
        setBorder(null);
        setSize(130, 32);
        setPreferredSize(getSize());
    }

    public int getChangesCount() {
        return changesCount;
    }

    public void setChangesCount(int changesCount) {
        this.changesCount = changesCount;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Icon icon = tabbedPane.getIconAt(index);
        if (icon != null) {
            icon.paintIcon(this, g, 0, 0);
        }
        String title = tabbedPane.getTitleAt(index);
        g.setColor(tabbedPane.getForegroundAt(index));
        Font titleFont = tabbedPane.getFont();
        g.setFont(titleFont);
        g.drawString(title, 36, 14 + titleFont.getSize() / 2);
        if (changesCount > 0) {
            g.setColor(changesCountBg);
            g.fillOval(110, 8, 20, 16);
            g.setColor(Color.BLACK);
            Font font = getFont().deriveFont(Font.BOLD);
            g.setFont(font);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            String text = changesCount > 99 ? "+" : String.valueOf(changesCount);
            char[] changesCharArray = text.toCharArray();
            int width = fontMetrics.charsWidth(changesCharArray, 0, changesCharArray.length);
            g.drawString(text, 120 - width / 2, 21);
        }
    }
}
