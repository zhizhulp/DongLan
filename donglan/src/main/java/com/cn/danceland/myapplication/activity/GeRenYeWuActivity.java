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

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.bean.HuiJiYeWuBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.QianKeHuiFangFragment;
import com.cn.danceland.myapplication.fragment.QianKeZengJiaFragment;

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


/**
 * Created by shy on 2017/12/19 09:34
 * Email:644563767@qq.com
 * 会籍个人业务
 */


public class GeRenYeWuActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;

    public String[] mTitleDataList = new String[]{"潜客添加", "潜客回访", "会员回访"};

    private String id;
    private String auth;
    private String date;
    HuiJiYeWuBean.Data data;
    private boolean isjiaolian;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);
        EventBus.getDefault().register(this);
        id = getIntent().getStringExtra("id");
        auth = getIntent().getStringExtra("auth");
        date = getIntent().getStringExtra("date");
        data = (HuiJiYeWuBean.Data) getIntent().getSerializableExtra("data");
        isjiaolian = getIntent().getBooleanExtra("isjiaolian", false);

        if (isjiaolian) {
            mTitleDataList = new String[]{"体测", "体测分析", "健身计划"};
        }
        initView();

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
            case 6881://入场成功
                finish();

                break;
            default:
                break;
        }

    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(this);
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
//                current_page = position;
//                if (position == 0) {
//                    btn_add.setVisibility(View.GONE);
//                } else {
//                    btn_add.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


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

            bundle.putString("date", date);
            bundle.putSerializable("data", data);
            bundle.putBoolean("isjiaolian", isjiaolian);

            if (arg0 == 0) {
                QianKeZengJiaFragment fragment = new QianKeZengJiaFragment();
                bundle.putInt("type",3);
                fragment.setArguments(bundle);
                return fragment;
            } else if (arg0 == 1) {
                if (isjiaolian){
                    QianKeZengJiaFragment fragment = new QianKeZengJiaFragment();
                    bundle.putInt("type",4);//体测分析
                    fragment.setArguments(bundle);
                    return fragment;

                }

                QianKeHuiFangFragment fragment = new QianKeHuiFangFragment();
                bundle.putString("auth", "1");
                fragment.setArguments(bundle);
                return fragment;
            } else if (arg0 == 2) {
                if (isjiaolian){
                    QianKeZengJiaFragment fragment = new QianKeZengJiaFragment();
                    bundle.putInt("type",5);//健身计划
                    fragment.setArguments(bundle);
                    return fragment;

                }
                QianKeHuiFangFragment fragment = new QianKeHuiFangFragment();
                bundle.putString("auth", "2");
                fragment.setArguments(bundle);
                return fragment;
            }
            return null;

        }

        @Override
        public int getCount() {
            return mTitleDataList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleDataList[position];
        }

    }


}
