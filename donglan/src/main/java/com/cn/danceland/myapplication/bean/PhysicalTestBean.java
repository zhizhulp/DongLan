package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/4/11.
 */

public class PhysicalTestBean implements Serializable {

    private String second_title;
    private List<String> attention;
    private List<Action_detail> action_detail;
    private String main_pic_path;
    private String main_pic_url;
    private String main_title;
    private String steps;

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public void setSecond_title(String second_title) {
        this.second_title = second_title;
    }
    public String getSecond_title() {
        return second_title;
    }

    public void setAttention(List<String> attention) {
        this.attention = attention;
    }
    public List<String> getAttention() {
        return attention;
    }

    public void setAction_detail(List<Action_detail> action_detail) {
        this.action_detail = action_detail;
    }
    public List<Action_detail> getAction_detail() {
        return action_detail;
    }

    public void setMain_pic_path(String main_pic_path) {
        this.main_pic_path = main_pic_path;
    }
    public String getMain_pic_path() {
        return main_pic_path;
    }

    public void setMain_pic_url(String main_pic_url) {
        this.main_pic_url = main_pic_url;
    }
    public String getMain_pic_url() {
        return main_pic_url;
    }

    public void setMain_title(String main_title) {
        this.main_title = main_title;
    }
    public String getMain_title() {
        return main_title;
    }

    @Override
    public String toString() {
        return "PhysicalTestBean{" +
                "second_title='" + second_title + '\'' +
                ", attention=" + attention +
                ", action_detail=" + action_detail +
                ", main_pic_path='" + main_pic_path + '\'' +
                ", main_pic_url='" + main_pic_url + '\'' +
                ", main_title='" + main_title + '\'' +
                ", steps='" + steps + '\'' +
                '}';
    }

    public class Action_detail {

        private String note;
        private String pic_path;
        private String pic_url;
        public void setNote(String note) {
            this.note = note;
        }
        public String getNote() {
            return note;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }
        public String getPic_path() {
            return pic_path;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
        public String getPic_url() {
            return pic_url;
        }

        @Override
        public String toString() {
            return "Action_detail{" +
                    "note='" + note + '\'' +
                    ", pic_path='" + pic_path + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    '}';
        }
    }

}
