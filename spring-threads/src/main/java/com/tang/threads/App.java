package com.tang.threads;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String s = "hello";
        s.equals("");
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes){
            System.out.println();
        }
        Long.valueOf(2);

    }

    public int cal(int n){
        if (n<0){
            throw new RuntimeException("n 小于0 , 请检查....");
        }
        if (n<=1){
            return n;
        }
        
        return cal(n - 1)+cal(n - 2);
    }

    public int cal_2(int n){
        if (n<0){
            throw new RuntimeException("n 小于0 , 请检查....");
        }
        if (n<=1){
            return n;
        }
        int first,second = 0,thrid = 1;
        for (int i=2;i<=n;i++){
            first = second;
            second = thrid;
            thrid = first+second;
        }
        return thrid;
    }
}

class jj{
    private int s;
    public int getS(){
        return s;
    }
}