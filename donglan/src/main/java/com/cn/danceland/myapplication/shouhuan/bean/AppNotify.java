package com.cn.danceland.myapplication.shouhuan.bean;

/**
 * APP提醒
 * Created by ${yxx} on 2018/8/16.
 */

public class AppNotify {
    private int notifyid;
    private int isOn;

    public int getNotifyid() {
        return notifyid;
    }

    public void setNotifyid(int notifyid) {
        this.notifyid = notifyid;
    }

    public int getIsOn() {
        return isOn;
    }

    public void setIsOn(int isOn) {
        this.isOn = isOn;
    }

    @Override
    public String toString() {
        return "AppNotify{" +
                "，notifyid=" + notifyid +
                "，isOn=" + isOn +
                '}';
    }
}
