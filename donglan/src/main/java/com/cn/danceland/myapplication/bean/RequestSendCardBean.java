package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/3/23 16:31
 * Email:644563767@qq.com
 */


public class RequestSendCardBean {

    private boolean success;
    private String errorMsg;
    private String code;
    private List<Data> data;
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

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }



    public class Data {

        private int id;
        private int member_id;
        private int branch_id;
        private String card_no;
        private String chip_number;
        private int type_id;
        private int total_count;
        private String end_date;
        private float face_value;
        private float sell_price;
        private int charge_mode;
        private long sell_date;
        private int operater_id;
        private int sell_id;
        private String open_date;
        private int month_count;
        private int lose_status;
        private int quit_status;
        private int pause_status;
        private int expire_status;
        private int enter_status;
        private int delete_remark;
        private String branch_name;
        private String member_name;
        private String member_no;
        private int gender;
        private String sell_name;
        private int person_id;
        private String type_name;
        private String able_up;
        private String old_card_id;
        private String deposit;
        private String img_url;
        private String phone_no;
        private String extends_info;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
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

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }
        public int getBranch_id() {
            return branch_id;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }
        public String getCard_no() {
            return card_no;
        }

        public void setChip_number(String chip_number) {
            this.chip_number = chip_number;
        }
        public String getChip_number() {
            return chip_number;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }
        public int getType_id() {
            return type_id;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }
        public int getTotal_count() {
            return total_count;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }
        public String getEnd_date() {
            return end_date;
        }

        public void setFace_value(float face_value) {
            this.face_value = face_value;
        }
        public float getFace_value() {
            return face_value;
        }

        public void setSell_price(float sell_price) {
            this.sell_price = sell_price;
        }
        public float getSell_price() {
            return sell_price;
        }

        public void setCharge_mode(int charge_mode) {
            this.charge_mode = charge_mode;
        }
        public int getCharge_mode() {
            return charge_mode;
        }

        public void setSell_date(long sell_date) {
            this.sell_date = sell_date;
        }
        public long getSell_date() {
            return sell_date;
        }

        public void setOperater_id(int operater_id) {
            this.operater_id = operater_id;
        }
        public int getOperater_id() {
            return operater_id;
        }

        public void setSell_id(int sell_id) {
            this.sell_id = sell_id;
        }
        public int getSell_id() {
            return sell_id;
        }

        public void setOpen_date(String open_date) {
            this.open_date = open_date;
        }
        public String getOpen_date() {
            return open_date;
        }

        public void setMonth_count(int month_count) {
            this.month_count = month_count;
        }
        public int getMonth_count() {
            return month_count;
        }

        public void setLose_status(int lose_status) {
            this.lose_status = lose_status;
        }
        public int getLose_status() {
            return lose_status;
        }

        public void setQuit_status(int quit_status) {
            this.quit_status = quit_status;
        }
        public int getQuit_status() {
            return quit_status;
        }

        public void setPause_status(int pause_status) {
            this.pause_status = pause_status;
        }
        public int getPause_status() {
            return pause_status;
        }

        public void setExpire_status(int expire_status) {
            this.expire_status = expire_status;
        }
        public int getExpire_status() {
            return expire_status;
        }

        public void setEnter_status(int enter_status) {
            this.enter_status = enter_status;
        }
        public int getEnter_status() {
            return enter_status;
        }

        public void setDelete_remark(int delete_remark) {
            this.delete_remark = delete_remark;
        }
        public int getDelete_remark() {
            return delete_remark;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }
        public String getBranch_name() {
            return branch_name;
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

        public void setGender(int gender) {
            this.gender = gender;
        }
        public int getGender() {
            return gender;
        }

        public void setSell_name(String sell_name) {
            this.sell_name = sell_name;
        }
        public String getSell_name() {
            return sell_name;
        }

        public void setPerson_id(int person_id) {
            this.person_id = person_id;
        }
        public int getPerson_id() {
            return person_id;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
        public String getType_name() {
            return type_name;
        }

        public void setAble_up(String able_up) {
            this.able_up = able_up;
        }
        public String getAble_up() {
            return able_up;
        }

        public void setOld_card_id(String old_card_id) {
            this.old_card_id = old_card_id;
        }
        public String getOld_card_id() {
            return old_card_id;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }
        public String getDeposit() {
            return deposit;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }
        public String getPhone_no() {
            return phone_no;
        }

        public void setExtends_info(String extends_info) {
            this.extends_info = extends_info;
        }
        public String getExtends_info() {
            return extends_info;
        }

    }
}