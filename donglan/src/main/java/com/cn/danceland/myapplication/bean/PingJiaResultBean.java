package com.cn.danceland.myapplication.bean;

/**
 * Created by feng on 2018/3/28.
 */

public class PingJiaResultBean {

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

        private int branch_id;
        private int bus_id;
        private String content;
        private int course_type_id;
        private Float course_type_score;
        private String create_date;
        private int employee_id;
        private Float employee_score;
        private int id;
        private int member_id;
        private String member_no;
        private int room_id;
        private Float room_score;
        private String type;
        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }
        public int getBranch_id() {
            return branch_id;
        }

        public void setBus_id(int bus_id) {
            this.bus_id = bus_id;
        }
        public int getBus_id() {
            return bus_id;
        }

        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }

        public void setCourse_type_id(int course_type_id) {
            this.course_type_id = course_type_id;
        }
        public int getCourse_type_id() {
            return course_type_id;
        }

        public void setCourse_type_score(Float course_type_score) {
            this.course_type_score = course_type_score;
        }
        public Float getCourse_type_score() {
            return course_type_score;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }
        public String getCreate_date() {
            return create_date;
        }

        public void setEmployee_id(int employee_id) {
            this.employee_id = employee_id;
        }
        public int getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_score(Float employee_score) {
            this.employee_score = employee_score;
        }
        public Float getEmployee_score() {
            return employee_score;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
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

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }
        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_score(Float room_score) {
            this.room_score = room_score;
        }
        public Float getRoom_score() {
            return room_score;
        }

        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

    }


}
