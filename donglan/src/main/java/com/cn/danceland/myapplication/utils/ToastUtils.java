package com.cn.danceland.myapplication.utils;

import android.widget.Toast;

import com.cn.danceland.myapplication.MyApplication;

/**
 * Toast封装
 */
public class ToastUtils {

    /** 之前显示的内容 */
    private static String oldMsg ;
    /** Toast对象 */
    private static Toast toast = null ;
    /** 第一次时间 */
    private static long oneTime = 0 ;
    /** 第二次时间 */
    private static long twoTime = 0 ;

    public static void showToastShort(String message){
        if(toast == null){
            toast = Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT);
            toast.show() ;
            oneTime = System.currentTimeMillis() ;
        }else{
            twoTime = System.currentTimeMillis() ;
            if(message.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    toast.show() ;
                }
            }else{
                oldMsg = message ;
                toast.setText(message) ;
                toast.show() ;
            }
        }
        oneTime = twoTime ;
    }




//
//    public static void showToastShort(String text) {
//        Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT).show();
//    }

    public static void showToastLong(String text) {
        Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_LONG).show();
    }
}
