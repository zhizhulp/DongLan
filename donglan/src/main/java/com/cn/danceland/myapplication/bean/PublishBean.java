package com.cn.danceland.myapplication.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by feng on 2017/11/14.
 * <p>
 * Copyright 2017 bejson.com
 */
/**
 * Copyright 2017 bejson.com
 */

/**
 * Auto-generated: 2017-11-15 9:47:39
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class PublishBean {

    private Integer author;
    private String content;
    private String enabled;
    private Integer id;
    private List<String> imgList;
    private String imgs;
    private Integer msg_type;
    private Integer privacy_type;
    private String publish_place;
    private Date publish_time;
    private Integer publish_type;
    private Integer share_id;
    private String title;
    private String vedio_url;
    private String vedio_img;
    public void setAuthor(int author) {
        this.author = author;
    }
    public int getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
    public String getEnabled() {
        return enabled;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
    public List<String> getImgList() {
        return imgList;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
    public String getImgs() {
        return imgs;
    }

    public void setMsgType(int msgType) {
        this.msg_type = msgType;
    }
    public int getMsgType() {
        return msg_type;
    }

    public void setprivacy_type(int privacy_type) {
        this.privacy_type = privacy_type;
    }
    public int getprivacy_type() {
        return privacy_type;
    }

    public void setPublishPlace(String publishPlace) {
        this.publish_place = publishPlace;
    }
    public String getPublishPlace() {
        return publish_place;
    }

    public void setPublishTime(Date publishTime) {
        this.publish_time = publishTime;
    }
    public Date getPublishTime() {
        return publish_time;
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

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public String isEnabled() {
        return enabled;
    }

    public String getVedioUrl() {
        return vedio_url;
    }

    public void setVedioUrl(String vedioUrl) {
        this.vedio_url = vedioUrl;
    }

    public String getVedioImg() {
        return vedio_img;
    }

    public void setVedioImg(String vedioImg) {
        this.vedio_img = vedioImg;
    }

}
