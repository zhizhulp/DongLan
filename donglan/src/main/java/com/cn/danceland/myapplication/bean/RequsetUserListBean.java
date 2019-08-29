package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2017/11/21 14:33
 * Email:644563767@qq.com
 */


public class RequsetUserListBean {

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
        private int number;
        private int size;
        private int totalElements;
        private int numberOfElements;
        private int totalPages;
        private boolean last;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "total=" + total +
                    ", content=" + content +
                    ", number=" + number +
                    ", size=" + size +
                    ", totalElements=" + totalElements +
                    ", numberOfElements=" + numberOfElements +
                    ", totalPages=" + totalPages +
                    ", last=" + last +
                    '}';
        }

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

        public void setItems(List<Content> items) {
            this.content = items;
        }

        public List<Content> getItems() {
            return content;
        }


        public class Content {
            public String getFollower() {
                return follower;
            }

            private String id;
            private String follower;
            private String user_id;
            private String success;
            private String error_msg;
            private String self_path;
            private String nick_name;
            private String self_url;
            private int gender;
            private String praise_user_id;
            private boolean is_follower;

            @Override
            public String toString() {
                return "Content{" +
                        "id='" + id + '\'' +
                        ", follower='" + follower + '\'' +
                        ", user_id='" + user_id + '\'' +
                        ", success='" + success + '\'' +
                        ", error_msg='" + error_msg + '\'' +
                        ", self_path='" + self_path + '\'' +
                        ", nick_name='" + nick_name + '\'' +
                        ", self_url='" + self_url + '\'' +
                        ", gender=" + gender +
                        ", praise_user_id='" + praise_user_id + '\'' +
                        ", is_follower='" + is_follower + '\'' +
                        '}';
            }

            public boolean getIs_follower() {
                return is_follower;
            }

            public void setIs_follower(boolean is_follower) {
                this.is_follower = is_follower;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getSelf_path() {
                return self_path;
            }

            public void setSelf_path(String self_path) {
                this.self_path = self_path;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getSelf_url() {
                return self_url;
            }

            public void setSelf_url(String self_url) {
                this.self_url = self_url;
            }

            public String getPraise_user_id() {
                return praise_user_id;
            }

            public void setPraise_user_id(String praise_user_id) {
                this.praise_user_id = praise_user_id;
            }

            public String getError_msg() {
                return error_msg;
            }

            public void setError_msg(String error_msg) {
                this.error_msg = error_msg;
            }

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
                this.self_path = selfUrl;
            }

            public String getSelfUrl() {
                return self_path;
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