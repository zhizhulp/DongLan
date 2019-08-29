package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.cn.danceland.myapplication.shouhuan.utils.OnColumnClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by yxx on 2016/8/12.
 */
public class ColumnView extends View {
    private Thread mThread;
    /**
     * 标题
     */
    private String title = "柱状图";
    /***/
    private Paint mPaint;
    private String emptyWarning = "没有数据";
    private Context mContext;
    private ArrayList<HashMap<String, String>> mDataList;
    private ArrayList<HashMap<String, Float>> mLocationList;
    private int[] colors;
    /**
     * 整个空间的宽度
     */
    private int width;
    /**
     * 整个空间的高度
     */
    private int height;
    /**
     * 箭头宽度
     */
    private int arrowWidth = 4;
    /**
     * 坐标原点距离边界
     */
    private int distance = 10;
    /**
     * 底部辅助横线条数
     */
    private int horizontalLineCount = 10;
    /**
     * 柱形图的宽度
     */
    private float ZhuZhuangTuWidth;
    /**
     * 水平线间隔
     */
    private float horizontalLineInterval;
    /**
     * 柱状图间隔
     */
    private float ZhuZhuangTuInterval;
    private Random mRandom;
    /**用来做动画的可变值*/
//    private float delta;
    /**
     * 用来做动画的线程while的flag
     */
    private boolean run = true;
    /**
     * 动画时长
     */
    private int millions = 6000;
    /**
     * 回调
     */
    private OnColumnClickListener mListener;
    /**
     * 设备密度
     */
    private float density;
    /**
     * 存放所有的delta
     */
    private float[] deltas;
    /**
     * 最大值出现的位置
     */
    private int maxValuePosition;

    public ColumnView(Context context) {
        super(context);
        init(context);
    }

