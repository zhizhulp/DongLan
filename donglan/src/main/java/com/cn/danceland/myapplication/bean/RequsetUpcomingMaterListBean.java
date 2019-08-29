package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/1/13 17:01
 * Email:644563767@qq.com
 */

public class RequsetUpcomingMaterListBean {

    private boolean success;
    private String errorMsg;
    private Data data;

    @Override
    public String toString() {
        return "RequsetUpcomingMaterListBean{" +
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
            private String role_type;
            private String person_id;
            private String operate_id;
            private String employee_name;
            private String member_id;
            private String member_no;
            private String member_name;
            private String title;
            private String content;
            private String record_time;
            private String result_type;
            private String pushed;
            private String warn_time;
            private String status;
            private String work_type_name;
            private String result;

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public String getWork_type_name() {
                return work_type_name;
            }

            public void setWork_type_name(String work_type_name) {
                this.work_type_name = work_type_name;
            }

            @Override
            public String toString() {
                return "Content{" +
                        "id='" + id + '\'' +
                        ", role_type='" + role_type + '\'' +
                        ", person_id='" + person_id + '\'' +
                        ", operate_id='" + operate_id + '\'' +
                        ", employee_name='" + employee_name + '\'' +
                        ", member_id='" + member_id + '\'' +
                        ", member_no='" + member_no + '\'' +
                        ", member_name='" + member_name + '\'' +
                        ", title='" + title + '\'' +
                        ", content='" + content + '\'' +
                        ", record_time='" + record_time + '\'' +
                        ", result_type='" + result_type + '\'' +
                        ", pushed='" + pushed + '\'' +
                        ", warn_time='" + warn_time + '\'' +
                        ", status='" + status + '\'' +
                        ", work_type_name='" + work_type_name + '\'' +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRole_type() {
                return role_type;
            }

            public void setRole_type(String role_type) {
                this.role_type = role_type;
            }

            public String getPerson_id() {
                return person_id;
            }

            public void setPerson_id(String person_id) {
                this.person_id = person_id;
            }

            public String getOperate_id() {
                return operate_id;
            }

            public void setOperate_id(String operate_id) {
                this.operate_id = operate_id;
            }

            public String getEmployee_name() {
                return employee_name;
            }

            public void setEmployee_name(String employee_name) {
                this.employee_name = employee_name;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getRecord_time() {
                return record_time;
            }

            public void setRecord_time(String record_time) {
                this.record_time = record_time;
            }

            public String getResult_type() {
                return result_type;
            }

            public void setResult_type(String result_type) {
                this.result_type = result_type;
            }

            public String getPushed() {
                return pushed;
            }

            public void setPushed(String pushed) {
                this.pushed = pushed;
            }

            public String getWarn_time() {
                return warn_time;
            }

            public void setWarn_time(String warn_time) {
                this.warn_time = warn_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

        }
    }
}