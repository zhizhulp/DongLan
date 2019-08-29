package com.cn.danceland.myapplication.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestSellCardsInfoBean;
import com.cn.danceland.myapplication.bean.RequestSellCardsTypeBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.PriceUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.cn.danceland.myapplication.R.id.iv_card;
import static com.cn.danceland.myapplication.R.id.tv_cardname;
import static com.cn.danceland.myapplication.R.id.tv_cardtype;

/**
 * Created by shy on 2017/11/2 16:37
 * Email:644563767@qq.com
 */


public class SellCardActivity extends BaseActivity implements View.OnClickListener {


    private ImageView iv_fenlie;
    private LinearLayout ll_fenlie;
   private TextView tv_fenlie;
    private ListView listView;
    private RequestSellCardsTypeBean sellCardsTypeBean = new RequestSellCardsTypeBean();
    private RequestSellCardsInfoBean sellCardsInfoBean = new RequestSellCardsInfoBean();
    private MyListAdapter myListAdapter;
    ProgressDialog dialog;
    private List<RequestSellCardsTypeBean.Data> cardTypeData = new ArrayList<>();
    PopupWindow popupWindow;
    private ListView pop_lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_card);

        initView();
        initData();
    }

    private void initData() {
        findAllCards();
        findCardsByCardId("");
    }

    private void initView() {


        dialog = new ProgressDialog(this);
        dialog.setMessage("加载中……");
        listView = findViewById(R.id.listview);
        tv_fenlie = findViewById(R.id.tv_fenlie);
        findViewById(R.id.iv_back).setOnClickListener(this);
        myListAdapter = new MyListAdapter();

        ll_fenlie =
                findViewById(R.id.ll_fenlie);
        ll_fenlie.setOnClickListener(this);
        iv_fenlie = findViewById(R.id.iv_fenlie);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("cardinfo", sellCardsInfoBean.getData().get(i));
                startActivity(new Intent(SellCardActivity.this, SellCardConfirmActivity.class).putExtras(bundle));
            }
        });



        setPop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_fenlie://分类

//                listPopup.showPopupWindow(v);
                iv_fenlie.setImageResource(R.drawable.img_down);

                        if (popupWindow.isShowing()) {
                            dismissPop();
//                            up_img.setVisibility(View.GONE);
//                            down_img.setVisibility(View.VISIBLE);
                        } else {
                            showPop(v);
//                            up_img.setVisibility(View.VISIBLE);
//                            down_img.setVisibility(View.GONE);
                        }



                break;

            case R.id.iv_back://返回
                finish();
                break;
            default:
                break;
        }
    }
    private void showPop(View v) {
        popupWindow.showAsDropDown(v);
    }

    private void dismissPop() {
        popupWindow.dismiss();
    }

    private void setPop() {

        View inflate = LayoutInflater.from(SellCardActivity.this).inflate(R.layout.shop_pop1, null);

        popupWindow = new PopupWindow(inflate);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失

        pop_lv = inflate.findViewById(R.id.pop_lv);
//        pop_lv.setAdapter(new PopAdapter());
        pop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if (i == 0) {
                    tv_fenlie.setText("全部");
                    findCardsByCardId("");
                } else {
                    //LogUtil.i(cardTypeData.get(id - 1).getName());
                    tv_fenlie.setText(cardTypeData.get(i - 1).getName());
                    findCardsByCardId(cardTypeData.get(i - 1).getId() + "");
                }
                popupWindow.dismiss();

            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                iv_fenlie.setImageResource(R.drawable.img_up);
            }
        });

    }
    private class PopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cardTypeData.size() + 1;
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
            View inflate = LayoutInflater.from(SellCardActivity.this).inflate(R.layout.shop_pop_item, null);
            TextView tv_item = inflate.findViewById(R.id.tv_item);
