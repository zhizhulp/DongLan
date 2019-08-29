package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2017/12/7 17:09
 * Email:644563767@qq.com
 * 轮播图数据
 */

public class RequestImageNewsDataBean {

    private boolean success;
    private String errorMsg;
    private List<Data> data;

    @Override
    public String toString() {
        return "RequestImageNewsDataBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

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

        private int id;
        private String img_url;
        private String title;
        private String url;


        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", img_url='" + img_url + '\'' +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +

                    '}';
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
        public String getImg_url() {
            return img_url;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }



    }
}