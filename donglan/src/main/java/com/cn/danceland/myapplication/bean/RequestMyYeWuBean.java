package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/9/26 17:34
 * Email:644563767@qq.com
 */


public class RequestMyYeWuBean {

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
            private int member_id;
            private float money;
            private long deal_time;
            private int type;
            private int big_type;
            private String operater_name;
            private String employee_name;
            private String pay_name;
            private String code;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            @Override
            public String toString() {
                return "Content{" +
                        "id=" + id +
                        ", member_id=" + member_id +
                        ", money=" + money +
                        ", deal_time=" + deal_time +
                        ", type=" + type +
                        ", big_type=" + big_type +
                        ", operater_name='" + operater_name + '\'' +
                        ", employee_name='" + employee_name + '\'' +
                        ", pay_name='" + pay_name + '\'' +
                        ", code='" + code + '\'' +
                        '}';
            }

            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }
            public int getMember_id() {
                return member_id;
            }

            public void setMoney(Float money) {
                this.money = money;
            }
            public Float getMoney() {
                return money;
            }

            public void setDeal_time(long deal_time) {
                this.deal_time = deal_time;
            }
            public long getDeal_time() {
                return deal_time;
            }

            public void setType(int type) {
                this.type = type;
            }
            public int getType() {
                return type;
            }

            public void setBig_type(int big_type) {
                this.big_type = big_type;
            }
            public int getBig_type() {
                return big_type;
            }

            public void setOperater_name(String operater_name) {
                this.operater_name = operater_name;
            }
            public String getOperater_name() {
                return operater_name;
            }

            public void setEmployee_name(String employee_name) {
                this.employee_name = employee_name;
            }
            public String getEmployee_name() {
                return employee_name;
            }

            public void setPay_name(String pay_name) {
                this.pay_name = pay_name;
            }
            public String getPay_name() {
                return pay_name;
            }

        }
    }
}
