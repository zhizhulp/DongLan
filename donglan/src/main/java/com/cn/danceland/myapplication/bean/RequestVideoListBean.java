package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2019/1/9 17:43
 * Email:644563767@qq.com
 */

public class RequestVideoListBean {

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
    public class Data {

        private List<Content> content;
        private int number;
        private int size;
        private int totalElements;
        private int numberOfElements;
        private int totalPages;
        private boolean last;
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

        public void setLast(boolean last) {
            this.last = last;
        }
        public boolean getLast() {
            return last;
        }
        public class Content {

            private int id;
            private String name;
            private String remark;
            private String url;
            private String img_path;
            private int type;
            private int type2;
            private int type3;
            private String type4;
            private String type_name;
            private String type2_name;
            private String type3_name;
            private String img_url;
            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setName(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
            public String getRemark() {
                return remark;
            }

            public void setUrl(String url) {
                this.url = url;
            }
            public String getUrl() {
                return url;
            }

            public void setImg_path(String img_path) {
                this.img_path = img_path;
            }
            public String getImg_path() {
                return img_path;
            }

            public void setType(int type) {
                this.type = type;
            }
            public int getType() {
                return type;
            }

            public void setType2(int type2) {
                this.type2 = type2;
            }
            public int getType2() {
                return type2;
            }

            public void setType3(int type3) {
                this.type3 = type3;
            }
            public int getType3() {
                return type3;
            }

            public void setType4(String type4) {
                this.type4 = type4;
            }
            public String getType4() {
                return type4;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }
            public String getType_name() {
                return type_name;
            }

            public void setType2_name(String type2_name) {
                this.type2_name = type2_name;
            }
            public String getType2_name() {
                return type2_name;
            }

            public void setType3_name(String type3_name) {
                this.type3_name = type3_name;
            }
            public String getType3_name() {
                return type3_name;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
            public String getImg_url() {
                return img_url;
            }

        }
    }
}