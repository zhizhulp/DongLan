package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/4/16 13:41
 * Email:644563767@qq.com
 * 资质认证请求实体类
 */


public class RequestZzrzLIstBean {


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

        private String id;
        private String person_id;
        private String name;
        private String pic_url;
        private String indate;
        private String gain_date;
        private String award_dept;
        private String img_url;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setPerson_id(String person_id) {
            this.person_id = person_id;
        }
        public String getPerson_id() {
            return person_id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
        public String getPic_url() {
            return pic_url;
        }

        public void setIndate(String indate) {
            this.indate = indate;
        }
        public String getIndate() {
            return indate;
        }

        public void setGain_date(String gain_date) {
            this.gain_date = gain_date;
        }
        public String getGain_date() {
            return gain_date;
        }

        public void setAward_dept(String award_dept) {
            this.award_dept = award_dept;
        }
        public String getAward_dept() {
            return award_dept;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }
        public String getImg_url() {
            return img_url;
        }

    }
}
