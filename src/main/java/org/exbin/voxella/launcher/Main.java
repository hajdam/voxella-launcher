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

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.exbin.voxella.launcher.api.GameController;
import org.exbin.voxella.launcher.gui.AboutPanel;
import org.exbin.voxella.launcher.gui.CheckForUpdatePanel;
import org.exbin.voxella.launcher.gui.GameListPanel;
import org.exbin.voxella.launcher.gui.LauncherPanel;
import org.exbin.voxella.launcher.gui.NewsPanel;
import org.exbin.voxella.launcher.gui.OptionsPanel;
import org.exbin.voxella.launcher.gui.UserStatusPanel;
import org.exbin.voxella.launcher.model.GameRecord;
import org.exbin.voxella.launcher.model.LanguageRecord;
import org.exbin.voxella.launcher.model.Launcher;
import org.exbin.voxella.launcher.model.ThemeRecord;
import org.exbin.voxella.launcher.preferences.Preferences;
import org.exbin.voxella.launcher.utils.ClipboardUtils;
import org.exbin.voxella.launcher.utils.WindowUtils;
import org.exbin.voxella.launcher.utils.gui.CloseControlPanel;

/**
 * Voxella Launcher main window.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class Main {

    private static final LookAndFeel defaultLaf = UIManager.getLookAndFeel();

    private Main() {
    }

    public static void main(String[] args) {
        final Launcher launcher = new Launcher();
        final Preferences settings = launcher.loadSettings();

        final ResourceBundle resourceBundle = ResourceBundle.getBundle("org/exbin/voxella/launcher/resources/Launcher");

        String language = settings.get(Launcher.PREFERENCES_LANGUAGE, "");
        if (!language.isEmpty()) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
        }

        final String defaultThemeClass = "com.formdev.flatlaf.FlatDarkLaf";
        String themeClass = settings.get(Launcher.PREFERENCES_THEME, defaultThemeClass);
        FlatDarkLaf.installLafInfo();
        FlatLightLaf.installLafInfo();
        try {
            UIManager.setLookAndFeel(themeClass);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        ClipboardUtils.registerDefaultClipboardPopupMenu();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            JFrame applicationFrame = new JFrame();
            applicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//            applicationFrame.setUndecorated(true);
            String iconPath = resourceBundle.getString("Application.icon");
            final Image applicationIcon = iconPath != null ? new javax.swing.ImageIcon(launcher.getClass().getResource(iconPath)).getImage() : null;

            final LauncherPanel mainPanel = new LauncherPanel();

            GameListPanel gameListPanel = new GameListPanel();
            gameListPanel.setOptionsAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameRecord gameRecord = gameListPanel.getSelectedGame();
                    JComponent optionsPanel = gameRecord.getOptionsComponent().get();

                    CloseControlPanel controlPanel = new CloseControlPanel();
                    JPanel dialogPanel = WindowUtils.createDialogPanel(optionsPanel, controlPanel);
                    final WindowUtils.DialogWrapper dialog = WindowUtils.createDialog(dialogPanel, applicationFrame, resourceBundle.getString("optionsDialog.title"), Dialog.ModalityType.APPLICATION_MODAL);
                    controlPanel.setHandler(dialog::close);
                    dialog.center(applicationFrame);
                    if (applicationIcon != null) {
                        dialog.getWindow().setIconImage(applicationIcon);
                    }
                    dialog.show();
                }
            });
            gameListPanel.setLaunchAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameRecord gameRecord = gameListPanel.getSelectedGame();
                    GameController controller = gameRecord.getController();
                    controller.runGame();
                }
            });

            NewsPanel newsPanel = new NewsPanel();

            List<LanguageRecord> languageRecords = new ArrayList<>();
            languageRecords.add(new LanguageRecord(resourceBundle.getString("language.default"), "", null, null, null));
            int index = 0;
            do {
                String prefix = "language" + index;
                if (resourceBundle.containsKey(prefix + ".name")) {
                    String name = resourceBundle.getString(prefix + ".name");
                    String locale = resourceBundle.getString(prefix + ".locale");
                    String code = resourceBundle.getString(prefix + ".code");
                    String alt = resourceBundle.containsKey(prefix + ".alt") ? resourceBundle.getString(prefix + ".alt") : null;
                    ImageIcon icon = null;
                    if (resourceBundle.containsKey(prefix + ".icon")) {
                        String langIconPath = resourceBundle.getString(prefix + ".icon");
                        icon = new ImageIcon(launcher.getClass().getResource(langIconPath));
                    }
                    languageRecords.add(new LanguageRecord(name, locale, code, alt, icon));
                } else {
                    break;
                }
                index++;
            } while (true);

            Set<String> lafClassNames = new HashSet<>();
            List<ThemeRecord> themeRecords = new ArrayList<>();
            themeRecords.add(new ThemeRecord(defaultThemeClass, FlatDarkLaf.NAME + resourceBundle.getString("theme.defaultSuffix")));
            lafClassNames.add(defaultThemeClass);
            themeRecords.add(new ThemeRecord("", resourceBundle.getString("theme.javaDefault")));
            String crossLafClassName = UIManager.getCrossPlatformLookAndFeelClassName();
            final String metalLafClassName = "javax.swing.plaf.metal.MetalLookAndFeel";
            boolean extraCrossPlatformLAF = !metalLafClassName.equals(crossLafClassName);
            if (extraCrossPlatformLAF) {
                themeRecords.add(new ThemeRecord(crossLafClassName, resourceBundle.getString("theme.crossLaf")));
                lafClassNames.add(crossLafClassName);
            }
            themeRecords.add(new ThemeRecord(metalLafClassName, "Metal"));
            lafClassNames.add(metalLafClassName);
            final String motifLafClassName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            themeRecords.add(new ThemeRecord(motifLafClassName, "Motif"));
            lafClassNames.add(motifLafClassName);
            UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
            for (UIManager.LookAndFeelInfo lookAndFeelInfo : infos) {
                String lafClassName = lookAndFeelInfo.getClassName();
                if (!lafClassNames.contains(lafClassName)) {
                    themeRecords.add(new ThemeRecord(lafClassName, lookAndFeelInfo.getName()));
                }
            }

            OptionsPanel optionsPanel = new OptionsPanel();
            optionsPanel.setLanguages(languageRecords);
            optionsPanel.setThemes(themeRecords);
            optionsPanel.setActiveTheme(themeClass);
            optionsPanel.setActiveLanguage(language);
            optionsPanel.setThemeChangeListener((themeRecord) -> {
                switchLookAndFeel(themeRecord.getClassName(), applicationFrame);
                settings.put(Launcher.PREFERENCES_THEME, themeRecord.getClassName());
            });
            optionsPanel.setLanguageChangeListener((languageRecord) -> {
                settings.put(Launcher.PREFERENCES_LANGUAGE, languageRecord.getLocale());
            });
            optionsPanel.setAboutAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AboutPanel aboutPanel = new AboutPanel();
                    AboutPanel.AboutInfo aboutInfo = new AboutPanel.AboutInfo();
                    aboutInfo.title = resourceBundle.getString("Application.title");
                    aboutInfo.description = resourceBundle.getString("Application.description");
                    aboutInfo.name = resourceBundle.getString("Application.title");
                    aboutInfo.version = resourceBundle.getString("Application.version");
                    aboutInfo.license = resourceBundle.getString("Application.license");
                    aboutInfo.vendor = resourceBundle.getString("Application.vendor");
                    aboutInfo.homepage = resourceBundle.getString("Application.homepage");
                    aboutInfo.aboutImage = resourceBundle.getString("Application.aboutImage");
                    aboutPanel.setAboutInfo(aboutInfo);

                    CloseControlPanel controlPanel = new CloseControlPanel();
                    JPanel dialogPanel = WindowUtils.createDialogPanel(aboutPanel, controlPanel);
                    final WindowUtils.DialogWrapper dialog = WindowUtils.createDialog(dialogPanel, applicationFrame, resourceBundle.getString("aboutDialog.title"), Dialog.ModalityType.APPLICATION_MODAL);
                    controlPanel.setHandler(dialog::close);
                    dialog.center(applicationFrame);
                    if (applicationIcon != null) {
                        dialog.getWindow().setIconImage(applicationIcon);
                    }
                    dialog.show();
                }
            });

            mainPanel.setGameListComponent(gameListPanel);
            mainPanel.setNewsComponent(newsPanel);
            mainPanel.setOptionsComponent(optionsPanel);

            UserStatusPanel userStatusPanel = new UserStatusPanel();
            mainPanel.setUserStatusPanel(userStatusPanel);

            CheckForUpdatePanel checkForUdatePanel = new CheckForUpdatePanel();
            mainPanel.setProgressStatusPanel(checkForUdatePanel);

            if (applicationIcon != null) {
                applicationFrame.setIconImage(applicationIcon);
            }

            applicationFrame.setTitle(resourceBundle.getString("Application.title"));
            applicationFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    launcher.saveSettings();
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

    private static void switchLookAndFeel(String className, @Nullable JFrame applicationFrame) {
        try {
            if (!className.isEmpty()) {
                UIManager.setLookAndFeel(className);
            } else {
                UIManager.setLookAndFeel(defaultLaf);
            }

            if (applicationFrame != null) {
                SwingUtilities.updateComponentTreeUI(applicationFrame);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(OptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
