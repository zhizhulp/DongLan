package com.cn.danceland.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.UserSelfHomeActivity;
import com.cn.danceland.myapplication.bean.RequsetSimpleBean;
import com.cn.danceland.myapplication.bean.RequsetUserListBean;
import com.cn.danceland.myapplication.evntbus.EventConstants;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shy on 2017/11/21 09:36
 * Email:644563767@qq.com
 */


public class UserListviewAdapter extends BaseAdapter {
    private List<RequsetUserListBean.Data.Content> data = new ArrayList<RequsetUserListBean.Data.Content>();
    private LayoutInflater mInflater;
    private Context context;
    private int type = 1;//等于3是点赞，默认是1

    public UserListviewAdapter(Context context, List<RequsetUserListBean.Data.Content> data, int type) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
        this.type = type;
    }

    public void addLastList(ArrayList<RequsetUserListBean.Data.Content> bean) {
        this.data.addAll(bean);
    }

    public void addLastList(ArrayList<RequsetUserListBean.Data.Content> bean, int type) {
        this.type = type;
        this.data.addAll(bean);
    }

//    public void setData(ArrayList<RequsetUserListBean.Data.Content> bean) {
//
//        this.data = bean;
//    }

    public void setData(ArrayList<RequsetUserListBean.Data.Content> bean, int type) {
        this.type = type;
        this.data = bean;

    }

    @Override
    public int getCount() {
        return data.size();
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

        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.listview_item_user_list, null);
            viewHolder.iv_avatar = view.findViewById(R.id.iv_avatar);
            viewHolder.tv_nickname = view.findViewById(R.id.tv_nickname);
            viewHolder.iv_sex = view.findViewById(R.id.iv_sex);
            viewHolder.ll_item = view.findViewById(R.id.ll_item);
            viewHolder.ll_guanzhu = view.findViewById(R.id.ll_guanzhu);
            viewHolder.iv_guanzhu = view.findViewById(R.id.iv_guanzhu);
            viewHolder.tv_guanzhu = view.findViewById(R.id.tv_guanzhu);
            viewHolder.item_layout_cv = view.findViewById(R.id.item_layout_cv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(context, 80f));
        if (position == 0) {
            layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 11f));
        } else if (position == data.size() - 1) {
            layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 5f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 16f));
        } else {
            layoutParams.setMargins(DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 5f), DensityUtils.dp2px(context, 16f), DensityUtils.dp2px(context, 11f));
        }
        viewHolder.item_layout_cv.setLayoutParams(layoutParams);

        RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
        LogUtil.i(type + "");
        if (type == 3) {//如果是点赞
            Glide.with(context)
                    .load(data.get(position).getSelf_url())
                    .apply(options)
                    .into(viewHolder.iv_avatar);
            LogUtil.i(data.get(position).getSelf_url());
            viewHolder.ll_guanzhu.setVisibility(View.GONE);

        } else {
            Glide.with(context)
                    .load(data.get(position).getSelf_path())
                    .apply(options)
                    .into(viewHolder.iv_avatar);
            //      LogUtil.i(data.get(position).getSelf_path());
            viewHolder.ll_guanzhu.setVisibility(View.VISIBLE);

        }

        viewHolder.tv_nickname.setText(data.get(position).getNickName());
        // LogUtil.i(data.get(position).getNickName());
        if (data.get(position).getGender() == 1) {
            viewHolder.iv_sex.setImageResource(R.drawable.img_sex1);
        }
        if (data.get(position).getGender() == 2) {
            viewHolder.iv_sex.setImageResource(R.drawable.img_sex2);
        }
        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // LogUtil.i("TYPE+"+type);
                switch (type) {
                    case 1://查看关注
                        context.startActivity(new Intent(context, UserSelfHomeActivity.class).putExtra("id", data.get(position).getUserId()));
                        break;
                    case 2://查看粉丝
                        context.startActivity(new Intent(context, UserSelfHomeActivity.class).putExtra("id", data.get(position).getFollower()));
                        break;
                    case 3://查看点赞
                        context.startActivity(new Intent(context, UserSelfHomeActivity.class).putExtra("id", data.get(position).getPraiseUserId()));
                        break;
                    default:
                        break;
                }
                // context.startActivity(new Intent(context, UserHomeActivity.class).putExtra("id", data.get(position).getAuthor()));
            }
        });

        if (data.get(position).getIs_follower()) {
            viewHolder.tv_guanzhu.setText("已关注");
            viewHolder.iv_guanzhu.setImageResource(R.drawable.img_xin1);
            // viewHolder.tv_guanzhu.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
        } else {
            viewHolder.tv_guanzhu.setText("+关注");
            viewHolder.iv_guanzhu.setImageResource(R.drawable.img_xin);
            // viewHolder.tv_guanzhu.setTextColor(Color.BLACK);
        }
        if (type == 2) {
            if (TextUtils.equals(data.get(position).getFollower(), SPUtils.getString(Constants.MY_USERID, null))) {

                viewHolder.tv_guanzhu.setText("");
                viewHolder.ll_guanzhu.setVisibility(View.INVISIBLE);
            }
        }
        if (type == 1) {
            if (TextUtils.equals(data.get(position).getUser_id(), SPUtils.getString(Constants.MY_USERID, null))) {

                viewHolder.tv_guanzhu.setText("");
                viewHolder.ll_guanzhu.setVisibility(View.INVISIBLE);
            }
        }

        viewHolder.ll_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!data.get(position).getIs_follower()) {//未关注添加关注
                    int pos = position;
                    try {
                        if (type==1){
                            addGuanzhu(data.get(position).getUser_id(), true, pos);
                        }
                        if (type==2){
                            addGuanzhu(data.get(position).getFollower(), true, pos);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }

    class StrBean {
        public String is_praise;
        public String msg_id;
        public String is_follower;
        public String user_id;
    }


    /**
     * 加关注
     *
     * @param id
     * @param b
     */
    private void addGuanzhu(final String id, final boolean b, final int pos) throws JSONException {

        StrBean strBean = new StrBean();
        strBean.is_follower = b + "";
        strBean.user_id = id;
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject(gson.toJson(strBean).toString());
        LogUtil.i(gson.toJson(strBean).toString());
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.ADD_GUANZHU), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject json) {
                LogUtil.i(json.toString());
                Gson gson = new Gson();
                RequsetSimpleBean requestInfoBean = new RequsetSimpleBean();
                requestInfoBean = gson.fromJson(json.toString(), RequsetSimpleBean.class);

                if (requestInfoBean.isSuccess()) {

                    ToastUtils.showToastShort("关注成功");
                    EventBus.getDefault().post(new StringEvent(data.get(pos).getUser_id(), EventConstants.ADD_GUANZHU));
                    data.get(pos).setIs_follower(true);
                    notifyDataSetChanged();

                } else {
                    ToastUtils.showToastShort("关注失败");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToastShort("请检查手机网络！");
            }
        });

        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("addGuanzhu");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getHttpQueues().add(request);
    }

    class ViewHolder {
        TextView tv_nickname;//昵称
        ImageView iv_avatar;//头像
        ImageView iv_sex;//性别
        LinearLayout ll_item;
        LinearLayout ll_guanzhu;
        ImageView iv_guanzhu;
        TextView tv_guanzhu;
        CardView item_layout_cv;
    }
}
