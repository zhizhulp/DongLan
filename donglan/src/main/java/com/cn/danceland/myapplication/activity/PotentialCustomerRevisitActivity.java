package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.evntbus.IntEvent;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.PotentialUpcomingMatterFragment;
import com.cn.danceland.myapplication.fragment.RevisitListFragment;
import com.cn.danceland.myapplication.fragment.RevisiterRecordAllFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.vondear.rxtools.RxDataTool;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeAnchor;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeRule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shy on 2018/1/8 16:00
 * Email:644563767@qq.com
 * 潜客回访
 */


public class PotentialCustomerRevisitActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    public String[] TITLES = new String[]{"最近维护", "回访记录", "未处理待办"};
    public String[] UPCOMING_CONDITION = new String[]{"未处理待办", "已处理待办", "全部待办"};
    public String[] LIST_TYPE = new String[]{"最近维护", "最晚维护", "健身指数", "关注程度"};
    PopupWindow popupWindow;
    private int untreated_num = 0;
    private Gson gson = new Gson();
    private int current_page = 0;
    private int current_item1 = 0;
    private int current_item2 = 0;
    private int current_item3 = 0;
    private SimplePagerTitleView simplePagerTitleView;
    private BadgePagerTitleView badgePagerTitleView;
    private String auth;
    private EditText et_searchInfo;
    private ImageView iv_del;
    private TextView tv_tiltle_num;
    int num1, num2, num3;
    private ListView pop_lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potential_customer_revisit);
        EventBus.getDefault().register(this);
        initView();
        initData();

    }

    private void initData() {
        try {
            find_upcoming_list();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe
    public void onEventMainThread(IntEvent event) {
        LogUtil.i("" + event.getMsg());
        switch (event.getEventCode()) {
            case 150:
                if (untreated_num > 0) {
                    TextView badgeTextView = (TextView) LayoutInflater.from(this).inflate(R.layout.simple_count_badge_layout, null);
                    badgeTextView.setText("" + untreated_num);
                    badgePagerTitleView.setBadgeView(badgeTextView);
                } else {
                    badgePagerTitleView.setBadgeView(null);
                }

                //   commonNavigatorAdapter.notifyDataSetChanged();
                break;
            case 151:
                try {
                    find_upcoming_list();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 161:
                num1 = event.getMsg();

                settitlenum();

                break;
            case 162:
                num2 = event.getMsg();

                settitlenum();

                break;
            case 163:
                num3 = event.getMsg();

                settitlenum();

                break;
            default:
                break;
        }

    }

    private void settitlenum() {


        if (current_page == 0) {
            if (num1 > 0) {
                tv_tiltle_num.setText("(" + num1 + ")");
            } else {
                tv_tiltle_num.setText("");
            }
        }
        if (current_page == 1) {
            if (num2 > 0) {
                tv_tiltle_num.setText("(" + num2 + ")");
            } else {
                tv_tiltle_num.setText("");
            }
        }
        if (current_page == 2) {
            if (num3 > 0) {
                tv_tiltle_num.setText("(" + num3 + ")");
            } else {
                tv_tiltle_num.setText("");
            }
        }
    }


    private void initView() {

        TextView tv_tiltle = findViewById(R.id.tv_tiltle);
        tv_tiltle_num = findViewById(R.id.tv_tiltle_num);
        auth = getIntent().getStringExtra("auth");
        if (TextUtils.equals(auth, "2")) {
            tv_tiltle.setText("会员管理");
        }
        findViewById(R.id.iv_back).setOnClickListener(this);
        et_searchInfo = findViewById(R.id.et_phone);

        iv_del = findViewById(R.id.iv_del);

        et_searchInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//
            }

            //监听edit
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(et_searchInfo.getText().toString().trim())) {
                    iv_del.setVisibility(View.VISIBLE);


                    searshInfo();


                } else {
                    iv_del.setVisibility(View.GONE);
                    searshInfo();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_searchInfo.setText(null);

                //  searshInfo();


            }
        });
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(myViewPagerAdapter);
        initMagicIndicator1();
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                current_page = position;

                searshInfo();


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setPop();
    }


    private void searshInfo() {

//4位以下纯数字滤掉
        if (RxDataTool.isInteger(et_searchInfo.getText().toString()) && et_searchInfo.getText().toString().length() < 5) {
            return;
        }
        if (current_page == 2) {
            if (current_item3 == 0) {//查询未处理
                EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 152));
            } else if (current_item3 == 1) {//查询已处理
                EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 153));
            } else {//查询全部
                EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 154));
            }

        }
        if (current_page == 1) {
            EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 210));
        }
        if (current_page == 0) {

            if (current_item1 == 0) {//最近
                EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 162));
            } else if (current_item1 == 1) {//最晚
                EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 163));
            } else if (current_item1 == 2) {//健身指数
                EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 164));
            } else if (current_item1 == 3) {//关注程度
                EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 165));
            }


        }


    }


    private void setPop() {

        View inflate = LayoutInflater.from(PotentialCustomerRevisitActivity.this).inflate(R.layout.shop_pop1, null);

        popupWindow = new PopupWindow(inflate);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失

        pop_lv = inflate.findViewById(R.id.pop_lv);
//        pop_lv.setAdapter(new PopAdapter());
        pop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                if (current_page == 0) {

                    if (i == 0) {//最近
                        //      EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 162));

                        TITLES[0] = "最近维护";
                    } else if (i == 1) {//最晚
                        //            EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 163));
                        TITLES[0] = "最晚维护";
                    } else if (i == 2) {//健身指数
                        TITLES[0] = "健身指数";
                        //            EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 164));
                    } else if (i == 3) {//关注程度
                        TITLES[0] = "关注程度";
                        //                 EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 165));
                    }
                    current_item1 = i;

                }
                if (current_page == 2) {
                    current_item3 = i;
                    if (i == 0) {//查询未处理
                        //     EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 152));
                        TITLES[2] = "未处理待办";

                    } else if (i == 1) {//查询已处理
                        TITLES[2] = "已处理待办";
                        //         EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 153));
                    } else {//查询全部
                        TITLES[2] = "全部待办";
                        //      EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 154));
                    }

                }
                if (current_page == 1) {
                    current_item2 = 0;
                    //   EventBus.getDefault().post(new StringEvent(et_searchInfo.getText().toString(), 210));
                }
                searshInfo();
                commonNavigatorAdapter.notifyDataSetChanged();

                popupWindow.dismiss();

            }
        });


    }

    private class PopAdapter extends BaseAdapter {
        String[] data = new String[]{};

        public PopAdapter(String[] data) {
            this.data = data;
        }

        public void setData(String[] data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.length;
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
            View inflate = LayoutInflater.from(PotentialCustomerRevisitActivity.this).inflate(R.layout.shop_pop_item, null);
            TextView tv_item = inflate.findViewById(R.id.tv_item);
            tv_item.setText(data[position]);
            return inflate;
        }
    }

    CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {
        @Override
        public int getCount() {
            return TITLES == null ? 0 : TITLES.length;
        }

        @Override
        public IPagerTitleView getTitleView(final Context context, final int index) {
            badgePagerTitleView = new BadgePagerTitleView(context);

            simplePagerTitleView = new ColorTransitionPagerTitleView(context);
            if (index == 0 || index == 2) {
                simplePagerTitleView.setText(TITLES[index]+"⇋");
            } else {
                simplePagerTitleView.setText(TITLES[index]);
            }



            simplePagerTitleView.setNormalColor(Color.BLACK);
            simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.color_dl_yellow));
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (index == 0) {


                        pop_lv.setAdapter(new PopAdapter(LIST_TYPE));

                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();

                        } else {
                            popupWindow.showAsDropDown(v);

                        }


                    }


                    if (index == 2) {


                        pop_lv.setAdapter(new PopAdapter(UPCOMING_CONDITION));

                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();

                        } else {
                            popupWindow.showAsDropDown(v);

                        }

                    }
                    mViewPager.setCurrentItem(index);
                    //      badgePagerTitleView.setBadgeView(null); // cancel badge when click tab
                }
            });

            badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
