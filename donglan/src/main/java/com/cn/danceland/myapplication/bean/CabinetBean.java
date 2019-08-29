package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2018/2/7.
 */

public class CabinetBean {

    private boolean success;
    private String errorMsg;
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

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data {

        private int id;
        //private String locker_no;
        private String locker_name;
        private int branch_id;
        private int locker_zone_id;
        private int member_id;
        private String member_no;
        private long start_date;
        private long end_date;
        private int sell_id;
        private int deposit;
        private int real_deposit;
        private int price;
        private int real_price;
        private int status;
        private int delete_remark;
        private String member_name;
        private String locker_zone_name;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

//        public void setLocker_no(String locker_no) {
//            this.locker_no = locker_no;
//        }
//        public String getLocker_no() {
//            return locker_no;
//        }

        public void setLocker_name(String locker_name) {
            this.locker_name = locker_name;
        }

        public String getLocker_name() {
            return locker_name;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public void setLocker_zone_id(int locker_zone_id) {
            this.locker_zone_id = locker_zone_id;
        }

        public int getLocker_zone_id() {
            return locker_zone_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }

        public int getMember_id() {
            return member_id;
        }

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }

        public String getMember_no() {
            return member_no;
        }

        public void setStart_date(long start_date) {
            this.start_date = start_date;
        }

        public long getStart_date() {
            return start_date;
        }

        public void setEnd_date(long end_date) {
            this.end_date = end_date;
        }

        public long getEnd_date() {
            return end_date;
        }

        public void setSell_id(int sell_id) {
            this.sell_id = sell_id;
        }

        public int getSell_id() {
            return sell_id;
        }

        public void setDeposit(int deposit) {
            this.deposit = deposit;
        }

        public int getDeposit() {
            return deposit;
        }

        public void setReal_deposit(int real_deposit) {
            this.real_deposit = real_deposit;
        }

        public int getReal_deposit() {
            return real_deposit;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }

        public void setReal_price(int real_price) {
            this.real_price = real_price;
        }

        public int getReal_price() {
            return real_price;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setDelete_remark(int delete_remark) {
            this.delete_remark = delete_remark;
        }

        public int getDelete_remark() {
            return delete_remark;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setLocker_zone_name(String locker_zone_name) {
            this.locker_zone_name = locker_zone_name;
        }

        public String getLocker_zone_name() {
            return locker_zone_name;
        }

    }
}
