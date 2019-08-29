package com.cn.danceland.myapplication.utils.multipartrequest;

import java.io.File;

/**
 * Created by shy on 2017/11/7 14:22
 * Email:644563767@qq.com
 */


public class FileUtil {

    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
