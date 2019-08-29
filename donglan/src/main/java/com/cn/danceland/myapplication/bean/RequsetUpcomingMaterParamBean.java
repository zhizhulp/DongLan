package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/1/13 14:48
 * Email:644563767@qq.com
 */

public class RequsetUpcomingMaterParamBean {

    private boolean success;
    private String errorMsg;
    private List<Data> data;

    @Override
    public String toString() {
        return "RequsetUpcomingMaterParamBean{" +
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

        private String id;
        private String customer_type;
        private String role_type;
        private String result_type;
        private String name;

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", customer_type='" + customer_type + '\'' +
                    ", role_type='" + role_type + '\'' +
                    ", result_type='" + result_type + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCustomer_type() {
            return customer_type;
        }

        public void setCustomer_type(String customer_type) {
            this.customer_type = customer_type;
        }

        public String getRole_type() {
            return role_type;
        }

        public void setRole_type(String role_type) {
            this.role_type = role_type;
        }

        public String getResult_type() {
            return result_type;
        }

        public void setResult_type(String result_type) {
            this.result_type = result_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}