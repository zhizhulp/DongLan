package com.cn.danceland.myapplication.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.cn.danceland.myapplication.activity.AddPotentialActivity;
import com.cn.danceland.myapplication.activity.AddRevisiterRecordActivity;
import com.cn.danceland.myapplication.activity.PotentialDetailsActivity;
import com.cn.danceland.myapplication.bean.RequsetPotentialListBean;
import com.cn.danceland.myapplication.evntbus.IntEvent;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.im.model.FriendProfile;
import com.cn.danceland.myapplication.im.model.FriendshipInfo;
import com.cn.danceland.myapplication.im.ui.ChatActivity;
import com.cn.danceland.myapplication.utils.CallLogUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.sns.TIMAddFriendRequest;
import com.tencent.imsdk.ext.sns.TIMDelFriendType;
import com.tencent.imsdk.ext.sns.TIMFriendResult;
import com.tencent.imsdk.ext.sns.TIMFriendshipManagerExt;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.cn.danceland.myapplication.R.id.tv_biaoqian;
import static com.cn.danceland.myapplication.R.id.tv_name;

/**
 * Created by shy on 2018/1/9 09:39
 * Email:644563767@qq.com
 * //回访人员列表
 */


public class RevisitListFragment extends BaseFragment {
    private PullToRefreshListView mListView;
    private List<RequsetPotentialListBean.Data.Content> datalist = new ArrayList<>();
    private MyListAatapter myListAatapter;
    private Gson gson = new Gson();
    private int mCurrentPage = 1;//起始请求页
    private boolean isEnd = false;
    private StrBean strBean;
    private String auth;
    private TextView tv_error;
    private ImageView imageView;

