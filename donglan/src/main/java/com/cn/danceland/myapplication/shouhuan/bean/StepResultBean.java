package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;

/**
 * 请求后台计步对象  提交  获取
 * Created by ${yxx} on 2018/7/26.
 */
public class StepResultBean implements Serializable {
    private long timestamp;
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String step;
    private String cal;
    private String deep;
    private String awakeTimes;
    private String oxValue;
    private String bpMin;
    private String bpMax;
    private String light;
    private String hrValue;
    private String fatigue;//疲劳
    private int id;
    private int person_id;

    public String getDeep() {
        return deep;
    }

    public void setDeep(String deep) {
        this.deep = deep;
    }

    public String getAwakeTimes() {
        return awakeTimes;
    }

    public void setAwakeTimes(String awakeTimes) {
        this.awakeTimes = awakeTimes;
    }

    public String getOxValue() {
        return oxValue;
    }

    public void setOxValue(String oxValue) {
        this.oxValue = oxValue;
    }

    public String getBpMin() {
        return bpMin;
    }

    public void setBpMin(String bpMin) {
        this.bpMin = bpMin;
    }

    public String getBpMax() {
        return bpMax;
    }

    public void setBpMax(String bpMax) {
        this.bpMax = bpMax;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getHrValue() {
        return hrValue;
    }

    public void setHrValue(String hrValue) {
        this.hrValue = hrValue;
    }

    public String getFatigue() {
        return fatigue;
    }

    public void setFatigue(String fatigue) {
        this.fatigue = fatigue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
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

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SleepResultBean{" +
                ", timestamp=" + timestamp +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                "，step=" + step +
                "，cal=" + cal +
                "，fatigue=" + fatigue +
                '}';
    }
}
