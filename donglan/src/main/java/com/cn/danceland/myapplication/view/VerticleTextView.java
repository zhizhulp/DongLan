package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * 躺着的textview  课表有用到
 * Created by yxx on 2018-11-19.
 */
public class VerticleTextView extends android.support.v7.widget.AppCompatTextView {

    final boolean topDown;

    public VerticleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final int gravity = getGravity();
        if (Gravity.isVertical(gravity) && (gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
            setGravity((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) | Gravity.CENTER);
            topDown = false;
        } else {
            topDown = true;
            setGravity((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) | Gravity.BOTTOM);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        canvas.save();

        if (topDown) {
            canvas.translate(0, getHeight() - getWidth() * 2);
            canvas.rotate(-90);
        } else {
            canvas.translate(getWidth(), 0);
            canvas.rotate(-90);
        }

        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        getLayout().draw(canvas);
        canvas.restore();
    }
}
