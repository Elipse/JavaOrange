package com.croer.javaorange.definition;

import java.awt.event.KeyEvent;
import javax.swing.JComponent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author elialva
 */
public class Mediator {

    private final FocusManager testosFocus;
    private JComponent oldComponent;
    private OrangeDiviner orangeDiviner;
    private OrangeScribe orangeScribe;

    private Mediator() {
        this.testosFocus = new FocusManager(this);
    }

    public static void install(OrangeFactory orangeFactory) {
        Mediator testosMediator = new Mediator();
        testosMediator.setOrangeDiviner(orangeFactory.newOrangeDiviner());
        testosMediator.setOrangeText(orangeFactory.newOrangeScribe());
    }

    void focusGained(JComponent jComponent) {
        if (jComponent.getTopLevelAncestor() == orangeDiviner || oldComponent == jComponent) {
            return;
        }

        oldComponent = jComponent;

        if (getType(jComponent).equals("diviner")) {
            orangeDiviner.show(jComponent);
        }

        if (getType(jComponent).equals("text")) {
            orangeScribe.show(jComponent);
        }
    }

    boolean toogleView(JComponent jComponent, final KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_CONTROL:
                if (getType(jComponent).equals("diviner")) {
                    if (orangeDiviner.isShowing()) {
                        return false;
//                        orangeDiviner.collapse();  //TODO LO DEBE PROCESAR EL DIALOGO POR EL ASUNTO DE CANCELAR VIA CONTROLLER
                    } else {
                        orangeDiviner.show(jComponent);
                    }
                    return true;
                }

                if (getType(jComponent).equals("text")) {
                    if (orangeScribe.isShowing()) {
                        orangeScribe.collapse();
                    } else {
                        orangeScribe.show(jComponent);
                    }
                    return true;
                }
                return false;
            case KeyEvent.VK_TAB:
                if (jComponent.getTopLevelAncestor() == orangeDiviner) {
                    orangeDiviner.collapse();
                    if (KeyEvent.getKeyModifiersText(e.getModifiers()).equals("Shift")) {
                        testosFocus.previousFocus();
                    } else {
                        testosFocus.nextFocus();
                    }
                    return true;
                }
                return false;
        }
        return false;
    }

    private String getType(JComponent jComponent) {

        if (jComponent.getTopLevelAncestor() == orangeDiviner) {
            return "diviner";
        }

        String name = jComponent.getName() != null ? jComponent.getName() : "";

        if (name.startsWith("Editor")) {
            return "diviner";
        }
        if (name.startsWith("popero")) {
            return "text";
        }
        return name;
    }

    private void setOrangeDiviner(OrangeDiviner newOrangeDiviner) {
        this.orangeDiviner = newOrangeDiviner;
    }

    private void setOrangeText(OrangeScribe newOrangeText) {
        this.orangeScribe = newOrangeText;
    }
}
