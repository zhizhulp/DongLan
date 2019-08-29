package com.cn.danceland.myapplication.fragment;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.adapter.recyclerview.CommonAdapter;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ViewHolder;
import com.cn.danceland.myapplication.bean.CornerMarkMessageBean;
import com.cn.danceland.myapplication.bean.RequestNoticeListBean;
import com.cn.danceland.myapplication.evntbus.EventConstants;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.base.BaseRecyclerViewRefreshFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 我的-通知列表
 * Created by yxx on 2018-12-08.
 */

public class MessageFragment extends BaseRecyclerViewRefreshFragment {
    private int mCurrentPage = 0;//当前请求页
    private String type = "1";//(1=>发现,2=>日常,3=>系统)0所有
    private List<RequestNoticeListBean.Data.Content> datalist = new ArrayList<>();
    private RequestNoticeListBean datainfo;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public CommonAdapter setAtapter() {
        MessageAdapter adapter = new MessageAdapter(getActivity(), R.layout.notice_item, datalist);
        adapter.setEmptyView(R.layout.no_info_layout);
        return adapter;
    }

    @Override
    public void initDownRefreshData() {
        mCurrentPage = 0;
        datalist = new ArrayList<>();
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
            mCurrentPage +=1;
            try {
                find_all_data(mCurrentPage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 查询数据
     *
     * @param pageCount
     * @throws JSONException
     */
    public void find_all_data(final int pageCount) throws JSONException {

        StrBean strBean = new StrBean();
        strBean.page = pageCount + "";
        strBean.type = type+ "";
        String s = new Gson().toJson(strBean);
        LogUtil.i("gson-" + s);
        JSONObject jsonObject = new JSONObject(s.toString());

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_QUERY_PAGE), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                Gson gson = new Gson();
                datainfo = gson.fromJson(jsonObject.toString(), RequestNoticeListBean.class);
                if (datainfo.getSuccess()) {
                    if (pageCount == 0) {
                        datalist = datainfo.getData().getContent();
                    } else {
                        datalist.addAll(datainfo.getData().getContent());
                    }
                    getListAdapter().setDatas(datalist);
                    getListAdapter().notifyDataSetChanged();
                } else {
                    ToastUtils.showToastLong(datainfo.getErrorMsg());
                }
//                if (datainfo.getSuccess()) {
//
//                    if ((mCurrentPage + 1) >= datainfo.getData().getTotalPages()) {
//                        isEnd = true;
//                        setEnd();
//                    } else {
//                        isEnd = false;
//                        init_pullToRefresh();
//                    }
//
//                    if (mCurrentPage == 0) {
//                        datalist = datainfo.getData().getContent();
//                        myListAatapter.notifyDataSetChanged();
//                    } else {
//                        datalist.addAll(datainfo.getData().getContent());
//                        myListAatapter.notifyDataSetChanged();
//                    }
//                    mCurrentPage = mCurrentPage + 1;
//                } else {
//                    ToastUtils.showToastLong(datainfo.getErrorMsg());
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }

    class StrBean {
        public String page;
        public String type;
    }

    class MessageAdapter extends CommonAdapter<RequestNoticeListBean.Data.Content> {

        public MessageAdapter(Context context, int layoutId, List<RequestNoticeListBean.Data.Content> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public void convert(ViewHolder viewHolder, final RequestNoticeListBean.Data.Content data, final int position) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(getActivity(), 80f));
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 11f));
            } else if (position == getDatas().size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 5f), DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 5f), DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 11f));
            }
            viewHolder.setLayoutParams(R.id.item_layout_cv, layoutParams);
            viewHolder.setText(R.id.tv_title, data.getTitle());
            viewHolder.setText(R.id.tv_content, data.getContent());
            viewHolder.setText(R.id.tv_time, data.getPush_date());
            if (data.getStatus().equals("0")) {
                viewHolder.setText(R.id.tv_status, "未读");
                viewHolder.setTextColor(R.id.tv_status, getResources().getColor(R.color.red));
            } else {
                viewHolder.setText(R.id.tv_status, "已读");
                viewHolder.setTextColor(R.id.tv_status, getResources().getColor(R.color.colorGray22));
            }
            viewHolder.setOnClickListener(R.id.item_layout, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_RECORD_SET_BADGE), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            LogUtil.i("Response--" + s);
                            CornerMarkMessageBean responseBean = new Gson().fromJson(s, CornerMarkMessageBean.class);
                            if (responseBean.getCode() != null && responseBean.getCode().equals("0")) {
                                String message_sum_str = SPUtils.getString(Constants.MY_APP_MESSAGE_SUM, "0");//应用消息总数 用于桌面icon显示
                                int message_sum = (Integer.valueOf(message_sum_str) - 1);
                                SPUtils.setString(Constants.MY_APP_MESSAGE_SUM, message_sum + "");//应用消息总数 用于桌面icon显示
                                ShortcutBadger.applyCount(getActivity(), message_sum); //for 1.1.4+
                                switch (type) {
                                    case "1":
                                        EventBus.getDefault().post(new StringEvent(0 + "", EventConstants.MY_MESSAGE_FOUND_NUM));
                                        break;
                                    case "2":
                                        EventBus.getDefault().post(new StringEvent(0 + "", EventConstants.MY_MESSAGE_DAILY_NUM));
                                        break;
                                    case "3":
                                        EventBus.getDefault().post(new StringEvent(0 + "", EventConstants.MY_MESSAGE_SYSTEM_NUM));
                                        break;
                                }
                            }
                            datalist.get(position).setStatus("1");
                            getListAdapter().setDatas(datalist);
                            getListAdapter().notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            if (volleyError != null) {
                                LogUtil.i(volleyError.toString());
                            } else {
                                LogUtil.i("NULL");
                            }
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("badge", SPUtils.getString(Constants.MY_APP_MESSAGE_SUM, ""));//应用消息总数 用于桌面icon显示);
                            map.put("recordId", data.getId());
                            LogUtil.i("finalMap--" + map.toString());
                            return map;
                        }
                    };
                    MyApplication.getHttpQueues().add(stringRequest);
                }
            });
        }
    }
}
