package com.cn.danceland.myapplication.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.BodyAgeBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by feng on 2017/12/29.
 */

public class BodyAgeActivity extends BaseActivity {
    LineChartView lineChartView;
    ImageView body_back;
    Gson gson;
    List<BodyAgeBean.Data> data;
    TextView no_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bodyage);

        gson = new Gson();
        initView();
        initData();

    }

    private void initData() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FINDBODYAGE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                BodyAgeBean bodyAgeBean = gson.fromJson(s, BodyAgeBean.class);
                if(bodyAgeBean!=null){
                    data = bodyAgeBean.getData();
                    if(data!=null&&data.size()>=2){
                        initLines(data);
                    }else{
                        no_content.setVisibility(View.VISIBLE);
                        lineChartView.setVisibility(View.GONE);
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("请检查网络！");

            }
        }){

        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void initView() {
        lineChartView = findViewById(R.id.lineChartView);
        no_content = findViewById(R.id.no_content);

        body_back = findViewById(R.id.body_back);
        body_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void initLines(List<BodyAgeBean.Data> data) {

        List<PointValue> pointValues = new ArrayList<PointValue>();// 身体年龄数据结合
        List<PointValue> realPoint = new ArrayList<PointValue>();// 身体年龄数据结合
        Axis axisY = new Axis();// Y轴属性
        Axis axisX = new Axis();// X轴属性
//        axisY.setName("温度");//设置Y轴显示名称
//        axisX.setName("时间");//设置X轴显示名称
        ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();//定义X轴刻度值的数据集合
        ArrayList<AxisValue> axisValuesY = new ArrayList<AxisValue>();//定义Y轴刻度值的数据集合

        axisX.setLineColor(Color.BLACK);// 设置X轴轴线颜色
        axisY.setLineColor(Color.BLACK);// 设置Y轴轴线颜色
//        axisX.setTextColor(Color.RED);// 设置X轴文字颜色
//        axisY.setTextColor(Color.RED);// 设置Y轴文字颜色
        axisX.setTextSize(10);// 设置X轴文字大小
        axisY.setTextSize(10);
        axisX.setTypeface(Typeface.DEFAULT);// 设置文字样式，此处为默认
        axisX.setHasTiltedLabels(false);// 设置X轴文字向左旋转45度
        axisX.setHasLines(false);// 是否显示X轴网格线
        axisY.setHasLines(false);// 是否显示Y轴网格线
        axisX.setHasSeparationLine(true);// 设置是否有分割线
        axisX.setInside(false);// 设置X轴文字是否在X轴内部

        if(data!=null){
            Float aFloat = Float.valueOf(data.get(0).getFaceAge());
            for(int i=1;i<=data.size()+5;i++){
                //axisValuesY.add(new AxisValue(i,(aFloat-i-2+"").toCharArray()));
                axisValuesX.add(new AxisValue(i,("第"+i+"次").toCharArray()));
            }
        }

//        for (int j = 25; j <= 35; j++) {//循环为节点、X、Y轴添加数据
//            axisValuesY.add(new AxisValue(j));// 添加Y轴显示的刻度值
//        }
//        for (int i=1; i <= 12; i++){
//            axisValuesX.add(new AxisValue(i,(i+"月").toCharArray()));
//        }
        axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
        //axisY.setValues(axisValuesY);
        //for (int k=1; k<= 3; k++){
            //pointValues.add(new PointValue(k,Float.parseFloat(tempPoint.get(i))));

        if(data!=null){
            for(int i =0;i<data.size();i++){
//                PointValue pointValue = new PointValue();
//                pointValue.set(i,Float.valueOf(data.get(0).getBodyage()));
//                pointValue.setLabel(data.get(0).getBodyage());
                pointValues.add(new PointValue(i,Float.valueOf(data.get(i).getBodyage())));
                //pointValues.add(new PointValue(i,Float.valueOf(data.get(i).getBodyage())));
//                PointValue pointValue1 = new PointValue();
//                pointValue1.set(i,Float.valueOf(data.get(0).getFaceAge()));
                //pointValue1.setLabel(data.get(i).getFaceAge());
                realPoint.add(new PointValue(i,Float.valueOf("24")));
                //realPoint.add(new PointValue(i,Float.valueOf(data.get(i).getFaceAge())));
            }
        }


        List<Line> lines = new ArrayList<Line>();//定义线的集合
        Line line = new Line(pointValues);//将值设置给折线
        line.setColor(0xFFA9A9A9);// 设置折线颜色
        line.setStrokeWidth(3);// 设置折线宽度
        line.setFilled(false);// 设置折线覆盖区域是否填充
        line.setCubic(false);// 是否设置为立体的
        line.setPointColor(Color.RED);// 设置节点颜色
        line.setPointRadius(3);// 设置节点半径
        line.setHasLabels(true);// 是否显示节点数据
        line.setHasLines(true);// 是否显示折线
        line.setHasPoints(true);// 是否显示节点
        line.setShape(ValueShape.CIRCLE);// 节点图形样式 DIAMOND菱形、SQUARE方形、CIRCLE圆形
        line.setHasLabelsOnlyForSelected(true);// 隐藏数据，触摸可以显示

        lines.add(line);// 将数据集合添加线

        Line realAge = new Line(realPoint);
        realAge.setColor(Color.BLUE);// 设置折线颜色
        realAge.setStrokeWidth(4);// 设置折线宽度
        realAge.setFilled(false);// 设置折线覆盖区域是否填充
        realAge.setCubic(false);// 是否设置为立体的
        realAge.setPointColor(Color.BLUE);// 设置节点颜色
        realAge.setPointRadius(2);// 设置节点半径
        realAge.setHasLabels(true);// 是否显示节点数据
        realAge.setHasLines(true);// 是否显示折线
        realAge.setHasPoints(true);// 是否显示节点
        realAge.setShape(ValueShape.CIRCLE);// 节点图形样式 DIAMOND菱形、SQUARE方形、CIRCLE圆形
        realAge.setHasLabelsOnlyForSelected(true);// 隐藏数据，触摸可以显示
        lines.add(realAge);

        LineChartData chartData = new LineChartData(lines);//将线的集合设置为折线图的数据
        chartData.setAxisYLeft(axisY);// 将Y轴属性设置到左边
        chartData.setAxisXBottom(axisX);// 将X轴属性设置到底部
        chartData.setBaseValue(20);// 设置反向覆盖区域颜色
        chartData.setValueLabelBackgroundAuto(false);// 设置数据背景是否跟随节点颜色
        chartData.setValueLabelBackgroundColor(Color.BLUE);// 设置数据背景颜色
        chartData.setValueLabelBackgroundEnabled(false);// 设置是否有数据背景
        chartData.setValueLabelsTextColor(Color.BLACK);// 设置数据文字颜色
        chartData.setValueLabelTextSize(15);// 设置数据文字大小
        chartData.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式
        lineChartView.setLineChartData(chartData);

        lineChartView.setZoomEnabled(true);//设置是否支持缩放
        lineChartView.setInteractive(true);//设置图表是否可以与用户互动
        lineChartView.setZoomType(ZoomType.HORIZONTAL);

//        Viewport v = new Viewport(lineChartView.getMaximumViewport());
//        v.left = 0;
//        v.right= 7;
//        v.bottom= 1;
//        v.top= 30;
//        lineChartView.setCurrentViewport(v);
    }
}
