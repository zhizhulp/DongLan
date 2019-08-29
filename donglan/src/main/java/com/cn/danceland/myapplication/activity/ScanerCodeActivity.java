package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.bean.MotionVerifyBean;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.view.AlertDialogCustomToHint;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.vondear.rxtools.activity.ActivityScanerCode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2018/3/26 14:07
 * Email:644563767@qq.com
 * 扫码并处理
 */

public class ScanerCodeActivity extends ActivityScanerCode {
    private String from = "";
    private AlertDialogCustomToHint.MyDialog dialog;
    private boolean isScaner = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStatus();
    }

    @Override
    public void do_result(String result) {
        LogUtil.i(result);
        if (this.getIntent() != null) {
            from = this.getIntent().getStringExtra("from");
        }
        if (from != null && from.length() > 0) {
            switch (from) {
                case "entrance"://扫码入场
                    String[] resultA = result.toString().split("\\,");
                    if (resultA != null && resultA.length > 1) {
                        if (resultA[2].equals("13")) {
                            startActivity(new Intent(ScanerCodeActivity.this, ScanerCodeDetailActivity.class).putExtra("message", result));
                            finish();
                        } else {
                            showConfirmDialog(result);
                        }
                    } else {
                        showConfirmDialog(result);
                    }
                    break;
                case "train"://扫码训练
                    LogUtil.i("isScaner"+isScaner);
                    if (isScaner) {
                        if (result != null && result.length() > 0) {//开启后结果  0成功   1失败
                            scan_train(result);
                        } else {
                            showResultDialog("扫码失败", false);
                        }
                    }
                    break;
            }
        }
    }

    // BaseActivity中统一调用MobclickAgent 类的 onResume/onPause 接口
    // 子类中无需再调用
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 基础指标统计，不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 基础指标统计，不能遗漏
    }

    private void scan_qrcode(final String result) {//扫码入场
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.SCAN_QRCODE_ENTER_V2), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestSimpleBean requsetSimpleBean = new Gson().fromJson(s, RequestSimpleBean.class);
                if (requsetSimpleBean.getSuccess()) {
                    if (TextUtils.equals(requsetSimpleBean.getCode(), "0")) {
                        showResultDialog("入场成功", true);
                    } else {
                        showResultDialog(requsetSimpleBean.getErrorMsg(), true);
                    }
                } else {
                    showResultDialog(requsetSimpleBean.getErrorMsg(), true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                showResultDialog("入场失败:请查看网络连接", true);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("qrcode", result);
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    private void scan_train(final String result) {//扫码训练
        LogUtil.i("url=" + Constants.plus(Constants.PUSH_SCANER_TRAIN));
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_SCANER_TRAIN), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestSimpleBean requsetSimpleBean = new Gson().fromJson(s, RequestSimpleBean.class);
                if (requsetSimpleBean.getSuccess()) {
                    if (TextUtils.equals(requsetSimpleBean.getCode(), "0")) {
                        showResultDialog2("扫码成功，开始训练", false);
                    } else if (TextUtils.equals(requsetSimpleBean.getCode(), "1")) {
                        showResultDialog2("开启失败", false);
                    } else {
                        SPUtils.setBoolean(Constants.SCANER_CODE_TRAIN_ISLOOK, true);
                        showResultDialog2("扫码失败", false);
                    }
                } else {
                    showResultDialog2(requsetSimpleBean.getErrorMsg(), false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                showResultDialog2("扫码失败", false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("codeValue", result);
                LogUtil.i("SPUtils.getString(Constants.MY_TOKEN, null)" + SPUtils.getString(Constants.MY_TOKEN, null));
                LogUtil.i("trainParams" + map.toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 确认对话
     */
    private void showConfirmDialog(final String result) {
        dialog = new AlertDialogCustomToHint("确认", "取消").CreateDialog(ScanerCodeActivity.this, "是否入场", new AlertDialogCustomToHint.Click() {
            @Override
            public void ok_bt(int dialogOKB) {
                scan_qrcode(result);
                dialog.dismiss();
            }

            @Override
            public void cancle_bt(int btn_cancel) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 显示结果对话
     */
    private void showResultDialog(final String result, boolean cancel) {
        dialog = new AlertDialogCustomToHint("确认", "取消").CreateDialog(ScanerCodeActivity.this, result, new AlertDialogCustomToHint.Click() {
            @Override
            public void ok_bt(int dialogOKB) {
                dialog.dismiss();
            }

            @Override
            public void cancle_bt(int btn_cancel) {
                dialog.dismiss();
            }
        });
        if (!cancel) {
            dialog.GoneCancel();
        }
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 显示结果对话
     */
    private void showResultDialog2(final String result, boolean cancel) {
        dialog = new AlertDialogCustomToHint("确认", "取消").CreateDialog(ScanerCodeActivity.this, result, new AlertDialogCustomToHint.Click() {
            @Override
            public void ok_bt(int dialogOKB) {
                SPUtils.setBoolean(Constants.SCANER_CODE_TRAIN_ISLOOK, true);
                dialog.dismiss();
            }

            @Override
            public void cancle_bt(int btn_cancel) {
                dialog.dismiss();
            }
        });
        if (!cancel) {
            dialog.GoneCancel();
        }
        dialog.setCanceledOnTouchOutside(true);
    }

    /**
     * 显示结果对话 是否查看最后一条训练数据
     */
    private void showResultDialog3(final boolean isAerobic) {
        if (dialog != null) {
            dialog.dismiss();
        }
        isScaner = false;
        dialog = new AlertDialogCustomToHint("确认", "取消").CreateDialog(ScanerCodeActivity.this, "是否查看最后一条训练数据？", new AlertDialogCustomToHint.Click() {
            @Override
            public void ok_bt(int dialogOKB) {
                dialog.dismiss();
                Intent intent2 = new Intent(ScanerCodeActivity.this, MotionDataActivity.class);
                intent2.putExtra("isAerobic", isAerobic);
                startActivity(intent2);
                finish();
            }

            @Override
            public void cancle_bt(int btn_cancel) {
                SPUtils.setBoolean(Constants.SCANER_CODE_TRAIN_ISLOOK, false);
                dialog.dismiss();
                isScaner = true;
            }
        });
        dialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 扫码训练 查询是否占用健身设备
     */
    private void verifyStatus() {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_SCANER_TRAIN_STATUS), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                MotionVerifyBean requsetBean = new Gson().fromJson(s, MotionVerifyBean.class);
                if (requsetBean.getSuccess()) {
                    if (TextUtils.equals(requsetBean.getCode(), "0")) {
                        if (requsetBean.getData().getIsOccupy() == 1) {//是否占用设备(1：占用 0：未占用)
                            isScaner = false;
                            showResultDialog2("训练中，无法同时开启多台设备", false);
                        } else {

                            boolean isAerobic = true;
                            if (requsetBean.getData().getLastData() != null) {
                                if (requsetBean.getData().getLastData().getSub_type() < 5) {
                                    isAerobic = true;
                                } else {
                                    isAerobic = false;
                                }
                            }
                            boolean islook = SPUtils.getBoolean(Constants.SCANER_CODE_TRAIN_ISLOOK, false);//扫码训练  true最后一条没看
                            if (islook) {
                                showResultDialog3(isAerobic);
                            }
                        }
                    } else {
                        showResultDialog2(requsetBean.getErrorMsg(), false);
                    }
                } else {
                    showResultDialog2(requsetBean.getErrorMsg(), false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                showResultDialog2("请求失败", false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }
}