    private FriendshipManagerPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册event事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(StringEvent event) {

        switch (event.getEventCode()) {

            case 161://刷新页面
                mCurrentPage = 1;
                strBean.page = strBean.page = mCurrentPage - 1 + "";
                try {
                    find_potential_list(gson.toJson(strBean).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 162://最近维护
                strBean = new StrBean(auth);
                mCurrentPage = 1;
                StrBean.Order order = new StrBean.Order();
                order.setName("last_time");
                order.setValue("desc");//最近维护
                List<StrBean.Order> oders=new ArrayList<>();
                oders.add(order);
                strBean.setOrder(oders);
                strBean.searchInfo = event.getMsg();
                try {
                    find_potential_list(gson.toJson(strBean).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 163://最晚维护
                strBean = new StrBean(auth);
                mCurrentPage = 1;
                strBean.searchInfo = event.getMsg();
                strBean.setPage(mCurrentPage - 1 + "");
                StrBean.Order order1 = new StrBean.Order();
                order1.setName("last_time");
                order1.setValue("asc");//最wan
                List<StrBean.Order> oders1=new ArrayList<>();
                oders1.add(order1);
                strBean.setOrder(oders1);
                try {
                    find_potential_list(gson.toJson(strBean).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 164://健身指数
                strBean = new StrBean(auth);
                mCurrentPage = 1;
                strBean.searchInfo = event.getMsg();
                strBean.setPage(mCurrentPage - 1 + "");
                StrBean.Order order2 = new StrBean.Order();
                order2.setName("last_time");
                order2.setValue("desc");
                StrBean.Order order3 = new StrBean.Order();
                order3.setName("fitness_level");
                order3.setValue("desc");
                List<StrBean.Order> oders2=new ArrayList<>();

                oders2.add(order3);
                oders2.add(order2);
                strBean.setOrder(oders2);

                try {
                    find_potential_list(gson.toJson(strBean).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 165://关注程度
                strBean = new StrBean(auth);
                strBean.searchInfo = event.getMsg();
                mCurrentPage = 1;
                strBean.setPage(mCurrentPage - 1 + "");



                StrBean.Order order4= new StrBean.Order();
                order4.setName("last_time");
                order4.setValue("desc");
                StrBean.Order order5 = new StrBean.Order();
                order5.setName("follow_level");
                order5.setValue("desc");
                List<StrBean.Order> oders3=new ArrayList<>();
                oders3.add(order5);
                oders3.add(order4);
                strBean.setOrder(oders3);



                try {
                    find_potential_list(gson.toJson(strBean).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }

    }

    @Override
    public View initViews() {
        auth = getArguments().getString("auth");
        View v = View.inflate(mActivity, R.layout.fragment_revist_list, null);
        Button btn_add = v.findViewById(R.id.btn_add);
        if (TextUtils.equals(auth, "2")) {//如果是会员隐藏添加按钮
            btn_add.setVisibility(View.GONE);
        }
        v.findViewById(R.id.btn_add).setOnClickListener(this);


        //       EasyRecyclerView easyRecyclerView=v.findViewById(R.id.easyrecyclerView);


        mListView = v.findViewById(R.id.pullToRefresh);
        View listEmptyView = v.findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        mListView.setEmptyView(listEmptyView);
        myListAatapter = new MyListAatapter();
        mListView.setAdapter(myListAatapter);
        //设置下拉刷新模式both是支持下拉和上拉
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                TimerTask task = new TimerTask() {
                    public void run() {
                        new DownRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                TimerTask task = new TimerTask() {
                    public void run() {
                        new UpRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);
            }
        });
        init_pullToRefresh();

        return v;
    }

    private void init_pullToRefresh() {
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        // 设置下拉刷新文本
        ILoadingLayout startLabels = mListView
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        // 设置上拉刷新文本
        ILoadingLayout endLabels = mListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
    }

    @Override
    public void initData() {
        strBean = new StrBean(auth);
        mCurrentPage = 1;
        strBean.setPage(mCurrentPage - 1 + "");
        StrBean.Order order = new StrBean.Order();
        order.setName("last_time");
        order.setValue("desc");//最近维护
        List<StrBean.Order> oders=new ArrayList<>();
        oders.add(order);
        strBean.setOrder(oders);

        try {
            find_potential_list(gson.toJson(strBean).toString());
            LogUtil.i(gson.toJson(strBean).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * 下拉刷新
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            init_pullToRefresh();
            mCurrentPage = 1;
            strBean.setPage(mCurrentPage - 1 + "");
            try {
                find_potential_list(gson.toJson(strBean).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            myListAatapter.notifyDataSetChanged();
            mListView.onRefreshComplete();
        }
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
                    ChatActivity.navToChat(mActivity, "dev" + datalist.get(position).getMember_no(), TIMConversationType.C2C, "", datalist.get(position).getNick_name());
                } else {
                    ChatActivity.navToChat(mActivity, datalist.get(position).getMember_no(), TIMConversationType.C2C, "", datalist.get(position).getNick_name());
                }
                for (TIMFriendResult res : result) {
                    LogUtil.i("identifier: " + res.getIdentifer() + " status: " + res.getStatus());
                }
            }
        });
    }

    /**
     * 上拉拉刷新
     */
    private class UpRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            if (!isEnd) {//还有数据请求
                strBean.setPage(mCurrentPage - 1 + "");
                try {
                    find_potential_list(gson.toJson(strBean).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //   myListAatapter.notifyDataSetChanged();
            mListView.onRefreshComplete();
            myListAatapter.notifyDataSetChanged();
            if (isEnd) {//没数据了
                mListView.onRefreshComplete();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                startActivity(new Intent(mActivity, AddPotentialActivity.class));
                break;
            default:
                break;
        }
    }


    static class StrBean {
        public StrBean(String auth) {
            this.auth = auth;
        }

        public String page;
        public String auth;
        public String searchInfo;

    //    public Order order;
        public  List<Order> order;

        @Override
        public String toString() {
            return "StrBean{" +
                    "page='" + page + '\'' +
                    ", auth='" + auth + '\'' +
                    ", order=" + order +
                    '}';
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

//        public Order getOrder() {
//            return order;
//        }
//
//        public void setOrder(Order order) {
//            this.order = order;
//        }

        public String getSearchInfo() {
            return searchInfo;
        }

        public void setSearchInfo(String searchInfo) {
            this.searchInfo = searchInfo;
        }

        public List<Order> getOrder() {
            return order;
        }

        public void setOrder(List<Order> order) {
            this.order = order;
        }

        public static class Order {

//            public String fitness_level;
//            public String follow_level;
//            public String last_time;
            public String name;
            public String value;

            @Override
            public String toString() {
                return "Order{" +
//                        "fitness_level='" + fitness_level + '\'' +
//                        ", follow_level='" + follow_level + '\'' +
//                        ", last_time='" + last_time + '\'' +
                        ", name='" + name + '\'' +
                        ", value='" + value + '\'' +
                        '}';
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

//            public String getFitness_level() {
//                return fitness_level;
//            }
//
//            public void setFitness_level(String fitness_level) {
//                this.fitness_level = fitness_level;
//            }
//
//            public String getFollow_level() {
//                return follow_level;
//            }
//
//            public void setFollow_level(String follow_level) {
//                this.follow_level = follow_level;
//            }
//
//            public String getLast_time() {
//                return last_time;
//            }
//
//            public void setLast_time(String last_time) {
//                this.last_time = last_time;
//            }
        }
    }


    class MyListAatapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (convertView == null) {

                vh = new ViewHolder();

                convertView = View.inflate(mActivity, R.layout.listview_item_customer_list, null);

                vh.iv_avatar = convertView.findViewById(R.id.iv_avatar);

                vh.iv_callphone = convertView.findViewById(R.id.iv_callphone);
                vh.iv_hx_msg = convertView.findViewById(R.id.iv_hx_msg);

                vh.tv_name = convertView.findViewById(tv_name);
                vh.tv_biaoqian = convertView.findViewById(tv_biaoqian);

                vh.iv_sex = convertView.findViewById(R.id.iv_sex);

                vh.tv_lasttime = convertView.findViewById(R.id.tv_lasttime);
                vh.iv_push_set = convertView.findViewById(R.id.iv_push_set);

                vh.ll_item = convertView.findViewById(R.id.ll_item);


                convertView.setTag(vh);

            } else {

                vh = (ViewHolder) convertView.getTag();

            }
            if (TextUtils.equals(auth,"2")){
               vh. iv_push_set.setVisibility(View.VISIBLE);
                if (datalist.get(position).getPush_setting()==0){
                    vh. iv_push_set.setImageResource(R.drawable.push_open);
                }else {
                    vh. iv_push_set.setImageResource(R.drawable.push_close);
                }
            }else {
                vh. iv_push_set.setVisibility(View.GONE);
            }


            //会籍或会籍主管
            if (SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_HUIJIGUWEN || SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_HUIJIZHUGUANG) {
                if (TextUtils.isEmpty(datalist.get(position).getAdmin_mark())) {
                    vh.tv_biaoqian.setText(datalist.get(position).getAdmin_mark());
                } else {
                    vh.tv_biaoqian.setText("(" + datalist.get(position).getAdmin_mark() + ")");
                }


            }
            //教练或教练主管
            if (SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIAN || SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIANZHUGUAN) {
                if (TextUtils.isEmpty(datalist.get(position).getTeach_mark())) {
                    vh.tv_biaoqian.setText(datalist.get(position).getTeach_mark());
                } else {
                    vh.tv_biaoqian.setText("(" + datalist.get(position).getTeach_mark() + ")");
                }
            }


            //第一个是上下文，第二个是圆角的弧度
            RequestOptions options = new RequestOptions().transform(new GlideRoundTransform(mActivity, 10)).placeholder(R.drawable.img_avatar1).error(R.drawable.img_avatar1);


            Glide.with(mActivity).load(datalist.get(position).getAvatar_url()).apply(options).into(vh.iv_avatar);
//            if (TextUtils.isEmpty(datalist.get(position).getNick_name())) {
//                vh.tv_name.setText(datalist.get(position).getCname());
//            } else {
//                vh.tv_name.setText(datalist.get(position).getCname() + "(" + datalist.get(position).getNick_name() + ")");
//            }
            vh.tv_name.setText(datalist.get(position).getCname());
            if (TextUtils.equals(datalist.get(position).getGender(), "1")) {
                vh.iv_sex.setImageResource(R.drawable.img_sex1);
            }
            if (TextUtils.equals(datalist.get(position).getGender(), "2")) {
                vh.iv_sex.setImageResource(R.drawable.img_sex2);
            }
            if (datalist.get(position).getLast_time() != null) {
                vh.tv_lasttime.setText(datalist.get(position).getLast_time().replace("-","."));
            } else {
                vh.tv_lasttime.setText("最近未维护");
            }

            vh.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info", datalist.get(position));
                    startActivity(new Intent(mActivity, PotentialDetailsActivity.class).putExtra("id", datalist.get(position).getId()).putExtra("auth", auth).putExtras(bundle));
                }
            });
            vh.iv_callphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (PermissionsUtil.hasPermission(mActivity, Manifest.permission.CALL_PHONE)) {
                        //有权限
                        showDialog(datalist.get(position).getPhone_no(), position);
                    } else {
                        PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permissions) {
                                showDialog(datalist.get(position).getPhone_no(), position);
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permissions) {
                                //用户拒绝了申请
                                ToastUtils.showToastShort("没有权限");
                            }
                        }, new String[]{Manifest.permission.CALL_PHONE}, false, null);
                    }

                    //pos = position;
                }
            });
            vh.iv_hx_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (TextUtils.isEmpty(datalist.get(position).getNick_name())) {
                        ToastUtils.showToastShort("该用户不在线");
                        return;
                    }

                    if (Constants.DEV_CONFIG) {
                        addUsers(position, "dev" + datalist.get(position).getMember_no());
//                        presenter.addFriend("dev" + datalist.get(position).getMember_no(), time + "", getString(R.string.default_group_name), null);
//                        ChatActivity.navToChat(mActivity, "dev" + datalist.get(position).getMember_no(), TIMConversationType.C2C, "");
                    } else {
                        addUsers(position, datalist.get(position).getMember_no());

                    }


                    //               LogUtil.i(FriendshipInfo.getInstance().getFriends().toString());
                    Map<String, List<FriendProfile>> friends = FriendshipInfo.getInstance().getFriends();
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
//                    else {
//                        Long time = System.currentTimeMillis();
//                        if (Constants.DEV_CONFIG) {
//                            presenter.addFriend("dev" + datalist.get(position).getMember_no(), time + "", getString(R.string.default_group_name), null);
//                            ChatActivity.navToChat(mActivity, "dev" + datalist.get(position).getMember_no(), TIMConversationType.C2C, "");
//                        } else {
//                            presenter.addFriend(datalist.get(position).getMember_no(), "" + time, getString(R.string.default_group_name), null);
//                            ChatActivity.navToChat(mActivity, datalist.get(position).getMember_no(), TIMConversationType.C2C, "");
//                        }
//                    }


                }
            });


            return convertView;

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
                        ChatActivity.navToChat(mActivity, "dev" + datalist.get(position).getMember_no(), TIMConversationType.C2C, "", datalist.get(position).getNick_name());
                    } else {
                        ChatActivity.navToChat(mActivity, datalist.get(position).getMember_no(), TIMConversationType.C2C, "", datalist.get(position).getNick_name());
                    }


