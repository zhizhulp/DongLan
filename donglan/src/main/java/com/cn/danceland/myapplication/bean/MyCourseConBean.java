package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by feng on 2018/3/1.
 */

public class MyCourseConBean implements Serializable {

    private Integer branch_id;
    private Condition condition;
    private Integer count;
    private Integer course_category;
    private Integer course_type_id;
    private String delete_remark;
    private Integer employee_id;
    private Date end_date;
    private Integer id;
    private Integer member_id;
    private String member_no;
    private Order order;
    private Integer page;
    private Integer pageCount;
    private Integer pay_way;
    private Integer price;
    private Integer real_price;
    private Integer rowCount;
    private Integer size;
    private Date start_date;
    private Integer surplus_count;
    private Integer time_length;
    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }
    public Integer getBranch_id() {
        return branch_id;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    public Condition getCondition() {
        return condition;
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

    public void setDelete_remark(String delete_remark) {
        this.delete_remark = delete_remark;
    }
    public String getDelete_remark() {
        return delete_remark;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }
    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
    public Date getEnd_date() {
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

    public void setMember_no(String member_no) {
        this.member_no = member_no;
    }
    public String getMember_no() {
        return member_no;
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

    public void setPay_way(Integer pay_way) {
        this.pay_way = pay_way;
    }
    public Integer getPay_way() {
        return pay_way;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getPrice() {
        return price;
    }

    public void setReal_price(Integer real_price) {
        this.real_price = real_price;
    }
    public Integer getReal_price() {
        return real_price;
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

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    public Date getStart_date() {
        return start_date;
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
