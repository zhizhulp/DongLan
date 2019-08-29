package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by yxx on 2018-09-13.
 */

public class BCAQuestionBean {

    private boolean success;
    private String errorMsg;
    private List<Data> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String type;// 7：体测分析-体能须知
        private String centent;// 一级
        private String order_no;
        private List<ChildData> options;// 二级

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCentent() {
            return centent;
        }

        public void setCentent(String centent) {
            this.centent = centent;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public List<ChildData> getOptions() {
            return options;
        }

        public void setOptions(List<ChildData> options) {
            this.options = options;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "type='" + type +
                    "order_no='" + order_no +
                    "centent='" + centent +
                    "options='" + options +
                    '}';
        }
    }

    public static class ChildData {
        private String title;
        private String order_no;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        @Override
        public String toString() {
            return "ChildData{" +
                    "order_no='" + order_no +
                    "title='" + title +
                    '}';
        }
    }
}
