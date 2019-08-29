package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2017/11/21 14:33
 * Email:644563767@qq.com
 */


public class RequsetUserListBeanZan {

    private boolean success;
    private String errorMsg;
    private Data data;

    @Override
    public String toString() {
        return "RequsetUserListBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
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

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        private int total;
        private List<Content> content;

        @Override
        public String toString() {
            return "Data{" +
                    "total=" + total +
                    ", items=" + content +
                    '}';
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

        public void setItems(List<Content> content) {
            this.content = content;
        }

        public List<Content> getItems() {
            return content;
        }


        public class Content {
            public String getFollower() {
                return follower;
            }

            @Override
            public String toString() {
                return "Items{" +
                        "id='" + id + '\'' +
                        ", follower='" + follower + '\'' +
                        ", user_id='" + user_id + '\'' +
                        ", success='" + success + '\'' +
                        ", error_msg='" + error_msg + '\'' +
                        ", self_url='" + self_url + '\'' +
                        ", nick_name='" + nick_name + '\'' +
                        ", gender=" + gender +
                        ", praise_user_id='" + praise_user_id + '\'' +
                        '}';
            }

            private String id;
            private String follower;
            private String user_id;
            private String success;
            private String error_msg;
            private String self_url;
            private String nick_name;
            private int gender;
            private String praise_user_id;

//            private int id;
//            private int follower;
//            private int userId;
//            private String success;
//            private String errorMsg;
//            private String self_url;
//            private String nickName;
//            private int gender;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String isFollower() {
                return follower;
            }

            public void setFollower(String follower) {
                this.follower = follower;
            }

            public String getPraiseUserId() {
                return praise_user_id;
            }

            public void setPraiseUserId(String praiseUserId) {
                this.praise_user_id = praiseUserId;
            }

            public void setUserId(String userId) {
                this.user_id = userId;
            }

            public String getUserId() {
                return user_id;
            }

            public void setSuccess(String success) {
                this.success = success;
            }

            public String getSuccess() {
                return success;
            }

            public void setErrorMsg(String errorMsg) {
                this.error_msg = errorMsg;
            }

            public String getErrorMsg() {
                return error_msg;
            }

            public void setSelfUrl(String selfUrl) {
                this.self_url = selfUrl;
            }

            public String getSelfUrl() {
                return self_url;
            }

            public void setNickName(String nickName) {
                this.nick_name = nickName;
            }

            public String getNickName() {
                return nick_name;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getGender() {
                return gender;
            }

        }

    }
}