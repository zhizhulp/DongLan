package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shy on 2017/12/28 17:57
 * Email:644563767@qq.com
 *
 */


public class RequestOrderListBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private Data data;

    @Override
    public String toString() {
        return "RequestOrderListBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
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

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }



    public class Data implements Serializable {

        private List<Content> content;
        private int number;
        private int size;
        private int totalElements;
        private int numberOfElements;
        private int totalPages;
        private boolean last;

        @Override
        public String toString() {
            return "Data{" +
                    "content=" + content +
                    ", number=" + number +
                    ", size=" + size +
                    ", totalElements=" + totalElements +
                    ", numberOfElements=" + numberOfElements +
                    ", totalPages=" + totalPages +
                    ", last=" + last +
                    '}';
        }

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

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public boolean getLast() {
            return last;
        }

        public class Content implements Serializable {

//            private String id;
//            private int bus_type;
//            private int for_other;
//            private String order_time;
//            private String person_id;
//            private String member_no;
//            private String member_id;
//            private String member_name;
//            private String branch_id;
//            private int status;
//            private int pay_way;
//            private float price;
//            private String extends_info;
//            private String order_no;
//            private String phone_no;
//            private String name;
//            private String admin_emp_id;
//            private String admin_emp_name;
//            private String branch_name;
//            private String belong_id;
//            private String card_type_id;
//            private String deposit_id;
//            private String deposit_price;
//            private String admin_emp_phone;
//            private String chip_number;
//            private String open_date;
//            private String card_no;


            private String id;
            private int bus_type;
            private String bus_id;
            private String for_other;
            private String order_time;
            private String person_id;
            private String member_id;
            private String branch_id;
            private int status;
            private int pay_way;
            private int price;
            private String order_no;
            private String order_remark;
            private String pay_remark;
            private String receive;
            private String pledge;
            private String platform;
            private String deposit_id;
            private String operator_id;
            private long pay_time;
            private String extends_info;
            private String extends_params;
            private int gender;
            private String member_name;
            private String member_no;
            private String money;
            private String phone_no;

            @Override
            public String toString() {
                return "Content{" +
                        "id='" + id + '\'' +
                        ", bus_type=" + bus_type +
                        ", bus_id='" + bus_id + '\'' +
                        ", for_other='" + for_other + '\'' +
                        ", order_time='" + order_time + '\'' +
                        ", person_id='" + person_id + '\'' +
                        ", member_id='" + member_id + '\'' +
                        ", branch_id='" + branch_id + '\'' +
                        ", status=" + status +
                        ", pay_way=" + pay_way +
                        ", price=" + price +
                        ", order_no='" + order_no + '\'' +
                        ", order_remark='" + order_remark + '\'' +
                        ", pay_remark='" + pay_remark + '\'' +
                        ", receive='" + receive + '\'' +
                        ", pledge='" + pledge + '\'' +
                        ", platform='" + platform + '\'' +
                        ", deposit_id='" + deposit_id + '\'' +
                        ", operator_id='" + operator_id + '\'' +
                        ", pay_time=" + pay_time +
                        ", extends_info='" + extends_info + '\'' +
                        ", extends_params='" + extends_params + '\'' +
                        ", gender=" + gender +
                        ", member_name='" + member_name + '\'' +
                        ", member_no='" + member_no + '\'' +
                        ", money='" + money + '\'' +
                        ", phone_no='" + phone_no + '\'' +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getBus_type() {
                return bus_type;
            }

            public void setBus_type(int bus_type) {
                this.bus_type = bus_type;
            }

            public String getBus_id() {
                return bus_id;
            }

            public void setBus_id(String bus_id) {
                this.bus_id = bus_id;
            }

            public String getFor_other() {
                return for_other;
            }

            public void setFor_other(String for_other) {
                this.for_other = for_other;
            }

            public String getOrder_time() {
                return order_time;
            }

            public void setOrder_time(String order_time) {
                this.order_time = order_time;
            }

            public String getPerson_id() {
                return person_id;
            }

            public void setPerson_id(String person_id) {
                this.person_id = person_id;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getBranch_id() {
                return branch_id;
            }

            public void setBranch_id(String branch_id) {
                this.branch_id = branch_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getPay_way() {
                return pay_way;
            }

            public void setPay_way(int pay_way) {
                this.pay_way = pay_way;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getOrder_remark() {
                return order_remark;
            }

            public void setOrder_remark(String order_remark) {
                this.order_remark = order_remark;
            }

            public String getPay_remark() {
                return pay_remark;
            }

            public void setPay_remark(String pay_remark) {
                this.pay_remark = pay_remark;
            }

            public String getReceive() {
                return receive;
            }

            public void setReceive(String receive) {
                this.receive = receive;
            }

            public String getPledge() {
                return pledge;
            }

            public void setPledge(String pledge) {
                this.pledge = pledge;
            }

            public String getPlatform() {
                return platform;
            }

            public void setPlatform(String platform) {
                this.platform = platform;
            }

            public String getDeposit_id() {
                return deposit_id;
            }

            public void setDeposit_id(String deposit_id) {
                this.deposit_id = deposit_id;
            }

            public String getOperator_id() {
                return operator_id;
            }

            public void setOperator_id(String operator_id) {
                this.operator_id = operator_id;
            }

            public long getPay_time() {
                return pay_time;
            }

            public void setPay_time(long pay_time) {
                this.pay_time = pay_time;
            }

            public String getExtends_info() {
                return extends_info;
            }

            public void setExtends_info(String extends_info) {
                this.extends_info = extends_info;
            }

            public String getExtends_params() {
                return extends_params;
            }

            public void setExtends_params(String extends_params) {
                this.extends_params = extends_params;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
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

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getPhone_no() {
                return phone_no;
            }

            public void setPhone_no(String phone_no) {
                this.phone_no = phone_no;
            }
        }
    }}
