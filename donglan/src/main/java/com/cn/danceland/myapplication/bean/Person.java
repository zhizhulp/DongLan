package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by shy on 2018/3/20 11:10
 * Email:644563767@qq.com
 */



public class Person  implements Serializable {
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
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
                ", zone_code='" + zone_code + '\'' +
                ", platform=" + platform +
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
                ", p_consume='" + p_consume + '\'' +
                ", chronic_ids='" + chronic_ids + '\'' +
                ", chonicList='" + chonicList + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    private String id;
    private String cname;
    private String member_no;
    private String phone_no;
    private String nick_name;
    private String gender;
    private String height;
    private String weight;
    private String birthday;
    private long reg_date;
    private String password;
    private String default_branch;
    private String zone_code;
    private int platform;
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
    private String p_consume;
    private String chronic_ids;
    private String chonicList;
    private String token;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
    public String getCname() {
        return cname;
    }

    public void setMember_no(String member_no) {
        this.member_no = member_no;
    }
    public String getMember_no() {
        return member_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
    public String getPhone_no() {
        return phone_no;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getBirthday() {
        return birthday;
    }

    public void setReg_date(long reg_date) {
        this.reg_date = reg_date;
    }
    public long getReg_date() {
        return reg_date;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setDefault_branch(String default_branch) {
        this.default_branch = default_branch;
    }
    public String getDefault_branch() {
        return default_branch;
    }

    public void setZone_code(String zone_code) {
        this.zone_code = zone_code;
    }
    public String getZone_code() {
        return zone_code;
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

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }
    public String getReg_id() {
        return reg_id;
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

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }
    public String getLogin_name() {
        return login_name;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }
    public int getTerminal() {
        return terminal;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }
    public String getDepartment_id() {
        return department_id;
    }

    public void setWeichat_no(String weichat_no) {
        this.weichat_no = weichat_no;
    }
    public String getWeichat_no() {
        return weichat_no;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getMail() {
        return mail;
    }

    public void setFollow_level(String follow_level) {
        this.follow_level = follow_level;
    }
    public String getFollow_level() {
        return follow_level;
    }

    public void setFitness_level(String fitness_level) {
        this.fitness_level = fitness_level;
    }
    public String getFitness_level() {
        return fitness_level;
    }

    public void setEmergency_name(String emergency_name) {
        this.emergency_name = emergency_name;
    }
    public String getEmergency_name() {
        return emergency_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return company;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public String getNationality() {
        return nationality;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }
    public String getCertificate_type() {
        return certificate_type;
    }

    public void setSys_role(String sys_role) {
        this.sys_role = sys_role;
    }
    public String getSys_role() {
        return sys_role;
    }

    public void setEmergency_phone(String emergency_phone) {
        this.emergency_phone = emergency_phone;
    }
    public String getEmergency_phone() {
        return emergency_phone;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }
    public String getHonor() {
        return honor;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign() {
        return sign;
    }

    public void setFitness(String fitness) {
        this.fitness = fitness;
    }
    public String getFitness() {
        return fitness;
    }

    public void setGood_at(String good_at) {
        this.good_at = good_at;
    }
    public String getGood_at() {
        return good_at;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    public String getHobby() {
        return hobby;
    }

    public void setQq_no(String qq_no) {
        this.qq_no = qq_no;
    }
    public String getQq_no() {
        return qq_no;
    }

    public void setP_consume(String p_consume) {
        this.p_consume = p_consume;
    }
    public String getP_consume() {
        return p_consume;
    }

    public void setChronic_ids(String chronic_ids) {
        this.chronic_ids = chronic_ids;
    }
    public String getChronic_ids() {
        return chronic_ids;
    }

    public void setChonicList(String chonicList) {
        this.chonicList = chonicList;
    }
    public String getChonicList() {
        return chonicList;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

}