                    LogUtil.i("delFriend succ");
                    for (TIMFriendResult result : timFriendResults) {
                        LogUtil.i("result id: " + result.getIdentifer() + "|status: " + result.getStatus());
                    }

                }
            });
        }

        /**
         * 是否添加回访记录
         */
        public void showDialogRrcord(final int pos) {
            final ContentResolver cr;
            cr = getActivity().getContentResolver();
            AlertDialog.Builder dialog =
                    new AlertDialog.Builder(mActivity);
            dialog.setTitle("提示");
            dialog.setMessage("是否读取通话记录，并添加到回访记录");
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Bundle bundle = new Bundle();
                    bundle.putString("time", CallLogUtils.getLastCallHistoryDuration(null, cr) + "");
                    bundle.putString("id", datalist.get(pos).getId());
                    bundle.putString("auth", datalist.get(pos).getAuth());
                    bundle.putString("member_name", datalist.get(pos).getCname());
                    bundle.putString("member_no", datalist.get(pos).getMember_no());
                    startActivity(new Intent(mActivity, AddRevisiterRecordActivity.class)
                            .putExtras(bundle));

                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }

        /**
         * 提示
         */
        private void showDialog(final String phoneNo, final int pos) {


//            String callHistoryListStr = CallLogUtils.getCallHistoryList(null, cr);
//            LogUtil.i(callHistoryListStr.toString());
//            LogUtil.i(CallLogUtils.getLastCallHistoryDuration(null,cr)+"");
            AlertDialog.Builder dialog =
                    new AlertDialog.Builder(mActivity);
            dialog.setTitle("提示");
            dialog.setMessage("是否呼叫" + phoneNo);
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    call(phoneNo);

                    //  call("13436907535");
                    showDialogRrcord(pos);

                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }

        /**
         * 调用拨号功能
         *
         * @param phone 电话号码
         */
        private void call(String phone) {

            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            //   Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phone));
            //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

        class ViewHolder {
            public ImageView iv_push_set;
            public ImageView iv_avatar;
            public ImageView iv_callphone;
            public TextView tv_name;
            public TextView tv_biaoqian;
            public ImageView iv_sex;
            public TextView tv_lasttime;
            public LinearLayout ll_item;
            public ImageView iv_hx_msg;
        }

    }

    private void setEnd() {
        //没数据了
        isEnd = true;
        ILoadingLayout endLabels = mListView.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("—我是有底线的—");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("—我是有底线的—");// 刷新时
        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
        endLabels.setLoadingDrawable(null);
        //  mListView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    /**
     * 查询潜客
     *
     * @throws JSONException
     */
    public void find_potential_list(final String s) throws JSONException {

        LogUtil.i(s.toString());
        JSONObject jsonObject = new JSONObject(s.toString());
        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_POTENTIAL_LIST), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequsetPotentialListBean potentialListBean = new RequsetPotentialListBean();
                potentialListBean = gson.fromJson(jsonObject.toString(), RequsetPotentialListBean.class);

                //   LogUtil.i(potentialListBean.toString());
                //  datalist = potentialListBean.getData().getContent();
                myListAatapter.notifyDataSetChanged();

                if (potentialListBean.getSuccess()) {

                    if (potentialListBean.getData() != null) {
                        EventBus.getDefault().post(new IntEvent(potentialListBean.getData().getTotalElements(), 161));
                    }

                    if (potentialListBean.getData().getLast()) {
                        //    mCurrentPage = mCurrentPage + 1;
                        isEnd = true;
                        setEnd();
                    } else {
                        //  datalist.addAll( orderinfo.getData().getContent());
                        //  myListAatapter.notifyDataSetChanged();
                        isEnd = false;
                        init_pullToRefresh();
                    }

                    if (mCurrentPage == 1) {
                        datalist = potentialListBean.getData().getContent();
                        myListAatapter.notifyDataSetChanged();
                        mCurrentPage = mCurrentPage + 1;
                    } else {
                        datalist.addAll(potentialListBean.getData().getContent());
                        myListAatapter.notifyDataSetChanged();
                        mCurrentPage = mCurrentPage + 1;
                    }

                } else {
                    ToastUtils.showToastLong(potentialListBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                imageView.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");

            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }
}
