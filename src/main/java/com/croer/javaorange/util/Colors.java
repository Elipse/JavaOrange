/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.croer.javaorange.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author elialva
 */
public class Colors {

    static Map<String, Color> colorMap = new HashMap();
    
    static {
//        colorMap.put("Citrine", new Color(r, g, b));
    }

    public static Color getColorByName(String name) {
        return colorMap.get(name);
    }

}
