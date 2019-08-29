package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 请求后台心率对象
 * Created by ${yxx} on 2018/7/26.
 */

public class HeartRateViewBean implements Serializable {
    private Data data;
    private boolean success;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class Data implements Serializable {
        private Integer sumcount;
        private Integer count;

        public Integer getSumcount() {
            return sumcount;
        }

        public void setSumcount(Integer sumcount) {
            this.sumcount = sumcount;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}
