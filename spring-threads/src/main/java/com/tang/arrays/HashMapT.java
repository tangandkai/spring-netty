package com.tang.arrays;

import java.util.HashMap;
import java.util.Map;

public class HashMapT {

    public static void main(String[] args) {
        Map<Object, Integer> hashMap = new HashMap<>();
        for (int i=0;i<10;i++){
            Param param = new Param();
            hashMap.put(param,9);
        }
        System.out.println(hashMap.size());
        System.out.println(hashMap);
    }


}

class Param{

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}

