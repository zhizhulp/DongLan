package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2018/3/6.
 */

public class TimeAxisBean  {

    private List<Data> data;
    private boolean success;
    private String errorMsg;
    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

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

    public class Data {

        private String cover_img_url;
        private String course_img_url_1;
        private String course_img_url_2;
        private String course_type_name;
        private String course_img_url_3;
        private String course_describe;
        private String delete_remark;
        private String cover_img_path;
        private Integer appoInteger_count;
        private String course_date;
        private String employee_name;
        private String id;
        private String end_date;
        private String course_img_path_3;
        private String course_type_id;
        private String course_img_path_2;
        private Integer end_time;
        private String max_count;
        private Integer start_time;
        private String course_img_path_1;
        private String start_date;
        private String employee_id;
        private String branch_id;
        private String week;
        private Integer status;
        private String branch_name;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public void setCourse_type_name(String course_type_name) {
            this.course_type_name = course_type_name;
        }
        public String getCourse_type_name() {
            return course_type_name;
        }

        public void setCourse_img_url_3(String course_img_url_3) {
            this.course_img_url_3 = course_img_url_3;
        }
        public String getCourse_img_url_3() {
            return course_img_url_3;
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

        public void setCover_img_path(String cover_img_path) {
            this.cover_img_path = cover_img_path;
        }
        public String getCover_img_path() {
            return cover_img_path;
        }

        public void setAppoInteger_count(Integer appoInteger_count) {
            this.appoInteger_count = appoInteger_count;
        }
        public Integer getAppoInteger_count() {
            return appoInteger_count;
        }

        public void setCourse_date(String course_date) {
            this.course_date = course_date;
        }
        public String getCourse_date() {
            return course_date;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }
        public String getEmployee_name() {
            return employee_name;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }
        public String getEnd_date() {
            return end_date;
        }

        public void setCourse_img_path_3(String course_img_path_3) {
            this.course_img_path_3 = course_img_path_3;
        }
        public String getCourse_img_path_3() {
            return course_img_path_3;
        }

        public void setCourse_type_id(String course_type_id) {
            this.course_type_id = course_type_id;
        }
        public String getCourse_type_id() {
            return course_type_id;
        }

        public void setCourse_img_path_2(String course_img_path_2) {
            this.course_img_path_2 = course_img_path_2;
        }
        public String getCourse_img_path_2() {
            return course_img_path_2;
        }

        public void setEnd_time(Integer end_time) {
            this.end_time = end_time;
        }
        public Integer getEnd_time() {
            return end_time;
        }

        public void setMax_count(String max_count) {
            this.max_count = max_count;
        }
        public String getMax_count() {
            return max_count;
        }

        public void setStart_time(Integer start_time) {
            this.start_time = start_time;
        }
        public Integer getStart_time() {
            return start_time;
        }

        public void setCourse_img_path_1(String course_img_path_1) {
            this.course_img_path_1 = course_img_path_1;
        }
        public String getCourse_img_path_1() {
            return course_img_path_1;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }
        public String getStart_date() {
            return start_date;
        }

        public void setEmployee_id(String employee_id) {
            this.employee_id = employee_id;
        }
        public String getEmployee_id() {
            return employee_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }
        public String getBranch_id() {
            return branch_id;
        }

        public void setWeek(String week) {
            this.week = week;
        }
        public String getWeek() {
            return week;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }
        public String getBranch_name() {
            return branch_name;
        }

    }

}
