package com.cn.danceland.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.bean.RequestMessageNumBean;
import com.cn.danceland.myapplication.evntbus.EventConstants;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.FoundFragment;
import com.cn.danceland.myapplication.fragment.MessageFragment;
import com.cn.danceland.myapplication.fragment.NoticeFragment;
import com.cn.danceland.myapplication.fragment.SystemMessageFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by feng on 2017/12/11.
 */

public class MessageActivity extends BaseFragmentActivity {
    int pinglunNum;
    int dianzanNum;
    int fansNum;
    RelativeLayout zan_message;
    ListView lv_message;
    DongLanTitleView dongLanTitleView;
    ImageView im_back;
    TabLayout tablayout;
    FragmentManager fragmentManager;
    //CommentFragment commentFragment;//不用了
    NoticeFragment noticeFragment;//yxx 通知
    FoundFragment foundFragment;//发现
    SystemMessageFragment systemMessageFragment;//yxx 系统消息
    TabItem tab1;
    TabItem tab2;
    TabItem tab3;
    TabItem tab4;
    private int currentTabIndex = 0;
    private View foundView;
    private TextView foundContent;
    private TextView foundNum;
    private View messageView;
    private TextView messageContent;
    private TextView messageNum;
    private View systemView;
    private TextView systemContent;
    private TextView systemNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        EventBus.getDefault().register(this);//注册event事件
        initViews();
        setOnclick();
    }

    private void setOnclick() {

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.setInt("pinglunNum", 0);
                SPUtils.setInt("dianzanNum", 0);
                SPUtils.setInt("fansNum", 0);
                EventBus.getDefault().post(new StringEvent(0 + "", 101));
                finish();
            }
        });
        //lv_message.setAdapter(new MessageAdapter());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SPUtils.setInt("pinglunNum", 0);
            SPUtils.setInt("dianzanNum", 0);
            SPUtils.setInt("fansNum", 0);
            EventBus.getDefault().post(new StringEvent(0 + "", 101));
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void initViews() {
        pinglunNum = SPUtils.getInt("pinglunNum", 0);
        dianzanNum = SPUtils.getInt("dianzanNum", 0);
        fansNum = SPUtils.getInt("fansNum", 0);
        registerBroadcastReceiver();
        tablayout = findViewById(R.id.tablayout);
//        tab1 = findViewById(R.id.tab1);
//        tab2 = findViewById(R.id.tab2);
//        tab3 = findViewById(R.id.tab3);
//        tab4 = findViewById(R.id.tab4);
        foundView = View.inflate(this, R.layout.message_item_tab, null);
        foundContent = foundView.findViewById(R.id.content_tv);
        foundNum = foundView.findViewById(R.id.num_tv);
        foundContent.setText("发现");
        tablayout.addTab(tablayout.newTab().setCustomView(foundView));
        messageView = View.inflate(this, R.layout.message_item_tab, null);
        messageContent = messageView.findViewById(R.id.content_tv);
        messageNum = messageView.findViewById(R.id.num_tv);
        messageContent.setText("通知");
        tablayout.addTab(tablayout.newTab().setCustomView(messageView));
        systemView = View.inflate(this, R.layout.message_item_tab, null);
        systemContent = systemView.findViewById(R.id.content_tv);
        systemNum = systemView.findViewById(R.id.num_tv);
        systemContent.setText("系统");
        tablayout.addTab(tablayout.newTab().setCustomView(systemView));

//        tablayout.addTab(tablayout.newTab().setText("发现"));
//        tablayout.addTab(tablayout.newTab().setText("通知"));
//        tablayout.addTab(tablayout.newTab().setText("系统"));
//        if (pinglunNum + dianzanNum + fansNum > 0) {
//            tablayout.addTab(tablayout.newTab().setText("通知" + "(" + (pinglunNum + dianzanNum + fansNum) + ")"));
//        } else {
//            tablayout.addTab(tablayout.newTab().setText("通知"));
//        }
//        if (dianzanNum > 0) {
//            tablayout.addTab(tablayout.newTab().setText("点赞" + "(" + dianzanNum + ")"));
//        } else {
//            tablayout.addTab(tablayout.newTab().setText("点赞"));
//        }
//        if (fansNum > 0) {
//            tablayout.addTab(tablayout.newTab().setText("粉丝" + "(" + fansNum + ")"));
//        } else {
//            tablayout.addTab(tablayout.newTab().setText("粉丝"));
//        }
        //   tablayout.addTab(tablayout.newTab().setText("私信"));
//        if(pinglunNum>0){
//            new QBadgeView(MessageActivity.this).bindTarget(tab2).setBadgeNumber(pinglunNum).setBadgeGravity(Gravity.RIGHT);
//        }else if(dianzanNum>0){
//            new QBadgeView(MessageActivity.this).bindTarget(tab3).setBadgeNumber(dianzanNum).setBadgeGravity(Gravity.RIGHT);
//        }else if(fansNum>0){
//            new QBadgeView(MessageActivity.this).bindTarget(tab4).setBadgeNumber(fansNum).setBadgeGravity(Gravity.RIGHT);
//        }

        showFragment("0");
        //SPUtils.setInt("pinglunNum", 0);
        tablayout.getTabAt(0).select();
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTabIndex = tab.getPosition();
                if (currentTabIndex == 0) {//发现
                    showFragment("0");
                } else if (currentTabIndex == 1) {
                    SPUtils.setInt("pinglunNum", 0);
                    SPUtils.setInt("dianzanNum", 0);
                    SPUtils.setInt("fansNum", 0);
                    showFragment("1");//评论

                } else if (currentTabIndex == 2) {
                    showFragment("2");
                    //ToastUtils.showToastShort("没有系统消息");
                }
//                else if (currentTabIndex == 4) {//私信对话列表
//                    showFragment("5");
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        zan_message = findViewById(R.id.zan_message);
//        lv_message = findViewById(R.id.lv_message);
        dongLanTitleView = findViewById(R.id.title);
        im_back = dongLanTitleView.findViewById(R.id.iv_back);
        ImageView iv_more = dongLanTitleView.findViewById(R.id.iv_more);
        iv_more.setImageDrawable(getResources().getDrawable(R.drawable.message_icon));
        iv_more.setVisibility(View.VISIBLE);
        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, SettingMessageActivity.class));
            }
        });
    }

    public void showFragment(String str) {
        fragmentManager = getSupportFragmentManager();
        foundFragment = new FoundFragment();
        noticeFragment = new NoticeFragment();
        systemMessageFragment = new SystemMessageFragment();
        MessageFragment messageFragment1=new MessageFragment();
        MessageFragment messageFragment2=new MessageFragment();
        MessageFragment messageFragment3=new MessageFragment();

        Bundle bundle = new Bundle();
        bundle.putString("type", str);
//        commentFragment.setArguments(bundle);
        noticeFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        if (TextUtils.equals(str, "0")) {
//            fragmentTransaction.replace(R.id.message_fragment, foundFragment).commit();
//        } else if (TextUtils.equals(str, "1")) {
//            fragmentTransaction.replace(R.id.message_fragment, noticeFragment).commit();
//        } else if (TextUtils.equals(str, "2")) {
//            fragmentTransaction.replace(R.id.message_fragment, systemMessageFragment).commit();
//        }
        if (TextUtils.equals(str, "0")) {
            messageFragment1.setType("1");//类型(1=>发现,2=>日常,3=>系统)
            fragmentTransaction.replace(R.id.message_fragment, messageFragment1).commit();
        } else if (TextUtils.equals(str, "1")) {
            messageFragment2.setType("2");//类型(1=>发现,2=>日常,3=>系统)
            fragmentTransaction.replace(R.id.message_fragment, messageFragment2).commit();
        } else if (TextUtils.equals(str, "2")) {
            messageFragment3.setType("3");//类型(1=>发现,2=>日常,3=>系统)
            fragmentTransaction.replace(R.id.message_fragment, messageFragment3).commit();
        }
        queryCount("1");//请求后面数据，刷新未读数  通知
        queryCount("2");//请求后面数据，刷新未读数  通知
        queryCount("3");//请求后面数据，刷新未读数  通知
//        if (TextUtils.equals(str, "5")) {
//            LogUtil.i("显示对话列表");
        //        fragmentTransaction
//                    .replace(R.id.message_fragment, myConversationListFragment)
////                    .add(R.id.message_fragment, myConversationListFragment)
////                    .show(myConversationListFragment)
//                    .commit();

//            fragmentTransaction
//                    .add(R.id.message_fragment, commentFragment)
//                    .show(commentFragment)
//                    .commit();
//        }
        //            fragmentTransaction.replace(R.id.message_fragment, commentFragment);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constants.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtil.i("收到广播");
                //  updateUnreadLabel();
                //   updateUnreadAddressLable();
                if (currentTabIndex == 4) {
                    // refresh conversation list
                }
            }
//                else if (currentTabIndex == 1) {
//                    if(contactListFragment != null) {
//                        contactListFragment.refresh();
//                    }
//                }
//                String action = intent.getAction();
//                if(action.equals(Constants.ACTION_GROUP_CHANAGED)){
//                    if (EaseCommonUtils.getTopActivity(ShouHuanMainActivity.this).equals(GroupsActivity.class.getName())) {
//                        GroupsActivity.instance.onResume();
//                    }
//                }
        };
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }


    /**
     * 查询数据
     *
     * @param type 类型(1=>发现,2=>日常,3=>系统)0所有
     */
    public void queryCount(final String type) {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_COUNT), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("Response--" + s);
                RequestMessageNumBean infoBean = new Gson().fromJson(s, RequestMessageNumBean.class);
                LogUtil.i(infoBean.toString());
                LogUtil.i("code--" + infoBean.getCode());
                if (infoBean.getSuccess() && infoBean.getCode() == 0) {
                    switch (type) {
                        case "1":
                            if (infoBean.getData().equals("0")) {
                                foundNum.setVisibility(View.GONE);
                            } else {
                                foundNum.setVisibility(View.VISIBLE);
                            }
                            if (Integer.valueOf(infoBean.getData()) > 99) {
                                foundNum.setText(99 + "");
                            } else {
                                foundNum.setText(infoBean.getData());
                            }
                            break;
                        case "2":
                            if (infoBean.getData().equals("0")) {
                                messageNum.setVisibility(View.GONE);
                            } else {
                                messageNum.setVisibility(View.VISIBLE);
                            }
                            if (Integer.valueOf(infoBean.getData()) > 99) {
                                messageNum.setText(99 + "");
                            } else {
                                messageNum.setText(infoBean.getData());
                            }
                            break;
                        case "3":
                            if (infoBean.getData().equals("0")) {
                                systemNum.setVisibility(View.GONE);
                            } else {
                                systemNum.setVisibility(View.VISIBLE);
                            }
                            if (Integer.valueOf(infoBean.getData()) > 99) {
                                systemNum.setText(99 + "");
                            } else {
                                systemNum.setText(infoBean.getData());
                            }
                            break;
                    }
                }
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
                HashMap<String, String> map = new HashMap<>();
                map.put("type", type);//类型(1=>发现,2=>日常,3=>系统)0所有
                return map;
            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    class StrBean {
        public String type;
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {
            case EventConstants.MY_MESSAGE_FOUND_NUM://我的通知发现未读数
                queryCount("1");//(1=>发现,2=>日常,3=>系统)0所有
                break;
            case EventConstants.MY_MESSAGE_DAILY_NUM://我的通知日常未读数
                queryCount("2");//(1=>发现,2=>日常,3=>系统)0所有
                break;
            case EventConstants.MY_MESSAGE_SYSTEM_NUM://我的通知系统未读数
                queryCount("3");//(1=>发现,2=>日常,3=>系统)0所有
                break;
            default:
                break;
        }
    }
}
