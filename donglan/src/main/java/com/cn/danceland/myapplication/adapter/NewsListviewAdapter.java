package com.cn.danceland.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.RequestCollectBean;
import com.cn.danceland.myapplication.bean.RequestNewsDataBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DensityUtils;
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
 * 热门话题adapter
 * Created by yxx on 2018/11/15.
 */
public class NewsListviewAdapter extends RecyclerView.Adapter<NewsListviewAdapter.ViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private List<RequestNewsDataBean.Data.Items> data = new ArrayList<RequestNewsDataBean.Data.Items>();
    private LayoutInflater mInflater;
    private Context context;

    private View mHeaderView;

    private OnItemClickListener mListener;

    public NewsListviewAdapter(List<RequestNewsDataBean.Data.Items> data, Context context) {
        this.data = data;
        this.context = context;

        mInflater = LayoutInflater.from(context);

    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, List<RequestNewsDataBean.Data.Items> data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new ViewHolder(mHeaderView);

//        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_homepage, parent, false);
//        return new Holder(layout);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_homepage, parent, false);

        NewsListviewAdapter.ViewHolder vh = new NewsListviewAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.loading_img);
        Glide.with(context)
                .load(data.get(pos).getImg_url())
                  .apply(options)
                .into(holder.iv_image);
        holder.tv_title.setText(data.get(pos).getTitle());
        holder.tv_time.setText(TimeUtils.timeStamp2Date(TimeUtils.date2TimeStamp(data.get(pos).getPublish_time(), "yyyy-MM-dd HH:mm:ss").toString(), "yyyy.MM.dd"));
        holder.tv_content.setText(data.get(pos).getNews_txt());
        holder.read_number_tv.setText(data.get(pos).getRead_number());

        if (data.get(pos).is_collect()) {
            holder.collect_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.home_item_on_collect_icon));
        } else {
            holder.collect_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.home_item_collect_icon));
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 100f));
        if (position == 0) {
            layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 0f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 11f));
        } else if (position == data.size() - 1) {
            layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 5f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 16f));
        } else {
            layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 5f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 11f));
        }
        holder.item_layout_cv.setLayoutParams(layoutParams);

        if (mListener == null) return;
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(pos, data);
            }
        });
        holder.collect_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_COLLECT_SAVE), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        LogUtil.i(s);

                        Gson gson = new Gson();
                        RequestCollectBean requestInfoBean = gson.fromJson(s, RequestCollectBean.class);
                        if (requestInfoBean.getSuccess() && requestInfoBean.getCode() == 0) {
                            data.get(pos).setIs_collect(!data.get(pos).is_collect());
                            if (data.get(pos).is_collect()) {
                                ToastUtils.showToastShort("收藏成功");
                                holder.collect_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.home_item_on_collect_icon));
                            } else {
                                ToastUtils.showToastShort("取消收藏成功");
                                holder.collect_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.home_item_collect_icon));
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
                        map.put("news_id", data.get(pos).getId());
                        map.put("type", "0");
                        map.put("is_collect", String.valueOf(!data.get(pos).is_collect()));
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
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? data.size() : data.size() + 1;
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        ImageView iv_image;//
        TextView tv_time;//时间
        TextView tv_content;//内容
        ImageView collect_iv;//收藏
        TextView read_number_tv;//阅读数
        CardView item_layout_cv;
        LinearLayout ll_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_tiltle);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_content = itemView.findViewById(R.id.tv_content);
            ll_item = itemView.findViewById(R.id.ll_item);
            collect_iv = itemView.findViewById(R.id.collect_iv);
            read_number_tv = itemView.findViewById(R.id.read_number_tv);
            item_layout_cv = itemView.findViewById(R.id.item_layout_cv);
        }
    }

    public void setData(List<RequestNewsDataBean.Data.Items> data) {
        this.data = data;
    }

    //增加数据
    public void addLastList(List<RequestNewsDataBean.Data.Items> bean) {
        data.addAll(bean);
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }
}
