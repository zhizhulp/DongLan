package com.cn.danceland.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.UserListActivity;
import com.cn.danceland.myapplication.activity.UserSelfHomeActivity;
import com.cn.danceland.myapplication.bean.RequsetUserListBeanZan;

import java.util.List;

/**
 * Created by shy on 2017/10/26 09:44
 * Email:644563767@qq.com
 */

public class DynZanHeadviewRecylerViewAdapter extends RecyclerView.Adapter<DynZanHeadviewRecylerViewAdapter.ViewHolder> {
    public List<RequsetUserListBeanZan.Data.Content> datas = null;
    private Context context;
    private String msgId;
    private String userId;

    public DynZanHeadviewRecylerViewAdapter(Context context, List<RequsetUserListBeanZan.Data.Content> datas) {
        this.datas = datas;
        this.context = context;
    }

    public void setData(List<RequsetUserListBeanZan.Data.Content> datas, String msgId ,String userId) {
        this.datas = datas;
        this.msgId = msgId;
        this.userId = userId;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recylerview_item_zan, viewGroup, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //   viewHolder.tv_pcikname.setText(datas.get(position).getNickName());
        viewHolder.iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datas.size() > 4 && datas.size() == position) {
                    //更多
               //     ToastUtils.showToastShort("查看全部点赞");
//                    context.startActivity(new Intent(context, UserListActivity.class).putExtra("id",  userId).putExtra("msgId", msgId).putExtra("type", 3).putExtra("from",6));

                } else {
                    context.startActivity(new Intent(context, UserSelfHomeActivity.class).putExtra("id", datas.get(position).getPraiseUserId()));
                }

            }
        });

        if (datas.size() > 4 && datas.size() == position) {
            //更多
//            viewHolder.iv_avatar.setImageResource(R.drawable.img_more_zan);
        } else {
            //m默认头像
            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
            Glide.with(context)
                    .load(datas.get(position).getSelfUrl())
                    .apply(options)
                    .into(viewHolder.iv_avatar);
        }


    }

    //获取数据的数量
    @Override
    public int getItemCount() {
//        if (datas.size() > 4) {
//            return datas.size() + 1;
//        }
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //   public TextView tv_pcikname;
        public ImageView iv_avatar;

        public ViewHolder(View view) {
            super(view);
            //       tv_pcikname = view.findViewById(R.id.tv_pcikname);
            iv_avatar = view.findViewById(R.id.iv_avatar);

        }
    }
}

