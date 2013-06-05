package org.starkeylab.dre.ruleengine;

import javax.rules.ObjectFilter;

public class ObjectFilterImpl implements ObjectFilter {

    ObjectFilterImpl() { }


    public Object filter(Object obj) {
      return obj;
    }


    public void reset() {
    }
}
