package com.cn.danceland.myapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.OrderExtendsInfoBean;
import com.cn.danceland.myapplication.bean.RequestOrderListBean;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.PriceUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.cn.danceland.myapplication.R.id.pullToRefresh;
import static com.cn.danceland.myapplication.R.id.tv_product_type;

/**
 * Created by shy on 2017/12/28 13:35
 * Email:644563767@qq.com
 */


public class MyOrderActivity extends BaseActivity implements View.OnClickListener {

    private PullToRefreshListView mListView;
    private List<RequestOrderListBean.Data.Content> datalist = new ArrayList<>();
    private MyListAatapter myListAatapter;
    private Gson gson = new Gson();
    private int mCurrentPage = 1;//起始请求页
    private boolean isEnd = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        mListView = findViewById(pullToRefresh);
        myListAatapter = new MyListAatapter();
        mListView.setAdapter(myListAatapter);
        //设置下拉刷新模式both是支持下拉和上拉
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {

                TimerTask task = new TimerTask() {
                    public void run() {
                        new DownRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);


            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                TimerTask task = new TimerTask() {
                    public void run() {
                        new UpRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);


            }
        });;


        init_pullToRefresh();

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
                find_all_order(mCurrentPage);
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
                    find_all_order(mCurrentPage);
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

    private void initData() {
        mCurrentPage = 1;
        try {
            find_all_order(mCurrentPage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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


    /**
     * 确认对话框
     */
    private void showCanselDialog(final int pos) {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("是否取消订单");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {

                    cancel_order(datalist.get(pos).getId(), pos);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        dialog.show();
    }


    class RequestBean {
        public String id;


    }

    class RequestOrderBean {
        public boolean success;
        public String errorMsg;
        public String data;
    }


    public void cancel_order(final String id, final int pos) throws JSONException {

        RequestBean requestBean = new RequestBean();
        requestBean.id = id;
        JSONObject jsonObject = new JSONObject(gson.toJson(requestBean).toString());
        LogUtil.i(jsonObject.toString());
        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.CANSEL_ORDER), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestOrderBean requestOrderBean = gson.fromJson(jsonObject.toString(), RequestOrderBean.class);
                if (requestOrderBean.success) {
                    datalist.get(pos).setStatus(4);
                    myListAatapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToastShort("取消订单失败");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                ToastUtils.showToastShort(volleyError.toString());

            }
        });
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
                convertView = View.inflate(MyOrderActivity.this, R.layout.listview_item_order_list, null);

                vh.tv_branch_name = convertView.findViewById(R.id.tv_branch_name);
                vh.tv_status = convertView.findViewById(R.id.tv_status);
                vh.tv_product_type = convertView.findViewById(tv_product_type);
                vh.tv_product_name = convertView.findViewById(R.id.tv_product_name);
                vh.tv_price = convertView.findViewById(R.id.tv_price);
                vh.tv_pay_price = convertView.findViewById(R.id.tv_pay_price);
                vh.btn_cancel = convertView.findViewById(R.id.btn_cancel);
                vh.btn_pay = convertView.findViewById(R.id.btn_pay);
                vh.ll_pay = convertView.findViewById(R.id.ll_pay);
                vh.ll_item = convertView.findViewById(R.id.ll_item);

                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.tv_price.setText(PriceUtils.formatPrice2String(datalist.get(position).getPrice()));

        //    LogUtil.i(datalist.get(position).getExtends_info());
            final OrderExtendsInfoBean content = gson.fromJson(datalist.get(position).getExtends_info(), OrderExtendsInfoBean.class);
            vh.tv_branch_name.setText(content.getBranch_name());


            if (datalist.get(position).getBus_type() == 31||datalist.get(position).getBus_type() == 33) {
                vh.tv_product_type.setText("预付定金");
                vh.tv_product_name.setText("会员卡定金");
                if (content.getDeposit_type() == 1) {
                    vh.tv_product_name.setText("会员卡定金");
                } else if (content.getDeposit_type() == 2) {
                    vh.tv_product_name.setText("私教定金");
                } else if (content.getDeposit_type() == 3) {
                    vh.tv_product_name.setText("租柜定金");
                }

                //vh.tv_product_name
             //   vh.tv_pay_price.setText(PriceUtils.formatPrice2String(datalist.get(position).getPrice()));
            }
            if (datalist.get(position).getBus_type() == 32||datalist.get(position).getBus_type() == 34) {
                vh.tv_product_type.setText("会员卡 ");
                vh.tv_product_name.setText(content.getCard_name());
                vh.tv_price.setText(PriceUtils.formatPrice2String(datalist.get(position).getPrice()));
//                if (!TextUtils.isEmpty(content.getDeposit_id())&&!TextUtils.equals("0",content.getDeposit_id())) {
//                    vh.tv_pay_price.setText(PriceUtils.formatPrice2String(content.getSell_price() - content.getDeposit_price()));
//                } else {
//                    vh.tv_pay_price.setText(PriceUtils.formatPrice2String(content.getSell_price()));
//                }

            }
            if (datalist.get(position).getBus_type() == 56||datalist.get(position).getBus_type() == 57) {
                vh.tv_product_type.setText("私教课程");
                vh.tv_product_name.setText(content.getCourse_type_name());
                vh.tv_price.setText(PriceUtils.formatPrice2String(datalist.get(position).getPrice()));
//                if (!TextUtils.isEmpty(content.getDeposit_id())&&!TextUtils.equals("0",content.getDeposit_id())) {
//                    vh.tv_pay_price.setText(PriceUtils.formatPrice2String(content.getSell_price() - content.getDeposit_price()));
//                } else {
//                    vh.tv_pay_price.setText(PriceUtils.formatPrice2String(content.getSell_price()));
//                }

            }



            vh.tv_pay_price.setText(PriceUtils.formatPrice2String(datalist.get(position).getReceive()));
            if (datalist.get(position).getStatus() == 1) {
                vh.tv_status.setText("待付款");
                vh.ll_pay.setVisibility(View.VISIBLE);
            } else if (datalist.get(position).getStatus() == 2) {
                vh.tv_status.setText("已付款");
                vh.ll_pay.setVisibility(View.GONE);
            } else if (datalist.get(position).getStatus() == 3) {
                vh.tv_status.setText("已完成");
                vh.ll_pay.setVisibility(View.GONE);
            } else if (datalist.get(position).getStatus() == 4) {
                vh.tv_status.setText("已取消");
                vh.ll_pay.setVisibility(View.GONE);
            }
            //     LogUtil.i(PriceUtils.formatPrice2String(datalist.get(position).getPrice()) + PriceUtils.formatPrice2String(content.getSell_price()));


            vh.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("orderinfo", datalist.get(position));
                    bundle.putSerializable("orderExtendsInfo", content);
                    startActivity(new Intent(MyOrderActivity.this, OrderDetailsActivity.class).putExtras(bundle));

                }
            });

            vh.btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCanselDialog(position);

                }
            });
            vh.btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    float price = 0;
                    if (datalist.get(position).getBus_type() == 1) {
                        price = datalist.get(position).getPrice();
                    }
                    if (datalist.get(position).getBus_type() == 2) {
                        if (!TextUtils.isEmpty(content.getDeposit_id())) {
                            price = content.getSell_price() - content.getDeposit_price();
                        } else {
                            price = content.getSell_price();
                        }
                    }

                    if (datalist.get(position).getPay_way() == 1) {
                        alipay(datalist.get(position).getId(),price,position );
                    }
                    if (datalist.get(position).getPay_way() == 2) {
                        wechatPay(datalist.get(position).getId(),price,position );
                    }
                }
            });
            return convertView;

        }


        class ViewHolder {
            public TextView tv_branch_name;
            public TextView tv_status;
            public TextView tv_product_type;
            public TextView tv_product_name;
            public TextView tv_price;
            public TextView tv_pay_price;
            public Button btn_cancel;
            public Button btn_pay;
            public LinearLayout ll_pay;
            public LinearLayout ll_item;
        }

    }

    class StrBean {
        public String page;
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
     * 微信支付
     */
    private void wechatPay(String id, float pay_price, final int pos) {
        PayBean payBean = new PayBean();
        payBean.id = id;
        payBean.order_no = 12345 + "";
        payBean.price = pay_price;
        String str = gson.toJson(payBean);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
            LogUtil.i(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.COMMIT_WECHAT_PAY), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestSimpleBean requestSimpleBean = gson.fromJson(jsonObject.toString(), RequestSimpleBean.class);
                if (requestSimpleBean.getSuccess()) {
                    ToastUtils.showToastShort("支付成功");
                    datalist.get(pos).setStatus(2);
                    myListAatapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToastShort("支付失败");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                ToastUtils.showToastShort(volleyError.toString());

            }
        }) ;
        //   MyApplication.getHttpQueues().add(stringRequest);
    }


    class PayBean {
        public String id;
        public String order_no;
        public float price;

        @Override
        public String toString() {
            return "PayBean{" +
                    "id='" + id + '\'' +
                    ", order_no='" + order_no + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }


    }

    /**
     * 支付宝支付
     */
    private void alipay(String id, float pay_price, final int pos) {
        PayBean payBean = new PayBean();
        payBean.id = id;
        payBean.order_no = 12345 + "";
        payBean.price = pay_price;
        String str = gson.toJson(payBean);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
            LogUtil.i(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.COMMIT_ALIPAY), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestSimpleBean requestSimpleBean = gson.fromJson(jsonObject.toString(), RequestSimpleBean.class);
                if (requestSimpleBean.getSuccess()) {
                    ToastUtils.showToastShort("支付成功");
                    datalist.get(pos).setStatus(2);
                    myListAatapter.notifyDataSetChanged();

                } else {
                    ToastUtils.showToastShort("支付失败");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort(volleyError.toString());

            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }


    /**
     * 查询订单
     *
     * @param pageCount
     * @throws JSONException
     */
    public void find_all_order(final int pageCount) throws JSONException {

        StrBean strBean = new StrBean();
        strBean.page = pageCount - 1 + "";
        String s = gson.toJson(strBean);

        JSONObject jsonObject = new JSONObject(s.toString());

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_ALL_ORDER), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestOrderListBean orderinfo = new RequestOrderListBean();
                Gson gson = new Gson();
                orderinfo = gson.fromJson(jsonObject.toString(), RequestOrderListBean.class);
              //  LogUtil.i(orderinfo.getData().getLast() + "" + mCurrentPage);

                if (orderinfo.getSuccess()) {
                    if (orderinfo.getData().getLast()) {
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
                        datalist = orderinfo.getData().getContent();
                        myListAatapter.notifyDataSetChanged();
                    } else {
                        datalist.addAll(orderinfo.getData().getContent());
                        myListAatapter.notifyDataSetChanged();
                    }
                    mCurrentPage = mCurrentPage + 1;
                } else {
                    ToastUtils.showToastLong(orderinfo.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                ToastUtils.showToastShort(volleyError.toString());

            }
        }) ;
        MyApplication.getHttpQueues().add(stringRequest);
    }


}
