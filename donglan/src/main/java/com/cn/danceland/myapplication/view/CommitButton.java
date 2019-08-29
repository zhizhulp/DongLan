package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;

/**
 * Created by shy on 2018/10/17 09:50
 * Email:644563767@qq.com
 */


public class CommitButton extends RelativeLayout {

    private String btn_text;
    private RelativeLayout relativeLayout;
    private final View view;


    public CommitButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.my_commit_button, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.commitButton, 0, 0);
        try {
            btn_text = ta.getString(R.styleable.commitButton_text);
          setUpView();
        } finally {
            ta.recycle();
        }
    }

    private void setUpView(){
        TextView btn_content = (TextView) view.findViewById(R.id.tv_btn_text);
        btn_content.setText(btn_text);
        relativeLayout = view.findViewById(R.id.rl_btn_commit);
    }
    public void setText(String btn_text){
        this.btn_text = btn_text;
        TextView btn_content = (TextView) view.findViewById(R.id.tv_btn_text);
        btn_content.setText(btn_text);

    }
    public void setClick(View.OnClickListener listener){

        relativeLayout.setOnClickListener(listener);
    }

}
