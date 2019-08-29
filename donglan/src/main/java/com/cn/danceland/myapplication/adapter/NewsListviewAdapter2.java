package com.cn.danceland.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.NewsDetailsActivity;
import com.cn.danceland.myapplication.bean.RequestCollectBean;
import com.cn.danceland.myapplication.bean.RequestNewsDataBean;
import com.cn.danceland.myapplication.bean.ShareBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shy on 2017/11/21 09:36
 * Email:644563767@qq.com
 */


public class NewsListviewAdapter2 extends BaseAdapter {

    private List<RequestNewsDataBean.Data.Items> data = new ArrayList<RequestNewsDataBean.Data.Items>();
    private LayoutInflater mInflater;
    private Context context;

    public NewsListviewAdapter2(List<RequestNewsDataBean.Data.Items> data, Context context) {
        this.data = data;
        this.context = context;

        mInflater = LayoutInflater.from(context);

    }

    public void addFirst(RequestNewsDataBean.Data.Items bean) {
        data.add(0, bean);
    }

    public void setData(List<RequestNewsDataBean.Data.Items> data) {
        this.data = data;
        //LogUtil.i(data.toString());
    }

    //增加数据
    public void addLastList(List<RequestNewsDataBean.Data.Items> bean) {
        //   LogUtil.i(data.toString());
        data.addAll(bean);
        // LogUtil.i(data.toString());
    }


    @Override
    public int getCount() {
        //   LogUtil.i(data.size()+"");
        return data.size();

        //

    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        //    LogUtil.i("getView"+position);
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.listview_item_homepage, null);
            viewHolder.iv_image = view.findViewById(R.id.iv_image);
            viewHolder.tv_title = view.findViewById(R.id.tv_tiltle);
            viewHolder.tv_time = view.findViewById(R.id.tv_time);
            viewHolder.tv_content = view.findViewById(R.id.tv_content);
            viewHolder.ll_item = view.findViewById(R.id.ll_item);
            viewHolder.collect_iv = view.findViewById(R.id.collect_iv);
            viewHolder.read_number_tv = view.findViewById(R.id.read_number_tv);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //  RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
        Glide.with(context)
                .load(data.get(position).getImg_url())
                //  .apply(options)
                .into(viewHolder.iv_image);
        viewHolder.tv_title.setText(data.get(position).getTitle());
        viewHolder.tv_time.setText(TimeUtils.timeStamp2Date(TimeUtils.date2TimeStamp(data.get(position).getPublish_time(), "yyyy-MM-dd HH:mm:ss").toString(), "yyyy.MM.dd"));
        viewHolder.tv_content.setText(data.get(position).getNews_txt());
        viewHolder.read_number_tv.setText(data.get(position).getRead_number());

        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReadNum(data.get(position).getId()+"");
                ShareBean shareBean=new ShareBean();
                shareBean.bus_id=data.get(position).getId()+"";
                shareBean.img_url=data.get(position).getImg_url();
                shareBean.title=data.get(position).getTitle();
                shareBean.url=data.get(position).getUrl();
                shareBean.type=3;//首页新闻
                context.startActivity(new Intent(context, NewsDetailsActivity.class)
                        .putExtra("url", data.get(position).getUrl())
                        .putExtra("shareBean", shareBean)
                        .putExtra("title", data.get(position).getTitle())
                        .putExtra("img_url", data.get(position).getImg_url()));

            }
        });
        if(data.get(position).is_collect()){
            viewHolder.collect_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.home_item_on_collect_icon));
        }else{
            viewHolder.collect_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.home_item_collect_icon));
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.collect_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_COLLECT_SAVE), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        LogUtil.i(s);

                        Gson gson = new Gson();
                        RequestCollectBean requestInfoBean = gson.fromJson(s, RequestCollectBean.class);
                        if (requestInfoBean.getSuccess() && requestInfoBean.getCode() == 0) {
                            data.get(position).setIs_collect(!data.get(position).is_collect());
                            if(data.get(position).is_collect()){
                                finalViewHolder.collect_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.home_item_on_collect_icon));
                            }else{
                                finalViewHolder.collect_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.home_item_collect_icon));
                            }
                        } else {
                            //失败
                            ToastUtils.showToastShort("请求失败，请查看网络连接");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showToastShort("请求失败，请查看网络连接");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("news_id", data.get(position).getId());
                        map.put("type", "0");
                        map.put("is_collect", String.valueOf(!data.get(position).is_collect()));
                        LogUtil.i("map--" + map.toString());
                        return map;
                    }
                };

                // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
                request.setTag("userRegister");
                // 设置超时时间
                request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                // 将请求加入全局队列中
                MyApplication.getHttpQueues().add(request);
            }
        });
        return view;
    }

    private void setReadNum(final String news_id) {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_READ_NUMBER), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("请求失败，请查看网络连接");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", news_id);
                LogUtil.i("map--" + map.toString());
                return map;
            }
        };
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }

    class ViewHolder {
        TextView tv_title;
        ImageView iv_image;//头像
        TextView tv_time;//时间
        TextView tv_content;//内容
        ImageView collect_iv;//收藏
        TextView read_number_tv;//阅读数

        LinearLayout ll_item;
    }


}
