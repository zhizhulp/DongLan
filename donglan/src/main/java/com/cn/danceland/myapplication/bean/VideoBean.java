package com.cn.danceland.myapplication.bean;

/**
 * Created by feng on 2017/11/17.
 */

public class VideoBean {

    private boolean success;
    private String errorMsg;
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

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    public class Data {

        private String imgUrl;
        private String imgPath;

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
        public String getImgUrl() {
            return imgUrl;
        }

    }
}
