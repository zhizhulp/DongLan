package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.MyContactsBean;
import com.cn.danceland.myapplication.im.model.FriendProfile;
import com.cn.danceland.myapplication.im.model.FriendshipInfo;
import com.cn.danceland.myapplication.im.ui.ChatActivity;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.sns.TIMAddFriendRequest;
import com.tencent.imsdk.ext.sns.TIMDelFriendType;
import com.tencent.imsdk.ext.sns.TIMFriendResult;
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt;
import com.tencent.qcloud.ui.CircleImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by shy on 2018/7/20 09:54
 * Email:644563767@qq.com
 * 我的联系人
 */


public class MyContactsActivity extends BaseActivity {
    private ListView listView;
    private List<MyContactsBean.Data> dataList = new ArrayList<>();
    private MyAdapter adapter;
    private TextView tv_error;
    private ImageView iv_error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_contacts);
        initView();
        initData();
    }

    private void initData() {
        findContacts();
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView) findViewById(R.id.list);
        adapter = new MyAdapter();

        View listEmptyView = findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        iv_error = listEmptyView.findViewById(R.id.iv_error);
        iv_error.setImageResource(R.drawable.img_error);
        tv_error.setText("您没有联系人");
        listView.setEmptyView(listEmptyView);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (Constants.DEV_CONFIG) {
                    addUsers(position, "dev" + dataList.get(position).getMember_no());
//                        presenter.addFriend("dev" + datalist.get(position).getMember_no(), time + "", getString(R.string.default_group_name), null);
//                        ChatActivity.navToChat(mActivity, "dev" + datalist.get(position).getMember_no(), TIMConversationType.C2C, "");
                } else {
                    addUsers(position, dataList.get(position).getMember_no());

                }


                //               LogUtil.i(FriendshipInfo.getInstance().getFriends().toString());
                Map<String, List<FriendProfile>> friends = FriendshipInfo.getInstance().getFriends();
                try {
                    List<FriendProfile> friends1 = friends.get(FriendshipInfo.getInstance().getGroups().get(0));
                    //          LogUtil.i(friends1.size() + "");
                    if (friends1.size() > Constants.MAX_FRIEND_COUNT) {

                        //将好友列表升序排列
                        Collections.sort(friends1, new Comparator<FriendProfile>() {
                            @Override
                            public int compare(FriendProfile o1, FriendProfile o2) {
                                Long a;
                                Long b;
                                if (TextUtils.isEmpty(o1.getRemark())) {
                                    a = 0L;
                                } else {
                                    a = Long.parseLong(o1.getRemark());
                                }
                                if (TextUtils.isEmpty(o2.getRemark())) {
                                    b = 0L;
                                } else {
                                    b = Long.parseLong(o2.getRemark());
                                }


                                //升序
                                return a.compareTo(b);
                            }
                        });
                        LogUtil.i("升序排序后--:" + friends1.size());

                        List<String> delUers = new ArrayList<String>();
                        for (int i = 0; i < friends1.size(); i++) {
                            LogUtil.i(friends1.get(i).getRemark());
                            if (i < friends1.size() - Constants.MAX_FRIEND_COUNT) {
                                //获取多余的好友id
                                delUers.add(friends1.get(i).getIdentify());
                            }
                        }
                        //删除多余的好友
                        delUsers(position, delUers);

                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void findContacts() {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.GET_CONTACTS), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                MyContactsBean myContactsBean = new Gson().fromJson(s, MyContactsBean.class);
                dataList = myContactsBean.getData();
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                iv_error.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        }) {


        };
        MyApplication.getHttpQueues().add(request);
    }

    public void addUsers(final int position, String user) {
        //创建请求列表
        List<TIMAddFriendRequest> reqList = new ArrayList<TIMAddFriendRequest>();

//添加好友请求
        TIMAddFriendRequest req = new TIMAddFriendRequest(user);
        req.setAddrSource("");
        req.setAddWording("add me");
        req.setRemark(System.currentTimeMillis() + "");
        reqList.add(req);


//申请添加好友
        TIMFriendshipManagerExt.getInstance().addFriend(reqList, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                LogUtil.i("addFriend failed: 添加好友好友失败" + code + " desc");
                switch (code) {
                    case 6011:
                        ToastUtils.showToastShort("用户不在线");
                        break;
                    case 6200:
                        ToastUtils.showToastShort("请查看网络连接");
                        break;
                    default:
                        ToastUtils.showToastShort("请稍后重试");
                        break;
                }
            }

            @Override
            public void onSuccess(List<TIMFriendResult> result) {
                LogUtil.i("addFriend succ添加好友成功");
                if (Constants.DEV_CONFIG) {
                    ChatActivity.navToChat(MyContactsActivity.this, "dev" + dataList.get(position).getMember_no(), TIMConversationType.C2C, dataList.get(position).getAvatar_url(), dataList.get(position).getName());
                } else {
                    ChatActivity.navToChat(MyContactsActivity.this, dataList.get(position).getMember_no(), TIMConversationType.C2C, dataList.get(position).getAvatar_url(), dataList.get(position).getName());
                }
                for (TIMFriendResult res : result) {
                    LogUtil.i("identifier: " + res.getIdentifer() + " status: " + res.getStatus());
                }
            }
        });
    }

    public void delUsers(final int position, List<String> users) {
        //双向删除好友 test_user
        TIMFriendshipManagerExt.DeleteFriendParam param = new TIMFriendshipManagerExt.DeleteFriendParam();
        param.setType(TIMDelFriendType.TIM_FRIEND_DEL_BOTH)
                .setUsers(users);

        TIMFriendshipManagerExt.getInstance().delFriend(param, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                LogUtil.e("delFriend failed: " + code + " desc");
                ToastUtils.showToastShort("此用户无法聊天");
            }

            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                if (Constants.DEV_CONFIG) {
                    ChatActivity.navToChat(MyContactsActivity.this, "dev" + dataList.get(position).getMember_no(), TIMConversationType.C2C, "", dataList.get(position).getName());
                } else {
                    ChatActivity.navToChat(MyContactsActivity.this, dataList.get(position).getMember_no(), TIMConversationType.C2C, "", dataList.get(position).getName());
                }


                LogUtil.i("delFriend succ");
                for (TIMFriendResult result : timFriendResults) {
                    LogUtil.i("result id: " + result.getIdentifer() + "|status: " + result.getStatus());
                }

            }
        });
    }

    public class MyAdapter extends BaseAdapter {
        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view != null) {

                viewHolder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(MyContactsActivity.this, R.layout.item_profile_summary, null);
                viewHolder = new ViewHolder();
                viewHolder.avatar = (CircleImageView) view.findViewById(R.id.avatar);
                viewHolder.name = (TextView) view.findViewById(R.id.name);
                viewHolder.des = (TextView) view.findViewById(R.id.description);
                viewHolder.item_layout_cv = view.findViewById(R.id.item_layout_cv);
                view.setTag(viewHolder);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(MyContactsActivity.this,80f));
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(MyContactsActivity.this, 16f), DensityUtils.dp2px(MyContactsActivity.this, 16f), DensityUtils.dp2px(MyContactsActivity.this, 16f), DensityUtils.dp2px(MyContactsActivity.this, 11f));
            } else if (position == dataList.size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(MyContactsActivity.this, 16f), DensityUtils.dp2px(MyContactsActivity.this, 11f), DensityUtils.dp2px(MyContactsActivity.this, 16f), DensityUtils.dp2px(MyContactsActivity.this, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(MyContactsActivity.this, 16f), DensityUtils.dp2px(MyContactsActivity.this, 11f), DensityUtils.dp2px(MyContactsActivity.this, 16f), DensityUtils.dp2px(MyContactsActivity.this, 11f));
            }
            viewHolder.item_layout_cv.setLayoutParams(layoutParams);
            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);

            viewHolder.name.setText(dataList.get(position).getName());
            Glide.with(MyContactsActivity.this).load(dataList.get(position).getAvatar_url()).apply(options).into(viewHolder.avatar);

            return view;
        }


        public class ViewHolder {
            public ImageView avatar;
            public TextView name;
            public TextView des;
            public CardView item_layout_cv;
        }
    }
}
