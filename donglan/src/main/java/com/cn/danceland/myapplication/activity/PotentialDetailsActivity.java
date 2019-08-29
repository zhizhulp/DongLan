package com.cn.danceland.myapplication.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.bean.RequsetPotentialListBean;
import com.cn.danceland.myapplication.evntbus.IntEvent;
import com.cn.danceland.myapplication.fragment.RevisiterInfoFragment;
import com.cn.danceland.myapplication.fragment.RevisiterRecordFragment;
import com.cn.danceland.myapplication.fragment.UpcomingMatterFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

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
 * Created by shy on 2018/1/11 16:53
 * Email:644563767@qq.com
 * 潜客详情页
 */


public class PotentialDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    public  String[] TITLES = new String[]{"详细资料", "回访记录", "未处理待办"};
    public  String[] UPCOMING_CONDITION = new String[]{"未处理待办", "已处理待办", "全部待办"};
    PopupWindow popupWindow;
    ListView pop_lv;
    private String id;
    private RequsetPotentialListBean.Data.Content info;
    private int current_page = 0;
    private Button btn_add;
    private Gson gson = new Gson();
    private int untreated_num = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                LogUtil.i("" + untreated_num);
//                TextView badgeTextView = (TextView) LayoutInflater.from(PotentialDetailsActivity.this).inflate(R.layout.simple_count_badge_layout, null);
//                badgeTextView.setText("" + untreated_num);
//                badgePagerTitleView.setBadgeView(badgeTextView);
//                commonNavigatorAdapter.notifyDataSetChanged();
            }
        }
    };
    private String auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potential_customer_details);
        EventBus.getDefault().register(this);
        initView();
        try {
            find_upcoming_list(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //注册event事件

    }

    @Override
    public void onDestroy() {
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
                    find_upcoming_list(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    private void setPop() {

        View inflate = LayoutInflater.from(PotentialDetailsActivity.this).inflate(R.layout.shop_pop1, null);

        popupWindow = new PopupWindow(inflate);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失

        pop_lv = inflate.findViewById(R.id.pop_lv);
//        pop_lv.setAdapter(new PopAdapter());
        pop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                if (i == 0) {//查询未处理
                    TITLES[2] = "未处理待办";
                    EventBus.getDefault().post(new IntEvent(0, 152));
                } else if (i == 1) {//查询已处理
                    TITLES[2] = "已处理待办";
                    EventBus.getDefault().post(new IntEvent(0, 153));
                } else {//查询全部
                    EventBus.getDefault().post(new IntEvent(0, 154));
                    TITLES[2] = "全部待办";

                }
                commonNavigatorAdapter.notifyDataSetChanged();

                popupWindow.dismiss();

            }
        });


    }

    private void initView() {
        id = getIntent().getStringExtra("id");
        auth = getIntent().getStringExtra("auth");
        //  LogUtil.i(id);
        info = (RequsetPotentialListBean.Data.Content) getIntent().getExtras().getSerializable("info");
        //  LogUtil.i(info.toString());

        TextView tv_tiltle=findViewById(R.id.tv_tiltle);
        auth = getIntent().getStringExtra("auth");
        if (TextUtils.equals(auth,"2")){
            tv_tiltle.setText("会员详情");
        }else {
            tv_tiltle.setText("潜客详情");
        }
        findViewById(R.id.iv_back).setOnClickListener(this);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_add.setVisibility(View.GONE);
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
                if (position == 0) {
                    btn_add.setVisibility(View.GONE);
                } else {
                    btn_add.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setPop();
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
            View inflate = LayoutInflater.from(PotentialDetailsActivity.this).inflate(R.layout.shop_pop_item, null);
            TextView tv_item = inflate.findViewById(R.id.tv_item);
            tv_item.setText(data[position]);
            return inflate;
        }
    }
    private TextView badgeTextView;
    private BadgePagerTitleView badgePagerTitleView;
    private CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {


        @Override
        public int getCount() {
            return TITLES == null ? 0 : TITLES.length;
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int index) {
            badgePagerTitleView = new BadgePagerTitleView(context);

            SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
            if ( index == 2) {
                simplePagerTitleView.setText(TITLES[index]+"⇋");
            } else {
                simplePagerTitleView.setText(TITLES[index]);
            }
            simplePagerTitleView.setNormalColor(Color.BLACK);
            simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.color_dl_yellow));
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //badgePagerTitleView.setBadgeView(null); // cancel badge when click tab
                    if (index == 2 ) {

                        pop_lv.setAdapter(new PopAdapter(UPCOMING_CONDITION));

                        if (popupWindow.isShowing()) {
                            popupWindow.dismiss();

                        } else {
                            popupWindow.showAsDropDown(v);

                        }
                    }
                    mViewPager.setCurrentItem(index);
                }
            });

            badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
