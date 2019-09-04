package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestScanerCodeBean;
import com.cn.danceland.myapplication.bean.RequestScanerCodeIfHandBean;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.view.AlertDialogCustomToHint;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.RoundImageView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 扫码入场-详情
 * Created by yxx on 2018-09-25.
 */
public class ScanerCodeDetailActivity extends BaseActivity {
    public static final int SELECT_HAND_DATA = 0x1001;
    private Context context;
    private DongLanTitleView title;//数据title
    private RoundImageView icon_iv;//icon
    private TextView name_tv;//姓名
    private TextView sex_tv;//性别
    private TextView tel_tv;//电话
    private TextView cardname_tv;//卡名称
    private TextView cardid_tv;//卡号
    private TextView effective_date_tv;//有效日期
    private TextView sum_tv;//剩余次数
    private TextView hand_tv;//手牌号
    private LinearLayout btn_cancel;
    private LinearLayout btn_ok;

    private RequestScanerCodeBean.Data memberData = new RequestScanerCodeBean.Data();

    private Gson gson = new Gson();

    private String message;//二维码扫出来的数据
    private String qrcode;//卡ID
    private String codeId;//卡ID 入场离场

    private String selectId = "";
    private String selectCode = "";
    private String selectArea = "";

    private AlertDialogCustomToHint.MyDialog dialog;
    private TextView tvOk;
    private TextView tvCancel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaner_code_details);
        context = this;

        message = getIntent().getStringExtra("message");
        LogUtil.i("message--" + message);
        String[] resultA = message.toString().split("\\,");
        if (resultA != null && resultA.length > 1) {
            StringBuilder data = new StringBuilder().append("1").append(",").append("1").append(",").append(Constants.QR_MAPPING_CARD_ENTER).append(",").append(resultA[resultA.length - 1]);
            codeId = resultA[resultA.length - 2];
        }
        qrcode = message.toString();
        LogUtil.i("codeId--" + codeId);
        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        title.setTitle("扫码入场");
        icon_iv = findViewById(R.id.icon_iv);
        name_tv = findViewById(R.id.name_tv);
        sex_tv = findViewById(R.id.sex_tv);
        tel_tv = findViewById(R.id.tel_tv);
        cardname_tv = findViewById(R.id.cardname_tv);
        cardid_tv = findViewById(R.id.cardid_tv);
        effective_date_tv = findViewById(R.id.effective_date_tv);
        sum_tv = findViewById(R.id.sum_tv);
        hand_tv = findViewById(R.id.hand_tv);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_ok = findViewById(R.id.btn_ok);

        tvOk = ((TextView) findViewById(R.id.tv_ok));
        tvCancel = ((TextView) findViewById(R.id.tv_cancel));
        queryData();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (memberData.getEnter()) {//已连接手牌
                    showConfirmDialog();//确认入场 离场
                } else {//未连接手牌
                    submitData();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //提交数据
    private void submitData() {
        StrBean prarmsBean = new StrBean();
        prarmsBean.param_key = "has_hand_card";
        //手牌  格式1，1,13，***
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_PARAM_KEY), gson.toJson(prarmsBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestScanerCodeIfHandBean requsetBean = gson.fromJson(jsonObject.toString(), RequestScanerCodeIfHandBean.class);
                if (requsetBean.getSuccess()) {
                    if (TextUtils.equals(requsetBean.getData().getHas_hand_card(), "1")) {//1:有手牌  0：没有手牌
                        Intent intent = new Intent(context, AlertDialogToListActivity.class);
                        intent.putExtra("from", "扫码入场");
                        intent.putExtra("gender", memberData.getGender() + "");
                        startActivityForResult(intent, SELECT_HAND_DATA);
                    } else {
                        showConfirmDialog();//确认入场
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 查询数据
     */
    private void queryData() {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.SCAN_QRCODE_ENTER_V2), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestScanerCodeBean requsetBean = new Gson().fromJson(s, RequestScanerCodeBean.class);
                if (requsetBean.getSuccess()) {
                    if (TextUtils.equals(requsetBean.getCode(), "0")) {
                        memberData = requsetBean.getData();
                        if (context != null) {//设置图片
                            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_donglan_loading);
                            if (memberData.getImg_url() != null && memberData.getImg_url().length() > 0) {
                                Glide.with(context).load(memberData.getAvatar_url()).apply(options).into(icon_iv);
                            }
                        }
                        title.setTitle(memberData.getEnter() ? "扫码离场" : "扫码入场");
                        tvOk.setText(memberData.getEnter() ? "确认离场" : "确认入场");
                        tvCancel.setText(memberData.getEnter() ? "取消离场" : "取消入场");
                        name_tv.setText(memberData.getCname());
                        if (memberData.getGender() == 1) {
                            sex_tv.setText("男");//性别
                        } else if (memberData.getGender() == 2) {
                            sex_tv.setText("女");//性别
                        } else {
                            sex_tv.setText("未设置");//性别
                        }
                        tel_tv.setText(memberData.getPhone_no());
                        cardname_tv.setText(memberData.getType_name());
                        cardid_tv.setText(memberData.getCard_no());
                        effective_date_tv.setText(TimeUtils.timeStamp2Date(memberData.getOpen_date() + "", "yyyy.MM.dd")
                                + "-" + TimeUtils.timeStamp2Date(memberData.getEnd_date() + "", "yyyy.MM.dd"));
                        if (memberData.getTotal_count() != null && memberData.getTotal_count().length() > 0) {
                            sum_tv.setText(memberData.getTotal_count() + "次");
                        } else {
                            sum_tv.setText("无");
                        }
                        if ((memberData.getHand_card_code() != null && memberData.getHand_card_code().length() > 0)
                                && (memberData.getHand_card_area() != null && memberData.getHand_card_area().length() > 0)) {//已连接手牌
                            hand_tv.setText(memberData.getHand_card_area() + "-" + memberData.getHand_card_code());
                        } else {//未连接手牌
                            hand_tv.setText("未选择");
                        }
                    } else {
                        showResultDialog(requsetBean.getErrorMsg());
                    }
                } else {
                    showResultDialog(requsetBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                showResultDialog("入场失败:请查看网络连接");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("qrcode", qrcode);
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 确认入场或确认离场接口
     */
    private void scan_qrcode() {
        SendBean prarmsBean = new SendBean();
        prarmsBean.card_id = codeId;
        if (selectId != null && selectId.length() > 0) {
            prarmsBean.hand_card_id = selectId;
            LogUtil.i("prarmsBean--" + prarmsBean.hand_card_id);
        }
        LogUtil.i("prarmsBean--" + prarmsBean.card_id);

        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.SEND_CARD_ENTER), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s.toString());
                RequestSimpleBean requsetSimpleBean = new Gson().fromJson(s.toString(), RequestSimpleBean.class);
                if (requsetSimpleBean.getSuccess()) {
                    if (TextUtils.equals(requsetSimpleBean.getCode(), "0")) {
                        showResultDialog(requsetSimpleBean.getErrorMsg());
                    } else {
                        showResultDialog(requsetSimpleBean.getErrorMsg());
                    }
                } else {
                    showResultDialog(requsetSimpleBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                showResultDialog("入场失败:请查看网络连接");
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("card_id", codeId);
                map.put("hand_card_id", selectId);
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_HAND_DATA) {
            if (resultCode == SELECT_HAND_DATA) {
                selectId = data.getStringExtra("selectId");
                selectCode = data.getStringExtra("selectCode");
                selectArea = data.getStringExtra("selectArea");
                LogUtil.i("selectId--" + selectId);
                LogUtil.i("selectCode--" + selectCode);
                LogUtil.i("selectArea--" + selectArea);
                if (selectId != null && selectId.length() > 0) {
                    hand_tv.setText(selectArea + "-" + selectCode);
                    showConfirmDialog();//确认入场
                }
            }
        }
    }

    /**
     * 显示结果对话
     */
    private void showResultDialog(final String result) {
        dialog = new AlertDialogCustomToHint("确认", "取消").CreateDialog(context, result, new AlertDialogCustomToHint.Click() {
            @Override
            public void ok_bt(int dialogOKB) {
                dialog.dismiss();
                finish();
            }

            @Override
            public void cancle_bt(int btn_cancel) {
                dialog.dismiss();
                finish();
            }
        });
    }

    /**
     * 确认对话
     */
    private void showConfirmDialog() {
        String hintStr;
        if (memberData.getEnter()) {//已连接手牌
            hintStr = "是否离场";
        } else {//未连接手牌
            hintStr = "是否入场";
        }
        dialog = new AlertDialogCustomToHint("确认", "取消").CreateDialog(context, hintStr, new AlertDialogCustomToHint.Click() {
            @Override
            public void ok_bt(int dialogOKB) {
                dialog.dismiss();
                scan_qrcode();
            }

            @Override
            public void cancle_bt(int btn_cancel) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 是否有手牌
     */
    class StrBean {
        public String param_key;
    }

    /**
     * 确认入场或确认离场接口
     */
    class SendBean {
        public String card_id;//健身卡id
        public String hand_card_id;//手牌id
    }
}
