package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by shy on 2018/3/20 10:20
 * Email:644563767@qq.com
 */


    public class RequestCollectBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private int code;
    private long data;

    @Override
    public String toString() {
        return "RequestLoginInfoBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
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

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
