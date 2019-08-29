package com.cn.danceland.myapplication.bean;

/**
 * Created by feng on 2018/1/5.
 */

public class TestingBean {

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

        private int status;
        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

    }

}
