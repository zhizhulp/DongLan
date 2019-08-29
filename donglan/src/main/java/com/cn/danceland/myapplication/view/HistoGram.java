package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.panosdk.plugin.indoor.util.ScreenUtils;
import com.cn.danceland.myapplication.R;

import java.text.NumberFormat;

/**
 * Created by ZHANGZDon 2016/6/16 0016.
 * 柱状图
 */
public class HistoGram extends View implements Runnable {
    private Handler handler = new Handler(); // 用于延时更新，实现动画
    private float animHeight; // 进度条动画高度
    private Paint axisLinePaint; // 坐标轴画笔
    private Paint hLinePaint; // 内部水平虚线画笔
    private Paint textPaint; // 绘制文本的画笔
    private Paint recPaint; // 绘制柱状图阴影背景的画笔
    private Paint dataPaint; // 绘制柱状图的画笔
    private Paint textPaint2; // 绘制白色文本的画笔
    private Paint textPaint3; // 绘制坐标的画笔
    private Paint textPaint4; // 绘制x轴上的白色竖线的画笔
    private String[] xTitleString; // x轴刻度
    private String[] yTitleString; // y轴刻度
    private String[] data; // 接口返回的indicatordata，用于计算柱子高度
    NumberFormat numberFormat; //用于格式化数字
    private float currentHeight; // 当前柱状图应有的高度，应由计算得来
    private int num = -1; // 画多少条柱子，因为存在刚开机数据不足24条的情况
    private float mRelativePxInHeight;
    private float mRelativePxInWidth;
    private OnChartClickListener listener;
    private int mDist;

    public void setNum(int num) {
        this.num = num;
        invalidate();
    }

    public void setData(String[] data) {
        this.data = data;
        invalidate();
    }

    public void setxTitleString(String[] title) {
        this.xTitleString = title;
        invalidate();
    }

    public HistoGram(Context context) {
        this(context, null);
    }

