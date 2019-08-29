package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/8/24 16:05
 * Email:644563767@qq.com
 */


public class RequsetHYTJBean {

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


    /**
     * Auto-generated: 2018-08-24 15:53:22
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class Content {

        private String id;
        private String branch_id;
        private String member_id;
        private String member_no;
        private long create_date;
        private int introduce_member_id;
        private String introduce_member_no;
        private String name;
        private int gender;
        private int status;
        private int delete_remark;
        private String phone_no;
        private String member_name;
        private String member_phone;

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

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }
        public String getMember_no() {
            return member_no;
        }

        public void setCreate_date(long create_date) {
            this.create_date = create_date;
        }
        public long getCreate_date() {
            return create_date;
        }

        public void setIntroduce_member_id(int introduce_member_id) {
            this.introduce_member_id = introduce_member_id;
        }
        public int getIntroduce_member_id() {
            return introduce_member_id;
        }

        public void setIntroduce_member_no(String introduce_member_no) {
            this.introduce_member_no = introduce_member_no;
        }
        public String getIntroduce_member_no() {
            return introduce_member_no;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
        public int getGender() {
            return gender;
        }

        public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setDelete_remark(int delete_remark) {
            this.delete_remark = delete_remark;
        }
        public int getDelete_remark() {
            return delete_remark;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }
        public String getPhone_no() {
            return phone_no;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }
        public String getMember_name() {
            return member_name;
        }

        public void setMember_phone(String member_phone) {
            this.member_phone = member_phone;
        }
        public String getMember_phone() {
            return member_phone;
        }

    }
}
