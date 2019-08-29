package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/8/11 16:50
 * Email:644563767@qq.com
 */


public class SiJiaoYuYueBean {

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




    public static class Data {

        private String id;
        private int type;
        private String user_obj_id;
        private int start_time;
        private int end_time;
        private Long occ_date;
        private int occ_type;
        private String occ_obj_id;
        private int occ_obj_status;
        public Data() {
        }

        public Data(int start_time, int end_time, int occ_obj_status) {
            this.start_time = start_time;
            this.end_time = end_time;
            this.occ_obj_status = occ_obj_status;
        }
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUser_obj_id() {
            return user_obj_id;
        }

        public void setUser_obj_id(String user_obj_id) {
            this.user_obj_id = user_obj_id;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public Long getOcc_date() {
            return occ_date;
        }

        public void setOcc_date(Long occ_date) {
            this.occ_date = occ_date;
        }

        public int getOcc_type() {
            return occ_type;
        }

        public void setOcc_type(int occ_type) {
            this.occ_type = occ_type;
        }

        public String getOcc_obj_id() {
            return occ_obj_id;
        }

        public void setOcc_obj_id(String occ_obj_id) {
            this.occ_obj_id = occ_obj_id;
        }

        public int getOcc_obj_status() {
            return occ_obj_status;
        }

        public void setOcc_obj_status(int occ_obj_status) {
            this.occ_obj_status = occ_obj_status;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", type=" + type +
                    ", user_obj_id='" + user_obj_id + '\'' +
                    ", start_time=" + start_time +
                    ", end_time=" + end_time +
                    ", occ_date=" + occ_date +
                    ", occ_type=" + occ_type +
                    ", occ_obj_id='" + occ_obj_id + '\'' +
                    ", occ_obj_status=" + occ_obj_status +
                    '}';
        }
    }
}
