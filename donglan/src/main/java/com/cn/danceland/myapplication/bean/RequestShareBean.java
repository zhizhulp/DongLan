package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2018/12/27 11:36
 * Email:644563767@qq.com
 */

public class RequestShareBean {

    private boolean success;
    private String errorMsg;
    private int code;
    private Data data;

    @Override
    public String toString() {
        return "RequestShareBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
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

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    public class Data extends ShareBean{


    }
}