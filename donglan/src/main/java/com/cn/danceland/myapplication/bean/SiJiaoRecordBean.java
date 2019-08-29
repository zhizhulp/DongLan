package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2018/3/12.
 */

public class SiJiaoRecordBean implements Serializable{

    private Data data;
    private boolean success;
    private String errorMsg;
    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
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

    public class Content implements Serializable{

        private String initiator_name;
        private String confirm_name;
        private String employee_name;
        private long initiator_date;
        private int id;
        private int operater_id;
        private long confirm_date;
        private int course_type_id;
        private int initiator_id;
        private int employee_id;
        private String member_no;
        private String sign_member_id;
        private int branch_id;
        private String branch_name;
        private String course_type_name;
        private String count;
        private int status;
        private long course_date;
        private int member_id;
        private int appointment_type;
        private int member_course_id;
        private String sign_date;
        private int end_time;
        private int confirm_id;
        private int start_time;
        private String member_name;
        private String phone_no;
        private String cancel_date;
        private String category;
        private int week;
        private int evaluate_id;
        private String employee_birthday;
        private String employee_gender;
        private String employee_avatar_url;

        public String getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Content{" +
                    "initiator_name='" + initiator_name + '\'' +
                    ", confirm_name='" + confirm_name + '\'' +
                    ", employee_name='" + employee_name + '\'' +
                    ", initiator_date=" + initiator_date +
                    ", id=" + id +
                    ", operater_id=" + operater_id +
                    ", confirm_date=" + confirm_date +
                    ", course_type_id=" + course_type_id +
                    ", initiator_id=" + initiator_id +
                    ", employee_id=" + employee_id +
                    ", member_no='" + member_no + '\'' +
                    ", sign_member_id='" + sign_member_id + '\'' +
                    ", branch_id=" + branch_id +
                    ", branch_name='" + branch_name + '\'' +
                    ", course_type_name='" + course_type_name + '\'' +
                    ", count='" + count + '\'' +
                    ", status=" + status +
                    ", course_date=" + course_date +
                    ", member_id=" + member_id +
                    ", appointment_type=" + appointment_type +
                    ", member_course_id=" + member_course_id +
                    ", sign_date='" + sign_date + '\'' +
                    ", end_time=" + end_time +
                    ", confirm_id=" + confirm_id +
                    ", start_time=" + start_time +
                    ", member_name='" + member_name + '\'' +
                    ", phone_no='" + phone_no + '\'' +
                    ", cancel_date='" + cancel_date + '\'' +
                    ", category='" + category + '\'' +
                    ", week=" + week +
                    ", evaluate_id=" + evaluate_id +
                    ", employee_birthday='" + employee_birthday + '\'' +
                    ", employee_gender='" + employee_gender + '\'' +
                    ", employee_avatar_url='" + employee_avatar_url + '\'' +
                    '}';
        }

        public String getEmployee_birthday() {
            return employee_birthday;
        }

        public void setEmployee_birthday(String employee_birthday) {
            this.employee_birthday = employee_birthday;
        }

        public String getEmployee_gender() {
            return employee_gender;
        }

        public void setEmployee_gender(String employee_gender) {
            this.employee_gender = employee_gender;
        }

        public String getEmployee_avatar_url() {
            return employee_avatar_url;
        }

        public void setEmployee_avatar_url(String employee_avatar_url) {
            this.employee_avatar_url = employee_avatar_url;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getEvaluate_id() {
            return evaluate_id;
        }

        public void setEvaluate_id(int evaluate_id) {
            this.evaluate_id = evaluate_id;
        }

        public void setInitiator_name(String initiator_name) {
            this.initiator_name = initiator_name;
        }
        public String getInitiator_name() {
            return initiator_name;
        }

        public void setConfirm_name(String confirm_name) {
            this.confirm_name = confirm_name;
        }
        public String getConfirm_name() {
            return confirm_name;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }
        public String getEmployee_name() {
            return employee_name;
        }

        public void setInitiator_date(long initiator_date) {
            this.initiator_date = initiator_date;
        }
        public long getInitiator_date() {
            return initiator_date;
        }

        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }

        public void setOperater_id(int operater_id) {
            this.operater_id = operater_id;
        }
        public int getOperater_id() {
            return operater_id;
        }

        public void setConfirm_date(long confirm_date) {
            this.confirm_date = confirm_date;
        }
        public long getConfirm_date() {
            return confirm_date;
        }

        public void setCourse_type_id(int course_type_id) {
            this.course_type_id = course_type_id;
        }
        public int getCourse_type_id() {
            return course_type_id;
        }

        public void setInitiator_id(int initiator_id) {
            this.initiator_id = initiator_id;
        }
        public int getInitiator_id() {
            return initiator_id;
        }

        public void setEmployee_id(int employee_id) {
            this.employee_id = employee_id;
        }
        public int getEmployee_id() {
            return employee_id;
        }

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }
        public String getMember_no() {
            return member_no;
        }

        public void setSign_member_id(String sign_member_id) {
            this.sign_member_id = sign_member_id;
        }
        public String getSign_member_id() {
            return sign_member_id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }
        public int getBranch_id() {
            return branch_id;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }
        public String getBranch_name() {
            return branch_name;
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

        public void setCourse_date(long course_date) {
            this.course_date = course_date;
        }
        public long getCourse_date() {
            return course_date;
        }

        public void setMember_id(int member_id) {
            this.member_id = member_id;
        }
        public int getMember_id() {
            return member_id;
        }

        public void setAppointment_type(int appointment_type) {
            this.appointment_type = appointment_type;
        }
        public int getAppointment_type() {
            return appointment_type;
        }

        public void setMember_course_id(int member_course_id) {
            this.member_course_id = member_course_id;
        }
        public int getMember_course_id() {
            return member_course_id;
        }

        public void setSign_date(String sign_date) {
            this.sign_date = sign_date;
        }
        public String getSign_date() {
            return sign_date;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }
        public int getEnd_time() {
            return end_time;
        }

        public void setConfirm_id(int confirm_id) {
            this.confirm_id = confirm_id;
        }
        public int getConfirm_id() {
            return confirm_id;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }
        public int getStart_time() {
            return start_time;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }
        public String getMember_name() {
            return member_name;
        }

        public void setCancel_date(String cancel_date) {
            this.cancel_date = cancel_date;
        }
        public String getCancel_date() {
            return cancel_date;
        }

        public void setWeek(int week) {
            this.week = week;
        }
        public int getWeek() {
            return week;
        }

    }

    public class Data implements Serializable{

        private List<Content> content;
        private int number;
        private int numberOfElements;
        private boolean last;
        private int totalPages;
        private int totalElements;
        private int size;

        @Override
        public String toString() {
            return "Data{" +
                    "content=" + content +
                    ", number=" + number +
                    ", numberOfElements=" + numberOfElements +
                    ", last=" + last +
                    ", totalPages=" + totalPages +
                    ", totalElements=" + totalElements +
                    ", size=" + size +
                    '}';
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }
        public List<Content> getContent() {
            return content;
        }

        public void setNumber(int number) {
            this.number = number;
        }
        public int getNumber() {
            return number;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }
        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setLast(boolean last) {
            this.last = last;
        }
        public boolean getLast() {
            return last;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }
        public int getTotalElements() {
            return totalElements;
        }

        public void setSize(int size) {
            this.size = size;
        }
        public int getSize() {
            return size;
        }

    }
}
