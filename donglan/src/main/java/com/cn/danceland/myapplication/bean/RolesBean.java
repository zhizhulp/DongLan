package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by feng on 2018/3/21.
 */

public class RolesBean implements Serializable {

    private int delete_remark;
    private int department_id;
    private String department_name;
    private int id;
    private String name;
    private String remark;
    private String role_type;
    public void setDelete_remark(int delete_remark) {
        this.delete_remark = delete_remark;
    }
    public int getDelete_remark() {
        return delete_remark;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }
    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
    public String getDepartment_name() {
        return department_name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRemark() {
        return remark;
    }

    public void setRole_type(String role_type) {
        this.role_type = role_type;
    }
    public String getRole_type() {
        return role_type;
    }


}
