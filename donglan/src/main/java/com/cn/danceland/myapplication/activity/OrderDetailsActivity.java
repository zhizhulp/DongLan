package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shy on 2017/12/29 15:39
 * Email:644563767@qq.com
 * 订单详情
 */


public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    private RequestOrderListBean.Data.Content orderInfo;
    private OrderExtendsInfoBean orderExtendsInfo;
    private Button btn_cancel;
    private Button btn_pay;
    private TextView tv_pay_status;
    private TextView tv_end_time;
    private TextView tv_pay_price_status;
    private TextView tv_pay_price;
    private TextView tv_useful_life;
    private TextView tv_price;
    private TextView tv_product_type;
    private TextView tv_product_name;
    private TextView tv_counselor;
    private TextView tv_order_time;
    private TextView tv_pay_way;
    private TextView tv_deposit_price;
    private LinearLayout ll_deposit;
    private TextView tv_employee;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        initView();
    }

    private void initView() {

        Bundle bundle = this.getIntent().getExtras();
        orderInfo = (RequestOrderListBean.Data.Content) bundle.getSerializable("orderinfo");
        orderExtendsInfo = (OrderExtendsInfoBean) bundle.getSerializable("orderExtendsInfo");
        LogUtil.i(orderInfo.toString());
        LogUtil.i(orderExtendsInfo.toString());
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_pay_status = findViewById(R.id.tv_pay_status);
        tv_end_time = findViewById(R.id.tv_end_time);
        tv_useful_life = findViewById(R.id.tv_useful_life);
        tv_price = findViewById(R.id.tv_price);
        tv_product_type = findViewById(R.id.tv_product_type);
        tv_product_name = findViewById(R.id.tv_product_name);
        tv_counselor = findViewById(R.id.tv_counselor);
        tv_order_time = findViewById(R.id.tv_order_time);
        tv_pay_way = findViewById(R.id.tv_pay_way);
        tv_deposit_price = findViewById(R.id.tv_deposit_price);
        ll_deposit = findViewById(R.id.ll_deposit);
        tv_pay_price_status = findViewById(R.id.tv_pay_price_STATUS);
        tv_pay_price = findViewById(R.id.tv_pay_price);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_pay = findViewById(R.id.btn_pay);
        tv_employee = findViewById(R.id.tv_employee);
        btn_cancel.setOnClickListener(this);
        btn_pay.setOnClickListener(this);

        if (orderInfo.getBus_type() == 31 || orderInfo.getBus_type() == 33) {
            tv_price.setText(PriceUtils.formatPrice2String(orderInfo.getReceive()));
            tv_counselor.setText(orderExtendsInfo.getAdmin_emp_name());
            tv_product_type.setText("预付定金");
            if (orderExtendsInfo.getDeposit_type() == 1) {
                tv_product_name.setText("会员卡定金");
            } else if (orderExtendsInfo.getDeposit_type() == 2) {
                tv_product_name.setText("私教定金");

            } else if (orderExtendsInfo.getDeposit_type() == 3) {
                tv_product_name.setText("租柜定金");

            }
            tv_useful_life.setText("1个月");

            if (orderInfo.getBus_type() == 33) {
                findViewById(R.id.ll_other).setVisibility(View.VISIBLE);
                TextView tv_friend_name = findViewById(R.id.tv_friend_name);
                tv_friend_name.setText(orderExtendsInfo.getMember_name());
                TextView tv_friend_phone = findViewById(R.id.tv_friend_phone);
                tv_friend_phone.setText(orderExtendsInfo.getPhone_no());
            }

        }

        if (orderInfo.getBus_type() == 32 || orderInfo.getBus_type() == 34) {
            tv_price.setText(PriceUtils.formatPrice2String(orderInfo.getReceive()));
            tv_counselor.setText(orderExtendsInfo.getSell_name());
            tv_product_type.setText("会员卡");
            if (orderExtendsInfo.getCharge_mode() == 2) {
                //如果是计次卡
                tv_product_name.setText(orderExtendsInfo.getCard_name() + "(" + orderExtendsInfo.getTotal_count() + "次)")
                ;
            } else {
                tv_product_name.setText(orderExtendsInfo.getCard_name());
            }

            tv_useful_life.setText(orderExtendsInfo.getMonth_count() + "个月");

            if (orderInfo.getBus_type() == 34) {
                findViewById(R.id.ll_other).setVisibility(View.VISIBLE);
                TextView tv_friend_name = findViewById(R.id.tv_friend_name);
                tv_friend_name.setText(orderExtendsInfo.getMember_name());
                TextView tv_friend_phone = findViewById(R.id.tv_friend_phone);
                tv_friend_phone.setText(orderExtendsInfo.getPhone_no());
            }
        }

        if (orderInfo.getBus_type() == 56 || orderInfo.getBus_type() == 57) {
            tv_price.setText(PriceUtils.formatPrice2String(orderInfo.getReceive()));
            tv_employee.setText("私教姓名:");
            tv_counselor.setText(orderExtendsInfo.getEmployee_name());
            tv_product_type.setText("私教课程");
            tv_product_name.setText(orderExtendsInfo.getCourse_type_name());
            if (orderExtendsInfo.getTime_length()!=0)
                tv_useful_life.setText(orderExtendsInfo.getTime_length() + "天");

            if (orderInfo.getBus_type() == 57) {//给别人买私教
                findViewById(R.id.ll_other).setVisibility(View.VISIBLE);
                TextView tv_friend_name = findViewById(R.id.tv_friend_name);
                tv_friend_name.setText(orderExtendsInfo.getMember_name());
                TextView tv_friend_phone = findViewById(R.id.tv_friend_phone);
                tv_friend_phone.setText(orderExtendsInfo.getPhone_no());
            }
        }


//        if (CardsInfo.getTime_unit() == 1) {
//            tv_useful_life.setText(orderExtendsInfo.get + "年");
//        }
//        if (CardsInfo.getTime_unit() == 2) {
//            tv_useful_life.setText(CardsInfo.getTime_value() + "个月");
//        }
//        tv_useful_life.setText(orderExtendsInfo.gete);
//
//
//        if (CardsInfo.getCharge_mode() == 1) {//计时卡
//            tv_product_type.setText("计时卡");
//        }
//        if (CardsInfo.getCharge_mode() == 2) {//计次卡
//            tv_product_type.setText("计次卡（" + CardsInfo.getTotal_count() + "次）");
//
//        }
//        if (CardsInfo.getCharge_mode() == 3) {//储值卡
//            tv_product_type.setText("储值卡");
//        }
//        //tv_number.setText(number + "");
//
//        tv_name.setText(CardsInfo.getName());
//
//
//        //单价
//        tv_price.setText(PriceUtils.formatPrice2String(CardsInfo.getPrice()));
//        total_price = CardsInfo.getPrice();
//
//        //总价
//        //     tv_total_price.setText(PriceUtils.formatPrice2String(CardsInfo.getPrice() * number));
//        // total_price = CardsInfo.getPrice() * number;
//
//
//        if (CardsInfo.getTime_unit() == 1) {
//            tv_useful_life.setText(CardsInfo.getTime_value() + "年");
//        }
//        if (CardsInfo.getTime_unit() == 2) {
//            tv_useful_life.setText(CardsInfo.getTime_value() + "个月");
//        }
//


        if (orderInfo.getStatus() == 1) {
            tv_pay_status.setText("订单待支付");
            tv_pay_price_status.setText("待支付金额：");
            tv_end_time.setVisibility(View.VISIBLE);
            tv_end_time.setText("剩余时间：" + TimeUtils.leftTime(orderInfo.getOrder_time()));

            btn_cancel.setVisibility(View.VISIBLE);
            btn_pay.setVisibility(View.VISIBLE);

        }
        if (orderInfo.getStatus() == 2) {
            tv_pay_status.setText("订单已支付");
            tv_pay_price_status.setText("已支付金额：");
            tv_end_time.setVisibility(View.GONE);
        }
        if (orderInfo.getStatus() == 3) {
            tv_pay_status.setText("订单已完成");
            tv_end_time.setVisibility(View.GONE);
            tv_pay_price_status.setText("已支付金额：");
        }
        if (orderInfo.getStatus() == 4) {
            tv_pay_status.setText("订单已取消");
            tv_pay_price_status.setText("待支付金额：");
            tv_end_time.setVisibility(View.GONE);
        }
        tv_pay_price.setText(PriceUtils.formatPrice2String(orderInfo.getPrice()));

        tv_order_time.setText(orderInfo.getOrder_time());
        if (orderInfo.getPay_way() == 1) {
            tv_pay_way.setText("支付宝");
        }
        if (orderInfo.getPay_way() == 2) {
            tv_pay_way.setText("微信");
        }
        if (!TextUtils.isEmpty(orderExtendsInfo.getDeposit_id())) {
            ll_deposit.setVisibility(View.VISIBLE);
            tv_deposit_price.setText("-" + PriceUtils.formatPrice2String(orderExtendsInfo.getDeposit_price()));
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_cancel:
                try {
                    cancel_order(orderInfo.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_pay:
                float price = 0;
                if (orderInfo.getBus_type() == 1) {
                    price = orderInfo.getPrice();
                }
                if (orderInfo.getBus_type() == 2) {
                    if (!TextUtils.isEmpty(orderExtendsInfo.getDeposit_id())) {
                        price = orderExtendsInfo.getSell_price() - orderExtendsInfo.getDeposit_price();
                    } else {
                        price = orderExtendsInfo.getSell_price();
                    }
                }

                if (orderInfo.getPay_way() == 1) {
                    alipay(orderInfo.getId(), price);
                }
                if (orderInfo.getPay_way() == 2) {
                    wechatPay(orderInfo.getId(), price);
                }


                break;
            default:
                break;
        }
    }


    class RequestBean {
        public String id;

    }

    class RequestOrderBean {
        public boolean success;
        public String errorMsg;
        public String data;
    }

    Gson gson = new Gson();


    /**
     * 微信支付
     */
    private void wechatPay(String id, float pay_price) {
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
                    tv_pay_status.setText("订单已完成");
                    tv_end_time.setVisibility(View.GONE);
                    tv_pay_price_status.setText("已支付金额：");
                    btn_cancel.setVisibility(View.GONE);
                    btn_pay.setVisibility(View.GONE);
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
    private void alipay(String id, float pay_price) {
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
                    tv_pay_status.setText("订单已完成");
                    tv_end_time.setVisibility(View.GONE);
                    tv_pay_price_status.setText("已支付金额：");
                    btn_cancel.setVisibility(View.GONE);
                    btn_pay.setVisibility(View.GONE);
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


    public void cancel_order(final String id) throws JSONException {

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

                    btn_cancel.setVisibility(View.GONE);
                    btn_pay.setVisibility(View.GONE);
                    tv_pay_status.setText("订单已取消");
                    tv_pay_price_status.setText("待支付金额：");
                    tv_end_time.setVisibility(View.GONE);
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
}
