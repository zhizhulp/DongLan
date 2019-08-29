package com.cn.danceland.myapplication.shouhuan.bean;

/**
 * 入睡时间  久坐   勿扰
 * Created by ${yxx} on 2018/8/16.
 */

public class LongSit {
    private int on;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    @Override
    public String toString() {
        return "LongSit{" +
                "，on=" + on +
                "，startHour=" + startHour +
                "，startMinute=" + startMinute +
                ", endHour=" + endHour +
                ", endMinute=" + endMinute +
                '}';
    }
}
