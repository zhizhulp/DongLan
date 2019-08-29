package com.cn.danceland.myapplication.pictureviewer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.utils.FileUtil;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.ImageSaveCallback;

import java.io.File;

import static com.bumptech.glide.Glide.with;


public class ImageDetailFragment2 extends Fragment {
    public static int mImageLoading;//占位符图片
    public static boolean mNeedDownload = false;//默认不支持下载
    private String mImageUrl;
    public SubsamplingScaleImageView mImageView;
    private PhotoViewAttacher mAttacher;
    private Bitmap mBitmap;
    private BigImageView bigImageView;
    private ImageView iv_gif;
    private boolean isavater;

    public static ImageDetailFragment2 newInstance(String imageUrl, boolean isavater) {
        final ImageDetailFragment2 imageDetailFragment = new ImageDetailFragment2();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        args.putBoolean("isavater", isavater);

        imageDetailFragment.setArguments(args);

        return imageDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        isavater= getArguments().getBoolean("isavater");
        BigImageViewer.initialize(GlideImageLoader.with(MyApplication.getContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_detail2, container, false);

        bigImageView = v.findViewById(R.id.mBigImage);
        iv_gif = v.findViewById(R.id.iv_gif);


        return v;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mImageView != null)
//            Glide.with(this).clear(mImageView);
//
//
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //  mImageUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512389262840&di=039fd750ee1b4ecaeb5d8bbf649bb8ec&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F19%2F62%2F61B58PICvc2_1024.jpg";
        //  LogUtil.i("mImageUrl=" + mImageUrl);


        StringBuilder sb = new StringBuilder(mImageUrl);

        String houzhui = mImageUrl.substring(mImageUrl.lastIndexOf(".") + 1);
        sb.insert(mImageUrl.length() - houzhui.length() - 1, "_400X400");

        if (TextUtils.equals(houzhui.toLowerCase(), "gif")) {
            bigImageView.setVisibility(View.GONE);
            iv_gif.setVisibility(View.VISIBLE);
            with(getActivity()).load(mImageUrl).into(iv_gif);
            iv_gif.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {


                    showDialog1();

                    return false;
                }
            });
        }
        iv_gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        BigImageViewer.prefetch(Uri.parse(mImageUrl));
        bigImageView.setProgressIndicator(new ProgressPieIndicator());

        bigImageView.showImage(Uri.parse(mImageUrl));
        //  bigImageView.showImage(Uri.parse(sb.toString()),Uri.parse(mImageUrl));
        bigImageView.setInitScaleType(BigImageView.INIT_SCALE_TYPE_CENTER_INSIDE);
        bigImageView.setImageSaveCallback(new ImageSaveCallback() {
            @Override
            public void onSuccess(String uri) {
                Toast.makeText(getActivity(),
                        "保存成功",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(),
                        "保存失败",
                        Toast.LENGTH_SHORT).show();
            }
        });
        bigImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDialog();

                return true;
            }
        });
        bigImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isavater){
                    getActivity().finish();
                }else {

                    ((ImagePagerActivity) getActivity()).startEndAnim();
                }
            }
        });
//        bigImageView.showImage(
//                Uri.parse("http://img1.imgtn.bdimg.com/it/u=1520386803,778399414&fm=21&gp=0.jpg"),
//                Uri.parse("http://youimg1.c-ctrip.com/target/tg/773/732/734/7ca19416b8cd423f8f6ef2d08366b7dc.jpg")
//        );

    }

    private static boolean checkSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    private void showDialog() {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(getActivity());
        dialog.setTitle("提示");
        dialog.setMessage("是否保存图片");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                bigImageView.saveImageIntoGallery();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void showDialog1() {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(getActivity());
        dialog.setTitle("提示");
        dialog.setMessage("是否保存图片");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                download(mImageUrl);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    // 保存图片到手机
    @SuppressLint("StaticFieldLeak")
    public void download(final String url) {

        AsyncTask<Void, Integer, File> execute = new AsyncTask<Void, Integer, File>() {

            @Override
            protected File doInBackground(Void... params) {
                File file = null;
                try {
                    FutureTarget<File> future =with(getActivity())
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    file = future.get();

                    // 首先保存图片
                    File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();

                    File appDir = new File(pictureFolder, "GIF");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = System.currentTimeMillis() + ".gif";
                    File destFile = new File(appDir, fileName);

                    FileUtil.copyFile(file.getAbsolutePath(), destFile.getAbsolutePath());

                    // 最后通知图库更新
                    getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));


                } catch (Exception e) {
                    LogUtil.e(e.getMessage());
                    Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
                }
                return file;
            }

            @Override
            protected void onPostExecute(File file) {

                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
    }

}
