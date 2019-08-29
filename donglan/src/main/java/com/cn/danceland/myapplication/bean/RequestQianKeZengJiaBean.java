package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/9/26 17:34
 * Email:644563767@qq.com
 */


public class RequestQianKeZengJiaBean {

    private boolean success;
    private String errorMsg;
    private int code;
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

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return "RequestMyYeWuBean{" +
                "success=" + success +
                ", errorMsg='" + errorMsg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }

    public class Data {

        private List<Content> content;
        private int number;
        private int size;
        private int totalElements;
        private boolean last;
        private int numberOfElements;
        private int totalPages;

        @Override
        public String toString() {
            return "Data{" +
                    "content=" + content +
                    ", number=" + number +
                    ", size=" + size +
                    ", totalElements=" + totalElements +
                    ", last=" + last +
                    ", numberOfElements=" + numberOfElements +
                    ", totalPages=" + totalPages +
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

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }
        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
        public int getTotalPages() {
            return totalPages;
        }


        public class Content {

            private int id;

            private String admin_mark;
            private String avatar_path;
            private String avatar_url;
            private String count_date;
            private String member_name;
            private String test_time;

            @Override
            public String toString() {
                return "Content{" +
                        "id=" + id +
                        ", admin_mark='" + admin_mark + '\'' +
                        ", avatar_path='" + avatar_path + '\'' +
                        ", avatar_url='" + avatar_url + '\'' +
                        ", count_date='" + count_date + '\'' +
                        ", member_name='" + member_name + '\'' +
                        ", test_time='" + test_time + '\'' +
                        '}';
            }

            public String getTest_time() {
                return test_time;
            }

            public void setTest_time(String test_time) {
                this.test_time = test_time;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAdmin_mark() {
                return admin_mark;
            }

            public void setAdmin_mark(String admin_mark) {
                this.admin_mark = admin_mark;
            }

            public String getAvatar_path() {
                return avatar_path;
            }

            public void setAvatar_path(String avatar_path) {
                this.avatar_path = avatar_path;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public String getCount_date() {
                return count_date;
            }

            public void setCount_date(String count_date) {
                this.count_date = count_date;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }


            //            admin_mark (string, optional): 标签 ,
//            avatar_path (string, optional): 头像路径 ,
//            avatar_url (string, optional): 头像地址 ,
//            count_date (string, optional): 时间 ,
//            member_name (string, optional): 会员名
        }
    }
}
