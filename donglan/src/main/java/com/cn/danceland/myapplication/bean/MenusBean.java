package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2017/12/15.
 */

public class MenusBean {
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

    @Override
    public String toString() {
        return "MenusBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {

        private int id;
        private int type;
        private String icon;
        private String name;
        private int parent_id;
        private String remark;
        private int status;
        private String fun_type;

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", type=" + type +
                    ", icon='" + icon + '\'' +
                    ", name='" + name + '\'' +
                    ", parent_id=" + parent_id +
                    ", remark='" + remark + '\'' +
                    ", status=" + status +
                    ", fun_type='" + fun_type + '\'' +
                    '}';
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
        public String getIcon() {
            return icon;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }
        public int getParent_id() {
            return parent_id;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
        public String getRemark() {
            return remark;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setFun_type(String fun_type) {
            this.fun_type = fun_type;
        }
        public String getFun_type() {
            return fun_type;
        }

    }


}
