package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/11/21 19:00
 * Email:644563767@qq.com
 */

public class CalendarPointBean {

    private boolean success;
    private String errorMsg;
    private int code;
    private List<Data> data;
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }
    public class Data {

        private long date;
        private boolean course;
        private boolean groupCourse;
        private boolean freeGroupCourse;
        public void setDate(long date) {
            this.date = date;
        }
        public long getDate() {
            return date;
        }

        public void setCourse(boolean course) {
            this.course = course;
        }
        public boolean getCourse() {
            return course;
        }

        public void setGroupCourse(boolean groupCourse) {
            this.groupCourse = groupCourse;
        }
        public boolean getGroupCourse() {
            return groupCourse;
        }

        public void setFreeGroupCourse(boolean freeGroupCourse) {
            this.freeGroupCourse = freeGroupCourse;
        }
        public boolean getFreeGroupCourse() {
            return freeGroupCourse;
        }

    }
}
