package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseRecyclerViewRefreshActivity;
import com.cn.danceland.myapplication.adapter.recyclerview.CommonAdapter;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ViewHolder;
import com.cn.danceland.myapplication.bean.TextPushListBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门店 文字推送列表
 * Created by yxx on 2018-12-03.
 */

public class TextPushListActivity extends BaseRecyclerViewRefreshActivity {
    private Context context;
    private List<TextPushListBean.Data.Content> dataList = new ArrayList<>();

    private int mCurrentPage = 0;//当前请求页
    private int from;
    private TextPushListBean datainfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (this.getIntent() != null) {
            from = this.getIntent().getIntExtra("from", 0);
        }
        setTitle("推送记录");
    }

    /**
     * 查询数据
     *
     * @param pageCount
     * @throws JSONException
     */
    public void find_all_data(final int pageCount) throws JSONException {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_TEXT_PUSH_LIST), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s.toString());
                datainfo = new Gson().fromJson(s.toString(), TextPushListBean.class);
                if (datainfo.getSuccess()) {
                    if (pageCount == 0) {
                        dataList = datainfo.getData().getContent();
                    } else {
                        dataList.addAll(datainfo.getData().getContent());
                    }
                    getListAdapter().setDatas(dataList);
                    getListAdapter().notifyDataSetChanged();
                } else {
                    ToastUtils.showToastLong(datainfo.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("push_range", from + "");
                map.put("page", pageCount + "");
                map.put("size", 10 + "");
                LogUtil.i(map.toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    @Override
    public CommonAdapter setAtapter() {
        MotionDataAdapter adapter = new MotionDataAdapter(this, R.layout.item_text_push, dataList);
        adapter.setEmptyView(R.layout.no_info_layout);
        return adapter;
    }

    @Override
    public void initDownRefreshData() {
        mCurrentPage = 0;
        dataList = new ArrayList<>();
        try {
            find_all_data(mCurrentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upDownRefreshData() {
        if ((mCurrentPage + 1) >= datainfo.getData().getTotalPages()) {
            setOnlyDownReresh();
        } else {
            mCurrentPage = +1;
            try {
                find_all_data(mCurrentPage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class MotionDataAdapter extends CommonAdapter<TextPushListBean.Data.Content> {

        public MotionDataAdapter(Context context, int layoutId, List<TextPushListBean.Data.Content> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, TextPushListBean.Data.Content data, int position) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 11f));
            } else if (position == getDatas().size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 5f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 5f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 11f));
            }
            switch (from) { //推送范围(100:店长推送;101:会籍推送;102:教练推送;103:服务部推送)
                case 100:
                    viewHolder.setImageResource(R.id.icon_iv, R.drawable.text_push_whole_shop_icon);
                    break;
                case 101:
                    viewHolder.setImageResource(R.id.icon_iv, R.drawable.text_push_membership_icon);
                    break;
                case 102:
                    viewHolder.setImageResource(R.id.icon_iv, R.drawable.text_push_coach_icon);
                    break;
                case 103:
                    viewHolder.setImageResource(R.id.icon_iv, R.drawable.text_push_service_icon);
                    break;
            }
            viewHolder.setLayoutParams(R.id.item_layout_cv, layoutParams);
            viewHolder.setText(R.id.tv_time, TimeUtils.timeStamp2Date(TimeUtils.date2TimeStamp(data.getPush_time(), "yyyy-MM-dd HH:mm:ss").toString(), "yyyy.MM.dd"));
            viewHolder.setText(R.id.tv_title, data.getTitle());
            viewHolder.setText(R.id.tv_content, data.getContent());

            viewHolder.setOnClickListener(R.id.item_layout, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
