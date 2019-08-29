package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/6/6.
 */

public class TimeTableConBean implements Serializable {


    /**
     * branch_id : 0
     * branch_name : string
     * condition : {"length":0}
     * course_category : 0
     * course_date : 0
     * course_describe : string
     * course_img_path_1 : string
     * course_img_path_2 : string
     * course_img_path_3 : string
     * course_type_id : 0
     * course_type_name : string
     * cover_img_path : string
     * delete_remark : 0
     * employee_id : 0
     * employee_name : string
     * end_date : 0
     * end_date_gt : 0
     * end_time : 0
     * id : 0
     * level : string
     * max_count : 0
     * member_id : 0
     * order : {}
     * page : 0
     * room_id : 0
     * size : 0
     * start_date : 0
     * start_date_lt : 0
     * start_time : 0
     * week : 0
     */

    private String branch_id;
    private String branch_name;
    private ConditionBean condition;
    private String course_category;
    private String course_date;
    private String course_describe;
    private String course_img_path_1;
    private String course_img_path_2;
    private String course_img_path_3;
    private String course_type_id;
    private String course_type_name;
    private String cover_img_path;
    private String delete_remark;
    private String employee_id;
    private String employee_name;
    private String end_date;
    private String end_date_gt;
    private String end_time;
    private String id;
    private String level;
    private String max_count;
    private String member_id;
    private OrderBean order;
    private String page;
    private String room_id;
    private String size;
    private String start_date;
    private String start_date_lt;
    private String start_time;
    private String week;

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

    public ConditionBean getCondition() {
        return condition;
    }

    public void setCondition(ConditionBean condition) {
        this.condition = condition;
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

    public String getDelete_remark() {
        return delete_remark;
    }

    public void setDelete_remark(String delete_remark) {
        this.delete_remark = delete_remark;
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

    public String getEnd_date_gt() {
        return end_date_gt;
    }

    public void setEnd_date_gt(String end_date_gt) {
        this.end_date_gt = end_date_gt;
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

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_date_lt() {
        return start_date_lt;
    }

    public void setStart_date_lt(String start_date_lt) {
        this.start_date_lt = start_date_lt;
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

    public static class ConditionBean {
        /**
         * length : 0
         */

        private String length;

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }
    }

    public static class OrderBean {
    }
}
