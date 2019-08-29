package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.ShopJiaoLianBean;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.view.DongLanTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shy on 2018/11/2 09:28
 * Email:644563767@qq.com
 */


public class EmployeeListActivity extends BaseActivity {
    private ListView listView;
    List<ShopJiaoLianBean.Data> huijiList = new ArrayList<>();
    private TextView tv_error;
    private ImageView iv_error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        huijiList = (List<ShopJiaoLianBean.Data>) bundle.getSerializable("huijiList");
        String title=bundle.getString("title");
        DongLanTitleView titleView=findViewById(R.id.dl_title);
        titleView.setTitle(title);
        listView = findViewById(R.id.listview);


        View listEmptyView = findViewById(R.id.rl_no_info);

        tv_error = listEmptyView.findViewById(R.id.tv_error);
        iv_error = listEmptyView.findViewById(R.id.iv_error);
        tv_error.setText("没有数据");
        listView.setEmptyView(listEmptyView);


        MyListAdapter mListAdapter = new MyListAdapter(this);
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }


    class MyListAdapter extends BaseAdapter {
        private Context context;

        public MyListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return huijiList.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {

            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = View.inflate(context, R.layout.listview_item_emp_list, null);
                vh.tv_nickname = convertView.findViewById(R.id.tv_nickname);
                vh.iv_sex = convertView.findViewById(R.id.iv_sex);
                vh.ll_item = convertView.findViewById(R.id.ll_item);
                vh.iv_avatar = convertView.findViewById(R.id.iv_avatar);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            //第一个是上下文，第二个是圆角的弧度
            RequestOptions options = new RequestOptions().transform(new GlideRoundTransform(context, 10)).placeholder(R.drawable.img_avatar1).error(R.drawable.img_avatar1);
            Glide.with(context).load(huijiList.get(position).getAvatar_url()).apply(options).into(vh.iv_avatar);
            if (huijiList.get(position).getGender() == 1) {
                vh.iv_sex.setImageResource(R.drawable.img_sex1);
            } else {
                vh.iv_sex.setImageResource(R.drawable.img_sex2);
            }
            
            vh.tv_nickname.setText(huijiList.get(position).getCname());
            vh.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(EmployeeListActivity.this, EmpUserHomeActivty.class)
                            .putExtra("person_id", huijiList.get(position).getPerson_id()+"")
                            .putExtra("employee_id", huijiList.get(position).getId()+"")
                            .putExtra("branch_id", huijiList.get(position).getBranch_id()+"")
                            .putExtra("avatar",huijiList.get(position).getAvatar_url()));
                }
            });



            return convertView;

        }


        class ViewHolder {
            public TextView tv_nickname;
            public ImageView iv_avatar;
            LinearLayout ll_item;
            public ImageView iv_sex;
        }
    }
}
