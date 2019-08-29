package com.cn.danceland.myapplication.fragment;

import android.content.Context;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.adapter.recyclerview.CommonAdapter;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ViewHolder;
import com.cn.danceland.myapplication.bean.RequestHuiJiYeWuBean;
import com.cn.danceland.myapplication.fragment.base.BaseRecyclerViewRefreshFragment;
import com.cn.danceland.myapplication.utils.Constants;
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
 * 个人业绩
 */


public class GeRenYeJiFragment extends BaseRecyclerViewRefreshFragment {

    private List<RequestHuiJiYeWuBean.Data.Content> dataList = new ArrayList<>();
    private MylistAtapter mylistAtapter;
    private int mCurrentPage = 0;
    private String mCurrentDate = null;
    Map<Integer,String> yewumap=new HashMap<>();
    private String id;
    private boolean isjiaolian;
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
        return super.initViews();
    }

    @Override
    public CommonAdapter setAtapter() {
        mylistAtapter = new MylistAtapter(mActivity, R.layout.listview_item_huiji_yewu, dataList);
//        EmptyWrapper mEmptyWrapper = new EmptyWrapper(mylistAtapter);
//        mEmptyWrapper.setEmptyView(R.layout.no_info_layout);
        mylistAtapter.setEmptyView(R.layout.no_info_layout);
        mylistAtapter.addHeaderView(View.inflate(mActivity, R.layout.empty_8dp_layout, null));
        mylistAtapter.addFootView(View.inflate(mActivity, R.layout.empty_8dp_layout, null));
        return mylistAtapter;
    }


    @Override
    public void initDownRefreshData() {

        yewumap.put(11,"买定金");
        yewumap.put(12,"用定金");
        yewumap.put(13,"退定金");
        yewumap.put(14,"充储值");
        yewumap.put(15,"退储值");
        yewumap.put(100,"花储值");
        yewumap.put(21,"买卡");
        yewumap.put(22,"卡升级");
        yewumap.put(23,"续卡");
        yewumap.put(24,"补卡");
        yewumap.put(25,"转卡");
        yewumap.put(26,"退卡");
        yewumap.put(27,"停卡");
        yewumap.put(28,"卡延期");
        yewumap.put(29,"卡挂失");
        yewumap.put(30,"卡加次");
        yewumap.put(41,"租柜");
        yewumap.put(42,"续柜");
        yewumap.put(43,"退柜");
        yewumap.put(44,"转柜");
        yewumap.put(45,"换柜");
        yewumap.put(51,"购买私教");
        yewumap.put(52,"私教转会员");
        yewumap.put(53,"私教换教练");
        yewumap.put(61,"退私教");

        yewumap.put(1,"定金业务");
        yewumap.put(2,"储值业务");
        yewumap.put(3,"卡业务");
        yewumap.put(4,"租柜业务");
        yewumap.put(5,"私教业务");

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
        String URL;
        if (isjiaolian){
            URL=  Constants.plus(Constants.QUERY_JIAOLIANYEJIMINGXI);
        }else {
            URL=   Constants.plus(Constants.QUERY_HUIJIYEJIMINGXI);
        }
        MyStringRequest request = new MyStringRequest(Request.Method.POST,URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestHuiJiYeWuBean huiJiYeWuBean = new Gson().fromJson(s, RequestHuiJiYeWuBean.class);
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
        MyApplication.getHttpQueues().add(request);
    }

    class MylistAtapter extends CommonAdapter<RequestHuiJiYeWuBean.Data.Content> {


        public MylistAtapter(Context context, int layoutId, List<RequestHuiJiYeWuBean.Data.Content> datas) {
            super(context, layoutId, datas);
        }


        @Override
        public void convert(ViewHolder viewHolder, RequestHuiJiYeWuBean.Data.Content data, int position) {
            if (isjiaolian){
                viewHolder.setText(R.id.tv_name, data.getEmp_name());
            }else {
                viewHolder.setText(R.id.tv_name, data.getEmployee_name());
            }

            viewHolder.setText(R.id.tv_admin_name, data.getMember_name());
            viewHolder.setText(R.id.tv_code, data.getCode());
            viewHolder.setText(R.id.tv_type, yewumap.get(data.getBig_type()) + "、" + yewumap.get(data.getBus_type()));
            if (isjiaolian){
                if (data.getCourse_type()==1){
                    viewHolder.setText(R.id.tv_type, yewumap.get(data.getBig_type()) + "、单人私教" );
                }else if (data.getCourse_type()==2){
                    viewHolder.setText(R.id.tv_type, yewumap.get(data.getBig_type()) + "、团体私教" );
                }

            }
            if (data.getBig_type()==3){
                viewHolder.setText(R.id.tv_kahao,getString(R.string.cardnumber));
            }
            if (data.getBig_type()==4){
                viewHolder.setText(R.id.tv_kahao, getString(R.string.boxnmumber));
            }
            if (isjiaolian){
                viewHolder.setVisible(R.id.ll_code,false);
            }else {
                viewHolder.setVisible(R.id.ll_code,true);
            }
//            RequestOptions options = new RequestOptions()
//                    .transform(new GlideRoundTransform(mActivity, 10));
//
//            Glide.with(mActivity).load(data.get).apply(options).into((ImageView) viewHolder.getView(R.id.iv_avatar));
            viewHolder.setText(R.id.tv_money,"¥" + data.getMoney());
            viewHolder.setText(R.id.tv_lasttime,TimeUtils.timeStamp2Date(data.getOrder_time() + "", "yyyy.MM.dd HH:mm"));


        }


    }
}
