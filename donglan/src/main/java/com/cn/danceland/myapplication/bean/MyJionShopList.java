package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/4/9 15:58
 * Email:644563767@qq.com
 */


public class MyJionShopList {

    private boolean success;
    private String errorMsg;
    private String code;
    private List<Data> data;

    @Override
    public String toString() {
        return "MyJionShopList{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data {

        private String name;
        private String create_time;
        private String auth;
        private String branch_id;
        private List<String> auths;
        private String logo;

        @Override
        public String toString() {
            return "Data{" +
                    "name='" + name + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", auth='" + auth + '\'' +
                    ", branch_id=" + branch_id +
                    ", auths='" + auths + '\'' +
                    ", logo='" + logo + '\'' +
                    '}';
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getAuth() {
            return auth;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public List<String> getAuths() {
            return auths;
        }

        public void setAuths(List<String> auths) {
            this.auths = auths;
        }
    }
}