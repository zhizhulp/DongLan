package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseRecyclerViewRefreshActivity;
import com.cn.danceland.myapplication.adapter.recyclerview.CommonAdapter;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ViewHolder;
import com.cn.danceland.myapplication.bean.RequestVideoListBean;
import com.cn.danceland.myapplication.bean.RequsetVideoHeadBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyListView;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.JZVideoPlayer;

/**
 * Created by shy on 2019/1/7 13:40
 * Email:644563767@qq.com
 * 视频列表
 */

public class VideoListActivity extends BaseRecyclerViewRefreshActivity {
    private List<RequestVideoListBean.Data.Content> datas = new ArrayList<>();
    private List<List<RequsetVideoHeadBean.Data>> videohead_datas = new ArrayList<>();
    private MyListView myListView;
    private int mCurrentPage = 0;
    private int[] currentTypes = {0, 0, 0, 0, 0};
    private RequsetVideoHeadBean videoHeadBean;

    @Override
    public CommonAdapter setAtapter() {
        setTitle("视频列表");
        MyAdapter myAdapter = new MyAdapter(this, R.layout.item_video_list, datas);
        View view = View.inflate(this, R.layout.headview_video, null);
        myListView = view.findViewById(R.id.lv_videoheader);

        myAdapter.addHeaderView(view);

        return myAdapter;
    }

    @Override
    public void initDownRefreshData() {
        mCurrentPage = 0;
        getHeaderData();
        getListData();
    }

    @Override
    public void upDownRefreshData() {
        getListData();
    }

    class MyAdapter extends CommonAdapter<RequestVideoListBean.Data.Content> {

        public MyAdapter(Context context, int layoutId, List<RequestVideoListBean.Data.Content> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, RequestVideoListBean.Data.Content videoBean, int position) {
            holder.setText(R.id.tv_tiltle, videoBean.getName());
            holder.setText(R.id.tv_desc, videoBean.getRemark());
            Glide.with(VideoListActivity.this).load(videoBean.getImg_url()).into((ImageView) holder.getView(R.id.iv_video));
        }
    }

    class MyAdapter1 extends CommonAdapter<RequsetVideoHeadBean.Data> {
        int pos;

        public MyAdapter1(Context context, int layoutId, List<RequsetVideoHeadBean.Data> datas, int pos) {

            super(context, layoutId, datas);
            this.pos = pos;
        }

