package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.HuiJiYeJiBean;
import com.cn.danceland.myapplication.fragment.GeRenYeJiFragment;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;

/**
 * Created by shy on 2018/6/25 15:18
 * Email:644563767@qq.com
 * 会籍个人业绩
 */


public class GeRenYeJiActivity extends FragmentActivity {


    private GeRenYeJiFragment geRenYeJiFragment;
    String toChatUsername;
    private boolean isjiaolian;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_ge_ren_ye_ji);


        isjiaolian=getIntent().getBooleanExtra("isjiaolian",false);
      //  LogUtil.i(isjiaolian+"");
        geRenYeJiFragment = new GeRenYeJiFragment();
        Bundle bundle=new Bundle();
        bundle.putString("date",getIntent().getStringExtra("date"));
        bundle.putString("id",getIntent().getStringExtra("id"));
        bundle.putBoolean("isjiaolian",isjiaolian);
        geRenYeJiFragment.setArguments(bundle);
        initView();
        getSupportFragmentManager().beginTransaction().add(R.id.container, geRenYeJiFragment).commit();

    }

    private void initView() {
        HuiJiYeJiBean.Data data= (HuiJiYeJiBean.Data) getIntent().getSerializableExtra("data");
        LogUtil.i(data.toString());
        ImageView iv_avatar=findViewById(R.id.iv_avatar);
        TextView tv_name=findViewById(R.id.tv_name);
        TextView tv_sum=findViewById(R.id.tv_sum);
        TextView tv_yewu1=findViewById(R.id.tv_yewu1);
        TextView tv_yewu2=findViewById(R.id.tv_yewu2);
        tv_name.setText(data.getEmp_name());

        if (isjiaolian){
            tv_sum.setText("总业绩：" + data.getAllccourse() + "元");
            tv_yewu1.setText("单人私教：" + data.getSinglecourse());
            tv_yewu2.setText("团体私教：" + data.getGroupcourse());
        }else {
            tv_sum.setText("总业绩：" + data.getTotal() + "元");
            tv_yewu1.setText("办卡：" + data.getNewcard());
            tv_yewu2.setText("租柜：" + data.getLeaselocker());
        }



        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(this, 10));

        Glide.with(this).load(data.getAvatar_url()).apply(options).into(iv_avatar);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        // make sure only one chat activity is opened
//        String username = intent.getStringExtra("userId");
//        if (toChatUsername.equals(username))
//            super.onNewIntent(intent);
//        else {
//            finish();
//            startActivity(intent);
//        }
//
//    }

}
