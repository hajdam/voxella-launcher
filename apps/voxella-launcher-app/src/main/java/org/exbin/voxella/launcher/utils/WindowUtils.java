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
package org.exbin.voxella.launcher.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;
import org.exbin.voxella.launcher.utils.handler.OkCancelService;

/**
 * Launcher model.
 *
 * @author Voxella Project
 */
@ParametersAreNonnullByDefault
public class WindowUtils {

    public static final String ESC_CANCEL_KEY = "esc-cancel";
    public static final String ENTER_OK_KEY = "enter-ok";

    private static final int BUTTON_CLICK_TIME = 150;

    private WindowUtils() {
    }

    public static void invokeWindow(final @Nonnull Window window) {
//        if (lookAndFeel != null) {
//            try {
//                javax.swing.UIManager.setLookAndFeel(lookAndFeel);
//            } catch (UnsupportedLookAndFeelException ex) {
//                Logger.getLogger(WindowUtils.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

        java.awt.EventQueue.invokeLater(() -> {
            if (window instanceof JDialog) {
                ((JDialog) window).setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }

            window.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            window.setVisible(true);
        });
    }

    @Nonnull
    public static DialogWrapper createDialog(final JComponent component, Component parent, String dialogTitle, Dialog.ModalityType modalityType) {
        final JDialog dialog = new JDialog(getWindow(parent), modalityType);
        Dimension size = component.getPreferredSize();
        dialog.add(component);
        dialog.setSize(size.width + 8, size.height + 24);
        dialog.setTitle(dialogTitle);
        if (component instanceof OkCancelService) {
            assignGlobalKeyListener(dialog, ((OkCancelService) component).getOkCancelListener());
        }
        return new DialogWrapper() {
            @Override
            public void show() {
                dialog.setVisible(true);
            }

            @Override
            public void showCentered(@Nullable Component component) {
                center(component);
                show();
            }

            @Override
            public void close() {
                closeWindow(dialog);
            }

            @Override
            public void dispose() {
                dialog.dispose();
            }

            @Override
            public Window getWindow() {
                return dialog;
            }

            @Override
            public Container getParent() {
                return dialog.getParent();
            }

            @Override
            public void center(@Nullable Component component) {
                if (component == null) {
                    center();
                } else {
                    dialog.setLocationRelativeTo(component);
                }
            }

            @Override
            public void center() {
                dialog.setLocationByPlatform(true);
            }
        };
    }

    @Nonnull
    public static JDialog createDialog(final JComponent component) {
        JDialog dialog = new JDialog();
        Dimension size = component.getPreferredSize();
        dialog.add(component);
        dialog.setSize(size.width + 8, size.height + 24);
        if (component instanceof OkCancelService) {
            assignGlobalKeyListener(dialog, ((OkCancelService) component).getOkCancelListener());
        }
        return dialog;
    }

    public static void invokeDialog(final JComponent component) {
        JDialog dialog = createDialog(component);
        invokeWindow(dialog);
    }

