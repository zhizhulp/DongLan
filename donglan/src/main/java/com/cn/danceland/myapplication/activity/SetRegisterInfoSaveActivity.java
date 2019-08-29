package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RootBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.RulerView;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2018/10/18 10:13
 * Email:644563767@qq.com
 */


public class SetRegisterInfoSaveActivity extends BaseActivity {
   String gender,name,birthday,height,weight,id;
    private TextView tv_height,tv_weight;
    private Gson gson=new Gson();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                LogUtil.i(name);
                FriendshipManagerPresenter.setMyNick(name, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        LogUtil.i("昵称修改失败"+i);
                    }

                    @Override
                    public void onSuccess() {

                        LogUtil.i("昵称修改成功");
                    }
                });
            }
        }
    };
    private Data mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_register_info_save);
        initdata();
        initView();
     
    }

    private void initView() {

        RulerView    ruler_height=(RulerView)findViewById(R.id.ruler_height);
        tv_height = findViewById(R.id.tv_height);
        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_height.setText(value+"cm");
                height=value+"";
            }
        });
        RulerView    ruler_weight=(RulerView)findViewById(R.id.ruler_weight);
        tv_weight = findViewById(R.id.tv_weight);
        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_weight.setText(value+"kg");
                weight=value+"";
            }
        });

/**
 *
 * @param selectorValue 未选择时 默认的值 滑动后表示当前中间指针正在指着的值
 * @param minValue   最大数值
 * @param maxValue   最小的数值
 * @param per   最小单位  如 1:表示 每2条刻度差为1.   0.1:表示 每2条刻度差为0.1 在demo中 身高mPerValue为1  体重mPerValue 为0.1
 */
        ruler_height.setValue(165, 50, 300, 1);//身高
        ruler_weight.setValue(50, 30, 200, 0.1f);//体重


        findViewById(R.id.dlbtn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(height)){
                    ToastUtils.showToastShort("请选择您的身高");
                    return;
                }

                if (TextUtils.isEmpty(weight)){
                    ToastUtils.showToastShort("请选择您的体重");
                    return;
                }

                commit();
            }
        });

    }

    private void initdata() {
        gender=getIntent().getStringExtra("gender");
        name=getIntent().getStringExtra("name");
        birthday=getIntent().getStringExtra("birthday");
        mData = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
      id = SPUtils.getString(Constants.MY_USERID, null);
        LogUtil.i(gender+name+birthday);
    }

    public void commit() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.SET_BASE_USERINFO_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RootBean rootBean = gson.fromJson(s, RootBean.class);
                if ("true".equals(rootBean.success)) {
                    ToastUtils.showToastShort("保存成功");
                    mData.getPerson().setBirthday(birthday);
                    mData.getPerson().setNick_name(name);
                    mData.getPerson().setHeight(height);
                    mData.getPerson().setWeight(weight);
                    mData.getPerson().setGender(gender);
                    DataInfoCache.saveOneCache(mData, Constants.MY_INFO);
                    EventBus.getDefault().post(new StringEvent("", 1010));
                    handler.sendEmptyMessage(1);
                    setResult(202);//关闭上个页面
                    Intent intent = new Intent(SetRegisterInfoSaveActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    ToastUtils.showToastShort("提交失败！");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("提交失败！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("id", id);
                hashMap.put("nickName", name);
                hashMap.put("gender", gender);
                hashMap.put("height", height);
                hashMap.put("weight", weight);
                hashMap.put("birthday", birthday);
                //LogUtil.e("zzf",hashMap.toString());
                return hashMap;
            }

        };
        MyApplication.getHttpQueues().add(stringRequest);

    }
}
