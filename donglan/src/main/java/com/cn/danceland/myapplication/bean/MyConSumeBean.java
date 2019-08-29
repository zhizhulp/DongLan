package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2018/5/4.
 */

public class MyConSumeBean {

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

    public class Protocol_params {

    }


    public class Content {

        private String branch_id;
        private String branch_name;
        private String bus_id;
        private String bus_type;
        private String deposit_id;
        private String employee_id;
        private String extends_info;
        private Extends_params extends_params;
        private String gender;
        private String id;
        private String member_id;
        private String member_name;
        private String member_no;
        private String money;
        private String operator_id;
        private String operator_name;
        private String opt_no;
        private String order_no;
        private String order_remark;
        private String order_time;
        private String pay_remark;
        private String pay_time;
        private String pay_way;
        private String person_id;
        private String phone_no;
        private String platform;
        private String pledge;
        private String price;
        private String protocol_id;
        private String protocol_info;
        private Protocol_params protocol_params;
        private String protocol_template_id;
        private String receive;
        private String root_opt_no;
        private String status;
        private String product_type;
        private String product_name;
        private String pay_params;// 支付参数
        private Long inner_order_no;// 内部定单编号

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getBus_id() {
            return bus_id;
        }

        public void setBus_id(String bus_id) {
            this.bus_id = bus_id;
        }

        public String getBus_type() {
            return bus_type;
        }

        public void setBus_type(String bus_type) {
            this.bus_type = bus_type;
        }

        public String getDeposit_id() {
            return deposit_id;
        }

        public void setDeposit_id(String deposit_id) {
            this.deposit_id = deposit_id;
        }

        public String getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_id(String employee_id) {
            this.employee_id = employee_id;
        }

        public String getExtends_info() {
            return extends_info;
        }

        public void setExtends_info(String extends_info) {
            this.extends_info = extends_info;
        }

        public Extends_params getExtends_params() {
            return extends_params;
        }

        public void setExtends_params(Extends_params extends_params) {
            this.extends_params = extends_params;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
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

        public String getOperator_id() {
            return operator_id;
        }

        public void setOperator_id(String operator_id) {
            this.operator_id = operator_id;
        }

        public String getOperator_name() {
            return operator_name;
        }

        public void setOperator_name(String operator_name) {
            this.operator_name = operator_name;
        }

        public String getOpt_no() {
            return opt_no;
        }

        public void setOpt_no(String opt_no) {
            this.opt_no = opt_no;
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

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getPay_remark() {
            return pay_remark;
        }

        public void setPay_remark(String pay_remark) {
            this.pay_remark = pay_remark;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getPay_way() {
            return pay_way;
        }

        public void setPay_way(String pay_way) {
            this.pay_way = pay_way;
        }

        public String getPerson_id() {
            return person_id;
        }

        public void setPerson_id(String person_id) {
            this.person_id = person_id;
        }

        public String getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getPledge() {
            return pledge;
        }

        public void setPledge(String pledge) {
            this.pledge = pledge;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProtocol_id() {
            return protocol_id;
        }

        public void setProtocol_id(String protocol_id) {
            this.protocol_id = protocol_id;
        }

        public String getProtocol_info() {
            return protocol_info;
        }

        public void setProtocol_info(String protocol_info) {
            this.protocol_info = protocol_info;
        }

        public Protocol_params getProtocol_params() {
            return protocol_params;
        }

        public void setProtocol_params(Protocol_params protocol_params) {
            this.protocol_params = protocol_params;
        }

        public String getProtocol_template_id() {
            return protocol_template_id;
        }

        public void setProtocol_template_id(String protocol_template_id) {
            this.protocol_template_id = protocol_template_id;
        }

        public String getReceive() {
            return receive;
        }

        public void setReceive(String receive) {
            this.receive = receive;
        }

        public String getRoot_opt_no() {
            return root_opt_no;
        }

        public void setRoot_opt_no(String root_opt_no) {
            this.root_opt_no = root_opt_no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getPay_params() {
            return pay_params;
        }

        public void setPay_params(String pay_params) {
            this.pay_params = pay_params;
        }

        public Long getInner_order_no() {
            return inner_order_no;
        }

        public void setInner_order_no(Long inner_order_no) {
            this.inner_order_no = inner_order_no;
        }
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

    public class Extends_params {

    }

}
