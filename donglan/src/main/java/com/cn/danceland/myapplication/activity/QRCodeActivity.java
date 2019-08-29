package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.vondear.rxtools.activity.ActivityScanerCode;

/**
 * Created by shy on 2018/3/16 16:16
 * Email:644563767@qq.com
 */


public class QRCodeActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        initView();
        initData();
    }

    private void initView() {
        Button btn_commit
                =findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.btn_commit:
       //     RxActivityTool.skipActivity(QRCodeActivity.this, ActivityScanerCode.class);
            startActivity(new Intent(QRCodeActivity.this, ActivityScanerCode.class));
        break;
        default:
        break;
        }
    }
}
