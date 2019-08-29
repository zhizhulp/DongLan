package com.cn.danceland.myapplication.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by feng on 2017/11/7.
 */
@Entity
public class Donglan {

    private int id;
    private String provinceValue;
    private String province;
    private String cityValue;
    private String city;
    @Generated(hash = 1285588360)
    public Donglan(int id, String provinceValue, String province, String cityValue,
            String city) {
        this.id = id;
        this.provinceValue = provinceValue;
        this.province = province;
        this.cityValue = cityValue;
        this.city = city;
    }
    @Generated(hash = 843809695)
    public Donglan() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProvince() {
        return this.province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void clear(){
        province = "";
        city = "";
    }
    public String getProvinceValue() {
        return this.provinceValue;
    }
    public void setProvinceValue(String provinceValue) {
        this.provinceValue = provinceValue;
    }
    public String getCityValue() {
        return this.cityValue;
    }
    public void setCityValue(String cityValue) {
        this.cityValue = cityValue;
    }

}
