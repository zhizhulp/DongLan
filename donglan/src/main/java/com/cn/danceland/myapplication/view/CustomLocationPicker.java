package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.db.DBData;
import com.cn.danceland.myapplication.db.Donglan;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2018/4/23.
 */

public class CustomLocationPicker extends AlertDialog {

    LoopView lp_province,lp_city;
    AlertDialog.Builder alertdialog;
    private OnClickEnter onClickEnter;
    DBData dbData;
    List<Donglan> zoneArr, cityList;
    ArrayList<String> proList, cityList1;
    View inflate;
    private String myCity,myProvince;

    public CustomLocationPicker(@NonNull Context context,String city,String province) {
        super(context);
        dbData = new DBData();
        myCity = city;
        myProvince = province;
        inflate = LayoutInflater.from(context).inflate(R.layout.location_picker, null);
        lp_province = inflate.findViewById(R.id.lp_province);
        lp_city = inflate.findViewById(R.id.lp_city);
        initLocationData();

        alertdialog = new AlertDialog.Builder(context);
        alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickEnter.onClick();
            }
        });

    }

    public interface OnClickEnter{
        public void onClick();
    }

    public void setDialogOnClickListener(OnClickEnter onClickEnter){

        this.onClickEnter = onClickEnter;
    }

    public void initLocationData() {

        cityList = dbData.getCityList();
        LogUtil.i(cityList.size()+"");
        //省份列表
        proList = new ArrayList<String>();
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                //城市名字为key，城市代码为value
                String prokey = cityList.get(i).getProvince();
                proList.add(prokey);
                for (int m = 0; m < proList.size() - 1; m++) {
                    if (proList.get(m).equals(proList.get(m + 1))) {
                        proList.remove(m);
                        m--;
                    }
                }

            }
        }

    }

    String city,province;

    public void showLocation(){

        lp_province.setItems(proList);
        lp_province.setTextSize(16);
        lp_city.setTextSize(16);
        lp_province.setNotLoop();
        lp_city.setNotLoop();


        cityList1 = new ArrayList<>();
        if(StringUtils.isNullorEmpty(myCity) || StringUtils.isNullorEmpty(myProvince)){
            lp_province.setInitPosition(0);
            lp_city.setInitPosition(0);
            LogUtil.i(proList.toString());
            province = proList.get(0);
            setLp_city();
            city = cityList1.get(0);
        }else{
            for(int i=0;i<proList.size();i++){
                if(myProvince.equals(proList.get(i))){
                    lp_province.setInitPosition(i);
                    province = proList.get(i);
                    setLp_city();
                }
            }
            for (int j= 0 ;j<cityList1.size();j++){
                if(myCity.equals(cityList1.get(j))){
                    lp_city.setInitPosition(j);
                    city = cityList1.get(j);
                }
            }
        }



        lp_province.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                province = proList.get(index);
                setLp_city();
                city = cityList1.get(0);
            }
        });


        lp_city.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                city = cityList1.get(index);
            }
        });


        alertdialog.setTitle("请选择地区");
        alertdialog.setView(inflate);

        alertdialog.show();

    }

    private void setLp_city(){
        List<Donglan> citys = dbData.queryPro(province);
        cityList1.clear();
        for(int i = 0;i<citys.size();i++){
            cityList1.add(citys.get(i).getCity());
        }
        lp_city.setItems(cityList1);
    }

    public String getZone(){

        return province + " " +city;

    }

    public String getCity(){
        return city;
    }

    public String getProvince(){
        return province;
    }

}
