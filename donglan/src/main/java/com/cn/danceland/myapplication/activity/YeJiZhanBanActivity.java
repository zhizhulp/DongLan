package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.ZongYeJiFragment1;
import com.cn.danceland.myapplication.fragment.ZongYeWument;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.view.CustomDateAndTimePicker;
import com.cn.danceland.myapplication.view.DongLanTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.cn.danceland.myapplication.R.id.iv_rili;
import static com.cn.danceland.myapplication.adapter.TabAdapter.TITLES;


/**
 * Created by shy on 2017/12/19 09:34
 * Email:644563767@qq.com
 * 业绩展板
 */


public class YeJiZhanBanActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private TextView tv_zongyeji;
    public String[] mTitleDataList = new String[]{"今日总业绩", "今日总业务"};
    private DongLanTitleView titleView;
    private String mDate, zongyeji, zongyewu;
    private int crrentpage = 0;
    private boolean isjiaolian=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yjzb);
        EventBus.getDefault().register(this);
        initView();
        mDate = TimeUtils.timeStamp2Date(System.currentTimeMillis() + "", "yyyy-MM-dd");
        zongyewu="0";
        zongyeji="0";
        showZongyeji( zongyeji);
        isjiaolian=getIntent().getBooleanExtra("isjiaolian",false);

        //会籍主管或会籍主管
        if (SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIANZHUGUAN || SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_HUIJIZHUGUANG) {

            tv_zongyeji.setVisibility(View.VISIBLE);
            findViewById(iv_rili).setVisibility(View.VISIBLE);
        }else {
            tv_zongyeji.setVisibility(View.GONE);
            findViewById(iv_rili).setVisibility(View.GONE);
        }


        if (isjiaolian){
            titleView.setTitle("教练展板(" + mDate.replace("-", ".") + ")");
        }else {
            titleView.setTitle("会籍展板(" + mDate.replace("-", ".") + ")");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {
            case 7101://刷新总数
                zongyeji = event.getMsg();

                if (crrentpage == 0) {
                    showZongyeji(zongyeji);
                } else {
                    showZongyewu(zongyewu);
                }
                break;
            case 7102://刷新总数
                zongyewu = event.getMsg();

                if (crrentpage == 0) {
                    showZongyeji(zongyeji);
                } else {
                    showZongyewu(zongyewu);
                }
                break;
            default:
                break;
        }

    }

    private void showZongyeji(String zongyeji) {
        if (isjiaolian){
            tv_zongyeji.setText("今日教练总业绩：" + zongyeji + "元");
        }else {
            tv_zongyeji.setText("今日会籍总业绩：" + zongyeji + "元");
        }

    }

    private void showZongyewu(String zongyewu) {
        if (isjiaolian){
            tv_zongyeji.setText("今日教练总业务：" + zongyewu + "个");
        }else {
            tv_zongyeji.setText("今日会籍总业务：" + (zongyewu) + "个");
        }

    }

    private void showDate(String date) {

        final CustomDateAndTimePicker customDateAndTimePicker = new CustomDateAndTimePicker(this, "请选择日期", date);
        customDateAndTimePicker.setGoneHourAndMinute();
        customDateAndTimePicker.showWindow();
        customDateAndTimePicker.setDialogOnClickListener(new CustomDateAndTimePicker.OnClickEnter() {
            @Override
            public void onClick() {
                String dateString = customDateAndTimePicker.getHorizongtal();
//                tv_birthday.setText(dateString);
//                potentialInfo.setBirthday(dateString);
                mDate = dateString;
                EventBus.getDefault().post(new StringEvent(dateString, 7100));
                if (isjiaolian){
                    titleView.setTitle("教练展板(" + dateString.replace("-", ".") + ")");
                }else {
                    titleView.setTitle("会籍展板(" + dateString.replace("-", ".") + ")");
                }

            }
        });
    }

    private void initView() {
        findViewById(iv_rili).setOnClickListener(this);
        titleView = findViewById(R.id.dl_title);
        tv_zongyeji = findViewById(R.id.tv_zongyeji);
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
                crrentpage = position;
//                current_page = position;
                if (position == 0) {
                    showZongyeji(zongyeji);
                } else {
                    showZongyewu(zongyewu);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (getIntent().getIntExtra("issend", 0) == 1) {
            mViewPager.setCurrentItem(1, false);
        }

    }

    private void initMagicIndicator1() {
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);

                colorTransitionPagerTitleView.setNormalColor(Color.BLACK);
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.color_dl_yellow));
                colorTransitionPagerTitleView.setText(mTitleDataList[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            //            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
//                return indicator;
//            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                //  indicator.setColors(Color.parseColor("#40c4ff"));
                indicator.setColors(getResources().getColor(R.color.color_dl_yellow));
                return indicator;
            }

        });
        commonNavigator.setAdjustMode(true);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case iv_rili:
                showDate(mDate);
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
            bundle.putBoolean("isjiaolian", isjiaolian);
//            bundle.putString("auth",auth);
            if (arg0 == 0) {
                ZongYeJiFragment1 fragment = new ZongYeJiFragment1();

                 fragment.setArguments(bundle);
                return fragment;
            } else if (arg0 == 1) {
                ZongYeWument fragment = new ZongYeWument();
                 fragment.setArguments(bundle);
                return fragment;
            }
            return null;

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


}
