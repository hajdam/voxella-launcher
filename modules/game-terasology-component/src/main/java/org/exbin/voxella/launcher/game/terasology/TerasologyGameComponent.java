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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import org.exbin.voxella.launcher.api.GameRecord;
import org.exbin.voxella.launcher.api.utils.BareBonesBrowserLaunch;
import org.exbin.voxella.launcher.api.utils.UiUtils;

/**
 * Terasology game component.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class TerasologyGameComponent extends JComponent {

    private final String RECOURCE_PREFIX = "org/exbin/voxella/launcher/game/terasology/resources/";
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(RECOURCE_PREFIX + "TerasologyGameComponent");

    private final Image bgImage;
    private final Dimension bgImageSize;
    private final Image logoImage;
    private final Dimension logoImageSize;

    private final Cursor defaultCursor = Cursor.getDefaultCursor();
    private final Cursor textCursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
    private final Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    private final ImageObserver emptyObserver = (Image img, int infoflags, int x, int y, int width, int height) -> false;
    private final Map<AffiliateLink, Image> affiliateImages = new HashMap<>();
    private final Map<AffiliateLink, String> affiliateToolTip = new HashMap<>();
    private final Map<AffiliateLink, String> affiliateUrl = new HashMap<>();
    private final String headerUrl;
    private final String headerToolTip;
    private String gameVersion;

    private GameRecord gameRecord;
    private AffiliateLink activeLink;
    private JTabbedPane tabbedPane;
    private final Point zeroPoint = new Point();

    public TerasologyGameComponent() {
        init();
        bgImage = new ImageIcon(getClass().getResource("/" + RECOURCE_PREFIX + "background.jpg")).getImage();
        bgImageSize = new Dimension(bgImage.getWidth(emptyObserver), bgImage.getHeight(emptyObserver));
        logoImage = new ImageIcon(getClass().getResource("/" + RECOURCE_PREFIX + "logo.png")).getImage();
        logoImageSize = new Dimension(logoImage.getWidth(emptyObserver), logoImage.getHeight(emptyObserver));

        for (AffiliateLink link : AffiliateLink.values()) {
            affiliateImages.put(link, new ImageIcon(getClass().getResource("/" + RECOURCE_PREFIX + resourceBundle.getString("affiliateLink.icon_" + link.getCode()))).getImage());
            affiliateToolTip.put(link, resourceBundle.getString("affiliateLink.toolTip_" + link.getCode()));
            affiliateUrl.put(link, resourceBundle.getString("affiliateLink.link_" + link.getCode()));
        }
        headerUrl = resourceBundle.getString("header.link");
        headerToolTip = resourceBundle.getString("header.toolTip");
        gameVersion = resourceBundle.getString("version.prefix") + resourceBundle.getString("version.unknown");

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                processMousePosition(e);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();

                    AffiliateLink targetLink = getActiveAffiate(mouseX, mouseY);
                    if (targetLink != null) {
                        BareBonesBrowserLaunch.openDesktopURL(affiliateUrl.get(targetLink));
                    } else if (isInHeader(mouseX, mouseY)) {
                        BareBonesBrowserLaunch.openDesktopURL(headerUrl);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (activeLink != null) {
                    activeLink = null;
                    repaint();
                }
                setCursor(null);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                processMousePosition(e);
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                tabbedPane.setBounds(10, logoImageSize.height + 20, width - 20, height - 88 - logoImageSize.height);
            }
        });
    }

    private void processMousePosition(MouseEvent e) {
        Cursor currentCursor = getCursor();
        String currentToolTip = getToolTipText();

        int mouseX = e.getX();
        int mouseY = e.getY();

        Cursor targetCursor = defaultCursor;
        String targetToolTip = null;
        AffiliateLink targetLink = getActiveAffiate(mouseX, mouseY);
        if (targetLink != activeLink) {
            activeLink = targetLink;
            repaint();
        }
        if (activeLink != null) {
            targetCursor = handCursor;
            targetToolTip = affiliateToolTip.get(activeLink);
        } else if (isInHeader(mouseX, mouseY)) {
            targetCursor = handCursor;
            targetToolTip = headerToolTip;
        } else {
//                    targetCursor = tabbedPane.getCursor();
//                    Component component = getComponentAt(mouseX, mouseY);
//                    if (component != TerasologyGameComponent.this) {
//                        targetCursor = component.getCursor();
//                    }
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

    private void init() {
        tabbedPane = new JTabbedPane();
        JEditorPane aboutText = new JEditorPane() {
            @Override
            public void paint(Graphics g) {
                super.paint(getWrapperGraphics(g, this));
            }
        };

        aboutText.setEditorKit(JEditorPane.createEditorKitForContentType(""));
        aboutText.setEditable(false);
        aboutText.setContentType("text/html");
        aboutText.setText(resourceBundle.getString("aboutText.text"));
        aboutText.setOpaque(false);
        aboutText.addHyperlinkListener((HyperlinkEvent e) -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                BareBonesBrowserLaunch.openDesktopURL(e.getURL());
            }
        });
        JScrollPane aboutScrollPane = new JScrollPane() {
            @Override
            public void paint(Graphics g) {
                super.paint(getWrapperGraphics(g, this));
            }
        };
        JViewport aboutViewport = new JViewport() {
            @Override
            public void paint(Graphics g) {
                super.paint(getWrapperGraphics(g, this));
            }
        };
        aboutViewport.setView(aboutText);
        aboutScrollPane.setViewport(aboutViewport);
        aboutScrollPane.setOpaque(false);
        tabbedPane.addTab(resourceBundle.getString("aboutTab.title"), aboutScrollPane);

        JEditorPane updatesText = new JEditorPane() {
            @Override
            public void paint(Graphics g) {
                super.paint(getWrapperGraphics(g, this));
            }
        };
        updatesText.setText("TODO");
        updatesText.setEditable(false);
        JScrollPane updatesScrollPane = new JScrollPane() {
            @Override
            public void paint(Graphics g) {
                super.paint(getWrapperGraphics(g, this));
            }
        };
        JViewport updatesViewport = new JViewport() {
            @Override
            public void paint(Graphics g) {
                super.paint(getWrapperGraphics(g, this));
            }
        };
        updatesViewport.setView(updatesText);
        updatesScrollPane.setViewport(updatesViewport);
        updatesScrollPane.setOpaque(false);
        tabbedPane.addTab(resourceBundle.getString("updatesTab.title"), updatesScrollPane);
        tabbedPane.invalidate();
        add(tabbedPane);
    }

    public void setGameRecord(@Nullable GameRecord gameRecord) {
        this.gameRecord = gameRecord;
        gameVersion = resourceBundle.getString("version.prefix") + (gameRecord != null ? gameRecord.getVersion() : resourceBundle.getString("version.unknown"));
    }

    @Override
    public void paint(Graphics g) {
        Dimension size = getSize();
        paintBgImage(g, size, 0, 0);

        int affiliateX = getAffiliateLinkRowX();
        int affiliateY = getAffiliateLinkRowY();
        int affiliateOffsetX = 0;
        for (AffiliateLink link : AffiliateLink.values()) {
            if (link == activeLink) {
                g.drawImage(affiliateImages.get(link), affiliateX + affiliateOffsetX, affiliateY, emptyObserver);
            } else {
                g.drawImage(affiliateImages.get(link), affiliateX + affiliateOffsetX + 5, affiliateY + 5, 38, 38, emptyObserver);
            }
            affiliateOffsetX += 58;
        }

        g.setColor(new Color(127, 127, 127, 127));
        g.fillRect(10, 10, size.width - 20, logoImageSize.height);
        g.drawImage(logoImage, 10, 10, emptyObserver);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(Font.BOLD));
        g.drawString(gameVersion, size.width - 145, 100);

//        g.translate(10, logoImageSize.height + 30);
        Graphics gWrapper = getComponentGraphics(g);
        super.paint(gWrapper);
    }

    private void paintBgImage(Graphics g, Dimension size, int bgX, int bgY) {
        int bgWidth;
        int bgHeight;
        if (bgImageSize.width * size.height >= size.width * bgImageSize.height) {
            bgWidth = size.height * bgImageSize.width / bgImageSize.height;
            bgX += -(bgWidth - size.width) / 2;
            bgHeight = size.height;
        } else {
            bgWidth = size.width;
            bgHeight = size.width * bgImageSize.height / bgImageSize.width;
            bgY += -(bgHeight - size.height) / 2;
        }

        g.drawImage(bgImage, bgX, bgY, bgWidth, bgHeight, emptyObserver);
    }

    @Nonnull
    @Override
    protected Graphics getComponentGraphics(Graphics g) {
        return TerasologyGameComponent.createGraphicsWrapper(g);
    }

    private int getAffiliateLinkRowX() {
        int affiliateRowWidth = getAffiliateLinkWidth();
        return (getWidth() - affiliateRowWidth) / 2;
    }

    private int getAffiliateLinkWidth() {
        int affiliatesCount = AffiliateLink.values().length;
        return affiliatesCount * 58 - 10;
    }

    private int getAffiliateLinkRowY() {
        return getHeight() - 58;
    }

    @Nullable
    private AffiliateLink getActiveAffiate(int posX, int posY) {
        int affiliateX = getAffiliateLinkRowX();
        int affiliateY = getAffiliateLinkRowY();

        if (posY > affiliateY && posY < affiliateY + 48) {
            int affiliateLinkWidth = getAffiliateLinkWidth();
            if (posX > affiliateX && posX < affiliateX + affiliateLinkWidth) {
                int affiliateLinkIndex = (posX - affiliateX) / 58;
                if (posX < affiliateX + affiliateLinkIndex * 58 + 48) {
                    return AffiliateLink.values()[affiliateLinkIndex];
                }
            }
        }

        return null;
    }

    private boolean isInHeader(int posX, int posY) {
        return posX > 10 && posX < logoImageSize.width + 10 && posY > 10 && posY < logoImageSize.height + 10;
    }

    private static Graphics2DWrapper createGraphicsWrapper(Graphics g) {
        if (g instanceof Graphics2DWrapper) {
            return (Graphics2DWrapper) g;
        }
        return new Graphics2DWrapper((Graphics2D) g, UiUtils.isDarkUI() ? 196 : 159);
    }

    @Nonnull
    private Graphics getWrapperGraphics(Graphics g, JComponent component) {
        if (g instanceof Graphics2DWrapper) {
            return g;
        }

        Dimension size = getSize();
        Point relativePos = SwingUtilities.convertPoint(component, zeroPoint, this);
        paintBgImage(g, size, -relativePos.x, -relativePos.y);
        return TerasologyGameComponent.createGraphicsWrapper(g);
    }

    private enum AffiliateLink {
        FACEBOOK,
        GITHUB,
        DISCORD,
        REDDIT,
        TWITTER,
        YOUTUBE,
        PATREON;

        @Nonnull
        private String getCode() {
            return name().toLowerCase();
        }
    }
}
