package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2019/1/9 16:48
 * Email:644563767@qq.com
 */

public class RequsetVideoHeadBean {
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
    public static class Data {

        private int id;
        private int type;
        private int pid;
        private String name;
        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
        public int getPid() {
            return pid;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

    }
}
