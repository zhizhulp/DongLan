package com.cn.danceland.myapplication.evntbus;

/**
 * Created by shy on 2017/11/10 10:05
 * Email:644563767@qq.com
 */


public class ObjectEvent {
    private Object mMsg;
    private int mEventCode;

    public ObjectEvent(String msg, int eventCode) {
        this.mMsg = msg;
        this.mEventCode = eventCode;
    }

    public Object getMsg() {
        return mMsg;
    }

    public int getEventCode() {
        return mEventCode;
    }
}