//            tv_item.setText(sellCardsInfoBean.getData().get(position).);


            if (position == 0) {
                tv_item.setText("全部");
            } else {
                tv_item.setText(cardTypeData.get(position - 1).getName());
            }
            return inflate;
        }
    }


    public class MyListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return sellCardsInfoBean.getData().size();
        }

        @Override
        public Object getItem(int i) {
            return sellCardsInfoBean.getData().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //         LayoutInflater.from(SellCardActivity.this).inflate( R.layout.listview_item_club_card, null);


            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(SellCardActivity.this).inflate(R.layout.listview_item_club_card, null);

                viewHolder.tv_name = view.findViewById(tv_cardname);
                viewHolder.tv_number = view.findViewById(R.id.tv_number);
                viewHolder.tv_time = view.findViewById(R.id.tv_time);
                viewHolder.tv_price = view.findViewById(R.id.tv_price);
                viewHolder.tv_cardtype = view.findViewById(tv_cardtype);
                viewHolder.iv_card = view.findViewById(iv_card);
                viewHolder.tv_price1 = view.findViewById(R.id.tv_price1);
                viewHolder.tv_branch_name = view.findViewById(R.id.tv_branch_name);
                viewHolder.tv_cardname1 = view.findViewById(R.id.tv_cardname1);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            RequestOptions options = new RequestOptions().placeholder(R.drawable.sijiao_card)
                    .transform(new GlideRoundTransform(SellCardActivity.this,6));

            Glide.with(SellCardActivity.this).load(sellCardsInfoBean.getData().get(i).getImg_url()).apply(options).into(viewHolder.iv_card);


            if (sellCardsInfoBean.getData().get(i).getCharge_mode() == 1) {//计时卡
                viewHolder.tv_cardtype.setText("卡类型：计时卡");
            }
            if (sellCardsInfoBean.getData().get(i).getCharge_mode() == 2) {//计次卡
                viewHolder.tv_cardtype.setText("卡类型：计次卡");
              //  viewHolder.tv_cardtype.setText("卡类型：计次卡（" + sellCardsInfoBean.getData().get(i).getTotal_count() + "次）");
            }
            if (sellCardsInfoBean.getData().get(i).getCharge_mode() == 3) {//储值卡
                viewHolder.tv_cardtype.setText("卡类型：储值卡");
            }


            viewHolder.tv_name.setText(sellCardsInfoBean.getData().get(i).getName());
            viewHolder.tv_cardname1.setText(sellCardsInfoBean.getData().get(i).getName());
            viewHolder.tv_price.setText(  PriceUtils.formatPrice2String(sellCardsInfoBean.getData().get(i).getPrice())+"元");
            viewHolder.tv_price1.setText(  PriceUtils.formatPrice2String(sellCardsInfoBean.getData().get(i).getPrice())+"元");
            if (!TextUtils.isEmpty(sellCardsInfoBean.getData().get(i).getTotal_count())) {
                viewHolder.tv_number.setText("次数：" + sellCardsInfoBean.getData().get(i).getTotal_count() + "次");
                viewHolder.tv_number.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_number.setVisibility(View.GONE);
            }

            if (sellCardsInfoBean.getData().get(i).getTime_unit() == 1) {
                viewHolder.tv_time.setText("有效期：" + sellCardsInfoBean.getData().get(i).getTime_value() + "年");
            }
            if (sellCardsInfoBean.getData().get(i).getTime_unit() == 2) {
                viewHolder.tv_time.setText("有效期：" + sellCardsInfoBean.getData().get(i).getTime_value() + "月");
            }
            viewHolder.tv_branch_name.setText(sellCardsInfoBean.getData().get(i).getBranch_name());


            return view;
        }


        class ViewHolder {
            TextView tv_name;
            TextView tv_price;
            TextView tv_number;
            TextView tv_time;
            TextView tv_price1;
            TextView tv_branch_name;
            TextView tv_cardname1;
            TextView tv_cardtype;
         ImageView iv_card;
        }

    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    listView.setAdapter(myListAdapter);
                    myListAdapter.notifyDataSetChanged();
                    break;
            }
            return true;
        }
    });


    /**
     * 查询所有在售卡
     */
    private void findAllCards() {

        MyStringRequest request = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.FINDALLCARDS), new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                sellCardsTypeBean = gson.fromJson(s, RequestSellCardsTypeBean.class);
                if (sellCardsTypeBean.getData() != null) {
                    cardTypeData = sellCardsTypeBean.getData();
                    pop_lv.setAdapter(new PopAdapter());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {

        };
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 查询所有在售卡by id
     */
    private void findCardsByCardId(String id) {

        dialog.show();
        String params = id;

        String url = Constants.plus(Constants.FIND_CARDS_BY_CARDTYPE) + id;

        MyStringRequest request = new MyStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                LogUtil.i(s);
                Gson gson = new Gson();
                // sellCardsInfoBean = new RequestSellCardsInfoBean();
                sellCardsInfoBean = gson.fromJson(s, RequestSellCardsInfoBean.class);
                if (sellCardsInfoBean.getSuccess()) {
                    Message message = new Message();
                    message.what = 1;
                    //message.obj="haha";
                    handler.sendMessage(message);
                } else {
                    ToastUtils.showToastShort(sellCardsInfoBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                dialog.dismiss();
                LogUtil.i(volleyError.toString());

            }

        }
        ) {

        };
        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("findCardsByCardId");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);

    }
}
