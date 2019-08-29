package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/9/26 17:34
 * Email:644563767@qq.com
 */


public class RequestHuiJiYeWuBean {

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
            private float price;
            private long order_time;
            private int bus_type;
            private int big_type;
            private int course_type;
            private String member_name;
            private String employee_name;
            private String emp_name;

            private String pay_name;
            private String code;

            public String getEmp_name() {
                return emp_name;
            }

            public void setEmp_name(String emp_name) {
                this.emp_name = emp_name;
            }

            public float getPrice() {
                return price;
            }

            public void setPrice(float price) {
                this.price = price;
            }

            public int getCourse_type() {
                return course_type;
            }

            public void setCourse_type(int course_type) {
                this.course_type = course_type;
            }
            //
//            big_type (string, optional): 业务大类 ,
//            bus_type (string, optional): 业务小类 ,
//            code (string, optional): big_type=3时为卡号,big_type=4时为柜号 ,
//            employee_name (string, optional): 员工名 ,
//            member_name (string, optional): 会员名 ,
//            order_time (string, optional): 下单时间 ,
//            price (number, optional): 定单金额


            @Override
            public String toString() {
                return "Content{" +
                        "id=" + id +
                        ", money=" + price +
                        ", order_time=" + order_time +
                        ", bus_type=" + bus_type +
                        ", big_type=" + big_type +
                        ", member_name='" + member_name + '\'' +
                        ", employee_name='" + employee_name + '\'' +
                        ", pay_name='" + pay_name + '\'' +
                        ", code='" + code + '\'' +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public float getMoney() {
                return price;
            }

            public void setMoney(float price) {
                this.price = price;
            }

            public long getOrder_time() {
                return order_time;
            }

            public void setOrder_time(long order_time) {
                this.order_time = order_time;
            }

            public int getBus_type() {
                return bus_type;
            }

            public void setBus_type(int bus_type) {
                this.bus_type = bus_type;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getEmployee_name() {
                return employee_name;
            }

            public void setEmployee_name(String employee_name) {
                this.employee_name = employee_name;
            }

            public String getPay_name() {
                return pay_name;
            }

            public void setPay_name(String pay_name) {
                this.pay_name = pay_name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getBig_type() {
                return big_type;
            }

            public void setBig_type(int big_type) {
                this.big_type = big_type;
            }
        }
    }
}
