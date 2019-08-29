package com.cn.danceland.myapplication.fragment;

import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.ForOtherListBean;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feng on 2018/4/18.
 */

public class ForOtherSiJiaoFragment extends BaseFragment {

    ListView lv_forother;
    Data personInfo;
    Gson gson;
    List<ForOtherListBean.Data> dataList;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;
    @Override
    public View initViews() {
        View inflate = View.inflate(mActivity, R.layout.fragment_forother, null);

        lv_forother = inflate.findViewById(R.id.lv_forother);
        rl_error = inflate.findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(mActivity).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("您还没有给他人送出私教");

        lv_forother.setEmptyView(rl_error);
        personInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);

        gson = new Gson();

      //  initData();
        return inflate;
    }
    @Override
    public void initData() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FOROTHERSIJIAOLIST), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ForOtherListBean forOtherListBean = gson.fromJson(s, ForOtherListBean.class);
                if(forOtherListBean!=null){
                    dataList = forOtherListBean.getData();
                    if(dataList!=null){
                        lv_forother.setAdapter(new OtherListAdapter());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                rl_error.setVisibility(View.VISIBLE);
                tv_error.setText("网络异常");
                Glide.with(mActivity).load(R.drawable.img_error7).into(iv_error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("memberId",personInfo.getMember().getId());
                return map;
            }


        };

        MyApplication.getHttpQueues().add(stringRequest);
    }

    @Override
    public void onClick(View v) {

    }

    private class OtherListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return dataList.size();
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

            View inflate = View.inflate(mActivity, R.layout.forother_item, null);
            TextView tv_sijiaoName = inflate.findViewById(R.id.tv_sijiaoName);
            TextView tv_jiaolian_name = inflate.findViewById(R.id.tv_jiaolian_name);
            TextView tv_goumai_num = inflate.findViewById(R.id.tv_goumai_num);
            TextView tv_shengyu_num = inflate.findViewById(R.id.tv_shengyu_num);
            TextView tv_huiyuan_name = inflate.findViewById(R.id.tv_huiyuan_name);
            TextView tv_youxiaoqi = inflate.findViewById(R.id.tv_youxiaoqi);
            TextView tv_category = inflate.findViewById(R.id.tv_category);

            tv_sijiaoName.setText(dataList.get(position).getCourse_type_name());
            tv_jiaolian_name.setText("上课教练: "+dataList.get(position).getEmployee_name());
            tv_goumai_num.setText(Html.fromHtml("购买节数: "+"<font color='#6D819C'>"+dataList.get(position).getCount()+"节</font>"));
            tv_shengyu_num.setText(Html.fromHtml("剩余节数: "+"<font color='#6D819C'>"+dataList.get(position).getSurplus_count()+"节</font>"));
            tv_huiyuan_name.setText("会员: "+dataList.get(position).getMember_name());

            String start_date = TimeUtils.timeStamp2Date(dataList.get(position).getStart_date(), "yyyy-MM-dd");
            String end_date = TimeUtils.timeStamp2Date(dataList.get(position).getEnd_date(), "yyyy-MM-dd");
            tv_youxiaoqi.setText((Html.fromHtml("有效期: "+"<font color='#6D819C'>"+dataList.get(position).getTime_length()+"天"+"</font>")));
            if(dataList.get(position).getCourse_category()==1){
                tv_category.setText("单人私教");
            }else if(dataList.get(position).getCourse_category()==2){
                tv_category.setText("小团课");
            }



            return inflate;
        }
    }

}
