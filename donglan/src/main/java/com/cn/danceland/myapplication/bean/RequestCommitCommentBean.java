package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2018/4/25 17:02
 * Email:644563767@qq.com
 */


public class RequestCommitCommentBean {
    private boolean success;
    private String errorMsg;
    private int code;
    private RequstCommentInfoBean.Content data;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RequstCommentInfoBean.Content getData() {
        return data;
    }

    public void setData(RequstCommentInfoBean.Content data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestCommitCommentBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }


}
