package com.cn.danceland.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestMyYeWuBean;
import com.cn.danceland.myapplication.bean.RequsetPotentialListBean;
import com.cn.danceland.myapplication.evntbus.IntEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shy on 2018/1/11 17:18
 * Email:644563767@qq.com
 */


public class YeWuOfMeFragment extends BaseFragment {

    private PullToRefreshListView mListView;
    private List<RequestMyYeWuBean.Data.Content> datalist = new ArrayList<>();

    private MyListAatapter myListAatapter;

    private Gson gson = new Gson();
    private int mCurrentPage = 1;//起始请求页
    private boolean isEnd = false;
    private String id;
    private String yewu_type = "";//1是定金业务，2是储值业务，3是卡业务，4是租柜业务，5是私教业务
    private String auth = "1";
    private TextView tv_error;
    private ImageView imageView;
    Map<Integer,String> yewumap=new HashMap<>();
    private Data data;

    @Override
    public View initViews() {
        View v = View.inflate(mActivity, R.layout.fragment_upcoming_matter_list, null);
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
        id = getArguments().getString("id");
        auth = getArguments().getString("auth");

        mListView.getRefreshableView().addHeaderView(addheaderview());
        info= (RequsetPotentialListBean.Data.Content) getArguments().getSerializable("info");
        setHeaderview(info);

        return v;
    }
    private RequsetPotentialListBean.Data.Content info;


    private void setHeaderview(RequsetPotentialListBean.Data.Content info) {
        if (!TextUtils.isEmpty(info.getSelf_avatar_url())) {
            RequestOptions options = new RequestOptions()
                    .transform(new GlideRoundTransform(mActivity,10)).placeholder(R.drawable.img_avatar1).error(R.drawable.img_avatar1);
            String S = info.getAvatar_url();
            Glide.with(mActivity).load(S).apply(options).into(iv_avatar);
        }

        if (TextUtils.equals(info.getGender(), "1")) {
            iv_sex.setImageResource(R.drawable.img_sex1);
        }
        if (TextUtils.equals(info.getGender(), "2")) {
            iv_sex.setImageResource(R.drawable.img_sex2);
        }
        tv_name.setText(info.getCname());
        if (info.getLast_time()!=null){
            tv_lasttime.setText("最后维护时间：" + info.getLast_time());
        }else {
            tv_lasttime.setText("最后维护时间：" + "最近未维护");
        }

        //会籍或会籍主管
        if (SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_HUIJIGUWEN || SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_HUIJIZHUGUANG) {
            if (TextUtils.isEmpty(info.getAdmin_mark())){
                tv_biaoqian.setText(info.getAdmin_mark());
            }else {
                tv_biaoqian.setText("("+info.getAdmin_mark()+")");
            }


        }
        //教练或教练主管
        if (SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIAN || SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIANZHUGUAN) {
            if (TextUtils.isEmpty(info.getTeach_mark())){
                tv_biaoqian.setText(info.getTeach_mark());
            }else {
                tv_biaoqian.setText("("+info.getTeach_mark()+")");
            }
        }

    }

    private ImageView iv_avatar;
    private ImageView iv_more;
    private TextView tv_biaoqian;
    private ImageView iv_sex;
    private TextView tv_name;
    private TextView tv_lasttime;

    private View addheaderview() {
        View v = View.inflate(mActivity, R.layout.listview_header_qianke, null);
        iv_avatar = v.findViewById(R.id.iv_avatar);
        iv_more = v.findViewById(R.id.iv_more);
        tv_biaoqian = v.findViewById(R.id.tv_biaoqian);
        iv_sex = v.findViewById(R.id.iv_sex);
        tv_name = v.findViewById(R.id.tv_name);
        tv_lasttime = v.findViewById(R.id.tv_lasttime);
        v.findViewById(R.id.iv_more).setVisibility(View.GONE);
        return v;

    }


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

