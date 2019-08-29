package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 请求后台最后一条心率对象
 * Created by ${yxx} on 2018/7/26.
 */

public class HeartRateLastBean implements Serializable {
    private HeartRateResultBean data;
    private boolean success;

    public HeartRateResultBean getData() {
        return data;
    }

    public void setData(HeartRateResultBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
