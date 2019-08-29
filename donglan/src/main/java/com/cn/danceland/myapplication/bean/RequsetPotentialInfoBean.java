package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by shy on 2018/1/12 15:37
 * Email:644563767@qq.com
 */

public class RequsetPotentialInfoBean {

    private boolean success;
    private String errorMsg;
    private Data data;

    @Override
    public String toString() {
        return "RequsetPotentialInfoBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
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

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data extends PotentialInfo implements Serializable {

    }
}