package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * 收藏 新闻
 * Created by yxx on 2018/10/23.
 */

public class RequestCollectDataBean {

    private boolean success;
    private String errorMsg;
    private Data data;

    @Override
    public String toString() {
        return "RequestNewsDataBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
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

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }


    public class Data {

        private int total;
        private List<Content> content;

        @Override
        public String toString() {
            return "Data{" +
                    "total=" + total +
                    ", content=" + content +
                    '}';
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }

        public class Content {
            private String id;// 主键，自增
            private String img_url;// 缩略图
            private String title;// 新闻的title
            private String news_content;// 新闻内容
            private String url;// 浏览地址
            private String top;// 是否置顶
            private String delete_mark;// 删除标志
            private String end_time;// 显示结束时间
            private String start_time;// 显示开始时间
            private String publish_time;// 发布时间
            private String news_txt;
            private String read_number;
            private boolean is_collect;

            @Override
            public String toString() {
                return "Items{" +
                        "id='" + id + '\'' +
                        ", img_url='" + img_url + '\'' +
                        ", title='" + title + '\'' +
                        ", news_content='" + news_content + '\'' +
                        ", url='" + url + '\'' +
                        ", top='" + top + '\'' +
                        ", delete_mark='" + delete_mark + '\'' +
                        ", end_time='" + end_time + '\'' +
                        ", start_time='" + start_time + '\'' +
                        ", publish_time='" + publish_time + '\'' +
                        ", news_txt='" + news_txt + '\'' +
                        ", read_number='" + read_number + '\'' +
                        ", is_collect=" + is_collect +
                        '}';
            }

            public String getRead_number() {
                return read_number;
            }

            public void setRead_number(String read_number) {
                this.read_number = read_number;
            }

            public boolean is_collect() {
                return is_collect;
            }

            public void setIs_collect(boolean is_collect) {
                this.is_collect = is_collect;
            }

            public String getNews_txt() {
                return news_txt;
            }

            public void setNews_txt(String news_txt) {
                this.news_txt = news_txt;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle() {
                return title;
            }

            public void setNews_content(String news_content) {
                this.news_content = news_content;
            }

            public String getNews_content() {
                return news_content;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUrl() {
                return url;
            }

            public void setTop(String top) {
                this.top = top;
            }

            public String getTop() {
                return top;
            }

            public void setDelete_mark(String delete_mark) {
                this.delete_mark = delete_mark;
            }

            public String getDelete_mark() {
                return delete_mark;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setPublish_time(String publish_time) {
                this.publish_time = publish_time;
            }

            public String getPublish_time() {
                return publish_time;
            }
        }
    }
}