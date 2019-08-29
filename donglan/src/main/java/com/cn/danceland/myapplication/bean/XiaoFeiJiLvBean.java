package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/11/7 14:42
 * Email:644563767@qq.com
 */


public class XiaoFeiJiLvBean {
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

        private String date;
        private List<XList> list;
        public void setDate(String date) {
            this.date = date;
        }
        public String getDate() {
            return date;
        }

        public void setList(List<XList> list) {
            this.list = list;
        }
        public List<XList> getList() {
            return list;
        }
        public class XList {

            private int id;
            private int type;
            private String store_type_id;
            private float price;
            private float giving;
            private String operate_time;
            private String operate_date;
            private String operate_id;
            private String goods_id;
            private String remark;
            private int branch_id;
            private int member_id;
            private int account_id;
            private int opt_no;
            private int bus_type;
            private String sell_id;
            private String bill_id;
            private String quit_sum_price;
            private String member_name;
            private String member_no;
            private String sell_name;
            private String operator_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getStore_type_id() {
                return store_type_id;
            }

            public void setStore_type_id(String store_type_id) {
                this.store_type_id = store_type_id;
            }

            public float getPrice() {
                return price;
            }

            public void setPrice(float price) {
                this.price = price;
            }

            public float getGiving() {
                return giving;
            }

            public void setGiving(float giving) {
                this.giving = giving;
            }

            public String getOperate_time() {
                return operate_time;
            }

            public void setOperate_time(String operate_time) {
                this.operate_time = operate_time;
            }

            public String getOperate_date() {
                return operate_date;
            }

            public void setOperate_date(String operate_date) {
                this.operate_date = operate_date;
            }

            public String getOperate_id() {
                return operate_id;
            }

            public void setOperate_id(String operate_id) {
                this.operate_id = operate_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getBranch_id() {
                return branch_id;
            }

            public void setBranch_id(int branch_id) {
                this.branch_id = branch_id;
            }

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public int getAccount_id() {
                return account_id;
            }

            public void setAccount_id(int account_id) {
                this.account_id = account_id;
            }

            public int getOpt_no() {
                return opt_no;
            }

            public void setOpt_no(int opt_no) {
                this.opt_no = opt_no;
            }

            public int getBus_type() {
                return bus_type;
            }

            public void setBus_type(int bus_type) {
                this.bus_type = bus_type;
            }

            public String getSell_id() {
                return sell_id;
            }

            public void setSell_id(String sell_id) {
                this.sell_id = sell_id;
            }

            public String getBill_id() {
                return bill_id;
            }

            public void setBill_id(String bill_id) {
                this.bill_id = bill_id;
            }

            public String getQuit_sum_price() {
                return quit_sum_price;
            }

            public void setQuit_sum_price(String quit_sum_price) {
                this.quit_sum_price = quit_sum_price;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getMember_no() {
                return member_no;
            }

            public void setMember_no(String member_no) {
                this.member_no = member_no;
            }

            public String getSell_name() {
                return sell_name;
            }

            public void setSell_name(String sell_name) {
                this.sell_name = sell_name;
            }

            public String getOperator_name() {
                return operator_name;
            }

            public void setOperator_name(String operator_name) {
                this.operator_name = operator_name;
            }
        }
    }

}
