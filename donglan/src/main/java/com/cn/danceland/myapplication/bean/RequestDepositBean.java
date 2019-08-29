package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2017/12/27 15:29
 * Email:644563767@qq.com
 */

public class RequestDepositBean {

    private boolean success;
    private String errorMsg;
    private List<Data> data;

    @Override
    public String toString() {
        return "RequestDepositBean{" +
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
        private int bus_type;
        private float money;
        private String phone_no;
        private int status;
        private String gender;
        private String member_id;
        private String member_name;
        private String member_no;
        private String admin_emp_name;
        private String admin_emp_id;
        private String branch_id;
        private String branch_name;
        private String operater_id;
        private String deal_time;
        private String end_time;
        private String remark;

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", bus_type=" + bus_type +
                    ", money='" + money + '\'' +
                    ", phone_no='" + phone_no + '\'' +
                    ", status=" + status +
                    ", gender='" + gender + '\'' +
                    ", member_id='" + member_id + '\'' +
                    ", member_name='" + member_name + '\'' +
                    ", member_no='" + member_no + '\'' +
                    ", admin_emp_name='" + admin_emp_name + '\'' +
                    ", admin_emp_id='" + admin_emp_id + '\'' +
                    ", branch_id='" + branch_id + '\'' +
                    ", branch_name='" + branch_name + '\'' +
                    ", operater_id='" + operater_id + '\'' +
                    ", deal_time='" + deal_time + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", remark='" + remark + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getBus_type() {
            return bus_type;
        }

        public void setBus_type(int bus_type) {
            this.bus_type = bus_type;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public String getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
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

        public String getAdmin_emp_name() {
            return admin_emp_name;
        }

        public void setAdmin_emp_name(String admin_emp_name) {
            this.admin_emp_name = admin_emp_name;
        }

        public String getAdmin_emp_id() {
            return admin_emp_id;
        }

        public void setAdmin_emp_id(String admin_emp_id) {
            this.admin_emp_id = admin_emp_id;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getOperater_id() {
            return operater_id;
        }

        public void setOperater_id(String operater_id) {
            this.operater_id = operater_id;
        }

        public String getDeal_time() {
            return deal_time;
        }

        public void setDeal_time(String deal_time) {
            this.deal_time = deal_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}