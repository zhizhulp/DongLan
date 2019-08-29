package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/3/21.
 */

public class ShopJiaoLianBean implements Serializable{

    private List<Data> data;
    private boolean success;
    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public class Data implements Serializable {

        private int auth;
        private String avatar_path;
        private int branch_id;
        private String cname;
        private int default_branch;
        private int department_id;
        private int enabled;
        private int gender;
        private int id;
        private String identity_card;
        private String member_no;
        private String nick_name;
        private String password;
        private String avatar_url;
        private int person_id;
        private String phone_no;
        private int platform;
        private long reg_date;
        private String remark;
        private String self_avatar_path;

        @Override
        public String toString() {
            return "Data{" +
                    "auth=" + auth +
                    ", avatar_path='" + avatar_path + '\'' +
                    ", branch_id=" + branch_id +
                    ", cname='" + cname + '\'' +
                    ", default_branch=" + default_branch +
                    ", department_id=" + department_id +
                    ", enabled=" + enabled +
                    ", gender=" + gender +
                    ", id=" + id +
                    ", identity_card='" + identity_card + '\'' +
                    ", member_no='" + member_no + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", password='" + password + '\'' +
                    ", avatar_url='" + avatar_url + '\'' +
                    ", person_id=" + person_id +
                    ", phone_no='" + phone_no + '\'' +
                    ", platform=" + platform +
                    ", reg_date=" + reg_date +
                    ", remark='" + remark + '\'' +
                    ", self_avatar_path='" + self_avatar_path + '\'' +
                    '}';
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public void setAuth(int auth) {
            this.auth = auth;
        }
        public int getAuth() {
            return auth;
        }

        public void setAvatar_path(String avatar_path) {
            this.avatar_path = avatar_path;
        }
        public String getAvatar_path() {
            return avatar_path;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }
        public int getBranch_id() {
            return branch_id;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }
        public String getCname() {
            return cname;
        }

        public void setDefault_branch(int default_branch) {
            this.default_branch = default_branch;
        }
        public int getDefault_branch() {
            return default_branch;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }
        public int getDepartment_id() {
            return department_id;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }
        public int getEnabled() {
            return enabled;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
        public int getGender() {
            return gender;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setIdentity_card(String identity_card) {
            this.identity_card = identity_card;
        }
        public String getIdentity_card() {
            return identity_card;
        }

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }
        public String getMember_no() {
            return member_no;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }
        public String getNick_name() {
            return nick_name;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public String getPassword() {
            return password;
        }

        public void setPerson_id(int person_id) {
            this.person_id = person_id;
        }
        public int getPerson_id() {
            return person_id;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }
        public String getPhone_no() {
            return phone_no;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }
        public int getPlatform() {
            return platform;
        }

        public void setReg_date(long reg_date) {
            this.reg_date = reg_date;
        }
        public long getReg_date() {
            return reg_date;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
        public String getRemark() {
            return remark;
        }

        public void setSelf_avatar_path(String self_avatar_path) {
            this.self_avatar_path = self_avatar_path;
        }
        public String getSelf_avatar_path() {
            return self_avatar_path;
        }

    }

}
