package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.app.AppManager;
import com.cn.danceland.myapplication.bean.SettingMessageBean;
import com.cn.danceland.myapplication.bean.SettingMessageSaveBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设置  我的消息
 * <p>
 * Created by yxx on 2018-11-21.
 */

public class SettingMessageActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    private ImageView found_switch_iv;
    private ImageView gym_card_switch_iv;
    private ImageView rent_ark_switch_iv;
    private ImageView one_to_one_switch_iv;
    private ImageView group_switch_iv;
    private ImageView free_group_switch_iv;
    private ImageView deposit_switch_iv;
    private ImageView stored_value_switch_iv;
    private ImageView daily_switch_iv;
    private ImageView system_switch_iv;

    private List<SettingMessageBean.Data> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_message);
        AppManager.getAppManager().addActivity(this);
        context = this;
        initView();
        getData();
    }

    private void initView() {
        found_switch_iv = findViewById(R.id.found_switch_iv);
        gym_card_switch_iv = findViewById(R.id.gym_card_switch_iv);
        rent_ark_switch_iv = findViewById(R.id.rent_ark_switch_iv);
        one_to_one_switch_iv = findViewById(R.id.one_to_one_switch_iv);
        group_switch_iv = findViewById(R.id.group_switch_iv);
        free_group_switch_iv = findViewById(R.id.free_group_switch_iv);
        deposit_switch_iv = findViewById(R.id.deposit_switch_iv);
        stored_value_switch_iv = findViewById(R.id.stored_value_switch_iv);
        daily_switch_iv = findViewById(R.id.daily_switch_iv);
        system_switch_iv = findViewById(R.id.system_switch_iv);

        found_switch_iv.setOnClickListener(this);
        gym_card_switch_iv.setOnClickListener(this);
        rent_ark_switch_iv.setOnClickListener(this);
        one_to_one_switch_iv.setOnClickListener(this);
        group_switch_iv.setOnClickListener(this);
        free_group_switch_iv.setOnClickListener(this);
        deposit_switch_iv.setOnClickListener(this);
        stored_value_switch_iv.setOnClickListener(this);
        daily_switch_iv.setOnClickListener(this);
        system_switch_iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SaveBean savebean = new SaveBean();
        switch (v.getId()) {
            case R.id.found_switch_iv:
                savebean.category = "1";
                break;
            case R.id.gym_card_switch_iv:
                savebean.category = "2";
                break;
            case R.id.rent_ark_switch_iv:
                savebean.category = "3";
                break;
            case R.id.one_to_one_switch_iv:
                savebean.category = "4";
                break;
            case R.id.group_switch_iv:
                savebean.category = "5";
                break;
            case R.id.free_group_switch_iv:
                savebean.category = "6";
                break;
            case R.id.deposit_switch_iv:
                savebean.category = "7";
                break;
            case R.id.stored_value_switch_iv:
                savebean.category = "8";
                break;
            case R.id.daily_switch_iv:
                savebean.category = "9";
                break;
            case R.id.system_switch_iv:
                savebean.category = "10";
                break;
        }
        for (SettingMessageBean.Data bean : data) {
            if (bean.getCategory().equals(savebean.category)) {
                savebean.checked = !bean.getChecked();
            }
        }
        setViewState(savebean.checked, (ImageView) v);
        saveData(savebean, (ImageView) v);
    }

    private void refreshView() {
        if (data != null && data.size() > 0) {
            for (SettingMessageBean.Data bean : data) {
                switch (bean.getCategory()) {
                    case "1":
                        setViewState(bean.getChecked(), found_switch_iv);
                        break;
                    case "2":
                        setViewState(bean.getChecked(), gym_card_switch_iv);
                        break;
                    case "3":
                        setViewState(bean.getChecked(), rent_ark_switch_iv);
                        break;
                    case "4":
                        setViewState(bean.getChecked(), one_to_one_switch_iv);
                        break;
                    case "5":
                        setViewState(bean.getChecked(), group_switch_iv);
                        break;
                    case "6":
                        setViewState(bean.getChecked(), free_group_switch_iv);
                        break;
                    case "7":
                        setViewState(bean.getChecked(), deposit_switch_iv);
                        break;
                    case "8":
                        setViewState(bean.getChecked(), stored_value_switch_iv);
                        break;
                    case "9":
                        setViewState(bean.getChecked(), daily_switch_iv);
                        break;
                    case "10":
                        setViewState(bean.getChecked(), system_switch_iv);
                        break;
                }
            }
        }
    }

    public void getData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.PUSH_RECEIVE_LIST), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("Response--" + s);
                SettingMessageBean bean = new Gson().fromJson(s, SettingMessageBean.class);
                if (bean.getSuccess() && bean.getCode() == 0) {
                    data = bean.getData();
                    refreshView();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }

    public void saveData(final SaveBean paramsB, ImageView imageview) {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_RECEIVE_SAVE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("Response--" + s);
                SettingMessageSaveBean responsebean = new Gson().fromJson(s, SettingMessageSaveBean.class);
                if (responsebean.getSuccess() && responsebean.getCode() == 0) {
                    getData();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("category", paramsB.category);
                map.put("checked", paramsB.checked + "");
                LogUtil.i("Params--" + map.toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void setViewState(boolean checked, ImageView imageview) {
        if (checked) {
            imageview.setImageDrawable(getResources().getDrawable(R.drawable.img_isdone_up));
        } else {
            imageview.setImageDrawable(getResources().getDrawable(R.drawable.img_isdone_off));
        }
    }

    private class SaveBean {
        String category;
        boolean checked;
    }
}
