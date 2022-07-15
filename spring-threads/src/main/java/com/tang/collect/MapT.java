package com.tang.collect;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class MapT {

    public static void main(String[] args) {
        int[] arr = new int[]{9,8,7,6,5,4};
        int[] ints = quickSort(arr, 0, 6);
        for (int v:ints){
//            System.out.println(v);
        }
        int[] arr1 = new int[]{5,4,6,3};
        sort(arr1,0,3);
        for (int n :arr1){
            System.out.println(n);
        }
    }

    private static int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    private static int partition(int[] arr, int left, int right) {
        // 设定基准值（pivot）
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i < right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pivot, index - 1);
        return index - 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void sort(int[] nums,int start,int end){
        if (start>end){
            return;
        }
        int left = start;
        int right = end;
        int middle = nums[left];
        int tmp;
        while (left<right){
            while (left<right && nums[right]>=middle){
                right--;
            }
            while (left<right && nums[left]<=middle){
                left++;
            }
            tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
        }
        nums[start] = nums[left];
        nums[left] = middle;
        sort(nums,start,left-1);
        sort(nums,left+1,end);

    }
}