        @Override
        protected void convert(ViewHolder holder, RequsetVideoHeadBean.Data data, final int position) {
            TextView tv_name = holder.getView(R.id.tv_name);
            holder.setText(R.id.tv_name, data.getName());
            if (currentTypes[pos] == position) {
                tv_name.setTextColor(getResources().getColor(R.color.color_dl_yellow));
                tv_name.setBackgroundResource(R.drawable.text_bg_gery);
            } else {
                tv_name.setTextColor(getResources().getColor(R.color.color_dl_deep_blue));
                tv_name.setBackground(null);
            }
            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCurrentPage = 0;
                    if (pos == 0) {
                        currentTypes[pos] = position;
                        currentTypes[1] = 0;
                        currentTypes[2] = 0;
                        currentTypes[3] = 0;
                        currentTypes[4] = 0;
                    }
                    if (pos == 1) {
                        currentTypes[pos] = position;
                        currentTypes[2] = 0;
                        currentTypes[3] = 0;
                        currentTypes[4] = 0;
                    }
                    if (pos == 2) {
                        currentTypes[pos] = position;
                        currentTypes[3] = 0;
                        currentTypes[4] = 0;
                    }
                    if (pos == 3) {
                        currentTypes[pos] = position;
                    }
                    if (pos == 4) {
                        currentTypes[pos] = position;
                    }
                    videohead_datas = priseData(videoHeadBean.getData());
                    myListView.setAdapter(new MylistviewAatapter(VideoListActivity.this, R.layout.listview_item_videoheader, videohead_datas));

                    notifyDataSetChanged();
                    getListData();
                }
            });
        }
    }


    class MylistviewAatapter extends com.cn.danceland.myapplication.adapter.listview.CommonAdapter<List<RequsetVideoHeadBean.Data>> {
        private RequsetVideoHeadBean.Data data1;

        public MylistviewAatapter(Context context, int layoutId, List<List<RequsetVideoHeadBean.Data>> datas) {

            super(context, layoutId, datas);
            data1 = new RequsetVideoHeadBean.Data();
            data1.setName("全部");
        }

        @Override
        protected void convert(com.cn.danceland.myapplication.adapter.listview.ViewHolder viewHolder, final List<RequsetVideoHeadBean.Data> item, final int position) {
            RecyclerView recyclerView = viewHolder.getView(R.id.recyclerView);
            //创建默认的线性LayoutManager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoListActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

            recyclerView.setLayoutManager(linearLayoutManager);
            //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
            recyclerView.setHasFixedSize(true);

            List<RequsetVideoHeadBean.Data> dataList = item;

            if (dataList.size() == 0) {
                dataList.add(0, data1);
            }
            if (!dataList.get(0).getName().equals("全部")) {
                dataList.add(0, data1);
            }


            recyclerView.setAdapter(new MyAdapter1(VideoListActivity.this, R.layout.item_text, dataList, position));
        }


    }

    private void getHeaderData() {

        MyStringRequest myStringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERYVIDEOTYPE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                videoHeadBean = new Gson().fromJson(s, RequsetVideoHeadBean.class);

                videohead_datas = priseData(videoHeadBean.getData());
                myListView.setAdapter(new MylistviewAatapter(VideoListActivity.this, R.layout.listview_item_videoheader, videohead_datas));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MyApplication.getHttpQueues().add(myStringRequest);
    }

    private void getListData() {

        MyStringRequest myStringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERYVIDEO), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestVideoListBean requestVideoListBean = new Gson().fromJson(s, RequestVideoListBean.class);
                datas = requestVideoListBean.getData().getContent();

                if (mCurrentPage == 0) {
                    getListAdapter().setDatas(datas);
                } else {
                    getListAdapter().addDatas(datas);
                }
                mCurrentPage = mCurrentPage + 1;
                getListAdapter().notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("size", "20");
                map.put("page", mCurrentPage + "");
                if (currentTypes[0] == 0) {
                    map.put("type", "0");
                } else {
                    map.put("type", videohead_datas.get(0).get(currentTypes[0] - 1).getId() + "");
                }
                if (currentTypes[1] == 0) {
                    map.put("type2", "0");
                } else {
                    map.put("type2", videohead_datas.get(1).get(currentTypes[1] - 1).getId() + "");
                }
                if (currentTypes[2] == 0) {
                    map.put("type3", "0");
                } else {
                    map.put("type3", videohead_datas.get(2).get(currentTypes[2] - 1).getId() + "");
                }
                if (currentTypes[3] == 0) {
                    map.put("type4", "0");
                } else {
                    map.put("type4", videohead_datas.get(3).get(currentTypes[3] - 1).getId() + "");
                }
                if (currentTypes[4] == 0) {
                    map.put("type5", "0");
                } else {
                    map.put("type5", videohead_datas.get(4).get(currentTypes[4] - 1).getId() + "");
                }


                return map;
            }
        };
        MyApplication.getHttpQueues().add(myStringRequest);
    }

    private List<List<RequsetVideoHeadBean.Data>> priseData(List<RequsetVideoHeadBean.Data> data) {
        List<List<RequsetVideoHeadBean.Data>> datas = new ArrayList<>();
        List<RequsetVideoHeadBean.Data> datachild1 = new ArrayList<>();
        List<RequsetVideoHeadBean.Data> datachild2 = new ArrayList<>();
        List<RequsetVideoHeadBean.Data> datachild3 = new ArrayList<>();
        List<RequsetVideoHeadBean.Data> datachild4 = new ArrayList<>();
        List<RequsetVideoHeadBean.Data> datachild5 = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getType() == 1) {
                datachild1.add(data.get(i));
            }

            if (data.get(i).getType() == 2) {
                datachild2.add(data.get(i));
            }

            if (data.get(i).getType() == 3) {
                datachild3.add(data.get(i));
            }


            if (data.get(i).getType() == 4) {
                datachild4.add(data.get(i));
            }
            if (data.get(i).getType() == 5) {
                datachild5.add(data.get(i));
            }
        }

        if (currentTypes[0] != 0) {
            datachild2 = new ArrayList<>();
            datachild3 = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {


                if (data.get(i).getType() == 2 && data.get(i).getPid() == datachild1.get(currentTypes[0] - 1).getId()) {//根据父类id判断
                    datachild2.add(data.get(i));
                }


            }
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < datachild2.size(); j++) {
                    if (data.get(i).getType() == 3 && data.get(i).getPid() == datachild2.get(j).getId()) {
                        datachild3.add(data.get(i));
                    }

                }

            }


        }

        if (currentTypes[1] != 0) {
            datachild3 = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {

                if (data.get(i).getType() == 3 && datachild2.size() > currentTypes[1] - 1 && data.get(i).getPid() == datachild2.get(currentTypes[1] - 1).getId()) {
                    datachild3.add(data.get(i));
                }

            }

        }


        datas.add(datachild1);
        datas.add(datachild2);
        datas.add(datachild3);
        datas.add(datachild4);
        datas.add(datachild5);


        return datas;

    }



}