    public ColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mPaint = new Paint();
        mLocationList = new ArrayList<>();
        run = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setWillNotDraw(false);
        if (isDataMapEmpty()) {
            canvas.drawColor(Color.GRAY);
            mPaint.setColor(Color.MAGENTA);
            mPaint.setTextSize(40f);
            canvas.drawText(emptyWarning, width / 2 - mPaint.getTextSize() * emptyWarning.length() / 2, height / 2 - mPaint.getTextSize() / 2, mPaint);
        } else {
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(4);
            //绘制x轴
            canvas.drawLine(distance * density, height - distance * density, width - distance * density, height - distance * density, mPaint);
            //绘制x轴箭头
            canvas.drawLine(width - distance * density - arrowWidth * density, height - distance * density - arrowWidth * density, width - distance * density, height - distance * density, mPaint);
            canvas.drawLine(width - distance * density - arrowWidth * density, height - distance * density + arrowWidth * density, width - distance * density, height - distance * density, mPaint);
            //绘制y轴
            canvas.drawLine(distance * density, distance * density, distance * density, height - distance * density, mPaint);
            //绘制y轴箭头
            canvas.drawLine(distance * density - arrowWidth * density, distance * density + arrowWidth * density, distance * density, distance * density, mPaint);
            canvas.drawLine(distance * density + arrowWidth * density, distance * density + arrowWidth * density, distance * density, distance * density, mPaint);
            //绘制底部阶梯标准线
            horizontalLineInterval = (height - 2 * distance * density) / horizontalLineCount;
            mPaint.setColor(Color.GRAY);
            mPaint.setStrokeWidth(1);
            float[] arr = getMaxValue(mDataList);
            if (arr[1] == 0) {
                return;
            }
            float average = arr[0] / (horizontalLineCount);
            mPaint.setTextSize(18);
            for (int i = 1; i <= horizontalLineCount; i++) {
                canvas.drawLine(distance * density, height - distance * density - horizontalLineInterval * i, width - distance * density, height - distance * density - horizontalLineInterval * i, mPaint);
                canvas.drawText(average * i + "", 5f, height - distance * density - horizontalLineInterval * i + mPaint.getTextSize() / 2, mPaint);
            }
            canvas.drawText("0", 5f, height - distance * density + mPaint.getTextSize() / 2, mPaint);
            mPaint.setStrokeWidth(3f);
            ZhuZhuangTuInterval = (width - 2 * distance * density) / mDataList.size();
            ZhuZhuangTuWidth = ZhuZhuangTuInterval / 1;
            mLocationList.clear();
            for (int i = 0; i < mDataList.size(); i++) {
                mPaint.setColor(colors[i]);
                float value = Float.parseFloat(mDataList.get(i).get("value").toString());
                //从上到下的动画
//                canvas.drawRect(distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 - ZhuZhuangTuWidth / 2,
//                        height - distance * density - (height - 2 * distance * density) * (value / arr[0]),
//                        distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 + ZhuZhuangTuWidth / 2
//                        , height - distance * density - delta, mPaint);
                //从下到上的动画,所有的图形的绘制时间都是一样的(delta一样),也就是说所有柱子同时绘制完成,长的绘制早,短的绘制晚.
                canvas.drawRect(distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 - ZhuZhuangTuWidth / 2,
                        height - distance * density - (height - 2 * distance * density) * (value / arr[0]) + deltas[i],
                        distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 + ZhuZhuangTuWidth / 2
                        , height - distance * density, mPaint);
                //若要所有的柱子同时开始绘制,需要给每个柱子添加一个delta.并且要对动画时间分别控制.
//                System.out.println(".........."+deltas[i]);
                HashMap<String, Float> map = new HashMap<>();
                map.put("left", distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 - ZhuZhuangTuWidth / 2);
                map.put("top", height - distance * density - (height - 2 * distance * density) * (value / arr[0]));
                map.put("right", distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 + ZhuZhuangTuWidth / 2);
                map.put("bottom", height - distance * density + deltas[i]);
                mLocationList.add(map);
                mPaint.setColor(Color.BLACK);
                mPaint.setTextSize(20);
                String name = mDataList.get(i).get("name").toString();
                canvas.drawText(name, distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 - (name.length() / 2) * mPaint.getTextSize(),
                        height - distance * density + mPaint.getTextSize(), mPaint);
                String unit = mDataList.get(i).get("unit").toString();
                String describe = value + unit;
                canvas.drawText(describe,
                        distance * density + ZhuZhuangTuInterval * i + ZhuZhuangTuInterval / 2 - ZhuZhuangTuWidth / 2,
                        height - distance * density - (height - 2 * distance * density) * (value / arr[0]), mPaint);
            }
            mPaint.setColor(Color.BLACK);
            canvas.drawText("unit:" + mDataList.get(0).get("unit").toString(), 0, distance * density / 2, mPaint);
            mPaint.setTextSize(30);
            canvas.drawText(title, width / 2 - title.length() / 2 * mPaint.getTextSize(), distance * density, mPaint);
        }
        if (stop(deltas) && !mThread.isAlive()) {
            if (null != mListener) {
                mListener.onDrawFinished();
            }
        }
    }

    /**
     * 是否为空,同来判断绘制是否完成
     */
    private boolean stop(float[] arr) {
        for (float f : arr) {
            if (f != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                for (int i = 0; i < mLocationList.size(); i++) {
                    if (x >= mLocationList.get(i).get("left") && x <= mLocationList.get(i).get("right")
                            && y >= mLocationList.get(i).get("top") && y <= mLocationList.get(i).get("bottom")) {
                        if (null != mListener) {
                            mListener.onColumnClick(i, mLocationList.get(i));
                        }
                    }
                }


                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取集合中的最大值
     */
    private float[] getMaxValue(ArrayList<HashMap<String, String>> list) {
        try {
            float max = Float.parseFloat(list.get(0).get("value").toString());
            for (int i = 0; i < list.size(); i++) {
                float temp = Float.parseFloat(list.get(i).get("value").toString());
                if (temp > max) {
                    max = temp;
                }
            }
            return new float[]{max, 1};
        } catch (Exception e) {
            return new float[]{-1, 0};
        }
    }

    /**
     * 获取最大值出现的位置
     */
    private int getMaxValuePosition(ArrayList<HashMap<String, String>> list) {
        try {
            int j = 0;
            float max = Float.parseFloat(list.get(0).get("value").toString());
            for (int i = 0; i < list.size(); i++) {
                float temp = Float.parseFloat(list.get(i).get("value").toString());
                if (temp > max) {
                    j = i;
                }
            }
            return j;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 设置数据
     */
    public void setDataMap(ArrayList<HashMap<String, String>> list) {
        if (null == list || list.size() == 0) {
            return;
        }
        this.mDataList = list;
        colors = new int[mDataList.size()];
        mRandom = new Random();
        for (int i = 0; i < mDataList.size(); i++) {
            int r = mRandom.nextInt(256);
            int g = mRandom.nextInt(256);
            int b = mRandom.nextInt(256);
            int color = Color.rgb(r, g, b);
            colors[i] = color;
        }
    }

    /**
     * 设置动画时间
     */
    public void setAnimationDurtion(int millions) {
        this.millions = millions;
    }

    /**
     * 监察数据集合是否为空
     */
    private boolean isDataMapEmpty() {
        if (null == mDataList || mDataList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置回调
     */
    public void setOnColumnClickListener(OnColumnClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();
        density = getDensity();
        maxValuePosition = getMaxValuePosition(mDataList);
//        delta = height - 2*distance*density;
        //获取所有的delta
        if (!isDataMapEmpty()) {
            deltas = new float[mDataList.size()];
            float m = getMaxValue(mDataList)[0];
            for (int i = 0; i < mDataList.size(); i++) {
                float v = Float.parseFloat(mDataList.get(i).get("value").toString());
                float delta = (height - 2 * distance * getDensity()) * (v / m);
                deltas[i] = delta;
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (run) {
                    try {
                        Thread.sleep(15);
                        for (int i = 0; i < deltas.length; i++) {
                            //                        delta = delta - (height - 2*distance*density)/(millions/20);//做向下的动画时使用
                            float delta = deltas[i];
                            float m = getMaxValue(mDataList)[0];
                            float v = Float.parseFloat(mDataList.get(i).get("value").toString());
                            float time = millions * (v / m);
                            if (time <= 800) {
                                time = 800;
                            }
                            delta = delta - (height - 2 * distance * density) * (v
                                    / m) / (time / 15);
                            if (delta <= 0) {
                                delta = 0;
                                if (i == maxValuePosition) {
                                    run = false;
                                }
                            }
                            deltas[i] = delta;
                        }
                        postInvalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }

    /**
     * 获取设备密度
     */
    private float getDensity() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }

    /**
     * 为柱状图设置标题
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
