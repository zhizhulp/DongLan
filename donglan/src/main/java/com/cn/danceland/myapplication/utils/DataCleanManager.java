package com.cn.danceland.myapplication.utils;

/**
 * Created by shy on 2018/4/24 15:41
 * Email:644563767@qq.com
 */


import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.cn.danceland.myapplication.MyApplication;

import java.io.File;
import java.math.BigDecimal;

/** * 本应用数据清除管理器 */
public class DataCleanManager {
    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 按名字清除本应用数据库 * *
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     *
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * * 清除本应用所有的数据 * *
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

//    /**
//     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
//     *
//     * @param directory
//     */
//    private static void deleteFilesByDirectory(File directory) {
//        if (directory != null && directory.exists() && directory.isDirectory()) {
//            for (File item : directory.listFiles()) {
//                item.delete();
//            }
//        }
//    }

//    //删除文件夹和文件夹里面的文件
//    public static void deleteDir(final String pPath) {
//        File dir = new File(pPath);
//        deleteFilesByDirectory(dir);
//    }

    public static void deleteFilesByDirectory(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteFilesByDirectory(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }



    // 获取文件
    // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
    // 目录，一般放一些长时间保存的数据
    // Context.getExternalCacheDir() -->
    // SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;


        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }
    public static String getAllCacheSize() throws Exception {
        File file = new File(MyApplication.getContext().getCacheDir().getPath());
        File file1 = new File(MyApplication.getContext().getExternalCacheDir().getPath());
        return getFormatSize(getFolderSize(file)+getFolderSize(file1));
    }

}

//}
//
//完整demo代码：
//        [java] view plain copy
//        package com.example.androidclearcache;
//
//        import android.os.Bundle;
//        import android.os.Handler;
//        import android.os.Message;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.View.OnClickListener;
//        import android.view.animation.Animation;
//        import android.view.animation.AnimationUtils;
//        import android.widget.ImageView;
//        import android.widget.LinearLayout;
//        import android.widget.TextView;
//        import java.io.File;
//        import android.app.Activity;
//        import android.app.Dialog;
//        import android.content.Context;
//
//public class MainActivity extends Activity implements OnClickListener {
//
//    public LinearLayout layout;
//    public TextView tv_cashe;
//    private Dialog dialog;
//
//    private Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case 0x01:
//                    dialog.dismiss();
//                    tv_cashe.setText("0.0KB");
//                    break;
//                case 0x02:
//                    dialog.dismiss();
//                    break;
//            }
//        };
//    };
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        layout = (LinearLayout) findViewById(R.id.layout);
//        tv_cashe=(TextView) findViewById(R.id.textView2);
//        //获得应用内部缓存(/data/data/com.example.androidclearcache/cache)
//        File file =new File(this.getCacheDir().getPath());
//        try {
//            tv_cashe.setText(DataCleanManager.getCacheSize(file));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        layout.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.layout:
//                Message msg = new Message();
//                dialog = createLoadingDialog(MainActivity.this, "清理中....");
//                dialog.show();
//
//                try {
//                    DataCleanManager.cleanInternalCache(getApplicationContext());
//                    msg.what = 0x01;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    msg.what = 0x02;
//                }
//                handler.sendMessageDelayed(msg, 1000);
//                break;
//
//            default:
//                break;
//        }
//    }
//
//    // 自定义的清理对话框
//    public static Dialog createLoadingDialog(Context context, String msg) {
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
//        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
//        // main.xml中的ImageView
//        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.dialog_img);
//        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
//        // 加载动画
//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.load_animation);
//        // 使用ImageView显示动画
//        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
//        tipTextView.setText(msg);// 设置加载信息
//
//        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
//
//        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
//        loadingDialog.setCanceledOnTouchOutside(false);
//        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
//                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
//        return loadingDialog;
//
//    }
//}