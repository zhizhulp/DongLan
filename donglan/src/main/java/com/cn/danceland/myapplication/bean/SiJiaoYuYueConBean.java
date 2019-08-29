package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by feng on 2018/3/7.
 */

public class SiJiaoYuYueConBean implements Serializable {
    private Integer appointment_type;
    private Integer branch_id;
    private String branch_name;
    private Date cancel_date;
    private Date confirm_date;
    private Integer confirm_id;
    private String confirm_name;
    private String course_date;
    private Integer course_type_id;
    private String course_type_name;
    private Integer employee_id;
    private String employee_name;
    private Long end_time;
    private Integer id;
    private Date initiator_date;
    private Integer initiator_id;
    private String initiator_name;
    private Integer member_course_id;
    private Integer member_id;
    private String member_name;
    private String member_no;
    private Integer operater_id;
    private Date sign_date;
    private Integer sign_member_id;
    private Long start_time;
    private Integer status;
    private Integer week;
    private Long start_date;
    private Long end_date;
    private Integer group_course_id;
    private String date;
    private Long course_date_gt;
    private Long course_date_lt;
    private Integer page;
    private Integer size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getCourse_date_gt() {
        return course_date_gt;
    }

    public void setCourse_date_gt(Long course_date_gt) {
        this.course_date_gt = course_date_gt;
    }

    public Long getCourse_date_lt() {
        return course_date_lt;
    }

    public void setCourse_date_lt(Long course_date_lt) {
        this.course_date_lt = course_date_lt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getGroup_course_id() {
        return group_course_id;
    }

    public void setGroup_course_id(Integer group_course_id) {
        this.group_course_id = group_course_id;
    }

    public Long getStart_date() {
        return start_date;
    }

    public void setStart_date(Long start_date) {
        this.start_date = start_date;
    }

    public Long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Long end_date) {
        this.end_date = end_date;
    }

    public Integer getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(Integer appointment_type) {
        this.appointment_type = appointment_type;
    }

    public void setBranch_id(Integer branch_id) {
        this.branch_id = branch_id;
    }
    public Integer getBranch_id() {
        return branch_id;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
    public String getBranch_name() {
        return branch_name;
    }

    public void setCancel_date(Date cancel_date) {
        this.cancel_date = cancel_date;
    }
    public Date getCancel_date() {
        return cancel_date;
    }

    public void setConfirm_date(Date confirm_date) {
        this.confirm_date = confirm_date;
    }
    public Date getConfirm_date() {
        return confirm_date;
    }

    public void setConfirm_id(Integer confirm_id) {
        this.confirm_id = confirm_id;
    }
    public Integer getConfirm_id() {
        return confirm_id;
    }

    public void setConfirm_name(String confirm_name) {
        this.confirm_name = confirm_name;
    }
    public String getConfirm_name() {
        return confirm_name;
    }

    public void setCourse_date(String course_date) {
        this.course_date = course_date;
    }
    public String getCourse_date() {
        return course_date;
    }

    public void setCourse_type_id(Integer course_type_id) {
        this.course_type_id = course_type_id;
    }
    public Integer getCourse_type_id() {
        return course_type_id;
    }

    public void setCourse_type_name(String course_type_name) {
        this.course_type_name = course_type_name;
    }
    public String getCourse_type_name() {
        return course_type_name;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }
    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
    public String getEmployee_name() {
        return employee_name;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }
    public Long getEnd_time() {
        return end_time;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setInitiator_date(Date initiator_date) {
        this.initiator_date = initiator_date;
    }
    public Date getInitiator_date() {
        return initiator_date;
    }

    public void setInitiator_id(Integer initiator_id) {
        this.initiator_id = initiator_id;
    }
    public Integer getInitiator_id() {
        return initiator_id;
    }

    public void setInitiator_name(String initiator_name) {
        this.initiator_name = initiator_name;
    }
    public String getInitiator_name() {
        return initiator_name;
    }

    public void setMember_course_id(Integer member_course_id) {
        this.member_course_id = member_course_id;
    }
    public Integer getMember_course_id() {
        return member_course_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }
    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }
    public String getMember_name() {
        return member_name;
    }

    public void setMember_no(String member_no) {
        this.member_no = member_no;
    }
    public String getMember_no() {
        return member_no;
    }

    public void setOperater_id(Integer operater_id) {
        this.operater_id = operater_id;
    }
    public Integer getOperater_id() {
        return operater_id;
    }

    public void setSign_date(Date sign_date) {
        this.sign_date = sign_date;
    }
    public Date getSign_date() {
        return sign_date;
    }

    public void setSign_member_id(Integer sign_member_id) {
        this.sign_member_id = sign_member_id;
    }
    public Integer getSign_member_id() {
        return sign_member_id;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }
    public Long getStart_time() {
        return start_time;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getStatus() {
        return status;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }
    public Integer getWeek() {
        return week;
    }

}
