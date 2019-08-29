package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/4/3.
 */

public class GroupRecordBean implements Serializable {

    private int code;
    private List<Data> data;
    private String errorMsg;
    private boolean success;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public static class Data implements Serializable{

        private int branch_id;
        private String branch_name;
        private String cancel_date;
        private int course_type_id;
        private String course_type_name;
        private String create_date;
        private String date;
        private String delete_remark;
        private int evaluate_id;
        private int group_course_id;
        private int id;
        private int member_course_id;
        private int member_id;
        private long start_time;
        private String member_name;
        private String member_no;
        private String nick_name;
        private int operater_id;
        private int person_id;
        private String self_avatar_path;
        private String sign_date;
        private String status;
        private String employee_name;
        private String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "branch_id=" + branch_id +
                    ", branch_name='" + branch_name + '\'' +
                    ", cancel_date='" + cancel_date + '\'' +
                    ", course_type_id=" + course_type_id +
                    ", course_type_name='" + course_type_name + '\'' +
                    ", create_date='" + create_date + '\'' +
                    ", date='" + date + '\'' +
                    ", delete_remark='" + delete_remark + '\'' +
                    ", evaluate_id=" + evaluate_id +
                    ", group_course_id=" + group_course_id +
                    ", id=" + id +
                    ", member_course_id=" + member_course_id +
                    ", member_id=" + member_id +
                    ", start_time=" + start_time +
                    ", member_name='" + member_name + '\'' +
                    ", member_no='" + member_no + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", operater_id=" + operater_id +
                    ", person_id=" + person_id +
                    ", self_avatar_path='" + self_avatar_path + '\'' +
                    ", sign_date='" + sign_date + '\'' +
                    ", status='" + status + '\'' +
                    ", employee_name='" + employee_name + '\'' +
                    ", count='" + count + '\'' +
                    '}';
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public String getEmployee_name() {
            return employee_name;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }
        public int getBranch_id() {
            return branch_id;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }
        public String getBranch_name() {
            return branch_name;
        }

        public void setCancel_date(String cancel_date) {
            this.cancel_date = cancel_date;
        }
        public String getCancel_date() {
            return cancel_date;
        }

        public void setCourse_type_id(int course_type_id) {
            this.course_type_id = course_type_id;
        }
        public int getCourse_type_id() {
            return course_type_id;
        }

        public void setCourse_type_name(String course_type_name) {
            this.course_type_name = course_type_name;
        }
        public String getCourse_type_name() {
            return course_type_name;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }
        public String getCreate_date() {
            return create_date;
        }

        public void setDate(String date) {
            this.date = date;
        }
        public String getDate() {
            return date;
        }

        public void setDelete_remark(String delete_remark) {
            this.delete_remark = delete_remark;
        }
        public String getDelete_remark() {
            return delete_remark;
        }

        public void setEvaluate_id(int evaluate_id) {
            this.evaluate_id = evaluate_id;
        }
        public int getEvaluate_id() {
            return evaluate_id;
        }

        public void setGroup_course_id(int group_course_id) {
            this.group_course_id = group_course_id;
        }
        public int getGroup_course_id() {
            return group_course_id;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setMember_course_id(int member_course_id) {
            this.member_course_id = member_course_id;
        }
        public int getMember_course_id() {
            return member_course_id;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }
        public int getMember_id() {
            return member_id;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }
        public String getMember_name() {
            return member_name;
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

        public void setOperater_id(int operater_id) {
            this.operater_id = operater_id;
        }
        public int getOperater_id() {
            return operater_id;
        }

        public void setPerson_id(int person_id) {
            this.person_id = person_id;
        }
        public int getPerson_id() {
            return person_id;
        }

        public void setSelf_avatar_path(String self_avatar_path) {
            this.self_avatar_path = self_avatar_path;
        }
        public String getSelf_avatar_path() {
            return self_avatar_path;
        }

        public void setSign_date(String sign_date) {
            this.sign_date = sign_date;
        }
        public String getSign_date() {
            return sign_date;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }

    }
}
