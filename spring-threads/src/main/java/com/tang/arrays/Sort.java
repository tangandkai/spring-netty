package com.tang.arrays;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Sort {

    public static void main(String[] args) {
        List<SortDTO> list = new ArrayList<>();
        list.add(new SortDTO("300"));
        list.add(new SortDTO("200"));
        list.add(new SortDTO("500"));
        list.add(new SortDTO("100"));
        // 我们先把数组的大小初始化成 list 的大小，保证能够正确执行 toArray
        SortDTO[] array = new SortDTO[list.size()];
        list.toArray(array);

        System.out.println("排序之前："+ JSON.toJSONString(array));
        Arrays.sort(array, Comparator.comparing(SortDTO::getSortTarget));
        System.out.println("排序之后："+ JSON.toJSONString(array));

        boolean b = binarySearch(new int[]{2, 4, 6, 7, 8, 9, 10}, 10, 0, 6);
        System.out.println(b);
    }

    public static boolean binarySearch(int[] arr,int value,int start,int end){
        int index = (start+end)/2;
        if (value==arr[index]){
            return true;
        }
        if (start>=end){
            return false;
        }
        if (value>arr[index]){
            start = index>start?index:start+1;
        }else {
            end = end>index?index:end-1;
        }
        return binarySearch(arr,value,start,end);
    }
}
