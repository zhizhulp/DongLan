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
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.CallLogUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.cn.danceland.myapplication.R.id.tv_name;

/**
 * Created by shy on 2018/1/9 09:39
 * Email:644563767@qq.com
 * //回访人员列表1
 */


public class RevisitListFragment1 extends BaseFragment {
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
    public void onEventMainThread(IntEvent event) {

        switch (event.getEventCode()) {

//            case 161://刷新页面
//                mCurrentPage = 1;
//                strBean.page = strBean.page = mCurrentPage - 1 + "";
//                try {
//                    find_potential_list(gson.toJson(strBean).toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case 162://最近维护
//                strBean = new StrBean(auth);
//                mCurrentPage = 1;
//                StrBean.Order order=new StrBean.Order();
//                order.setLast_time("desc");//最近维护
//                strBean.setOrder(order);
//                try {
//                    find_potential_list(gson.toJson(strBean).toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case 163://最晚维护
//                strBean = new StrBean(auth);
//                mCurrentPage = 1;
//                strBean.setPage(mCurrentPage - 1 + "");
//                StrBean.Order order1=new StrBean.Order();
//                order1.setLast_time("asc");
//                strBean.setOrder(order1);
//                try {
//                    find_potential_list(gson.toJson(strBean).toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case 164://健身指数
//                strBean = new StrBean(auth);
//                mCurrentPage = 1;
//                strBean.setPage(mCurrentPage - 1 + "");
//                StrBean.Order order2=new StrBean.Order();
//                order2.setLast_time("desc");
//                order2.setFitness_level("desc");
//                strBean.setOrder(order2);
//
//                try {
//                    find_potential_list(gson.toJson(strBean).toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case 165://关注程度
//                strBean = new StrBean(auth);
//                mCurrentPage = 1;
//                strBean.setPage(mCurrentPage - 1 + "");
//                StrBean.Order order3=new StrBean.Order();
//                order3.setLast_time("desc");//最近维护
//                order3.setFollow_level("desc");
//                strBean.setOrder(order3);
//
//                try {
//                    find_potential_list(gson.toJson(strBean).toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                break;

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
        mListView = v.findViewById(R.id.pullToRefresh);
        View    listEmptyView=v.findViewById(R.id.rl_no_info);
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
                new DownRefresh().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new UpRefresh().execute();
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
        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);//减一个月
        Date m = c.getTime();
        String date = format.format(m);
        // LogUtil.i("过去一个月："+date);

        strBean = new StrBean(auth);
        mCurrentPage = 1;
        strBean.setPage(mCurrentPage - 1 + "");
        StrBean.Order order = new StrBean.Order();
        order.setLast_time("desc");//最近维护
        strBean.setOrder(order);
        strBean.setLast_time_lt(date);//最近一个未维护
        LogUtil.i(gson.toJson(strBean).toString());
        try {
            find_potential_list(gson.toJson(strBean).toString());
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
        public Order order;
        public String last_time_lt;

        @Override
        public String toString() {
            return "StrBean{" +
                    "page='" + page + '\'' +
                    ", auth='" + auth + '\'' +
                    ", order=" + order +
                    ", last_time_lt='" + last_time_lt + '\'' +
                    '}';
        }

        public String getLast_time_lt() {
            return last_time_lt;
        }

        public void setLast_time_lt(String last_time_lt) {
            this.last_time_lt = last_time_lt;
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

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public static class Order {

            public String fitness_level;
            public String follow_level;
            public String last_time;

            @Override
            public String toString() {
                return "Order{" +
                        "fitness_level='" + fitness_level + '\'' +
                        ", follow_level='" + follow_level + '\'' +
                        ", last_time='" + last_time + '\'' +
                        '}';
            }

            public String getFitness_level() {
                return fitness_level;
            }

            public void setFitness_level(String fitness_level) {
                this.fitness_level = fitness_level;
            }

            public String getFollow_level() {
                return follow_level;
            }

            public void setFollow_level(String follow_level) {
                this.follow_level = follow_level;
            }

            public String getLast_time() {
                return last_time;
            }

            public void setLast_time(String last_time) {
                this.last_time = last_time;
            }
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

                vh.iv_sex = convertView.findViewById(R.id.iv_sex);

                vh.tv_lasttime = convertView.findViewById(R.id.tv_lasttime);

                vh.ll_item = convertView.findViewById(R.id.ll_item);

                convertView.setTag(vh);

            } else {

                vh = (ViewHolder) convertView.getTag();

            }
            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
            Glide.with(mActivity).load(datalist.get(position).getSelf_avatar_url()).apply(options).into(vh.iv_avatar);
            if (TextUtils.isEmpty(datalist.get(position).getNick_name())) {
                vh.tv_name.setText(datalist.get(position).getCname());
            } else {
                vh.tv_name.setText(datalist.get(position).getCname() + "(" + datalist.get(position).getNick_name() + ")");
            }
            if (TextUtils.equals(datalist.get(position).getGender(), "1")) {
                vh.iv_sex.setImageResource(R.drawable.img_sex1);
            }
            if (TextUtils.equals(datalist.get(position).getGender(), "2")) {
                vh.iv_sex.setImageResource(R.drawable.img_sex2);
            }
            if (datalist.get(position).getLast_time()!=null){
                vh.tv_lasttime.setText("最后维护时间：" + datalist.get(position).getLast_time());
            }else {
                vh.tv_lasttime.setText("最后维护时间：" + "最近未维护");
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
//            vh.iv_hx_msg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String userName = datalist.get(position).getNick_name();
//                    String userPic = datalist.get(position).getSelf_avatar_path();
//                    String hxIdFrom;
//                    if (Constants.DEV_CONFIG) {
//                        hxIdFrom = "dev" + datalist.get(position).getMember_no();
//                    } else {
//                        hxIdFrom = datalist.get(position).getMember_no();
//                    }
//
//                    LogUtil.i(userName + userPic + hxIdFrom);
//                    EaseUser easeUser = new EaseUser(hxIdFrom);
//                    easeUser.setAvatar(userPic);
//                    easeUser.setNick(userName);
//
//                    List<EaseUser> users = new ArrayList<EaseUser>();
//                    users.add(easeUser);
//                    if (easeUser != null) {
//                        DemoHelper.getInstance().updateContactList(users);
//                    } else {
//                        LogUtil.i("USER IS NULL");
//
//                    }
//
//                    startActivity(new Intent(mActivity, MyChatActivity.class).putExtra("userId", hxIdFrom).putExtra("chatType", EMMessage.ChatType.Chat));
//
//
//                }
//            });
            return convertView;

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
            public ImageView iv_avatar;
            public ImageView iv_hx_msg;
            public ImageView iv_callphone;
            public TextView tv_name;
            public ImageView iv_sex;
            public TextView tv_lasttime;
            public LinearLayout ll_item;
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
