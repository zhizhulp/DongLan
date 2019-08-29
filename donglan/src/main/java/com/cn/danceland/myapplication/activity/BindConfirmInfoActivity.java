package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestNewBindBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shy on 2018/12/24 18:21
 * Email:644563767@qq.com
 */


public class BindConfirmInfoActivity extends BaseActivity {
    private RequestNewBindBean info;
    private List<RequestNewBindBean.Data.Branchs> branchs=new ArrayList<>();
    String phone;
    private boolean isresetpsd=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_confirm_info);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        EventBus.getDefault().register(this);
        initView();
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


    private void initView() {
        info = (RequestNewBindBean) getIntent().getSerializableExtra("info");
        branchs=info.getData().getBranchs();
        phone = getIntent().getStringExtra("phone");
        isresetpsd=getIntent().getBooleanExtra("isresetpsd",false);
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_login = findViewById(R.id.tv_login);
        TextView tv_register = findViewById(R.id.tv_register);
        ImageView iv_sex = findViewById(R.id.iv_sex);
        CircleImageView circleimageview = findViewById(R.id.circleimageview);
        MyListView mylist_shop = findViewById(R.id.mylist_shop);
        mylist_shop.setAdapter(new MyAdapter());
        tv_name.setText(info.getData().getPerson().getCname());
        if (info.getData().getPerson().getGender()==1){
            iv_sex.setImageResource(R.drawable.img_sex1);
        }else {
            iv_sex.setImageResource(R.drawable.img_sex2);
        }
        RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar).error(R.drawable.img_my_avatar);
        Glide.with(this).load(info.getData().getPerson().getSelf_avatar_path()).apply(options).into(circleimageview);
        if (isresetpsd){
            tv_login.setText("是我的，重置密码");
        }
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//设置密码后登陆
                startActivity(new Intent(BindConfirmInfoActivity.this,SetPswdActivity.class)
                        .putExtra("id",info.getData().getPerson().getId())
                        .putExtra("login_type",1)
                        .putExtra("phone",phone)

                );
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//设置密码后重新创建人
                startActivity(new Intent(BindConfirmInfoActivity.this,SetPswdActivity.class)
                        .putExtra("id",info.getData().getPerson().getId())
                        .putExtra("login_type",2)
                        .putExtra("phone",phone)
                );
            }
        });
    }

    class  MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return branchs.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=View.inflate(BindConfirmInfoActivity.this,R.layout.listview_item_myjoin_shop,null);
            TextView tv_shopname=convertView.findViewById(R.id.tv_shopname);
            TextView tv_auth=convertView.findViewById(R.id.tv_auth);
            tv_shopname.setText(branchs.get(position).getBranch_name());
            tv_auth.setText(branchs.get(position).getAuth());
            return convertView;
        }
    }

}
