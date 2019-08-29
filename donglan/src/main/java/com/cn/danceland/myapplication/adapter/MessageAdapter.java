package com.cn.danceland.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.DynHomeActivity;
import com.cn.danceland.myapplication.db.MiMessage;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.SPUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by feng on 2017/12/22.
 */

public class MessageAdapter extends BaseAdapter {
    public List<MiMessage> list;
    public Context context;

    public MessageAdapter(List<MiMessage> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.message_item,null);
            viewHolder.im_user = convertView.findViewById(R.id.im_user);
//            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
//            viewHolder.tv_content = convertView.findViewById(R.id.tv_content);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            viewHolder.tv_zan = convertView.findViewById(R.id.tv_zan);
            viewHolder.rl_message_item = convertView.findViewById(R.id.rl_message_item);
            viewHolder.rl_message = convertView.findViewById(R.id.rl_message);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(list.get(position).getSelfPath()).into(viewHolder.im_user);
        String name = list.get(position).getPersonName();
        if(list.get(position).getType().equals("1")){
            viewHolder.tv_zan.setText(name+" 给你点赞了");
        }else if(list.get(position).getType().equals("2")){
            viewHolder.tv_zan.setText(name+" 关注了你");
        }else if(list.get(position).getType().equals("3")){
            viewHolder.tv_zan.setText(name+" 评论了你");
        }
        viewHolder.rl_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!list.get(position).getType().equals("2")){
                    Intent intent = new Intent(context, DynHomeActivity.class);
                    intent.putExtra("msgId",list.get(position).getDynId());
                    intent.putExtra("userId", SPUtils.getString(Constants.MY_USERID,""));
                    context.startActivity(intent);
                }
            }
        });
//        viewHolder.tv_name.setText("消息标题");
//        viewHolder.tv_content.setText("消息内容");
        viewHolder.tv_date.setText(list.get(position).getTime());

        return convertView;
    }
    private  class ViewHolder{
        CircleImageView im_user;
        TextView tv_name,tv_content,tv_date,tv_zan;
        RelativeLayout rl_message_item,rl_message;
    }
}
