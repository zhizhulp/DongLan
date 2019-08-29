package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2018/4/13 15:03
 * Email:644563767@qq.com
 */

public class RequestPingFenBean {

    private boolean success;
    private String errorMsg;
    private String code;
    private float data;
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setData(float data) {
        this.data = data;
    }
    public float getData() {
        return data;
    }

}