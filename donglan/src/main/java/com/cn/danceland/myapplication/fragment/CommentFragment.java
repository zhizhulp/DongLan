//package com.cn.danceland.myapplication.fragment;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.cn.danceland.myapplication.R;
//import com.cn.danceland.myapplication.adapter.MessageAdapter;
//import com.cn.danceland.myapplication.db.DBData;
//import com.cn.danceland.myapplication.db.MiMessage;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Created by feng on 2017/12/22.
// */
//
//public class CommentFragment extends BaseFragment {
//    DBData dbData;
//    ListView lv_message;
//    List<MiMessage> messageList;
//    Bundle arguments;
//    String type;
//    TextView tv_no;
//    RelativeLayout rl_error;
//    ImageView iv_error;
//    TextView tv_error;
//    @Override
//    public View initViews() {
//        View v = View.inflate(mActivity, R.layout.fragment_comment,null);
//        lv_message = v.findViewById(R.id.lv_message);
//        rl_error = v.findViewById(R.id.rl_error);
//        iv_error = rl_error.findViewById(R.id.iv_error);
//        Glide.with(mActivity).load(R.drawable.img_error).into(iv_error);
//        tv_error = rl_error.findViewById(R.id.tv_error);
//        tv_error.setText("您还未收到任何通知");
//
//        lv_message.setEmptyView(rl_error);
//        //tv_no = v.findViewById(R.id.tv_no);
//        arguments = getArguments();
//        initData();
//        return v;
//    }
//
//    private void initData() {
//        dbData = new DBData();
//        type = arguments.getString("type", null);
//        //3表示的是评论类型的推送消息
//        messageList = new ArrayList<MiMessage>();
//        messageList = dbData.getMessageList();
//        Collections.reverse(messageList);
//        if(messageList!=null&&messageList.size()>0){
//            if("4".equals(type)){
//                //tv_no.setVisibility(View.VISIBLE);
//                lv_message.setVisibility(View.GONE);
//            }else{
//                //tv_no.setVisibility(View.GONE);
//                lv_message.setAdapter(new MessageAdapter(messageList,mActivity));
//            }
//        }else{
//            rl_error.setVisibility(View.VISIBLE);
//            tv_error.setText("您还未收到任何通知");
//            Glide.with(mActivity).load(R.drawable.img_error7).into(iv_error);
//            //tv_no.setVisibility(View.VISIBLE);
//            lv_message.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//}