//
//                // setup badge

            if (index == 2) {
                TextView badgeTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.simple_count_badge_layout, null);
                badgeTextView.setText("" + untreated_num);
                badgePagerTitleView.setBadgeView(badgeTextView);
            }

            if (index == 2) {

                badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(context, 8)));
                badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, 0));

            }

            // don't cancel badge when tab selected
            badgePagerTitleView.setAutoCancelBadge(false);

            if (untreated_num == 0) {
                badgePagerTitleView.setBadgeView(null);
            }

            return badgePagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
            // indicator.setColors(Color.parseColor("#40c4ff"));
            indicator.setColors(getResources().getColor(R.color.color_dl_yellow));
            return indicator;
        }
    };

    private void initMagicIndicator1() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(commonNavigatorAdapter);
        magicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicIndicator, mViewPager);
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


    public class MyViewPagerAdapter extends FragmentPagerAdapter {


        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            Bundle bundle = new Bundle();
            bundle.putString("auth", auth);

            if (arg0 == 0) {
                RevisitListFragment fragment = new RevisitListFragment();
                fragment.setArguments(bundle);
                return fragment;
            } else if (arg0 == 1) {
                //RevisitListFragment1 fragment = new RevisitListFragment1();
                RevisiterRecordAllFragment fragment = new RevisiterRecordAllFragment();
                fragment.setArguments(bundle);
                return fragment;
            } else {
                PotentialUpcomingMatterFragment fragment = new PotentialUpcomingMatterFragment();
                fragment.setArguments(bundle);
                return fragment;
            }


        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

    }

    class StrBean {
        public String page;
        public String auth;
        public String status = "0";

        // public String member_id;
    }

    class RequsetSimpleBean1 {
        public boolean success;
        public int data;
        public String errorMsg;

        @Override
        public String toString() {
            return "RequsetSimpleBean{" +
                    "success=" + success +
                    ", data=" + data +
                    ", errorMsg='" + errorMsg + '\'' +
                    '}';
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

    /**
     * 查询未待办数量
     *
     * @throws JSONException
     */
    public void find_upcoming_list() throws JSONException {

        StrBean strBean = new StrBean();
        strBean.auth = auth;
        //   strBean.page = pageCount - 1 + "";
        //  strBean.member_id = id;
        String s = gson.toJson(strBean);
        LogUtil.i(s.toString());
        JSONObject jsonObject = new JSONObject(s.toString());

        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_NOT_UPCOMINGMATTER), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequsetSimpleBean1 requsetSimpleBean = new RequsetSimpleBean1();
                requsetSimpleBean = gson.fromJson(jsonObject.toString(), RequsetSimpleBean1.class);
                Message message = Message.obtain();
                message.what = 1;

                //LogUtil.i(requsetSimpleBean.toString());
                if (requsetSimpleBean.isSuccess()) {
                    untreated_num = requsetSimpleBean.data;
                    //handler.sendMessage(message);
                    // initMagicIndicator1();
                    EventBus.getDefault().post(new IntEvent(requsetSimpleBean.data, 150));
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
