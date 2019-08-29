package com.cn.danceland.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.GeRenYeWuActivity;
import com.cn.danceland.myapplication.adapter.recyclerview.CommonAdapter;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ViewHolder;
import com.cn.danceland.myapplication.bean.HuiJiYeWuBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.base.BaseRecyclerViewRefreshFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shy on 2018/12/5 10:37
 * Email:644563767@qq.com
 * <p>
 * 总业绩
 */


public class ZongYeWument extends BaseRecyclerViewRefreshFragment {

    private List<HuiJiYeWuBean.Data> dataList = new ArrayList<>();
    private MylistAtapter mylistAtapter;
    private int mCurrentPage = 0;
    private String mCurrentDate =null;
    private boolean isjiaolian;

    @Override
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {


            case 7100://刷新页面
                if (event.getMsg() != null) {
                    mCurrentDate=event.getMsg();
                    findhjyj(event.getMsg(), event.getMsg());
                }

            default:
                break;
        }
    }



    @Override
    public CommonAdapter setAtapter() {
        isjiaolian=getArguments().getBoolean("isjiaolian");
        mylistAtapter = new MylistAtapter(mActivity, R.layout.listview_item_jinriyewu, dataList);
//        EmptyWrapper mEmptyWrapper = new EmptyWrapper(mylistAtapter);
//        mEmptyWrapper.setEmptyView(R.layout.no_info_layout);
        mylistAtapter.setEmptyView(R.layout.no_info_layout);
        mylistAtapter.addHeaderView(View.inflate(mActivity,R.layout.empty_8dp_layout,null));
        mylistAtapter.addFootView(View.inflate(mActivity,R.layout.empty_8dp_layout,null));
        return mylistAtapter;
    }


    @Override
    public void initDownRefreshData() {
        mCurrentPage = 0;
        if (mCurrentDate==null){
            mCurrentDate= TimeUtils.timeStamp2Date(System.currentTimeMillis()+"","yyyy-MM-dd");
        }
        findhjyj(mCurrentDate, mCurrentDate);
        setOnlyDownReresh();
    }

    @Override
    public void upDownRefreshData() {


    }


    private void findhjyj(final String start, final String end) {
        String url;
        if (isjiaolian){
            url= Constants.plus(Constants.QUERY_JIAOLIANYEWU);
         }else {
            url= Constants.plus(Constants.QUERY_HUIJIYEWU);
        }

        MyStringRequest request = new MyStringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                HuiJiYeWuBean huiJiYeJiBean = new Gson().fromJson(s, HuiJiYeWuBean.class);
                dataList = huiJiYeJiBean.getData();

                getListAdapter().setDatas(dataList);
                getListAdapter().notifyDataSetChanged();
                double zongyeji=0;
                for (int i=0;i<dataList.size();i++ ){
                    if (isjiaolian){
                        zongyeji=zongyeji+dataList.get(i).getAll();
                    }else {
                        zongyeji=zongyeji+dataList.get(i).getTotal();
                    }


                }

                EventBus.getDefault().post(new StringEvent((int)zongyeji+"",7102));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("start", start);
                map.put("end", end);
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    class MylistAtapter extends CommonAdapter<HuiJiYeWuBean.Data> {

        private List<HuiJiYeWuBean.Data> datas;


        public MylistAtapter(Context context, int layoutId, List<HuiJiYeWuBean.Data> datas) {
            super(context, layoutId, datas);
            this.datas = datas;
        }


        @Override
        public void convert(ViewHolder viewHolder, final HuiJiYeWuBean.Data data, int position) {

            if (isjiaolian){
                viewHolder.setText(R.id.tv_name, data.getEmp_name());
                viewHolder.setText(R.id.tv_sum, "总业务：" + data.getAll() + "个");
                viewHolder.setText(R.id.tv_yewu1, "体侧：" + data.getTice());
                viewHolder.setText(R.id.tv_yewu2, "体侧分析：" + data.getTicefenxi());
                viewHolder.setText(R.id.tv_yewu3, "健身计划：" + data.getJihua());
                RequestOptions options = new RequestOptions()
                        .transform(new GlideRoundTransform(mActivity, 10));

                Glide.with(mActivity).load(data.getAvatar_url()).apply(options).into((ImageView) viewHolder.getView(R.id.iv_avatar));
                viewHolder.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(mActivity, GeRenYeWuActivity.class).putExtra("id",data.getEmployee_id())
                                .putExtra("date",mCurrentDate)
                                .putExtra("data",data)
                                .putExtra("isjiaolian",isjiaolian)
                        );
                    }
                });
            }else {
                viewHolder.setText(R.id.tv_name, data.getEmp_name());
                viewHolder.setText(R.id.tv_sum, "总业务：" + data.getTotal() + "个");
                viewHolder.setText(R.id.tv_yewu1, "潜增：" + data.getNewGuest());
                viewHolder.setText(R.id.tv_yewu2, "潜访：" + data.getVisitGuest());
                viewHolder.setText(R.id.tv_yewu3, "会访：" + data.getVisitMember());
                RequestOptions options = new RequestOptions()
                        .transform(new GlideRoundTransform(mActivity, 10));

                Glide.with(mActivity).load(data.getAvatar_url()).apply(options).into((ImageView) viewHolder.getView(R.id.iv_avatar));
                viewHolder.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(mActivity, GeRenYeWuActivity.class).putExtra("id",data.getEmployee_id())
                                .putExtra("date",mCurrentDate)
                                .putExtra("data",data)
                        );
                    }
                });
            }


            //会籍主管或会籍主管
            if (SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIANZHUGUAN || SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_HUIJIZHUGUANG) {


            }else {
                viewHolder.setOnClickListener(R.id.ll_item,null);
            }

        }


    }
}
