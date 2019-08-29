package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/1/13 09:43
 * Email:644563767@qq.com
 */

public class RequsetRevisiterRecordListBean {

    private boolean success;
    private String errorMsg;
    private Data data;

    @Override
    public String toString() {
        return "RequsetRevisiterRecordListBean{" +
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

            private String id;
            private String branch_id;
            private String operate_id;
            private String operate_name;
            private String member_id;
            private String member_no;
            private String member_name;
            private String maintain_time;
            private int length;
            private String type;
            private String content;
            private String result;
            private int auth;

            @Override
            public String toString() {
                return "Content{" +
                        "id='" + id + '\'' +
                        ", branch_id='" + branch_id + '\'' +
                        ", operate_id='" + operate_id + '\'' +
                        ", operate_name='" + operate_name + '\'' +
                        ", member_id='" + member_id + '\'' +
                        ", member_no='" + member_no + '\'' +
                        ", member_name='" + member_name + '\'' +
                        ", maintain_time='" + maintain_time + '\'' +
                        ", length=" + length +
                        ", type='" + type + '\'' +
                        ", content='" + content + '\'' +
                        ", result='" + result + '\'' +
                        ", auth=" + auth +
                        '}';
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
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

            public String getOperate_id() {
                return operate_id;
            }

            public void setOperate_id(String operate_id) {
                this.operate_id = operate_id;
            }

            public String getOperate_name() {
                return operate_name;
            }

            public void setOperate_name(String operate_name) {
                this.operate_name = operate_name;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getMember_no() {
                return member_no;
            }

            public void setMember_no(String member_no) {
                this.member_no = member_no;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getMaintain_time() {
                return maintain_time;
            }

            public void setMaintain_time(String maintain_time) {
                this.maintain_time = maintain_time;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getAuth() {
                return auth;
            }

            public void setAuth(int auth) {
                this.auth = auth;
            }
        }
    }
}