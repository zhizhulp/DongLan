package com.cn.danceland.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestConsultantInfoBean;
import com.cn.danceland.myapplication.bean.RequestParamsBean;
import com.cn.danceland.myapplication.bean.RequestSellCardsInfoBean;
import com.cn.danceland.myapplication.bean.explain.Explain;
import com.cn.danceland.myapplication.bean.explain.ExplainCond;
import com.cn.danceland.myapplication.bean.explain.ExplainRequest;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.PriceUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.XCRoundRectImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by shy on 2017/11/3 09:44
 * Email:644563767@qq.com
 */


public class SellCardConfirmActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_phone;
    private EditText et_name;
    private TextView tv_select_date;
    private TextView tv_select_counselor;
    private EditText et_phone;
    private RequestSellCardsInfoBean.Data CardsInfo;
    private RequestConsultantInfoBean.Data consultantInfo;
    private Calendar cal;
    private int year, month, day;
    private List<RequestConsultantInfoBean.Data> consultantListInfo = new ArrayList<>();

    private MyPopupListAdapter myPopupListAdapter;
    // private ListPopup listPopup;
    private boolean isme = true;//是不是本人
    private String startDate;
//    private Button btn_commit;
//    private Button btn_dj_commit;
    private Data info;
    private TextView tv_shuoming;
    private RequestParamsBean requestParamsBean;
    private ImageView iv_gouaka;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_card_confirm);

        initView();
        initData();
    }

    private void initData() {
        getDate();
        // LogUtil.i(CardsInfo.getBranch_id());
        findConsultant(CardsInfo.getBranch_id());
        queryList();
        StrBean strBean = new StrBean();
        List<String> params = new ArrayList<>();
        params.add("deposit_days");
        params.add("deposit_card_min");
        params.add("deposit_card_max");
        params.add("deposit_course_min");
        params.add("deposit_course_max");
        params.add("deposit_locker_min");
        params.add("deposit_locker_max");
        strBean.setParam_keys(params);
        findParams(strBean);
    }

    //获取当前日期
    private void getDate() {
        cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);       //获取年月日时分秒

        month = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    private void initView() {
        Bundle bundle = this.getIntent().getExtras();
        CardsInfo = (RequestSellCardsInfoBean.Data) bundle.getSerializable("cardinfo");
        info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        myPopupListAdapter = new MyPopupListAdapter(this);
        //listPopup = new ListPopup(this);
        TextView tv_name = findViewById(R.id.tv_cardname);
        TextView tv_number = findViewById(R.id.tv_number);
        TextView tv_time = findViewById(R.id.tv_time);
        TextView tv_price = findViewById(R.id.tv_price);
        TextView tv_cardtype = findViewById(R.id.tv_cardtype);
        TextView tv_branch_name = findViewById(R.id.tv_branch_name);
        TextView tv_price1 = findViewById(R.id.tv_price1);
        TextView tv_cardname1 = findViewById(R.id.tv_cardname1);
        tv_shuoming = findViewById(R.id.tv_shuoming);
        XCRoundRectImageView iv_card = findViewById(R.id.iv_card);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.sijiao_card);
        Glide.with(SellCardConfirmActivity.this).load(CardsInfo.getImg_url()).apply(options).into(iv_card);

        if (CardsInfo.getCharge_mode() == 1) {//计时卡
            tv_cardtype.setText("卡类型：计时卡");
        }
        if (CardsInfo.getCharge_mode() == 2) {//计次卡
            tv_cardtype.setText("卡类型：计次卡（" + CardsInfo.getTotal_count() + "次）");
        }
        if (CardsInfo.getCharge_mode() == 3) {//储值卡
            tv_cardtype.setText("卡类型：储值卡");
        }


        tv_name.setText(CardsInfo.getName());
        tv_cardname1.setText(CardsInfo.getName());
        tv_price.setText("售价：" + PriceUtils.formatPrice2String(CardsInfo.getPrice()));
        tv_price1.setText(PriceUtils.formatPrice2String(CardsInfo.getPrice()));
        tv_branch_name.setText(CardsInfo.getBranch_name());
        if (!TextUtils.isEmpty(CardsInfo.getTotal_count())) {
            tv_number.setText("次数：" + CardsInfo.getTotal_count() + "次");
            tv_number.setVisibility(View.VISIBLE);
        } else {
            tv_number.setVisibility(View.GONE);
        }

        if (CardsInfo.getTime_unit() == 1) {
            tv_time.setText("使用时间：" + CardsInfo.getTime_value() + "年");
        }
        if (CardsInfo.getTime_unit() == 2) {
            tv_time.setText("使用时间：" + CardsInfo.getTime_value() + "月");
        }


        //    RadioGroup radioGroup = findViewById(R.id.rg_who);
        findViewById(R.id.iv_back).setOnClickListener(this);
//        btn_commit = findViewById(R.id.btn_commit);
//        btn_dj_commit = findViewById(R.id.btn_dj_commit);
        findViewById(R.id.dlbtn_commit).setOnClickListener(this);
        ll_phone = findViewById(R.id.ll_phone);

