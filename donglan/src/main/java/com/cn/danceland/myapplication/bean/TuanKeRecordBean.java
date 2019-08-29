package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/3/12.
 */

public class TuanKeRecordBean implements Serializable {

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

    public class Data {

        private int id;
        private int branch_id;
        private String branch_name;
        private int group_course_id;
        private int course_type_id;
        private int member_id;
        private String member_no;
        private long date;
        private long create_date;
        private String cancel_date;
        private int status;
        private String operater_id;
        private int delete_remark;
        private String course_type_name;
        private int start_time;
        private int end_time;
        private String room_name;
        private String member_name;

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

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

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }
        public String getBranch_name() {
            return branch_name;
        }

        public void setGroup_course_id(int group_course_id) {
            this.group_course_id = group_course_id;
        }
        public int getGroup_course_id() {
            return group_course_id;
        }

        public void setCourse_type_id(int course_type_id) {
            this.course_type_id = course_type_id;
        }
        public int getCourse_type_id() {
            return course_type_id;
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

        public void setDate(long date) {
            this.date = date;
        }
        public long getDate() {
            return date;
        }

        public void setCreate_date(long create_date) {
            this.create_date = create_date;
        }
        public long getCreate_date() {
            return create_date;
        }

        public void setCancel_date(String cancel_date) {
            this.cancel_date = cancel_date;
        }
        public String getCancel_date() {
            return cancel_date;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setOperater_id(String operater_id) {
            this.operater_id = operater_id;
        }
        public String getOperater_id() {
            return operater_id;
        }

        public void setDelete_remark(int delete_remark) {
            this.delete_remark = delete_remark;
        }
        public int getDelete_remark() {
            return delete_remark;
        }

        public void setCourse_type_name(String course_type_name) {
            this.course_type_name = course_type_name;
        }
        public String getCourse_type_name() {
            return course_type_name;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }
        public int getStart_time() {
            return start_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }
        public int getEnd_time() {
            return end_time;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }
        public String getMember_name() {
            return member_name;
        }

    }

}
