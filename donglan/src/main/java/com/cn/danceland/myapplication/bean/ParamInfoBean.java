package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/1/10 11:50
 * Email:644563767@qq.com
 * 参数相关
 */

public class ParamInfoBean {

    private boolean success;
    private String errorMsg;
    private List<Data> data;

    @Override
    public String toString() {
        return "ParamInfoBean{" +
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

        private int data_key;
        private String data_value;
        private int type_code;
        private boolean isCheck=false;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "data_key=" + data_key +
                    ", data_value='" + data_value + '\'' +
                    ", type_code=" + type_code +
                    '}';
        }

        public void setData_key(int data_key) {
            this.data_key = data_key;
        }
        public int getData_key() {
            return data_key;
        }

        public void setData_value(String data_value) {
            this.data_value = data_value;
        }
        public String getData_value() {
            return data_value;
        }

        public int getType_code() {
            return type_code;
        }

        public void setType_code(int type_code) {
            this.type_code = type_code;
        }
    }
}