package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by yxx on 2018-09-25.
 */

public class RequestScanerCodeHandsBean {
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
        return "RequestScanerCodeBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public class Data {
        private List<ChildData> content;
        private int number;
        private int size;
        private int totalElements;
        private int numberOfElements;
        private int totalPages;
        private boolean last;

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

        public List<ChildData> getContent() {
            return content;
        }

        public void setContent(List<ChildData> content) {
            this.content = content;
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
    public static class ChildData {
        private Long id;
        private String  code;
        private String  area;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        @Override
        public String toString() {
            return "ChildData{" +
                    "id=" + id +
                    ", code='" + code + '\'' +
                    ", area='" + area + '\'' +
                    '}';
        }
    }
}
