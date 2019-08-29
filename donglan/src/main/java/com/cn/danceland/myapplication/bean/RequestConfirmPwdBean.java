package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

public class RequestConfirmPwdBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private int code;

    @Override
    public String toString() {
        return "RequestLoginInfoBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
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

}
