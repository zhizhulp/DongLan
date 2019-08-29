package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.SiJiaoYuYueBean;

import java.util.ArrayList;
import java.util.List;

public class CustomLine2 extends View {
    private String TAG = "CustomLine2";
    private List<SiJiaoYuYueBean.Data> data;
    private Paint breakLinePaint;
    private Path beakLinePath;
    PathEffect pathEffect = new DashPathEffect(new float[]{10, 10}, 0);
    private Paint linePaint;
    private Paint recPaint;
    private Paint textPaint;
    private Paint timePaint;
    private Paint timeCirclePaint;
    private Paint timeLinePaint;
    private int startTime = 60 * 8;
    private int endTime = 60 * 22;
    private int totalHours = (endTime - startTime) / 10;
    private int perLineHeight;
    private int lineWidth;
    private Rect bound = new Rect();
    private String signedStr = "已签到";
    private String cancelStr = "已取消";
    private String confirmedStr = "等待对方确认";
    private String confirmedNoSignStr = "已确认未签到";
    private String groupClassStr = "团课占用";
    private int startX = getPaddingLeft();
    private int startY = getPaddingRight();
    private List<Integer> notLine;
    private Shader mShader;
    private float timeTextWidth;
    private int timeTextPadding = PxUtils.dip2px(getContext(), 6);
    private int cRadius = PxUtils.dip2px(getContext(), 7.5f);
    private GestureDetectorCompat mDetector;
    private Callback callback;

    public interface Callback {
        void onClickTime(int time);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public CustomLine2(Context context) {
        super(context);
        initPaint(context);
    }

    public CustomLine2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    public CustomLine2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
    }

    public List<SiJiaoYuYueBean.Data> getData() {
        return data;
    }

    public void setData(List<SiJiaoYuYueBean.Data> data) {
        this.data = data;
        updateUI();
    }

    public void updateUI() {
        invalidate();
    }