    public HistoGram(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setTitle(String[] title) {
        this.xTitleString = title;
    }

    public HistoGram(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 进行相关初始化操作
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        axisLinePaint = new Paint();
        hLinePaint = new Paint();
        textPaint = new Paint();
        recPaint = new Paint();
        dataPaint = new Paint();
        textPaint2 = new Paint();
        textPaint3 = new Paint();
        textPaint4 = new Paint();
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(3); //设置打印时保留三位小数
//        axisLinePaint.setColor(Color.parseColor("#dbdde4")); //设置坐标轴的颜色为白色
        axisLinePaint.setColor(context.getResources().getColor(R.color.color_dl_yellow)); //设置坐标轴的颜色为白色
        hLinePaint.setARGB(51, 255, 255, 255);
//        textPaint.setColor(Color.parseColor("#8593a1"));
        textPaint.setColor(context.getResources().getColor(R.color.color_tab_text_yellow));
//    textPaint.setTextSize(29);
        textPaint.setTextSize(ScreenUtils.dip2px(12, getContext()));
        recPaint.setColor(Color.TRANSPARENT);
        dataPaint.setColor(Color.CYAN);
        textPaint2.setColor(Color.WHITE);
        textPaint2.setTextSize(ScreenUtils.dip2px(12, getContext()));
        textPaint3.setColor(context.getResources().getColor(R.color.colorGray9));
        textPaint3.setTextSize(ScreenUtils.dip2px(9, getContext()));
        textPaint4.setColor(Color.parseColor("#8593a1"));
        textPaint4.setTextSize(ScreenUtils.dip2px(6, getContext()));
        axisLinePaint.setAntiAlias(true);
        hLinePaint.setAntiAlias(true);
        textPaint.setAntiAlias(true);
        recPaint.setAntiAlias(true);
        dataPaint.setAntiAlias(true);
        textPaint2.setAntiAlias(true);
        textPaint3.setAntiAlias(true);
        textPaint4.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null || xTitleString == null || num < 0) {
            return;
        }
        //绘制y轴刻度
        Paint.FontMetrics metrics = textPaint3.getFontMetrics();
        int decent = (int) metrics.descent;
        float width = getWidth();
        float height = getHeight();
        //根据原型图得出，图中每px高度在实际中的相对尺寸
        mRelativePxInHeight = height / 470;
        //根据原型图得出，图中每px宽度在实际中的相对尺寸
        mRelativePxInWidth = width / 690;
        textPaint3.setTextAlign(Paint.Align.RIGHT);
        //绘制纵坐标
        yTitleString = new String[6];
        yTitleString[5] = "0";
        yTitleString[4] = "20";
        yTitleString[3] = "40";
        yTitleString[2] = "60";
        yTitleString[1] = "80";
        yTitleString[0] = "100";
        for (int i = 0; i < yTitleString.length; i++) {
            canvas.drawText(yTitleString[i], 88 * mRelativePxInWidth, (72 + i * 56) * mRelativePxInHeight + decent, textPaint3);
        }
        //绘制x轴刻度
        textPaint3.setTextAlign(Paint.Align.CENTER);
        textPaint4.setTextAlign(Paint.Align.CENTER);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setTextSize(ScreenUtils.dip2px(9, getContext()));
        //计算柱子之间的间隔
        //最左侧位置100 * mRelativePxInWidth,最右侧位置630 ePxInWidth,
        float totalWidth = 630 - 100;
        // 柱子与之子之间的间隔
        mDist = (int) (totalWidth / (xTitleString.length + 1));
        for (int i = 0; i < xTitleString.length; i++) {
            //绘制白色竖线
            canvas.drawLine((100 + (i + 1) * mDist) * mRelativePxInWidth, 348 * mRelativePxInHeight, (100 + (i + 1) * mDist) * mRelativePxInWidth, 352 * mRelativePxInHeight, axisLinePaint);
            //绘制x轴文字
            canvas.drawText(xTitleString[i], (100 + (i + 1) * mDist) * mRelativePxInWidth, 370 * mRelativePxInHeight, textPaint3);
        }
//    绘制矩形阴影
        for (int i = 0; i < num; i++) {
            RectF rectF = new RectF();
//      rectF.left = 111 * relativePxInWidth + i * 22 * relativePxInWidth;
//      rectF.right = 121 * relativePxInWidth + i * 22 * relativePxInWidth;
            rectF.left = 95 * mRelativePxInWidth + (i + 1) * mDist * mRelativePxInWidth;
            rectF.right = 105 * mRelativePxInWidth + (i + 1) * mDist * mRelativePxInWidth;
            rectF.top = 70 * mRelativePxInHeight;
            rectF.bottom = 338 * mRelativePxInHeight;
            canvas.drawRoundRect(rectF, 0, 0, recPaint);
//            canvas.drawRoundRect(rectF, 10, 10, recPaint);
        }
        //    绘制x轴坐标线
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(100 * mRelativePxInWidth, (66 + i * 56) * mRelativePxInHeight + decent, 630 * mRelativePxInWidth, (66 + i * 56) * mRelativePxInHeight + decent, axisLinePaint);
        }
//    延时绘制，实现动画效果。数字越大，延时越久，动画效果就会越慢
        handler.postDelayed(this, 1);
        for (int i = 0; i < num; i++) {
            RectF dataRectF = new RectF();
            dataRectF.left = 95 * mRelativePxInWidth + (i + 1) * mDist * mRelativePxInWidth;
            dataRectF.right = 105 * mRelativePxInWidth + (i + 1) * mDist * mRelativePxInWidth;
            dataPaint.setColor(Color.parseColor("#3ac2d9"));
            //获取柱子高度
            currentHeight = Float.parseFloat(data[num - 1 - i]);
            if (currentHeight == 0) {
                dataRectF.top = 346 * mRelativePxInHeight;
            } else if (currentHeight == 100) {
                dataRectF.top = 70 * mRelativePxInHeight;
            } else {
                if (animHeight >= currentHeight) {
                    dataRectF.top = 346 * mRelativePxInHeight - currentHeight / 100 * 276 * mRelativePxInHeight;
                } else {
                    dataRectF.top = 346 * mRelativePxInHeight - 276 * mRelativePxInHeight * (animHeight / 100);
                }
            }
            dataRectF.bottom = 346 * mRelativePxInHeight;
//        限制最高高度
            if (dataRectF.top < 70 * mRelativePxInHeight) {
                dataRectF.top = 70 * mRelativePxInHeight;
            }
//            canvas.drawRoundRect(dataRectF, 10, 10, dataPaint);
            canvas.drawRoundRect(dataRectF, 0, 0, dataPaint);
        }
    }

    //实现柱子增长的动画效果
    @Override
    public void run() {
        animHeight += 1;
        if (animHeight >= 276 * mRelativePxInHeight) {
            return;
        } else {
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //获取点击坐标
                float x = event.getX();
                float y = event.getY();
                //判断点击点的位置
                float leftx = 0;
                float rightx = 0;
                for (int i = 0; i < num; i++) {
                    leftx = 95 * mRelativePxInWidth + (i + 1) * mDist * mRelativePxInWidth - mDist / 2 * mRelativePxInWidth;
                    rightx = 105 * mRelativePxInWidth + (i + 1) * mDist * mRelativePxInWidth + mDist / 2 * mRelativePxInWidth;
                    if (x < leftx) {
                        continue;
                    }
                    if (leftx <= x && x <= rightx) {
                        //获取点击的柱子区域的y值
                        float top = 346 * mRelativePxInHeight - Float.parseFloat(data[num - 1 - i]) / 100 * 276 * mRelativePxInHeight;
                        float bottom = 346 * mRelativePxInHeight;
                        if (y >= top && y <= bottom) {
                            //判断是否设置监听
                            //将点击的第几条柱子，点击柱子顶部的坐值，用于弹出dialog提示数据，还要返回百分比currentHeidht = Float.parseFloat(data[num - 1 - i])
                            if (listener != null) {
                                Log.e("ss", "x" + x + ";y:" + y);
                                listener.onClick(i + 1, leftx + mDist / 2, top, Float.parseFloat(data[num - 1 - i]));
                            }
                            break;
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:
                Log.e("touch", "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("touch", "ACTION_UP");
                break;
        }
        return true;
    }

    /**
     * 柱子点击时的监听接口
     */
    public interface OnChartClickListener {
        void onClick(int num, float x, float y, float value);
    }

    /**
     * 设置柱子点击监听的方法
     *
     * @param listener
     */
    public void setOnChartClickListener(OnChartClickListener listener) {
        this.listener = listener;
    }
}