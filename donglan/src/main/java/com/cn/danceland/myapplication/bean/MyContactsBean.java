package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/7/20 16:47
 * Email:644563767@qq.com
 */


public class MyContactsBean {
    private boolean success;
    private String errorMsg;
    private String code;
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

        private String avatar_url;
        private String member_no;
        private String name;
        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }
        public String getAvatar_url() {
            return avatar_url;
        }

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }
        public String getMember_no() {
            return member_no;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

    }
}
