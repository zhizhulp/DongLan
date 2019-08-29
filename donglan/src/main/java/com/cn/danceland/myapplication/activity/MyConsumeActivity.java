package com.cn.danceland.myapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.MyConSumeBean;
import com.cn.danceland.myapplication.bean.MyConsumeCon;
import com.cn.danceland.myapplication.bean.WeiXinBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.AlertDialogCustomToHint;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.XCRoundRectImageView;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtools.module.alipay.PayResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by feng on 2018/5/2.
 */

public class MyConsumeActivity extends BaseActivity implements AbsListView.OnScrollListener {
    private String unpaidOrder;
    DongLanTitleView consume_title;
    ListView lv_consume;
    Gson gson;
    List<MyConSumeBean.Content> content;
    public static final int SDK_PAY_FLAG = 0x1001;
    int page = 0, totalPages;
    ConsumeAdapter consumeAdapter;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;

    private Dialog dialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    LogUtil.i(payResult.toString());
                    switch (payResult.getResultStatus()) {
                        case "9000":
                            ToastUtils.showToastShort("支付成功");
                            finish();
                            break;
                        case "8000":
                            ToastUtils.showToastShort("正在处理中");
                            break;
                        case "4000":
                            ToastUtils.showToastShort("订单支付失败");
                            //btn_repay.setVisibility(View.VISIBLE);
                            break;
                        case "5000":
                            ToastUtils.showToastShort("重复请求");
                            break;
                        case "6001":
                            ToastUtils.showToastShort("已取消支付");
                            //btn_repay.setVisibility(View.VISIBLE);
                            break;
                        case "6002":
                            ToastUtils.showToastShort("网络连接出错");
                            //btn_repay.setVisibility(View.VISIBLE);
                            break;
                        case "6004":
                            ToastUtils.showToastShort("正在处理中");
                            break;
                        default:
                            ToastUtils.showToastShort("支付失败");
                            //btn_repay.setVisibility(View.VISIBLE);
                            break;
                    }


                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//支付宝沙箱环境

        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_myconsume);
        initHost();
        initView();

    }

    /**
     * 支付宝支付
     */
    private void alipay(final String orderInfo) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MyConsumeActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void initView() {

        consume_title = findViewById(R.id.consume_title);
        consume_title.setTitle("我的消费");
        lv_consume = findViewById(R.id.lv_consume);
        lv_consume.setOnScrollListener(this);
        lv_consume.setAdapter(consumeAdapter);
        lv_consume.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("1".equals(content.get(position).getStatus())) {
//                    if("2".equals(content.get(position).getPay_way())){
//                        alipay(content.get(position).getPay_params());
//                    }else if("3".equals(content.get(position).getPay_way())){
//                        wxPay(content.get(position).getPay_params());
//                    }
                    showWindow(position);
                } else {
                    if (content.get(position).getRoot_opt_no() != null) {
                        startActivity(new Intent(MyConsumeActivity.this, MyConsumeAboutActivity.class).putExtra("root_opt_no", content.get(position).getRoot_opt_no()));
                    }
                }
            }
        });
        rl_error = findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(this).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("您还没有任何消费记录");

        lv_consume.setEmptyView(rl_error);

        initData(page);
    }

    private void showWindow(final int position) {
        dialog = new AlertDialogCustomToHint("确认", "取消").CreateDialog(MyConsumeActivity.this,  "支付金额："+content.get(position).getReceive() + "元", new AlertDialogCustomToHint.Click() {
            @Override
            public void ok_bt(int dialogOKB) {
                dialog.dismiss();
                if ("2".equals(content.get(position).getPay_way())) {
                    alipay(content.get(position).getPay_params());
                } else if ("3".equals(content.get(position).getPay_way())) {
                    wxPay(content.get(position).getPay_params());
                }
            }

            @Override
            public void cancle_bt(int btn_cancel) {
                dialog.dismiss();
            }
        });
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("是否继续支付");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if ("2".equals(content.get(position).getPay_way())) {
//                    alipay(content.get(position).getPay_params());
//                } else if ("3".equals(content.get(position).getPay_way())) {
//                    wxPay(content.get(position).getPay_params());
//                }
//            }
//        });
//        builder.setNegativeButton("查看相关订单", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(MyConsumeActivity.this,MyConsumeAboutActivity.class).putExtra("root_opt_no",content.get(position).getRoot_opt_no()));
//            }
//        });
//        builder.show();

    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        if (event.getEventCode() == 40001) {
            ToastUtils.showToastShort("支付成功");
            finish();
        }
        if (event.getEventCode() == 40002) {
            ToastUtils.showToastShort("支付失败");
            //btn_repay.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 微信支付
     */
    private IWXAPI api;


    /**
     * 初始化微信支付api
     */
    private void initWechat() {
        api = WXAPIFactory.createWXAPI(this, "wx530b17b3c2de2e0d", true);
        api.registerApp("wx530b17b3c2de2e0d");
    }

    /****
     * 微信支付
     * @param orderInfo 订单信息
     */
    private void wxPay(String orderInfo) {
        unpaidOrder = orderInfo;
        orderInfo = orderInfo.replaceAll("package", "packageValue");
        WeiXinBean wxOrderBean = new Gson().fromJson(orderInfo.toString(), WeiXinBean.class);
        LogUtil.i(wxOrderBean.toString());
        sendPayRequest(wxOrderBean);

    }


    /**
     * 调用微信支付
     */
    public void sendPayRequest(WeiXinBean weiXinBean) {

        PayReq req = new PayReq();
        req.appId = weiXinBean.getAppid();
        req.partnerId = weiXinBean.getPartnerid();
        //预支付订单
        req.prepayId = weiXinBean.getPrepayid();
        req.nonceStr = weiXinBean.getNoncestr();
        req.timeStamp = weiXinBean.getTimestamp() + "";
        req.packageValue = weiXinBean.getPackageValue();
        req.sign = weiXinBean.getSign();

        api.sendReq(req);
    }

    private void initData(final int nowpage) {
        MyConsumeCon myConsumeCon = new MyConsumeCon();
        myConsumeCon.setPage(nowpage);
        myConsumeCon.setSize(15);
        String s = gson.toJson(myConsumeCon);

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.MYCONSUME), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                MyConSumeBean myConSumeBean = gson.fromJson(jsonObject.toString(), MyConSumeBean.class);
                if (myConSumeBean != null && myConSumeBean.getData() != null && myConSumeBean.getData().getContent() != null) {
                    totalPages = myConSumeBean.getData().getTotalPages();
                    totalElements = myConSumeBean.getData().getTotalElements();
                    content.addAll(myConSumeBean.getData().getContent());
                    if (content != null) {
                        consumeAdapter.notifyDataSetChanged();
                        page++;
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                rl_error.setVisibility(View.VISIBLE);
                tv_error.setText("网络异常");
                Glide.with(MyConsumeActivity.this).load(R.drawable.img_error7).into(iv_error);
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    private void initHost() {
        gson = new Gson();
        content = new ArrayList<>();

        consumeAdapter = new ConsumeAdapter();
        initWechat();
    }

    private int lastVisibleItem;//最后一个可见的item
    private int totalItemCount, totalElements;//总的item

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastVisibleItem == totalItemCount && scrollState == SCROLL_STATE_IDLE && page <= totalPages && totalItemCount < totalElements) {
            initData(page);
        }
    }

    private class ConsumeAdapter extends BaseAdapter {

//        List<MyConSumeBean.Content> content;
//
//        public ConsumeAdapter(List<MyConSumeBean.Content> content) {
//            this.content = content;
//        }

        @Override
        public int getCount() {
            return content.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(MyConsumeActivity.this, R.layout.item_myconsume, null);
                viewHolder.xc_img = convertView.findViewById(R.id.xc_img);
                viewHolder.tv_type = convertView.findViewById(R.id.tv_type);
                viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
                viewHolder.tv_platform = convertView.findViewById(R.id.tv_platform);
                viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
                viewHolder.tv_status = convertView.findViewById(R.id.tv_status);
                viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
                viewHolder.item_layout_cv = convertView.findViewById(R.id.item_layout_cv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(MyConsumeActivity.this, 80f));
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(MyConsumeActivity.this, 16f), DensityUtils.dp2px(MyConsumeActivity.this, 16f), DensityUtils.dp2px(MyConsumeActivity.this, 16f), DensityUtils.dp2px(MyConsumeActivity.this, 11f));
            } else if (position == content.size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(MyConsumeActivity.this, 16f), DensityUtils.dp2px(MyConsumeActivity.this, 5f), DensityUtils.dp2px(MyConsumeActivity.this, 16f), DensityUtils.dp2px(MyConsumeActivity.this, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(MyConsumeActivity.this, 16f), DensityUtils.dp2px(MyConsumeActivity.this, 5f), DensityUtils.dp2px(MyConsumeActivity.this, 16f), DensityUtils.dp2px(MyConsumeActivity.this, 11f));
            }

            viewHolder.item_layout_cv.setLayoutParams(layoutParams);

            MyConSumeBean.Content contentItem = content.get(position);

            switch (contentItem.getPay_way()) {
                case "1"://现金
                    Glide.with(MyConsumeActivity.this).load(R.drawable.cash_logo).into(viewHolder.xc_img);
                    break;
                case "2":
                    Glide.with(MyConsumeActivity.this).load(R.drawable.alipay_logo).into(viewHolder.xc_img);
                    break;
                case "3":
                    Glide.with(MyConsumeActivity.this).load(R.drawable.wechat_logo).into(viewHolder.xc_img);
                    break;
                case "4"://刷卡
                    Glide.with(MyConsumeActivity.this).load(R.drawable.zhifu_card).into(viewHolder.xc_img);
                    break;
                case "5":
                    Glide.with(MyConsumeActivity.this).load(R.drawable.chuzhi_card).into(viewHolder.xc_img);
                    break;
            }

            viewHolder.tv_type.setText(contentItem.getProduct_type());
            if (contentItem.getProduct_name() != null) {
                viewHolder.tv_name.setText(contentItem.getProduct_name());
            } else {
                viewHolder.tv_name.setText("");
            }

            if ("3".equals(contentItem.getPlatform())) {
                viewHolder.tv_platform.setText("PC端");
            } else if ("1".equals(contentItem.getPlatform()) || "2".equals(contentItem.getPlatform())) {
                viewHolder.tv_platform.setText("App端");
            } else if ("4".equals(contentItem.getPlatform())) {
                viewHolder.tv_platform.setText("微信公众号");
            }

            viewHolder.tv_price.setText("金额: " + contentItem.getReceive() + "元");
            viewHolder.tv_status.setTextColor(MyConsumeActivity.this.getResources().getColor(R.color.colorGray22));
            switch (contentItem.getStatus()) {
                case "1":
                    viewHolder.tv_status.setText("未支付");
                    viewHolder.tv_status.setTextColor(MyConsumeActivity.this.getResources().getColor(R.color.home_enter_total_text_color));
                    break;
                case "2":
                    viewHolder.tv_status.setText("未发货");
                    viewHolder.tv_status.setTextColor(MyConsumeActivity.this.getResources().getColor(R.color.colorGray22));
                    break;
                case "3":
                    viewHolder.tv_status.setText("已完成");
                    viewHolder.tv_status.setTextColor(MyConsumeActivity.this.getResources().getColor(R.color.colorGray22));
                    break;
                case "4":
                    viewHolder.tv_status.setText("已取消");
                    viewHolder.tv_status.setTextColor(MyConsumeActivity.this.getResources().getColor(R.color.colorGray22));
                    break;
                case "5":
                    viewHolder.tv_status.setText("已退");
                    viewHolder.tv_status.setTextColor(MyConsumeActivity.this.getResources().getColor(R.color.colorGray22));
                    break;

            }
            String payTime = TimeUtils.timeStamp2Date(contentItem.getPay_time(), "yyyy-MM-dd HH:mm:ss");
            viewHolder.tv_time.setText(contentItem.getOrder_time());

            return convertView;
        }
    }

    private class ViewHolder {
        XCRoundRectImageView xc_img;
        TextView tv_type, tv_name, tv_platform, tv_price, tv_status, tv_time;
        CardView item_layout_cv;
    }
}
