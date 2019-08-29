package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shy on 2018/12/5 16:59
 * Email:644563767@qq.com
 */


public class HuiJiYeWuBean implements Serializable{
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

    public class Data implements Serializable{

//        private float newcard;
//        private float leaselocker;
        private String emp_name;
        private String employee_id;
        private String avatar_path;
        private String avatar_url;
        private long total;
        private   long   newGuest;
        private    long     visitGuest;
        private    long    visitMember ;
        private    long    all ;
        private    long    jihua ;
        private    long    tice ;
        private    long    ticefenxi ;

//        all (integer, optional): 总计 ,
//        jihua (integer, optional): 健身计划 ,
//        tice (integer, optional): 体测 ,
//        ticefenxi (integer, optional): 体测分析


        public long getJihua() {
            return jihua;
        }

        public void setJihua(long jihua) {
            this.jihua = jihua;
        }

        public long getTice() {
            return tice;
        }

        public void setTice(long tice) {
            this.tice = tice;
        }

        public long getTicefenxi() {
            return ticefenxi;
        }

        public void setTicefenxi(long ticefenxi) {
            this.ticefenxi = ticefenxi;
        }

        public long getAll() {
            return all;
        }

        public void setAll(long all) {
            this.all = all;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public long getNewGuest() {
            return newGuest;
        }

        public void setNewGuest(long newGuest) {
            this.newGuest = newGuest;
        }

        public long getVisitGuest() {
            return visitGuest;
        }

        public void setVisitGuest(long visitGuest) {
            this.visitGuest = visitGuest;
        }

        public long getVisitMember() {
            return visitMember;
        }

        public void setVisitMember(long visitMember) {
            this.visitMember = visitMember;
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

        public long getTotal() {
            return total;
        }
    }
}
