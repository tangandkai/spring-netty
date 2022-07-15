package com.tang.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayListTest {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello,word");
        System.out.println(list);
//        list.add("jack");
        Object[] array = list.toArray();
        array[0] = "jack";

        Iterator<String> iterator = list.iterator();

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(2,4,6,8));
        Iterator<Integer> iterator1 = arrayList.iterator();
        while (iterator1.hasNext()){
            System.out.println(iterator1.next());
            iterator1.remove();
        }
        System.out.println(arrayList);
    }
}
