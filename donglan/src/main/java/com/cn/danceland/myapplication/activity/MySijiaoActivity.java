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
import com.cn.danceland.myapplication.fragment.ForOtherSiJiaoFragment;
import com.cn.danceland.myapplication.fragment.MySiJiaoFragment;
import com.cn.danceland.myapplication.view.DongLanTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2018/4/16.
 */

public class MySijiaoActivity extends BaseFragmentActivity {

    DongLanTitleView activity_mysijiao_title;
    MagicIndicator magicIndicator;
    ViewPager view_pager;
    ArrayList<Fragment> fragmentArrayList;
    List<String> mTitleDataList;
    FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysijiao);
        initHost();
        initView();
    }

    private void initHost() {


        supportFragmentManager = getSupportFragmentManager();
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new MySiJiaoFragment());
        fragmentArrayList.add(new ForOtherSiJiaoFragment());

    }

    private void initView() {
        activity_mysijiao_title = findViewById(R.id.activity_mysijiao_title);
        activity_mysijiao_title.setTitle("我的私教");

        magicIndicator = findViewById(R.id.magic_indicator);

        view_pager = findViewById(R.id.view_pager);
        view_pager.setAdapter(new myFragmentPagerAdapter(supportFragmentManager,fragmentArrayList));

        initIndicator();

    }

    private void initIndicator() {
        mTitleDataList = new ArrayList<>();
        mTitleDataList.add("我的私教");
        mTitleDataList.add("送出的私教");

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ff6600"));
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        view_pager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setColors(Color.parseColor("#ff6600"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicIndicator,view_pager);
        if (getIntent().getIntExtra("issend",0)==1){
            view_pager.setCurrentItem(1,false);
        }
    }

    public class myFragmentPagerAdapter extends FragmentPagerAdapter {

        private FragmentManager fragmetnmanager;  //创建FragmentManager
        private List<Fragment> listfragment; //创建一个List<Fragment>
        public myFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.fragmetnmanager=fm;
            this.listfragment=list;
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return listfragment.get(arg0); //返回第几个fragment
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return listfragment.size(); //总共有多少个fragment
        }


    }
}
