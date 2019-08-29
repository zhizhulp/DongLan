package com.cn.danceland.myapplication.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;
    private TextView tv_result;
    private Button btn_commit;
    int errCode=-1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        tv_result = findViewById(R.id.tv_result);
        btn_commit = findViewById(R.id.btn_commit);
//        btn_commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (errCode == 0) {
//                    tv_result.setText("支付成功");
//                    EventBus.getDefault().post(new StringEvent("支付成功", 40001));
//                }else {
//                    tv_result.setText("支付失败");
//                    EventBus.getDefault().post(new StringEvent("支付失败", 40002));
//                }
//            }
//        });
        api = WXAPIFactory.createWXAPI(this, "wx530b17b3c2de2e0d");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.i("onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            errCode=resp.errCode;
            if (resp.errCode == 0) {
                tv_result.setText("支付成功");
                EventBus.getDefault().post(new StringEvent("支付成功", 40001));
            }else {
                tv_result.setText("支付失败");
                EventBus.getDefault().post(new StringEvent("支付失败", 40002));
                ToastUtils.showToastShort("支付失败");
            }
            finish();


//            if (resp.errCode == 0) {
//
//
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("提示");
//                builder.setMessage("支付成功");
//                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        EventBus.getDefault().post(new StringEvent("支付成功", 40001));
//
//                    }
//                });
//                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialogInterface) {
//                        EventBus.getDefault().post(new StringEvent("支付成功", 40001));
//                        finish();
//                    }
//                });
//                builder.show();
//            }
//            if (resp.errCode == -1) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("提示");
//                builder.setMessage("支付失败");
//                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        EventBus.getDefault().post(new StringEvent("支付失败", 40002));
//                    }
//                });
//                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialogInterface) {
//                        EventBus.getDefault().post(new StringEvent("支付失败", 40002));
//                        finish();
//                    }
//                });
//                builder.show();
//            }
//            if (resp.errCode == -2) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("提示");
//                builder.setMessage("支付已取消");
//                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialogInterface) {
//                        EventBus.getDefault().post(new StringEvent("支付失败", 40002));
//                        finish();
//                    }
//                });
//                builder.show();
//            }
        }
    }
}