package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2018/3/20 14:33
 * Email:644563767@qq.com
 */

public class RequsetUserDynInfoBean {

    private boolean success;
    private String errorMsg;
    private String code;
    private Data data;

    @Override
    public String toString() {
        return "RequsetUserDynInfoBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
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
        @Override
        public String toString() {
            return "Data{" +
                    "person=" + person +
                    ", fanse_no=" + fanse_no +
                    ", follow_no=" + follow_no +
                    ", dyn_no=" + dyn_no +
                    ", is_follow=" + is_follow +
                    '}';
        }

        private Person person;
        private int fanse_no;
        private int follow_no;
        private int dyn_no;
        private boolean is_follow;
        public void setPerson(Person person) {
            this.person = person;
        }
        public Person getPerson() {
            return person;
        }

        public void setFanse_no(int fanse_no) {
            this.fanse_no = fanse_no;
        }
        public int getFanse_no() {
            return fanse_no;
        }

        public void setFollow_no(int follow_no) {
            this.follow_no = follow_no;
        }
        public int getFollow_no() {
            return follow_no;
        }

        public void setDyn_no(int dyn_no) {
            this.dyn_no = dyn_no;
        }
        public int getDyn_no() {
            return dyn_no;
        }

        public void setIs_follow(boolean is_follow) {
            this.is_follow = is_follow;
        }
        public boolean getIs_follow() {
            return is_follow;
        }

    }



}
