package com.cn.danceland.myapplication.evntbus;

import java.util.List;

/**
 * Created by shy on 2017/11/10 10:05
 * Email:644563767@qq.com
 */


public class StringEvent {
    private String mMsg;
    private int mEventCode;
    private List<String> mMsgs;

    @Override
    public String toString() {
        return "StringEvent{" +
                "mMsg='" + mMsg + '\'' +
                ", mEventCode=" + mEventCode +
                ", mMsgs=" + mMsgs +
                '}';
    }

    public List<String> getmMsgs() {
        return mMsgs;
    }

    public void setmMsgs(List<String> mMsgs) {
        this.mMsgs = mMsgs;
    }

    public int getmEventCode() {
        return mEventCode;
    }

    public void setmEventCode(int mEventCode) {
        this.mEventCode = mEventCode;
    }

    public StringEvent(String msg, int eventCode) {
        this.mMsg = msg;
        this.mEventCode = eventCode;
    }
     public StringEvent(List<String> mMsgs, int eventCode) {
        this.mMsgs = mMsgs;
        this.mEventCode = eventCode;
    }

    public String getMsg() {
        return mMsg;
    }

    public int getEventCode() {
        return mEventCode;
    }
}
