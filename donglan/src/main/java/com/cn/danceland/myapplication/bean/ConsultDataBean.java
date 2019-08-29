package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 请求后台最后一条心率对象
 * Created by ${yxx} on 2018/7/26.
 */
public class ConsultDataBean implements Serializable {
    private boolean success;
    private String errorMsg;
    private List<ConsultBean> data;

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

    public List<ConsultBean> getData() {
        return data;
    }

    public void setData(List<ConsultBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ConsultDataBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

}
