/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.croer.javaorange;

import com.croer.javaorange.diviner.SimpleOrangeDivinerPanel;
import com.croer.javaorange.diviner.SimpleOrangeDiviner;
import com.croer.javaorange.definition.OrangeDiviner;
import com.croer.javaorange.definition.OrangeFactory;
import com.croer.javaorange.definition.OrangeScribe;

/**
 *
 * @author elialva
 */
class SimpleOrangeFactory implements OrangeFactory {

    public SimpleOrangeFactory() {
    }

    public OrangeDiviner newOrangeDiviner() {
        return new SimpleOrangeDiviner(null, new SimpleOrangeDivinerPanel());
    }

    public OrangeScribe newOrangeScribe() {
        return new SimpleOrangeScribe(null, new SimpleOrangeScribePanel());
    }
}
