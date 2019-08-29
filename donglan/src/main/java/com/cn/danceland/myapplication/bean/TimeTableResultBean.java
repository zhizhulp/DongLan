package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/6/6.
 */

public class TimeTableResultBean implements Serializable {


    /**
     * code : 0
     * data : [{"appoint_count":0,"branch_id":0,"branch_name":"string","course_category":0,"course_date":0,"course_describe":"string","course_img_path_1":"string","course_img_path_2":"string","course_img_path_3":"string","course_img_url_1":"string","course_img_url_2":"string","course_img_url_3":"string","course_type_id":0,"course_type_name":"string","cover_img_path":"string","cover_img_url":"string","delete_remark":0,"employee_avatar_path":"string","employee_id":0,"employee_name":"string","end_date":0,"end_time":0,"id":0,"level":"string","max_count":0,"room_id":0,"room_name":"string","self_appoint_count":0,"start_date":0,"start_time":0,"week":0}]
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
         * appoint_count : 0
         * branch_id : 0
         * branch_name : string
         * course_category : 0
         * course_date : 0
         * course_describe : string
         * course_img_path_1 : string
         * course_img_path_2 : string
         * course_img_path_3 : string
         * course_img_url_1 : string
         * course_img_url_2 : string
         * course_img_url_3 : string
         * course_type_id : 0
         * course_type_name : string
         * cover_img_path : string
         * cover_img_url : string
         * delete_remark : 0
         * employee_avatar_path : string
         * employee_id : 0
         * employee_name : string
         * end_date : 0
         * end_time : 0
         * id : 0
         * level : string
         * max_count : 0
         * room_id : 0
         * room_name : string
         * self_appoint_count : 0
         * start_date : 0
         * start_time : 0
         * week : 0
         */

        private String appoint_count;
        private String branch_id;
        private String branch_name;
        private String course_category;//1	一对一私教 2	小团体私教 3	免费团课
        private String course_date;
        private String course_describe;
        private String course_img_path_1;
        private String course_img_path_2;
        private String course_img_path_3;
        private String course_img_url_1;
        private String course_img_url_2;
        private String course_img_url_3;
        private String course_type_id;
        private String course_type_name;
        private String cover_img_path;
        private String cover_img_url;
        private String delete_remark;
        private String employee_avatar_path;
        private String employee_id;
        private String employee_name;
        private String end_date;
        private String end_time;
        private String id;
        private String level;
        private String max_count;
        private String room_id;
        private String room_name;
        private String self_appoint_count;
        private String start_date;
        private String start_time;
        private String week;
        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getAppoint_count() {
            return appoint_count;
        }

        public void setAppoint_count(String appoint_count) {
            this.appoint_count = appoint_count;
        }

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

        public String getCourse_category() {
            return course_category;
        }

        public void setCourse_category(String course_category) {
            this.course_category = course_category;
        }

        public String getCourse_date() {
            return course_date;
        }

        public void setCourse_date(String course_date) {
            this.course_date = course_date;
        }

        public String getCourse_describe() {
            return course_describe;
        }

        public void setCourse_describe(String course_describe) {
            this.course_describe = course_describe;
        }

        public String getCourse_img_path_1() {
            return course_img_path_1;
        }

        public void setCourse_img_path_1(String course_img_path_1) {
            this.course_img_path_1 = course_img_path_1;
        }

        public String getCourse_img_path_2() {
            return course_img_path_2;
        }

        public void setCourse_img_path_2(String course_img_path_2) {
            this.course_img_path_2 = course_img_path_2;
        }

        public String getCourse_img_path_3() {
            return course_img_path_3;
        }

        public void setCourse_img_path_3(String course_img_path_3) {
            this.course_img_path_3 = course_img_path_3;
        }

        public String getCourse_img_url_1() {
            return course_img_url_1;
        }

        public void setCourse_img_url_1(String course_img_url_1) {
            this.course_img_url_1 = course_img_url_1;
        }

        public String getCourse_img_url_2() {
            return course_img_url_2;
        }

        public void setCourse_img_url_2(String course_img_url_2) {
            this.course_img_url_2 = course_img_url_2;
        }

        public String getCourse_img_url_3() {
            return course_img_url_3;
        }

        public void setCourse_img_url_3(String course_img_url_3) {
            this.course_img_url_3 = course_img_url_3;
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

        public String getCover_img_path() {
            return cover_img_path;
        }

        public void setCover_img_path(String cover_img_path) {
            this.cover_img_path = cover_img_path;
        }

        public String getCover_img_url() {
            return cover_img_url;
        }

        public void setCover_img_url(String cover_img_url) {
            this.cover_img_url = cover_img_url;
        }

        public String getDelete_remark() {
            return delete_remark;
        }

        public void setDelete_remark(String delete_remark) {
            this.delete_remark = delete_remark;
        }

        public String getEmployee_avatar_path() {
            return employee_avatar_path;
        }

        public void setEmployee_avatar_path(String employee_avatar_path) {
            this.employee_avatar_path = employee_avatar_path;
        }

        public String getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_id(String employee_id) {
            this.employee_id = employee_id;
        }

        public String getEmployee_name() {
            return employee_name;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getMax_count() {
            return max_count;
        }

        public void setMax_count(String max_count) {
            this.max_count = max_count;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getSelf_appoint_count() {
            return self_appoint_count;
        }

        public void setSelf_appoint_count(String self_appoint_count) {
            this.self_appoint_count = self_appoint_count;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "appoint_count='" + appoint_count + '\'' +
                    ", branch_id='" + branch_id + '\'' +
                    ", branch_name='" + branch_name + '\'' +
                    ", course_category='" + course_category + '\'' +
                    ", course_date='" + course_date + '\'' +
                    ", course_describe='" + course_describe + '\'' +
                    ", course_img_path_1='" + course_img_path_1 + '\'' +
                    ", course_img_path_2='" + course_img_path_2 + '\'' +
                    ", course_img_path_3='" + course_img_path_3 + '\'' +
                    ", course_img_url_1='" + course_img_url_1 + '\'' +
                    ", course_img_url_2='" + course_img_url_2 + '\'' +
                    ", course_img_url_3='" + course_img_url_3 + '\'' +
                    ", course_type_id='" + course_type_id + '\'' +
                    ", course_type_name='" + course_type_name + '\'' +
                    ", cover_img_path='" + cover_img_path + '\'' +
                    ", cover_img_url='" + cover_img_url + '\'' +
                    ", delete_remark='" + delete_remark + '\'' +
                    ", employee_avatar_path='" + employee_avatar_path + '\'' +
                    ", employee_id='" + employee_id + '\'' +
                    ", employee_name='" + employee_name + '\'' +
                    ", end_date='" + end_date + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", id='" + id + '\'' +
                    ", level='" + level + '\'' +
                    ", max_count='" + max_count + '\'' +
                    ", room_id='" + room_id + '\'' +
                    ", room_name='" + room_name + '\'' +
                    ", self_appoint_count='" + self_appoint_count + '\'' +
                    ", start_date='" + start_date + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", week='" + week + '\'' +
                    ", color='" + color + '\'' +
                    '}';
        }
    }
}
