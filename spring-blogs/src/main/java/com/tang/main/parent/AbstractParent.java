package com.tang.main.parent;

public abstract class AbstractParent {

    protected String key;

    public void t(){
        key = "AbstractParent";
    }

    public void t1(){
        String k = key;
    }
}
