/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.croer.javaorange.diviner;

import com.croer.javaorange.util.ColorUtils;
import com.croer.javaorange.util.Configuration;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author elialva
 */
public class SimpleOrangeTextPane extends JTextPane {

    List<Color> colorArray = Arrays.asList(new Color[]{Color.black, Color.orange});
    List<String> textArray = Arrays.asList(new String[]{"codibar", "descrip", "color", "todo"});
    private final CompositeConfiguration CONFIGURATION;
    private Style[] styles;

    public SimpleOrangeTextPane() {
        this.CONFIGURATION = Configuration.getCONFIGURATION();

        //Create the style array to show the colors 
        List<Object> colorList = CONFIGURATION.getList("ColorWord");
        styles = new Style[colorList.size()];
        StyleContext sc = new StyleContext();
        for (int i = 0; i < colorList.size(); i++) {
            styles[i] = sc.addStyle((String) colorList.get(i), sc.getStyle(StyleContext.DEFAULT_STYLE));
            StyleConstants.setForeground(styles[i], ColorUtils.getColorByName((String) colorList.get(i)));
            StyleConstants.setBold(styles[i], true);
        }

//        addKeyListener(new KeyListenerTextPane());
        List<Object> navigationList = CONFIGURATION.getList("PageNavigation");
        System.out.println("ListaY " + navigationList);
        for (Object object : navigationList) {
            getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(object.toString()), "none");
        }

        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("F5"), "COLOR");

        getActionMap().put("COLOR", new AbstractAction() {

            int i = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                String[] styleNames = new String[]{"magentaStyle", "blueStyle", "redStyle"};
                final Style[] styles = new Style[styleNames.length];
                Color[] colors = new Color[]{Color.magenta, Color.blue, Color.red};
                TitledBorder border = (TitledBorder) SimpleOrangeTextPane.this.getBorder();
                Color a = colorArray.get(i++ % colorArray.size());

                border.setTitleColor(a);

                repaint();

            }
        });
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("F6"), "context");

        getActionMap().put("context", new AbstractAction() {

            int i = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                TitledBorder border = (TitledBorder) SimpleOrangeTextPane.this.getBorder();
                border.setTitle(textArray.get(i++ % textArray.size()));
                repaint();

            }
        });

        getActionMap().put("na", null);
        DefaultStyledDocument dsd = (DefaultStyledDocument) getDocument();
        dsd.addDocumentListener(new DocumentListenerTextPane());
        dsd.setDocumentFilter(new DocumentFilter() {

            Pattern regEx = Pattern.compile("^[\\d\\s]*$");

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regEx.matcher(text);
                if (!matcher.matches()) {
                    return;
                }
                super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                System.out.println("LaCden es " + string);
                Matcher matcher = regEx.matcher(string);
                if (!matcher.matches()) {
                    return;
                }
                super.insertString(fb, offset, string, attr); //To change body of generated methods, choose Tools | Templates.
            }
        });
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

//                    e.consume();
                    break;
                }
                case KeyEvent.VK_PAGE_UP: {
//                    SimpleOrangeTextPane.this.firePropertyChange("pageup", 0, e.getKeyCode());
//                    e.consume();
                    break;
                }
                case KeyEvent.VK_PAGE_DOWN: {
                    SimpleOrangeTextPane.this.firePropertyChange("pagedown", 0, e.getKeyCode());
//                    e.consume();
                    break;
                }
//                case KeyEvent.VK_UP: {
//                    SimpleOrangeTextPane.this.firePropertyChange("up", 0, e.getKeyCode());
//                    e.consume();
//                    break;
//                }
                case KeyEvent.VK_DOWN: {
                    SimpleOrangeTextPane.this.firePropertyChange("down", 0, e.getKeyCode());
                    e.consume();
                    JTable d;
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

    protected void fireText(DocumentEvent e) {
        Document document = e.getDocument();
        String text = "";
        try {
            text = document.getText(0, document.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(SimpleOrangeTextPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        firePropertyChange("text", null, text);
    }

    protected void colorStyledDocument(final DefaultStyledDocument document) {
        EventQueue.invokeLater(new Runnable() {

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

    public void setLook(String title, Color color, DocumentFilter filter) {
        //set title
        DefaultStyledDocument dsd = (DefaultStyledDocument) getDocument();
        dsd.addDocumentListener(new DocumentListenerTextPane());
        dsd.setDocumentFilter(filter);
    }
}
