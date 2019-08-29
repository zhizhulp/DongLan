package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/5/15.
 */

public class CourseEvaluateBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private String code;
    private Data data;
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

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public class Data {

        private String course_type_score;
        private String room_score;
        private String employee_score;
        public void setCourse_type_score(String course_type_score) {
            this.course_type_score = course_type_score;
        }
        public String getCourse_type_score() {
            return course_type_score;
        }

        public void setRoom_score(String room_score) {
            this.room_score = room_score;
        }
        public String getRoom_score() {
            return room_score;
        }

        public void setEmployee_score(String employee_score) {
            this.employee_score = employee_score;
        }
        public String getEmployee_score() {
            return employee_score;
        }

    }

}
