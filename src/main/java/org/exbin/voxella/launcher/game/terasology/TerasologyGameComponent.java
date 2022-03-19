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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Terasology game component.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class TerasologyGameComponent extends JComponent {

    private Image bgImage;
    private Dimension bgImageSize;
    private Image logoImage;
    private Dimension logoImageSize;

    private final Cursor defaultCursor = Cursor.getDefaultCursor();
    private final Cursor textCursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
    private final Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    private final ImageObserver emptyObserver = (Image img, int infoflags, int x, int y, int width, int height) -> false;
    private Image discordImage;
    private Image facebookImage;

    private JButton testButton = new JButton("Test");

    public TerasologyGameComponent() {
        init();
        bgImage = new ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/game/component/terasology/resources/background.jpg")).getImage();
        bgImageSize = new Dimension(bgImage.getWidth(emptyObserver), bgImage.getHeight(emptyObserver));
        logoImage = new ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/game/component/terasology/resources/logo.png")).getImage();
        logoImageSize = new Dimension(logoImage.getWidth(emptyObserver), logoImage.getHeight(emptyObserver));

        discordImage = new ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/game/component/terasology/resources/discord.png")).getImage();
        facebookImage = new ImageIcon(getClass().getResource("/org/exbin/voxella/launcher/game/component/terasology/resources/facebook.png")).getImage();
        testButton.setSize(testButton.getPreferredSize());

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Cursor currentCursor = getCursor();
                String currentToolTip = getToolTipText();
                Dimension size = getSize();

                int mouseX = e.getX();
                int mouseY = e.getY();

                Cursor targetCursor = defaultCursor;
                String targetToolTip = null;
                if (mouseY > size.height - 58 && mouseY < size.height - 10) {
                    if (mouseX > 10 && mouseX < 58) {
                        targetToolTip = "Terasology on Discord";
                        targetCursor = handCursor;
                    } else if (mouseX > 68 && mouseX < 116) {
                        targetToolTip = "Terasology on Facebook";
                        targetCursor = handCursor;
                    }
                }

                if (targetCursor != currentCursor) {
                    setCursor(targetCursor);
                }
                if (targetToolTip == null) {
                    if (currentToolTip != null) {
                        setToolTipText(targetToolTip);
                    }
                } else if (!targetToolTip.equals(currentToolTip)) {
                    setToolTipText(targetToolTip);
                }
            }
        });

        setCursor(handCursor);
    }

    private void init() {
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension size = getSize();

        int bgWidth;
        int bgHeight;
        int bgX = 0;
        int bgY = 0;
        if (bgImageSize.width * size.height > size.width * bgImageSize.height) {
            bgWidth = size.height * bgImageSize.width / bgImageSize.height;
            bgX = - (bgWidth - size.width) / 2;
            bgHeight = size.height;
        } else {
            bgWidth = size.width;
            bgHeight = size.width * bgImageSize.height / bgImageSize.width;
            bgY = - (bgHeight - size.height) / 2;
        }

        g.drawImage(bgImage, bgX, bgY, bgWidth, bgHeight, emptyObserver);

        g.drawImage(discordImage, 10, size.height - 58, emptyObserver);
        g.drawImage(facebookImage, 68, size.height - 58, emptyObserver);

        g.setColor(new Color(127, 127, 127, 127));
        g.fillRect(10, 10, size.width - 20, logoImageSize.height);
        g.drawImage(logoImage, 10, 10, emptyObserver);
        g.translate(10, logoImageSize.height + 30);
        testButton.paint(g);

        // TODO
    }
    
    private enum AffiliateLink {
        DISCORD,
        FACEBOOK
    }
}