            case 171://刷新页面
                mCurrentPage = 1;
                try {
                    find_upcoming_list(id, mCurrentPage, yewu_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 172://

                mCurrentPage = 1;
                yewu_type = "1";
                try {
                    find_upcoming_list(id, mCurrentPage, yewu_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 173://
                mCurrentPage = 1;
                yewu_type = "2";
                try {
                    find_upcoming_list(id, mCurrentPage, yewu_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 174://
                mCurrentPage = 1;
                yewu_type = "3";
                try {
                    find_upcoming_list(id, mCurrentPage, yewu_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 175://
                mCurrentPage = 1;
                yewu_type = "4";
                try {
                    find_upcoming_list(id, mCurrentPage, yewu_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 176://
                mCurrentPage = 1;
                yewu_type = "5";
                try {
                    find_upcoming_list(id, mCurrentPage, yewu_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 177://全部
                mCurrentPage = 1;
                yewu_type = "";
                try {
                    find_upcoming_list(id, mCurrentPage, yewu_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }


    @Override
    public void initData() {

        yewumap.put(11,"买定金");
        yewumap.put(12,"用定金");
        yewumap.put(13,"退定金");
        yewumap.put(14,"充储值");
        yewumap.put(15,"退储值");
        yewumap.put(100,"花储值");
        yewumap.put(21,"买卡");
        yewumap.put(22,"卡升级");
        yewumap.put(23,"续卡");
        yewumap.put(24,"补卡");
        yewumap.put(25,"转卡");
        yewumap.put(26,"退卡");
        yewumap.put(27,"停卡");
        yewumap.put(28,"卡延期");
        yewumap.put(29,"卡挂失");
        yewumap.put(30,"卡加次");
        yewumap.put(41,"租柜");
        yewumap.put(42,"续柜");
        yewumap.put(43,"退柜");
        yewumap.put(44,"转柜");
        yewumap.put(45,"换柜");
        yewumap.put(51,"购买私教");
        yewumap.put(52,"私教转会员");
        yewumap.put(53,"私教换教练");
        yewumap.put(61,"退私教");

        yewumap.put(1,"定金业务");
        yewumap.put(2,"储值业务");
        yewumap.put(3,"卡业务");
        yewumap.put(4,"租柜业务");
        yewumap.put(5,"私教业务");

        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);

        mCurrentPage = 1;
        //  status=null;
        try {
            find_upcoming_list(id, mCurrentPage, yewu_type);
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
                find_upcoming_list(id, mCurrentPage, yewu_type);
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
                    find_upcoming_list(id, mCurrentPage, yewu_type);
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




    /**
     * 查询待办
     *
     * @param pageCount
     * @throws JSONException
     */
    public void find_upcoming_list(final String id, final int pageCount, final String big_type) throws JSONException {



        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.YEWU_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestMyYeWuBean potentialListBean = new RequestMyYeWuBean();
                potentialListBean = gson.fromJson(s, RequestMyYeWuBean.class);

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

                LogUtil.e(volleyError.toString());
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("member_id", id);
                map.put("page", pageCount - 1 + "");
                map.put("size", 10 + "");
                map.put("big_type", big_type);
                map.put("operater_id", data.getEmployee().getId()+"");

                LogUtil.i(map.toString());

                return map;
            }
        };


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

                convertView = View.inflate(mActivity, R.layout.listview_item_my_yewu, null);

                vh.ll_code = convertView.findViewById(R.id.ll_code);
                vh.ll_admin = convertView.findViewById(R.id.ll_admin);

                vh.tv_name = convertView.findViewById(R.id.tv_name);

                vh.tv_type = convertView.findViewById(R.id.tv_type);

                vh.ll_item = convertView.findViewById(R.id.ll_item);
                vh.tv_lasttime = convertView.findViewById(R.id.tv_lasttime);
                vh.tv_money = convertView.findViewById(R.id.tv_money);
                vh.tv_admin_name = convertView.findViewById(R.id.tv_admin_name);
                vh.tv_code = convertView.findViewById(R.id.tv_code);


                convertView.setTag(vh);

            } else {

                vh = (ViewHolder) convertView.getTag();

            }





            vh.tv_name.setText(datalist.get(position).getOperater_name());

            vh.tv_type.setText(yewumap.get(datalist.get(position).getBig_type()) + "、" + yewumap.get(datalist.get(position).getType()));
            vh.tv_money.setText("¥" + datalist.get(position).getMoney());
            vh.tv_lasttime.setText(TimeUtils.timeStamp2Date(datalist.get(position).getDeal_time() + "", "yyyy-MM-dd HH:mm"));
            if (!TextUtils.isEmpty(datalist.get(position).getCode())) {
                vh.ll_code.setVisibility(View.VISIBLE);
                vh.tv_code.setText(datalist.get(position).getCode());
            } else {
                vh.ll_code.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(datalist.get(position).getEmployee_name())) {
                vh.ll_admin.setVisibility(View.VISIBLE);
                vh.tv_admin_name.setText(datalist.get(position).getEmployee_name());
            } else {
                vh.ll_admin.setVisibility(View.GONE);
            }


            return convertView;

        }


        class ViewHolder {
            public TextView tv_money;
            public TextView tv_type;
            public TextView tv_name;
            public TextView tv_lasttime;
            public TextView tv_admin_name;
            public TextView tv_code;
            public LinearLayout ll_item;
            public LinearLayout ll_code;
            public LinearLayout ll_admin;

        }

    }


}