//        et_name = findViewById(R.id.et_name);
//        et_phone = findViewById(R.id.et_phone);
//        findViewById(R.id.iv_phonebook).setOnClickListener(this);


        iv_gouaka = findViewById(R.id.iv_gouaka);
        iv_gouaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isme){
                    isme=false;
                    iv_gouaka.setImageResource(R.drawable.img_gouka2);
                }else {
                    isme=true;
                    iv_gouaka.setImageResource(R.drawable.img_gouka);
                }
            }
        });



    }

    private static final int PICK_CONTACT = 0;

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
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
                                et_phone.setText(phoneNumber.trim());

                            }
                            phones.close();
                        }


                    }
                }
                break;
        }
    }

    public class StrBean {

        private List<String> param_keys;

        public void setParam_keys(List<String> param_keys) {
            this.param_keys = param_keys;
        }

        public List<String> getParam_keys() {
            return param_keys;
        }
    }

    public void findParams(StrBean strBean) {
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_PARAM_KEY), new Gson().toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                requestParamsBean = new Gson().fromJson(jsonObject.toString(), RequestParamsBean.class);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) ;
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * @方法说明:按条件查询说明须知列表
     **/
    public void queryList() {
        ExplainRequest request = new ExplainRequest();
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        ExplainCond cond = new ExplainCond();
        cond.setBranch_id(Long.valueOf(info.getPerson().getDefault_branch()));
        cond.setType(Byte.valueOf("1"));// 1 买卡须知 2 买私教须知 3 买储值须知 4 买卡说明 5 买私教说明

        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                LogUtil.i(json.toString());
                DLResult<List<Explain>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<Explain>>>() {
                }.getType());
                if (result.isSuccess()) {
                    List<Explain> list = result.getData();
                    if (list != null && list.size() > 0) {
                        tv_shuoming.setText(list.get(0).getContent());
                    }
                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        });
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

                    tv_select_counselor.setText(consultantListInfo.get(position).getCname());
                    //       listPopup.dismiss();
                }
            });

            return convertView;

        }


        class ViewHolder {
            public TextView mTextView;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.iv_phonebook://打开通讯录
//
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
//                startActivityForResult(intent, PICK_CONTACT);
//
////                Uri uri = ContactsContract.Contacts.CONTENT_URI;
////                Intent intent = new Intent(Intent.ACTION_PICK,
////                        uri);
////                startActivityForResult(intent, 0);
//                break;

            case R.id.iv_back://返回
                finish();
/*            case R.id.tv_select_counselor://选择会籍顾问

                //listPopup.showPopupWindow();
                break;
            case R.id.tv_select_date://选择日期

                DatePickerDialog dialog = new DatePickerDialog(SellCardConfirmActivity.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int month2, int day3) {
                        tv_select_date.setText(year1 + "-" + (month2 + 1) + "-" + day3);
                        startDate = tv_select_date.getText().toString();
                        if (!TimeUtils.isDateOneBigger(year + "-" + (month + 1) + "-" + day, tv_select_date.getText().toString())) {

                        } else {
                            tv_select_date.setText("*请选开卡日期");
                            ToastUtils.showToastShort("不能选择今天以前的日期");
                        }

                    }
                }, year, month, day);//后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
                dialog.show();


                break;*/
            case R.id.dlbtn_commit://全款支付

                Bundle bundle = new Bundle();
                bundle.putSerializable("cardinfo", CardsInfo);

                LogUtil.i("提交");
                if (isme) {
                    bundle.putInt("product_type", 1);

                    startActivity(new Intent(SellCardConfirmActivity.this, OrderConfirmActivity.class).putExtras(bundle));
                    finish();
                } else {
                  //  bundle.putInt("product_type", 2);
                    if (requestParamsBean != null) {
                        showPirce(requestParamsBean.getData().getDeposit_card_min(), requestParamsBean.getData().getDeposit_card_max());
                    }
                }


                break;
//            case R.id.btn_dj_commit://支付定金
//
//                if (requestParamsBean != null) {
//                    showPirce(requestParamsBean.getData().getDeposit_card_min(), requestParamsBean.getData().getDeposit_card_max());
//                }
//
//                break;
//            case value:
//                break;
            default:
                break;
        }
    }

    private void showPirce(final String min, final String max) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.edit_name, null);
        TextView dialogTitleName = dialogView.findViewById(R.id.tv_nick_name);
        dialogTitleName.setText("预付定金金额");
        final EditText ed = dialogView.findViewById(R.id.edit_name);
        ed.setHint("请输入定金额：最小" + min + "元" + "，最大" + max+ "元");
        ed.setInputType(InputType.TYPE_CLASS_PHONE);
//        ed.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Log.e("输入完点击确认执行该方法", "输入结束");
//                return false;
//            }
//        });
        builder.setView(dialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(ed.getText().toString()) ) {
                    if (Float.parseFloat(ed.getText().toString()) >=Float.parseFloat(min) && Float.parseFloat(ed.getText().toString()) <= Float.parseFloat(max)) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("cardinfo", CardsInfo);
                        bundle1.putInt("product_type", 2);
                        bundle1.putString("deposit_days",requestParamsBean.getData().getDeposit_days());
                        bundle1.putString("deposit_price",ed.getText().toString());
                        startActivity(new Intent(SellCardConfirmActivity.this, OrderConfirmActivity.class).putExtras(bundle1));
                        finish();
                    } else {
                        ToastUtils.showToastShort("输入金额不在有效范围，请重新输入");


                    }
                }
            }
        });
        builder.show();

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
                    LogUtil.i(consultantListInfo.toString());
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
