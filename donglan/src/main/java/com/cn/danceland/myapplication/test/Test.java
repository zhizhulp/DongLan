package com.cn.danceland.myapplication.test;

import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(new Date(1553249707000L));
        Calendar overTime = Calendar.getInstance();
        currentTime.setTime(new Date(1553249707002L));
        System.out.print(currentTime.compareTo(overTime));
    }
}
