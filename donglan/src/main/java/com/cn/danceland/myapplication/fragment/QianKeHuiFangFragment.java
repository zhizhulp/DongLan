package com.cn.danceland.myapplication.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.adapter.recyclerview.CommonAdapter;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ViewHolder;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.HuiJiYeWuBean;
import com.cn.danceland.myapplication.bean.RequsetRevisiterRecordListBean;
import com.cn.danceland.myapplication.fragment.base.BaseRecyclerViewRefreshFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shy on 2018/12/5 10:37
 * Email:644563767@qq.com
 * 潜客回访
 */


public class QianKeHuiFangFragment extends BaseRecyclerViewRefreshFragment {

    private List<RequsetRevisiterRecordListBean.Data.Content> dataList = new ArrayList<>();
    private MylistAtapter mylistAtapter;
    private int mCurrentPage = 0;
    private String mCurrentDate = null;
    private String id,auth;
    HuiJiYeWuBean.Data data;
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
        id = getArguments().getString("id");
        auth = getArguments().getString("auth");
        data = (HuiJiYeWuBean.Data) getArguments().getSerializable("data");
        return super.initViews();
    }

    @Override
    public CommonAdapter setAtapter() {
        mylistAtapter = new MylistAtapter(mActivity, R.layout.listview_item_all_record_list, dataList);
//        EmptyWrapper mEmptyWrapper = new EmptyWrapper(mylistAtapter);
//        mEmptyWrapper.setEmptyView(R.layout.no_info_layout);
        mylistAtapter.setEmptyView(R.layout.no_info_layout);
        View view = View.inflate(mActivity, R.layout.headview_jinriyewu, null);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_sum = view.findViewById(R.id.tv_sum);
        TextView tv_yewu1 = view.findViewById(R.id.tv_yewu1);
        TextView tv_yewu2 = view.findViewById(R.id.tv_yewu2);
        TextView tv_yewu3 = view.findViewById(R.id.tv_yewu3);
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar);
        tv_name.setText(data.getEmp_name());
        tv_sum.setText("总业务：" + data.getTotal() + "个");
        tv_yewu1.setText("潜增：" + data.getNewGuest());
        tv_yewu2.setText("潜访：" + data.getVisitGuest());
        tv_yewu3.setText("会访：" + data.getVisitMember());
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
        maintain_start=TimeUtils.date2TimeStamp(mCurrentDate,"yyyy-MM-dd");
        maintain_end=maintain_start+24*60*60*1000;

        try {
            find_record_list(auth);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upDownRefreshData() {


    }

    class StrBean {
        public String page;
        public String auth;
        // public String member_id;
        public String operate_id;
        public String searchInfo;
        public String maintain_start;
        public String maintain_end;
    }

    private Gson gson = new Gson();
    private  long maintain_start;
    private  long maintain_end;

    /**
     * 查询回访记录
     */
    public void find_record_list(final String auth) throws JSONException {


        StrBean strBean = new StrBean();
        strBean.page = mCurrentPage + "";
        strBean.auth = auth;
        strBean.maintain_start=maintain_start+"";
        strBean.maintain_end=maintain_end+"";

        strBean.operate_id = ((Data) DataInfoCache.loadOneCache(Constants.MY_INFO)).getEmployee().getId() + "";
        String s = gson.toJson(strBean);
        LogUtil.i(s);
        JSONObject jsonObject = new JSONObject(s.toString());
        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_VISIT_RECORD), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequsetRevisiterRecordListBean potentialListBean = gson.fromJson(jsonObject.toString(), RequsetRevisiterRecordListBean.class);

                dataList = potentialListBean.getData().getContent();
                if (mCurrentPage == 0) {
                    getListAdapter().setDatas(dataList);

                } else {
                    getListAdapter().addDatas(dataList);
                }
                if (potentialListBean.getData().getLast()) {
                    setOnlyDownReresh();
                }

                getListAdapter().notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());

            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }



    class MylistAtapter extends CommonAdapter<RequsetRevisiterRecordListBean.Data.Content> {


        public MylistAtapter(Context context, int layoutId, List<RequsetRevisiterRecordListBean.Data.Content> datas) {
            super(context, layoutId, datas);
        }


        @Override
        public void convert(ViewHolder viewHolder, RequsetRevisiterRecordListBean.Data.Content data, int position) {
        viewHolder.setText(R.id.tv_name, data.getOperate_name());
        viewHolder.setText(R.id.tv_type, data.getType());
        viewHolder.setText(R.id.tv_result, "回访结果："+data.getResult());



            if (TextUtils.equals(data.getType(), "电话")) {
                viewHolder.setVisible(R.id.tv_time,true);
                if (data.getLength() > 59) {
                    viewHolder.setText(R.id.tv_time,data.getLength() / 60 + "分钟" + data.getLength() % 60 + "秒");
                } else {
                    viewHolder.setText(R.id.tv_time,data.getLength() % 60 + "秒");
                }

            } else {
                viewHolder.setVisible(R.id.tv_time,false);
            }
            viewHolder.setText(R.id.tv_content,data.getContent());
            viewHolder.setText(R.id.tv_lasttime,TimeUtils.timeStamp2Date(TimeUtils.date2TimeStamp(data.getMaintain_time(), "yyyy-MM-dd HH:mm:ss") + "", "yy.MM.dd HH:mm"));
            viewHolder.setText(R.id.tv_member_no,data.getMember_no());
            viewHolder.setText(R.id.tv_username,data.getMember_name());


        }


    }
}
