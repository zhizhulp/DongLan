package com.cn.danceland.myapplication.bean;

import java.io.Serializable;

/**
 * Created by shy on 2018/12/28 09:35
 * Email:644563767@qq.com
 */

  /*
    保存信息bean
     */
public class ShareBean implements Serializable{
    public String title;
    public String url;
    public String bus_id;
    public String img_url;
    public int type;
    public int target;
    public String description;

    @Override
    public String toString() {
        return "ShareBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", bus_id='" + bus_id + '\'' +
                ", img_url='" + img_url + '\'' +
                ", type=" + type +
                ", target=" + target +
                ", description='" + description + '\'' +
                '}';
    }
}