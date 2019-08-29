package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2018/1/8.
 */

public class BodyAgeBean {

    private boolean success;
    private String errorMsg;
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

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public class Data {

        private String date;
        private String bodyage;
        private String faceAge;
        public void setDate(String date) {
            this.date = date;
        }
        public String getDate() {
            return date;
        }

        public void setBodyage(String bodyage) {
            this.bodyage = bodyage;
        }
        public String getBodyage() {
            return bodyage;
        }

        public void setFaceAge(String faceAge) {
            this.faceAge = faceAge;
        }
        public String getFaceAge() {
            return faceAge;
        }

    }


}
