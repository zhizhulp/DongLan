package com.cn.danceland.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestConsultantInfoBean;
import com.cn.danceland.myapplication.bean.RequestOrderPayInfoBean;
import com.cn.danceland.myapplication.bean.RequestPayWayBean;
import com.cn.danceland.myapplication.bean.RequestSellCardsInfoBean;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.bean.WeiXinBean;
import com.cn.danceland.myapplication.bean.explain.Explain;
import com.cn.danceland.myapplication.bean.explain.ExplainCond;
import com.cn.danceland.myapplication.bean.explain.ExplainRequest;
import com.cn.danceland.myapplication.bean.store.storeaccount.StoreAccount;
import com.cn.danceland.myapplication.bean.store.storeaccount.StoreAccountCond;
import com.cn.danceland.myapplication.bean.store.storeaccount.StoreAccountRequest;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.PriceUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.CommitButton;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtools.module.alipay.PayResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import razerdp.basepopup.BasePopupWindow;

/**
 * Created by shy on 2017/12/20 10:14
 * Email:644563767@qq.com
 */

@RuntimePermissions
public class OrderConfirmActivity extends BaseActivity implements View.OnClickListener {
    private RequestSellCardsInfoBean.Data CardsInfo;
    private boolean isme;//是否是本人购买
    private int product_type;//1是卡，2是定金，3是私教，4是其他
    private int number = 1;
    private RequestConsultantInfoBean.Data consultantInfo;
    private LinearLayout ll_02,ll_alipay,ll_weixin;
    private TextView tv_explain;//
    private EditText et_grant_name;//好友名字
    private EditText et_grant_phone;//好友电话
    private List<RequestConsultantInfoBean.Data> consultantListInfo = new ArrayList<>();

    private MyPopupListAdapter myPopupListAdapter;
    private TextView tv_counselor;
    private ListPopup listPopup;
    private TextView tv_pay_price;
    private float total_price;
    private float pay_price;
    private Button btn_commit;
    private CheckBox cb_alipay;
    private CheckBox cb_wechat;
    private int pay_way = 0;//2支付宝，3微信，5储值卡
    private TextView tv_dingjin;
    private String depositId;
    private float deposit_price;
    private Button btn_repay;
    private String strBean;
    private String unpaidOrder;
    public static int ORDER_BUS_TYPE_DEPOSIT_APP = 31;// app买定金
    public static int ORDER_BUS_TYPE_CARD_OPEN_APP = 32;// APP卖卡,业务系统取卡
    private int order_bustype = 0;
    private TextView tv_useful_life;
    private TextView tv_price;
    private TextView tv_product_type;
    private TextView tv_total_price;
    public static final int SDK_PAY_FLAG = 0x1001;
    private  boolean isRepay=false;
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
                            showpayresult();

                            break;
                        case "8000":
                            ToastUtils.showToastShort("正在处理中");
                            break;
                        case "4000":
                            ToastUtils.showToastShort("订单支付失败");
                            btn_repay.setVisibility(View.VISIBLE);
                            isRepay=true;
                            break;
                        case "5000":
                            ToastUtils.showToastShort("重复请求");
                            break;
                        case "6001":
                            ToastUtils.showToastShort("已取消支付");
                            btn_repay.setVisibility(View.VISIBLE);
                            isRepay=true;
                            break;
                        case "6002":
                            ToastUtils.showToastShort("网络连接出错");
                            btn_repay.setVisibility(View.VISIBLE);
                            isRepay=true;
                            break;
                        case "6004":
                            ToastUtils.showToastShort("正在处理中");
                            break;
                        default:
                            ToastUtils.showToastShort("支付失败");
                            btn_repay.setVisibility(View.VISIBLE);
                            isRepay=true;
                            break;
                    }


                    break;
                default:
                    break;
            }

        }
    };
    private Bundle bundle;
    private CheckBox cb_chuzhjika;
    private ImageView iv_check;
    private CommitButton commitButton;


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (Constants.DEV_CONFIG) {
//            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//支付宝沙箱环境
//        }

        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_order_comfirm);
        initView();
        initData();
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        if (event.getEventCode() == 40001) {
            showpayresult();
        }
        if (event.getEventCode() == 40002) {

            btn_repay.setVisibility(View.VISIBLE);
            isRepay=true;
        }
    }

    private void initData() {
        findConsultant(CardsInfo.getBranch_id());
        if (product_type == 2) {
            //   find_deposit_days();
            //find_deposit_price();
            tv_useful_life.setText(bundle.getString("deposit_days", "30") + "天");
            LogUtil.i(PriceUtils.formatPrice2String(bundle.getString("deposit_price", "00")));
            deposit_price = Float.parseFloat(bundle.getString("deposit_price", "00"));
//            tv_useful_life.setText(deposit_days + "天");
            tv_price.setText(PriceUtils.formatPrice2String(deposit_price));
            tv_total_price.setText(PriceUtils.formatPrice2String(deposit_price * number));
            tv_pay_price.setText(PriceUtils.formatPrice2String(deposit_price * number));
            total_price = PriceUtils.formatPrice2float(deposit_price * number);
            pay_price = total_price;
        }
        queryList(CardsInfo.getBranch_id());

        getStoreCard();
        find_pay_way();
    }

    private void initView() {
        initWechat();
        bundle = this.getIntent().getExtras();
        CardsInfo = (RequestSellCardsInfoBean.Data) bundle.getSerializable("cardinfo");
        //  consultantInfo = (RequestConsultantInfoBean.Data) bundle.getSerializable("consultantInfo");
        isme = bundle.getBoolean("isme", true);
        product_type = bundle.getInt("product_type", 1);
        deposit_price = Float.parseFloat(bundle.getString("deposit_price", "00"));

        commitButton = findViewById(R.id.dlbtn_commit);
        commitButton.setOnClickListener(this);
        if (product_type == 1) {//如实卡
            order_bustype = ORDER_BUS_TYPE_CARD_OPEN_APP;

        } else if (product_type == 2) {//如果是定金
            order_bustype = ORDER_BUS_TYPE_DEPOSIT_APP;

            findViewById(R.id.ll_chuzhika).setVisibility(View.GONE);
            TextView tv_gouka=findViewById(R.id.tv_gouka);
            tv_gouka.setText("购买定金");
        }
        myPopupListAdapter = new MyPopupListAdapter(this);

        listPopup = new ListPopup(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        //  LinearLayout ll_01 = findViewById(R.id.ll_01);

        ll_02 = findViewById(R.id.ll_02);
        ll_weixin = findViewById(R.id.ll_weixin);
        ll_alipay = findViewById(R.id.ll_alipay);
        LinearLayout ll_03 = findViewById(R.id.ll_03);
        ll_03.setOnClickListener(this);


        TextView tv_name = findViewById(R.id.tv_product_name);
        // TextView tv_number = findViewById(R.id.tv_number);
        tv_useful_life = findViewById(R.id.tv_useful_life);
        tv_price = findViewById(R.id.tv_price);
        tv_product_type = findViewById(R.id.tv_product_type);
        tv_total_price = findViewById(R.id.tv_total_price);
        tv_dingjin = findViewById(R.id.tv_dingjin);
        tv_explain = findViewById(R.id.tv_explain);
        findViewById(R.id.ll_counselor).setOnClickListener(this);
        findViewById(R.id.iv_phonebook).setOnClickListener(this);
        btn_commit = findViewById(R.id.btn_commit);
        btn_repay = findViewById(R.id.btn_commit2);
        btn_repay.setOnClickListener(this);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        tv_counselor = findViewById(R.id.tv_counselor);
        et_grant_name = findViewById(R.id.et_grant_name);
        et_grant_phone = findViewById(R.id.et_grant_phone);
        tv_pay_price = findViewById(R.id.tv_pay_price);
        cb_alipay = findViewById(R.id.cb_alipay);
        cb_wechat = findViewById(R.id.cb_wechat);
        cb_chuzhjika = findViewById(R.id.cb_chuzhjika);
    //    cb_alipay.setChecked(true);//默认支付宝支付

        cb_alipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_wechat.setChecked(false);
                    pay_way = 2;
                    cb_chuzhjika.setChecked(false);
                }
//                else {
//                    cb_wechat.setChecked(true);
//
//                   // pay_way = 3;
//                }
            }
        });
        cb_wechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_alipay.setChecked(false);

                    pay_way = 3;
                    cb_chuzhjika.setChecked(false);
                }
