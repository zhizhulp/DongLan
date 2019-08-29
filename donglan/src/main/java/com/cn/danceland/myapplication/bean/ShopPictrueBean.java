package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/10/25 10:14
 * Email:644563767@qq.com
 */


public class ShopPictrueBean {
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

        private String id;
        private String branch_id;
        private String picture_url;
        private int order_num;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }
        public String getBranch_id() {
            return branch_id;
        }

        public void setPicture_url(String picture_url) {
            this.picture_url = picture_url;
        }
        public String getPicture_url() {
            return picture_url;
        }

        public void setOrder_num(int order_num) {
            this.order_num = order_num;
        }
        public int getOrder_num() {
            return order_num;
        }

    }
}
