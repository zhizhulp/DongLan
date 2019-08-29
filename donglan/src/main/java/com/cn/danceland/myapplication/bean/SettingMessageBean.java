package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by yxx on 2018-09-13.
 */

public class SettingMessageBean {

    private boolean success;
    private String errorMsg;
    private int code;
    private List<Data> data;

    @Override
    public String toString() {
        return "SettingMessageBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public boolean getSuccess() {
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String label;
        private String category;
        private boolean checked;

        @Override
        public String toString() {
            return "Data{" +
                    "label='" + label + '\'' +
                    ", category='" + category + '\'' +
                    ", checked=" + checked +
                    '}';
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public boolean getChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }
}
