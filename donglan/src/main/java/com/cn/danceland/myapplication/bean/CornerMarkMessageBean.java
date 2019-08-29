package com.cn.danceland.myapplication.bean;

/**
 * 获取推送角标数 应用消息总数 用于桌面icon显示
 * Created by yxx on 2018-10-15.
 */

public class CornerMarkMessageBean {
    private boolean success;
    private String code;
    private String errorMsg;
    private Long data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CornerMarkMessageBean{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
