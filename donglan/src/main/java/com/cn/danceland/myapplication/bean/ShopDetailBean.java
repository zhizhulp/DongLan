package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2017/12/6.
 */

public class ShopDetailBean {


    /**
     * success : true
     * errorMsg : null
     * code : null
     * data : {"branch_id":7,"league_id":1,"name":"再来测试门店","real_name":"再来测试门店","address":"大师傅发射点发生","real_address":"大师傅发射点发生","status":0,"zone_code":120103,"description":"手动阀手动阀的","logo_path":"branch_imgs/37ec60e2ff704841b13939260a0fcd8f.png","enabled":1,"lat":39.7295,"lng":116.147,"telphone":"001-1111111","follows":0,"pictures":"[\"branch_imgs/386c7ae2018042abbc3edae107722f4f_800X350.png\",\"branch_imgs/5fd9d5fd12e94e4f99a59bdab7925884_800X350.png\"]","remark":"大夫电风扇","create_time":1527134616000,"creator":1,"logo_url":"http://192.168.1.93/branch_imgs/37ec60e2ff704841b13939260a0fcd8f.png","league_name":"111","create_name":null,"photo_url":["http://192.168.1.93/branch_imgs/386c7ae2018042abbc3edae107722f4f_800X350.png","http://192.168.1.93/branch_imgs/5fd9d5fd12e94e4f99a59bdab7925884_800X350.png"]}
     */

    private boolean success;
    private Object errorMsg;
    private Object code;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * branch_id : 7
         * league_id : 1
         * name : 再来测试门店
         * real_name : 再来测试门店
         * address : 大师傅发射点发生
         * real_address : 大师傅发射点发生
         * status : 0
         * zone_code : 120103
         * description : 手动阀手动阀的
         * logo_path : branch_imgs/37ec60e2ff704841b13939260a0fcd8f.png
         * enabled : 1
         * lat : 39.7295
         * lng : 116.147
         * telphone : 001-1111111
         * follows : 0
         * pictures : ["branch_imgs/386c7ae2018042abbc3edae107722f4f_800X350.png","branch_imgs/5fd9d5fd12e94e4f99a59bdab7925884_800X350.png"]
         * remark : 大夫电风扇
         * create_time : 1527134616000
         * creator : 1
         * logo_url : http://192.168.1.93/branch_imgs/37ec60e2ff704841b13939260a0fcd8f.png
         * league_name : 111
         * create_name : null
         * photo_url : ["http://192.168.1.93/branch_imgs/386c7ae2018042abbc3edae107722f4f_800X350.png","http://192.168.1.93/branch_imgs/5fd9d5fd12e94e4f99a59bdab7925884_800X350.png"]
         */

        private String branch_id;
        private String league_id;
        private String name;
        private String real_name;
        private String address;
        private String real_address;
        private String status;
        private String zone_code;
        private String description;
        private String logo_path;
        private String enabled;
        private double lat;
        private double lng;
        private String telphone;
        private String follows;
        private String pictures;
        private String remark;
        private String create_time;
        private String creator;
        private String logo_url;
        private String open_time;
        private String close_time;
        private String league_name;
        private Object create_name;
        private List<String> photo_url;

        public String getOpen_time() {
            return open_time;
        }

        public void setOpen_time(String open_time) {
            this.open_time = open_time;
        }

        public String getClose_time() {
            return close_time;
        }

        public void setClose_time(String close_time) {
            this.close_time = close_time;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getLeague_id() {
            return league_id;
        }

        public void setLeague_id(String league_id) {
            this.league_id = league_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getReal_address() {
            return real_address;
        }

        public void setReal_address(String real_address) {
            this.real_address = real_address;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getZone_code() {
            return zone_code;
        }

        public void setZone_code(String zone_code) {
            this.zone_code = zone_code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLogo_path() {
            return logo_path;
        }

        public void setLogo_path(String logo_path) {
            this.logo_path = logo_path;
        }

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getFollows() {
            return follows;
        }

        public void setFollows(String follows) {
            this.follows = follows;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getLeague_name() {
            return league_name;
        }

        public void setLeague_name(String league_name) {
            this.league_name = league_name;
        }

        public Object getCreate_name() {
            return create_name;
        }

        public void setCreate_name(Object create_name) {
            this.create_name = create_name;
        }

        public List<String> getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(List<String> photo_url) {
            this.photo_url = photo_url;
        }
    }
}