    public static void closeWindow(Window window) {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Finds frame component for given component.
     *
     * @param component instantiated component
     * @return frame instance if found
     */
    @Nullable
    public static Frame getFrame(Component component) {
        Window parentComponent = SwingUtilities.getWindowAncestor(component);
        while (!(parentComponent == null || parentComponent instanceof Frame)) {
            parentComponent = SwingUtilities.getWindowAncestor(parentComponent);
        }
        if (parentComponent == null) {
            parentComponent = JOptionPane.getRootFrame();
        }
        return (Frame) parentComponent;
    }

    @Nullable
    public static Window getWindow(Component component) {
        return SwingUtilities.getWindowAncestor(component);
    }

    /**
     * Assign ESCAPE/ENTER key for all focusable components recursively.
     *
     * @param component target component
     * @param closeButton button which will be used for closing operation
     */
    public static void assignGlobalKeyListener(Component component, final JButton closeButton) {
        assignGlobalKeyListener(component, closeButton, closeButton);
    }

    /**
     * Assign ESCAPE/ENTER key for all focusable components recursively.
     *
     * @param component target component
     * @param okButton button which will be used for default ENTER
     * @param cancelButton button which will be used for closing operation
     */
    public static void assignGlobalKeyListener(Component component, final JButton okButton, final JButton cancelButton) {
        assignGlobalKeyListener(component, new OkCancelListener() {
            @Override
            public void okEvent() {
                doButtonClick(okButton);
            }

            @Override
            public void cancelEvent() {
                doButtonClick(cancelButton);
            }
        });
    }

    /**
     * Assign ESCAPE/ENTER key for all focusable components recursively.
     *
     * @param component target component
     * @param listener ok and cancel event listener
     */
    public static void assignGlobalKeyListener(Component component, @Nullable final OkCancelListener listener) {
        JRootPane rootPane = SwingUtilities.getRootPane(component);
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ESC_CANCEL_KEY);
        rootPane.getActionMap().put(ESC_CANCEL_KEY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (listener == null) {
                    return;
                }

                boolean performCancelAction = true;

                Window window = SwingUtilities.getWindowAncestor(event.getSource() instanceof JRootPane ? (JRootPane) event.getSource() : rootPane);
                if (window != null) {
                    Component focusOwner = window.getFocusOwner();
                    if (focusOwner instanceof JComboBox) {
                        performCancelAction = !((JComboBox) focusOwner).isPopupVisible();
                    } else if (focusOwner instanceof JRootPane) {
                        // Ignore in popup menus
                        // performCancelAction = false;
                    }
                }

                if (performCancelAction) {
                    listener.cancelEvent();
                }
            }
        });

        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), ENTER_OK_KEY);
        rootPane.getActionMap().put(ENTER_OK_KEY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (listener == null) {
                    return;
                }

                boolean performOkAction = true;

                Window window = SwingUtilities.getWindowAncestor(event.getSource() instanceof JRootPane ? (JRootPane) event.getSource() : rootPane);
                if (window != null) {
                    Component focusOwner = window.getFocusOwner();
                    if (focusOwner instanceof JTextArea || focusOwner instanceof JEditorPane) {
                        performOkAction = !((JTextComponent) focusOwner).isEditable();
                    }
                }

                if (performOkAction) {
                    listener.okEvent();
                }
            }
        });
    }

    /**
     * Performs visually visible click on the button component.
     *
     * @param button button component
     */
    public static void doButtonClick(JButton button) {
        button.doClick(BUTTON_CLICK_TIME);
    }

    /**
     * Creates panel for given main and control panel.
     *
     * @param mainPanel main panel
     * @param controlPanel control panel
     * @return panel
     */
    @Nonnull
    public static JPanel createDialogPanel(@Nonnull JComponent mainPanel, @Nonnull JComponent controlPanel) {
        JPanel dialogPanel;
        if (controlPanel instanceof OkCancelService) {
            dialogPanel = new DialogPanel((OkCancelService) controlPanel);
        } else {
            dialogPanel = new JPanel(new BorderLayout());
        }
        dialogPanel.add(mainPanel, BorderLayout.CENTER);
        dialogPanel.add(controlPanel, BorderLayout.SOUTH);
        Dimension mainPreferredSize = mainPanel.getPreferredSize();
        Dimension controlPreferredSize = controlPanel.getPreferredSize();
        dialogPanel.setPreferredSize(new Dimension(mainPreferredSize.width, mainPreferredSize.height + controlPreferredSize.height));
        return dialogPanel;
    }

    @ParametersAreNonnullByDefault
    private static final class DialogPanel extends JPanel implements OkCancelService {

        private final OkCancelService okCancelService;

        public DialogPanel(OkCancelService okCancelService) {
            super(new BorderLayout());
            this.okCancelService = okCancelService;
        }

        @Nullable
        @Override
        public JButton getDefaultButton() {
            return null;
        }

        @Nonnull
        @Override
        public OkCancelListener getOkCancelListener() {
            return okCancelService.getOkCancelListener();
        }
    }

    @ParametersAreNonnullByDefault
    public interface DialogWrapper {

        void show();

        void showCentered(@Nullable Component window);

        void close();

        void dispose();

        @Nonnull
        Window getWindow();

        @Nonnull
        Container getParent();

        void center(@Nullable Component window);

        void center();
    }
}
