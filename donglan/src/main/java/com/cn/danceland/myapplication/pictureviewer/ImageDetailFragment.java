package com.cn.danceland.myapplication.pictureviewer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.cn.danceland.myapplication.R;

public class ImageDetailFragment extends Fragment {
    public static int mImageLoading;//占位符图片
    public static boolean mNeedDownload = false;//默认不支持下载
    private String mImageUrl;
    public ImageView mImageView;
    private PhotoViewAttacher mAttacher;
    private Bitmap mBitmap;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment imageDetailFragment = new ImageDetailFragment();

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
        final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mNeedDownload) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("保存图片");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ImageUtil.saveImage(getActivity(), mImageUrl, mBitmap);
                        }
                    });
                    builder.create().show();
                }
                return false;
            }
        });
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });
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


        if (!TextUtils.isEmpty(mImageUrl)) {

//            final File downDir = Environment.getExternalStorageDirectory();
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
//
//                            } else {
//                                imgDir = Environment.getDataDirectory().getPath() + "/Gilde/" + MD5Utils.encode(mImageUrl) + ".jpg";
//
//                            }
//                            File file = new File(imgDir);
//                            Log.i("taginfo", file.getAbsolutePath());
//                            if (!file.exists()) {
//                                try {
//                                    file.createNewFile();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            FileOutputStream fout = null;
//                            try {
//                                //保存图片
//                                fout = new FileOutputStream(file);
//                                resource.compress(Bitmap.CompressFormat.JPEG, 100, fout);
//
//                                // 将保存的地址给SubsamplingScaleImageView,这里注意设置ImageViewState
//                                //    imageView.setImage(ImageSource.uri(file.getAbsolutePath()), new ImageViewState(0.5F, new PointF(0, 0), 0));
//                                //    Glide.with(getActivity()).load(file.getAbsolutePath()).into(mImageView);
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


            RequestOptions options = new RequestOptions().placeholder(mImageLoading).error(mImageLoading).skipMemoryCache(true);

            Glide.with(getActivity()).asBitmap().load(mImageUrl).apply(options).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    Log.i("taginfo", "onLoadFailed: "+e.toString());
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    Log.i("taginfo", "onResourceReady: ");
                    return false;
                }
            })
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            mBitmap = resource;
                            mImageView.setImageBitmap(mBitmap);
                            mAttacher.update();
                        }
                    });



//            Glide.with(getActivity()).load(mImageUrl).asBitmap().placeholder(mImageLoading).error(mImageLoading)
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//                            mBitmap = bitmap;
//                            mImageView.setImageBitmap(mBitmap);
//                            mAttacher.update();
//                        }
//                    });

        } else

        {
            mImageView.setImageResource(mImageLoading);
        }
    }

    private static boolean checkSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
