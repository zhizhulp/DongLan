package com.cn.danceland.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;

/**
 * 统一提示框 居中有个hint
 * Created by yxx on 2018-11-13.
 */

public class AlertDialogCustomToHint {
    private Context mcontext;
    private MyDialog builders;
    private View layout;
    private TextView  alert_message;
    private Button btn_cancel;
    private Button btn_ok;
    private Click click;
    private String ok, cancle;
    private RelativeLayout dialogButtonDividerR;//两个按钮之间的线

    public AlertDialogCustomToHint(String ok, String cancle) {
        this.ok = ok;
        this.cancle = cancle;
    }

    public MyDialog CreateDialog(Context mcontext,  String hintContent, Click click) {
        this.mcontext = mcontext;
        this.click = click;
        LayoutInflater inflater = (LayoutInflater)
                mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.notice_dialog_to_hint, null);
        builders = new MyDialog(mcontext, 0, 0, layout, R.style.load_dialog);
        initView();
        alert_message.setText(hintContent);
        Show();
        return builders;
    }

    public class MyDialog extends Dialog {

        private static final int default_width = 311; //默认宽度
        private static final int default_height = 171;//默认高度

        public MyDialog(Context context, View layout, int style) {
            this(context, default_width, default_height, layout, style);
        }

        public MyDialog(Context context, int width, int height, View layout, int style) {
            super(context, style);
            setContentView(layout);
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            float widthdp = window.getWindowManager().getDefaultDisplay().getWidth();
            float heightdp = window.getWindowManager().getDefaultDisplay().getHeight();
            params.width = (int) (widthdp * 0.8);
//            params.height = (int) (heightdp * 0.25);
//            params.width = (DensityUtils.dp2px(context, Float.valueOf(width)));
//            params.height = (DensityUtils.dp2px(context, Float.valueOf(height)));

            window.setAttributes(params);
        }

        /**
         * 隐藏取消按钮
         */
        public void GoneCancel() {
            if (btn_cancel != null) {
                btn_cancel.setVisibility(View.GONE);
            }
            if (dialogButtonDividerR != null) {
                dialogButtonDividerR.setVisibility(View.GONE);
            }
        }
    }

    private void initView() {
        btn_ok = (Button) layout.findViewById(R.id.dialogOKB);
        btn_cancel = (Button) layout.findViewById(R.id.btn_cancel);
        alert_message = (TextView) layout.findViewById(R.id.alert_message);
        dialogButtonDividerR = (RelativeLayout) layout.findViewById(R.id.dialogButtonDividerR);
        btn_ok.setOnClickListener(new setonclicklistener(1));
        btn_cancel.setOnClickListener(new setonclicklistener(0));
        if (ok != null)
            btn_ok.setText(ok);
        if (cancle != null) {
            btn_cancel.setText(cancle);
        } else {
            btn_cancel.setVisibility(View.GONE);
            if (dialogButtonDividerR != null) {
                dialogButtonDividerR.setVisibility(View.GONE);
            }
        }

    }

    class setonclicklistener implements View.OnClickListener {

        private int id;

        public setonclicklistener(int id) {
            // TODO Auto-generated constructor stub
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.dialogOKB:
                    click.ok_bt(1);
                    break;
                case R.id.btn_cancel:
                    click.cancle_bt(0);
                    break;
                default:
                    break;
            }
        }
    }

    /*
     * 显示当前对话框
     * */
    public void Show() {
        if (builders != null) {
            builders.show();
        }
    }

    public interface Click {
        void cancle_bt(int btn_cancel);

        void ok_bt(int dialogOKB);//点击OK 1 点击取消 0
    }
}
