package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextUtils;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.WeekView;

import java.util.List;

/**
 * 演示一个变态需求的周视图
 * Created by huanghaibin on 2018/2/9.
 */

public class CustomWeekView extends WeekView {


    private int mRadius;

    /**
     * 自定义魅族标记的文本画笔
     */
    private Paint mTextPaint = new Paint();


    /**
     * 24节气画笔
     */
    private Paint mSolarTermTextPaint = new Paint();

    /**
     * 背景圆点
     */
    private Paint mPointPaint = new Paint();

    /**
     * 今天的背景色
     */
    private Paint mCurrentDayPaint = new Paint();


    /**
     * 圆点半径
     */
    private float mPointRadius;

    private int mPadding;

    private float mCircleRadius;
    /**
     * 自定义魅族标记的圆形背景
     */
    private Paint mSchemeBasicPaint = new Paint();

    private float mSchemeBaseLine;

    public CustomWeekView(Context context) {
        super(context);
        mTextPaint.setTextSize(dipToPx(context, 8));
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);


        mSolarTermTextPaint.setColor(0xff489dff);
        mSolarTermTextPaint.setAntiAlias(true);
        mSolarTermTextPaint.setTextAlign(Paint.Align.CENTER);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setFakeBoldText(true);
        mSchemeBasicPaint.setColor(Color.WHITE);

        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);


        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.FILL);
        mCurrentDayPaint.setColor(0xFFeaeaea);


        mCircleRadius = dipToPx(getContext(), 7);

        mPadding = dipToPx(getContext(), 3);

        mPointRadius = dipToPx(context, 2);

        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mCircleRadius - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);

        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
        //4.0以上硬件加速会导致无效
       // mSelectedPaint.setMaskFilter(new BlurMaskFilter(28, BlurMaskFilter.Blur.SOLID));//加阴影

        setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemeBasicPaint);
    //    mSchemeBasicPaint.setMaskFilter(new BlurMaskFilter(28, BlurMaskFilter.Blur.SOLID));
    }


    @Override
    protected void onPreviewHook() {
        mSolarTermTextPaint.setTextSize(mCurMonthLunarTextPaint.getTextSize());
        mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 5;
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x) {

        boolean isSelected = isSelected(calendar);
        if (isSelected) {
            mPointPaint.setColor(Color.WHITE);
        } else {
            mPointPaint.setColor(Color.GRAY);
        }
        List<Calendar.Scheme> schemes = calendar.getSchemes();
        if (schemes != null && schemes.size() > 0) {
           // Log.d("tag", schemes.toString());
            if (schemes.size() == 1) {
                mPointPaint.setColor(schemes.get(0).getShcemeColor());
                canvas.drawCircle(x + mItemWidth / 2, mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);
            }
            if (schemes.size() == 2) {
                mPointPaint.setColor(schemes.get(0).getShcemeColor());
                canvas.drawCircle(x - 10 + mItemWidth / 2, mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);
                mPointPaint.setColor(schemes.get(1).getShcemeColor());
                canvas.drawCircle(x + 10 + mItemWidth / 2, mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);
            }
            if (schemes.size() == 3) {
                mPointPaint.setColor(schemes.get(0).getShcemeColor());
                canvas.drawCircle(x - 20 + mItemWidth / 2, mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);
                mPointPaint.setColor(schemes.get(1).getShcemeColor());
                canvas.drawCircle(x + mItemWidth / 2, mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);
                mPointPaint.setColor(schemes.get(2).getShcemeColor());
                canvas.drawCircle(x + 20 + mItemWidth / 2, mItemHeight - 3 * mPadding, mPointRadius, mPointPaint);
            }
        }


    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        int top = -mItemHeight / 6;

        if (calendar.isCurrentDay() && !isSelected) {
            canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);
        }
        if (calendar.isCurrentDay() && isSelected) {//今天被选中
            Paint paint=new Paint();

            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            //  paint.setColor(0xFFeaeaea);
            Shader mShader = new LinearGradient(cx-mRadius,cy , cx+mRadius, cy, new int[]{Color.parseColor("#FF6243"), Color.parseColor("#FF0072")}, new float[]{0,1.0f}, Shader.TileMode.CLAMP);
            paint.setShader(mShader);
            canvas.drawCircle(cx, cy, mRadius, paint);
        }


//        if (hasScheme && !TextUtils.isEmpty(calendar.getScheme())) {//判断不为空
//            canvas.drawCircle(x + mItemWidth - mPadding - mCircleRadius / 2, mPadding + mCircleRadius, mCircleRadius, mSchemeBasicPaint);
//
//            mTextPaint.setColor(calendar.getSchemeColor());
//
//            canvas.drawText(calendar.getScheme(), x + mItemWidth - mPadding - mCircleRadius, mPadding + mSchemeBaseLine, mTextPaint);
//        }

//        if (calendar.isWeekend() && calendar.isCurrentMonth()) {//设置周末颜色
//            mCurMonthTextPaint.setColor(0xFF489dff);
//            mCurMonthLunarTextPaint.setColor(0xFF489dff);
//            mSchemeTextPaint.setColor(0xFF489dff);
//            mSchemeLunarTextPaint.setColor(0xFF489dff);
//            mOtherMonthLunarTextPaint.setColor(0xFF489dff);
//            mOtherMonthTextPaint.setColor(0xFF489dff);
//        } else {
            mCurMonthTextPaint.setColor(0xff333333);
            mCurMonthLunarTextPaint.setColor(0xffCFCFCF);
            mSchemeTextPaint.setColor(0xff333333);
            mSchemeLunarTextPaint.setColor(0xffCFCFCF);

            mOtherMonthTextPaint.setColor(0xFFe1e1e1);
            mOtherMonthLunarTextPaint.setColor(0xFFe1e1e1);
     //   }

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    mSelectTextPaint);
            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + mItemHeight / 10, mSelectedLunarTextPaint);
        } else if (hasScheme) {

            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + mItemHeight / 10,
                    !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint : mSchemeLunarTextPaint);
        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);

            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + mItemHeight / 10,
                    calendar.isCurrentDay() ? mCurDayLunarTextPaint :
                            !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint :
                                    calendar.isCurrentMonth() ?
                                            mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
