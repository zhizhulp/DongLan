package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.cn.danceland.myapplication.R;

/**
 * Created by feng on 2017/9/25.
 */

public class SliderView extends View {
    ViewGroup parentView;
    float parentLeft;
    float parentRight;
    float parentTop;
    float parentBottom;
    float slideLeft;
    float slideRight;
    float slideTop;
    float slideBottom;
    //定义画笔和初始位置
    Paint p = new Paint();
    public float currentX = 50;
    public float currentY = 30;
    public int textColor;

    public SliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取资源文件里面的属性，由于这里只有一个属性值，不用遍历数组，直接通过R文件拿出color值
        //把属性放在资源文件里，方便设置和复用
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SliderView);
        textColor = array.getColor(R.styleable.SliderView_TextColor, Color.BLACK);

        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        parentView = (ViewGroup) getParent();
        parentLeft = parentView.getLeft();
        parentRight = parentView.getRight();
        parentTop = parentView.getTop();
        parentBottom = parentView.getBottom();
        p.setColor(Color.argb(50, 240,255,255));  //设置画笔颜色
        p.setStyle(Paint.Style.FILL);//设置填充样式
        p.setStrokeWidth(15);//设置画笔宽度
//        if(currentX==0.0){
//            currentX = parentLeft+(parentRight-parentLeft)/4;
//        }
//        if(currentY==0.0){
//            currentY = parentBottom/2;
//        }
//        slideLeft = currentX-(parentRight-parentLeft)/4;
//        slideRight = currentX+(parentRight-parentLeft)/4;
//        slideTop = currentY-parentBottom/2;
//        slideBottom = currentY+parentBottom/2;
//        if(slideLeft<parentLeft){
//            currentX = parentLeft+(parentRight-parentLeft)/4;
//            slideLeft = parentLeft;
//        }
//        if(slideRight>parentRight){
//            currentX = parentRight-(parentRight-parentLeft)/4;
//            slideRight= parentRight;
//        }
        slideLeft = currentX-(parentRight-parentLeft)/4;
        slideRight = currentX+(parentRight-parentLeft)/4;
        slideTop = currentY-parentBottom/2;
        slideBottom = currentY+parentBottom/2;

        RectF rect = new RectF(slideLeft,slideTop ,slideRight ,slideBottom );

        canvas.drawRoundRect(rect, 180, 180, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        currentX = event.getX();
        currentY = 30;//固定Y轴坐标

        invalidate();//重新绘制图形
        return true;
    }
}
