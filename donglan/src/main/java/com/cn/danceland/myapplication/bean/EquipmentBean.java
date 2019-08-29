package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by feng on 2018/1/3.
 */

public class EquipmentBean {
    private boolean success;
    private String errorMsg;
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
            private int branch_id;
            private String branch_name;
            private String bca_name;
            private String bca_no;
            private int hide;
            private String manufacturer;
            private String brand;
            private String model;
            private int delete_remark;
            private int status;
            public void setId(int id) {
                this.id = id;
            }
            public int getId() {
                return id;
            }

            public void setBranch_id(int branch_id) {
                this.branch_id = branch_id;
            }
            public int getBranch_id() {
                return branch_id;
            }

            public void setBranch_name(String branch_name) {
                this.branch_name = branch_name;
            }
            public String getBranch_name() {
                return branch_name;
            }

            public void setBca_name(String bca_name) {
                this.bca_name = bca_name;
            }
            public String getBca_name() {
                return bca_name;
            }

            public void setBca_no(String bca_no) {
                this.bca_no = bca_no;
            }
            public String getBca_no() {
                return bca_no;
            }

            public void setHide(int hide) {
                this.hide = hide;
            }
            public int getHide() {
                return hide;
            }

            public void setManufacturer(String manufacturer) {
                this.manufacturer = manufacturer;
            }
            public String getManufacturer() {
                return manufacturer;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }
            public String getBrand() {
                return brand;
            }

            public void setModel(String model) {
                this.model = model;
            }
            public String getModel() {
                return model;
            }

            public void setDelete_remark(int delete_remark) {
                this.delete_remark = delete_remark;
            }
            public int getDelete_remark() {
                return delete_remark;
            }

            public void setStatus(int status) {
                this.status = status;
            }
            public int getStatus() {
                return status;
            }
        }

    }



}
