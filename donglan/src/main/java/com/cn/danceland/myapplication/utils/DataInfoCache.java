package com.cn.danceland.myapplication.utils;

import android.content.Context;
import android.util.Log;

import com.cn.danceland.myapplication.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Administrator(林冠宏) on 2016/1/20.
 * <p>
 * 本类 采用泛型 广泛接受 ArrayList 直接做缓存，即是开发中经常用到的 bean；
 * <p>
 * 使用注意： 传进来的 ArrayList 所绑定的 种子，一定要是 已经继承 Serializable 接口的；
 * <p>
 * 使用文本流做缓存。
 */

public class DataInfoCache {

    /**
     * 定义一些你项目里面的 缓存文件名字 ，自定义，不要也没关系，调用函数再传入也行
     */

    public static String QzInfo = "Qz_List_Info";
    public static String CyInfo = "Cy_List_Info";
    private static String DATA_CACHE = "Data_Cache_File";

    /**
     * 保存 一组 数据
     *
     * @param data      种子
     * @param cacheName 缓存文件名
     */
    public static <T> void saveListCache(ArrayList<T> data, String cacheName) {
        new DataCache<T>().saveGlobal(MyApplication.getContext(), data, cacheName);
    }

    /**
     * 保存 一个 数据
     *
     * @param data      种子
     * @param cacheName 缓存文件名
     */
    public static <T> void saveOneCache(Object data, String cacheName) {
        new DataCache<T>().saveGlobalone(MyApplication.getContext(), data, cacheName);
    }


    /**
     * 直接根据 缓存文件名获取
     */
    public static <T> ArrayList<T> loadListCache(String cacheName) {
        return new DataCache<T>().loadGlobal(MyApplication.getContext(), cacheName);
    }

    /**
     * 直接根据文件名 获取一个文件名获取
     */
    public static Object loadOneCache(String cacheName) {
        return new DataCache().loadGlobal_one(MyApplication.getContext(), cacheName);
    }


    /**
     * 获取 一组 数据
     *
     * @param <T> 数据缓存 save or load
     */
    static class DataCache<T> {
        public void save(Context ctx, ArrayList<T> data, String name) {
            save(ctx, data, name, "");
        }

        public void saveGlobal(Context ctx, ArrayList<T> data, String name) {
            save(ctx, data, name, DATA_CACHE);
        }

        public void saveGlobalone(Context ctx, Object data, String name) {
            save_one(ctx, data, name, DATA_CACHE);
        }

        private void save_one(Context ctx, Object data, String name, String folder) {
            if (ctx == null) {
                return;
            }
            File file;
            if (!folder.isEmpty()) {
                File fileDir = new File(ctx.getFilesDir(), folder);
                if (!fileDir.exists() || !fileDir.isDirectory()) {
                    fileDir.mkdir();
                }
                file = new File(fileDir, name);
            } else {
                file = new File(ctx.getFilesDir(), name);
            }
            if (file.exists()) {
                file.delete();
            }

               Log.d("taginfo", file.getAbsolutePath());
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(data);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private void save(Context ctx, ArrayList<T> data, String name, String folder) {
            if (ctx == null) {
                return;
            }
            File file;
            if (!folder.isEmpty()) {
                File fileDir = new File(ctx.getFilesDir(), folder);
                if (!fileDir.exists() || !fileDir.isDirectory()) {
                    fileDir.mkdir();
                }
                file = new File(fileDir, name);
            } else {
                file = new File(ctx.getFilesDir(), name);
            }
            if (file.exists()) {
                file.delete();
            }
            //  Log.d("taginfo", file.getAbsolutePath());
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(data);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public ArrayList<T> load(Context ctx, String name) {
            return load(ctx, name, "");
        }

        public ArrayList<T> loadGlobal(Context ctx, String name) {
            return load(ctx, name, DATA_CACHE);
        }

        public Object loadGlobal_one(Context ctx, String name) {
            return load_one(ctx, name, DATA_CACHE);
        }

        private Object load_one(Context ctx, String name, String folder) {
            Object data = null;

            File file;
            if (!folder.isEmpty()) {
                File fileDir = new File(ctx.getFilesDir(), folder);
                if (!fileDir.exists() || !fileDir.isDirectory()) {
                    fileDir.mkdir();
                }
                file = new File(fileDir, name);
            } else {
                file = new File(ctx.getFilesDir(), name);
            }
            //      Log.d("taginfo", "file " + file.getAbsolutePath());
            if (file.exists()) {
                try {
                    //    Log.d("taginfo", "write object");
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    data = (Object) ois.readObject();
                    ois.close();
                } catch (Exception e) {
                    //   Log.d("taginfo", e.toString());
                }
            }
            if (data == null) {     /** 如果没有 */
                //  Log.d("taginfo", "data == null");
                LogUtil.i("data == null" + "data是空");
             //   data = new Object();
                // data = new Class<T>().newInstance();Class<?>
            }
            return data;
        }

        private ArrayList<T> load(Context ctx, String name, String folder) {
            ArrayList<T> data = null;

            File file;
            if (!folder.isEmpty()) {
                File fileDir = new File(ctx.getFilesDir(), folder);
                if (!fileDir.exists() || !fileDir.isDirectory()) {
                    fileDir.mkdir();
                }
                file = new File(fileDir, name);
            } else {
                file = new File(ctx.getFilesDir(), name);
            }
            //       Log.d("taginfo", "file " + file.getAbsolutePath());
            if (file.exists()) {
                try {
                    //             Log.d("taginfo", "write object");
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    data = (ArrayList<T>) ois.readObject();
                    ois.close();
                } catch (Exception e) {
                    //           Log.d("taginfo", e.toString());
                }
            }
            if (data == null) {     /** 如果没有 */
                LogUtil.i("data == null" + "data是空");
                data = new ArrayList<T>();
            }
            return data;
        }
    }
}