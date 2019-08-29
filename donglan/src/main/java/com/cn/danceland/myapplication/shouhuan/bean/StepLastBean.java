package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;

/**
 * 请求后台最后一条心率对象
 * Created by ${yxx} on 2018/7/26.
 */
public class StepLastBean implements Serializable {
    private StepResultBean data;
    private boolean success;

    public StepResultBean getData() {
        return data;
    }

    public void setData(StepResultBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
