package com.cn.danceland.myapplication.bean;

/**
 * Created by yxx on 2018-09-25.
 */

public class RequestScanerCodeBean {
    private boolean success;
    private String errorMsg;
    private String code;
    private Data data;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestScanerCodeBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {

        private long open_date;
        private long end_date;
        private String phone_no;
        private String type_name;
        private int gender;
        private String card_no;
        private String img_url;
        private String avatar_url;
        private String total_count;
        private String cname;
        private boolean enter;
        private String hand_card_code;//手牌号
        private String hand_card_area;//手牌区

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getHand_card_area() {
            return hand_card_area;
        }

        public void setHand_card_area(String hand_card_area) {
            this.hand_card_area = hand_card_area;
        }

        public String getHand_card_code() {
            return hand_card_code;
        }

        public void setHand_card_code(String hand_card_code) {
            this.hand_card_code = hand_card_code;
        }

        public boolean getEnter() {
            return enter;
        }

        public void setEnter(boolean enter) {
            this.enter = enter;
        }

        public void setOpen_date(long open_date) {
            this.open_date = open_date;
        }
        public long getOpen_date() {
            return open_date;
        }

        public void setEnd_date(long end_date) {
            this.end_date = end_date;
        }
        public long getEnd_date() {
            return end_date;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }
        public String getPhone_no() {
            return phone_no;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
        public String getType_name() {
            return type_name;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
        public int getGender() {
            return gender;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }
        public String getCard_no() {
            return card_no;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
        public String getImg_url() {
            return img_url;
        }

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }
        public String getCname() {
            return cname;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "open_date=" + open_date +
                    ", end_date=" + end_date +
                    ", phone_no='" + phone_no + '\'' +
                    ", type_name='" + type_name + '\'' +
                    ", gender=" + gender +
                    ", card_no='" + card_no + '\'' +
                    ", img_url='" + img_url + '\'' +
                    ", total_count=" + total_count +
                    ", cname='" + cname + '\'' +
                    '}';
        }
    }
}
