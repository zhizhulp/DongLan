package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
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
import com.cn.danceland.myapplication.bean.SijiaoOrderConfirmBean;
import com.cn.danceland.myapplication.bean.WeiXinBean;
import com.cn.danceland.myapplication.bean.explain.Explain;
import com.cn.danceland.myapplication.bean.explain.ExplainCond;
import com.cn.danceland.myapplication.bean.explain.ExplainRequest;
import com.cn.danceland.myapplication.bean.store.storetype.StoreType;
import com.cn.danceland.myapplication.bean.store.storetype.StoreTypeRequest;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.CommitButton;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vondear.rxtools.module.alipay.PayResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by feng on 2018/3/14.
 */

public class SellStoreCardActivity extends BaseActivity {
    private String unpaidOrder;
    StoreType cardid;
    LinearLayout ll_zhifu, btn_repay;
    private StoreTypeRequest request;
    private Gson gson;
    private SimpleDateFormat sdf;
    Data info;
    //StoreType storeType;
    CheckBox btn_weixin, btn_zhifubao, cb_shuoming;
    TextView storecard_tv, tv_price;
    DongLanTitleView storecard_title;
    String zhifu="0";
    public static final int SDK_PAY_FLAG = 0x1001;
    private List<RequestConsultantInfoBean.Data> consultantListInfo = new ArrayList<>();
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
                            isRepay = true;
                            btn_weixin.setClickable(false);
                            break;
                        case "5000":
                            ToastUtils.showToastShort("重复请求");
                            break;
                        case "6001":
                            ToastUtils.showToastShort("已取消支付");
                            btn_repay.setVisibility(View.VISIBLE);
                            isRepay = true;
                            btn_weixin.setClickable(false);
                            break;
                        case "6002":
                            ToastUtils.showToastShort("网络连接出错");
                            btn_repay.setVisibility(View.VISIBLE);
                            isRepay = true;
                            btn_weixin.setClickable(false);
                            break;
                        case "6004":
                            ToastUtils.showToastShort("正在处理中");
                            break;
                        default:
                            ToastUtils.showToastShort("支付失败");
                            btn_repay.setVisibility(View.VISIBLE);
                            isRepay = true;
                            btn_weixin.setClickable(false);
                            break;
                    }


                    break;
                default:
                    break;
            }

        }
    };
    private SijiaoOrderConfirmBean sijiaoOrderConfirmBean;
    private LinearLayout ll_02, ll_alipay, ll_weixin;
    private CommitButton commitButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Constants.DEV_CONFIG) {
            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//支付宝沙箱环境
        }
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.storecardbill);
        initHost();
        initView();
        queryList();
        find_pay_way();
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        if (event.getEventCode() == 40001) {
            ToastUtils.showToastShort("支付成功");
            showpayresult();
        }
        if (event.getEventCode() == 40002) {
            ToastUtils.showToastShort("支付失败");
            btn_zhifubao.setClickable(false);
            isRepay = true;
            btn_repay.setVisibility(View.VISIBLE);
        }
    }

    private void showpayresult() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SellStoreCardActivity.this);
        builder.setMessage("支付完成");
        builder.setPositiveButton("查看订单", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(SellStoreCardActivity.this, XiaoFeiRecordActivity.class));
                finish();
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void find_pay_way() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PAY_WAYS_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestPayWayBean payWayBean = new Gson().fromJson(s, RequestPayWayBean.class);
                if (payWayBean.getData().getAlipay_enable() == 1) {
                    ll_alipay.setVisibility(View.VISIBLE);
                }
                if (payWayBean.getData().getWxpay_enable() == 1) {
                    ll_weixin.setVisibility(View.VISIBLE);
                }
                if (payWayBean.getData().getXypay_enable() == 1) {

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
     * 支付宝支付
     */
    private void alipay(final String orderInfo) {

        unpaidOrder = orderInfo;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(SellStoreCardActivity.this);
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

    /**
     * @方法说明:按条件查询说明须知列表
     **/
    public void queryList() {
        ExplainRequest request = new ExplainRequest();
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        ExplainCond cond = new ExplainCond();
        cond.setBranch_id(Long.valueOf(info.getPerson().getDefault_branch()));
        cond.setType(Byte.valueOf("3"));// 1 买卡须知 2 买私教须知 3 买储值须知 4 买卡说明 5 买私教说明

        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                DLResult<List<Explain>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<Explain>>>() {
                }.getType());
                if (result.isSuccess()) {
                    List<Explain> list = result.getData();
                    if (list != null && list.size() > 0) {
                        storecard_tv.setText(list.get(0).getContent());
                    }
                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        });
    }

    private void initHost() {
        sijiaoOrderConfirmBean = new SijiaoOrderConfirmBean();
        request = new StoreTypeRequest();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cardid = (StoreType) getIntent().getSerializableExtra("item");
        info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        initWechat();
        findConsultant(info.getPerson().getDefault_branch());
//        LogUtil.i(info.toString());
//        LogUtil.i(info.getMember().getAuth());
//        LogUtil.i(info.getMember().getAdmin_emp_id() + "");
//        LogUtil.i(info.getMember().getFinal_admin_id());

        if (TextUtils.equals(info.getMember().getAuth(), "1")) {
            if (!TextUtils.isEmpty(info.getMember().getAdmin_emp_id())) {
                sijiaoOrderConfirmBean.setSell_id(info.getMember().getAdmin_emp_id());
            } else {

                findViewById(R.id.ll_huiji).setVisibility(View.VISIBLE);
            }

        }
        if (TextUtils.equals(info.getMember().getAuth(), "2")) {
            if (!TextUtils.isEmpty(info.getMember().getFinal_admin_id())) {
                sijiaoOrderConfirmBean.setSell_id(info.getMember().getFinal_admin_id());
            } else {
                findViewById(R.id.ll_huiji).setVisibility(View.VISIBLE);
            }

        }


    }

    private ListPopup listPopup;
    private TextView tv_counselor;
    private boolean isRepay = false;
    private void initView() {
        myPopupListAdapter = new MyPopupListAdapter(this);
        listPopup = new ListPopup(this);
        tv_counselor = findViewById(R.id.tv_counselor);
        findViewById(R.id.ll_counselor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPopup.showPopupWindow();
            }
        });
        storecard_title = findViewById(R.id.store_title);
        storecard_title.setTitle("充值");
        btn_weixin = findViewById(R.id.btn_weixin);
        btn_zhifubao = findViewById(R.id.btn_zhifubao);
        storecard_tv = findViewById(R.id.storecard_tv);
        tv_price = findViewById(R.id.tv_price);
        cb_shuoming = findViewById(R.id.cb_shuoming);
        btn_repay = findViewById(R.id.btn_repay);
        ll_alipay = findViewById(R.id.ll_alipay);
        ll_weixin = findViewById(R.id.ll_weixin);

        commitButton = findViewById(R.id.dlbtn_commit);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepay){
                    if ("2".equals(zhifu)) {
                        alipay(unpaidOrder);
                    } else if ("3".equals(zhifu)) {
                        wxPay(unpaidOrder);
                    }
                    return;
                }
                if ("0".equals(zhifu)){
                    ToastUtils.showToastShort("请选择支付方式");
                    return;
                }

                if (cardid != null) {
                    confirmOrder(cardid.getFace() + "");
                } else {
                    ToastUtils.showToastShort("获取面额失败");
                }


            }
        });
        btn_zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_zhifubao.setChecked(true);
                btn_weixin.setChecked(false);
                zhifu = "2";
            }
        });

        btn_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_zhifubao.setChecked(false);
                btn_weixin.setChecked(true);
                zhifu = "3";
            }
        });

        btn_repay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("2".equals(zhifu)) {
                    alipay(unpaidOrder);
                } else if ("3".equals(zhifu)) {
                    wxPay(unpaidOrder);
                }
            }
        });

        ll_zhifu = findViewById(R.id.ll_zhifu);
        ll_zhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_shuoming.isChecked()) {
                    if (cardid != null) {
                        confirmOrder(cardid.getFace() + "");
                    } else {
                        ToastUtils.showToastShort("获取面额失败");
                    }
                } else {
                    ToastUtils.showToastShort("请阅读购买说明，并同意");
                }


            }
        });
        if (cardid != null) {
            tv_price.setText("待支付：￥" + cardid.getFace() + "元");
            commitButton.setText("待支付：￥"+ cardid.getFace() + "元");
        }
        //findById();
    }

