package com.cn.danceland.myapplication.bean;

/**
 * 我想咨询
 * //意向类型
 * byte CONSULTATION_TYPE_JOIN = 1; //托管 加盟
 * byte CONSULTATION_TYPE_TRAINING = 2; //培训教练
 * byte CONSULTATION_TYPE_SOFT = 3; //购买 软件
 * <p>
 * //合作类型
 * byte CONSULTATION_SUB_TYPE_JOIN = 1;//健身房加盟
 * byte CONSULTATION_SUB_TYPE_COO = 2; //健身房合作
 * byte CONSULTATION_SUB_TYPE_MRG = 3; //健身房管理
 * <p>
 * //学习类型
 * byte CONSULTATION_SUB_TYPE_SIGLE_COURSE = 1;//私教培训
 * byte CONSULTATION_SUB_TYPE_GYM = 2;//操课培训
 * byte CONSULTATION_SUB_TYPE_ALL = 3;//全能培训
 * <p>
 * //软件类型
 * byte CONSULTATION_SUB_TYPE_TEAM = 1 ;//团队版
 * byte CONSULTATION_SUB_TYPE_ENTE = 2 ;//企业版
 * byte CONSULTATION_SUB_TYPE_FLAG = 3 ;//旗舰版
 * <p>
 * Created by yxx on 22018/8/30.
 */
public class ConsultBean {
    private String status;//状态
    private String create_time;//咨询时间
    private String accept_time;//认领时间
    private String type;// 意向类型  1 2 3
    private String sub_type;// sub_type 意向子类型
    private String cname;// 姓名
    private String gender;// 性别
    private String phone_no;// 手机号
    private String remark;// 备注

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public String getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(String accept_time) {
        this.accept_time = accept_time;
    }

    @Override
    public String toString() {
        return "ConsultBean{" +
                "status='" + status + '\'' +
                ", create_time='" + create_time + '\'' +
                ", accept_time='" + accept_time + '\'' +
                ", type='" + type + '\'' +
                ", sub_type='" + sub_type + '\'' +
                ", cname='" + cname + '\'' +
                ", gender='" + gender + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
