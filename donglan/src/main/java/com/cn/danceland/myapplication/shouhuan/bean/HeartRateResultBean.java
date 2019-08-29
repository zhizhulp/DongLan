package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 请求后台心率对象  提交  获取
 * Created by ${yxx} on 2018/7/26.
 */
public class HeartRateResultBean implements Serializable {
    private String day;
    private String hour;
    private Integer id;
    private String max_value;//心率 只取这个
    private String min_value;
    private String minute;
    private String month;
    private long timestamp;
    private String year;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMax_value() {
        return max_value;
    }

    public void setMax_value(String max_value) {
        this.max_value = max_value;
    }

    public String getMin_value() {
        return min_value;
    }

    public void setMin_value(String min_value) {
        this.min_value = min_value;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    @Override
    public String toString() {
        return "HeartRateResultBean{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", max_value=" + max_value +
                ", min_value=" + min_value +
                '}';
    }
}
