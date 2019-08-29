package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/3/2.
 */

public class MyCourseBean implements Serializable {

    private List<Data> data;
    private boolean success;

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

    @Override
    public String toString() {
        return "MyCourseBean{" +
                "data=" + data +
                ", success=" + success +
                '}';
    }

    public class Data implements Serializable {

        private Integer branch_id;
        private Integer count;
        private Integer course_category;
        private Integer course_type_id;
        private String course_type_name;
        private Integer delete_remark;
        private Integer employee_id;
        private String employee_name;
        private long end_date;
        private Integer id;
        private Integer member_id;
        private String member_name;
        private String member_no;
        private Double price;
        private long start_date;
        private Integer surplus_count;
        private Integer time_length;
        private String self_avatar_path;
        private String img_url;
        private String course_type_describe;

        @Override
        public String toString() {
            return "Data{" +
                    "branch_id=" + branch_id +
                    ", count=" + count +
                    ", course_category=" + course_category +
                    ", course_type_id=" + course_type_id +
                    ", course_type_name='" + course_type_name + '\'' +
                    ", delete_remark=" + delete_remark +
                    ", employee_id=" + employee_id +
                    ", employee_name='" + employee_name + '\'' +
                    ", end_date=" + end_date +
                    ", id=" + id +
                    ", member_id=" + member_id +
                    ", member_name='" + member_name + '\'' +
                    ", member_no='" + member_no + '\'' +
                    ", price=" + price +
                    ", start_date=" + start_date +
                    ", surplus_count=" + surplus_count +
                    ", time_length=" + time_length +
                    ", self_avatar_path='" + self_avatar_path + '\'' +
                    ", img_url='" + img_url + '\'' +
                    ", course_type_describe='" + course_type_describe + '\'' +
                    '}';
        }

        public String getCourse_type_describe() {
            return course_type_describe;
        }

        public void setCourse_type_describe(String course_type_describe) {
            this.course_type_describe = course_type_describe;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getSelf_avatar_path() {
            return self_avatar_path;
        }

        public void setSelf_avatar_path(String self_avatar_path) {
            this.self_avatar_path = self_avatar_path;
        }

        public void setBranch_id(Integer branch_id) {
            this.branch_id = branch_id;
        }

        public Integer getBranch_id() {
            return branch_id;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getCount() {
            return count;
        }

        public void setCourse_category(Integer course_category) {
            this.course_category = course_category;
        }

        public Integer getCourse_category() {
            return course_category;
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

        public void setEnd_date(long end_date) {
            this.end_date = end_date;
        }

        public long getEnd_date() {
            return end_date;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public void setMember_id(Integer member_id) {
            this.member_id = member_id;
        }

        public Integer getMember_id() {
            return member_id;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }

        public String getMember_no() {
            return member_no;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getPrice() {
            return price;
        }

        public void setStart_date(long start_date) {
            this.start_date = start_date;
        }

        public long getStart_date() {
            return start_date;
        }

        public String getEmployee_name() {
            return employee_name;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }

        public void setSurplus_count(Integer surplus_count) {
            this.surplus_count = surplus_count;
        }

        public Integer getSurplus_count() {
            return surplus_count;
        }

        public void setTime_length(Integer time_length) {
            this.time_length = time_length;
        }

        public Integer getTime_length() {
            return time_length;
        }

    }

}
