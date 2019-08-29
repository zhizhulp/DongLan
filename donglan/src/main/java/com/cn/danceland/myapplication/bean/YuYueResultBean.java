package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/4/17.
 */

public class YuYueResultBean implements Serializable {

    private int code;
    private int data;
    private String errorMsg;
    private boolean success;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(int data) {
        this.data = data;
    }
    public int getData() {
        return data;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

}
