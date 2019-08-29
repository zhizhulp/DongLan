package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;

/**
 * 请求后台数据对象  提交  周数据
 * 1.根据条件查询心率每日平均数
 * 2.查询指定时间段内，每天睡眠时间的总和
 * Created by ${yxx} on 2018/7/30.
 */
public class SleepMorePostBean implements Serializable {
    private String group_time_gt;
    private String group_time_lt ;

    public String getGroup_time_gt() {
        return group_time_gt;
    }

    public void setGroup_time_gt(String group_time_gt) {
        this.group_time_gt = group_time_gt;
    }

    public String getGroup_time_lt() {
        return group_time_lt;
    }

    public void setGroup_time_lt(String group_time_lt) {
        this.group_time_lt = group_time_lt;
    }

    @Override
    public String toString() {
        return "HeartRateWeekPostBean{" +
                ", group_time_gt=" + group_time_gt +
                ", group_time_lt=" + group_time_lt  +
                '}';
    }
}
