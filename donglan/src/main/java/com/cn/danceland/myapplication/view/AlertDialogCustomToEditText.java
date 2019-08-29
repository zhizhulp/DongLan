package com.cn.danceland.myapplication.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cn.danceland.myapplication.R;

/**
 * 弹框 带输入框
 * <p>
 * Created by yangxiaoxue on 2018/07/9.
 */
public class AlertDialogCustomToEditText {

    private Context mcontext;
    private MyDialog builders;
    private View layout;
    private TextView title, alert_message;
    private ContainsEmojiEditText alert_edittext;
    private Button btn_cancel;
    private Button btn_ok;
    private Click click;
    private String ok, cancle;

    public AlertDialogCustomToEditText(String ok, String cancle) {
        this.ok = ok;
        this.cancle = cancle;
    }

    /**
     * 初始化
     *
     * @param mcontext
     * @param content
     * @param edittextStr    edittext字符串
     * @param edittextLength edittext长度
     * @param click
     * @return
     */
    public MyDialog CreateDialog(Activity mcontext, String content, String edittextStr, int edittextLength, Click click) {
        this.mcontext = mcontext;
        this.click = click;
        LayoutInflater inflater = (LayoutInflater)
                mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.edittext_notice_dialog, null);
        builders = new MyDialog(mcontext, 0, 0, layout, R.style.load_dialog);
        initView(edittextStr, edittextLength);
        title.setText(content);
        Show();
        return builders;
    }

    public class MyDialog extends Dialog {

        private static final int default_width = 160; //默认宽度
        private static final int default_height = 120;//默认高度

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
//	        params.height  = (int) (heightdp*0.25);
            window.setAttributes(params);
        }
    }

    private void initView(String edittextStr, int edittextLength) {
        title = (TextView) layout.findViewById(R.id.title);
        btn_ok = (Button) layout.findViewById(R.id.dialogOKB);
        btn_cancel = (Button) layout.findViewById(R.id.btn_cancel);
        alert_message = (TextView) layout.findViewById(R.id.alert_message);
        alert_edittext = (ContainsEmojiEditText) layout.findViewById(R.id.alert_et);

        btn_ok.setOnClickListener(new setonclicklistener(1));
        btn_cancel.setOnClickListener(new setonclicklistener(0));
        alert_edittext.setText(edittextStr);
        InputFilter[] filters = {new NameLengthFilter(edittextLength)};
        alert_edittext.setFilters(filters);
        if (ok != null)
            btn_ok.setText(ok);
        if (cancle != null) {
            btn_cancel.setText(cancle);
        } else {
            btn_cancel.setVisibility(View.GONE);
        }
    }

    class setonclicklistener implements OnClickListener {

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
                    click.ok_bt(1, alert_edittext.getText().toString());
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

        void ok_bt(int dialogOKB, String edittextStr);//点击OK 1 点击取消 0
    }

    ;

    private class NameLengthFilter implements InputFilter {
        int MAX_EN;// 最大英文/数字长度 一个汉字算两个字母
        String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字

        public NameLengthFilter(int mAX_EN) {
            super();
            MAX_EN = mAX_EN;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            int destCount = dest.toString().length()
                    + getChineseCount(dest.toString());
            int sourceCount = source.toString().length()
                    + getChineseCount(source.toString());
            if (destCount + sourceCount > MAX_EN) {
                return "";

            } else {
                return source;
            }
        }

        private int getChineseCount(String str) {
            int count = 0;
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            while (m.find()) {
                for (int i = 0; i <= m.groupCount(); i++) {
                    count = count + 1;
                }
            }
            return count;
        }
    }
}
