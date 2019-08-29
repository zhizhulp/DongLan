package com.cn.danceland.myapplication.utils;

/**
 * Created by shy on 2017/11/18 14:45
 * Email:644563767@qq.com
 */

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * SD卡相关的辅助类
 */
public class SDCardUtils {
    private SDCardUtils() {
        /** cannot be instantiated **/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }


    /**
     * 读取文件的大小
     */

    public static long getFileSize(File f) throws Exception {

        long l = 0;

        if (f.exists()) {

            FileInputStream mFIS = new FileInputStream(f);

            l = mFIS.available();

        } else {

            f.createNewFile();

        }

        return l;

    }

    /**
     * 将文件大小转换成字节
     */

    public static String formatFileSize(File file) throws Exception {
        long fSize =getFileSize(file);
                DecimalFormat df = new DecimalFormat("#.00");

        String fileSizeString = "";

        if (fSize < 1024) {

            fileSizeString = df.format((double) fSize) + "B";

        } else if (fSize > 104875) {

            fileSizeString = df.format((double) fSize / 1024) + "K";

        } else if (fSize > 1073741824) {

            fileSizeString = df.format((double) fSize / 104875) + "M";

        } else {

            fileSizeString = df.format((double) fSize / 1073741824) + "G";

        }

        return fileSizeString;

    }


}