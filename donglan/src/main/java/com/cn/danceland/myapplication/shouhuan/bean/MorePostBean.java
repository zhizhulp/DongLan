package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;

/**
 * 请求后台数据对象  提交  周数据
 * 1.根据条件查询心率每日平均数
 * 2.查询指定时间段内，每天睡眠时间的总和
 * 3.查询计步周月
 * Created by ${yxx} on 2018/7/30.
 */
public class MorePostBean implements Serializable {
    private String timestamp_gt;
    private String timestamp_lt;

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
                ", timestamp_gt=" + timestamp_gt +
                ", timestamp_lt=" + timestamp_lt +
                '}';
    }
}
