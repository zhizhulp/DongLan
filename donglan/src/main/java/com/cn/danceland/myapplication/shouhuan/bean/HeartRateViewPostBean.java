package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;

/**
 * 请求后台心率合格率对象  提交
 * 根据条件查询心率合格率
 * Created by ${yxx} on 2018/7/30.
 */
public class HeartRateViewPostBean implements Serializable {
    private String max_value_gt;//默认51
    private String max_value_lt;//默认129
    private String timestamp_gt;
    private String timestamp_lt;

    public String getMax_value_gt() {
        return max_value_gt;
    }

    public void setMax_value_gt(String max_value_gt) {
        this.max_value_gt = max_value_gt;
    }

    public String getMax_value_lt() {
        return max_value_lt;
    }

    public void setMax_value_lt(String max_value_lt) {
        this.max_value_lt = max_value_lt;
    }

    public String getTimestamp_gt() {
        return timestamp_gt;
    }

    public void setTimestamp_gt(String timestamp_gt) {
        this.timestamp_gt = timestamp_gt;
    }

    public String getTimestamp_lt() {
        return timestamp_lt;
    }

    public void setTimestamp_lt(String timestamp_lt) {
        this.timestamp_lt = timestamp_lt;
    }

    @Override
    public String toString() {
        return "HeartRateWeekPostBean{" +
                ", max_value_gt=" + max_value_gt +
                ", max_value_lt=" + max_value_lt +
                ", timestamp_gt=" + timestamp_gt +
                ", timestamp_lt=" + timestamp_lt +
                '}';
    }
}
