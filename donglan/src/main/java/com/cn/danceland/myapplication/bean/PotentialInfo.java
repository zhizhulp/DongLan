package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shy on 2018/1/9 14:33
 * Email:644563767@qq.com
 * /潜客资料
 */


public class PotentialInfo implements Serializable {


    public String id;
    public String person_id;
    public String admin_emp_id;
    public String teach_emp_id;
    public String branch_id;
    public String enabled;
    public String auth;
    public String admin_name;
    public String teach_name;
    public String type;
    public String create_time;
    public String height;
    public String weight;
    public String birthday;
    public String reg_date;
    public String password;
    public String platform;
    public String avatar_path;
    public String self_avatar_path;
    public String reg_id;
    public String identity_card;
    public String login_name;
    public String terminal;
    public String department_id;
    public String emergency_name;
    public String certificate_type;
    public String sys_role;
    public String emergency_phone;
    public String avatar_url;
    public String self_avatar_url;


 //   private String branch_id;
 public String branch_name;
    public List<String> chronic_ids;
    public String nationality;//国籍
    public List<String> project_ids;
    public List<String> target_ids;
    public String zone_code;


    public String guest_recom;// 潜客推荐人
    public String member_recom;// 会员推荐人
    public String guest_aware_way;// 潜客了解途径
    public String    guest_way;
    public String member_aware_way;// 会员了解途径
    public String target;// 健身目标
    public String card_type;// 意向卡型
    public String total_money;// 累计会费
    public String final_admin_id;// 终身会籍
    public String final_teach_id;// 终身教练
    public String final_admin_name;// 终身会籍名
    public String final_teach_name;// 终身教练名
    public String last_time;// 最近访问日期
    public String maintain_status;// 访问状态
    public String remark;// 备注
    /* 以下为为员信息 */
    public String cname;// 姓名
    public String member_no;// 会员编号
    public String phone_no;// 手机号
    public String gender;// 性别
    public String nick_name;// 昵称
    public String default_branch;// 默认门店

    public String weichat_no;// 微信号
    public String medical_history;// 慢性病史
    public String mail;// 邮箱
    public String follow_level;// 关注指数
    public String fitness_level;// 健身指数
    public String address;// 详细住址
    public String company;// 单位
    private String admin_mark;//会籍备注
    private String teach_mark;//教练备注

    private List<ChonicList> chonicList;
    private int push_setting;
    private List<ProjectList> projectList;
    private List<TargetList> targetList;

    public int getPush_setting() {
        return push_setting;
    }

    public void setPush_setting(int push_setting) {
        this.push_setting = push_setting;
    }

    @Override
    public String toString() {
        return "PotentialInfo{" +
                "id='" + id + '\'' +
                ", person_id='" + person_id + '\'' +
                ", admin_emp_id='" + admin_emp_id + '\'' +
                ", teach_emp_id='" + teach_emp_id + '\'' +
                ", branch_id='" + branch_id + '\'' +
                ", enabled='" + enabled + '\'' +
                ", auth='" + auth + '\'' +
                ", admin_name='" + admin_name + '\'' +
                ", teach_name='" + teach_name + '\'' +
                ", type='" + type + '\'' +
                ", create_time='" + create_time + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", birthday='" + birthday + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", password='" + password + '\'' +
                ", platform='" + platform + '\'' +
                ", avatar_path='" + avatar_path + '\'' +
                ", self_avatar_path='" + self_avatar_path + '\'' +
                ", reg_id='" + reg_id + '\'' +
                ", identity_card='" + identity_card + '\'' +
                ", login_name='" + login_name + '\'' +
                ", terminal='" + terminal + '\'' +
                ", department_id='" + department_id + '\'' +
                ", emergency_name='" + emergency_name + '\'' +
                ", certificate_type='" + certificate_type + '\'' +
                ", sys_role='" + sys_role + '\'' +
                ", emergency_phone='" + emergency_phone + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", self_avatar_url='" + self_avatar_url + '\'' +
                ", branch_name='" + branch_name + '\'' +
                ", chronic_ids=" + chronic_ids +
                ", nationality='" + nationality + '\'' +
                ", project_ids=" + project_ids +
                ", target_ids=" + target_ids +
                ", zone_code='" + zone_code + '\'' +
                ", guest_recom='" + guest_recom + '\'' +
                ", member_recom='" + member_recom + '\'' +
                ", guest_aware_way='" + guest_aware_way + '\'' +
                ", guest_way='" + guest_way + '\'' +
                ", member_aware_way='" + member_aware_way + '\'' +
                ", target='" + target + '\'' +
                ", card_type='" + card_type + '\'' +
                ", total_money='" + total_money + '\'' +
                ", final_admin_id='" + final_admin_id + '\'' +
                ", final_teach_id='" + final_teach_id + '\'' +
                ", final_admin_name='" + final_admin_name + '\'' +
                ", final_teach_name='" + final_teach_name + '\'' +
                ", last_time='" + last_time + '\'' +
                ", maintain_status='" + maintain_status + '\'' +
                ", remark='" + remark + '\'' +
                ", cname='" + cname + '\'' +
                ", member_no='" + member_no + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", gender='" + gender + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", default_branch='" + default_branch + '\'' +
                ", weichat_no='" + weichat_no + '\'' +
                ", medical_history='" + medical_history + '\'' +
                ", mail='" + mail + '\'' +
                ", follow_level='" + follow_level + '\'' +
                ", fitness_level='" + fitness_level + '\'' +
                ", address='" + address + '\'' +
                ", company='" + company + '\'' +
                ", admin_mark='" + admin_mark + '\'' +
                ", teach_mark='" + teach_mark + '\'' +
                ", chonicList=" + chonicList +
                ", push_setting='" + push_setting + '\'' +
                ", projectList=" + projectList +
                ", targetList=" + targetList +
                '}';
    }

