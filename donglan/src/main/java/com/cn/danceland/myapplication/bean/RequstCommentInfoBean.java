package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2017/11/23 13:42
 * Email:644563767@qq.com
 */

public class RequstCommentInfoBean {

    private boolean success;
    private String errorMsg;
    private Data data;

    @Override
    public String toString() {
        return "RequstCommentInfoBean{" +
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
            private boolean last;
        private int total;
        private List<Content> content;

        @Override
        public String toString() {
            return "Data{" +
                    "total=" + total +
                    ", items=" + content +
                    '}';
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
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

    }

    public static class Content {

        private String id;//评论的id
        private String reply_user_id;//发评论人的id
        private String content;//发评论的内容
        private String time;
        private String parent_id;//回复评论的id
        private String reply_msgI_id;//动态的id
        private String success;
        private String error_msg;
        private String self_url;//发评论的头像
        private String nick_name;//发评论人的昵称
        private Integer gender;
//        private String replyed_user_id;//要回复的人id
//        private String replyed_self_url;//要回复的头像
//        private String replyed_nick_name;//要回复人的昵称

        private String replyed_user_id;// 被回复的人id
        private String replyed_self_url;// 被回复的头像
        private String replyed_nick_name;// 被回复人的昵称


        @Override
        public String toString() {
            return "Items{" +
                    "id='" + id + '\'' +
                    ", reply_user_id='" + reply_user_id + '\'' +
                    ", content='" + content + '\'' +
                    ", time='" + time + '\'' +
                    ", parent_id='" + parent_id + '\'' +
                    ", reply_msgI_id='" + reply_msgI_id + '\'' +
                    ", success='" + success + '\'' +
                    ", error_msg='" + error_msg + '\'' +
                    ", self_url='" + self_url + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", gender=" + gender +
                    ", reply_user='" + replyed_user_id + '\'' +
                    ", reply_self_url='" + replyed_self_url + '\'' +
                    ", reply_nick_name='" + replyed_nick_name + '\'' +
                    '}';
        }

        public String getReplyUser() {
            return replyed_user_id;
        }

        public void setReplyUser(String replyUser) {
            this.replyed_user_id = replyUser;
        }

        public String getReplySelfUrl() {
            return replyed_self_url;
        }

        public void setReplySelfUrl(String replySelfUrl) {
            this.replyed_self_url = replySelfUrl;
        }

        public String getReplyNickName() {
            return replyed_nick_name;
        }

        public void setReplyNickName(String replyNickName) {
            this.replyed_nick_name = replyNickName;
        }


        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setReplyUserId(String replyUserId) {
            this.reply_user_id = replyUserId;
        }

        public String getReplyUserId() {
            return reply_user_id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public void setParentId(String parentId) {
            this.parent_id = parentId;
        }

        public String getParentId() {
            return parent_id;
        }

        public void setReplyMsgId(String replyMsgId) {
            this.reply_msgI_id = replyMsgId;
        }

        public String getReplyMsgId() {
            return reply_msgI_id;
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