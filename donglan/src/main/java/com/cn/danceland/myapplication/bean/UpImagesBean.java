package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2017/11/14.
 */

public class UpImagesBean {

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

        private String imgUrl;
        private String imgPath;

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getImgPath() {
            return imgPath;
        }

    }
}
