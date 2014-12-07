/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.croer.javaorange.util;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author elialva
 */
public class Util {

    public static void activateFunctionKey(JDialog dialog, AbstractAction formAction) {
        JRootPane rootPane = dialog.getRootPane();
        String keyStroke = (String) formAction.getValue(Action.NAME);
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyStroke), keyStroke);
        rootPane.getActionMap().put(keyStroke, formAction);
    }
    
    public static void activateFunctionKey(JFrame frame, AbstractAction formAction) {
        JRootPane rootPane = frame.getRootPane();
        String keyStroke = (String) formAction.getValue(Action.NAME);
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyStroke), keyStroke);
        rootPane.getActionMap().put(keyStroke, formAction);
    }
}
