package com.cn.danceland.myapplication.pictureviewer;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.ImageBean;
import com.cn.danceland.myapplication.utils.AppUtils;

import java.util.List;


public class ImagePagerActivity extends FragmentActivity {
    private static final String TAG = "ImagePagerActivity";
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;
    private static boolean mIsShowNumber = true;//是否显示数字下标
    private ImageBean imageBean;
    private FrameLayout fl_image;
    private ImagePagerActivity context;

    public ImagePagerActivity getContext() {
        return context;
    }

    public void setContext(ImagePagerActivity context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
        this.context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
        }

        setContentView(R.layout.activity_image_detail_pager);
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        imageBean = getIntent().getParcelableExtra("image");

        List<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        mPager = (HackyViewPager) findViewById(R.id.pager);
        fl_image = findViewById(R.id.fl_image);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (TextView) findViewById(R.id.indicator);

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator,
                        arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);
        indicator.setVisibility(mIsShowNumber ? View.VISIBLE : View.GONE);

        if (imageBean != null) {
            startAnim();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    public void startAnim() {
        int mOriginLeft = imageBean.getViewLeft();
        int mOriginTop = imageBean.getViewTop();
        int mOriginHeight = imageBean.getViewHeight();
        int mOriginWidth = imageBean.getViewWidth();

        Animation scaleAnimation = new ScaleAnimation((float) mOriginWidth / (float) AppUtils.getWidth(), 1f,
                (float) mOriginWidth / (float) AppUtils.getWidth(), 1f, mOriginLeft, mOriginTop);
        scaleAnimation.setDuration(250);
      //  scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());//加速变化 越来越快
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
           //     fl_image.getBackground().setAlpha(0);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //  fl_image.getBackground().setAlpha(255);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        //设置动画持续时长
        alphaAnimation.setDuration(250);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
     //   alphaAnimation.setFillAfter(true);


        ValueAnimator animator = ValueAnimator.ofInt(0, 255);
        animator.setDuration(250);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                fl_image.getBackground().setAlpha(curValue);
            }
        });
        animator.start();
        AnimationSet animationSet=new AnimationSet(true);
      //  animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        fl_image.startAnimation(animationSet);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }


    public void startEndAnim() {
        int mOriginLeft = imageBean.getViewLeft();
        int mOriginTop = imageBean.getViewTop();
        int mOriginHeight = imageBean.getViewHeight();
        int mOriginWidth = imageBean.getViewWidth();

        Animation scaleAnimation = new ScaleAnimation(1f, (float) mOriginWidth / (float) AppUtils.getWidth(), 1f,
                (float) mOriginWidth / (float) AppUtils.getWidth(), mOriginLeft, mOriginTop);
        scaleAnimation.setDuration(250);
     //   scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());//减速变化 越来越慢
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //  fl_image.getBackground().setAlpha(255);
                context.overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        //设置动画持续时长
        alphaAnimation.setDuration(250);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
    //    alphaAnimation.setFillAfter(true);

//        ValueAnimator animator = ValueAnimator.ofInt(255, 0);
//        animator.setDuration(250);
//        animator.setInterpolator(new AccelerateInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int curValue = (int) animation.getAnimatedValue();
//                fl_image.getBackground().setAlpha(curValue);
//            }
//        });
//        animator.start();
        AnimationSet animationSet=new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        fl_image.startAnimation(animationSet);
    }


    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public List<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, List<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position).toString();
            return ImageDetailFragment2.newInstance(url,false);
        }


    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
    }
    public static void startActivity(Context context, PictureConfig config) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, config.list);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, config.position);
        intent.putExtra("image", config.getImageBean());
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        ImageDetailFragment.mImageLoading = config.resId;
        ImageDetailFragment.mNeedDownload = config.needDownload;
        mIsShowNumber = config.mIsShowNumber;
        ImageUtil.path = config.path;
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.no_anim, R.anim.no_anim);
    }
}