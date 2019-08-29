package com.cn.danceland.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.shouhuan.chart.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${yxx} on 2018/8/14.
 */
public class PickerViewDialog extends Dialog {
    public Context context;
    private PickerView pickerView;
    private PickerListener listener;
//    private PickerView.onSelectListener listener;
    private List<String> data = new ArrayList<String>();

//    public PickerViewDialog(Context context, PickerView.onSelectListener listener) {
//        super(context);
//        this.listener = listener;
//        this.context = context;
//    }
    public PickerViewDialog(Context context, PickerListener listener) {
        super(context);
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_pickview_layout, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.height = DensityUtil.dip2px(context, 300);
        lp.width = DensityUtil.dip2px(context, 300);
        win.setAttributes(lp);

        for (int i = 30; i < 121; i++) {
            data.add(i + "");
        }
        pickerView = view.findViewById(R.id.pickerview);
        pickerView.setData(data);

//        pickerView.setOnSelectListener(listener) ;
        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
//                Toast.makeText(context, "选择了 " + text + " 分",
//                        Toast.LENGTH_SHORT).show();
                listener.getPickerSelect(text);
            }
        });

//        view.findViewById(R.id.btn_cancel).setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
        view.findViewById(R.id.btn_ok).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.getPickerSelect(data.get(pickerView.getmCurrentSelected()));
                dismiss();
            }
        });
    }

    public interface PickerListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        void getPickerSelect(String text);
    }

}