//
//                // setup badge
            if (index == 2) {
                badgeTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.simple_count_badge_layout, null);
                badgeTextView.setText("" + untreated_num);
                badgePagerTitleView.setBadgeView(badgeTextView);
            }

            if (index == 2) {

                badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(context, 6)));
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
          //  indicator.setColors(Color.parseColor("#40c4ff"));
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
            case R.id.btn_add:
                if (current_page == 1) {

                    Bundle bundle = new Bundle();
                    //    bundle.putString("time", CallLogUtils.getLastCallHistoryDuration(null, cr) + "");
                    bundle.putString("id", info.getId());
                    bundle.putString("auth", info.getAuth());
                    bundle.putString("member_name", info.getCname());
                    bundle.putString("member_no", info.getMember_no());
                    startActivity(new Intent(PotentialDetailsActivity.this, AddRevisiterRecordActivity.class)
                            .putExtras(bundle));
                }

                if (current_page == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putString("person_id", info.getPerson_id());
                    bundle.putString("id", info.getId());
                    bundle.putString("auth", info.getAuth());
                    bundle.putString("member_name", info.getCname());
                    bundle.putString("member_no", info.getMember_no());
                    startActivity(new Intent(PotentialDetailsActivity.this, AddUpcomingMatterActivity.class)
                            .putExtras(bundle));
                }


                //     startActivity(new Intent(PotentialCustomerRevisitActivity.this, AddPotentialActivity.class));
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
            bundle.putString("id", id);
            bundle.putString("auth",auth);
            if (arg0 == 0) {
                RevisiterInfoFragment fragment = new RevisiterInfoFragment();

                fragment.setArguments(bundle);
                return fragment;
            } else if (arg0 == 1) {
                RevisiterRecordFragment fragment = new RevisiterRecordFragment();
                fragment.setArguments(bundle);
                return fragment;
            } else {

                UpcomingMatterFragment fragment = new UpcomingMatterFragment();
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

    /**
     * 是否添加回访记录
     */
    public void showDialogRrcord(final int pos) {
//        final ContentResolver cr;
//        cr = this.getContentResolver();
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("是否读取通话记录，并添加到回访记录");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Bundle bundle = new Bundle();
                //    bundle.putString("time", CallLogUtils.getLastCallHistoryDuration(null, cr) + "");
                bundle.putString("id", info.getId());
                bundle.putString("auth", info.getAuth());
                bundle.putString("member_name", info.getCname());
                bundle.putString("member_no", info.getMember_no());
                startActivity(new Intent(PotentialDetailsActivity.this, AddRevisiterRecordActivity.class)
                        .putExtras(bundle));

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }


    class StrBean {
        public String page;
        //public String auth = "1";
        public String member_id;
        public String status="0";

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
    public void find_upcoming_list(final String id) throws JSONException {

        StrBean strBean = new StrBean();
        //  strBean.page = pageCount - 1 + "";
        strBean.member_id = id;
        String s = gson.toJson(strBean);

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
                    handler.sendMessage(message);
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
