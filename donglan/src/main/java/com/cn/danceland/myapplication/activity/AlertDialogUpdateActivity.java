package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.utils.LogUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class AlertDialogUpdateActivity extends BaseActivity {

    private AlertDialog progressDialog;
    private Button btn_ok, btn_ok1;
    private TextView tv_message;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog_update);
        this.setFinishOnTouchOutside(false);//点击外部不能取消
        initView();

    }

    private void initView() {

//        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDownloadProgressDialog(AlertDialogUpdateActivity.this,getIntent().getStringExtra("url"));
//
//            }
//        });
        progressBar = findViewById(R.id.progress_bar);
        btn_ok = findViewById(R.id.btn_ok);
        btn_ok1 = findViewById(R.id.btn_ok1);
        tv_message = findViewById(R.id.tv_message);
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        tv_message.setText("您的应用版本过低，系统将为您强制升级");
        btn_ok1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ok1.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                new DownloadAPK(progressBar).execute(getIntent().getStringExtra("url"));
            }
        });

    }


//
//    private void showDownloadProgressDialog(Context context, String downloadUrl) {
//        progressDialog = new AlertDialog.Builder(context).create();
//   //     progressDialog.setTitle("提示");
//        View view=View.inflate(context, R.layout.download_progressbar,null);
//        ProgressBar progressBar=view.findViewById(R.id.progress_bar);
//        btn_ok = view.findViewById(R.id.btn_ok);
//        tv_message = view.findViewById(R.id.tv_message);
//        progressBar.setIndeterminate(false);
//        progressBar.setMax(100);
//
//        progressDialog.setView(view);
//        progressDialog.setCancelable(false);                    //设置不可点击界面之外的区域让对话框小时
//        //     progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);         //进度条类型
//        progressDialog.show();
//        // String downloadUrl = "http://ac-edNxPKqQ.clouddn.com/800exxxxxxx68ebcefda.apk"; //这里写你的apk url地址
//        new DownloadAPK(progressBar).execute(downloadUrl);
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //主页面返回两次退出
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {


            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 下载APK的异步任务
     */

    private class DownloadAPK extends AsyncTask<String, Integer, String> {
        ProgressBar progressbar;
        File file;

        public DownloadAPK(ProgressBar progressDialog) {
            this.progressbar = progressDialog;
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;

            try {
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                int fileLength = conn.getContentLength();
                bis = new BufferedInputStream(conn.getInputStream());
                String fileName = Environment.getExternalStorageDirectory().getPath() + "/donglan/update.apk";
                LogUtil.i(fileName);
                file = new File(fileName);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while ((count = bis.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressbar.setProgress(progress[0]);
            tv_message.setText("正在下载安装文件..." + progress[0] + "%");

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            tv_message.setText("下载完成，请点击安装");
            btn_ok.setVisibility(View.VISIBLE);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    installAPK(file);

                }
            });
            installAPK(file);
        }

//

    }

    private void installAPK(File file) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {
            //参数1 上下文；参数2 Provider主机地址 authorities 和配置文件中保持一致 ；参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(MyApplication.getCurrentActivity(), "com.cn.danceland.myapplication.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        MyApplication.getCurrentActivity().startActivity(intent);
    }

}
