package com.cn.danceland.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cn.danceland.myapplication.fragment.AttentionFragment;
import com.cn.danceland.myapplication.fragment.SelectionFragment;

public class TabAdapter extends FragmentPagerAdapter
{

	public static String[] TITLES = new String[]{ "精选", "关注" };


	public TabAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		if (arg0 == 0) {
			SelectionFragment fragment = new SelectionFragment();
			return fragment;
		}
		if (arg0==1){
			AttentionFragment fragment = new AttentionFragment();
			return fragment;
		}

		return new SelectionFragment();

	}

	@Override
	public int getCount()
	{
		return TITLES.length;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return TITLES[position];
	}

}
