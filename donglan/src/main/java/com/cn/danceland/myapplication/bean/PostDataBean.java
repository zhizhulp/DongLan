package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * 请求后台最后一条心率对象
 * Created by ${yxx} on 2018/7/26.
 */
public class PostDataBean implements Serializable {
    private boolean success;
    private String errorMsg;
    private String code;

    public boolean isSuccess() {
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

    @Override
    public String toString() {
        return "ConsultDataBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg +
                ", code=" + code +
                '}';
    }

}
