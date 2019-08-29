package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2018/5/21.
 */

public class CourseFindPerson {

    /**
     * code : 0
     * data : [{"branch_id":0,"branch_name":"string","cancel_date":"2018-05-21T09:06:32.716Z","course_type_id":0,"course_type_name":"string","create_date":"2018-05-21T09:06:32.716Z","date":"2018-05-21T09:06:32.716Z","delete_remark":"string","end_time":0,"evaluate_id":0,"group_course_id":0,"id":0,"member_id":0,"member_name":"string","member_no":"string","nick_name":"string","operater_id":0,"person_id":0,"self_avatar_path":"string","start_time":0,"status":"string"}]
     * errorMsg : string
     * success : false
     */

    private String code;
    private String errorMsg;
    private boolean success;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * branch_id : 0
         * branch_name : string
         * cancel_date : 2018-05-21T09:06:32.716Z
         * course_type_id : 0
         * course_type_name : string
         * create_date : 2018-05-21T09:06:32.716Z
         * date : 2018-05-21T09:06:32.716Z
         * delete_remark : string
         * end_time : 0
         * evaluate_id : 0
         * group_course_id : 0
         * id : 0
         * member_id : 0
         * member_name : string
         * member_no : string
         * nick_name : string
         * operater_id : 0
         * person_id : 0
         * self_avatar_path : string
         * start_time : 0
         * status : string
         */

        private String branch_id;
        private String branch_name;
        private String cancel_date;
        private String course_type_id;
        private String course_type_name;
        private String create_date;
        private String date;
        private String delete_remark;
        private String end_time;
        private String evaluate_id;
        private String group_course_id;
        private String id;
        private String member_id;
        private String member_name;
        private String member_no;
        private String nick_name;
        private String operater_id;
        private String person_id;
        private String self_avatar_path;
        private String start_time;
        private String status;

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

        public String getCancel_date() {
            return cancel_date;
        }

        public void setCancel_date(String cancel_date) {
            this.cancel_date = cancel_date;
        }

        public String getCourse_type_id() {
            return course_type_id;
        }

        public void setCourse_type_id(String course_type_id) {
            this.course_type_id = course_type_id;
        }

        public String getCourse_type_name() {
            return course_type_name;
        }

        public void setCourse_type_name(String course_type_name) {
            this.course_type_name = course_type_name;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDelete_remark() {
            return delete_remark;
        }

        public void setDelete_remark(String delete_remark) {
            this.delete_remark = delete_remark;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getEvaluate_id() {
            return evaluate_id;
        }

        public void setEvaluate_id(String evaluate_id) {
            this.evaluate_id = evaluate_id;
        }

        public String getGroup_course_id() {
            return group_course_id;
        }

        public void setGroup_course_id(String group_course_id) {
            this.group_course_id = group_course_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getOperater_id() {
            return operater_id;
        }

        public void setOperater_id(String operater_id) {
            this.operater_id = operater_id;
        }

        public String getPerson_id() {
            return person_id;
        }

        public void setPerson_id(String person_id) {
            this.person_id = person_id;
        }

        public String getSelf_avatar_path() {
            return self_avatar_path;
        }

        public void setSelf_avatar_path(String self_avatar_path) {
            this.self_avatar_path = self_avatar_path;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
