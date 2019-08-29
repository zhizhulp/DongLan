package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2018/10/31 17:59
 * Email:644563767@qq.com
 */


public class RequestPayWayBean {
    private boolean success;
    private String errorMsg;
    private int code;
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

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    public class Data {

        private String branch_id;
        private int pay_type;
        private int alipay_enable;
        private int wxpay_enable;
        private int xypay_enable;
        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }
        public String getBranch_id() {
            return branch_id;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }
        public int getPay_type() {
            return pay_type;
        }

        public void setAlipay_enable(int alipay_enable) {
            this.alipay_enable = alipay_enable;
        }
        public int getAlipay_enable() {
            return alipay_enable;
        }

        public void setWxpay_enable(int wxpay_enable) {
            this.wxpay_enable = wxpay_enable;
        }
        public int getWxpay_enable() {
            return wxpay_enable;
        }

        public void setXypay_enable(int xypay_enable) {
            this.xypay_enable = xypay_enable;
        }
        public int getXypay_enable() {
            return xypay_enable;
        }

    }
}
