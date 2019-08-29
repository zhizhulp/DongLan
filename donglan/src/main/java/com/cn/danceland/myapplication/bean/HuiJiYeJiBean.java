package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shy on 2018/12/5 16:59
 * Email:644563767@qq.com
 */


public class HuiJiYeJiBean implements Serializable {
    private boolean success;
    private String errorMsg;
    private int code;
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

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "HuiJiYeJiBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public static class Data implements Serializable {

        private float newcard;
        private float leaselocker;
        private String emp_name;
        private String employee_id;
        private String avatar_path;
        private String avatar_url;
        private float allccourse;//教练总计 ,
        private String groupcourse;//(number, optional): 团体私教 ,
        private String singlecourse;// (number, optional): 单人私教

        private float total;//会籍总计

        @Override
        public String toString() {
            return "Data{" +
                    "newcard=" + newcard +
                    ", leaselocker=" + leaselocker +
                    ", emp_name='" + emp_name + '\'' +
                    ", employee_id='" + employee_id + '\'' +
                    ", avatar_path='" + avatar_path + '\'' +
                    ", avatar_url='" + avatar_url + '\'' +
                    ", allccourse=" + allccourse +
                    ", groupcourse='" + groupcourse + '\'' +
                    ", singlecourse='" + singlecourse + '\'' +
                    ", total=" + total +
                    '}';
        }

        public float getAllccourse() {
            return allccourse;
        }

        public void setAllccourse(float allccourse) {
            this.allccourse = allccourse;
        }

        public String getGroupcourse() {
            return groupcourse;
        }

        public void setGroupcourse(String groupcourse) {
            this.groupcourse = groupcourse;
        }

        public String getSinglecourse() {
            return singlecourse;
        }

        public void setSinglecourse(String singlecourse) {
            this.singlecourse = singlecourse;
        }

        public float getNewcard() {
            return newcard;
        }

        public void setNewcard(float newcard) {
            this.newcard = newcard;
        }

        public float getLeaselocker() {
            return leaselocker;
        }

        public void setLeaselocker(float leaselocker) {
            this.leaselocker = leaselocker;
        }

        public String getEmp_name() {
            return emp_name;
        }

        public void setEmp_name(String emp_name) {
            this.emp_name = emp_name;
        }

        public String getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_id(String employee_id) {
            this.employee_id = employee_id;
        }

        public String getAvatar_path() {
            return avatar_path;
        }

        public void setAvatar_path(String avatar_path) {
            this.avatar_path = avatar_path;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }
    }
}
