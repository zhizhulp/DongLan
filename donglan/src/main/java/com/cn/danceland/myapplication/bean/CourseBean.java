package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/3/1.
 */

public class CourseBean implements Serializable {

    private Data data;
    private boolean success;
    private String errorMsg;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
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

    public class Content {

        private String course_type_name;
        private String delete_remark;
        private String course_date;
        private String employee_name;
        private Integer id;
        private long end_date;
        private Integer course_type_id;
        private Integer end_time;
        private Integer max_count;
        private Integer start_time;
        private long start_date;
        private Integer employee_id;
        private Integer branch_id;
        private Integer week;
        private String branch_name;
        public void setCourse_type_name(String course_type_name) {
            this.course_type_name = course_type_name;
        }
        public String getCourse_type_name() {
            return course_type_name;
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

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }
        public String getEmployee_name() {
            return employee_name;
        }

        public void setId(Integer id) {
            this.id = id;
        }
        public Integer getId() {
            return id;
        }

        public void setEnd_date(long end_date) {
            this.end_date = end_date;
        }
        public long getEnd_date() {
            return end_date;
        }

        public void setCourse_type_id(Integer course_type_id) {
            this.course_type_id = course_type_id;
        }
        public Integer getCourse_type_id() {
            return course_type_id;
        }

        public void setEnd_time(Integer end_time) {
            this.end_time = end_time;
        }
        public Integer getEnd_time() {
            return end_time;
        }

        public void setMax_count(Integer max_count) {
            this.max_count = max_count;
        }
        public Integer getMax_count() {
            return max_count;
        }

        public void setStart_time(Integer start_time) {
            this.start_time = start_time;
        }
        public Integer getStart_time() {
            return start_time;
        }

        public void setStart_date(long start_date) {
            this.start_date = start_date;
        }
        public long getStart_date() {
            return start_date;
        }

        public void setEmployee_id(Integer employee_id) {
            this.employee_id = employee_id;
        }
        public Integer getEmployee_id() {
            return employee_id;
        }

        public void setBranch_id(Integer branch_id) {
            this.branch_id = branch_id;
        }
        public Integer getBranch_id() {
            return branch_id;
        }

        public void setWeek(Integer week) {
            this.week = week;
        }
        public Integer getWeek() {
            return week;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }
        public String getBranch_name() {
            return branch_name;
        }

    }

    public class Data {

        private List<Content> content;
        private Integer number;
        private Integer numberOfElements;
        private boolean last;
        private Integer totalPages;
        private Integer totalElements;
        private Integer size;
        public void setContent(List<Content> content) {
            this.content = content;
        }
        public List<Content> getContent() {
            return content;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }
        public Integer getNumber() {
            return number;
        }

        public void setNumberOfElements(Integer numberOfElements) {
            this.numberOfElements = numberOfElements;
        }
        public Integer getNumberOfElements() {
            return numberOfElements;
        }

        public void setLast(boolean last) {
            this.last = last;
        }
        public boolean getLast() {
            return last;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }
        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalElements(Integer totalElements) {
            this.totalElements = totalElements;
        }
        public Integer getTotalElements() {
            return totalElements;
        }

        public void setSize(Integer size) {
            this.size = size;
        }
        public Integer getSize() {
            return size;
        }

    }

}
