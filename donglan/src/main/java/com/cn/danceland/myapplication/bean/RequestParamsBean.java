package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2018/5/4 16:44
 * Email:644563767@qq.com
 */

public class RequestParamsBean {

    private boolean success;
    private String errorMsg;
    private String code;
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

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }
    public class Data {

        private String deposit_course_min;
        private String deposit_card_min;
        private String deposit_days;
        private String deposit_card_max;
        private String deposit_locker_min;
        private String deposit_locker_max;
        private String deposit_course_max;
        public void setDeposit_course_min(String deposit_course_min) {
            this.deposit_course_min = deposit_course_min;
        }
        public String getDeposit_course_min() {
            return deposit_course_min;
        }

        public void setDeposit_card_min(String deposit_card_min) {
            this.deposit_card_min = deposit_card_min;
        }
        public String getDeposit_card_min() {
            return deposit_card_min;
        }

        public void setDeposit_days(String deposit_days) {
            this.deposit_days = deposit_days;
        }
        public String getDeposit_days() {
            return deposit_days;
        }

        public void setDeposit_card_max(String deposit_card_max) {
            this.deposit_card_max = deposit_card_max;
        }
        public String getDeposit_card_max() {
            return deposit_card_max;
        }

        public void setDeposit_locker_min(String deposit_locker_min) {
            this.deposit_locker_min = deposit_locker_min;
        }
        public String getDeposit_locker_min() {
            return deposit_locker_min;
        }

        public void setDeposit_locker_max(String deposit_locker_max) {
            this.deposit_locker_max = deposit_locker_max;
        }
        public String getDeposit_locker_max() {
            return deposit_locker_max;
        }

        public void setDeposit_course_max(String deposit_course_max) {
            this.deposit_course_max = deposit_course_max;
        }
        public String getDeposit_course_max() {
            return deposit_course_max;
        }

    }
}