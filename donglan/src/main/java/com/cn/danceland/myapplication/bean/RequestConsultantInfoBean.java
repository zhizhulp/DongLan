package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shy on 2017/12/18 13:26
 * Email:644563767@qq.com
 */


public class RequestConsultantInfoBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private List<Data> data;

    @Override
    public String toString() {
        return "RequestConsultantInfoBean{" +
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

    public static class Data implements Serializable {
      private   int id;
        private int branch_id;
        private int person_id;
        private int enabled;
        private int auth;
        private String is_default;
        private int type;
        private int department_id;
        private String cname;
        private String phone_no;
        private String member_no;
        //     private int reg_date;
        private int gender;
        private String login_name;
        private String password;
        private int platform;
        private String avatar_path;
        private String self_avatar_path;
        private String remark;
        private String identity_card;
        private String avatar_url;
        private int default_branch;
        private String nick_name;
        private String height;
        private String weight;
        private String birthday;
        private String aware_way;
        private String zone_code;
        private String type_name;
        private String branch_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "branch_id='" + branch_id + '\'' +
                    ", person_id='" + person_id + '\'' +
                    ", enabled=" + enabled +
                    ", auth=" + auth +
                    ", is_default='" + is_default + '\'' +
                    ", type=" + type +
                //    ", employee_id=" + employee_id +
                    ", department_id=" + department_id +
                    ", cname='" + cname + '\'' +
                    ", phone_no='" + phone_no + '\'' +
                    ", member_no='" + member_no + '\'' +
                    //  ", reg_date=" + reg_date +
                    ", gender=" + gender +
                    ", login_name='" + login_name + '\'' +
                    ", password='" + password + '\'' +
                    ", platform=" + platform +
                    ", avatar_path='" + avatar_path + '\'' +
                    ", self_avatar_path='" + self_avatar_path + '\'' +
                    ", remark='" + remark + '\'' +
                    ", identity_card='" + identity_card + '\'' +
                    ", avatar_url='" + avatar_url + '\'' +
                    ", default_branch=" + default_branch +
                    ", nick_name='" + nick_name + '\'' +
                    ", height='" + height + '\'' +
                    ", weight='" + weight + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", aware_way='" + aware_way + '\'' +
                    ", zone_code='" + zone_code + '\'' +
                    ", type_name='" + type_name + '\'' +
                    ", branch_name='" + branch_name + '\'' +
                    '}';
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public void setPerson_id(int person_id) {
            this.person_id = person_id;
        }

        public int getPerson_id() {
            return person_id;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setAuth(int auth) {
            this.auth = auth;
        }

        public int getAuth() {
            return auth;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public int getDepartment_id() {
            return department_id;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCname() {
            return cname;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getPhone_no() {
            return phone_no;
        }

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }

        public String getMember_no() {
            return member_no;
        }


        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getGender() {
            return gender;
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
        }

        public String getLogin_name() {
            return login_name;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public int getPlatform() {
            return platform;
        }

        public void setAvatar_path(String avatar_path) {
            this.avatar_path = avatar_path;
        }

        public String getAvatar_path() {
            return avatar_path;
        }

        public void setSelf_avatar_path(String self_avatar_path) {
            this.self_avatar_path = self_avatar_path;
        }

        public String getSelf_avatar_path() {
            return self_avatar_path;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }

        public void setIdentity_card(String identity_card) {
            this.identity_card = identity_card;
        }

        public String getIdentity_card() {
            return identity_card;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setDefault_branch(int default_branch) {
            this.default_branch = default_branch;
        }

        public int getDefault_branch() {
            return default_branch;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getHeight() {
            return height;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getWeight() {
            return weight;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setAware_way(String aware_way) {
            this.aware_way = aware_way;
        }

        public String getAware_way() {
            return aware_way;
        }

        public void setZone_code(String zone_code) {
            this.zone_code = zone_code;
        }

        public String getZone_code() {
            return zone_code;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getType_name() {
            return type_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getBranch_name() {
            return branch_name;
        }

    }
}
