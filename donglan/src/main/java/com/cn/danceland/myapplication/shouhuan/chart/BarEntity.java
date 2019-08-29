package com.cn.danceland.myapplication.shouhuan.chart;

/**
 * Created by yxx on 2018/8/8.
 */
public class BarEntity {
    public String title = "";
    private float positivePer;
    public String negativeColor  = "#ff6600";//深睡
    private float neutralPer ;
    public String neutralColor = "#0068b7";//浅睡
    private float negativePer;
    public String positiveColor = "#7ecef4";//清醒
    private float Allcount;
    private float scale;
    /*填充区域比例*/
    private float fillScale;

    @Override
    public String toString() {
        return "BarEntity{" +
                "title='" + title + '\'' +
                ", positivePer=" + positivePer +
                ", positiveColor='" + positiveColor + '\'' +
                ", neutralPer=" + neutralPer +
                ", neutralColor='" + neutralColor + '\'' +
                ", negativePer=" + negativePer +
                ", negativeColor='" + negativeColor + '\'' +
                ", Allcount=" + Allcount +
                ", scale=" + scale +
                ", fillScale=" + fillScale +
                '}';
    }

    public float getFillScale() {
        return fillScale;
    }

    public void setFillScale(float fillScale) {
        this.fillScale = fillScale;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPositivePer() {
        return positivePer;
    }

    public void setPositivePer(float positivePer) {
        this.positivePer = positivePer;
    }

    public String getPositiveColor() {
        return positiveColor;
    }

    public void setPositiveColor(String positiveColor) {
        this.positiveColor = positiveColor;
    }

    public float getNeutralPer() {
        return neutralPer;
    }

    public void setNeutralPer(float neutralPer) {
        this.neutralPer = neutralPer;
    }

    public String getNeutralColor() {
        return neutralColor;
    }

    public void setNeutralColor(String neutralColor) {
        this.neutralColor = neutralColor;
    }

    public float getNegativePer() {
        return negativePer;
    }

    public void setNegativePer(float negativePer) {
        this.negativePer = negativePer;
    }

    public String getNegativeColor() {
        return negativeColor;
    }

    public void setNegativeColor(String negativeColor) {
        this.negativeColor = negativeColor;
    }

    public float getAllcount() {
        return Allcount;
    }

    public void setAllcount(float allcount) {
        Allcount = allcount;
    }
}
