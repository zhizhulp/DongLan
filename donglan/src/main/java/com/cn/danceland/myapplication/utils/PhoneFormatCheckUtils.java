package com.cn.danceland.myapplication.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/***
 * 判断电话号码工具类
 */
public class PhoneFormatCheckUtils {

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 1+任意数
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((1[0-9][0-9])|(13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

//
//    /**
//     * 检查身份证号码合法性
//     * @param idCardNo
//     * @return
//     * @throws Exception
//     */
//    public static boolean checkIdCardNo(String idCardNo) throws Exception{
//        try {
//            if(TextUtils.isEmpty(idCardNo)){
//                return false;
//            }
//            int length = idCardNo.length();
//            if(length == 15){
//                Pattern p = Pattern.compile("^[0-9]*$");
//                Matcher m = p.matcher(idCardNo);
//                return m.matches();
//            }else if(length == 18){
//                String front_17 = idCardNo.substring(0, idCardNo.length() - 1);//号码前17位
//                String verify = idCardNo.substring(17, 18);//校验位(最后一位)
//                Pattern p = Pattern.compile("^[0-9]*$");
//                Matcher m = p.matcher(front_17);
//                if(!m.matches()){
//                    return false;
//                }else{
//                    checkVerify(front_17, verify);
//                }
//            }
//            return false;
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//
//    /**
//     * 校验验证位合法性
//     * @param front_17
//     * @param verify
//     * @return
//     * @throws Exception
//     */
//    public static boolean checkVerify(String front_17,String verify) throws Exception{
//        try {
//            int[] wi = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
//            String[] vi = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
//            int s = 0;
//            for(int i = 0; i<front_17.length(); i++){
//                int ai = Integer.parseInt(front_17.charAt(i) + "");
//                s += wi[i]*ai;
//            }
//            int y = s % 11;
//            String v = vi[y];
//            if(!(verify.toUpperCase().equals(v))){
//                return false;
//            }
//            return true;
//        } catch (Exception e) {
//            throw e;
//        }
//    }

    /**
     * 判断身份证合法
     * @param IDNumber
     * @return
     */
    public static boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                       LogUtil.i("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.i("异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }

}
