package com.cn.danceland.myapplication.shouhuan.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by yxx on 2018/8/8.
 */
public class SourceEntity {
    public static class Source {
        private int allCount;
        private int awakeCount;//清醒
        private int shallowCount;//浅睡
        private int deepCount;//深睡
        private String source;
        private int scale;
        private long time;
        private long dayAwake;//每天清醒
        private int cal;//卡路里

        public int getCal() {
            return cal;
        }

        public void setCal(int cal) {
            this.cal = cal;
        }

        public long getDayAwake() {
            return dayAwake;
        }

        public void setDayAwake(long dayAwake) {
            this.dayAwake = dayAwake;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public float getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }

        public int getAwakeCount() {
            return awakeCount;
        }

        public void setAwakeCount(int awakeCount) {
            this.awakeCount = awakeCount;
        }

        public int getShallowCount() {
            return shallowCount;
        }

        public void setShallowCount(int shallowCount) {
            this.shallowCount = shallowCount;
        }

        public int getDeepCount() {
            return deepCount;
        }

        public void setDeepCount(int deepCount) {
            this.deepCount = deepCount;
        }

        public int getScale() {
            return scale;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    private List<Source> list;

    //测试数据
    public List<Source> parseData() {
        list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i <= 6; i++) {
            Source source = new Source();
            source.setAwakeCount(100);
            source.setShallowCount(200);//浅睡
            source.setDeepCount(300);//深睡
            source.setScale(r.nextInt(210));
            source.setSource("星期" + (i + 1));
            source.setAllCount(source.getAwakeCount() + source.getShallowCount() + source.getDeepCount());
            list.add(source);
        }
        return list;
    }

    public List<Source> getList() {
        return list;
    }

    public void setList(List<Source> list) {
        this.list = list;
    }
}
