package com.cn.danceland.myapplication.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtil {
    private static final boolean DEBUG = true;

    public static final String TAG = "taginfo";

    private static void d(String method, String msg) {
        Log.d(TAG, "[" + method + "]" + msg);
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, getFileLineMethod() + ">>>>>" + msg);
        }
    }

    public static void y(String msg) {
        if (DEBUG) {
            Log.e("ccf", getLineMethod() + msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(TAG, getLineMethod() + msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, getFileLineMethod() + ">>>>>" + msg);
        }
    }

    private static String getFileLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer("[类：")
                .append(traceElement.getFileName()).append(" | 行：")
                .append(traceElement.getLineNumber()).append(" | 函数：")
                .append(traceElement.getMethodName()).append("]");
        return toStringBuffer.toString();
    }

    private static String getLineMethod() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer("[行数：")
                .append(traceElement.getLineNumber()).append(" | 函数：")
                .append(traceElement.getMethodName()).append("]");
        return toStringBuffer.toString();
    }

    private static String _FILE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[2];
        return traceElement.getFileName();
    }

    private static String _FUNC_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getMethodName();
    }

    private static int _LINE_() {
        StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
        return traceElement.getLineNumber();
    }

    private static String _TIME_() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(now);
    }
}