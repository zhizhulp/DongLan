package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/5/15.
 */

public class CheckUpdateBean implements Serializable {

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

        private String id;
        private String platform;
        private String current_version;
        private String url;
        private String description;
        private String min_version;
        private String date;
        private String status;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }
        public String getPlatform() {
            return platform;
        }

        public void setCurrent_version(String current_version) {
            this.current_version = current_version;
        }
        public String getCurrent_version() {
            return current_version;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }

        public void setDescription(String description) {
            this.description = description;
        }
        public String getDescription() {
            return description;
        }

        public void setMin_version(String min_version) {
            this.min_version = min_version;
        }
        public String getMin_version() {
            return min_version;
        }

        public void setDate(String date) {
            this.date = date;
        }
        public String getDate() {
            return date;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }

    }

}
