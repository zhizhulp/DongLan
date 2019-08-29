package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;

/**
 * 请求后台睡眠对象  提交  获取
 * Created by ${yxx} on 2018/7/26.
 */
public class SleepResultBean implements Serializable {
    private String state;
    private long timestamp;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String minutes;//睡了多久
    private String group_time;//属于哪天的觉
    private String minutes1;//浅睡
    private String minutes2;//深睡
    //    private Integer id;
    //    private Integer person_id;
        private String state_one_count;

    public String getState_one_count() {
        return state_one_count;
    }

    public void setState_one_count(String state_one_count) {
        this.state_one_count = state_one_count;
    }

    public String getMinutes1() {
        return minutes1;
    }

    public void setMinutes1(String minutes1) {
        this.minutes1 = minutes1;
    }

    public String getMinutes2() {
        return minutes2;
    }

    public void setMinutes2(String minutes2) {
        this.minutes2 = minutes2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getGroup_time() {
        return group_time;
    }

    public void setGroup_time(String group_time) {
        this.group_time = group_time;
    }

    @Override
    public String toString() {
        return "SleepResultBean{" +
//                "id=" + id +
//                "，person_id=" + person_id +
                "，group_time=" + group_time +
                "，state=" + state +
//                "，state_one_count=" + state_one_count +
                ", timestamp=" + timestamp +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                '}';
    }
}
