package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/3/9.
 */

public class TuanKeBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private Data data;
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

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }


    public class Data {

        private int id;
        private int branch_id;
        private String branch_name;
        private int course_type_id;
        private String course_type_name;
        private int employee_id;
        private String employee_name;
        private long start_date;
        private long end_date;
        private int week;
        private int start_time;
        private int end_time;
        private int max_count;
        private String room_id;
        private String level;
        private String cover_img_path;
        private String course_img_path_1;
        private String course_img_path_2;
        private String course_img_path_3;
        private String course_describe;
        private String delete_remark;
        private String course_date;
        private int appoint_count;
        private String cover_img_url;
        private String course_img_url_1;
        private String course_img_url_2;
        private String course_img_url_3;
        private int course_category;
        private String room_name;
        private int self_appoint_count;
        private String employee_avatar_path;

        public int getSelf_appoint_count() {
            return self_appoint_count;
        }

        public void setSelf_appoint_count(int self_appoint_count) {
            this.self_appoint_count = self_appoint_count;
        }

        public String getEmployee_avatar_path() {
            return employee_avatar_path;
        }

        public void setEmployee_avatar_path(String employee_avatar_path) {
            this.employee_avatar_path = employee_avatar_path;
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

        public void setEmployee_id(int employee_id) {
            this.employee_id = employee_id;
        }
        public int getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }
        public String getEmployee_name() {
            return employee_name;
        }

        public void setStart_date(long start_date) {
            this.start_date = start_date;
        }
        public long getStart_date() {
            return start_date;
        }

        public void setEnd_date(long end_date) {
            this.end_date = end_date;
        }
        public long getEnd_date() {
            return end_date;
        }

        public void setWeek(int week) {
            this.week = week;
        }
        public int getWeek() {
            return week;
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

        public void setMax_count(int max_count) {
            this.max_count = max_count;
        }
        public int getMax_count() {
            return max_count;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }
        public String getRoom_id() {
            return room_id;
        }

        public void setLevel(String level) {
            this.level = level;
        }
        public String getLevel() {
            return level;
        }

        public void setCover_img_path(String cover_img_path) {
            this.cover_img_path = cover_img_path;
        }
        public String getCover_img_path() {
            return cover_img_path;
        }

        public void setCourse_img_path_1(String course_img_path_1) {
            this.course_img_path_1 = course_img_path_1;
        }
        public String getCourse_img_path_1() {
            return course_img_path_1;
        }

        public void setCourse_img_path_2(String course_img_path_2) {
            this.course_img_path_2 = course_img_path_2;
        }
        public String getCourse_img_path_2() {
            return course_img_path_2;
        }

        public void setCourse_img_path_3(String course_img_path_3) {
            this.course_img_path_3 = course_img_path_3;
        }
        public String getCourse_img_path_3() {
            return course_img_path_3;
        }

        public void setCourse_describe(String course_describe) {
            this.course_describe = course_describe;
        }
        public String getCourse_describe() {
            return course_describe;
        }

        public void setDelete_remark(String delete_remark) {
            this.delete_remark = delete_remark;
        }
        public String getDelete_remark() {
            return delete_remark;
        }

        public void setCourse_date(String course_date) {
            this.course_date = course_date;
        }
        public String getCourse_date() {
            return course_date;
        }

        public void setAppoint_count(int appoint_count) {
            this.appoint_count = appoint_count;
        }
        public int getAppoint_count() {
            return appoint_count;
        }

        public void setCover_img_url(String cover_img_url) {
            this.cover_img_url = cover_img_url;
        }
        public String getCover_img_url() {
            return cover_img_url;
        }

        public void setCourse_img_url_1(String course_img_url_1) {
            this.course_img_url_1 = course_img_url_1;
        }
        public String getCourse_img_url_1() {
            return course_img_url_1;
        }

        public void setCourse_img_url_2(String course_img_url_2) {
            this.course_img_url_2 = course_img_url_2;
        }
        public String getCourse_img_url_2() {
            return course_img_url_2;
        }

        public void setCourse_img_url_3(String course_img_url_3) {
            this.course_img_url_3 = course_img_url_3;
        }
        public String getCourse_img_url_3() {
            return course_img_url_3;
        }

        public void setCourse_category(int course_category) {
            this.course_category = course_category;
        }
        public int getCourse_category() {
            return course_category;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }
        public String getRoom_name() {
            return room_name;
        }

    }

}
