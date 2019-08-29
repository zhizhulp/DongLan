package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 我的 运动数据
 * Created by yxx on 2018-12-04.
 */

public class TextPushListBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private String code;
    private Data data;

    @Override
    public String toString() {
        return "MotionBean{" +
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {

        private List<Content> content;
        private int number;
        private int size;
        private int totalElements;
        private int numberOfElements;
        private int totalPages;
        private boolean last;

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

        public class Content implements Serializable {
            private String  branch_id;//门店id ,
            private String  content ;//推送内容 ,
            private String  id ;//主键 ,
            private String  origintor_id ;// 发起人 ,
            private String push_range ;//推送范围 ,
            private String  push_time;// 推送时间 ,
            private String title ;//标题

            @Override
            public String toString() {
                return "Content{" +
                        "branch_id='" + branch_id + '\'' +
                        ", content='" + content + '\'' +
                        ", id='" + id + '\'' +
                        ", origintor_id='" + origintor_id + '\'' +
                        ", push_range='" + push_range + '\'' +
                        ", push_time='" + push_time + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }

            public String getBranch_id() {
                return branch_id;
            }

            public void setBranch_id(String branch_id) {
                this.branch_id = branch_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrigintor_id() {
                return origintor_id;
            }

            public void setOrigintor_id(String origintor_id) {
                this.origintor_id = origintor_id;
            }

            public String getPush_range() {
                return push_range;
            }

            public void setPush_range(String push_range) {
                this.push_range = push_range;
            }

            public String getPush_time() {
                return push_time;
            }

            public void setPush_time(String push_time) {
                this.push_time = push_time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}

