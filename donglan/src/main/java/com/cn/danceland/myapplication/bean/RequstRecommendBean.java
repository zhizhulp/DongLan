package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/3/14 18:30
 * Email:644563767@qq.com
 */


public class RequstRecommendBean {

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

    @Override
    public String toString() {
        return "RequstRecommendBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {

        private String id;
        private int branch_id;
        private int member_id;
        private String member_no;
        private long create_date;
        private int introduce_member_id;
        private String introduce_member_no;
        private String name;
        private int gender;
        private int status;
        private int delete_remark;
        private String phone_no;
        private String member_name;
        private String member_phone;

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", branch_id=" + branch_id +
                    ", member_id=" + member_id +
                    ", member_no='" + member_no + '\'' +
                    ", create_date=" + create_date +
                    ", introduce_member_id=" + introduce_member_id +
                    ", introduce_member_no='" + introduce_member_no + '\'' +
                    ", name='" + name + '\'' +
                    ", gender=" + gender +
                    ", status=" + status +
                    ", delete_remark=" + delete_remark +
                    ", phone_no='" + phone_no + '\'' +
                    ", member_name='" + member_name + '\'' +
                    ", member_phone='" + member_phone + '\'' +
                    '}';
        }

        public String getMember_phone() {
            return member_phone;
        }

        public void setMember_phone(String member_phone) {
            this.member_phone = member_phone;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public int getBranch_id() {
            return branch_id;
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

        public void setCreate_date(long create_date) {
            this.create_date = create_date;
        }

        public long getCreate_date() {
            return create_date;
        }

        public void setIntroduce_member_id(int introduce_member_id) {
            this.introduce_member_id = introduce_member_id;
        }

        public int getIntroduce_member_id() {
            return introduce_member_id;
        }

        public void setIntroduce_member_no(String introduce_member_no) {
            this.introduce_member_no = introduce_member_no;
        }

        public String getIntroduce_member_no() {
            return introduce_member_no;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getGender() {
            return gender;
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

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getPhone_no() {
            return phone_no;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_name() {
            return member_name;
        }

    }
}