//    public void findById() {
//        Long id = null;
//        // TODO 准备数据
//        if(cardid!=null){
//            id = cardid.getId();
//        }
//        request.findById(id, new Response.Listener<String>() {
//            public void onResponse(String res) {
//                DLResult<StoreType> result = gson.fromJson(res, new TypeToken<DLResult<StoreType>>() {
//                }.getType());
//                if (result.isSuccess()) {
//                    LogUtil.e("zzf",res);
//
//                    storeType = result.getData();
//
//
//                } else {
//                    ToastUtils.showToastShort("请检查手机网络！");
//                }
//            }
//        });
//
//    }

    private void confirmOrder(String price) {


        SijiaoOrderConfirmBean.Extends_params extends_params = sijiaoOrderConfirmBean.new Extends_params();
        sijiaoOrderConfirmBean.setPay_way(zhifu);//2支付宝
        sijiaoOrderConfirmBean.setPlatform(1);
        sijiaoOrderConfirmBean.setBranch_id(Integer.valueOf(info.getPerson().getDefault_branch()));
        sijiaoOrderConfirmBean.setBus_type(16);
        extends_params.setStore_type_id(cardid.getId() + "");
        extends_params.setFace(cardid.getFace() + "");
        extends_params.setGiving(cardid.getGiving() + "");
        extends_params.setSell_id(sijiaoOrderConfirmBean.getSell_id());
        sijiaoOrderConfirmBean.setReceive(price);
        sijiaoOrderConfirmBean.setPrice(price);
        sijiaoOrderConfirmBean.setProduct_type("储值卡充值");
        sijiaoOrderConfirmBean.setProduct_name("");
        sijiaoOrderConfirmBean.setExtends_params(extends_params);
        String s = gson.toJson(sijiaoOrderConfirmBean);
        LogUtil.i(s);

        if (TextUtils.isEmpty(sijiaoOrderConfirmBean.getSell_id())) {
            ToastUtils.showToastShort("请选择一位会籍顾问");
            return;
        }

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.COMMIT_CARD_ORDER), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                RequestOrderPayInfoBean requestOrderInfoBean = gson.fromJson(jsonObject.toString(), RequestOrderPayInfoBean.class);

                if (requestOrderInfoBean.getSuccess()) {
                    ll_zhifu.setVisibility(View.GONE);
                    if (requestOrderInfoBean.getData() != null) {
                        if (requestOrderInfoBean.getData().getPayWay() == 2) {
                            alipay(requestOrderInfoBean.getData().getPay_params());
                        }
                        if (requestOrderInfoBean.getData().getPayWay() == 3) {
                            wxPay(requestOrderInfoBean.getData().getPay_params());
                        }
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
        MyApplication.getHttpQueues().add(jsonObjectRequest);
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


    private MyPopupListAdapter myPopupListAdapter;

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

                    //  consultantInfo = consultantListInfo.get(position);
                    sijiaoOrderConfirmBean.setSell_id(consultantListInfo.get(position).getId() + "");
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


    class PayBean {
        public String id;
        public String order_no;
        public float price;
        public int bus_type;
        public String member_id;


        @Override
        public String toString() {
            return "PayBean{" +
                    "id='" + id + '\'' +
                    ", order_no='" + order_no + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }


    }
}
