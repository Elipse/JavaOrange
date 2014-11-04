package com.croer.javaorange;

import com.croer.javaorange.definition.Mediator;
import com.croer.javaorange.definition.OrangeScribe;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author elialva
 */
public class SimpleOrangeScribe extends JPopupMenu implements OrangeScribe {

    private final Mediator testosMediator;

    public SimpleOrangeScribe(Mediator testosMediator, JPanel jPanel) {
        this.testosMediator = testosMediator;
        add(jPanel);
        setFocusable(false);
    }

    public void show(JComponent jComponent) {
        super.show(jComponent, 1, 1 + jComponent.getHeight());
    }

    public void collapse() {
        setVisible(false);
    }

}
