package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2017/11/23 13:51
 * Email:644563767@qq.com
 * 一条动态请求bean
 */
public class RequstOneDynInfoBean {

    private boolean success;
    private String errorMsg;
    private Data data;

    @Override
    public String toString() {
        return "RequstOneDynInfoBean{" +
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
        private String id;
        private String title;
        private String content;
        private int privacy_type;
        private int msg_type;
        private String author;
        private int publish_type;
        private int share_id;
        private int enabled;
        private String publish_time;
        private String publish_place;
        private List<String> imgList;
        private String success;
        private String errorMsg;
        private String userName;
        private String userId;
        private String self_url;
        private String nick_name;
        private int priase_number;
        private int reply_number;
        private int followerNumber;
        private String vedio_img;
        private String vedio_url;
        private boolean is_praise;
        private boolean is_follower;

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", privacy_type=" + privacy_type +
                    ", msg_type=" + msg_type +
                    ", author='" + author + '\'' +
                    ", publish_type=" + publish_type +
                    ", share_id=" + share_id +
                    ", enabled=" + enabled +
                    ", publish_time='" + publish_time + '\'' +
                    ", publish_place='" + publish_place + '\'' +
                    ", imgList=" + imgList +
                    ", success='" + success + '\'' +
                    ", errorMsg='" + errorMsg + '\'' +
                    ", userName='" + userName + '\'' +
                    ", userId='" + userId + '\'' +
                    ", self_url='" + self_url + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", priase_number=" + priase_number +
                    ", reply_number=" + reply_number +
                    ", followerNumber=" + followerNumber +
                    ", vedio_img='" + vedio_img + '\'' +
                    ", vedio_url='" + vedio_url + '\'' +
                    ", is_praise=" + is_praise +
                    ", is_follower=" + is_follower +
                    '}';
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setPrivacyType(int privacyType) {
            this.privacy_type = privacyType;
        }

        public int getPrivacyType() {
            return privacy_type;
        }

        public void setMsgType(int msgType) {
            this.msg_type = msgType;
        }

        public int getMsgType() {
            return msg_type;
        }


        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int isEnabled() {
            return enabled;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getFollowerNumber() {
            return followerNumber;
        }

        public void setFollowerNumber(int followerNumber) {
            this.followerNumber = followerNumber;
        }

        public boolean isFollower() {
            return is_follower;
        }

        public boolean isPraise() {
            return is_praise;
        }

        public void setPublishType(int publishType) {
            this.publish_type = publishType;
        }

        public int getPublishType() {
            return publish_type;
        }

        public void setShareId(int shareId) {
            this.share_id = shareId;
        }

        public int getShareId() {
            return share_id;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setPublishTime(String publishTime) {
            this.publish_time = publishTime;
        }

        public String getPublishTime() {
            return publish_time;
        }

        public void setPublishPlace(String publishPlace) {
            this.publish_place = publishPlace;
        }

        public String getPublishPlace() {
            return publish_place;
        }

        public void setVedioUrl(String vedioUrl) {
            this.vedio_url = vedioUrl;
        }

        public String getVedioUrl() {
            return vedio_url;
        }

        public void setVedioImg(String vedioImg) {
            this.vedio_img = vedioImg;
        }

        public String getVedioImg() {
            return vedio_img;
        }


        public void setSuccess(String success) {
            this.success = success;
        }

        public String getSuccess() {
            return success;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
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

        public void setPriaseNumber(int priaseNumber) {
            this.priase_number = priaseNumber;
        }

        public int getPriaseNumber() {
            return priase_number;
        }

        public void setReplyNumber(int replyNumber) {
            this.reply_number = replyNumber;
        }

        public int getReplyNumber() {
            return reply_number;
        }

        public void setPraise(boolean praise) {
            this.is_praise = praise;
        }

        public boolean getPraise() {
            return is_praise;
        }

        public void setFollower(boolean follower) {
            this.is_follower = follower;
        }

        public boolean getFollower() {
            return is_follower;
        }

    }
}