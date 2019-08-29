package com.cn.danceland.myapplication.bean;

/**
 * Created by feng on 2018/2/24.
 */

public class FindSiJiaoBean {

    private Integer branch_id;
    private String branch_name;
    private Integer charge_mode;
    private Condition condition;
    private Integer count;
    private Integer course_category;
    private String course_category_name;
    private Integer days;
    private Integer id;
    private Integer keep_pro;
    private String keep_pro_name;
    private String name;
    private Integer on_sale;
    private Order order;
    private Integer page;
    private Integer pageCount;
    private Integer price;
    private Integer rowCount;
    private Integer size;
    private Integer time_length;
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

    public void setCharge_mode(int charge_mode) {
        this.charge_mode = charge_mode;
    }
    public int getCharge_mode() {
        return charge_mode;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    public Condition getCondition() {
        return condition;
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

    public void setCourse_category_name(String course_category_name) {
        this.course_category_name = course_category_name;
    }
    public String getCourse_category_name() {
        return course_category_name;
    }

    public void setDays(int days) {
        this.days = days;
    }
    public int getDays() {
        return days;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setKeep_pro(int keep_pro) {
        this.keep_pro = keep_pro;
    }
    public int getKeep_pro() {
        return keep_pro;
    }

    public void setKeep_pro_name(String keep_pro_name) {
        this.keep_pro_name = keep_pro_name;
    }
    public String getKeep_pro_name() {
        return keep_pro_name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setOn_sale(int on_sale) {
        this.on_sale = on_sale;
    }
    public int getOn_sale() {
        return on_sale;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }

    public void setPage(int page) {
        this.page = page;
    }
    public int getPage() {
        return page;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public int getPageCount() {
        return pageCount;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
    public int getRowCount() {
        return rowCount;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    public void setTime_length(int time_length) {
        this.time_length = time_length;
    }
    public int getTime_length() {
        return time_length;
    }

    public class Order {

    }
    public class Condition {

        private int length;
        public void setLength(int length) {
            this.length = length;
        }
        public int getLength() {
            return length;
        }

    }

}
