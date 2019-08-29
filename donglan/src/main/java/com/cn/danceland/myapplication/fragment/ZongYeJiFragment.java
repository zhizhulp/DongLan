//package com.cn.danceland.myapplication.fragment;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ListAdapter;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
//import com.cn.danceland.myapplication.MyApplication;
//import com.cn.danceland.myapplication.R;
//import com.cn.danceland.myapplication.adapter.listview.CommonAdapter;
//import com.cn.danceland.myapplication.adapter.listview.ViewHolder;
//import com.cn.danceland.myapplication.bean.HuiJiYeJiBean;
//import com.cn.danceland.myapplication.fragment.base.BaseListViewRefreshFragment;
//import com.cn.danceland.myapplication.utils.Constants;
//import com.cn.danceland.myapplication.utils.GlideRoundTransform;
//import com.cn.danceland.myapplication.utils.LogUtil;
//import com.cn.danceland.myapplication.utils.MyStringRequest;
//import com.google.gson.Gson;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * Created by shy on 2018/12/5 10:37
// * Email:644563767@qq.com
// * <p>
// * 总业绩
// */
//
//
//public class ZongYeJiFragment extends BaseListViewRefreshFragment {
//
//    private List<HuiJiYeJiBean.Data> dataList = new ArrayList<>();
//    private MylistAtapter mylistAtapter= new MylistAtapter(mActivity, R.layout.listview_item_jinriyeji, dataList);
//
//
//    @Override
//    public ListAdapter setAtapter() {
//      //  mylistAtapter = new MylistAtapter(mActivity, R.layout.listview_item_jinriyeji, dataList);
//
//        return mylistAtapter;
//    }
//
//    @Override
//    public void setListAdapter(ListAdapter listAdapter) {
//        super.setListAdapter(listAdapter);
//
//    }
//
//    @Override
//    public View initViews() {
//        return super.initViews();
//
//    }
//
//    @Override
//    public void initRereshData() {
//
//        getPullToRefresh().setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        findhjyj();
//    }
//
//
//    private void findhjyj() {
//        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.QUERY_HUIJI, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                LogUtil.i(s);
//                HuiJiYeJiBean huiJiYeJiBean = new Gson().fromJson(s, HuiJiYeJiBean.class);
//                dataList = huiJiYeJiBean.getData();
//                   mylistAtapter = new MylistAtapter(mActivity, R.layout.listview_item_jinriyeji,dataList);
//                setListAdapter(mylistAtapter);
//              //  mylistAtapter.setDatas(dataList);
//                //.notifyDataSetChanged();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                LogUtil.e(volleyError.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("start", "2017-01-01");
//                map.put("end", "2019-01-01");
//                return map;
//            }
//        };
//        MyApplication.getHttpQueues().add(request);
//    }
//
//    class MylistAtapter extends CommonAdapter<HuiJiYeJiBean.Data> {
//
//        private List<HuiJiYeJiBean.Data> datas;
//
////        private void setDatas(List<HuiJiYeJiBean.Data> datas) {
////            this.datas = datas;
////
////        }
//
//        public MylistAtapter(Context context, int layoutId, List<HuiJiYeJiBean.Data> datas) {
//            super(context, layoutId, datas);
//            this.datas = datas;
//        }
//
//        @Override
//        protected void convert(ViewHolder viewHolder, HuiJiYeJiBean.Data data, int i) {
//            viewHolder.setText(R.id.tv_name, data.getEmp_name());
//            viewHolder.setText(R.id.tv_sum, "总业绩："+data.getTotal()+"元");
//            viewHolder.setText(R.id.tv_yewu1, "办卡："+data.getNewcard());
//            viewHolder.setText(R.id.tv_yewu2, "租柜："+data.getLeaselocker());
//            RequestOptions options = new RequestOptions()
//                    .transform(new GlideRoundTransform(mActivity,10));
//
//            Glide.with(mActivity).load(data.getAvatar_url()).apply(options).into((ImageView) viewHolder.getView(R.id.iv_avatar));
//        }
//
//
//    }
//}
