package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/3/22.
 */

public class JiaoLianCourseBean implements Serializable {

    private int code;
    private Data data;
    private String errorMsg;
    private boolean success;

    @Override
    public String toString() {
        return "JiaoLianCourseBean{" +
                "code=" + code +
                ", data=" + data +
                ", errorMsg='" + errorMsg + '\'' +
                ", success=" + success +
                '}';
    }

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

    public class Content implements Serializable {
        private String course_type_describe;
        private int branch_id;
        private int count;
        private int course_category;
        private int course_type_id;
        private String course_type_name;
        private String delete_remark;
        private int employee_id;
        private String employee_name;
        private Long end_date;
        private int id;
        private int member_id;
        private String member_name;
        private String member_no;
        private float price;
        private float real_price;
        private Long start_date;
        private int surplus_count;
        private int time_length;
        private String self_avatar_path;
        private String img_url;
private String room_id;

        @Override
        public String toString() {
            return "Content{" +
                    "course_type_describe='" + course_type_describe + '\'' +
                    ", branch_id=" + branch_id +
                    ", count=" + count +
                    ", course_category=" + course_category +
                    ", course_type_id=" + course_type_id +
                    ", course_type_name='" + course_type_name + '\'' +
                    ", delete_remark='" + delete_remark + '\'' +
                    ", employee_id=" + employee_id +
                    ", employee_name='" + employee_name + '\'' +
                    ", end_date=" + end_date +
                    ", id=" + id +
                    ", member_id=" + member_id +
                    ", member_name='" + member_name + '\'' +
                    ", member_no='" + member_no + '\'' +
                    ", price=" + price +
                    ", real_price=" + real_price +
                    ", start_date=" + start_date +
                    ", surplus_count=" + surplus_count +
                    ", time_length=" + time_length +
                    ", self_avatar_path='" + self_avatar_path + '\'' +
                    ", img_url='" + img_url + '\'' +
                    ", room_id='" + room_id + '\'' +
                    '}';
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
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

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

        public void setCourse_category(int course_category) {
            this.course_category = course_category;
        }

        public int getCourse_category() {
            return course_category;
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

        public void setDelete_remark(String delete_remark) {
            this.delete_remark = delete_remark;
        }

        public String getDelete_remark() {
            return delete_remark;
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

        public void setEnd_date(Long end_date) {
            this.end_date = end_date;
        }

        public Long getEnd_date() {
            return end_date;
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

        public void setPrice(float price) {
            this.price = price;
        }

        public float getPrice() {
            return price;
        }

        public void setReal_price(float real_price) {
            this.real_price = real_price;
        }

        public float getReal_price() {
            return real_price;
        }

        public void setStart_date(Long start_date) {
            this.start_date = start_date;
        }

        public Long getStart_date() {
            return start_date;
        }

        public void setSurplus_count(int surplus_count) {
            this.surplus_count = surplus_count;
        }

        public int getSurplus_count() {
            return surplus_count;
        }

        public void setTime_length(int time_length) {
            this.time_length = time_length;
        }

        public int getTime_length() {
            return time_length;
        }

    }


    public class Data implements Serializable {

        private List<Content> content;
        private boolean last;
        private int number;
        private int numberOfElements;
        private int size;
        private int totalElements;
        private int totalPages;

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public List<Content> getContent() {
            return content;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public boolean getLast() {
            return last;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalPages() {
            return totalPages;
        }

    }

}
