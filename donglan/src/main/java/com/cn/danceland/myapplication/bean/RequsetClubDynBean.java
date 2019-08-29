package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/3/21 11:59
 * Email:644563767@qq.com
 */


public class RequsetClubDynBean {

    private boolean success;
    private String errorMsg;
    private String code;
    private Data data;

    @Override
    public String toString() {
        return "RequsetClubDynBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code='" + code + '\'' +
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
        private boolean last;
        private int totalPages;
        private int numberOfElements;

        @Override
        public String toString() {
            return "Data{" +
                    "content=" + content +
                    ", number=" + number +
                    ", size=" + size +
                    ", totalElements=" + totalElements +
                    ", last=" + last +
                    ", totalPages=" + totalPages +
                    ", numberOfElements=" + numberOfElements +
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

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public class Content {

            private int id;
            private int delete_mark;
            private String end_time;
            private String img_url;
            private String news_content;
            private int top;
            private String publish_time;
            private String start_time;
            private String title;
            private String url;
            private String news_txt;
            private int branch_id;
            private int type;
            private String read_number;//阅读数

            @Override
            public String toString() {
                return "Content{" +
                        "id=" + id +
                        ", delete_mark=" + delete_mark +
                        ", end_time='" + end_time + '\'' +
                        ", img_url='" + img_url + '\'' +
                        ", news_content='" + news_content + '\'' +
                        ", top=" + top +
                        ", publish_time='" + publish_time + '\'' +
                        ", start_time='" + start_time + '\'' +
                        ", title='" + title + '\'' +
                        ", url='" + url + '\'' +
                        ", news_txt='" + news_txt + '\'' +
                        ", branch_id=" + branch_id +
                        ", type=" + type +
                        ", read_number='" + read_number + '\'' +
                        '}';
            }

            public String getRead_number() {
                return read_number;
            }

            public void setRead_number(String read_number) {
                this.read_number = read_number;
            }

            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setDelete_mark(int delete_mark) {
                this.delete_mark = delete_mark;
            }
            public int getDelete_mark() {
                return delete_mark;
            }



            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
            public String getImg_url() {
                return img_url;
            }

            public void setNews_content(String news_content) {
                this.news_content = news_content;
            }
            public String getNews_content() {
                return news_content;
            }

            public void setTop(int top) {
                this.top = top;
            }
            public int getTop() {
                return top;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getPublish_time() {
                return publish_time;
            }

            public void setPublish_time(String publish_time) {
                this.publish_time = publish_time;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public void setTitle(String title) {
                this.title = title;
            }
            public String getTitle() {
                return title;
            }

            public void setUrl(String url) {
                this.url = url;
            }
            public String getUrl() {
                return url;
            }

            public void setNews_txt(String news_txt) {
                this.news_txt = news_txt;
            }
            public String getNews_txt() {
                return news_txt;
            }

            public void setBranch_id(int branch_id) {
                this.branch_id = branch_id;
            }
            public int getBranch_id() {
                return branch_id;
            }

            public void setType(int type) {
                this.type = type;
            }
            public int getType() {
                return type;
            }

        }

    }
}

