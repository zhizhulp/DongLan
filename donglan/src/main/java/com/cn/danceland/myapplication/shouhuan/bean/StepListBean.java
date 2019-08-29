package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 请求后台心率对象
 * Created by ${yxx} on 2018/7/26.
 */

public class StepListBean implements Serializable {
    private List<StepResultBean> data;
    private boolean success;

    public List<StepResultBean> getData() {
        return data;
    }

    public void setData(List<StepResultBean> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

}
