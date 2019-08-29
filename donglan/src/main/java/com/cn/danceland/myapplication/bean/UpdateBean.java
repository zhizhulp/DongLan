package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/5/12.
 */

public class UpdateBean implements Serializable {

    private String code;
    private Data data;
    private String errorMsg;
    private boolean success;
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

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }


    public class Data {

        private String current_version;
        private String date;
        private String description;
        private String id;
        private String min_version;
        private String platform;
        private String status;
        private String url;
        public void setCurrent_version(String current_version) {
            this.current_version = current_version;
        }
        public String getCurrent_version() {
            return current_version;
        }

        public void setDate(String date) {
            this.date = date;
        }
        public String getDate() {
            return date;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        public String getDescription() {
            return description;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setMin_version(String min_version) {
            this.min_version = min_version;
        }
        public String getMin_version() {
            return min_version;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }
        public String getPlatform() {
            return platform;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }

    }

}
