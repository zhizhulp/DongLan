package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/3/27 15:56
 * Email:644563767@qq.com
 */

public class RequsetAllPaiMingBean {

    private boolean success;
    private String errorMsg;
    private String code;
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data {

        private String id;
        private String person_id;
        private String admin_emp_id;
        private String teach_emp_id;
        private String branch_id;
        private String enabled;
        private String auth;
        private String branch_name;
        private String guest_recom;
        private String member_recom;
        private String guest_aware_way;
        private String member_aware_way;
        private String card_type;
        private String total_money;
        private String type;
        private String create_time;
        private String final_admin_id;
        private String final_teach_id;
        private String last_time;
        private String maintain_status;
        private String remark;
        private String cname;
        private String member_no;
        private String phone_no;
        private String gender;
        private String nick_name;
        private String height;
        private String weight;
        private String birthday;
        private String reg_date;
        private String password;
        private String default_branch;
        private String zone_code;
        private String platform;
        private String avatar_path;
        private String self_avatar_path;
        private String reg_id;
        private String identity_card;
        private String login_name;
        private String terminal;
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
        private int branchRanking;
        private String appRanking;
        private int branchScore;
        private String appScore;

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", person_id='" + person_id + '\'' +
                    ", admin_emp_id='" + admin_emp_id + '\'' +
                    ", teach_emp_id='" + teach_emp_id + '\'' +
                    ", branch_id='" + branch_id + '\'' +
                    ", enabled='" + enabled + '\'' +
                    ", auth='" + auth + '\'' +
                    ", branch_name='" + branch_name + '\'' +
                    ", guest_recom='" + guest_recom + '\'' +
                    ", member_recom='" + member_recom + '\'' +
                    ", guest_aware_way='" + guest_aware_way + '\'' +
                    ", member_aware_way='" + member_aware_way + '\'' +
                    ", card_type='" + card_type + '\'' +
                    ", total_money='" + total_money + '\'' +
                    ", type='" + type + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", final_admin_id='" + final_admin_id + '\'' +
                    ", final_teach_id='" + final_teach_id + '\'' +
                    ", last_time='" + last_time + '\'' +
                    ", maintain_status='" + maintain_status + '\'' +
                    ", remark='" + remark + '\'' +
                    ", cname='" + cname + '\'' +
                    ", member_no='" + member_no + '\'' +
                    ", phone_no='" + phone_no + '\'' +
                    ", gender='" + gender + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", height='" + height + '\'' +
                    ", weight='" + weight + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", reg_date='" + reg_date + '\'' +
                    ", password='" + password + '\'' +
                    ", default_branch='" + default_branch + '\'' +
                    ", zone_code='" + zone_code + '\'' +
                    ", platform='" + platform + '\'' +
                    ", avatar_path='" + avatar_path + '\'' +
                    ", self_avatar_path='" + self_avatar_path + '\'' +
                    ", reg_id='" + reg_id + '\'' +
                    ", identity_card='" + identity_card + '\'' +
                    ", login_name='" + login_name + '\'' +
                    ", terminal='" + terminal + '\'' +
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
                    ", branchRanking=" + branchRanking +
                    ", appRanking='" + appRanking + '\'' +
                    ", branchScore=" + branchScore +
                    ", appScore='" + appScore + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPerson_id() {
            return person_id;
        }

        public void setPerson_id(String person_id) {
            this.person_id = person_id;
        }

        public String getAdmin_emp_id() {
            return admin_emp_id;
        }

        public void setAdmin_emp_id(String admin_emp_id) {
            this.admin_emp_id = admin_emp_id;
        }

        public String getTeach_emp_id() {
            return teach_emp_id;
        }

        public void setTeach_emp_id(String teach_emp_id) {
            this.teach_emp_id = teach_emp_id;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getGuest_recom() {
            return guest_recom;
        }

        public void setGuest_recom(String guest_recom) {
            this.guest_recom = guest_recom;
        }

        public String getMember_recom() {
            return member_recom;
        }

        public void setMember_recom(String member_recom) {
            this.member_recom = member_recom;
        }

        public String getGuest_aware_way() {
            return guest_aware_way;
        }

        public void setGuest_aware_way(String guest_aware_way) {
            this.guest_aware_way = guest_aware_way;
        }

        public String getMember_aware_way() {
            return member_aware_way;
        }

        public void setMember_aware_way(String member_aware_way) {
            this.member_aware_way = member_aware_way;
        }

        public String getCard_type() {
            return card_type;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
        }

        public String getTotal_money() {
            return total_money;
        }

        public void setTotal_money(String total_money) {
            this.total_money = total_money;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getFinal_admin_id() {
            return final_admin_id;
        }

        public void setFinal_admin_id(String final_admin_id) {
            this.final_admin_id = final_admin_id;
        }

        public String getFinal_teach_id() {
            return final_teach_id;
        }

        public void setFinal_teach_id(String final_teach_id) {
            this.final_teach_id = final_teach_id;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public String getMaintain_status() {
            return maintain_status;
        }

        public void setMaintain_status(String maintain_status) {
            this.maintain_status = maintain_status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getReg_date() {
            return reg_date;
        }

        public void setReg_date(String reg_date) {
            this.reg_date = reg_date;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDefault_branch() {
            return default_branch;
        }

        public void setDefault_branch(String default_branch) {
            this.default_branch = default_branch;
        }

        public String getZone_code() {
            return zone_code;
        }

        public void setZone_code(String zone_code) {
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

        public String getTerminal() {
            return terminal;
        }

        public void setTerminal(String terminal) {
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

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getM_consume() {
            return m_consume;
        }

        public void setM_consume(String m_consume) {
            this.m_consume = m_consume;
        }

        public String getProject_ids() {
            return project_ids;
        }

        public void setProject_ids(String project_ids) {
            this.project_ids = project_ids;
        }

        public String getChronic_ids() {
            return chronic_ids;
        }

        public void setChronic_ids(String chronic_ids) {
            this.chronic_ids = chronic_ids;
        }

        public String getTarget_ids() {
            return target_ids;
        }

        public void setTarget_ids(String target_ids) {
            this.target_ids = target_ids;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getSelf_avatar_url() {
            return self_avatar_url;
        }

        public void setSelf_avatar_url(String self_avatar_url) {
            this.self_avatar_url = self_avatar_url;
        }

        public String getChonicList() {
            return chonicList;
        }

        public void setChonicList(String chonicList) {
            this.chonicList = chonicList;
        }

        public String getProjectList() {
            return projectList;
        }

        public void setProjectList(String projectList) {
            this.projectList = projectList;
        }

        public String getTargetList() {
            return targetList;
        }

        public void setTargetList(String targetList) {
            this.targetList = targetList;
        }

        public String getGuest_way() {
            return guest_way;
        }

        public void setGuest_way(String guest_way) {
            this.guest_way = guest_way;
        }

        public String getAdmin_name() {
            return admin_name;
        }

        public void setAdmin_name(String admin_name) {
            this.admin_name = admin_name;
        }

        public String getTeach_name() {
            return teach_name;
        }

        public void setTeach_name(String teach_name) {
            this.teach_name = teach_name;
        }

        public String getFinal_admin_name() {
            return final_admin_name;
        }

        public void setFinal_admin_name(String final_admin_name) {
            this.final_admin_name = final_admin_name;
        }

        public String getFinal_teach_name() {
            return final_teach_name;
        }

        public void setFinal_teach_name(String final_teach_name) {
            this.final_teach_name = final_teach_name;
        }

        public int getBranchRanking() {
            return branchRanking;
        }

        public void setBranchRanking(int branchRanking) {
            this.branchRanking = branchRanking;
        }

        public String getAppRanking() {
            return appRanking;
        }

        public void setAppRanking(String appRanking) {
            this.appRanking = appRanking;
        }

        public int getBranchScore() {
            return branchScore;
        }

        public void setBranchScore(int branchScore) {
            this.branchScore = branchScore;
        }

        public String getAppScore() {
            return appScore;
        }

        public void setAppScore(String appScore) {
            this.appScore = appScore;
        }
    }
}