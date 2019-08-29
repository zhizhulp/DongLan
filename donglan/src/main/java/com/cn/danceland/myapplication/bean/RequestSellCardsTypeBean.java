package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2017/11/16 15:38
 * Email:644563767@qq.com
 */


import java.util.List;

public class RequestSellCardsTypeBean {

    private boolean success;
    private String errorMsg;
    private List<Data> data;

    @Override
    public String toString() {
        return "RequestSellCardsTypeBean{" +
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
        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", branch_id=" + branch_id +
                    ", name='" + name + '\'' +
                    ", delete_remark='" + delete_remark + '\'' +
                    '}';
        }

        private int id;
        private int branch_id;
        private String name;
        private String delete_remark;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setDelete_remark(String delete_remark) {
            this.delete_remark = delete_remark;
        }

        public String getDelete_remark() {
            return delete_remark;
        }

    }
}