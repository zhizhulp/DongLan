package com.cn.danceland.myapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestNewBindBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MD5Utils;
import com.cn.danceland.myapplication.utils.MyStringNoTokenRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.AlertDialogCustom;
import com.cn.danceland.myapplication.view.CommitButton;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shy on 2018/12/21 18:21
 * Email:644563767@qq.com
 * 验证密码页面
 */


public class VerifyPSWDActivty extends BaseActivity {

    private EditText et_sfz;
    String phone;
    private Dialog dialog;
    private RequestNewBindBean newBindBean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_bind_pswd);
        initview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {
            case 1015:
                finish();
                break;

            default:
                break;
        }
    }
    private void initview() {
        phone = getIntent().getStringExtra("phone");
        et_sfz = findViewById(R.id.et_password);
        CommitButton commitButton = findViewById(R.id.dlbtn_commit);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_sfz.getText().toString().trim())) {
                    ToastUtils.showToastShort("请输入您的密码");
                    return;
                }
                yanzhengshenfen();
            }
        });
    }

    /**
     * 验证身份
     */
    private void yanzhengshenfen() {
        MyStringNoTokenRequest myStringNoTokenRequest = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.VERIFY_PHONEPASSWORD), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                newBindBean = new Gson().fromJson(s, RequestNewBindBean.class);
                if (newBindBean.getCode() == 1) {//密码验证不通过,身份证为空
                    showClearDialog("密码匹配不一致", 1);
                }
                if (newBindBean.getCode() == 2) {//密码验证不通过,身份证不为空
                    showClearDialog("密码匹配不一致", 2);
                }
                if (newBindBean.getCode() == 0) {//通过
                    List<String> strings = new ArrayList<>();
                    strings.add(phone);
                    strings.add(et_sfz.getText().toString());
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
                Map<String, String> map = new HashMap<>();
                map.put("phone_no", phone);
                map.put("password", MD5Utils.encode(et_sfz.getText().toString()));
                map.put("deviceNo", AppUtils.getDeviceId(VerifyPSWDActivty.this));
                return map;
            }
        };
        MyApplication.getHttpQueues().add(myStringNoTokenRequest);

    }

    private void showClearDialog(String msg, final int code) {
        dialog = new AlertDialogCustom("重新验证", "找回密码").CreateDialog(this, "提示", msg, new AlertDialogCustom.Click() {
            @Override
            public void ok_bt(int dialogOKB) {
                dialog.dismiss();

            }

            @Override
            public void cancle_bt(int btn_cancel) {
                dialog.dismiss();
                if (code == 1) {
                    startActivity(new Intent(VerifyPSWDActivty.this, BindConfirmInfoActivity.class)//驗證門店信息
                            .putExtra("phone", phone)
                            .putExtra("info", newBindBean));
                }
                if (code == 2) {
                    //  原证件号码不为空,请输入证件号码完成验证
                    startActivity(new Intent(VerifyPSWDActivty.this, BindSFZActivty.class).putExtra("phone", phone));


                }
            }
        });
    }


}
