/**
 * Copyright 2017 bejson.com
 */
package com.cn.danceland.myapplication.bean;

/**
 * 用户资料
 */
public class UserInfoBean {

    private String id;// 主键
    private String cName; // 中文名称 Varchar(50)
    private String phone; // 手机号 Varchar(20)
    private String nickName; // 昵称 Vharchar(50)
    private String height; // 身高 Float
    private String weight; // 体重 Float
    private String regDate; // 注册日期 Date
    private String birthday; // 出生日期
    private String zoneCode; // 区化编码
    private String enabled; // 是否启用
    private String auth; // 身份
    private String branchId; // 所属门店
    private String gender; // 用户性别
    private String status; // 在线状态
    private String memberNo; // 会员编号 Varchar(50)
    private String password;// 登录密码
    private String romType; // 平台
    private String userName; // 用户自定义的会员号 Varchar(50)
    private String avatarPath; // 头像物理路径 Varchar(200)
    private String selfAvatarPath; // 个性头像物理路径 Varchar(200)
    private String teachMumberId; // 指导教练
    private String awareWay; // 了解途径
    private String adminMumberId; // 所属会籍
    private String remark; // 备注

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "id=" + id +
                ", cName='" + cName + '\'' +
                ", phone='" + phone + '\'' +
                ", nickName='" + nickName + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", regDate=" + regDate +
                ", birthday='" + birthday + '\'' +
                ", zoneCode='" + zoneCode + '\'' +
                ", enabled=" + enabled +
                ", auth=" + auth +
                ", branchId='" + branchId + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", memberNo='" + memberNo + '\'' +
                ", password='" + password + '\'' +
                ", romType=" + romType +
                ", userName='" + userName + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", selfAvatarPath='" + selfAvatarPath + '\'' +
                ", teachMumberId='" + teachMumberId + '\'' +
                ", awareWay='" + awareWay + '\'' +
                ", adminMumberId='" + adminMumberId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRomType() {
        return romType;
    }

    public void setRomType(String romType) {
        this.romType = romType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getSelfAvatarPath() {
        return selfAvatarPath;
    }

    public void setSelfAvatarPath(String selfAvatarPath) {
        this.selfAvatarPath = selfAvatarPath;
    }

    public String getTeachMumberId() {
        return teachMumberId;
    }

    public void setTeachMumberId(String teachMumberId) {
        this.teachMumberId = teachMumberId;
    }

    public String getAwareWay() {
        return awareWay;
    }

    public void setAwareWay(String awareWay) {
        this.awareWay = awareWay;
    }

    public String getAdminMumberId() {
        return adminMumberId;
    }

    public void setAdminMumberId(String adminMumberId) {
        this.adminMumberId = adminMumberId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}