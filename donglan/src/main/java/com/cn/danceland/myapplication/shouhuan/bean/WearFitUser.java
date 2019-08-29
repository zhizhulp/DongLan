package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;

/**
 * Created by ${yxx} on 2018/8/14.
 */
public class WearFitUser implements Serializable {
    private int stepLength;//步长
    private int age;//年龄
    private int height;//身高
    private int weight;//体重
    private int bpMax;//血压最大
    private int bpMin;//血压最小
    private int distanceUnit;//距离单位 0英里 1厘米
    private int gold_steps;//目标步数
    private int sex;//性别 1男  2女
    private int wear;//佩戴 1左手   2右手
    private int upHand;//抬手亮屏 0关  1开
    private int antiLostAlert;//防丢开关 0关  1开
    private int callsAlerts;//来电提醒 0关  1开
    private int SMSAlerts;//短信提醒 0关  1开
    private int heightUnit;//身高单位 0英里 1厘米
    private int sleepHour;//入睡小时
    private int sleepMinutes;//入睡分钟
    private int WakeUpHour;//醒来小时
    private int WakeUpMinutes;//醒来分钟

    public int getSleepHour() {
        return sleepHour;
    }

    public void setSleepHour(int sleepHour) {
        this.sleepHour = sleepHour;
    }

    public int getSleepMinutes() {
        return sleepMinutes;
    }

    public void setSleepMinutes(int sleepMinutes) {
        this.sleepMinutes = sleepMinutes;
    }

    public int getWakeUpHour() {
        return WakeUpHour;
    }

    public void setWakeUpHour(int wakeUpHour) {
        WakeUpHour = wakeUpHour;
    }

    public int getWakeUpMinutes() {
        return WakeUpMinutes;
    }

    public void setWakeUpMinutes(int wakeUpMinutes) {
        WakeUpMinutes = wakeUpMinutes;
    }

    public int getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(int heightUnit) {
        this.heightUnit = heightUnit;
    }

    public int getUpHand() {
        return upHand;
    }

    public void setUpHand(int upHand) {
        this.upHand = upHand;
    }

    public int getAntiLostAlert() {
        return antiLostAlert;
    }

    public void setAntiLostAlert(int antiLostAlert) {
        this.antiLostAlert = antiLostAlert;
    }

    public int getCallsAlerts() {
        return callsAlerts;
    }

    public void setCallsAlerts(int callsAlerts) {
        this.callsAlerts = callsAlerts;
    }

    public int getSMSAlerts() {
        return SMSAlerts;
    }

    public void setSMSAlerts(int SMSAlerts) {
        this.SMSAlerts = SMSAlerts;
    }

    public int getStepLength() {
        return stepLength;
    }

    public void setStepLength(int stepLength) {
        this.stepLength = stepLength;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBpMax() {
        return bpMax;
    }

    public void setBpMax(int bpMax) {
        this.bpMax = bpMax;
    }

    public int getBpMin() {
        return bpMin;
    }

    public void setBpMin(int bpMin) {
        this.bpMin = bpMin;
    }

    public int getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(int distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public int getGold_steps() {
        return gold_steps;
    }

    public void setGold_steps(int gold_steps) {
        this.gold_steps = gold_steps;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getWear() {
        return wear;
    }

    public void setWear(int wear) {
        this.wear = wear;
    }

    @Override
    public String toString() {
        return "WearFitUser{" +
                "，stepLength=" + stepLength +
                "，age=" + age +
                "，height=" + height +
                ", weight=" + weight +
                ", bpMax=" + bpMax +
                ", bpMin=" + bpMin +
                ", distanceUnit=" + distanceUnit +
                ", gold_steps=" + gold_steps +
                ", sex=" + sex +
                ", wear=" + wear +
                '}';
    }
}
