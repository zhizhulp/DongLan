package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2017/12/22 15:49
 * Email:644563767@qq.com
 * 订单请求参数
 */


public class OrderInfoBean {

    private int admin_emp_id;//会籍顾问id
    private String admin_emp_name;//会籍顾问名称
    private String admin_emp_phone;//会籍顾问电话
    private int branch_id;//门店id
    private String branch_name;//门店名称
    private int for_other;//0自己1别人
    private String name;//
    private String order_no;
    private String order_time;
    private String pay_way;//1是支付宝，2是微信
    private String phone_no;//
    private String price;//
    private String member_no;//会员编号
    private String member_name;//会员自己的真实姓名

    private String card_type_id;//卡id
    private String deposit_id;//定金id
    private String deposit_price;//定金金额

    private String deposit_type;  //定金类型
    private String card_name;//卡名称
    private String month_count;// 使用期限月数
    private String total_count; //次卡总次数

    @Override
    public String toString() {
        return "OrderInfoBean{" +
                "admin_emp_id=" + admin_emp_id +
                ", admin_emp_name='" + admin_emp_name + '\'' +
                ", admin_emp_phone='" + admin_emp_phone + '\'' +
                ", branch_id=" + branch_id +
                ", branch_name='" + branch_name + '\'' +
                ", for_other=" + for_other +
                ", name='" + name + '\'' +
                ", order_no='" + order_no + '\'' +
                ", order_time='" + order_time + '\'' +
                ", pay_way='" + pay_way + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", price='" + price + '\'' +
                ", member_no='" + member_no + '\'' +
                ", member_name='" + member_name + '\'' +
                ", card_type_id='" + card_type_id + '\'' +
                ", deposit_id='" + deposit_id + '\'' +
                ", deposit_price='" + deposit_price + '\'' +
                ", deposit_type='" + deposit_type + '\'' +
                ", card_name='" + card_name + '\'' +
                ", month_count='" + month_count + '\'' +
                ", total_count='" + total_count + '\'' +
                '}';
    }

    public String getMonth_count() {
        return month_count;
    }

    public void setMonth_count(String month_count) {
        this.month_count = month_count;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getDeposit_type() {
        return deposit_type;
    }

    public void setDeposit_type(String deposit_type) {
        this.deposit_type = deposit_type;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getDeposit_price() {
        return deposit_price;
    }

    public void setDeposit_price(String deposit_price) {
        this.deposit_price = deposit_price;
    }

    public String getCard_type_id() {
        return card_type_id;
    }

    public void setCard_type_id(String card_type_id) {
        this.card_type_id = card_type_id;
    }

    public String getDeposit_id() {
        return deposit_id;
    }

    public void setDeposit_id(String deposit_id) {
        this.deposit_id = deposit_id;
    }

    public String getAdmin_emp_phone() {
        return admin_emp_phone;
    }

    public void setAdmin_emp_phone(String admin_emp_phone) {
        this.admin_emp_phone = admin_emp_phone;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_no() {
        return member_no;
    }

    public void setMember_no(String member_no) {
        this.member_no = member_no;
    }

    public String getCname() {
        return member_name;
    }

    public void setCname(String cname) {
        this.member_name = cname;
    }

    public int getAdmin_emp_id() {
        return admin_emp_id;
    }

    public void setAdmin_emp_id(int admin_emp_id) {
        this.admin_emp_id = admin_emp_id;
    }

    public String getAdmin_emp_name() {
        return admin_emp_name;
    }

    public void setAdmin_emp_name(String admin_emp_name) {
        this.admin_emp_name = admin_emp_name;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public int getFor_other() {
        return for_other;
    }

    public void setFor_other(int for_other) {
        this.for_other = for_other;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}