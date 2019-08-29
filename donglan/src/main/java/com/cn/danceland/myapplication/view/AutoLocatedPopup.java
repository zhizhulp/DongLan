package com.cn.danceland.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.PublishActivity;
import com.cn.danceland.myapplication.utils.DensityUtils;

import razerdp.basepopup.BasePopupWindow;


/**
 * Created by 大灯泡 on 2016/11/23.
 * <p>
 * 自动定位的popup，空间不足显示在上面
 */
public class AutoLocatedPopup extends BasePopupWindow implements View.OnClickListener {
    private Context context;

    private View popupView;

    public AutoLocatedPopup(Activity context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //setAutoLocatePopup(true);
        bindEvent();
        this.context=context;
    }



    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_menu, null);
        return popupView;
    }

    @Override
    protected Animation initShowAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, -DensityUtils.dp2px(getContext(), 350f), 0);
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(1));
        return translateAnimation;
    }

    //
    @Override
    protected Animation initExitAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0, -DensityUtils.dp2px(getContext(), 350f));
        translateAnimation.setDuration(450);
        translateAnimation.setInterpolator(new OvershootInterpolator(-4));
        return translateAnimation;
    }


    @Override
    public View initAnimaView() {
        return popupView.findViewById(R.id.popup_contianer);
     //   return null;
    }

    private void bindEvent() {
        if (popupView != null) {
            popupView.findViewById(R.id.tx_1).setOnClickListener(this);
            popupView.findViewById(R.id.tx_2).setOnClickListener(this);
            //  popupView.findViewById(R.id.tx_3).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_1:

                //ToastUtils.showToastShort("发布动态");
                Intent intent = new Intent(context, PublishActivity.class);
                intent.putExtra("isPhoto", "0");
                context.startActivity(intent);
                dismiss();

                break;
            case R.id.tx_2:

                Intent intent1 = new Intent(context, PublishActivity.class);
                intent1.putExtra("isPhoto", "1");
                context.startActivity(intent1);
                //ToastUtils.showToastShort("发布视频");
                dismiss();
                break;

            default:
                break;
        }

    }
}
