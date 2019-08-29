package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2018/12/28 10:21
 * Email:644563767@qq.com
 */

/**
 * 请求服务获取信息bean
 */
public class ShareInfoFromServiceBean {
    public String course_type_id;
    public String employee_id;
    public String share_type;
    public String bus_id;
    public String room_id;

    @Override
    public String toString() {
        return "ShareInfoFromServiceBean{" +
                "course_type_id='" + course_type_id + '\'' +
                ", employee_id='" + employee_id + '\'' +
                ", share_type='" + share_type + '\'' +
                ", bus_id='" + bus_id + '\'' +
                ", room_id='" + room_id + '\'' +
                '}';
    }
}