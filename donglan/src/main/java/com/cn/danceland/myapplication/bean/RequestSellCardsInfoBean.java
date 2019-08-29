package com.cn.danceland.myapplication.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shy on 2017/11/16 16:13
 * Email:644563767@qq.com
 */

public class RequestSellCardsInfoBean implements Serializable {

    private boolean success;
    private String errorMsg;
    private List<Data> data;

    @Override
    public String toString() {
        return "RequestSellCardsInfoBean{" +
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

    public void setData(List<Data> data) {
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data implements Serializable{


//
//        private Long id;// 主键
//        private Long branch_id;// 门店ID
//        private String name;// 名称
//        private Short charge_mode;// 计费方式
//        private Integer total_count;// 总次数
//        private Float price;// 价钱
//        private Integer time_unit;// 单位按年按月
//        private Integer time_value;// 时间长度
//        private Short on_sale;// 是否在售
//        private Long category_id;// 卡分类ID
//        private String category_name;// 卡分类名称
//        private Integer month_count;// 记时卡总月数
//        private Short delete_remark;// 删除标记
//        private String branch_name;// 名称
//


        private String id;
        private String branch_id;
        private String name;
        private int charge_mode;//1计时收费2计次收费3充值
        private String total_count;
        private float price;
        private int time_unit;
        private int time_value;
        private int on_sale;
        private int category_id;
        private String category_name;
        private int month_count;
        private int delete_remark;
        private String branch_name;
        private String  img_url;
        private String     remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id='" + id + '\'' +
                    ", branch_id='" + branch_id + '\'' +
                    ", name='" + name + '\'' +
                    ", charge_mode=" + charge_mode +
                    ", total_count='" + total_count + '\'' +
                    ", price=" + price +
                    ", time_unit=" + time_unit +
                    ", time_value=" + time_value +
                    ", on_sale=" + on_sale +
                    ", category_id=" + category_id +
                    ", category_name='" + category_name + '\'' +
                    ", month_count=" + month_count +
                    ", delete_remark=" + delete_remark +
                    ", branch_name='" + branch_name + '\'' +
                    ", img_url='" + img_url + '\'' +
                    '}';
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCharge_mode() {
            return charge_mode;
        }

        public void setCharge_mode(int charge_mode) {
            this.charge_mode = charge_mode;
        }

        public String getTotal_count() {
            return total_count;
        }

        public void setTotal_count(String total_count) {
            this.total_count = total_count;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getTime_unit() {
            return time_unit;
        }

        public void setTime_unit(int time_unit) {
            this.time_unit = time_unit;
        }

        public int getTime_value() {
            return time_value;
        }

        public void setTime_value(int time_value) {
            this.time_value = time_value;
        }

        public int getOn_sale() {
            return on_sale;
        }

        public void setOn_sale(int on_sale) {
            this.on_sale = on_sale;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public int getMonth_count() {
            return month_count;
        }

        public void setMonth_count(int month_count) {
            this.month_count = month_count;
        }

        public int getDelete_remark() {
            return delete_remark;
        }

        public void setDelete_remark(int delete_remark) {
            this.delete_remark = delete_remark;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }
    }

}