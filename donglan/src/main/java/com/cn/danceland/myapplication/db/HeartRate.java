package com.cn.danceland.myapplication.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by shy on 2018/5/23 18:44
 * Email:644563767@qq.com
 */

@Entity
public class HeartRate {

    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private Long date;//时间
    private int heartRate;//心率

    @Override
    public String toString() {
        return "HeartRate{" +
                "id=" + id +
                ", date=" + date +
                ", heartRate=" + heartRate +
                '}';
    }

    @Generated(hash = 611562310)

    public HeartRate(Long id, Long date, int heartRate) {
        this.id = id;
        this.date = date;
        this.heartRate = heartRate;
    }
    @Generated(hash = 1430820581)
    public HeartRate() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDate() {
        return this.date;
    }
    public void setDate(Long date) {
        this.date = date;
    }
    public int getHeartRate() {
        return this.heartRate;
    }
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

}
