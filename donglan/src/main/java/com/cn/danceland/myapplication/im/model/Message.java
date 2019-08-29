package com.cn.danceland.myapplication.im.model;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.im.adapters.ChatAdapter;
import com.cn.danceland.myapplication.im.utils.TimeUtil;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageStatus;
import com.tencent.imsdk.ext.message.TIMMessageExt;


/**
 * 消息数据基类
 */
public abstract class Message {

    protected final String TAG = "Message";

    TIMMessage message;

    private boolean hasTime;

    /**
     * 消息描述信息
     */
    private String desc;


    public TIMMessage getMessage() {
        return message;
    }


    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    public abstract void showMessage(ChatAdapter.ViewHolder viewHolder, Context context);

    /**
     * 获取显示气泡
     *
     * @param viewHolder 界面样式
     */
    public RelativeLayout getBubbleView(ChatAdapter.ViewHolder viewHolder, Context context) {
        viewHolder.systemMessage.setVisibility(hasTime ? View.VISIBLE : View.GONE);
        viewHolder.systemMessage.setText(TimeUtil.getChatTimeStr(message.timestamp()));
        showDesc(viewHolder);
        //    LogUtil.i("FaceUrl:"+message.getSenderProfile().getFaceUrl());
        RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
        if (message.isSelf()) {
            viewHolder.leftPanel.setVisibility(View.GONE);
            viewHolder.rightPanel.setVisibility(View.VISIBLE);
            Data info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
            if (info.getPerson().getSelf_avatar_path() != null) {


                Glide.with(context).load(info.getPerson().getSelf_avatar_path()).apply(options).into(viewHolder.rightAvatar);
//               LogUtil.i(message.getSenderProfile().getFaceUrl());
            }

            return viewHolder.rightMessage;
        } else {
            viewHolder.leftPanel.setVisibility(View.VISIBLE);
            viewHolder.rightPanel.setVisibility(View.GONE);
              if (message.getConversation().getType() == TIMConversationType.C2C){
//            if (message.getSenderProfile() != null && message.getSenderProfile().getFaceUrl() != null) {
//                Glide.with(context).load(message.getSenderProfile().getFaceUrl()).apply(options).into(viewHolder.leftAvatar);
//                LogUtil.i("getNickName:" + message.getSenderProfile().getNickName());
//                LogUtil.i("getPeer:" + message.getConversation().getPeer());
//                LogUtil.i("getFaceUrl:" + message.getSenderProfile().getFaceUrl());
//                         }
                  Glide.with(context).load(viewHolder.avatarUrl).apply(options).into(viewHolder.leftAvatar);

            }
            //群聊显示名称，群名片>个人昵称>identify
            if (message.getConversation().getType() == TIMConversationType.Group) {
                viewHolder.sender.setVisibility(View.VISIBLE);
                String name = "";
                if (message.getSenderGroupMemberProfile() != null)
                    name = message.getSenderGroupMemberProfile().getNameCard();
                if (name.equals("") && message.getSenderProfile() != null)
                    name = message.getSenderProfile().getNickName();
                if (name.equals("")) name = message.getSender();
                viewHolder.sender.setText(name);
                if (message.getSenderProfile() != null && message.getSenderProfile().getFaceUrl() != null) {
                    Glide.with(context).load(message.getSenderProfile().getFaceUrl()).apply(options).into(viewHolder.leftAvatar);

                    LogUtil.i("FaceUrl:" + message.getSenderProfile().getFaceUrl());
                }

            } else {
                viewHolder.sender.setVisibility(View.GONE);
            }
            return viewHolder.leftMessage;
        }

    }

    /**
     * 显示消息状态
     *
     * @param viewHolder 界面样式
     */
    public void showStatus(ChatAdapter.ViewHolder viewHolder) {
        switch (message.status()) {
            case Sending:
                viewHolder.error.setVisibility(View.GONE);
                viewHolder.sending.setVisibility(View.VISIBLE);
                break;
            case SendSucc:
                viewHolder.error.setVisibility(View.GONE);
                viewHolder.sending.setVisibility(View.GONE);
                break;
            case SendFail:
                viewHolder.error.setVisibility(View.VISIBLE);
                viewHolder.sending.setVisibility(View.GONE);
                viewHolder.leftPanel.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 判断是否是自己发的
     */
    public boolean isSelf() {
        return message.isSelf();
    }

    /**
     * 获取消息摘要
     */
    public abstract String getSummary();

    String getRevokeSummary() {
        if (message.status() == TIMMessageStatus.HasRevoked) {
            return getSender() + "撤回了一条消息";
        }
        return null;
    }

    /**
     * 保存消息或消息文件
     */
    public abstract void save();


    /**
     * 删除消息
     */
    public void remove() {
        TIMMessageExt ext = new TIMMessageExt(message);
        ext.remove();
    }


    /**
     * 是否需要显示时间获取
     */
    public boolean getHasTime() {
        return hasTime;
    }


    /**
     * 是否需要显示时间设置
     *
     * @param message 上一条消息
     */
    public void setHasTime(TIMMessage message) {
        if (message == null) {
            hasTime = true;
            return;
        }
        hasTime = this.message.timestamp() - message.timestamp() > 300;
    }


    /**
     * 消息是否发送失败
     */
    public boolean isSendFail() {
        return message.status() == TIMMessageStatus.SendFail;
    }

    /**
     * 清除气泡原有数据
     */
    protected void clearView(ChatAdapter.ViewHolder viewHolder, Context context) {
        getBubbleView(viewHolder, context).removeAllViews();
        getBubbleView(viewHolder, context).setOnClickListener(null);
    }

    /**
     * 显示撤回的消息
     */
    boolean checkRevoke(ChatAdapter.ViewHolder viewHolder) {
        if (message.status() == TIMMessageStatus.HasRevoked) {
            viewHolder.leftPanel.setVisibility(View.GONE);
            viewHolder.rightPanel.setVisibility(View.GONE);
            viewHolder.systemMessage.setVisibility(View.VISIBLE);
            viewHolder.systemMessage.setText(getSummary());
            return true;
        }
        return false;
    }

    /**
     * 获取发送者
     */
    public String getSender() {
        if (message.getSender() == null) return "";
        return message.getSender();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    private void showDesc(ChatAdapter.ViewHolder viewHolder) {

        if (desc == null || desc.equals("")) {
            viewHolder.rightDesc.setVisibility(View.GONE);
        } else {
            viewHolder.rightDesc.setVisibility(View.VISIBLE);
            viewHolder.rightDesc.setText(desc);
        }
    }
}
