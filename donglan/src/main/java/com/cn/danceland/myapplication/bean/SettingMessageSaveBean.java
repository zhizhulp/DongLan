package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by yxx on 2018-09-13.
 */

public class SettingMessageSaveBean {

    private boolean success;
    private String errorMsg;
    private int code;

    @Override
    public String toString() {
        return "SettingMessageBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code=" + code +
                '}';
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
