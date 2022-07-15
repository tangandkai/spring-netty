package com.tang.main.time;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;

public class TimeWeek {

    public int getWeek(String date){
        String[] split = date.split("-");
        if (split.length!=3){
            throw new RuntimeException("传入的date格式不对，需要按照yyyy-m-d");
        }
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0, 0);
        return dateTime.get(weekFields.weekOfYear());
    }

    public static void main(String[] args) {
        int week = new TimeWeek().getWeek("2020-12-31");
        System.out.println(week);
    }
}
