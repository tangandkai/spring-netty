package com.tang.main.parent;

public class ChildOne extends AbstractParent{

    @Override
    public void t() {
        key = "ChildOne";
    }

    @Override
    public void t1() {
        super.t1();
    }
}
