package com.cn.danceland.myapplication.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 从手环拉取的计步对象  疲劳共用
 * Created by yxx on 2018/8/16.
 */
@Entity
public class WearFitStepBean {
    @Id(autoincrement = true)
    private Long id;

    private long timestamp;//时间戳
    private int step; // 步数 datas.get(10) * 256 * 256 + datas.get(11) * 256 + datas.get(12)+"";
    private int cal;// 卡路里 datas.get(13) * 256 * 256 +datas.get(14) * 256 + datas.get(15)+"";
    private int fatigue;// 疲劳

    @Generated(hash = 427901643)
    public WearFitStepBean() {
    }

    @Generated(hash = 942991365)
    public WearFitStepBean(Long id, long timestamp, int step, int cal, int fatigue) {
        this.id = id;
        this.timestamp = timestamp;
        this.step = step;
        this.cal = cal;
        this.fatigue = fatigue;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getCal() {
        return cal;
    }

    public void setCal(int cal) {
        this.cal = cal;
    }

    @Override
    public String toString() {
        return "WearFitSleepBean{" +
                " timestamp=" + timestamp +
                "，step=" + step +
                ", cal=" + cal +
                '}';
    }
}
