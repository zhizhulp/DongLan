package com.cn.danceland.myapplication.shouhuan.bean;

/**
 * 手环计步对象
 * Created by yxx on 22018/8/3.
 */
public class StepBean {
    private int state = 1;//状态 统一一种
    private int continuoustime = 50;//持续时间 图表item的宽度
    private long startTime;//开始时间
    private long endTime;//结束时间
    private int step; // 步数 datas.get(10) * 256 * 256 + datas.get(11) * 256 + datas.get(12)+"";
    private int cal;// 卡路里 datas.get(13) * 256 * 256 +datas.get(14) * 256 + datas.get(15)+"";

    public StepBean() {
    }

    public StepBean(int state, int continuoustime, long startTime, long endTime) {
        this.state = state;
        this.continuoustime = continuoustime;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getContinuoustime() {
        return continuoustime;
    }

    public void setContinuoustime(int continuoustime) {
        this.continuoustime = continuoustime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SleepBean{" +
                "state=" + state +
                ", continuoustime=" + continuoustime +
                ",startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
