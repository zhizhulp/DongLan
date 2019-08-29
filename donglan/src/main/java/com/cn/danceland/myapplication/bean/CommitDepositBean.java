package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/3/2.
 */

public class CommitDepositBean implements Serializable {

    private Integer branch_id;
    private Integer bus_type;
    private Extends_params extends_params;
    private Integer for_other;
    private String pay_way;
    private Integer platform;
    private String price;
    private String receive;
    private String product_type;
    private String product_name;

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

        private String admin_emp_id;
        private String admin_emp_name;
        private String bus_type;
        private String deposit_type;
        private String money;



        public void setAdmin_emp_id(String admin_emp_id) {
            this.admin_emp_id = admin_emp_id;
        }
        public String getAdmin_emp_id() {
            return admin_emp_id;
        }

        public void setAdmin_emp_name(String admin_emp_name) {
            this.admin_emp_name = admin_emp_name;
        }
        public String getAdmin_emp_name() {
            return admin_emp_name;
        }

        public void setBus_type(String bus_type) {
            this.bus_type = bus_type;
        }
        public String getBus_type() {
            return bus_type;
        }

        public void setDeposit_type(String deposit_type) {
            this.deposit_type = deposit_type;
        }
        public String getDeposit_type() {
            return deposit_type;
        }

        public void setMoney(String money) {
            this.money = money;
        }
        public String getMoney() {
            return money;
        }

    }

}
