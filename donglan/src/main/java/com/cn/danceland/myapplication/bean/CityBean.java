package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2017/11/6.
 */

public class CityBean {

    private String value;
    private String label;
    private List children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }



    @Override
    public String toString() {
        return "CityBean{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", children=" + children +
                '}';
    }
}
