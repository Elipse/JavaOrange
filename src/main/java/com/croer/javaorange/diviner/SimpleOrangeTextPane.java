/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.croer.javaorange.diviner;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author elialva
 */
public class SimpleOrangeTextPane extends JTextPane {

    public SimpleOrangeTextPane() {
        addKeyListener(new KeyListenerTextPane());
        getDocument().addDocumentListener(new DocumentListenerTextPane());
    }

    private class KeyListenerTextPane implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER: {
                    SimpleOrangeTextPane.this.firePropertyChange("selection", 0, e.getKeyCode());
                    e.consume();
                    break;
                }
                case KeyEvent.VK_PAGE_UP: {
                    SimpleOrangeTextPane.this.firePropertyChange("pageup", 0, e.getKeyCode());
                    e.consume();
                    break;
                }
                case KeyEvent.VK_PAGE_DOWN: {
                    SimpleOrangeTextPane.this.firePropertyChange("pagedown", 0, e.getKeyCode());
                    e.consume();
                    break;
                }
                case KeyEvent.VK_UP: {
                    SimpleOrangeTextPane.this.firePropertyChange("up", 0, e.getKeyCode());
                    e.consume();
                    break;
                }
                case KeyEvent.VK_DOWN: {
                    SimpleOrangeTextPane.this.firePropertyChange("down", 0, e.getKeyCode());
                    e.consume();
                    break;
                }
                case KeyEvent.VK_INSERT:
                    String keys = KeyEvent.getModifiersExText(e.getModifiersEx());
                    if (keys.equals("Shift")) {
                        SimpleOrangeTextPane.this.firePropertyChange("substract", 0, e.getKeyCode());
                    } else {
                        SimpleOrangeTextPane.this.firePropertyChange("add", 0, e.getKeyCode());
                    }
                    e.consume();
                    break;
                default:
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private class DocumentListenerTextPane implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            fireText(e);
            colorStyledDocument((DefaultStyledDocument) e.getDocument());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            fireText(e);
            colorStyledDocument((DefaultStyledDocument) e.getDocument());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

    }

    public void fireText(DocumentEvent e) {
        Document document = e.getDocument();
        String text = "";
        try {
            text = document.getText(0, document.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(SimpleOrangeTextPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        firePropertyChange("text", null, text);
    }

    public void colorStyledDocument(final DefaultStyledDocument document) {
        String[] styleNames = new String[]{"magentaStyle", "blueStyle", "redStyle"};
        final Style[] styles = new Style[styleNames.length];
        Color[] colors = new Color[]{Color.magenta, Color.blue, Color.red};
        StyleContext sc = new StyleContext();

        for (int i = 0; i < styles.length; i++) {
            styles[i] = sc.addStyle(styleNames[i], sc.getStyle(StyleContext.DEFAULT_STYLE));
            StyleConstants.setForeground(styles[i], colors[i]);
            StyleConstants.setBold(styles[i], true);
        }

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                String input = "";
                try {
                    input = document.getText(0, document.getLength());
                } catch (BadLocationException ex) {
//                    Logger.getLogger(ColorPane.class.getName()).log(Level.SEVERE, null, ex);
                }

                StringBuilder inputMut = new StringBuilder(input);
                String[] split = StringUtils.split(inputMut.toString());
                int i = 0;
                for (String string : split) {
                    int start = inputMut.indexOf(string);
                    int end = start + string.length();
                    inputMut.replace(start, end, StringUtils.repeat(" ", string.length()));
                    document.setCharacterAttributes(start, string.length(), styles[i++ % styles.length], true);
                }
            }
        }
        );
    }
}
