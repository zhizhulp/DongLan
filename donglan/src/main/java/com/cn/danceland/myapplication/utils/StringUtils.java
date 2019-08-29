package com.cn.danceland.myapplication.utils;

import com.cn.danceland.myapplication.R;

import java.util.regex.Pattern;

/**
 * Created by feng on 2018/4/3.
 */

public class StringUtils {

    public static boolean isNullorEmpty(String string){
        if(string!=null){
            if("".equals(string)){
                return true;
            }
        }else {
            return true;
        }
        return false;
    }

    /**
     * 是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全是数字
     * @param str
     * @return
     */
    public static boolean isAllNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 首字母是否是数字
     * @param str
     * @return
     */
    public static boolean isFirstNumeric(String str){
        char[] chars = new char[1];
        chars[0] = str.charAt(0);
        String temp = new String(chars);
        return isNumeric(temp);
    }
}
