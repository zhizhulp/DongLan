package com.cn.danceland.myapplication.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 从手环拉取的睡眠对象
 * Created by yxx on 2018/8/3.
 */
@Entity
public class WearFitSleepBean {
    @Id(autoincrement = true)
    private Long id;

    private String state; // 11位state  1 睡眠(包含深睡和浅睡) 2 深睡
    private long timestamp;//时间戳
    private int continuoustime;//12位 * 256+13位 睡了多久

    @Generated(hash = 1114343845)
    public WearFitSleepBean() {
    }

    @Generated(hash = 1560330387)
    public WearFitSleepBean(Long id, String state, long timestamp, int continuoustime) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
        this.continuoustime = continuoustime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getContinuoustime() {
        return continuoustime;
    }

    public void setContinuoustime(int continuoustime) {
        this.continuoustime = continuoustime;
    }

    @Override
    public String toString() {
        return "WearFitSleepBean{" +
                "state=" + state +
                ", timestamp=" + timestamp +
                ", continuoustime=" + continuoustime +
                '}';
    }
}
