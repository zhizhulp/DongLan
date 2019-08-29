package com.cn.danceland.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.cn.danceland.myapplication.MyApplication;

public class UIUtils {

	/**
	 * 获取上下文
	 * 
	 * @return
	 */
	public static Context getContext() {
		return MyApplication.getContext();
	}

	/**
	 * 获取主线程
	 * 
	 * @return
	 */
	public static Thread getMainThread() {
		return MyApplication.getMainThread();
	}

	/**
	 * 获取主线程id
	 * 
	 * @return
	 */
	public static long getMainThreadId() {
		return MyApplication.getMainThreadId();
	}

	/**
	 * 获取到主线程的looper
	 * 
	 * @return
	 */
	public static Looper getMainThreadLooper() {
		return MyApplication.getMainThreadLooper();
	}

	/** dip转换px */
	public static int dip2px(int dip) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** pxz转换dip */
	public static int px2dip(int px) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/** 获取主线程的handler */
	public static Handler getHandler() {
		return MyApplication.getMainThreadHandler();
	}

	/** 延时在主线程执行runnable */
	public static boolean postDelayed(Runnable runnable, long delayMillis) {
		return getHandler().postDelayed(runnable, delayMillis);
	}

	/** 在主线程执行runnable */
	public static boolean post(Runnable runnable) {
		return getHandler().post(runnable);
	}

	/** 从主线程looper里面移除runnable */
	public static void removeCallbacks(Runnable runnable) {
		getHandler().removeCallbacks(runnable);
	}

	/**
	 * 获取布局
	 * 
	 * @param resId
	 * @return
	 */
	public static View inflate(int resId) {
		return LayoutInflater.from(getContext()).inflate(resId, null);
	}

	/** 获取资源 */
	public static Resources getResources() {

		return getContext().getResources();
	}

	/** 获取文字 */
	public static String getString(int resId) {
		return getResources().getString(resId);
	}

	/** 获取文字数组 */
	public static String[] getStringArray(int resId) {
		return getResources().getStringArray(resId);
	}

	/** 获取dimen */
	public static int getDimens(int resId) {
		return getResources().getDimensionPixelSize(resId);
	}

	/** 获取drawable */
	public static Drawable getDrawable(int resId) {
		return getResources().getDrawable(resId);
	}

	/** 获取颜色 */
	public static int getColor(int resId) {
		return getResources().getColor(resId);
	}

	/** 获取颜色选择器 */
	public static ColorStateList getColorStateList(int resId) {
		return getResources().getColorStateList(resId);
	}

	// 判断当前的线程是不是在主线程
	public static boolean isRunInMainThread() {
		return android.os.Process.myTid() == getMainThreadId();
	}

	public static void runInMainThread(Runnable runnable) {
		// 在主线程运行
		if (isRunInMainThread()) {
			runnable.run();
		} else {
			post(runnable);
		}
	}


	/** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
	public static void showToastSafe(final int resId) {
		showToastSafe(getString(resId));
	}

	/** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
	public static void showToastSafe(final String str) {
		if (isRunInMainThread()) {
			showToast(str);
		} else {
			post(new Runnable() {
				@Override
				public void run() {
					showToast(str);
				}
			});
		}
	}

	private static void showToast(String str) {
		// BaseActivity frontActivity = BaseActivity.getForegroundActivity();
		// if (frontActivity != null) {
		Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
		// }
	}

	/**
	 * 选择变换
	 *
	 * @param origin 原图
	 * @param alpha  旋转角度，可正可负
	 * @return 旋转后的图片
	 */
	public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
		if (origin == null) {
			return null;
		}
		int width = origin.getWidth();
		int height = origin.getHeight();
		Matrix matrix = new Matrix();
		matrix.setRotate(alpha);
		// 围绕原地进行旋转
		Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
		if (newBM.equals(origin)) {
			return newBM;
		}
		origin.recycle();
		return newBM;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	private static double EARTH_RADIUS = 6378.137;//通过经纬度获取距离(单位：米)
	/**
	 * 通过经纬度获取距离(单位：米)
	 *
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return 距离
	 */
	public static double getDistance(double lat1, double lng1, double lat2,
									 double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		return s;
	}

	/**
	 * 详情ImageView等比缩放 多用于商品详情  譬如生鲜详情
	 * <p/>
	 * Created by yangxiaoxue on 2018/10/19.
	 * @param context
	 * @param view ImageView 等
	 * @param imageWidth 设计图比例宽
	 * @param imageHeight 设计图比例高
	 * @return
	 */
	public static View setViewRatio(Context context, View view,float imageWidth,float imageHeight) {
		// 获取屏幕宽度，由宽度来设置banner的高度。
		Display displaey = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		int width = displaey.getWidth();
		float heightTemp = ((imageHeight / imageWidth) * width);
		int height = (int) heightTemp;
		LayoutParams lp;
		lp = view.getLayoutParams();
		lp.width = width;
		lp.height = height;
		view.setLayoutParams(lp);
		return view;
	}

	/**
	 * 修改状态栏颜色，支持4.4以上版本
	 * @param activity
	 * @param colorId
	 */
//	public static void setStatusBarColor(Activity activity, int colorId) {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			Window window = activity.getWindow();
//			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//			window.setStatusBarColor(activity.getResources().getColor(colorId));
//		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			//使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
//			transparencyBar(activity);
//			SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//			tintManager.setStatusBarTintEnabled(true);
//			tintManager.setStatusBarTintResource(colorId);
//		}
//	}
}
