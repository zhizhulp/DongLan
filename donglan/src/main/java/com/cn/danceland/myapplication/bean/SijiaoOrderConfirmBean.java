package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/2/28.
 */

public class SijiaoOrderConfirmBean implements Serializable{

    private Integer branch_id;
    private Integer bus_type;
    private String deposit_id;
    private Extends_params extends_params;
    private Integer for_other;
    private String pay_way;
    private Integer platform;
    private String price;
    private String receive;
    private String product_type;
    private String product_name;
    private String employee_id;

    public String getSell_id() {
        return employee_id;
    }

    public void setSell_id(String sell_id) {
        this.employee_id = sell_id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }
    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBus_type(Integer bus_type) {
        this.bus_type = bus_type;
    }
    public Integer getBus_type() {
        return bus_type;
    }

    public void setDeposit_id(String deposit_id) {
        this.deposit_id = deposit_id;
    }
    public String getDeposit_id() {
        return deposit_id;
    }

    public void setExtends_params(Extends_params extends_params) {
        this.extends_params = extends_params;
    }
    public Extends_params getExtends_params() {
        return extends_params;
    }

    public void setFor_other(Integer for_other) {
        this.for_other = for_other;
    }
    public Integer getFor_other() {
        return for_other;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }
    public String getPay_way() {
        return pay_way;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
    public Integer getPlatform() {
        return platform;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }
    public String getReceive() {
        return receive;
    }

    public class Extends_params {
        private String phone_no;
        private String other_name;
        private String face;
        private String giving;

        private String course_type_id;
        private String course_type_name;
        private String employee_id;
        private String employee_name;
        private String start_date;
        private String end_date;
        private Integer time_length;
        private String store_type_id;
        private String sell_id;

        public String getSell_id() {
            return sell_id;
        }

        public void setSell_id(String sell_id) {
            this.sell_id = sell_id;
        }

        public String getStore_type_id() {
            return store_type_id;
        }

        public void setStore_type_id(String store_type_id) {
            this.store_type_id = store_type_id;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public String getGiving() {
            return giving;
        }

        public void setGiving(String giving) {
            this.giving = giving;
        }

        public Integer getTime_length() {
            return time_length;
        }

        public void setTime_length(Integer time_length) {
            this.time_length = time_length;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }
        public String getPhone_no() {
            return phone_no;
        }

        public void setOther_name(String other_name) {
            this.other_name = other_name;
        }
        public String getOther_name() {
            return other_name;
        }

        public void setCourse_type_id(String course_type_id) {
            this.course_type_id = course_type_id;
        }
        public String getCourse_type_id() {
            return course_type_id;
        }

        public void setCourse_type_name(String course_type_name) {
            this.course_type_name = course_type_name;
        }
        public String getCourse_type_name() {
            return course_type_name;
        }

        public void setEmployee_id(String employee_id) {
            this.employee_id = employee_id;
        }
        public String getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }
        public String getEmployee_name() {
            return employee_name;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }
        public String getStart_date() {
            return start_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }
        public String getEnd_date() {
            return end_date;
        }

    }
    
}
