package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/7/24 18:01
 * Email:644563767@qq.com
 */


public class BlackListBean {
    @Override
    public String toString() {
        return "BlackListBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

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
        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", user_id=" + user_id +
                    ", blocked_id=" + blocked_id +
                    ", nick_name='" + nick_name + '\'' +
                    ", gender=" + gender +
                    ", self_avatar_path='" + self_avatar_path + '\'' +
                    '}';
        }

        private int id;
        private int user_id;
        private int blocked_id;
        private String nick_name;
        private int gender;
        private String self_avatar_path;
        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
        public int getUser_id() {
            return user_id;
        }

        public void setBlocked_id(int blocked_id) {
            this.blocked_id = blocked_id;
        }
        public int getBlocked_id() {
            return blocked_id;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }
        public String getNick_name() {
            return nick_name;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
        public int getGender() {
            return gender;
        }

        public void setSelf_avatar_path(String self_avatar_path) {
            this.self_avatar_path = self_avatar_path;
        }
        public String getSelf_avatar_path() {
            return self_avatar_path;
        }

    }
}