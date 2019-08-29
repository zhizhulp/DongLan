package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/4/2.
 */

public class CourseMemberBean implements Serializable {


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

    public class Content {

        private int branch_id;
        private int count;
        private int course_category;
        private int course_type_id;
        private String course_type_name;
        private String delete_remark;
        private String employee_avatar_path;
        private int employee_id;
        private String employee_name;
        private String end_date;
        private int id;
        private int member_id;
        private String member_name;
        private String member_no;
        private String nick_name;
        private int person_id;
        private int price;
        private int real_price;
        private String self_avatar_path;
        private String start_date;
        private int surplus_count;
        private int time_length;
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

        public void setEmployee_avatar_path(String employee_avatar_path) {
            this.employee_avatar_path = employee_avatar_path;
        }
        public String getEmployee_avatar_path() {
            return employee_avatar_path;
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

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }
        public String getEnd_date() {
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

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }
        public String getNick_name() {
            return nick_name;
        }

        public void setPerson_id(int person_id) {
            this.person_id = person_id;
        }
        public int getPerson_id() {
            return person_id;
        }

        public void setPrice(int price) {
            this.price = price;
        }
        public int getPrice() {
            return price;
        }

        public void setReal_price(int real_price) {
            this.real_price = real_price;
        }
        public int getReal_price() {
            return real_price;
        }

        public void setSelf_avatar_path(String self_avatar_path) {
            this.self_avatar_path = self_avatar_path;
        }
        public String getSelf_avatar_path() {
            return self_avatar_path;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }
        public String getStart_date() {
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
}
