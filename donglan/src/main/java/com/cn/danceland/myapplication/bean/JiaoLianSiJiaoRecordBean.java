package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2018/3/12.
 */
public class JiaoLianSiJiaoRecordBean {

    private boolean success;
    private String errorMsg;
    private int code;
    private List<Data> data;

    @Override
    public String toString() {
        return "JiaoLianSiJiaoRecordBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public class Data {

        private String id;
        private long course_date;
        private int appointment_type;
        private long start_time;
        private long end_time;
        private int member_course_id;
        private String course_type_name;
        private int status;
        private String member_name;
        private String employee_name;
        private int count;
        private int appoint_count;
        private int course_category;

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", course_date=" + course_date +
                    ", appointment_type=" + appointment_type +
                    ", start_time=" + start_time +
                    ", end_time=" + end_time +
                    ", member_course_id=" + member_course_id +
                    ", course_type_name='" + course_type_name + '\'' +
                    ", status=" + status +
                    ", member_name='" + member_name + '\'' +
                    ", employee_name='" + employee_name + '\'' +
                    ", count=" + count +
                    ", appoint_count=" + appoint_count +
                    ", course_category=" + course_category +
                    '}';
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setCourse_date(long course_date) {
            this.course_date = course_date;
        }
        public long getCourse_date() {
            return course_date;
        }

        public void setAppointment_type(int appointment_type) {
            this.appointment_type = appointment_type;
        }
        public int getAppointment_type() {
            return appointment_type;
        }

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public void setMember_course_id(int member_course_id) {
            this.member_course_id = member_course_id;
        }
        public int getMember_course_id() {
            return member_course_id;
        }

        public void setCourse_type_name(String course_type_name) {
            this.course_type_name = course_type_name;
        }
        public String getCourse_type_name() {
            return course_type_name;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }
        public String getMember_name() {
            return member_name;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }
        public String getEmployee_name() {
            return employee_name;
        }

        public void setCount(int count) {
            this.count = count;
        }
        public int getCount() {
            return count;
        }

        public void setAppoint_count(int appoint_count) {
            this.appoint_count = appoint_count;
        }
        public int getAppoint_count() {
            return appoint_count;
        }

        public void setCourse_category(int course_category) {
            this.course_category = course_category;
        }
        public int getCourse_category() {
            return course_category;
        }

    }
}
