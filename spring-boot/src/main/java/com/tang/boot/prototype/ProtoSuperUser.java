package com.tang.boot.prototype;

public class ProtoSuperUser {
    private ThreadLocal<Integer> value = ThreadLocal.withInitial(()->21);

    public void setValue(int value) {
        this.value.set(value);
    }

    public int getValue(){
        return this.value.get();
    }
}
