package com.cn.danceland.myapplication.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequsetHYTJBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shy on 2018/8/24 11:11
 * Email:644563767@qq.com
 */


public class HuiYuanTuiJianActivty extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView pullToRefresh;
    List<RequsetHYTJBean.Content> datalist = new ArrayList<RequsetHYTJBean.Content>();
    ProgressDialog dialog;
    private int mCurrentPage = 0;//当前请求页
    private String userId;
    private String msgId;
    private boolean isdyn = false;
    private TextView tv_tiltle;
    int type = 1;//1是关注，2是粉丝，3是点赞。默认是1
    private ImageView imageView;
    private TextView tv_error;

    MyListAatapter myListAatapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hytj);
        initData();
        initView();
    }

    private void initData() {
        find_users(0);
    }


    private void initView() {
        tv_tiltle = findViewById(R.id.tv_tiltle);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        //   View listEmptyView = View.inflate(this, R.layout.no_info_layout, (ViewGroup) pullToRefresh.getRefreshableView().getParent());
        View listEmptyView = findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        imageView.setImageResource(R.drawable.img_error);

        pullToRefresh.getRefreshableView().setEmptyView(listEmptyView);
        dialog = new ProgressDialog(this);

        dialog.setMessage("正在加载……");
        findViewById(R.id.iv_back).setOnClickListener(this);

        myListAatapter = new MyListAatapter();
        pullToRefresh.setAdapter(myListAatapter);
        //加入头布局
        //  pullToRefresh.getRefreshableView().addHeaderView(initHeadview());

        //设置下拉刷新模式both是支持下拉和上拉
        pullToRefresh.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        init();


        pullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {


                TimerTask task = new TimerTask() {
                    public void run() {
                        new FinishRefresh().execute();    //execute the task
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);

            }
        });


    }

    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if (!isEnd) {
                find_users(mCurrentPage);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            myListAatapter.notifyDataSetChanged();
            pullToRefresh.onRefreshComplete();
        }
    }

    private void init() {
        // 设置下拉刷新文本
        ILoadingLayout startLabels = pullToRefresh
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在加载...");// 刷新时
        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
        // 设置上拉刷新文本
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在加载...");// 刷新时
        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }

    }

    class StrBean {
        public String page;

    }

    Gson gson = new Gson();

    /**
     * 查询
     *
     * @param pageCount
     */
    public void find_users(final int pageCount) {


        StrBean strBean = new StrBean();
        strBean.page = pageCount + "";

        String s = gson.toJson(strBean);
        LogUtil.i(gson.toJson(strBean).toString());

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.INTRODUCE_QUERYPAGEBYEMPLOYEE), gson.toJson(strBean).toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequsetHYTJBean requsetHYTJBean = gson.fromJson(jsonObject.toString(), RequsetHYTJBean.class);
                if (mCurrentPage == 0) {
                    datalist = requsetHYTJBean.getContent();
                    mCurrentPage++;
                } else {
                    datalist.addAll(requsetHYTJBean.getContent());
                }
                if (requsetHYTJBean.getLast()) {
                    setEnd();
                }

                myListAatapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
                //  ToastUtils.showToastShort(volleyError.toString());
                imageView.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private boolean isEnd;

    private void setEnd() {
        isEnd = true;//没数据了
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("—我是有底线的—");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("—我是有底线的—");// 刷新时
        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
        endLabels.setLoadingDrawable(null);
        pullToRefresh.setMode(PullToRefreshBase.Mode.DISABLED);
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

                convertView = View.inflate(HuiYuanTuiJianActivty.this, R.layout.listview_item_hytj, null);


                vh.tv_phone = convertView.findViewById(R.id.tv_phone);

                vh.tv_name = convertView.findViewById(R.id.tv_name);

                vh.tv_type = convertView.findViewById(R.id.tv_type);

                vh.tv_time = convertView.findViewById(R.id.tv_time);

                vh.ll_item = convertView.findViewById(R.id.ll_item);
                vh.tv_phone1 = convertView.findViewById(R.id.tv_phone1);
                vh.iv_callphone = convertView.findViewById(R.id.iv_callphone);
                vh.tv_username = convertView.findViewById(R.id.tv_username);
                convertView.setTag(vh);

            } else {

                vh = (ViewHolder) convertView.getTag();

            }
            vh.tv_name.setText(datalist.get(position).getMember_name());

            vh.tv_username.setText(datalist.get(position).getName());
            vh.tv_phone.setText(datalist.get(position).getMember_phone());
            vh.tv_username.setText(datalist.get(position).getName());
            vh.tv_phone1.setText(datalist.get(position).getPhone_no());
            if (datalist.get(position).getStatus() == 1) {
                vh.tv_type.setText("推荐成功");
            } else if(datalist.get(position).getStatus() == 2){
                vh.tv_type.setText("失败");
            }else if (datalist.get(position).getStatus() == 0){
                vh.tv_type.setText("推荐中");
            }
            vh.tv_time.setText(TimeUtils.timeStamp2Date(datalist.get(position).getCreate_date() + "", "yyyy-MM-dd"));

            vh.iv_callphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (PermissionsUtil.hasPermission(HuiYuanTuiJianActivty.this, Manifest.permission.CALL_PHONE)) {
                        //有权限
                        showDialog(datalist.get(position).getPhone_no(), position);
                    } else {
                        PermissionsUtil.requestPermission(HuiYuanTuiJianActivty.this, new PermissionListener() {
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

            return convertView;

        }


        /**
         * 提示
         */
        private void showDialog(final String phoneNo, final int pos) {


//            String callHistoryListStr = CallLogUtils.getCallHistoryList(null, cr);
//            LogUtil.i(callHistoryListStr.toString());
//            LogUtil.i(CallLogUtils.getLastCallHistoryDuration(null,cr)+"");
            AlertDialog.Builder dialog =
                    new AlertDialog.Builder(HuiYuanTuiJianActivty.this);
            dialog.setTitle("提示");
            dialog.setMessage("是否呼叫" + phoneNo);
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    call(phoneNo);


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
            if (ActivityCompat.checkSelfPermission(HuiYuanTuiJianActivty.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);

        }


        class ViewHolder {
            public TextView tv_type;
            public TextView tv_name;
            public TextView tv_username;
            public TextView tv_phone;
            public TextView tv_time;
            public TextView tv_phone1;
            public ImageView iv_callphone;
            public LinearLayout ll_item;

        }

    }

}
