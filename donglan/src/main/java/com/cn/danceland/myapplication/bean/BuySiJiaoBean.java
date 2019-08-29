package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/2/24.
 */

public class BuySiJiaoBean implements Serializable {

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

        private List<Content> content;
        private int number;
        private int size;
        private int totalElements;
        private int numberOfElements;
        private boolean last;
        private int totalPages;
        public void setContent(List<Content> content) {
            this.content = content;
        }
        public List<Content> getContent() {
            return content;
        }

        public void setNumber(int number) {
            this.number = number;
        }
        public int getNumber() {
            return number;
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

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }
        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setLast(boolean last) {
            this.last = last;
        }
        public boolean getLast() {
            return last;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
        public int getTotalPages() {
            return totalPages;
        }

    }

    public class Content implements Serializable{

        private String img_url;
        private int charge_mode;
        private int count;
        private String keep_pro_name;
        private int on_sale;
        private int time_length;
        private String img_path;
        private int id;
        private Float price;
        private int days;
        private int course_category;
        private String name;
        private String course_category_name;
        private int keep_pro;
        private int branch_id;
        private String employees;
        private String branch_name;
        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
        public String getImg_url() {
            return img_url;
        }

        public void setCharge_mode(int charge_mode) {
            this.charge_mode = charge_mode;
        }
        public int getCharge_mode() {
            return charge_mode;
        }

        public void setCount(int count) {
            this.count = count;
        }
        public int getCount() {
            return count;
        }

        public void setKeep_pro_name(String keep_pro_name) {
            this.keep_pro_name = keep_pro_name;
        }
        public String getKeep_pro_name() {
            return keep_pro_name;
        }

        public void setOn_sale(int on_sale) {
            this.on_sale = on_sale;
        }
        public int getOn_sale() {
            return on_sale;
        }

        public void setTime_length(int time_length) {
            this.time_length = time_length;
        }
        public int getTime_length() {
            return time_length;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }
        public String getImg_path() {
            return img_path;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setPrice(Float price) {
            this.price = price;
        }
        public Float getPrice() {
            return price;
        }

        public void setDays(int days) {
            this.days = days;
        }
        public int getDays() {
            return days;
        }

        public void setCourse_category(int course_category) {
            this.course_category = course_category;
        }
        public int getCourse_category() {
            return course_category;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setCourse_category_name(String course_category_name) {
            this.course_category_name = course_category_name;
        }
        public String getCourse_category_name() {
            return course_category_name;
        }

        public void setKeep_pro(int keep_pro) {
            this.keep_pro = keep_pro;
        }
        public int getKeep_pro() {
            return keep_pro;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }
        public int getBranch_id() {
            return branch_id;
        }

        public void setEmployees(String employees) {
            this.employees = employees;
        }
        public String getEmployees() {
            return employees;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }
        public String getBranch_name() {
            return branch_name;
        }

    }
}
