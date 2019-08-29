package com.cn.danceland.myapplication.pictureviewer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.utils.MD5Utils;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageDetailFragment1 extends Fragment {
    public static int mImageLoading;//占位符图片
    public static boolean mNeedDownload = false;//默认不支持下载
    private String mImageUrl;
    public SubsamplingScaleImageView mImageView;
    private PhotoViewAttacher mAttacher;
    private Bitmap mBitmap;

    public static ImageDetailFragment1 newInstance(String imageUrl) {
        final ImageDetailFragment1 imageDetailFragment = new ImageDetailFragment1();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        imageDetailFragment.setArguments(args);

        return imageDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_detail1, container, false);
        mImageView = (SubsamplingScaleImageView ) v.findViewById(R.id.image);
        mImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE);

        mImageView.setMinScale(0.5F);//最小显示比例

        mImageView.setMaxScale(3.0F);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）


      //  mAttacher = new PhotoViewAttacher(mImageView);
//        mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mNeedDownload) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setMessage("保存图片");
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ImageUtil.saveImage(getActivity(), mImageUrl, mBitmap);
//                        }
//                    });
//                    builder.create().show();
//                }
//                return false;
//            }
//        });
//        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//
//            @Override
//            public void onPhotoTap(View arg0, float arg1, float arg2) {
//                getActivity().finish();
//            }
//        });
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
      //  mImageUrl = "http://cdn.duitang.com/uploads/item/201409/20/20140920230643_8tij8.png";


        //保存路径
        String imgDir = "";
        if (checkSDCard()) {
            imgDir = Environment.getExternalStorageDirectory().getPath() + "/Gilde/" + MD5Utils.encode(mImageUrl) + ".jpg";
            File dirFirstFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Gilde");//方法二：通过变量文件来获取需要创建的文件夹名字
            if(!dirFirstFolder.exists())
            { //如果该文件夹不存在，则进行创建
                dirFirstFolder.mkdirs();//创建文件夹

            }

        } else {
            imgDir = Environment.getDataDirectory().getPath() + "/Gilde/" + MD5Utils.encode(mImageUrl) + ".jpg";
            File dirFirstFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Gilde");//方法二：通过变量文件来获取需要创建的文件夹名字
            if(!dirFirstFolder.exists())
            { //如果该文件夹不存在，则进行创建
                dirFirstFolder.mkdirs();//创建文件夹

            }
        }
        File file = new File(imgDir);

        if (!file.exists()) {

            //使用Glide下载图片,保存到本地

            RequestOptions options = new RequestOptions().placeholder(mImageLoading).error(mImageLoading);

            Glide.with(getActivity()).asBitmap()
                    .load(mImageUrl)
                    .apply(options)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {


                            //保存路径
                            String imgDir = "";
                            if (checkSDCard()) {
                                imgDir = Environment.getExternalStorageDirectory().getPath() + "/Gilde/" + MD5Utils.encode(mImageUrl) + ".jpg";
                                File dirFirstFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Gilde");//方法二：通过变量文件来获取需要创建的文件夹名字
                                if(!dirFirstFolder.exists())
                                { //如果该文件夹不存在，则进行创建
                                    dirFirstFolder.mkdirs();//创建文件夹

                                }

                            } else {
                                imgDir = Environment.getDataDirectory().getPath() + "/Gilde/" + MD5Utils.encode(mImageUrl) + ".jpg";
                                File dirFirstFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Gilde");//方法二：通过变量文件来获取需要创建的文件夹名字
                                if(!dirFirstFolder.exists())
                                { //如果该文件夹不存在，则进行创建
                                    dirFirstFolder.mkdirs();//创建文件夹

                                }
                            }
                            File file = new File(imgDir);

                            if (!file.exists()) {
                                try {
                                    file.createNewFile();
                                    //   Log.i("taginfo", file.getAbsolutePath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            FileOutputStream fout = null;
                            try {
                                //保存图片
                                fout = new FileOutputStream(file);
                                resource.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                                //Log.i("taginfo", file.getAbsolutePath());
                                // 将保存的地址给SubsamplingScaleImageView,这里注意设置ImageViewState
                             //   mImageView.setImage(ImageSource.uri(file.getAbsolutePath()), new ImageViewState(0.5F, new PointF(0, 0), 0));
                                mImageView.setImage(ImageSource.uri(file.getAbsolutePath()));
                                //  Glide.with(getActivity()).load(file.getAbsolutePath()).into(mImageView);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    if (fout != null) fout.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

        }else {
           // mImageView.setImage(ImageSource.uri(file.getAbsolutePath()), new ImageViewState(0.5F, new PointF(0, 0), 0));
            mImageView.setImage(ImageSource.uri(file.getAbsolutePath()));
        }


//        if (!TextUtils.isEmpty(mImageUrl)) {
//
//
//            //使用Glide下载图片,保存到本地
//
//            RequestOptions options = new RequestOptions().placeholder(mImageLoading).error(mImageLoading);
//
//            Glide.with(getActivity()).asBitmap()
//                    .load(mImageUrl)
//                    .apply(options)
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//
//
//                            //保存路径
//                            String imgDir = "";
//                            if (checkSDCard()) {
//                                imgDir = Environment.getExternalStorageDirectory().getPath() + "/Gilde/" + MD5Utils.encode(mImageUrl) + ".jpg";
//                                File dirFirstFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Gilde");//方法二：通过变量文件来获取需要创建的文件夹名字
//                                if(!dirFirstFolder.exists())
//                                { //如果该文件夹不存在，则进行创建
//                                    dirFirstFolder.mkdirs();//创建文件夹
//
//                                }
//
//                            } else {
//                                imgDir = Environment.getDataDirectory().getPath() + "/Gilde/" + MD5Utils.encode(mImageUrl) + ".jpg";
//                                File dirFirstFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Gilde");//方法二：通过变量文件来获取需要创建的文件夹名字
//                                if(!dirFirstFolder.exists())
//                                { //如果该文件夹不存在，则进行创建
//                                    dirFirstFolder.mkdirs();//创建文件夹
//
//                                }
//                            }
//                            File file = new File(imgDir);
//
//                            if (!file.exists()) {
//                                try {
//                                    file.createNewFile();
//                                 //   Log.i("taginfo", file.getAbsolutePath());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            FileOutputStream fout = null;
//                            try {
//                                //保存图片
//                                fout = new FileOutputStream(file);
//                                resource.compress(Bitmap.CompressFormat.JPEG, 100, fout);
//                                //Log.i("taginfo", file.getAbsolutePath());
//                                // 将保存的地址给SubsamplingScaleImageView,这里注意设置ImageViewState
//                                mImageView.setImage(ImageSource.uri(file.getAbsolutePath()), new ImageViewState(0.5F, new PointF(0, 0), 0));
//                                  //  Glide.with(getActivity()).load(file.getAbsolutePath()).into(mImageView);
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            } finally {
//                                try {
//                                    if (fout != null) fout.close();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });


//            RequestOptions options = new RequestOptions().placeholder(mImageLoading).error(mImageLoading).skipMemoryCache(true);
//
//            Glide.with(getActivity()).asBitmap().load(mImageUrl).apply(options).listener(new RequestListener<Bitmap>() {
//                @Override
//                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                    Log.i("taginfo", "onLoadFailed: "+e.toString());
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                    Log.i("taginfo", "onResourceReady: ");
//                    return false;
//                }
//            })
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                            mBitmap = resource;
//                            mImageView.setImageBitmap(mBitmap);
//                            mAttacher.update();
//                        }
//                    });



//            Glide.with(getActivity()).load(mImageUrl).asBitmap().placeholder(mImageLoading).error(mImageLoading)
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                            mBitmap = bitmap;
//                            mImageView.setImageBitmap(mBitmap);
//                            mAttacher.update();
//                        }
//                    });

     //   }
    }

    private static boolean checkSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
