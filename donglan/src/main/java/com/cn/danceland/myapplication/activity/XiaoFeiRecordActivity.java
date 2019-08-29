package com.cn.danceland.myapplication.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.XiaoFeiJiLvBean;
import com.cn.danceland.myapplication.bean.store.storebill.StoreBillCond;
import com.cn.danceland.myapplication.bean.store.storebill.StoreBillRequest;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyListView;
import com.cn.danceland.myapplication.utils.PriceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by feng on 2018/3/16.
 */

public class XiaoFeiRecordActivity extends BaseActivity {

    ListView lv_xiaofei;
    private StoreBillRequest request;
    private Gson gson;
    Data info;
    private SimpleDateFormat sdf;
    ImageView xiaofei_back;
    float allchongzhi, allxiaofei;
    TextView tv_leijichongzhi, tv_leijixiaofei;
    SharedPreferences bus_type;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.xiaofeirecord);
        initHost();

        initView();
        queryList();


    }

    private void initHost() {

        request = new StoreBillRequest();

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);

        bus_type = getSharedPreferences("bus_type", MODE_PRIVATE);

    }

    private void initView() {
        lv_xiaofei = findViewById(R.id.lv_xiaofei);
        tv_leijichongzhi = findViewById(R.id.tv_leijichongzhi);
        tv_leijixiaofei = findViewById(R.id.tv_leijixiaofei);

        rl_error = findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(this).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("请先购买储值卡");
        lv_xiaofei.setEmptyView(rl_error);


    }


    /**
     * @方法说明:按条件查询储值卡流水帐单列表
     **/
    public void queryList() {


        StoreBillCond cond = new StoreBillCond();
        // TODO 准备查询条件
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                LogUtil.e(json.toString());
                XiaoFeiJiLvBean result = gson.fromJson(json.toString(), XiaoFeiJiLvBean.class);
                if (result.getSuccess()) {
                    List<XiaoFeiJiLvBean.Data> list = result.getData();
                    if(list!=null){

                        for(int i=0;i<list.size();i++){
                            for (int j=0;j<list.get(i).getList().size();j++){
                                if(list.get(i).getList().get(j).getType()==1){
                                    allchongzhi = allchongzhi+list.get(i).getList().get(j).getPrice()+list.get(i).getList().get(j).getGiving();
                                }else if(list.get(i).getList().get(j).getType()==3){
                                    allxiaofei = allxiaofei+list.get(i).getList().get(j).getPrice();
                                }
                            }


                        }
                        tv_leijichongzhi.setText(PriceUtils.formatPrice2String(allchongzhi)+"元");
                        tv_leijixiaofei.setText(PriceUtils.formatPrice2String(allxiaofei)+"元");
                        lv_xiaofei.setAdapter(new MyAdapter(list));
                    }
                }

                //        DLResult<List<StoreBill>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<StoreBill>>>() {
//                }.getType());
//                if (result.isSuccess()) {
//                    List<StoreBill> list = result.getData();
//                    if(list!=null){
//
//                        for(int i=0;i<list.size();i++){
//                            if(list.get(i).getType()==1){
//                                allchongzhi = allchongzhi+list.get(i).getPrice()+list.get(i).getGiving();
//                            }else if(list.get(i).getType()==3){
//                                allxiaofei = allxiaofei+list.get(i).getPrice();
//                            }
//                        }
//                        tv_leijichongzhi.setText(allchongzhi+"元");
//                        tv_leijixiaofei.setText(allxiaofei+"元");
//                        lv_xiaofei.setAdapter(new MyAdapter(list));
//                    }
//                    System.out.println(list);
//                    // TODO 请求成功后的代码
//                } else {
//                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
//                }
            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        List<XiaoFeiJiLvBean.Data> list;

        public MyAdapter(  List<XiaoFeiJiLvBean.Data> list) {
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
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
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(XiaoFeiRecordActivity.this, R.layout.xiaofei_father_item, null);
                viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
                viewHolder.mylist = convertView.findViewById(R.id.mylist);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_date.setText(list.get(position).getDate());
         viewHolder.mylist.setAdapter(new MyChildAdapter(list.get(position).getList()));

            return convertView;
        }

        class ViewHolder {
            TextView tv_date;
            MyListView mylist;
        }

    }


    private class MyChildAdapter extends BaseAdapter {
        List<XiaoFeiJiLvBean.Data.XList> list;

        MyChildAdapter(List<XiaoFeiJiLvBean.Data.XList> list) {

            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
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
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(XiaoFeiRecordActivity.this, R.layout.xiaofei_item, null);
                viewHolder.tv_goodtype = convertView.findViewById(R.id.tv_goodtype);
                viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
                viewHolder.tv_xiaofei = convertView.findViewById(R.id.tv_xiaofei);
                viewHolder.tv_ac = convertView.findViewById(R.id.tv_ac);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            int type = list.get(position).getBus_type();
            viewHolder.tv_time.setVisibility(View.VISIBLE);
            if (list.get(position).getType() == 1) {//充钱
                viewHolder.tv_ac.setText("充");
                viewHolder.tv_xiaofei.setTextColor(Color.parseColor("#ff5e3a"));
                viewHolder.tv_ac.setBackground(getResources().getDrawable(R.drawable.circle_yellow));
                viewHolder.tv_xiaofei.setText("¥ " + list.get(position).getPrice() + "元");
            } else if (list.get(position).getType() == 3) {//消费
                viewHolder.tv_xiaofei.setTextColor(Color.parseColor("#6d819c"));
                viewHolder.tv_ac.setText("消");
                viewHolder.tv_ac.setBackground(getResources().getDrawable(R.drawable.circle_deep_bule));
                viewHolder.tv_xiaofei.setText("¥ " + list.get(position).getPrice() + "元");

            } else if (list.get(position).getType() == 2) {//退钱
                viewHolder.tv_ac.setText("退");
                viewHolder.tv_xiaofei.setTextColor(Color.parseColor("#6d819c"));
                viewHolder.tv_ac.setBackground(getResources().getDrawable(R.drawable.circle_deep_red));
                viewHolder.tv_xiaofei.setText("¥ " + list.get(position).getPrice() + "元");
                viewHolder.tv_time.setVisibility(View.GONE);
            }
            if (list.get(position).getGiving() != 0) {
                viewHolder.tv_time.setText("¥+ " +list.get(position).getGiving() + "元");
                viewHolder.tv_time.setVisibility(View.VISIBLE);
//                    viewHolder.tv_xiaofei.setText("¥ " + list.get(position).getPrice() + " + " + list.get(position).getGiving() + "元");
            } else {
                viewHolder.tv_time.setVisibility(View.GONE);
            }


            if (type != 0) {
                viewHolder.tv_goodtype.setText(bus_type.getString(type+"", ""));
            }
            //viewHolder.tv_time.setText(sdf.format(list.get(position).getOperate_time()));

            return convertView;
        }
    }

    class ViewHolder {
        TextView tv_goodtype, tv_time, tv_xiaofei,tv_ac;
    }

}
