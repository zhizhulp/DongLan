package com.cn.danceland.myapplication.bean;

/**
 * Created by feng on 2018/4/27.
 */

public class ReportCommitResultBean {

    private int code;
    private Data data;
    private String errorMsg;
    private boolean success;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
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

    public class Data {

        private String body_build;
        private int branch_id;
        private String clean;
        private String course;
        private String date;
        private String door;
        private int emp_id;
        private String emp_name;
        private String group_course;
        private int id;
        private String item_placement;
        private String meet;
        private String power;
        private String remark;
        private String sport_device;
        public void setBody_build(String body_build) {
            this.body_build = body_build;
        }
        public String getBody_build() {
            return body_build;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }
        public int getBranch_id() {
            return branch_id;
        }

        public void setClean(String clean) {
            this.clean = clean;
        }
        public String getClean() {
            return clean;
        }

        public void setCourse(String course) {
            this.course = course;
        }
        public String getCourse() {
            return course;
        }

        public void setDate(String date) {
            this.date = date;
        }
        public String getDate() {
            return date;
        }

        public void setDoor(String door) {
            this.door = door;
        }
        public String getDoor() {
            return door;
        }

        public void setEmp_id(int emp_id) {
            this.emp_id = emp_id;
        }
        public int getEmp_id() {
            return emp_id;
        }

        public void setEmp_name(String emp_name) {
            this.emp_name = emp_name;
        }
        public String getEmp_name() {
            return emp_name;
        }

        public void setGroup_course(String group_course) {
            this.group_course = group_course;
        }
        public String getGroup_course() {
            return group_course;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setItem_placement(String item_placement) {
            this.item_placement = item_placement;
        }
        public String getItem_placement() {
            return item_placement;
        }

        public void setMeet(String meet) {
            this.meet = meet;
        }
        public String getMeet() {
            return meet;
        }

        public void setPower(String power) {
            this.power = power;
        }
        public String getPower() {
            return power;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
        public String getRemark() {
            return remark;
        }

        public void setSport_device(String sport_device) {
            this.sport_device = sport_device;
        }
        public String getSport_device() {
            return sport_device;
        }

    }

}
