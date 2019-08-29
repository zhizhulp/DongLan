package com.cn.danceland.myapplication.fragment;

import android.os.AsyncTask;
import android.text.TextUtils;
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
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequsetRevisiterRecordListBean;
import com.cn.danceland.myapplication.evntbus.IntEvent;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragmentEventBus;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.cn.danceland.myapplication.R.id.tv_lasttime;
import static com.cn.danceland.myapplication.R.id.tv_name;

/**
 * Created by shy on 2018/1/11 17:18
 * Email:644563767@qq.com
 * 回访记录
 */


public class RevisiterRecordAllFragment extends BaseFragmentEventBus {

    private PullToRefreshListView mListView;
    private List<RequsetRevisiterRecordListBean.Data.Content> datalist = new ArrayList<>();

    private MyListAatapter myListAatapter;

    private Gson gson = new Gson();
    private int mCurrentPage = 1;//起始请求页
    private boolean isEnd = false;
    private String auth;
    private TextView tv_error;
    private ImageView imageView;
    private String searchInfo;

    @Override
    public View initViews() {
        View v = View.inflate(mActivity, R.layout.fragment_revisiter_record, null);
//        v.findViewById(R.id.btn_add).setOnClickListener(this);
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

                TimerTask task = new TimerTask(){
                    public void run(){
                        new DownRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                TimerTask task = new TimerTask(){
                    public void run(){
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


    @Override
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {


            case 210://刷新页面
                mCurrentPage = 1;
                searchInfo=event.getMsg();
                try {
                    find_record_list(auth, mCurrentPage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void initDta() {
        auth = getArguments().getString("auth");
        try {
            find_record_list(auth, mCurrentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_add:
//
//                break;
            default:
                break;
        }
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
     * 下拉刷新
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            init_pullToRefresh();
            mCurrentPage = 1;
            try {
                find_record_list(auth, mCurrentPage);
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
                try {
                    find_record_list(auth, mCurrentPage);
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


    class StrBean {
        public String page;
        public String auth;
        // public String member_id;
        public String operate_id;
        public String searchInfo;
    }


    /**
     * 查询回访记录
     *
     * @param pageCount
     * @throws JSONException
     */
    public void find_record_list(final String auth, final int pageCount) throws JSONException {


        StrBean strBean = new StrBean();
        strBean.page = pageCount - 1 + "";
        strBean.auth = auth;
        strBean.searchInfo = searchInfo;
        strBean.operate_id = ((Data) DataInfoCache.loadOneCache(Constants.MY_INFO)).getEmployee().getId() + "";
        String s = gson.toJson(strBean);
        LogUtil.i(s);
        JSONObject jsonObject = new JSONObject(s.toString());
        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_VISIT_RECORD), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequsetRevisiterRecordListBean potentialListBean = new RequsetRevisiterRecordListBean();
                potentialListBean = gson.fromJson(jsonObject.toString(), RequsetRevisiterRecordListBean.class);

                myListAatapter.notifyDataSetChanged();

                if (potentialListBean.getSuccess()) {

                    if (potentialListBean.getData() != null) {
                        EventBus.getDefault().post(new IntEvent(potentialListBean.getData().getTotalElements(), 162));
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

                    if (pageCount == 1) {
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
        }) ;
        MyApplication.getHttpQueues().add(stringRequest);
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

                convertView = View.inflate(mActivity, R.layout.listview_item_all_record_list, null);


                vh.tv_content = convertView.findViewById(R.id.tv_content);

                vh.tv_name = convertView.findViewById(tv_name);

                vh.tv_type = convertView.findViewById(R.id.tv_type);

                vh.tv_time = convertView.findViewById(R.id.tv_time);

                vh.ll_item = convertView.findViewById(R.id.ll_item);
                vh.tv_lasttime = convertView.findViewById(tv_lasttime);
                vh.tv_result = convertView.findViewById(R.id.tv_result);
                vh.tv_username = convertView.findViewById(R.id.tv_username);
                vh.tv_member = convertView.findViewById(R.id.tv_member_no);
                convertView.setTag(vh);

            } else {

                vh = (ViewHolder) convertView.getTag();

            }

            vh.tv_name.setText(datalist.get(position).getOperate_name());
            vh.tv_type.setText(datalist.get(position).getType());
            vh.tv_result.setText("回访结果："+datalist.get(position).getResult());
            if (TextUtils.equals(datalist.get(position).getType(), "电话")) {

                vh.tv_time.setVisibility(View.VISIBLE);
                if (datalist.get(position).getLength() > 59) {
                    vh.tv_time.setText(datalist.get(position).getLength() / 60 + "分钟" + datalist.get(position).getLength() % 60 + "秒");
                } else {
                    vh.tv_time.setText(datalist.get(position).getLength() % 60 + "秒");
                }

            } else {
                vh.tv_time.setVisibility(View.GONE);
            }
            vh.tv_content.setText(datalist.get(position).getContent());
            //    LogUtil.i(      TimeUtils.timeStamp2Date(TimeUtils.date2TimeStamp(datalist.get(position).getMaintain_time(),"yyyy-MM-dd HH:mm:ss")+"","yy-MM-dd HH:mm"));

            vh.tv_lasttime.setText(TimeUtils.timeStamp2Date(TimeUtils.date2TimeStamp(datalist.get(position).getMaintain_time(), "yyyy-MM-dd HH:mm:ss") + "", "yy.MM.dd HH:mm"));
            vh.tv_member.setText(datalist.get(position).getMember_no());
            vh.tv_username.setText(datalist.get(position).getMember_name());
            return convertView;

        }

        class ViewHolder {
            public TextView tv_content;
            public TextView tv_type;
            public TextView tv_name;
            public TextView tv_username;
            public TextView tv_member;
            public TextView tv_time;
            public TextView tv_result;
            public TextView tv_lasttime;
            public LinearLayout ll_item;

        }

    }


}
