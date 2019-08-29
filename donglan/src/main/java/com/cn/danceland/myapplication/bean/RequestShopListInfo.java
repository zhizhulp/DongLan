package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2017/12/13 13:30
 * Email:644563767@qq.com
 */

public class RequestShopListInfo {

    private boolean success;
    private String errorMsg;
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

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data {

        private String branch_id;
        private String description;
        private String logo;
        private int enabled;
        private int status;
        private double lat;
        private double lng;
        private String telphone_no;
        private String bname;
        private String address;
        private int zone_code;
        private int follows;
        private String logo_url;

        public void setBranch_id(String branch_id) {
            this.branch_id = branch_id;
        }

        public String getBranch_id() {
            return branch_id;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogo() {
            return logo;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLat() {
            return lat;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLng() {
            return lng;
        }

        public void setTelphone_no(String telphone_no) {
            this.telphone_no = telphone_no;
        }

        public String getTelphone_no() {
            return telphone_no;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }

        public String getBname() {
            return bname;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }

        public void setZone_code(int zone_code) {
            this.zone_code = zone_code;
        }

        public int getZone_code() {
            return zone_code;
        }

        public void setFollows(int follows) {
            this.follows = follows;
        }

        public int getFollows() {
            return follows;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public String getLogo_url() {
            return logo_url;
        }

    }
}