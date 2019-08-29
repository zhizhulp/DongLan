package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shy on 2017/12/26 17:57
 * Email:644563767@qq.com
 */



public class RequestNewBindBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private  int code;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestNewBindBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable {

        private int auth;
        private Person person;
        private List<Branchs> branchs;

        public List<Branchs> getBranchs() {
            return branchs;
        }

        public void setBranchs(List<Branchs> branchs) {
            this.branchs = branchs;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "auth=" + auth +
                    ", person=" + person +
                    '}';
        }

        public void setAuth(int auth) {
            this.auth = auth;
        }
        public int getAuth() {
            return auth;
        }

        public void setPerson(Person person) {
            this.person = person;
        }
        public Person getPerson() {
            return person;
        }

        public class Person implements Serializable {

            private String id;
            private String cname;
            private String member_no;
            private String phone_no;
            private int gender;
            private String nick_name;
            private float height;
            private float weight;
            private String birthday;
            private long reg_date;
            private String password;
            private int default_branch;
            private long zone_code;
            private String platform;
            private String avatar_path;
            private String self_avatar_path;
            private String reg_id;
            private String remark;
            private String identity_card;
            private String login_name;
            private int terminal;
            private String department_id;
            private String weichat_no;
            private String mail;
            private String follow_level;
            private String fitness_level;
            private String emergency_name;
            private String address;
            private String company;
            private String nationality;
            private String certificate_type;
            private String sys_role;
            private String emergency_phone;
            private String honor;
            private String sign;
            private String fitness;
            private String good_at;
            private String hobby;
            private String qq_no;
            private String repeat_phone;
            private String p_consume;
            private String wx_open_id;
            private String chronic_ids;
            private String chonicList;
            private String web_default_branch;
            private String self_avatar_url;
            private String avatar_url;

            @Override
            public String toString() {
                return "Person{" +
                        "id='" + id + '\'' +
                        ", cname='" + cname + '\'' +
                        ", member_no='" + member_no + '\'' +
                        ", phone_no='" + phone_no + '\'' +
                        ", gender=" + gender +
                        ", nick_name='" + nick_name + '\'' +
                        ", height=" + height +
                        ", weight=" + weight +
                        ", birthday='" + birthday + '\'' +
                        ", reg_date=" + reg_date +
                        ", password='" + password + '\'' +
                        ", default_branch=" + default_branch +
                        ", zone_code=" + zone_code +
                        ", platform='" + platform + '\'' +
                        ", avatar_path='" + avatar_path + '\'' +
                        ", self_avatar_path='" + self_avatar_path + '\'' +
                        ", reg_id='" + reg_id + '\'' +
                        ", remark='" + remark + '\'' +
                        ", identity_card='" + identity_card + '\'' +
                        ", login_name='" + login_name + '\'' +
                        ", terminal=" + terminal +
                        ", department_id='" + department_id + '\'' +
                        ", weichat_no='" + weichat_no + '\'' +
                        ", mail='" + mail + '\'' +
                        ", follow_level='" + follow_level + '\'' +
                        ", fitness_level='" + fitness_level + '\'' +
                        ", emergency_name='" + emergency_name + '\'' +
                        ", address='" + address + '\'' +
                        ", company='" + company + '\'' +
                        ", nationality='" + nationality + '\'' +
                        ", certificate_type='" + certificate_type + '\'' +
                        ", sys_role='" + sys_role + '\'' +
                        ", emergency_phone='" + emergency_phone + '\'' +
                        ", honor='" + honor + '\'' +
                        ", sign='" + sign + '\'' +
                        ", fitness='" + fitness + '\'' +
                        ", good_at='" + good_at + '\'' +
                        ", hobby='" + hobby + '\'' +
                        ", qq_no='" + qq_no + '\'' +
                        ", repeat_phone='" + repeat_phone + '\'' +
                        ", p_consume='" + p_consume + '\'' +
                        ", wx_open_id='" + wx_open_id + '\'' +
                        ", chronic_ids='" + chronic_ids + '\'' +
                        ", chonicList='" + chonicList + '\'' +
                        ", web_default_branch='" + web_default_branch + '\'' +
                        ", self_avatar_url='" + self_avatar_url + '\'' +
                        ", avatar_url='" + avatar_url + '\'' +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }

            public String getMember_no() {
                return member_no;
            }

            public void setMember_no(String member_no) {
                this.member_no = member_no;
            }

            public String getPhone_no() {
                return phone_no;
            }

