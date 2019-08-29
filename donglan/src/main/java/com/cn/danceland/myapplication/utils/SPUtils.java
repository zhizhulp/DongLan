package com.cn.danceland.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cn.danceland.myapplication.MyApplication;

/**
 * SharePreference封装
 */
public class SPUtils {
	public static final String PREF_NAME = "saveInfo";


	public static boolean getBoolean(String key, boolean defaultValue) {
		SharedPreferences sp = MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defaultValue);
	}

	public static void setBoolean( String key, boolean value) {
		SharedPreferences sp = MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();

	}

	public static String getString( String key, String defaultValue) {
		SharedPreferences sp = MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
	}

	public static void setString( String key, String value) {
		SharedPreferences sp =  MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();

	}

	public static int getInt( String key, int defaultValue) {
		SharedPreferences sp =  MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	public static void setInt( String key, int value) {
		SharedPreferences sp =  MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();

	}

	public static void remove( String key) {
		SharedPreferences sp =  MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		sp.edit().remove(key).commit();

	}
	// 退出登录时要调用
	public static void clean() {
		SharedPreferences sp =  MyApplication.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		try {

			if (null != sp) {
				sp.edit().clear().commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
