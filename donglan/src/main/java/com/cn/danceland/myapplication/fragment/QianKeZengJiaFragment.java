package com.cn.danceland.myapplication.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.adapter.recyclerview.CommonAdapter;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ViewHolder;
import com.cn.danceland.myapplication.bean.HuiJiYeWuBean;
import com.cn.danceland.myapplication.bean.RequestQianKeZengJiaBean;
import com.cn.danceland.myapplication.fragment.base.BaseRecyclerViewRefreshFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shy on 2018/12/5 10:37
 * Email:644563767@qq.com
 * 潜客增加
 */


public class QianKeZengJiaFragment extends BaseRecyclerViewRefreshFragment {

    private List<RequestQianKeZengJiaBean.Data.Content> dataList = new ArrayList<>();
    private MylistAtapter mylistAtapter;
    private int mCurrentPage = 0;
    private String mCurrentDate = null;
    private String id;
    HuiJiYeWuBean.Data data;
    private boolean isjiaolian;
    private int type=0;
//    @Override
//    public void onEventMainThread(StringEvent event) {
//        switch (event.getEventCode()) {
//
//
//            case 7100://刷新页面
//                if (event.getMsg() != null) {
//                    mCurrentDate=event.getMsg();
//                    findhjyj(event.getMsg(), event.getMsg());
//                }
//
//            default:
//                break;
//        }
//    }


    @Override
    public View initViews() {

        mCurrentDate = getArguments().getString("date");
        id=getArguments().getString("id");
        isjiaolian=getArguments().getBoolean("isjiaolian");
        data= (HuiJiYeWuBean.Data) getArguments().getSerializable("data");
        type=getArguments().getInt("type");
        return super.initViews();
    }

    @Override
    public CommonAdapter setAtapter() {
        mylistAtapter = new MylistAtapter(mActivity, R.layout.listview_item_xzhj, dataList);
//        EmptyWrapper mEmptyWrapper = new EmptyWrapper(mylistAtapter);
//        mEmptyWrapper.setEmptyView(R.layout.no_info_layout);
        mylistAtapter.setEmptyView(R.layout.no_info_layout);
       View view= View.inflate(mActivity,R.layout.headview_jinriyewu,null);
             TextView tv_name =view.findViewById(R.id.tv_name);
             TextView tv_sum =view.findViewById(R.id.tv_sum);
             TextView tv_yewu1 =view.findViewById(R.id.tv_yewu1);
             TextView tv_yewu2 =view.findViewById(R.id.tv_yewu2);
             TextView tv_yewu3 =view.findViewById(R.id.tv_yewu3);
            ImageView iv_avatar =view.findViewById(R.id.iv_avatar);
        tv_name.setText( data.getEmp_name());
        if (isjiaolian){
            tv_sum.setText("总业务：" + data.getAll() + "个");
            tv_yewu1.setText("体测：" + data.getTice());
            tv_yewu2.setText( "体测分析：" + data.getTicefenxi());
            tv_yewu3.setText("健身计划：" + data.getJihua());
        }else {
            tv_sum.setText("总业务：" + data.getTotal() + "个");
            tv_yewu1.setText("潜增：" + data.getNewGuest());
            tv_yewu2.setText( "潜访：" + data.getVisitGuest());
            tv_yewu3.setText("会访：" + data.getVisitMember());
        }

        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(mActivity, 10));

        Glide.with(mActivity).load(data.getAvatar_url()).apply(options).into(iv_avatar);





        mylistAtapter.addHeaderView(view);
        mylistAtapter.addFootView(View.inflate(mActivity, R.layout.empty_8dp_layout, null));
        return mylistAtapter;
    }


    @Override
    public void initDownRefreshData() {


        mCurrentPage = 0;
//        if (mCurrentDate == null) {
//            mCurrentDate = TimeUtils.timeStamp2Date(System.currentTimeMillis() + "", "yyyy-MM-dd");
//        }
        findhjyj(mCurrentDate, mCurrentDate, id);
        //    setOnlyDownReresh();
    }

    @Override
    public void upDownRefreshData() {


    }


    private void findhjyj(final String start, final String end, final String employee_id) {
        String URL="";
        if (isjiaolian){
            switch(type){
                case 3:
                    URL=Constants.plus(Constants.QUERY_TICEMINGXI);
                    break;

                case 4:
                    URL=Constants.plus(Constants.QUERY_TICEFENXIMINGXI);
                    break;
                case 5:
                    break;
                default:
                    break;
            }
        }else {
            URL=Constants.plus(Constants.QUERY_XINQIANKE);
        }

        MyStringRequest request = new MyStringRequest(Request.Method.POST, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestQianKeZengJiaBean huiJiYeWuBean = new Gson().fromJson(s, RequestQianKeZengJiaBean.class);
                dataList = huiJiYeWuBean.getData().getContent();
                if (mCurrentPage == 0) {
                    getListAdapter().setDatas(dataList);

                } else {
                    getListAdapter().addDatas(dataList);
                }
                if (huiJiYeWuBean.getData().getLast()) {
                    setOnlyDownReresh();
                }

                getListAdapter().notifyDataSetChanged();


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
                map.put("employee_id", employee_id);
                map.put("end", end);
                map.put("page", mCurrentPage + "");
                map.put("size", 10+"");
                return map;
            }
        };
        if (isjiaolian&&type==5){

        }else {
            MyApplication.getHttpQueues().add(request);
        }

    }

    class MylistAtapter extends CommonAdapter<RequestQianKeZengJiaBean.Data.Content> {


        public MylistAtapter(Context context, int layoutId, List<RequestQianKeZengJiaBean.Data.Content> datas) {
            super(context, layoutId, datas);
        }


        @Override
        public void convert(ViewHolder viewHolder, RequestQianKeZengJiaBean.Data.Content data, int position) {
            viewHolder.setText(R.id.tv_name, data.getMember_name());
            if (isjiaolian){
                if (data.getTest_time() != null) {
                    viewHolder.setText(R.id.tv_lasttime, TimeUtils.timeStamp2Date(data.getTest_time(),"yyyy.MM.dd HH:mm:ss"));
                } else {
                    viewHolder.setText(R.id.tv_lasttime,"最近未维护");
                }
            }else {
                if (data.getCount_date() != null) {
                    viewHolder.setText(R.id.tv_lasttime, TimeUtils.timeStamp2Date(data.getCount_date(),"yyyy.MM.dd HH:mm:ss"));
                } else {
                    viewHolder.setText(R.id.tv_lasttime,"最近未维护");
                }
            }


            RequestOptions options = new RequestOptions()
                    .transform(new GlideRoundTransform(mActivity, 10))
                    .error(R.drawable.img_avatar1);

            Glide.with(mActivity).load(data.getAvatar_url()).apply(options).into((ImageView) viewHolder.getView(R.id.iv_avatar));



        }


    }
}
