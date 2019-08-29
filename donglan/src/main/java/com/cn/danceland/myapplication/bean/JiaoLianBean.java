package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/2/27.
 */

public class JiaoLianBean implements Serializable {
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

        private Integer id;
        private Integer course_type_id;
        private Integer employee_id;
        private String employee_name;
        private Integer delete_remark;
        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setCourse_type_id(int course_type_id) {
            this.course_type_id = course_type_id;
        }
        public int getCourse_type_id() {
            return course_type_id;
        }

        public void setEmployee_id(int employee_id) {
            this.employee_id = employee_id;
        }
        public int getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }
        public String getEmployee_name() {
            return employee_name;
        }

        public void setDelete_remark(int delete_remark) {
            this.delete_remark = delete_remark;
        }
        public int getDelete_remark() {
            return delete_remark;
        }

    }
}
