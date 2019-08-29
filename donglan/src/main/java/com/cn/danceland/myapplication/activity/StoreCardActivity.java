package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.fragment.MyStoreFragment;
import com.cn.danceland.myapplication.fragment.SellStoreCardFragment;
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
 * Created by feng on 2018/3/14.
 * 储值卡
 */

public class StoreCardActivity extends BaseFragmentActivity {

    TabLayout tl;
    //MagicIndicator magic_indicator;
    ViewPager view_pager;
    ArrayList<String> mTitleDataList;
    FragmentManager supportFragmentManager;
    ArrayList<Fragment> fragmentArrayList;
    ImageView storecard_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storecard);
        initHost();

        initView();

    }

    private void initHost() {


        supportFragmentManager = getSupportFragmentManager();

        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new MyStoreFragment());
        fragmentArrayList.add(new SellStoreCardFragment());


    }

    public void showFragment(int i){

        view_pager.setCurrentItem(i);
    }

    private void initView() {


        DongLanTitleView titleView=findViewById(R.id.dl_title);
        titleView.setMoreTvOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StoreCardActivity.this,XiaoFeiRecordActivity.class));
            }
        });
        mTitleDataList = new ArrayList<>();
        mTitleDataList.add("我的储值");
        mTitleDataList.add("我要充值");

        //magic_indicator = findViewById(R.id.magic_indicator);
        view_pager = findViewById(R.id.view_pager);

        view_pager.setAdapter(new myFragmentPagerAdapter(supportFragmentManager,fragmentArrayList));




        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
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