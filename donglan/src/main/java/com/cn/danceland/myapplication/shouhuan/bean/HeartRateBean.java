package com.cn.danceland.myapplication.shouhuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 请求后台心率对象
 * Created by ${yxx} on 2018/7/26.
 */

public class HeartRateBean implements Serializable {
    private List<HeartRateResultBean> data;
    private boolean success;

    public void setData(List<HeartRateResultBean> data) {
        this.data = data;
    }

    public List<HeartRateResultBean> getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

//    public class Data implements Serializable {
//        private Integer id;
//        private Integer person_id;
//        private Integer timestamp;
//        private String day;
//        private String hour;
//        private String identifier;
//        private String max_value;//心率 只取这个
//        private String min_value;
//        private String minute;
//        private String month;
//        private String year;
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public Integer getPerson_id() {
//            return person_id;
//        }
//
//        public void setPerson_id(Integer person_id) {
//            this.person_id = person_id;
//        }
//
//        public Integer getTimestamp() {
//            return timestamp;
//        }
//
//        public void setTimestamp(Integer timestamp) {
//            this.timestamp = timestamp;
//        }
//
//        public String getDay() {
//            return day;
//        }
//
//        public void setDay(String day) {
//            this.day = day;
//        }
//
//        public String getHour() {
//            return hour;
//        }
//
//        public void setHour(String hour) {
//            this.hour = hour;
//        }
//
//        public String getIdentifier() {
//            return identifier;
//        }
//
//        public void setIdentifier(String identifier) {
//            this.identifier = identifier;
//        }
//
//        public String getMax_value() {
//            return max_value;
//        }
//
//        public void setMax_value(String max_value) {
//            this.max_value = max_value;
//        }
//
//        public String getMin_value() {
//            return min_value;
//        }
//
//        public void setMin_value(String min_value) {
//            this.min_value = min_value;
//        }
//
//        public String getMinute() {
//            return minute;
//        }
//
//        public void setMinute(String minute) {
//            this.minute = minute;
//        }
//
//        public String getMonth() {
//            return month;
//        }
//
//        public void setMonth(String month) {
//            this.month = month;
//        }
//
//        public String getYear() {
//            return year;
//        }
//
//        public void setYear(String year) {
//            this.year = year;
//        }
//    }

}
