package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;

/**
 * 请求后台心率对象  提交
 * 按条件查询不分页[人员心率数据]列表接口参数
 * Created by ${yxx} on 2018/7/26.
 */
public class HeartRatePostBean implements Serializable {
    private String day;
    private String month;
    private String year;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "heartRatePostBean{" +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
