package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/1/2 16:07
 * Email:644563767@qq.com
 */


public class RequestMyCardListBean {

    private boolean success;
    private String errorMsg;
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

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }


    public class Data {

        private String id;
        private String member_id;
        private String branch_id;
        private String branch_name;
        private String member_name;
        private String member_no;
        private String card_no;
        private String gender;
        private String chip_number;
        private String type_id;
        private String total_count;
        private String end_date;
        private String face_value;
        private int charge_mode;
        private String sell_price;
        private long sell_date;
        private String sell_name;
        private String operater_id;
        private String sell_id;
        private String open_date;
        private int pay_way;
        private int month_count;
        private int up_status;
        private int continue_status;
        private int lose_status;
        private int quit_status;
        private int pause_status;
        private int patch_status;
        private int expire_status;
        private int extend_status;
        private int enter_status;
        private int delete_remark;
        private String type_name;
        private String pay_way_name;
        private String able_up;
        private String old_card_id;
        private String deposit;
        private String img_url;
        private int card_status;//1:可用2:未发卡3:未开卡4:挂失5:退卡6:被升级7:停卡8:过期

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", member_id='" + member_id + '\'' +
                    ", branch_id='" + branch_id + '\'' +
                    ", branch_name='" + branch_name + '\'' +
                    ", member_name='" + member_name + '\'' +
                    ", member_no='" + member_no + '\'' +
                    ", card_no='" + card_no + '\'' +
                    ", gender='" + gender + '\'' +
                    ", chip_number='" + chip_number + '\'' +
                    ", type_id='" + type_id + '\'' +
                    ", total_count='" + total_count + '\'' +
                    ", end_date='" + end_date + '\'' +
                    ", face_value='" + face_value + '\'' +
                    ", charge_mode=" + charge_mode +
                    ", sell_price='" + sell_price + '\'' +
                    ", sell_date=" + sell_date +
                    ", sell_name='" + sell_name + '\'' +
                    ", operater_id='" + operater_id + '\'' +
                    ", sell_id='" + sell_id + '\'' +
                    ", open_date='" + open_date + '\'' +
                    ", pay_way=" + pay_way +
                    ", month_count=" + month_count +
                    ", up_status=" + up_status +
                    ", continue_status=" + continue_status +
                    ", lose_status=" + lose_status +
                    ", quit_status=" + quit_status +
                    ", pause_status=" + pause_status +
                    ", patch_status=" + patch_status +
                    ", expire_status=" + expire_status +
                    ", extend_status=" + extend_status +
                    ", enter_status=" + enter_status +
                    ", delete_remark=" + delete_remark +
                    ", type_name='" + type_name + '\'' +
                    ", pay_way_name='" + pay_way_name + '\'' +
                    ", able_up='" + able_up + '\'' +
                    ", old_card_id='" + old_card_id + '\'' +
                    ", deposit='" + deposit + '\'' +
                    ", img_url='" + img_url + '\'' +
                    ", card_status='" + card_status + '\'' +
                    '}';
        }

        public int getCard_status() {
            return card_status;
        }

        public void setCard_status(int card_status) {
            this.card_status = card_status;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
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

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getChip_number() {
            return chip_number;
        }

        public void setChip_number(String chip_number) {
            this.chip_number = chip_number;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getFace_value() {
            return face_value;
        }

        public void setFace_value(String face_value) {
            this.face_value = face_value;
        }

        public int getCharge_mode() {
            return charge_mode;
        }

        public void setCharge_mode(int charge_mode) {
            this.charge_mode = charge_mode;
        }

        public String getSell_price() {
            return sell_price;
        }

        public void setSell_price(String sell_price) {
            this.sell_price = sell_price;
        }

        public long getSell_date() {
            return sell_date;
        }

        public void setSell_date(long sell_date) {
            this.sell_date = sell_date;
        }

        public String getSell_name() {
            return sell_name;
        }

        public void setSell_name(String sell_name) {
            this.sell_name = sell_name;
        }

        public String getOperater_id() {
            return operater_id;
        }

        public void setOperater_id(String operater_id) {
            this.operater_id = operater_id;
        }

        public String getSell_id() {
            return sell_id;
        }

        public void setSell_id(String sell_id) {
            this.sell_id = sell_id;
        }

        public String getOpen_date() {
            return open_date;
        }

        public void setOpen_date(String open_date) {
            this.open_date = open_date;
        }

        public int getPay_way() {
            return pay_way;
        }

        public void setPay_way(int pay_way) {
            this.pay_way = pay_way;
        }

        public int getMonth_count() {
            return month_count;
        }

        public void setMonth_count(int month_count) {
            this.month_count = month_count;
        }

        public int getUp_status() {
            return up_status;
        }

        public void setUp_status(int up_status) {
            this.up_status = up_status;
        }

        public int getContinue_status() {
            return continue_status;
        }

        public void setContinue_status(int continue_status) {
            this.continue_status = continue_status;
        }

        public int getLose_status() {
            return lose_status;
        }

        public void setLose_status(int lose_status) {
            this.lose_status = lose_status;
        }

        public int getQuit_status() {
            return quit_status;
        }

        public void setQuit_status(int quit_status) {
            this.quit_status = quit_status;
        }

        public int getPause_status() {
            return pause_status;
        }

        public void setPause_status(int pause_status) {
            this.pause_status = pause_status;
        }

        public int getPatch_status() {
            return patch_status;
        }

        public void setPatch_status(int patch_status) {
            this.patch_status = patch_status;
        }

        public int getExpire_status() {
            return expire_status;
        }

        public void setExpire_status(int expire_status) {
            this.expire_status = expire_status;
        }

        public int getExtend_status() {
            return extend_status;
        }

        public void setExtend_status(int extend_status) {
            this.extend_status = extend_status;
        }

        public int getEnter_status() {
            return enter_status;
        }

        public void setEnter_status(int enter_status) {
            this.enter_status = enter_status;
        }

        public int getDelete_remark() {
            return delete_remark;
        }

        public void setDelete_remark(int delete_remark) {
            this.delete_remark = delete_remark;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getPay_way_name() {
            return pay_way_name;
        }

        public void setPay_way_name(String pay_way_name) {
            this.pay_way_name = pay_way_name;
        }

        public String getAble_up() {
            return able_up;
        }

        public void setAble_up(String able_up) {
            this.able_up = able_up;
        }

        public String getOld_card_id() {
            return old_card_id;
        }

        public void setOld_card_id(String old_card_id) {
            this.old_card_id = old_card_id;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }
    }
}
