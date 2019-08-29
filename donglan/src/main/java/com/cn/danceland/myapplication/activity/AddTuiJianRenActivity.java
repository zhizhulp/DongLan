package com.cn.danceland.myapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.PhoneFormatCheckUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.ContainsEmojiEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by shy on 2018/11/9 09:25
 * Email:644563767@qq.com
 */


public class AddTuiJianRenActivity extends BaseActivity {

    private ContainsEmojiEditText et_name;
    private EditText et_phone;
    private TextView tv_sex;
    private StrBean strBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tuijianren);
        initView();
    }

    private void initView() {
        strBean = new StrBean();
        strBean.setGender(0);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        tv_sex = findViewById(R.id.tv_sex);
        tv_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListDialog();
            }
        });
        findViewById(R.id.dlbtn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(et_name.getText().toString())){
                    ToastUtils.showToastShort("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(et_phone.getText().toString())){
                    ToastUtils.showToastShort("请输入电话");
                    return;
                }
                if (strBean.getGender()==0){
                    ToastUtils.showToastShort("请输选择姓别");
                    return;
                }
                if (!PhoneFormatCheckUtils.isPhoneLegal(et_phone.getText().toString())) {

                    ToastUtils.showToastShort("输入电话不合法请重新输入");
                    return;
                }
                strBean.setName(et_name.getText().toString());
                strBean.setPhone_no(et_phone.getText().toString());

                introduce_save(strBean);
            }
        });
    }


    private void showListDialog() {
        final String[] items = {"男", "女"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
        //listDialog.setTitle("我是一个列表Dialog");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:

                        tv_sex.setText("男");
                        strBean.setGender(1);
                        break;
                    case 1:

                        tv_sex.setText("女");
                        strBean.setGender(2);
                        break;
                    default:
                        break;
                }
            }
        });
        listDialog.show();
    }
    class StrBean {
        Integer gender;
        String name;
        String phone_no;
        String member_no;

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getMember_no() {
            return member_no;
        }

        public void setMember_no(String member_no) {
            this.member_no = member_no;
        }
    }

    Gson gson = new Gson();

    /**
     * 添加推荐好友
     *
     * @param strBean
     */
    public void introduce_save(final StrBean strBean) {

        LogUtil.i(gson.toJson(strBean));
        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.INTRODUCE_SAVE), gson.toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestSimpleBean requsetSimpleBean = gson.fromJson(jsonObject.toString(), RequestSimpleBean.class);
                if (requsetSimpleBean.getSuccess()) {
                    ToastUtils.showToastShort("成功推荐");
                  finish();
                    strBean.gender = 1;
                } else {
                    ToastUtils.showToastShort("推荐失败" + requsetSimpleBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }


}
