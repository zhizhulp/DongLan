package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.fragment.ShopListFragment;
import com.cn.danceland.myapplication.utils.SPUtils;

/**
 * Created by shy on 2018/4/11 11:22
 * Email:644563767@qq.com
 */


public class ShopListActivity extends BaseFragmentActivity {

    private ShopListFragment shopListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        shopListFragment = new ShopListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("jingdu", SPUtils.getString("jingdu", "0"));
        bundle.putString("weidu", SPUtils.getString("weidu", "0"));
        shopListFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container, shopListFragment).commit();

    }


}
