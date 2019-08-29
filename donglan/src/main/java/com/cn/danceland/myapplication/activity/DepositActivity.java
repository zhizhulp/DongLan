package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestDepositBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.PriceUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shy on 2017/12/27 16:21
 * Email:644563767@qq.com
 */


public class DepositActivity extends BaseActivity implements View.OnClickListener {


    private ListView listView;
    private List<RequestDepositBean.Data> mDepositList = new ArrayList<>();
    MyDepositListAdapter mListAdapter;
    private TextView tv_error;
    private ImageView iv_error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        initView();
        initData();

    }

    class Requsetbean {
        public String bus_type;
        public String status;
    }

    private void initData() {

        Gson gson = new Gson();
        Requsetbean requsetbean = new Requsetbean();
        if (TextUtils.isEmpty(getIntent().getStringExtra("bus_type"))) {
            requsetbean.bus_type = "1";
        } else {
            requsetbean.bus_type = getIntent().getStringExtra("bus_type");
        }

        requsetbean.status = "1";
        String strBean = gson.toJson(requsetbean);

        try {
            commit_deposit(strBean.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        listView = findViewById(R.id.listview);


        View listEmptyView = findViewById(R.id.rl_no_info);

        tv_error = listEmptyView.findViewById(R.id.tv_error);
        iv_error = listEmptyView.findViewById(R.id.iv_error);
        tv_error.setText("您没有可用的定金");
        listView.setEmptyView(listEmptyView);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        mListAdapter = new MyDepositListAdapter(this);
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = getIntent();
                intent.putExtra("dingjin", mDepositList.get(i).getMoney());
                intent.putExtra("id", mDepositList.get(i).getId());
                setResult(11, intent);
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_cancel:
                Intent intent = getIntent();
                intent.putExtra("dingjin", 0f);
                setResult(11, intent);
                finish();
                break;
            default:
                break;
        }
    }

    public void commit_deposit(final String str) throws JSONException {

        JSONObject jsonObject = new JSONObject(str);

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_ALL_DEPOSIT), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestDepositBean depositBean = new RequestDepositBean();
                Gson gson = new Gson();
                depositBean = gson.fromJson(jsonObject.toString(), RequestDepositBean.class);

                if (depositBean.getSuccess()) {
                    mDepositList = depositBean.getData();
                    mListAdapter.notifyDataSetChanged();

                } else {
                    ToastUtils.showToastShort("查询失败");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                iv_error.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");

            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }

    class MyDepositListAdapter extends BaseAdapter {
        private Context context;

        public MyDepositListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mDepositList.size();
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
                convertView = View.inflate(context, R.layout.listview_item_deposit, null);
                vh.tv_price = convertView.findViewById(R.id.tv_price);
                vh.tv_name = convertView.findViewById(R.id.tv_name);
                vh.iv_type = convertView.findViewById(R.id.iv_type);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.tv_price.setText(PriceUtils.formatPrice2String(mDepositList.get(position).getMoney()));
            if (mDepositList.get(position).getBus_type() == 1) {
                vh.tv_name.setText("会员卡定金");
                vh.iv_type.setImageResource(R.drawable.img_dingjin1);
            }
//            if (mDepositList.get(position).getBus_type() == 2) {
//                vh.tv_name.setText("租柜定金");
//                vh.iv_type.setImageResource(R.drawable.img_dingjin2);
//            }
            if (mDepositList.get(position).getBus_type() == 3) {
                vh.tv_name.setText("私教定金");
                vh.iv_type.setImageResource(R.drawable.img_dingjin3);
            }


            return convertView;

        }


        class ViewHolder {
            public TextView tv_price;
            public TextView tv_name;
            public ImageView iv_type;
        }
    }
}
