package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by yxx on 2018-09-27.
 */

public class FitnessTestHistoryBean {
    private boolean success;
    private String errorMsg;
    private String code;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FitnessTestHistoryBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
        private List<Content> content;
        private int number;
        private int size;
        private int totalElements;
        private int numberOfElements;
        private int totalPages;
        private boolean last;

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "content=" + content +
                    ", number=" + number +
                    ", size=" + size +
                    ", totalElements=" + totalElements +
                    ", numberOfElements=" + numberOfElements +
                    ", totalPages=" + totalPages +
                    ", last=" + last +
                    '}';
        }
    }

    public static class Content {
        private Long id;
        private Long teach_id;
        private String teach_name;
        private String test_time;
        private String member_id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getTeach_id() {
            return teach_id;
        }

        public void setTeach_id(Long teach_id) {
            this.teach_id = teach_id;
        }

        public String getTeach_name() {
            return teach_name;
        }

        public void setTeach_name(String teach_name) {
            this.teach_name = teach_name;
        }

        public String getTest_time() {
            return test_time;
        }

        public void setTest_time(String test_time) {
            this.test_time = test_time;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        @Override
        public String toString() {
            return "ChildData{" +
                    "id=" + id +
                    ", teach_id=" + teach_id +
                    ", teach_name='" + teach_name + '\'' +
                    ", test_time=" + test_time +
                    ", member_id='" + member_id + '\'' +
                    '}';
        }
    }
}
