package com.cn.danceland.myapplication.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.bean.RequstRecommendBean;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shy on 2018/3/14 10:39
 * Email:644563767@qq.com
 * 推荐我的人
 */


public class RecommendedFragment extends BaseFragment {
    private List<RequstRecommendBean.Data> dataList = new ArrayList<>();
    private Myadapter myadapter = new Myadapter();
    private StrBean strBean1;
    private ImageView iv_error;
    private TextView tv_error;

    @Override
    public View initViews() {
        View v = View.inflate(mActivity, R.layout.fragment_recommended, null);

        ListView listView = v.findViewById(R.id.listview);
        View listEmptyView = v.findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        iv_error = listEmptyView.findViewById(R.id.iv_error);
        iv_error.setImageResource(R.drawable.img_error);
        tv_error.setText("还没有朋友推荐您");
        listView.setEmptyView(listEmptyView);

        listView.setAdapter(myadapter);

        return v;
    }

    @Override
    public void initData() {
        strBean1 = new StrBean();
        Data data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        strBean1.introduce_member_no = data.getPerson().getMember_no();
        introduce_querylist(strBean1);
    }

    @Override
    public void onClick(View view) {

    }

    class StrBean {
        Integer gender;
        String name;
        String phone_no;
        String introduce_member_no;

    }

    Gson gson = new Gson();


    public void introduce_confirm(final String id) {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.INTRODUCE_CONFIRM), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                RequestSimpleBean simpleBean = gson.fromJson(s.toString(), RequestSimpleBean.class);
                if (simpleBean.getSuccess()) {
                    ToastUtils.showToastShort("确认推荐成功");
                    initData();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", id);
                LogUtil.i(id);
                return map;
            }


        };

        MyApplication.getHttpQueues().add(stringRequest);

    }


    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = View.inflate(mActivity, R.layout.listview_item_recommended, null);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_phone = view.findViewById(R.id.tv_phone);
            TextView tv_time = view.findViewById(R.id.tv_time);
            TextView tv_status = view.findViewById(R.id.tv_status);
            ImageView iv_sex = view.findViewById(R.id.iv_sex);
            tv_name.setText(dataList.get(i).getMember_name());
            tv_phone.setText("联系电话:"+dataList.get(i).getMember_phone());
            tv_time.setText("推荐时间:"+TimeUtils.timeStamp2Date(dataList.get(i).getCreate_date() + "", new String("yyyy.MM.dd")));
            if (dataList.get(i).getGender() == 1) {

                iv_sex.setImageResource(R.drawable.img_sex1);
            } else {

                iv_sex.setImageResource(R.drawable.img_sex2);
            }


            if (dataList.get(i).getStatus() == 0) {
                tv_status.setText("确认");
                tv_status.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_sell_card));
                tv_status.setTextColor(getResources().getColor(R.color.white));
                tv_status.setEnabled(true);
                tv_status.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        introduce_confirm(dataList.get(i).getId());
                    }
                });
            } else if (dataList.get(i).getStatus() == 1) {
                tv_status.setText("已确认");
                tv_status.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey1));
                tv_status.setTextColor(getResources().getColor(R.color.color_dl_deep_blue));
                tv_status.setEnabled(false);
            } else if (dataList.get(i).getStatus() == 2) {
                tv_status.setText("确认");
                tv_status.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey1));
                tv_status.setTextColor(getResources().getColor(R.color.color_dl_deep_blue));
                tv_status.setEnabled(false);
            }


            final Data data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);

            return view;
        }
    }

    /**
     * 查找已推荐好友
     *
     * @param strBean
     */
    public void introduce_querylist(final StrBean strBean) {

//        StrBean strBean = new StrBean();
//        strBean.page = pageCount - 1 + "";
//        strBean.member_id = id;
//        String s = gson.toJson(strBean);

//        JSONObject jsonObject = new JSONObject(s.toString());
        LogUtil.i(gson.toJson(strBean));
        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.INTRODUCE_QUERYLIST), gson.toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequstRecommendBean requstRecommendBean = gson.fromJson(jsonObject.toString(), RequstRecommendBean.class);
                dataList = requstRecommendBean.getData();
                myadapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                // ToastUtils.showToastShort(volleyError.toString());
                iv_error.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        }) ;
        MyApplication.getHttpQueues().add(stringRequest);
    }
}
