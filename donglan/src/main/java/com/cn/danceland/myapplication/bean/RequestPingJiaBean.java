package com.cn.danceland.myapplication.bean;

/**
 * Created by shy on 2018/4/13 16:25
 * Email:644563767@qq.com
 * <p>
 * Copyright 2018 bejson.com
 */


import java.util.List;


public class RequestPingJiaBean {

    private boolean success;
    private String errorMsg;
    private String code;
    private Data data;

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public class Data {

        private List<Content> content;
        private int number;
        private int size;
        private int totalElements;
        private int totalPages;
        private boolean last;
        private int numberOfElements;

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

        public void setSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public boolean getLast() {
            return last;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public class Content {

            private String id;
            private String branch_id;
            private String member_id;
            private String member_no;
            private int type;
            private int bus_id;
            private long create_date;
            private String content;
            private String course_type_id;
            private String course_type_score;
            private String employee_id;
            private float employee_score;
            private String room_id;
            private String room_score;
            private String self_avatar_path;
            private String nick_name;

            @Override
            public String toString() {
                return "Content{" +
                        "id='" + id + '\'' +
                        ", branch_id='" + branch_id + '\'' +
                        ", member_id='" + member_id + '\'' +
                        ", member_no='" + member_no + '\'' +
                        ", type=" + type +
                        ", bus_id=" + bus_id +
                        ", create_date=" + create_date +
                        ", content='" + content + '\'' +
                        ", course_type_id='" + course_type_id + '\'' +
                        ", course_type_score='" + course_type_score + '\'' +
                        ", employee_id='" + employee_id + '\'' +
                        ", employee_score=" + employee_score +
                        ", room_id='" + room_id + '\'' +
                        ", room_score='" + room_score + '\'' +
                        ", self_avatar_path='" + self_avatar_path + '\'' +
                        ", nick_name='" + nick_name + '\'' +
                        '}';
            }

            public String getSelf_avatar_path() {
                return self_avatar_path;
            }

            public void setSelf_avatar_path(String self_avatar_path) {
                this.self_avatar_path = self_avatar_path;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public void setMember_no(String member_no) {
                this.member_no = member_no;
            }

            public String getMember_no() {
                return member_no;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getType() {
                return type;
            }

            public void setBus_id(int bus_id) {
                this.bus_id = bus_id;
            }

            public int getBus_id() {
                return bus_id;
            }

            public void setCreate_date(long create_date) {
                this.create_date = create_date;
            }

            public long getCreate_date() {
                return create_date;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getContent() {
                return content;
            }

            public void setCourse_type_id(String course_type_id) {
                this.course_type_id = course_type_id;
            }

            public String getCourse_type_id() {
                return course_type_id;
            }

            public void setCourse_type_score(String course_type_score) {
                this.course_type_score = course_type_score;
            }

            public String getCourse_type_score() {
                return course_type_score;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getBranch_id() {
                return branch_id;
            }

            public void setBranch_id(String branch_id) {
                this.branch_id = branch_id;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getEmployee_id() {
                return employee_id;
            }

            public void setEmployee_id(String employee_id) {
                this.employee_id = employee_id;
            }

            public float getEmployee_score() {
                return employee_score;
            }

            public void setEmployee_score(float employee_score) {
                this.employee_score = employee_score;
            }

            public String getRoom_id() {
                return room_id;
            }

            public void setRoom_id(String room_id) {
                this.room_id = room_id;
            }

            public String getRoom_score() {
                return room_score;
            }

            public void setRoom_score(String room_score) {
                this.room_score = room_score;
            }
        }


    }
}
