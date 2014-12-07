/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.croer.javaorange.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author elialva
 */
public class Configuration   {

    private static CompositeConfiguration CONFIGURATION;


    public static CompositeConfiguration getCONFIGURATION() {
        if (CONFIGURATION == null) {
            List lc = new ArrayList();
            try {
                PropertiesConfiguration pc1 = new PropertiesConfiguration("prueba.properties");
//                PropertiesConfiguration pc2 = new PropertiesConfiguration("mvc.properties");
                lc.add(pc1);
//                lc.add(pc2);
            } catch (ConfigurationException ex) {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            }
            CONFIGURATION = new CompositeConfiguration(lc);
        }
        return CONFIGURATION;
    }
}
