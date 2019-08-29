package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2017/12/26 17:57
 * Email:644563767@qq.com
 */



public class    RequestSimpleBean {

    private boolean success;
    private String errorMsg;
    private  String code;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public void setData(String data) {
        this.data = data;
    }
    public String getData() {
        return data;
    }

}