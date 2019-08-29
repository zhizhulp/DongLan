/**
 * Copyright 2017 bejson.com
 */
package com.cn.danceland.myapplication.bean;


import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    private String token;
    private String sig;
    private String imgUrl;
    private Person person;
    private Employee employee;
    private Member member;
    private List<Roles> roles;
    private String verCode;
    private Branch branch;
    private Boolean hasPwd;
    private String phone_no;

    public String getPhone() {
        return phone_no;
    }

    public void setPhone(String phone) {
        this.phone_no = phone;
    }

    public Boolean getHasPwd() {
        return hasPwd;
    }

    public void setHasPwd(Boolean hasPwd) {
        this.hasPwd = hasPwd;
    }

    @Override
    public String toString() {
        return "Data{" +
                "token='" + token + '\'' +
                ", sig='" + sig + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", person=" + person +
                ", employee=" + employee +
                ", member=" + member +
                ", roles=" + roles +
                ", verCode='" + verCode + '\'' +
                ", branch=" + branch +
                '}';
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getVerCode() {
        return verCode;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public class Member implements Serializable {

        private String id;
        private int person_id;
        private String admin_emp_id;
        private int teach_emp_id;
        private int branch_id;
        private int enabled;
        private String auth;
        private String branch_name;
        private String guest_recom;
        private String member_recom;
        private String guest_aware_way;
        private String member_aware_way;
        private String card_type;
        private String total_money;
        private String type;
        private long create_time;
        private String final_admin_id;
        private String final_teach_id;
        private String last_time;
        private String maintain_status;
        private String remark;
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
        private String default_branch;
        private String zone_code;
        private int platform;
        private String avatar_path;
        private String self_avatar_path;
        private String reg_id;
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
        private String score;
        private String member_name;
        private String m_consume;
        private String project_ids;
        private String chronic_ids;
        private String target_ids;
        private String avatar_url;
        private String self_avatar_url;
        private String chonicList;
        private String projectList;
        private String targetList;
        private String guest_way;
        private String admin_name;
        private String teach_name;
        private String final_admin_name;
        private String final_teach_name;
        private String branchRanking;
        private String appRanking;
        private String branchScore;
        private String appScore;

        @Override
        public String toString() {
            return "Member{" +
                    "id=" + id +
                    ", person_id=" + person_id +
                    ", admin_emp_id=" + admin_emp_id +
                    ", teach_emp_id=" + teach_emp_id +
                    ", branch_id=" + branch_id +
                    ", enabled=" + enabled +
                    ", auth=" + auth +
                    ", branch_name='" + branch_name + '\'' +
                    ", guest_recom='" + guest_recom + '\'' +
                    ", member_recom='" + member_recom + '\'' +
                    ", guest_aware_way='" + guest_aware_way + '\'' +
                    ", member_aware_way='" + member_aware_way + '\'' +
                    ", card_type='" + card_type + '\'' +
                    ", total_money='" + total_money + '\'' +
                    ", type='" + type + '\'' +
                    ", create_time=" + create_time +
                    ", final_admin_id='" + final_admin_id + '\'' +
                    ", final_teach_id='" + final_teach_id + '\'' +
                    ", last_time='" + last_time + '\'' +
                    ", maintain_status='" + maintain_status + '\'' +
                    ", remark='" + remark + '\'' +
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
                    ", default_branch='" + default_branch + '\'' +
                    ", zone_code='" + zone_code + '\'' +
                    ", platform=" + platform +
                    ", avatar_path='" + avatar_path + '\'' +
                    ", self_avatar_path='" + self_avatar_path + '\'' +
                    ", reg_id='" + reg_id + '\'' +
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
                    ", score='" + score + '\'' +
                    ", member_name='" + member_name + '\'' +
                    ", m_consume='" + m_consume + '\'' +
                    ", project_ids='" + project_ids + '\'' +
                    ", chronic_ids='" + chronic_ids + '\'' +
                    ", target_ids='" + target_ids + '\'' +
                    ", avatar_url='" + avatar_url + '\'' +
                    ", self_avatar_url='" + self_avatar_url + '\'' +
                    ", chonicList='" + chonicList + '\'' +
                    ", projectList='" + projectList + '\'' +
                    ", targetList='" + targetList + '\'' +
                    ", guest_way='" + guest_way + '\'' +
                    ", admin_name='" + admin_name + '\'' +
                    ", teach_name='" + teach_name + '\'' +
                    ", final_admin_name='" + final_admin_name + '\'' +
                    ", final_teach_name='" + final_teach_name + '\'' +
                    ", branchRanking='" + branchRanking + '\'' +
                    ", appRanking='" + appRanking + '\'' +
                    ", branchScore='" + branchScore + '\'' +
                    ", appScore='" + appScore + '\'' +
                    '}';
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setPerson_id(int person_id) {
            this.person_id = person_id;
        }

        public int getPerson_id() {
            return person_id;
        }

        public void setAdmin_emp_id(String admin_emp_id) {
            this.admin_emp_id = admin_emp_id;
        }

        public String getAdmin_emp_id() {
            return admin_emp_id;
        }

        public void setTeach_emp_id(int teach_emp_id) {
            this.teach_emp_id = teach_emp_id;
        }

        public int getTeach_emp_id() {
            return teach_emp_id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getAuth() {
            return auth;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setGuest_recom(String guest_recom) {
            this.guest_recom = guest_recom;
        }

        public String getGuest_recom() {
            return guest_recom;
        }

        public void setMember_recom(String member_recom) {
            this.member_recom = member_recom;
        }

        public String getMember_recom() {
            return member_recom;
        }

        public void setGuest_aware_way(String guest_aware_way) {
            this.guest_aware_way = guest_aware_way;
        }

        public String getGuest_aware_way() {
            return guest_aware_way;
        }

        public void setMember_aware_way(String member_aware_way) {
            this.member_aware_way = member_aware_way;
        }

        public String getMember_aware_way() {
            return member_aware_way;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
        }

        public String getCard_type() {
            return card_type;
        }

        public void setTotal_money(String total_money) {
            this.total_money = total_money;
        }

        public String getTotal_money() {
            return total_money;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setFinal_admin_id(String final_admin_id) {
            this.final_admin_id = final_admin_id;
        }

        public String getFinal_admin_id() {
            return final_admin_id;
        }

        public void setFinal_teach_id(String final_teach_id) {
            this.final_teach_id = final_teach_id;
        }

        public String getFinal_teach_id() {
            return final_teach_id;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setMaintain_status(String maintain_status) {
            this.maintain_status = maintain_status;
        }

        public String getMaintain_status() {
            return maintain_status;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
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

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getGender() {
            return gender;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setHeight(float height) {
            this.height = height;
        }

        public float getHeight() {
            return height;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public float getWeight() {
            return weight;
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

        public void setScore(String score) {
            this.score = score;
        }

        public String getScore() {
            return score;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setM_consume(String m_consume) {
            this.m_consume = m_consume;
        }

        public String getM_consume() {
            return m_consume;
        }

        public void setProject_ids(String project_ids) {
            this.project_ids = project_ids;
        }

        public String getProject_ids() {
            return project_ids;
        }

        public void setChronic_ids(String chronic_ids) {
            this.chronic_ids = chronic_ids;
        }

        public String getChronic_ids() {
            return chronic_ids;
        }

        public void setTarget_ids(String target_ids) {
            this.target_ids = target_ids;
        }

        public String getTarget_ids() {
            return target_ids;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setSelf_avatar_url(String self_avatar_url) {
            this.self_avatar_url = self_avatar_url;
        }

        public String getSelf_avatar_url() {
            return self_avatar_url;
        }

        public void setChonicList(String chonicList) {
            this.chonicList = chonicList;
        }

        public String getChonicList() {
            return chonicList;
        }

        public void setProjectList(String projectList) {
            this.projectList = projectList;
        }

        public String getProjectList() {
            return projectList;
        }

        public void setTargetList(String targetList) {
            this.targetList = targetList;
        }

        public String getTargetList() {
            return targetList;
        }

        public void setGuest_way(String guest_way) {
            this.guest_way = guest_way;
        }

        public String getGuest_way() {
            return guest_way;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
        }

        public String getAdmin_name() {
            return admin_name;
        }

        public void setTeach_name(String teach_name) {
            this.teach_name = teach_name;
        }

        public String getTeach_name() {
            return teach_name;
        }

        public void setFinal_admin_name(String final_admin_name) {
            this.final_admin_name = final_admin_name;
        }

        public String getFinal_admin_name() {
            return final_admin_name;
        }

        public void setFinal_teach_name(String final_teach_name) {
            this.final_teach_name = final_teach_name;
        }

        public String getFinal_teach_name() {
            return final_teach_name;
        }

        public void setBranchRanking(String branchRanking) {
            this.branchRanking = branchRanking;
        }

        public String getBranchRanking() {
            return branchRanking;
        }

        public void setAppRanking(String appRanking) {
            this.appRanking = appRanking;
        }

        public String getAppRanking() {
            return appRanking;
        }

        public void setBranchScore(String branchScore) {
            this.branchScore = branchScore;
        }

        public String getBranchScore() {
            return branchScore;
        }

        public void setAppScore(String appScore) {
            this.appScore = appScore;
        }

        public String getAppScore() {
            return appScore;
        }

    }

    public class Employee implements Serializable {

        private int id;
        private int branch_id;
        private int person_id;
        private int enabled;
        private int auth;
        private String branch_name;
        private String avatar_url;
        private String cname;
        private String member_no;
        private String phone_no;
        private int gender;
        private String nick_name;
        private String height;
        private String weight;
        private String birthday;
        private long reg_date;
        private String password;
        private int default_branch;
        private String zone_code;
        private int platform;
        private String avatar_path;
        private String self_avatar_path;
        private String reg_id;
        private String remark;
        private String identity_card;
        private String login_name;
        private String terminal;
        private int department_id;
        private String weichat_no;
        private String medical_history;
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
        private String emp_name;
        private String emp_no;
        private String role_type;

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
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

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getAvatar_url() {
            return avatar_url;
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

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getGender() {
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

        public void setDefault_branch(int default_branch) {
            this.default_branch = default_branch;
        }

        public int getDefault_branch() {
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

        public void setTerminal(String terminal) {
            this.terminal = terminal;
        }

        public String getTerminal() {
            return terminal;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public int getDepartment_id() {
            return department_id;
        }

        public void setWeichat_no(String weichat_no) {
            this.weichat_no = weichat_no;
        }

        public String getWeichat_no() {
            return weichat_no;
        }

        public void setMedical_history(String medical_history) {
            this.medical_history = medical_history;
        }

        public String getMedical_history() {
            return medical_history;
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

        public void setEmp_name(String emp_name) {
            this.emp_name = emp_name;
        }

        public String getEmp_name() {
            return emp_name;
        }

        public void setEmp_no(String emp_no) {
            this.emp_no = emp_no;
        }

        public String getEmp_no() {
            return emp_no;
        }

        public void setRole_type(String role_type) {
            this.role_type = role_type;
        }

        public String getRole_type() {
            return role_type;
        }

    }

    public class Roles implements Serializable {

        private int id;
        private int department_id;
        private String name;
        private int role_type;
        private String delete_remark;
        private String remark;
        private String department_name;

        @Override
        public String toString() {
            return "Roles{" +
                    "id=" + id +
                    ", department_id=" + department_id +
                    ", name='" + name + '\'' +
                    ", role_type=" + role_type +
                    ", delete_remark='" + delete_remark + '\'' +
                    ", remark='" + remark + '\'' +
                    ", department_name='" + department_name + '\'' +
                    '}';
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setDepartment_id(int department_id) {
            this.department_id = department_id;
        }

        public int getDepartment_id() {
            return department_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setRole_type(int role_type) {
            this.role_type = role_type;
        }

        public int getRole_type() {
            return role_type;
        }

        public void setDelete_remark(String delete_remark) {
            this.delete_remark = delete_remark;
        }

        public String getDelete_remark() {
            return delete_remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getDepartment_name() {
            return department_name;
        }

    }

    public class Branch implements Serializable {

        private String branch_id;// 主键
        private String league_id;// 加盟商主键
        private String name;// 门店名称
        private String real_name;// 合同名称
        private String address;// 门店地址
        private String real_address;// 合同地址
        private Integer status;// 营业状态
        private Integer zone_code;// 区划编码
        private String description;// 描述
        private String logo_path;// 门店图标
        private Integer enabled;// 可用标志
        private Float lat;// 纬度
        private Float lng;// 经度
        private String telphone;// 固定电话
        private Integer follows;// 关注数
        private String pictures;// 图片
        private String remark;// 备注
        //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private String create_time;// 创建时间
        private String creator;// 创建人
        private Long open_time ;//营业时间
        private Long close_time ;//营业时间


        //此处可添加查询显示辅助字段

        private String logo_url;// 门店图标
        private String league_name;
        private String create_name;
        private List<String> photo_url;//门店照片列表

        @Override
        public String toString() {
            return "Branch{" +
                    "branch_id='" + branch_id + '\'' +
                    ", league_id='" + league_id + '\'' +
                    ", name='" + name + '\'' +
                    ", real_name='" + real_name + '\'' +
                    ", address='" + address + '\'' +
                    ", real_address='" + real_address + '\'' +
                    ", status=" + status +
                    ", zone_code=" + zone_code +
                    ", description='" + description + '\'' +
                    ", logo_path='" + logo_path + '\'' +
                    ", enabled=" + enabled +
                    ", lat=" + lat +
                    ", lng=" + lng +
                    ", telphone='" + telphone + '\'' +
                    ", follows=" + follows +
                    ", pictures='" + pictures + '\'' +
                    ", remark='" + remark + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", creator='" + creator + '\'' +
                    ", open_time=" + open_time +
                    ", close_time=" + close_time +
                    ", logo_url='" + logo_url + '\'' +
                    ", league_name='" + league_name + '\'' +
                    ", create_name='" + create_name + '\'' +
                    ", photo_url=" + photo_url +
                    '}';
        }

        public Long getOpen_time() {
            return open_time;
        }

        public void setOpen_time(Long open_time) {
            this.open_time = open_time;
        }

        public Long getClose_time() {
            return close_time;
        }

        public void setClose_time(Long close_time) {
            this.close_time = close_time;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getLeague_id() {
            return league_id;
        }

        public void setLeague_id(String league_id) {
            this.league_id = league_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getReal_address() {
            return real_address;
        }

        public void setReal_address(String real_address) {
            this.real_address = real_address;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getZone_code() {
            return zone_code;
        }

        public void setZone_code(Integer zone_code) {
            this.zone_code = zone_code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public Integer getEnabled() {
            return enabled;
        }

        public void setEnabled(Integer enabled) {
            this.enabled = enabled;
        }

        public Float getLat() {
            return lat;
        }

        public void setLat(Float lat) {
            this.lat = lat;
        }

        public Float getLng() {
            return lng;
        }

        public void setLng(Float lng) {
            this.lng = lng;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public Integer getFollows() {
            return follows;
        }

        public void setFollows(Integer follows) {
            this.follows = follows;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getLeague_name() {
            return league_name;
        }

        public void setLeague_name(String league_name) {
            this.league_name = league_name;
        }

        public String getCreate_name() {
            return create_name;
        }

        public void setCreate_name(String create_name) {
            this.create_name = create_name;
        }

        public List<String> getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(List<String> photo_url) {
            this.photo_url = photo_url;
        }

//
//        private int id;
//        private int parent_id;
//        private String name;
//        private String order_no;
//        private int level;
//        private boolean leaf;
//        private int delete_remark;
//        private String remark;
//        private String creater;
//        private long create_time;
//        private String updater;
//        private String update_time;
//        private String hx_groupid;
//        private String branch_name;
//        private int branch_id;
//        private String children;
//        private String member_no;
//        public void setId(int id) {
//            this.id = id;
//        }
//        public int getId() {
//            return id;
//        }
//
//        public void setParent_id(int parent_id) {
//            this.parent_id = parent_id;
//        }
//        public int getParent_id() {
//            return parent_id;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//        public String getName() {
//            return name;
//        }
//
//        public void setOrder_no(String order_no) {
//            this.order_no = order_no;
//        }
//        public String getOrder_no() {
//            return order_no;
//        }
//
//        public void setLevel(int level) {
//            this.level = level;
//        }
//        public int getLevel() {
//            return level;
//        }
//
//        public void setLeaf(boolean leaf) {
//            this.leaf = leaf;
//        }
//        public boolean getLeaf() {
//            return leaf;
//        }
//
//        public void setDelete_remark(int delete_remark) {
//            this.delete_remark = delete_remark;
//        }
//        public int getDelete_remark() {
//            return delete_remark;
//        }
//
//        public void setRemark(String remark) {
//            this.remark = remark;
//        }
//        public String getRemark() {
//            return remark;
//        }
//
//        public void setCreater(String creater) {
//            this.creater = creater;
//        }
//        public String getCreater() {
//            return creater;
//        }
//
//        public void setCreate_time(long create_time) {
//            this.create_time = create_time;
//        }
//        public long getCreate_time() {
//            return create_time;
//        }
//
//        public void setUpdater(String updater) {
//            this.updater = updater;
//        }
//        public String getUpdater() {
//            return updater;
//        }
//
//        public void setUpdate_time(String update_time) {
//            this.update_time = update_time;
//        }
//        public String getUpdate_time() {
//            return update_time;
//        }
//
//        public void setHx_groupid(String hx_groupid) {
//            this.hx_groupid = hx_groupid;
//        }
//        public String getHx_groupid() {
//            return hx_groupid;
//        }
//
//        public void setBranch_name(String branch_name) {
//            this.branch_name = branch_name;
//        }
//        public String getBranch_name() {
//            return branch_name;
//        }
//
//        public void setBranch_id(int branch_id) {
//            this.branch_id = branch_id;
//        }
//        public int getBranch_id() {
//            return branch_id;
//        }
//
//        public void setChildren(String children) {
//            this.children = children;
//        }
//        public String getChildren() {
//            return children;
//        }
//
//        public void setMember_no(String member_no) {
//            this.member_no = member_no;
//        }
//        public String getMember_no() {
//            return member_no;
//        }

    }

}