package com.cn.danceland.myapplication.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.QrCodeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by shy on 2018/3/26 11:01
 * Email:644563767@qq.com
 */


public class MyQRCodeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_qrcode);
        EventBus.getDefault().register(this);
        initData();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 6881:
                    showNormalDialog(msg1);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private String msg1;

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {
            case 6881:
//                ToastUtils.showToastShort(event.getMsg());
//                Toast.makeText(this,event.getMsg(),Toast.LENGTH_LONG).show();

                Message message = Message.obtain();
                message.what = 6881;
                msg1 = event.getMsg();
                handler.sendMessage(message);
                break;
            default:
                break;
        }

    }

    private void initData() {
        ImageView mIvCode = findViewById(R.id.iv_qrcode);
        String data = getIntent().getStringExtra("data");
//        RxQRCode.createQRCode(data, 800, 800, mIvCode);
        Bitmap bitmap = QrCodeUtil.createQRImage(MyQRCodeActivity.this, data,
                BitmapFactory.decodeResource(getResources(), R.drawable.img_dl_logo));
        mIvCode.setImageBitmap(bitmap);
    }













    private void showNormalDialog(String msg) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage(msg);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        msg1 = "";
                        finish();
                    }
                });

        normalDialog.show();
    }

}
