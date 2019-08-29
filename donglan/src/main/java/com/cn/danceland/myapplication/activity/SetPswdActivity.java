package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.bean.RequestSimpleBean1;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MD5Utils;
import com.cn.danceland.myapplication.utils.MyStringNoTokenRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shy on 2018/7/26 17:36
 * Email:644563767@qq.com
 */


public class SetPswdActivity extends BaseActivity implements View.OnClickListener {
    private String id;
    private String phone;
    private EditText mEtPsw;
    private EditText mEtConfirmPsd;
    private Data data;
    private int login_type = 0; //1登陆，2创建人然后登陆

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpswd);
        initView();
        initData();

    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        login_type = getIntent().getIntExtra("login_type", 0);
        phone = getIntent().getStringExtra("phone");
        LogUtil.i(id);
        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.dlbtn_commit).setOnClickListener(this);
        mEtPsw = findViewById(R.id.et_password);
        mEtConfirmPsd = findViewById(R.id.et_confirm_password);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dlbtn_commit:

                //判断密码是否为空
                if (TextUtils.isEmpty(mEtPsw.getText().toString())) {
                    Toast.makeText(SetPswdActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断密码是否包含空格
                if (mEtPsw.getText().toString().contains(" ")) {
                    Toast.makeText(SetPswdActivity.this, "密码不能包含空格，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断密码位数是否符合6-20
                if (mEtPsw.getText().toString().length() < 6) {
                    Toast.makeText(SetPswdActivity.this, "设置密码要大于等于6位，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtPsw.getText().toString().length() > 20) {
                    Toast.makeText(SetPswdActivity.this, "设置密码要小于等于20位，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断密码输入是否一致
                if (!TextUtils.equals(mEtConfirmPsd.getText().toString(), mEtPsw.getText().toString())) {
                    Toast.makeText(SetPswdActivity.this, "密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (login_type != 2) {
                    resetPwd(id, mEtPsw.getText().toString());
                } else {
                    unbind();//重新绑定账号
                }

                break;
            default:
                break;
        }
    }

    private void unbind() {
        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.UNBIND_ANDREGISTER), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestSimpleBean1 simpleBean = new Gson().fromJson(s, RequestSimpleBean1.class);
                if (simpleBean.getSuccess()) {
                    ToastUtils.showToastShort("绑定成功");
                    List<String> strings = new ArrayList<>();
                    strings.add(phone);
                    strings.add(mEtPsw.getText().toString());
                    EventBus.getDefault().post(new StringEvent(strings, 1015));
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("phone", phone);
                map.put("platform", "1");
                map.put("password", MD5Utils.encode(mEtPsw.getText().toString()));
                map.put("deviceNo", AppUtils.getDeviceId(SetPswdActivity.this));

                LogUtil.i(map.toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    private void resetPwd(final String id, final String pswd) {
        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.RESET_PASSWORD_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestSimpleBean simpleBean = new Gson().fromJson(s, RequestSimpleBean.class);
                if (simpleBean.getSuccess()) {
                    ToastUtils.showToastShort("密码设置成功");
                    if (data != null) {
                        data.setHasPwd(true);
                        DataInfoCache.saveOneCache(data, Constants.MY_INFO);
                    }
                    if (login_type == 1) {
                        List<String> strings = new ArrayList<>();
                        strings.add(phone);
                        strings.add(pswd);
                        EventBus.getDefault().post(new StringEvent(strings, 1015));
                    }

                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("person_id", id);
                map.put("pwd", MD5Utils.encode(pswd));
                map.put("deviceNo", AppUtils.getDeviceId(SetPswdActivity.this));

                LogUtil.i(map.toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }


}
