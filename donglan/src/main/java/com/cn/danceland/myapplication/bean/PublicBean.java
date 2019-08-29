package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * 公共对象
 * Created by yxx on 2018-12-07.
 */

public class PublicBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private String code;

    @Override
    public String toString() {
        return "PublicBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

