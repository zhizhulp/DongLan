package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/4/10.
 */

public class BranchBannerBean implements Serializable {

    private int code;
    private List<Data> data;
    private String errorMsg;
    private boolean success;
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


    public static class Data implements Serializable{

        private int branch_id;
        private int id;
        private String img_path;
        private String img_url;
        private int order_num;
        private String title;
        private String url;
        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }
        public int getBranch_id() {
            return branch_id;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }
        public String getImg_path() {
            return img_path;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
        public String getImg_url() {
            return img_url;
        }

        public void setOrder_num(int order_num) {
            this.order_num = order_num;
        }
        public int getOrder_num() {
            return order_num;
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
