package com.cn.danceland.myapplication.bean;

/**
 * Created by yxx on 2018-09-25.
 */

public class RequestScanerCodeIfHandBean {
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

    public class Data {
        private String has_hand_card;//1:有手牌  0：没有手牌

        public String getHas_hand_card() {
            return has_hand_card;
        }

        public void setHas_hand_card(String has_hand_card) {
            this.has_hand_card = has_hand_card;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "has_hand_card='" + has_hand_card + '\'' +
                    '}';
        }
    }
}
