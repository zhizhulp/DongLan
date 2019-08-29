package com.cn.danceland.myapplication.bean;

public class RequestSimpleRemarkBean {

    private boolean success;
    private String errorMsg;
    private String code;
    private RemarkData data;

    @Override
    public String toString() {
        return "RequestSimpleRemarkBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RemarkData getData() {
        return data;
    }

    public void setData(RemarkData data) {
        this.data = data;
    }

    public class RemarkData {
        private String group_course_table_remark;

        @Override
        public String toString() {
            return "RemarkData{" +
                    "group_course_table_remark='" + group_course_table_remark + '\'' +
                    '}';
        }

        public String getGroup_course_table_remark() {
            return group_course_table_remark;
        }

        public void setGroup_course_table_remark(String group_course_table_remark) {
            this.group_course_table_remark = group_course_table_remark;
        }
    }
}