    public String getGuest_way() {
        return guest_way;
    }

    public void setGuest_way(String guest_way) {
        this.guest_way = guest_way;
    }

    public String getAdmin_mark() {
        return admin_mark;
    }

    public void setAdmin_mark(String admin_mark) {
        this.admin_mark = admin_mark;
    }

    public String getTeach_mark() {
        return teach_mark;
    }

    public void setTeach_mark(String teach_mark) {
        this.teach_mark = teach_mark;
    }

    public List<ChonicList> getChonicList() {
        return chonicList;
    }

    public void setChonicList(List<ChonicList> chonicList) {
        this.chonicList = chonicList;
    }

    public List<ProjectList> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectList> projectList) {
        this.projectList = projectList;
    }

    public List<TargetList> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<TargetList> targetList) {
        this.targetList = targetList;
    }

    public class TargetList implements Serializable{

        private String id;
        private String data_key;
        private String data_value;
        private String type_code;
        private String type_name;

        @Override
        public String toString() {
            return "ChonicList{" +
                    "id='" + id + '\'' +
                    ", data_key='" + data_key + '\'' +
                    ", data_value='" + data_value + '\'' +
                    ", type_code='" + type_code + '\'' +
                    ", type_name='" + type_name + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getData_key() {
            return data_key;
        }

        public void setData_key(String data_key) {
            this.data_key = data_key;
        }

        public String getData_value() {
            return data_value;
        }

        public void setData_value(String data_value) {
            this.data_value = data_value;
        }

        public String getType_code() {
            return type_code;
        }

        public void setType_code(String type_code) {
            this.type_code = type_code;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
    public class ProjectList implements Serializable {

        private String id;
        private String data_key;
        private String data_value;
        private String type_code;
        private String type_name;

        @Override
        public String toString() {
            return "ChonicList{" +
                    "id='" + id + '\'' +
                    ", data_key='" + data_key + '\'' +
                    ", data_value='" + data_value + '\'' +
                    ", type_code='" + type_code + '\'' +
                    ", type_name='" + type_name + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getData_key() {
            return data_key;
        }

        public void setData_key(String data_key) {
            this.data_key = data_key;
        }

        public String getData_value() {
            return data_value;
        }

        public void setData_value(String data_value) {
            this.data_value = data_value;
        }

        public String getType_code() {
            return type_code;
        }

        public void setType_code(String type_code) {
            this.type_code = type_code;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
    public class ChonicList implements Serializable{

        private String id;
        private String data_key;
        private String data_value;
        private String type_code;
        private String type_name;

        @Override
        public String toString() {
            return "ChonicList{" +
                    "id='" + id + '\'' +
                    ", data_key='" + data_key + '\'' +
                    ", data_value='" + data_value + '\'' +
                    ", type_code='" + type_code + '\'' +
                    ", type_name='" + type_name + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getData_key() {
            return data_key;
        }

        public void setData_key(String data_key) {
            this.data_key = data_key;
        }

        public String getData_value() {
            return data_value;
        }

        public void setData_value(String data_value) {
            this.data_value = data_value;
        }

        public String getType_code() {
            return type_code;
        }

        public void setType_code(String type_code) {
            this.type_code = type_code;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
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

    public String getEmergency_name() {
        return emergency_name;
    }

    public void setEmergency_name(String emergency_name) {
        this.emergency_name = emergency_name;
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

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public List<String> getChronic_ids() {
        return chronic_ids;
    }

    public void setChronic_ids(List<String> chronic_ids) {
        this.chronic_ids = chronic_ids;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<String> getProject_ids() {
        return project_ids;
    }

    public void setProject_ids(List<String> project_ids) {
        this.project_ids = project_ids;
    }

    public List<String> getTarget_ids() {
        return target_ids;
    }

    public void setTarget_ids(List<String> target_ids) {
        this.target_ids = target_ids;
    }

    public String getZone_code() {
        return zone_code;
    }

    public void setZone_code(String zone_code) {
        this.zone_code = zone_code;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public String getDefault_branch() {
        return default_branch;
    }

    public void setDefault_branch(String default_branch) {
        this.default_branch = default_branch;
    }

    public String getWeichat_no() {
        return weichat_no;
    }

    public void setWeichat_no(String weichat_no) {
        this.weichat_no = weichat_no;
    }

    public String getMedical_history() {
        return medical_history;
    }

    public void setMedical_history(String medical_history) {
        this.medical_history = medical_history;
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
}