    private void initPaint(Context context) {
        perLineHeight = PxUtils.dip2px(context, 10);
        lineWidth = PxUtils.dip2px(context, 150);
        //画线
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.GRAY);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeWidth(PxUtils.dip2px(context, 0.5f));
        //画虚线
        breakLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        breakLinePaint.setColor(Color.GRAY);
        breakLinePaint.setStyle(Paint.Style.STROKE);
        breakLinePaint.setStrokeWidth(PxUtils.dip2px(context, 0.5f));
        breakLinePaint.setPathEffect(pathEffect);
        beakLinePath = new Path();
        //画矩形
        recPaint = new Paint();
        recPaint.setAntiAlias(true);
        recPaint.setColor(getResources().getColor(R.color.color_dl_deep_blue));
        recPaint.setStyle(Paint.Style.FILL);
        //画字
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getResources().getColor(R.color.black));
        textPaint.setTextSize(PxUtils.sp2px(context, 8));
        //画时间
        timePaint = new Paint();
        timePaint.setAntiAlias(true);
        timePaint.setColor(Color.GRAY);
        timePaint.setTextSize(PxUtils.sp2px(context, 13));
        timeTextWidth = timePaint.measureText("88:88");
        //画时间轴的圆圈
        timeCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timeCirclePaint.setColor(getResources().getColor(R.color.color_dl_yellow));
        timeCirclePaint.setStyle(Paint.Style.STROKE);
        timeCirclePaint.setStrokeWidth(PxUtils.dip2px(getContext(), 2));
        //画时间轴的线
        timeLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        timeLinePaint.setColor(Color.parseColor("#808080"));
        timeLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        timeLinePaint.setStrokeWidth(PxUtils.dip2px(context, 1f));
        mDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.d(TAG, "onDown: ");
                return super.onDown(e);
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d(TAG, "onShowPress: ");
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongPress: ");
                super.onLongPress(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG, "onFling: ");
                return super.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.d(TAG, "onScroll: ");
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d(TAG, "onSingleTapConfirmed: ");
                if (callback != null) callback.onClickTime(handleClick(e));
                return super.onSingleTapConfirmed(e);
            }
        });
    }

    private int handleClick(MotionEvent e) {
        float cY = e.getY();//12
        int re = (int) (cY - startY);//12
        int lineCount = re / perLineHeight;//1
        int b = re % perLineHeight;//2
        int result;
        if (b > perLineHeight / 2) {
            result = lineCount * 10 + 10;
        } else {
            result = lineCount * 10;
        }
        //Log.d(TAG, "当前点击的时间: " + (startTime + result) / 60 + ":" + (startTime + result) % 60);
        return result;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int newHeightSize = perLineHeight * totalHours + paddingTop + paddingBottom;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), newHeightSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        int width = getMeasuredWidth();
        lineWidth = width - paddingLeft - paddingRight - (int) timeTextWidth - 2 * timeTextPadding - 2 * cRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画方框和框内的文字
        float newStartX = timeTextWidth + startX + 2 * timeTextPadding + 2 * cRadius;
        if (notLine == null) {
            notLine = new ArrayList<>();
        } else {
            notLine.clear();
        }
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                SiJiaoYuYueBean.Data data = this.data.get(i);
                int occType = data.getOcc_type();
                int status = data.getOcc_obj_status();
                //矩形框
                int startTime = data.getStart_time();
                int endTime = data.getEnd_time();
                if (startTime < this.startTime || endTime > this.endTime) return;

                int countStart = (startTime - 480) / 10;//0
                int countEnd = (endTime - 480) / 10;//6
                for (int j = 0; j <= countEnd - countStart; j++) {//start=0 end=6
                    notLine.add(countStart + j);//0 1 2 3 4 5 6
                }
                int left = (int) newStartX;
                int top = startY + (countStart * perLineHeight);
                int right = (int) (newStartX + lineWidth);
                int bottom = startY + (countEnd * perLineHeight);
                bound.set(left, top, right, bottom);
                //文字
                textPaint.setTextSize(PxUtils.sp2px(getContext(), 14));
                Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
                int baseline = (bound.bottom + bound.top - fontMetrics.bottom - fontMetrics.top) / 2;
                textPaint.setTextAlign(Paint.Align.CENTER);
                String drawTex = "";
                if (occType == 1) {//团课排课
                    setPaint1(bottom - top);
                    drawTex = groupClassStr;
                } else if (occType == 2) {//私教预约
                    if (status == 1) {//等待对方确认
                        setPaint1(bottom - top);
                        drawTex = confirmedStr;
                    } else if (status == 2) {//已确认未签到
                        setPaint2();
                        drawTex = confirmedNoSignStr;
                    } else if (status == 3) {//已取消
                        setPaint31();
                        canvas.drawRect(bound, recPaint);
                        setPaint32();
                        drawTex = cancelStr;
                    } else if (status == 4) {//已签到
                        setPaint4();
                        drawTex = signedStr;
                    }
                } else if (occType == 3) {//团课私教预约
                    if (status == 1) {//等待对方确认
                        setPaint1(bottom - top);
                        drawTex = confirmedStr;
                    } else if (status == 2) {//已取消
                        setPaint31();
                        canvas.drawRect(bound, recPaint);
                        setPaint32();
                        drawTex = cancelStr;
                    } else if (status == 3) {//已签到
                        setPaint4();
                        drawTex = signedStr;
                    }
                } else if (occType == 4) {//免费团课报名
                    if (status == 2) {//已取消
                        setPaint31();
                        canvas.drawRect(bound, recPaint);
                        setPaint32();
                        drawTex = cancelStr;
                    }
                }
                canvas.drawRect(bound, recPaint);
                canvas.drawText(drawTex, bound.centerX(), baseline, textPaint);
            }
        }

        //画时间文字和线
        float fontHeight = getFontHeight(timePaint);
        for (int i = 0; i <= totalHours; i++) {
            int x = startTime + i * 10;//480为8点
            String i1 = (x / 60) + "";
            if (i1.length() == 1) i1 = "0" + i1;
            String showTime = i1 + " : " + x % 60 + "0";
            if (x % 60 == 0) {
                canvas.drawCircle(startX, startY + (i * perLineHeight), cRadius, timeCirclePaint);
                canvas.drawLine(startX, startY + (i * perLineHeight) + cRadius, startX, startY + ((i + 6) * perLineHeight) - cRadius, timeLinePaint);
                canvas.drawText(showTime, startX + 2 * cRadius, (float) (startY + (i * perLineHeight)) + fontHeight / 2, timePaint);
                if (notExitLine(i)) {//防止过度绘制
                    canvas.drawLine(newStartX, startY + (i * perLineHeight),
                            newStartX + lineWidth, startY + (i * perLineHeight), linePaint);
                }
            } else {
                if (notExitLine(i)) {//防止过度绘制
                    beakLinePath.reset();//不加会出现过度绘制,beakLinePath包含过多的信息
                    beakLinePath.moveTo(newStartX, startY + (i * perLineHeight));
                    beakLinePath.lineTo(newStartX + lineWidth, startY + (i * perLineHeight));
                    canvas.drawPath(beakLinePath, breakLinePaint);
                }
            }

        }
    }

    private void setPaint4() {
        recPaint.setShader(null);
        recPaint.setStyle(Paint.Style.FILL);
        recPaint.setColor(getResources().getColor(R.color.color_dl_deep_blue));
        textPaint.setColor(Color.WHITE);
    }

    private void setPaint31() {
        recPaint.setShader(null);
        recPaint.setStyle(Paint.Style.STROKE);
        recPaint.setStrokeWidth(PxUtils.dip2px(getContext(), 2));
        recPaint.setColor(getResources().getColor(R.color.color_dl_deep_blue));
    }

    private void setPaint32() {
        recPaint.setShader(null);
        recPaint.setStyle(Paint.Style.FILL);
        recPaint.setColor(Color.WHITE);
        textPaint.setColor(getResources().getColor(R.color.color_dl_deep_blue));
    }

    private void setPaint2() {
        recPaint.setShader(null);
        recPaint.setStyle(Paint.Style.FILL);
        recPaint.setColor(getResources().getColor(R.color.color_dl_deep_blue));
        textPaint.setColor(Color.WHITE);
    }

    private void setPaint1(int shaderHeight) {
        if (mShader == null) {
            mShader = new LinearGradient(0, 0, lineWidth, shaderHeight,
                    new int[]{Color.parseColor("#FF6243"), Color.parseColor("#FF0072")}, new float[]{0, 1.0f}, Shader.TileMode.CLAMP);
        }
        recPaint.setShader(mShader);
        recPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.WHITE);
    }

    private boolean notExitLine(int i) {
        if (notLine == null || notLine.size() == 0) return true;
        for (int j = 0; j < notLine.size(); j++) {
            if (i == notLine.get(j)) {
                return false;
            }
        }
        return true;
    }

    public float getFontHeight(Paint paint) {
//        Paint.FontMetrics fm = paint.getFontMetrics();//有点大
//        return fm.descent - fm.ascent;
        String text = "88:88";
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
//        return super.onTouchEvent(event);
        return true;
    }
}