// else {
//                    cb_alipay.setChecked(true);
//                    pay_way = 2;
//                }
            }
        });
        cb_chuzhjika.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_alipay.setChecked(false);
                    cb_wechat.setChecked(false);
                    pay_way = 5;
                    if (pay_price > chuzhika) {
                        ToastUtils.showToastShort("储值卡余额不足");
                        cb_chuzhjika.setChecked(false);
                        pay_way = 2;
                        cb_alipay.setChecked(true);
                    }

                }

            }
        });

        iv_check = findViewById(R.id.iv_check);
        iv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isme) {
                    isme = false;
                    ll_02.setVisibility(View.VISIBLE);
                    iv_check.setImageResource(R.drawable.img_check2);
                } else {
                    isme = true;
                    ll_02.setVisibility(View.GONE);
                    iv_check.setImageResource(R.drawable.img_check1);
                }
            }
        });

        if (isme) {//是否是本人
            ll_02.setVisibility(View.GONE);
            if (product_type == 1) {

                //   tv_explain.setText(R.string.explain_for_card);
            }

            if (product_type == 2) {//是定金
                //  ll_01.setVisibility(View.GONE);
                //       tv_explain.setText(R.string.explain_for_dingjin);
            }
        } else

        {//给其他人买

            if (product_type == 1) {
                //   tv_explain.setText(R.string.explain_for_card_for_other);
            }

            if (product_type == 2) {//是定金
                //   tv_explain.setText(R.string.explain_for_dingjin_for_other);
            }
        }


        //    tv_counselor.setText(consultantInfo.getCname());

        if (product_type == 1)

        {//如果商品是卡


            if (CardsInfo.getCharge_mode() == 1) {//计时卡
                tv_product_type.setText("计时卡");
            }
            if (CardsInfo.getCharge_mode() == 2) {//计次卡
                tv_product_type.setText("计次卡（" + CardsInfo.getTotal_count() + "次）");

            }
            if (CardsInfo.getCharge_mode() == 3) {//储值卡
                tv_product_type.setText("储值卡");
            }
            //tv_number.setText(number + "");

            tv_name.setText(CardsInfo.getName());


            //单价
            tv_price.setText(PriceUtils.formatPrice2String(CardsInfo.getPrice()));
            total_price = CardsInfo.getPrice();

            //总价
            //     tv_total_price.setText(PriceUtils.formatPrice2String(CardsInfo.getPrice() * number));
            // total_price = CardsInfo.getPrice() * number;


            if (CardsInfo.getTime_unit() == 1) {
                tv_useful_life.setText(CardsInfo.getTime_value() + "年");
            }
            if (CardsInfo.getTime_unit() == 2) {
                tv_useful_life.setText(CardsInfo.getTime_value() + "个月");
            }
            pay_price = total_price;
            tv_pay_price.setText(PriceUtils.formatPrice2String(pay_price));
            commitButton.setText("待支付："+PriceUtils.formatPrice2String(pay_price));

        }
        if (product_type == 2)

        {//如果商品是定金
            ll_03.setVisibility(View.GONE);
            tv_name.setText("预付定金");
            tv_product_type.setText("会籍卡定金");
            tv_useful_life.setText(deposit_days + "天");
            tv_price.setText(PriceUtils.formatPrice2String(deposit_price));
            tv_total_price.setText(PriceUtils.formatPrice2String(deposit_price * number));
            tv_pay_price.setText(PriceUtils.formatPrice2String(deposit_price * number));

            total_price = PriceUtils.formatPrice2float(deposit_price * number);
            pay_price = total_price;
            LogUtil.i(PriceUtils.formatPrice2String(deposit_price * number));
            commitButton.setText("待支付："+PriceUtils.formatPrice2String(deposit_price * number));
        }



    }

    Gson gson = new Gson();

    class DepositPrarmsBean {
        public String param_key;

    }

    private String deposit_days;

    /**
     * 查询定金有效期
     */
    private void find_deposit_days() {
        DepositPrarmsBean prarmsBean = new DepositPrarmsBean();
        prarmsBean.param_key = "deposit_days";

        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_PARAM_KEY), gson.toJson(prarmsBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestSimpleBean requsetSimpleBean = gson.fromJson(jsonObject.toString(), RequestSimpleBean.class);
                if (requsetSimpleBean.getSuccess()) {
                    deposit_days = requsetSimpleBean.getData();
                    tv_useful_life.setText(deposit_days + "天");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(request);
    }

    private float chuzhika = 0;

    private void getStoreCard() {
        StoreAccountRequest request = new StoreAccountRequest();
        StoreAccountCond cond = new StoreAccountCond();
        // TODO 准备查询条件
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                LogUtil.e("zzf", json.toString());
                DLResult<StoreAccount> result = gson.fromJson(json.toString(), new TypeToken<DLResult<StoreAccount>>() {
                }.getType());
                if (result != null && result.getData() != null) {
                    StoreAccount data = result.getData();
                    float balance = data.getRemain() + data.getGiving();
                    chuzhika = balance;
//                    if("0".equals(type)){
//                        if(balance<(price-deposit)){
//                            isStoreCard = false;
//                        }else {
//                            isStoreCard = true;
//                        }
//                    }else{
//                        isStoreCard =false;
//                    }
                } else {
                    //   isStoreCard = false;
                }
            }
        });

    }


    private void find_pay_way() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PAY_WAYS_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestPayWayBean payWayBean=new Gson().fromJson(s,RequestPayWayBean.class);
                if (payWayBean.getData().getAlipay_enable()==1){
                    ll_alipay.setVisibility(View.VISIBLE);
                }
                if (payWayBean.getData().getWxpay_enable()==1){
                    ll_weixin.setVisibility(View.VISIBLE);
                }
                if (payWayBean.getData().getXypay_enable()==1){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        });

        MyApplication.getHttpQueues().add(stringRequest);
    }

    /**
     * 查询定金金额
     */
    private void find_deposit_price() {
        DepositPrarmsBean prarmsBean = new DepositPrarmsBean();
        prarmsBean.param_key = "deposit_price";

        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_PARAM_KEY), gson.toJson(prarmsBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestSimpleBean requsetSimpleBean = gson.fromJson(jsonObject.toString(), RequestSimpleBean.class);
                if (requsetSimpleBean.getSuccess()) {

                    LogUtil.i(requsetSimpleBean.getData());
                    deposit_price = Float.parseFloat(requsetSimpleBean.getData());
                    tv_useful_life.setText(deposit_days + "天");
                    tv_price.setText(PriceUtils.formatPrice2String(deposit_price));
                    tv_total_price.setText(PriceUtils.formatPrice2String(deposit_price * number));
                    tv_pay_price.setText(PriceUtils.formatPrice2String(deposit_price * number));
                    commitButton.setText("待支付："+PriceUtils.formatPrice2String(deposit_price * number));
                    total_price = PriceUtils.formatPrice2float(deposit_price * number);
                    pay_price = total_price;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * @方法:按条件查询须知列表
     **/
    public void queryList(String branch_id) {
        ExplainRequest request = new ExplainRequest();
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        ExplainCond cond = new ExplainCond();
        cond.setBranch_id(Long.valueOf(branch_id));
        cond.setType(Byte.valueOf("4"));// 1 买卡须知 2 买私教须知 3 买储值须知 4 买卡 5 买私教

        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                DLResult<List<Explain>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<Explain>>>() {
                }.getType());
                if (result.isSuccess()) {
                    List<Explain> list = result.getData();
                    if (list != null && list.size() > 0) {
                        tv_explain.setText(list.get(0).getContent());
                    }
                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        });
    }


    /**
     * 提交卡订单
     *
     * @param str
     * @throws JSONException
     */
    public void commit_card(final String str) throws JSONException {

        JSONObject jsonObject = new JSONObject(str);

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.COMMIT_CARD_ORDER), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());

                RequestOrderPayInfoBean requestOrderInfoBean = new RequestOrderPayInfoBean();
                Gson gson = new Gson();
                requestOrderInfoBean = gson.fromJson(jsonObject.toString(), RequestOrderPayInfoBean.class);

                if (requestOrderInfoBean.getSuccess()) {
                    //  ToastUtils.showToastShort("提交成功");
                    btn_commit.setVisibility(View.GONE);
                    if (requestOrderInfoBean.getData().getPayWay() == 2) {//支付宝支付
                        alipay(requestOrderInfoBean.getData().getPay_params());
                    }
                    if (requestOrderInfoBean.getData().getPayWay() == 3) {//微信支付
                        wxPay(requestOrderInfoBean.getData().getPay_params());
                    }
                    if (requestOrderInfoBean.getData().getPayWay() == 5) {
                        chuzhika(requestOrderInfoBean.getData().getPay_params());
                    }
                } else {
                    ToastUtils.showToastShort("订单提交失败");
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
     * 提交定金订单
     *
     * @param str
     * @throws JSONException
     */
    public void commit_deposit(final String str) throws JSONException {

        JSONObject jsonObject = new JSONObject(str);

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.COMMIT_DEPOSIT), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestOrderPayInfoBean requestOrderInfoBean = new RequestOrderPayInfoBean();
                Gson gson = new Gson();
                requestOrderInfoBean = gson.fromJson(jsonObject.toString(), RequestOrderPayInfoBean.class);

                if (requestOrderInfoBean.getSuccess()) {
                    //   ToastUtils.showToastShort("提交成功");
                    btn_commit.setVisibility(View.GONE);

                    if (requestOrderInfoBean.getData().getPayWay() == 2) {//支付宝支付
                        alipay(requestOrderInfoBean.getData().getPay_params());
                    }

                    if (requestOrderInfoBean.getData().getPayWay() == 3) {//微信支付

                        wxPay(requestOrderInfoBean.getData().getPay_params());
                    }

                    if (requestOrderInfoBean.getData().getPayWay() == 5) {
                        chuzhika(requestOrderInfoBean.getData().getPay_params());
                    }
                } else {
                    ToastUtils.showToastShort("订单提交失败");
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
     * 支付宝支付
     */
    private void alipay(final String orderInfo) {
        LogUtil.i(orderInfo);
        unpaidOrder = orderInfo;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderConfirmActivity.this);
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

    class PayBean {
        public String id;
    }

    /**
     * 储值卡支付
     *
     * @param id
     */
    private void chuzhika(String id) {
        unpaidOrder = id;
        PayBean payBean = new PayBean();

        payBean.id = id;
        String str = gson.toJson(payBean);


        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.COMMIT_CHUZHIKA), str, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestSimpleBean requestSimpleBean = gson.fromJson(jsonObject.toString(), RequestSimpleBean.class);
                if (requestSimpleBean.getSuccess()) {
                    ToastUtils.showToastShort("支付成功");
                    showpayresult();

                } else {
                    if (TextUtils.equals(requestSimpleBean.getCode(), "-5") || TextUtils.equals(requestSimpleBean.getCode(), "-6") || TextUtils.equals(requestSimpleBean.getCode(), "-7") || TextUtils.equals(requestSimpleBean.getCode(), "-8")) {
                        ToastUtils.showToastShort("储值卡余额不足");
                        btn_repay.setVisibility(View.VISIBLE);
                        isRepay=true;
                    } else {
                        ToastUtils.showToastShort("支付异常");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort("支付失败");

            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String phoneNumber = null;
                        if (hasPhone.equalsIgnoreCase("1")) {
                            hasPhone = "true";
                        } else {
                            hasPhone = "false";
                        }
                        if (Boolean.parseBoolean(hasPhone)) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                                            + contactId,
                                    null,
                                    null);
                            while (phones.moveToNext()) {
                                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                //将电话放入
                                et_grant_phone.setText(phoneNumber.trim().replace(" ", ""));
                                if (TextUtils.isEmpty(et_grant_name.getText())) {
                                    et_grant_name.setText(name);
                                }

                            }
                            phones.close();
                        }


                    }
                }
                break;
            case 11:

