package com.tang.main.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListIterator {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(10);
        for (int i=0;i<10;i++){
            list.add(i);
        }
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            list.add(1);
        }
    }
}
