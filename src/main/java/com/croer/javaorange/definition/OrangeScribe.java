/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.croer.javaorange.definition;

import javax.swing.JComponent;

/**
 *
 * @author elialva
 */
public interface OrangeScribe {

    public boolean isShowing();

    public void show(JComponent jComponent);

    public void collapse();

}
