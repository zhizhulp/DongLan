package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.PxUtils;

import java.util.List;

/**
 * Created by feng on 2018/6/13.
 */

public class CustomLine extends View {

    private int width, height;
    private float padding, margin;//每条线的间距10dp
    private int top, courseTimeLength;//起始y
    private Paint paintLine;
    private Context context;
    private int startX;
    private List<Integer> positionList, statusList, roleList, textPositionList;
    private Paint paintRectSure, paintRectNoSure, paintRectCancel, paintText,paintCancelText;
    private int preposition;

    public CustomLine(Context context) {
        super(context);
        this.context = context;
        initPaint();
    }

    public CustomLine(Context context, List<Integer> positionList, List<Integer> statusList, List<Integer> roleList, List<Integer> textPositionList, int courseTimeLength) {
        super(context);
        this.context = context;
        this.positionList = positionList;
        this.statusList = statusList;
        this.roleList = roleList;
        this.textPositionList = textPositionList;
        this.courseTimeLength = courseTimeLength;
        initPaint();
    }

    public CustomLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
    }

    public CustomLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }


    private void initPaint() {
        PorterDuffXfermode porterDuffXfermode=new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
        //设置画笔基本属性
        paintLine = new Paint();
        paintLine.setAntiAlias(true);//抗锯齿功能
        paintLine.setColor(Color.GRAY);  //设置画笔颜色
        paintLine.setStyle(Paint.Style.FILL);//设置填充样式   Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
        paintLine.setStrokeWidth(1);//设置画笔宽度

        paintRectSure = new Paint();//已确认颜色的画笔
        paintRectSure.setAntiAlias(true);
        paintRectSure.setColor(getResources().getColor(R.color.color_dl_deep_blue));
        paintRectSure.setStyle(Paint.Style.FILL);
        paintRectSure.setXfermode(porterDuffXfermode);

        paintRectNoSure = new Paint();//等待确认颜色的画笔
        paintRectNoSure.setAntiAlias(true);
        paintRectNoSure.setColor(Color.parseColor("#87CEFA"));
        paintRectNoSure.setStyle(Paint.Style.FILL);
        paintRectNoSure.setXfermode(porterDuffXfermode);


        paintRectCancel = new Paint();//已取消和已签到颜色的画笔
        paintRectCancel.setAntiAlias(true);
        paintRectCancel.setColor(getResources().getColor(R.color.color_dl_deep_blue));
        paintRectCancel.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRectCancel.setStrokeWidth(10);
        paintRectCancel.setXfermode(porterDuffXfermode);



        paintText = new Paint();//已取消和已签到颜色的画笔
        paintText.setAntiAlias(true);
        paintText.setColor(getResources().getColor(R.color.white));
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setTextSize(DensityUtils.sp2px(context,14f));

        paintCancelText = new Paint();//已取消和已签到颜色的画笔
        paintCancelText.setAntiAlias(true);
        paintCancelText.setColor(getResources().getColor(R.color.color_dl_deep_blue));
        paintCancelText.setStyle(Paint.Style.FILL);
        paintCancelText.setTextAlign(Paint.Align.CENTER);
        paintCancelText.setTextSize(DensityUtils.sp2px(context,14f));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(DensityUtils.dp2px(context,84*10f+50),
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, PxUtils.dp2px(context, (float) 12.5), r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.i("onDraw");

        for (int i = 0; i < 84; i++) {
            if (positionList != null && statusList != null && roleList != null) {
                if (positionList.get(i) != 999) {
                    if (statusList.get(i) == 1) {
                        Shader mShader = new LinearGradient(0, PxUtils.dp2px(context, padding), width, PxUtils.dp2px(context, padding) + PxUtils.dp2px(context, 10), new int[]{ Color.parseColor("#FF6243"),Color.parseColor("#FF0072")}, new float[]{0,1.0f}, Shader.TileMode.CLAMP);
                        paintRectNoSure.setShader(mShader);
                        canvas.drawRect(0, PxUtils.dp2px(context, padding), width, PxUtils.dp2px(context, padding) + PxUtils.dp2px(context, 10), paintRectNoSure);
                    } else if (statusList.get(i) == 2) {
                        canvas.drawRect(0, PxUtils.dp2px(context, padding), width, PxUtils.dp2px(context, padding) + PxUtils.dp2px(context, 10), paintRectSure);
                    } else if (statusList.get(i) == 3) {
                        paintRectCancel.setColor(getResources().getColor(R.color.white));
                        paintRectCancel.setStyle(Paint.Style.FILL);
                        canvas.drawRect(0, PxUtils.dp2px(context, padding), width, PxUtils.dp2px(context, padding) + PxUtils.dp2px(context, 10), paintRectCancel);
                        paintRectCancel.setColor(getResources().getColor(R.color.color_dl_deep_blue));
                        paintRectCancel.setStyle(Paint.Style.STROKE);
                        paintRectCancel.setStrokeWidth(10);
                        canvas.drawRect(0, PxUtils.dp2px(context, padding), width, PxUtils.dp2px(context, padding) + PxUtils.dp2px(context, 10), paintRectCancel);
                    } else if (statusList.get(i) == 4) {
                        canvas.drawRect(0, PxUtils.dp2px(context, padding), width, PxUtils.dp2px(context, padding) + PxUtils.dp2px(context, 10), paintRectSure);
                    }
                }
            }

            if (i % 6 == 0) {
                canvas.drawLine(0, PxUtils.dp2px(context, padding), width, PxUtils.dp2px(context, padding), paintLine);
            } else {
                startX = 0;
                for (int j = 0; j < 31; j++) {
                    canvas.drawLine(startX, PxUtils.dp2px(context, padding), startX + (width / 60), PxUtils.dp2px(context, padding), paintLine);
                    startX = startX + (width / 60) + (width / 60);
                }
            }
            padding = padding + 10;
            LogUtil.i(i+"跟线");
        }
        canvas.drawLine(0, PxUtils.dp2px(context, padding), width, PxUtils.dp2px(context, padding), paintLine);

        if (textPositionList != null && textPositionList.size() > 0) {
            for (int i = 0; i < textPositionList.size(); i++) {
                if (textPositionList.get(i) != 999) {
                    if (statusList.get(textPositionList.get(i)) == 1) {
                        canvas.drawText("等待对方确认", width / 2, PxUtils.dp2px(context, positionList.get(textPositionList.get(i)) * 10 + courseTimeLength * 5), paintText);
                    } else if (statusList.get(textPositionList.get(i)) == 2) {
                        if (roleList.get(textPositionList.get(i)) == 1) {
                            canvas.drawText("该时间段已被预约", width / 2, PxUtils.dp2px(context, positionList.get(textPositionList.get(i)) * 10 + courseTimeLength * 5), paintText);
                        } else if (roleList.get(textPositionList.get(i)) == 2) {
                            canvas.drawText("已确认未签到", width / 2, PxUtils.dp2px(context, positionList.get(textPositionList.get(i)) * 10 + courseTimeLength * 5), paintText);
                        }
                    } else if (statusList.get(textPositionList.get(i)) == 3) {
                        canvas.drawText("已取消", width / 2, PxUtils.dp2px(context, positionList.get(textPositionList.get(i)) * 10 + courseTimeLength * 5), paintCancelText);
                    } else if (statusList.get(textPositionList.get(i)) == 4) {
                        canvas.drawText("已签到", width / 2, PxUtils.dp2px(context, positionList.get(textPositionList.get(i)) * 10 + courseTimeLength * 5), paintText);
                    }
                }
            }
        }
    }
}