//                LogUtil.i(data.getFloatExtra("dingjin", 0)+"");
                if (data != null) {
                    deposit_price = data.getFloatExtra("dingjin", 0);
                    if (deposit_price != 0f) {

                        if (total_price > deposit_price) {
                            depositId = data.getStringExtra("id");
                            //        LogUtil.i(depositId);
                            tv_dingjin.setText("- " + PriceUtils.formatPrice2String(deposit_price));

                            pay_price = total_price - deposit_price;
                            tv_pay_price.setText(PriceUtils.formatPrice2String(pay_price));
                            commitButton.setText("待支付："+PriceUtils.formatPrice2String(pay_price));

                        } else {
                            ToastUtils.showToastShort("定金金额必须小于商品金额");
                            depositId = "";
                            tv_dingjin.setText("未使用");
                            pay_price = total_price;
                            tv_pay_price.setText(PriceUtils.formatPrice2String(pay_price));
                            commitButton.setText("待支付："+PriceUtils.formatPrice2String(pay_price));
                        }


                    } else {
                        depositId = "";
                        tv_dingjin.setText("未使用");
                        pay_price = total_price - deposit_price;
                        tv_pay_price.setText(PriceUtils.formatPrice2String(pay_price));
                        commitButton.setText("待支付："+PriceUtils.formatPrice2String(pay_price));
                    }


                }

                break;


        }


    }

    private static final int PICK_CONTACT = 0;

    private void showpayresult() {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderConfirmActivity.this);
        builder.setMessage("支付完成");

        if (order_bustype == 32) {
            builder.setPositiveButton("查看订单", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(OrderConfirmActivity.this, MyCardActivity.class));
                    finish();
                }
            });

        }
        if (order_bustype == 34) {
            builder.setPositiveButton("查看订单", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    startActivity(new Intent(OrderConfirmActivity.this, MyCardActivity.class).putExtra("issend", 1));
                    finish();
                }
            });
        }


        builder.setNegativeButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });

        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                //   wxPay();
                finish();
                //sendPayRequest();
                break;

            case R.id.iv_phonebook://选择通讯录
                if (PermissionsUtil.hasPermission(OrderConfirmActivity.this, Manifest.permission.READ_CONTACTS)) {
                    //有权限
                    read_contacts();
                } else {
                    PermissionsUtil.requestPermission(OrderConfirmActivity.this, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            read_contacts();
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            //用户拒绝了申请
                            ToastUtils.showToastShort("没有权限");
                        }
                    }, new String[]{Manifest.permission.READ_CONTACTS}, false, null);
                }
                //read_contacts();
                break;
            case R.id.ll_counselor://选择会籍顾问

                listPopup.showPopupWindow();

                break;
            case R.id.ll_03://选择支付定金

                startActivityForResult(new Intent(OrderConfirmActivity.this, DepositActivity.class), 11);


                break;
            case R.id.btn_commit://提交订单

                if (product_type == 1) {//卡订单
                    final NewOrderInfoBean newOrderInfoBean = new NewOrderInfoBean();
                    //final OrderInfoBean orderInfoBean = new OrderInfoBean();


                    if (consultantInfo == null) {
                        ToastUtils.showToastLong("请选择会籍顾问");
                        break;
                    }
                    if (pay_way==0){
                        ToastUtils.showToastLong("请选择支付方式");
                        break;
                    }
                    newOrderInfoBean.setBranch_id(consultantInfo.getBranch_id());
                    newOrderInfoBean.setBus_type(order_bustype);
                    NewOrderInfoBean.ExtendsParams extendsParams = new NewOrderInfoBean.ExtendsParams();
                    extendsParams.setSell_id(consultantInfo.getId() + "");
                    extendsParams.setBus_type("1");
                    extendsParams.setFace_value(CardsInfo.getPrice() + "");
                    extendsParams.setBranch_name(consultantInfo.getBranch_name());
                    extendsParams.setSell_name(consultantInfo.getCname());
                    extendsParams.setMonth_count(CardsInfo.getMonth_count() + "");
                    extendsParams.setType_id(CardsInfo.getId());
                    extendsParams.setCharge_mode(CardsInfo.getCharge_mode() + "");
                    extendsParams.setType_name(CardsInfo.getName());
                    newOrderInfoBean.setProduct_type(CardsInfo.getCategory_name());
                    newOrderInfoBean.setProduct_name(CardsInfo.getName());

                    newOrderInfoBean.setPay_way(pay_way + "");
                    newOrderInfoBean.setPrice(pay_price + "");//实付
                    newOrderInfoBean.setReceive(CardsInfo.getPrice() + "");//总价
                    //佳楠专用参数
                    NewOrderInfoBean.Protocol_Params protocol_params = new NewOrderInfoBean.Protocol_Params();
                    protocol_params.admin_emp = consultantInfo.getBranch_name();
                    protocol_params.card_category_name = CardsInfo.getCategory_name();
                    if (CardsInfo.getCharge_mode() == 1) {
                        protocol_params.card_charge_mode = "包时";
                        protocol_params.card_total_count = "0";
                    } else {
                        protocol_params.card_charge_mode = "计次";
                        protocol_params.card_total_count = CardsInfo.getTotal_count();
                    }


                    if (CardsInfo.getTime_unit() == 1) {
                        protocol_params.card_term_of_validity = CardsInfo.getTime_value() + "年";
                    } else if (CardsInfo.getTime_unit() == 2) {
                        protocol_params.card_term_of_validity = CardsInfo.getTime_value() + "月";
                    } else if (CardsInfo.getTime_unit() == 3) {
                        protocol_params.card_term_of_validity = CardsInfo.getTime_value() + "天";
                    }
                    protocol_params.card_type_name = CardsInfo.getName();
                    protocol_params.order_price = pay_price + "";
                    if (CardsInfo.getRemark() == null) {
                        protocol_params.card_remark = "";
                    } else {
                        protocol_params.card_remark = CardsInfo.getRemark();
                    }

                    protocol_params.admin_emp = consultantInfo.getCname();

                    if (isme) {
                        Data data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
                        protocol_params.member_name = data.getPerson().getCname();
                        protocol_params.member_phone = data.getPerson().getPhone_no();
                        newOrderInfoBean.setFor_other(0);
                    } else {
                        newOrderInfoBean.setFor_other(1);
                        protocol_params.member_name = et_grant_name.getText().toString().trim();
                        protocol_params.member_phone = et_grant_phone.getText().toString().trim();

                        extendsParams.setMember_name(et_grant_name.getText().toString().trim());
                        extendsParams.setPhone_no(et_grant_phone.getText().toString().trim());
                    }

                    if (!TextUtils.isEmpty(depositId)) {
//                        orderInfoBean.setDeposit_id(depositId);
//                        orderInfoBean.setDeposit_price(deposit_price + "");
                        newOrderInfoBean.setDeposit_id(depositId);
                    }
                    if (CardsInfo.getCharge_mode() == 2) {//如果是计次卡
//                        orderInfoBean.setTotal_count(CardsInfo.getTotal_count());
                        extendsParams.setTotal_count(CardsInfo.getTotal_count());
                    }
                    if (isme) {
                        newOrderInfoBean.setBus_type(32);
//                        orderInfoBean.setFor_other(0);
                    } else {
                        order_bustype = 34;
                        newOrderInfoBean.setBus_type(34);//给别人买定金
//                        orderInfoBean.setFor_other(1);
//                        orderInfoBean.setName(et_grant_name.getText().toString().trim());
//                        orderInfoBean.setPhone_no(et_grant_phone.getText().toString().trim());
                    }
                    //   LogUtil.i(orderInfoBean.toString());

                    if (!isme && TextUtils.isEmpty(et_grant_name.getText().toString().trim())) {
                        ToastUtils.showToastLong("请输入好友姓名");
                        break;
                    }
                    if (!isme && TextUtils.isEmpty(et_grant_phone.getText().toString().trim())) {
                        ToastUtils.showToastLong("请输入好友手机号");
                        break;


                    }
                    if (!isme) {
                        if (StringUtils.isFirstNumeric(et_grant_name.getText().toString())) {//姓名不能以数字开头
                            ToastUtils.showToastShort(MyApplication.getContext().getResources().getString(R.string.name_no_numeric_first_text));
                            return;
                        }
                        if (StringUtils.isAllNumeric(et_grant_name.getText().toString())) {//姓名不能全数字
                            ToastUtils.showToastShort(MyApplication.getContext().getResources().getString(R.string.name_no_numeric_text));
                            return;
                        }


                    }

                    newOrderInfoBean.setExtends_params(extendsParams);
                    newOrderInfoBean.setProtocol_params(protocol_params);
                    Gson gson = new Gson();
                    strBean = gson.toJson(newOrderInfoBean);
                    LogUtil.i(strBean.toString());

                    if (pay_price - 0.01F < 0) {
                        ToastUtils.showToastLong("订单金额不能小于0.01元");
                        break;
                    }
                    try {
                        commit_card(strBean.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                if (product_type == 2) {//定金订单

                    //   final OrderInfoBean orderInfoBean = new OrderInfoBean();
                    final NewOrderInfoBean newOrderInfoBean = new NewOrderInfoBean();
                    if (consultantInfo == null) {
                        ToastUtils.showToastLong("请选择会籍顾问");
                        break;
                    }
                    newOrderInfoBean.setBranch_id(consultantInfo.getBranch_id());
                    newOrderInfoBean.setBus_type(31);
                    NewOrderInfoBean.ExtendsParams extendsParams = new NewOrderInfoBean.ExtendsParams();
                    extendsParams.setAdmin_emp_id(consultantInfo.getId() + "");
                    extendsParams.setBus_type("1");
                    extendsParams.setMoney(pay_price + "");
                    extendsParams.setAdmin_emp_name(consultantInfo.getCname());
                    extendsParams.setDeposit_type("1");//定金类型
                    newOrderInfoBean.setProduct_type("会籍卡定金");
                    newOrderInfoBean.setProduct_name("");

                    if (isme) {
                        newOrderInfoBean.setFor_other(0);
                        newOrderInfoBean.setBus_type(31);
                    } else {
                        order_bustype = 33;
                        newOrderInfoBean.setBus_type(33);//给别人买定金
                        newOrderInfoBean.setFor_other(1);
                        extendsParams.setMember_name(et_grant_name.getText().toString().trim());
                        extendsParams.setPhone_no(et_grant_phone.getText().toString().trim());
                    }

                    newOrderInfoBean.setExtends_params(extendsParams);
                    newOrderInfoBean.setPay_way(pay_way + "");
                    newOrderInfoBean.setPrice(pay_price + "");
                    newOrderInfoBean.setReceive(pay_price + "");


                    if (!isme && TextUtils.isEmpty(et_grant_name.getText().toString().trim())) {
                        ToastUtils.showToastLong("请输入好友姓名");
                        break;
                    }
                    if (!isme && TextUtils.isEmpty(et_grant_phone.getText().toString().trim())) {
                        ToastUtils.showToastLong("请输入好友手机号");
                        break;
                    }

                    Gson gson = new Gson();
                    strBean = gson.toJson(newOrderInfoBean);
                    LogUtil.i(strBean.toString());
                    if (pay_price < 0.01F) {
                        ToastUtils.showToastLong("订单金额不能小于0.01元");
                        return;
                    }
                    try {
                        commit_deposit(strBean.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                cb_alipay.setClickable(false);
                cb_wechat.setClickable(false);
                cb_chuzhjika.setClickable(false);
                break;
            case R.id.btn_commit2://重新支付

                if (pay_way == 2) {
                    //支付宝
                    //alipay(unpaidOrder);
                    alipay(unpaidOrder);
                }
                if (pay_way == 3) {
                    //微信
                    //  wechatPay(unpaidOrder);
                    wxPay(unpaidOrder);
                }
                if (pay_way == 5) {
                    chuzhika(unpaidOrder);
                }

                break;
            case R.id.dlbtn_commit:

                if (isRepay){//重新支付

                    if (pay_way == 2) {
                        //支付宝
                        //alipay(unpaidOrder);
                        alipay(unpaidOrder);
                    }
                    if (pay_way == 3) {
                        //微信
                        //  wechatPay(unpaidOrder);
                        wxPay(unpaidOrder);
                    }
                    if (pay_way == 5) {
                        chuzhika(unpaidOrder);
                    }
                    return;
                }



                if (product_type == 1) {//卡订单
                    final NewOrderInfoBean newOrderInfoBean = new NewOrderInfoBean();
                    //final OrderInfoBean orderInfoBean = new OrderInfoBean();


                    if (consultantInfo == null) {
                        ToastUtils.showToastLong("请选择会籍顾问");
                        break;
                    }
                    if (pay_way==0){
                        ToastUtils.showToastLong("请选择支付方式");
                        break;
                    }
                    newOrderInfoBean.setBranch_id(consultantInfo.getBranch_id());
                    newOrderInfoBean.setBus_type(order_bustype);
                    NewOrderInfoBean.ExtendsParams extendsParams = new NewOrderInfoBean.ExtendsParams();
                    extendsParams.setSell_id(consultantInfo.getId() + "");
                    extendsParams.setBus_type("1");
                    extendsParams.setFace_value(CardsInfo.getPrice() + "");
                    extendsParams.setBranch_name(consultantInfo.getBranch_name());
                    extendsParams.setSell_name(consultantInfo.getCname());
                    extendsParams.setMonth_count(CardsInfo.getMonth_count() + "");
                    extendsParams.setType_id(CardsInfo.getId());
                    extendsParams.setCharge_mode(CardsInfo.getCharge_mode() + "");
                    extendsParams.setType_name(CardsInfo.getName());
                    newOrderInfoBean.setProduct_type(CardsInfo.getCategory_name());
                    newOrderInfoBean.setProduct_name(CardsInfo.getName());

                    newOrderInfoBean.setPay_way(pay_way + "");
                    newOrderInfoBean.setPrice(pay_price + "");//实付
                    newOrderInfoBean.setReceive(CardsInfo.getPrice() + "");//总价
                    //佳楠专用参数
                    NewOrderInfoBean.Protocol_Params protocol_params = new NewOrderInfoBean.Protocol_Params();
                    protocol_params.admin_emp = consultantInfo.getBranch_name();
                    protocol_params.card_category_name = CardsInfo.getCategory_name();
                    if (CardsInfo.getCharge_mode() == 1) {
                        protocol_params.card_charge_mode = "包时";
                        protocol_params.card_total_count = "0";
                    } else {
                        protocol_params.card_charge_mode = "计次";
                        protocol_params.card_total_count = CardsInfo.getTotal_count();
                    }


                    if (CardsInfo.getTime_unit() == 1) {
                        protocol_params.card_term_of_validity = CardsInfo.getTime_value() + "年";
                    } else if (CardsInfo.getTime_unit() == 2) {
                        protocol_params.card_term_of_validity = CardsInfo.getTime_value() + "月";
                    } else if (CardsInfo.getTime_unit() == 3) {
                        protocol_params.card_term_of_validity = CardsInfo.getTime_value() + "天";
                    }
                    protocol_params.card_type_name = CardsInfo.getName();
                    protocol_params.order_price = pay_price + "";
                    if (CardsInfo.getRemark() == null) {
                        protocol_params.card_remark = "";
                    } else {
                        protocol_params.card_remark = CardsInfo.getRemark();
                    }

                    protocol_params.admin_emp = consultantInfo.getCname();

                    if (isme) {
                        Data data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
                        protocol_params.member_name = data.getPerson().getCname();
                        protocol_params.member_phone = data.getPerson().getPhone_no();
                        newOrderInfoBean.setFor_other(0);
                    } else {
                        newOrderInfoBean.setFor_other(1);
                        protocol_params.member_name = et_grant_name.getText().toString().trim();
                        protocol_params.member_phone = et_grant_phone.getText().toString().trim();

                        extendsParams.setMember_name(et_grant_name.getText().toString().trim());
                        extendsParams.setPhone_no(et_grant_phone.getText().toString().trim());
                    }

                    if (!TextUtils.isEmpty(depositId)) {
//                        orderInfoBean.setDeposit_id(depositId);
//                        orderInfoBean.setDeposit_price(deposit_price + "");
                        newOrderInfoBean.setDeposit_id(depositId);
                    }
                    if (CardsInfo.getCharge_mode() == 2) {//如果是计次卡
//                        orderInfoBean.setTotal_count(CardsInfo.getTotal_count());
                        extendsParams.setTotal_count(CardsInfo.getTotal_count());
                    }
                    if (isme) {
                        newOrderInfoBean.setBus_type(32);
//                        orderInfoBean.setFor_other(0);
                    } else {
                        order_bustype = 34;
                        newOrderInfoBean.setBus_type(34);//给别人买定金
//                        orderInfoBean.setFor_other(1);
//                        orderInfoBean.setName(et_grant_name.getText().toString().trim());
//                        orderInfoBean.setPhone_no(et_grant_phone.getText().toString().trim());
                    }
                    //   LogUtil.i(orderInfoBean.toString());

                    if (!isme && TextUtils.isEmpty(et_grant_name.getText().toString().trim())) {
                        ToastUtils.showToastLong("请输入好友姓名");
                        break;
                    }
                    if (!isme && TextUtils.isEmpty(et_grant_phone.getText().toString().trim())) {
                        ToastUtils.showToastLong("请输入好友手机号");
                        break;


                    }
                    if (!isme) {
                        if (StringUtils.isFirstNumeric(et_grant_name.getText().toString())) {//姓名不能以数字开头
                            ToastUtils.showToastShort(MyApplication.getContext().getResources().getString(R.string.name_no_numeric_first_text));
                            return;
                        }
                        if (StringUtils.isAllNumeric(et_grant_name.getText().toString())) {//姓名不能全数字
                            ToastUtils.showToastShort(MyApplication.getContext().getResources().getString(R.string.name_no_numeric_text));
                            return;
                        }


                    }

                    newOrderInfoBean.setExtends_params(extendsParams);
                    newOrderInfoBean.setProtocol_params(protocol_params);
                    Gson gson = new Gson();
                    strBean = gson.toJson(newOrderInfoBean);
                    LogUtil.i(strBean.toString());

                    if (pay_price - 0.01F < 0) {
                        ToastUtils.showToastLong("订单金额不能小于0.01元");
                        break;
                    }
                    try {
                        commit_card(strBean.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                if (product_type == 2) {//定金订单

                    //   final OrderInfoBean orderInfoBean = new OrderInfoBean();
                    final NewOrderInfoBean newOrderInfoBean = new NewOrderInfoBean();
                    if (consultantInfo == null) {
                        ToastUtils.showToastLong("请选择会籍顾问");
                        break;
                    }
                    newOrderInfoBean.setBranch_id(consultantInfo.getBranch_id());
                    newOrderInfoBean.setBus_type(31);
                    NewOrderInfoBean.ExtendsParams extendsParams = new NewOrderInfoBean.ExtendsParams();
                    extendsParams.setAdmin_emp_id(consultantInfo.getId() + "");
                    extendsParams.setBus_type("1");
                    extendsParams.setMoney(pay_price + "");
                    extendsParams.setAdmin_emp_name(consultantInfo.getCname());
                    extendsParams.setDeposit_type("1");//定金类型
                    newOrderInfoBean.setProduct_type("会籍卡定金");
                    newOrderInfoBean.setProduct_name("");

                    if (isme) {
                        newOrderInfoBean.setFor_other(0);
                        newOrderInfoBean.setBus_type(31);
                    } else {
                        order_bustype = 33;
                        newOrderInfoBean.setBus_type(33);//给别人买定金
                        newOrderInfoBean.setFor_other(1);
                        extendsParams.setMember_name(et_grant_name.getText().toString().trim());
                        extendsParams.setPhone_no(et_grant_phone.getText().toString().trim());
                    }

                    newOrderInfoBean.setExtends_params(extendsParams);
                    newOrderInfoBean.setPay_way(pay_way + "");
                    newOrderInfoBean.setPrice(pay_price + "");
                    newOrderInfoBean.setReceive(pay_price + "");


                    if (!isme && TextUtils.isEmpty(et_grant_name.getText().toString().trim())) {
                        ToastUtils.showToastLong("请输入好友姓名");
                        break;
                    }
                    if (!isme && TextUtils.isEmpty(et_grant_phone.getText().toString().trim())) {
                        ToastUtils.showToastLong("请输入好友手机号");
                        break;
                    }

                    Gson gson = new Gson();
                    strBean = gson.toJson(newOrderInfoBean);
                    LogUtil.i(strBean.toString());
                    if (pay_price < 0.01F) {
                        ToastUtils.showToastLong("订单金额不能小于0.01元");
                        return;
                    }
                    try {
                        commit_deposit(strBean.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                cb_alipay.setClickable(false);
                cb_wechat.setClickable(false);
                cb_chuzhjika.setClickable(false);
                break;
            default:
                break;
        }
    }


    static class NewOrderInfoBean

    {


        public int bus_type;// 业务类型
        //	byte ORDER_BUS_TYPE_DEPOSIT_APP = 31;// app买定金
        //	byte ORDER_BUS_TYPE_CARD_OPEN_APP = 32;// APP卖卡,业务系统取卡
        public String person_id;// 人主键
        public String member_id;// 会员主键
        public int branch_id;// 门店主键
        public String pay_way;// 支付方式
        public String price;// 支付金额
        public String receive;
        public int platform = 1;
        public ExtendsParams extends_params;
        private String deposit_id;//定金id
        private int for_other;//0自己1别人
        public Protocol_Params protocol_params;
        public String product_type;//产品类型名称
        public String product_name;//产品名称

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public Protocol_Params getProtocol_params(Protocol_Params protocol_params) {
            return this.protocol_params;
        }

        public void setProtocol_params(Protocol_Params protocol_params) {
            this.protocol_params = protocol_params;
        }

        @Override
        public String toString() {
            return "NewOrderInfoBean{" +
                    "bus_type=" + bus_type +
                    ", person_id='" + person_id + '\'' +
                    ", member_id='" + member_id + '\'' +
                    ", branch_id=" + branch_id +
                    ", pay_way='" + pay_way + '\'' +
                    ", price='" + price + '\'' +
                    ", receive='" + receive + '\'' +
                    ", platform=" + platform +
                    ", extends_params=" + extends_params +
                    ", deposit_id='" + deposit_id + '\'' +
                    ", for_other=" + for_other +
                    ", protocol_params=" + protocol_params +
                    '}';
        }

        public int getFor_other() {
            return for_other;
        }

        public void setFor_other(int for_other) {
            this.for_other = for_other;
        }

        public String getDeposit_id() {
            return deposit_id;
        }

        public void setDeposit_id(String deposit_id) {
            this.deposit_id = deposit_id;
        }

        public int getBus_type() {
            return bus_type;
        }

        public void setBus_type(int bus_type) {
            this.bus_type = bus_type;
        }

        public String getPerson_id() {
            return person_id;
        }

        public void setPerson_id(String person_id) {
            this.person_id = person_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public int getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public String getPay_way() {
            return pay_way;
        }

        public void setPay_way(String pay_way) {
            this.pay_way = pay_way;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getReceive() {
            return receive;
        }

        public void setReceive(String receive) {
            this.receive = receive;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public ExtendsParams getExtends_params() {
            return extends_params;
        }

        public void setExtends_params(ExtendsParams extends_params) {
            this.extends_params = extends_params;
        }

        static class Protocol_Params {

            public String member_name;
            public String member_phone;
            public String admin_emp;//会籍顾问
            public String card_type_name;
            public String card_category_name;
            public String card_charge_mode;
            public String card_term_of_validity;
            public String order_remark = "";
            public String card_remark = "";
            public String card_total_count;
            public String order_price;

            @Override
            public String toString() {
                return "Protocol_Params{" +
                        "member_name='" + member_name + '\'' +
                        ", member_phone='" + member_phone + '\'' +
                        ", admin_emp='" + admin_emp + '\'' +
                        ", card_type_name='" + card_type_name + '\'' +
                        ", card_category_name='" + card_category_name + '\'' +
                        ", card_charge_mode='" + card_charge_mode + '\'' +
                        ", card_term_of_validity='" + card_term_of_validity + '\'' +
                        ", order_remark='" + order_remark + '\'' +
                        ", card_total_count='" + card_total_count + '\'' +
                        ", card_remark='" + card_remark + '\'' +
                        ", order_price='" + order_price + '\'' +
                        '}';
            }
        }

        static class ExtendsParams {
            public String admin_emp_id;
            public String bus_type;
            public String member_id;
            public String money;
            public String remark;


            public String month_count;// 使用期限月数
            public String type_id;
            public String total_count; //次卡总次数
            public String face_value;//type.price
            public String charge_mode;
            public String sell_id;
            public String sell_name;//会藉顾问名
            public String sell_price;//=type.price-定金
            public String branch_name;// 门店名
            public String type_name;//卡名称

            public String admin_emp_name;


            //        private String card_type_id;//卡id
//        private String deposit_id;//定金id
//        private String deposit_price;//定金金额
//
            private String deposit_type;  //定金类型
//        private String card_name;//卡名称
//        private String month_count;// 使用期限月数
//        private String total_count; //次卡总次数

            //     private String member_name;//好友真实姓名
            private String phone_no;//好友电话
            private String other_name;//好友真实姓名


            @Override
            public String toString() {
                return "ExtendsParams{" +
                        "admin_emp_id='" + admin_emp_id + '\'' +
                        ", bus_type='" + bus_type + '\'' +
                        ", member_id='" + member_id + '\'' +
                        ", money='" + money + '\'' +
                        ", remark='" + remark + '\'' +
                        ", month_count='" + month_count + '\'' +
                        ", type_id='" + type_id + '\'' +
                        ", total_count='" + total_count + '\'' +
                        ", face_value='" + face_value + '\'' +
                        ", charge_mode='" + charge_mode + '\'' +
                        ", sell_id='" + sell_id + '\'' +
                        ", sell_name='" + sell_name + '\'' +
                        ", sell_price='" + sell_price + '\'' +
                        ", branch_name='" + branch_name + '\'' +
                        ", type_name='" + type_name + '\'' +
                        ", admin_emp_name='" + admin_emp_name + '\'' +
                        ", deposit_type='" + deposit_type + '\'' +
                        ", phone_no='" + phone_no + '\'' +
                        ", other_name='" + other_name + '\'' +
                        '}';
            }


            public String getOther_name() {
                return other_name;
            }

            public void setOther_name(String other_name) {
                this.other_name = other_name;
            }

            public String getMember_name() {
                return other_name;
            }

            public void setMember_name(String member_name) {
                this.other_name = member_name;
            }

            public String getPhone_no() {
                return phone_no;
            }

            public void setPhone_no(String phone_no) {
                this.phone_no = phone_no;
            }

            public String getDeposit_type() {
                return deposit_type;
            }

            public void setDeposit_type(String deposit_type) {
                this.deposit_type = deposit_type;
            }

            public String getAdmin_emp_name() {
                return admin_emp_name;
            }

            public void setAdmin_emp_name(String admin_emp_name) {
                this.admin_emp_name = admin_emp_name;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getMonth_count() {
                return month_count;
            }

            public void setMonth_count(String month_count) {
                this.month_count = month_count;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getTotal_count() {
                return total_count;
            }

            public void setTotal_count(String total_count) {
                this.total_count = total_count;
            }

            public String getFace_value() {
                return face_value;
            }

            public void setFace_value(String face_value) {
                this.face_value = face_value;
            }

            public String getCharge_mode() {
                return charge_mode;
            }

            public void setCharge_mode(String charge_mode) {
                this.charge_mode = charge_mode;
            }

            public String getSell_id() {
                return sell_id;
            }

            public void setSell_id(String sell_id) {
                this.sell_id = sell_id;
            }

            public String getSell_name() {
                return sell_name;
            }

            public void setSell_name(String sell_name) {
                this.sell_name = sell_name;
            }

            public String getSell_price() {
                return sell_price;
            }

            public void setSell_price(String sell_price) {
                this.sell_price = sell_price;
            }

            public String getBranch_name() {
                return branch_name;
            }

            public void setBranch_name(String branch_name) {
                this.branch_name = branch_name;
            }

            public String getAdmin_emp_id() {
                return admin_emp_id;
            }

            public void setAdmin_emp_id(String admin_emp_id) {
                this.admin_emp_id = admin_emp_id;
            }

            public String getBus_type() {
                return bus_type;
            }

            public void setBus_type(String bus_type) {
                this.bus_type = bus_type;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }

    @NeedsPermission({Manifest.permission.READ_CONTACTS, Manifest.permission_group.CONTACTS})
    void read_contacts() {
        //  Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        startActivityForResult(intent, PICK_CONTACT);
    }


    class ListPopup extends BasePopupWindow {


        Context context;

        public ListPopup(Context context) {
            super(context);
            ListView popup_list = (ListView) findViewById(R.id.popup_list);
            popup_list.setAdapter(myPopupListAdapter);
            this.context = context;
        }

        @Override
        protected Animation initShowAnimation() {
            return null;
        }

        @Override
        public View getClickToDismissView() {
            return getPopupWindowView();
        }

        @Override
        public View onCreatePopupView() {

            //  popupView=View.inflate(context,R.layout.popup_list_consultant,null);
            return createPopupById(R.layout.popup_list_consultant);

        }

        @Override
        public View initAnimaView() {
            return null;
        }
    }


    class MyPopupListAdapter extends BaseAdapter {
        private Context context;

        public MyPopupListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return consultantListInfo.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            //     LogUtil.i("asdasdjalsdllasjdlk");
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = View.inflate(context, R.layout.listview_item_list_consultant, null);
                vh.mTextView = (TextView) convertView.findViewById(R.id.item_tx);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.mTextView.setText(consultantListInfo.get(position).getCname());

            vh.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    consultantInfo = consultantListInfo.get(position);

                    tv_counselor.setText(consultantListInfo.get(position).getCname());
                    tv_counselor.setTextColor(Color.BLACK);
                    listPopup.dismiss();
                }
            });

            return convertView;

        }


        class ViewHolder {
            public TextView mTextView;
        }
    }

    /**
     * 查找会籍顾问
     */
    private void findConsultant(final String branchId) {


        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_CONSULTANT_URL), new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestConsultantInfoBean requestConsultantInfoBean = gson.fromJson(s, RequestConsultantInfoBean.class);
                if (requestConsultantInfoBean.getSuccess()) {
                    consultantListInfo = requestConsultantInfoBean.getData();
                    //  LogUtil.i(consultantListInfo.toString());
                    myPopupListAdapter.notifyDataSetChanged();

                } else {
                    ToastUtils.showToastShort(requestConsultantInfoBean.getErrorMsg());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort(volleyError.toString());
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("branch_id", branchId);
                return map;

            }


        };
        MyApplication.getHttpQueues().add(request);

    }
}