            public void setPhone_no(String phone_no) {
                this.phone_no = phone_no;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public void setHeight(float height) {
                this.height = height;
            }

            public void setWeight(float weight) {
                this.weight = weight;
            }

            public float getHeight() {
                return height;
            }

            public float getWeight() {
                return weight;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public long getReg_date() {
                return reg_date;
            }

            public void setReg_date(long reg_date) {
                this.reg_date = reg_date;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getDefault_branch() {
                return default_branch;
            }

            public void setDefault_branch(int default_branch) {
                this.default_branch = default_branch;
            }

            public long getZone_code() {
                return zone_code;
            }

            public void setZone_code(long zone_code) {
                this.zone_code = zone_code;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getAvatar_path() {
                return avatar_path;
            }

            public void setAvatar_path(String avatar_path) {
                this.avatar_path = avatar_path;
            }

            public String getSelf_avatar_path() {
                return self_avatar_path;
            }

            public void setSelf_avatar_path(String self_avatar_path) {
                this.self_avatar_path = self_avatar_path;
            }

            public String getReg_id() {
                return reg_id;
            }

            public void setReg_id(String reg_id) {
                this.reg_id = reg_id;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getIdentity_card() {
                return identity_card;
            }

            public void setIdentity_card(String identity_card) {
                this.identity_card = identity_card;
            }

            public String getLogin_name() {
                return login_name;
            }

            public void setLogin_name(String login_name) {
                this.login_name = login_name;
            }

            public int getTerminal() {
                return terminal;
            }

            public void setTerminal(int terminal) {
                this.terminal = terminal;
            }

            public String getDepartment_id() {
                return department_id;
            }

            public void setDepartment_id(String department_id) {
                this.department_id = department_id;
            }

            public String getWeichat_no() {
                return weichat_no;
            }

            public void setWeichat_no(String weichat_no) {
                this.weichat_no = weichat_no;
            }

            public String getMail() {
                return mail;
            }

            public void setMail(String mail) {
                this.mail = mail;
            }

            public String getFollow_level() {
                return follow_level;
            }

            public void setFollow_level(String follow_level) {
                this.follow_level = follow_level;
            }

            public String getFitness_level() {
                return fitness_level;
            }

            public void setFitness_level(String fitness_level) {
                this.fitness_level = fitness_level;
            }

            public String getEmergency_name() {
                return emergency_name;
            }

            public void setEmergency_name(String emergency_name) {
                this.emergency_name = emergency_name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCompany() {
                return company;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getNationality() {
                return nationality;
            }

            public void setNationality(String nationality) {
                this.nationality = nationality;
            }

            public String getCertificate_type() {
                return certificate_type;
            }

            public void setCertificate_type(String certificate_type) {
                this.certificate_type = certificate_type;
            }

            public String getSys_role() {
                return sys_role;
            }

            public void setSys_role(String sys_role) {
                this.sys_role = sys_role;
            }

            public String getEmergency_phone() {
                return emergency_phone;
            }

            public void setEmergency_phone(String emergency_phone) {
                this.emergency_phone = emergency_phone;
            }

            public String getHonor() {
                return honor;
            }

            public void setHonor(String honor) {
                this.honor = honor;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getFitness() {
                return fitness;
            }

            public void setFitness(String fitness) {
                this.fitness = fitness;
            }

            public String getGood_at() {
                return good_at;
            }

            public void setGood_at(String good_at) {
                this.good_at = good_at;
            }

            public String getHobby() {
                return hobby;
            }

            public void setHobby(String hobby) {
                this.hobby = hobby;
            }

            public String getQq_no() {
                return qq_no;
            }

            public void setQq_no(String qq_no) {
                this.qq_no = qq_no;
            }

            public String getRepeat_phone() {
                return repeat_phone;
            }

            public void setRepeat_phone(String repeat_phone) {
                this.repeat_phone = repeat_phone;
            }

            public String getP_consume() {
                return p_consume;
            }

            public void setP_consume(String p_consume) {
                this.p_consume = p_consume;
            }

            public String getWx_open_id() {
                return wx_open_id;
            }

            public void setWx_open_id(String wx_open_id) {
                this.wx_open_id = wx_open_id;
            }

            public String getChronic_ids() {
                return chronic_ids;
            }

            public void setChronic_ids(String chronic_ids) {
                this.chronic_ids = chronic_ids;
            }

            public String getChonicList() {
                return chonicList;
            }

            public void setChonicList(String chonicList) {
                this.chonicList = chonicList;
            }

            public String getWeb_default_branch() {
                return web_default_branch;
            }

            public void setWeb_default_branch(String web_default_branch) {
                this.web_default_branch = web_default_branch;
            }

            public String getSelf_avatar_url() {
                return self_avatar_url;
            }

            public void setSelf_avatar_url(String self_avatar_url) {
                this.self_avatar_url = self_avatar_url;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }
        }

        public class Branchs implements Serializable {
            @Override
            public String toString() {
                return "Branchs{" +
                        "branch_name='" + branch_name + '\'' +
                        ", auth='" + auth + '\'' +
                        '}';
            }

            private String branch_name;
            private String auth;
            public void setBranch_name(String branch_name) {
                this.branch_name = branch_name;
            }
            public String getBranch_name() {
                return branch_name;
            }

            public void setAuth(String auth) {
                this.auth = auth;
            }
            public String getAuth() {
                return auth;
            }

        }
    }

}