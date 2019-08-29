package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/3/5.
 */

public class TimeAxisCon implements Serializable {

    private Integer branch_id;
    private String branch_name;
    private Condition condition;
    private Long course_date;
    private Integer course_type_id;
    private String course_type_name;
    private Integer delete_remark;
    private Integer employee_id;
    private String employee_name;
    private Integer end_date;
    private Integer end_time;
    private Integer id;
    private Integer max_count;
    private Order order;
    private Integer page;
    private Integer pageCount;
    private Integer rowCount;
    private Integer size;
    private Integer start_date;
    private Integer start_time;
    private String week;
    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }
    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
    public String getBranch_name() {
        return branch_name;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    public Condition getCondition() {
        return condition;
    }

    public void setCourse_date(Long course_date) {
        this.course_date = course_date;
    }
    public Long getCourse_date() {
        return course_date;
    }

    public void setCourse_type_id(Integer course_type_id) {
        this.course_type_id = course_type_id;
    }
    public Integer getCourse_type_id() {
        return course_type_id;
    }

    public void setCourse_type_name(String course_type_name) {
        this.course_type_name = course_type_name;
    }
    public String getCourse_type_name() {
        return course_type_name;
    }

    public void setDelete_remark(Integer delete_remark) {
        this.delete_remark = delete_remark;
    }
    public Integer getDelete_remark() {
        return delete_remark;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }
    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
    public String getEmployee_name() {
        return employee_name;
    }

    public void setEnd_date(Integer end_date) {
        this.end_date = end_date;
    }
    public Integer getEnd_date() {
        return end_date;
    }

    public void setEnd_time(Integer end_time) {
        this.end_time = end_time;
    }
    public Integer getEnd_time() {
        return end_time;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setMax_count(Integer max_count) {
        this.max_count = max_count;
    }
    public Integer getMax_count() {
        return max_count;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getPage() {
        return page;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
    public Integer getPageCount() {
        return pageCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
    public Integer getRowCount() {
        return rowCount;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getSize() {
        return size;
    }

    public void setStart_date(Integer start_date) {
        this.start_date = start_date;
    }
    public Integer getStart_date() {
        return start_date;
    }

    public void setStart_time(Integer start_time) {
        this.start_time = start_time;
    }
    public Integer getStart_time() {
        return start_time;
    }

    public void setWeek(String week) {
        this.week = week;
    }
    public String getWeek() {
        return week;
    }

    public class Condition {

        private Integer length;
        public void setLength(Integer length) {
            this.length = length;
        }
        public Integer getLength() {
            return length;
        }

    }

    public class Order {

    }
}
