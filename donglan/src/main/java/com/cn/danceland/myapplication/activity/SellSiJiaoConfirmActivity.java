package com.cn.danceland.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.BuySiJiaoBean;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestParamsBean;
import com.cn.danceland.myapplication.bean.explain.Explain;
import com.cn.danceland.myapplication.bean.explain.ExplainCond;
import com.cn.danceland.myapplication.bean.explain.ExplainRequest;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2018/1/15.
 */

public class SellSiJiaoConfirmActivity extends BaseActivity {
    int state=999;

    RelativeLayout rl_buy;
    RadioButton btn_sijiao,btn_sijiaodingjin;
    ImageView sell_img;
    BuySiJiaoBean.Content itemContent;
    Gson gson;
    ExplainRequest request;
    Data info;
    TextView tv_shuoming;
    Float deposit_course_min,deposit_course_max;
    String deposit_days;
   ImageView xc_img;
    private TextView tv_branch_name;
    private TextView tv_course_name;
    private TextView tv_category;
    private TextView tv_count;
    private TextView tv_price;
    private ImageView iv_gouaka;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sellsijiaoconfirm);

        request = new ExplainRequest();

        info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        itemContent = (BuySiJiaoBean.Content)getIntent().getSerializableExtra("itemContent");
        initView();
        queryList();
        findParams();
    }


    public class StrBean {

        private List<String> param_keys;

        public void setParam_keys(List<String> param_keys) {
            this.param_keys = param_keys;
        }

        public List<String> getParam_keys() {
            return param_keys;
        }
    }

    public void findParams() {

        StrBean strBean = new StrBean();
        List<String> params = new ArrayList<>();
        params.add("deposit_days");
        params.add("deposit_card_min");
        params.add("deposit_card_max");
        params.add("deposit_course_min");
        params.add("deposit_course_max");
        params.add("deposit_locker_min");
        params.add("deposit_locker_max");
        strBean.setParam_keys(params);

        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FIND_PARAM_KEY), new Gson().toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestParamsBean.Data data = new Gson().fromJson(jsonObject.toString(), RequestParamsBean.class).getData();
                if(data!=null){
                    deposit_course_min = Float.valueOf(data.getDeposit_course_min());
                    deposit_course_max = Float.valueOf(data.getDeposit_course_max());
                    deposit_days = data.getDeposit_days();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());

            }
        });
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * @方法说明:按条件查询说明须知列表
     **/
    public void queryList() {

        ExplainCond cond = new ExplainCond();
        cond.setBranch_id(Long.valueOf(info.getPerson().getDefault_branch()));
        cond.setType(Byte.valueOf("2"));// 1 买卡须知 2 买私教须知 3 买储值须知 4 买卡说明 5 买私教说明

        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                DLResult<List<Explain>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<Explain>>>() {
                }.getType());
                if (result.isSuccess()) {
                    List<Explain> list = result.getData();
                    if(list!=null&&list.size()>0){
                        tv_shuoming.setText(list.get(0).getContent());
                    }
                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        });
    }

    private void initView() {
        state = 0;

        tv_shuoming = findViewById(R.id.tv_shuoming);
        xc_img = findViewById(R.id.xc_img);


        rl_buy = findViewById(R.id.rl_buy);
        iv_gouaka = findViewById(R.id.iv_gouaka);
        btn_sijiao = findViewById(R.id.btn_sijiao);
        btn_sijiaodingjin = findViewById(R.id.btn_sijiaodingjin);

        btn_sijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btn_sijiao.setChecked(true);
//                btn_sijiaodingjin.setChecked(false);
                state = 0;
            }
        });

        btn_sijiaodingjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btn_sijiao.setChecked(false);
//                btn_sijiaodingjin.setChecked(true);
                state = 1;
            }
        });
        iv_gouaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state==1){
                    state=0;
                    iv_gouaka.setImageResource(R.drawable.img_gousijiao);
                }
             else {
                    state=1;
                    iv_gouaka.setImageResource(R.drawable.img_gousijiao1);
                }

            }
        });


        rl_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state==0){
                    startActivity(new Intent(SellSiJiaoConfirmActivity.this,SiJiaoOrderActivity.class).putExtra("type","0").putExtra("itemContent",itemContent));
                    finish();
                }else if(state==1){
                    showPirce(deposit_course_min,deposit_course_max);
                }

            }
        });
        findViewById(R.id.dlbtn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state==0){
                    startActivity(new Intent(SellSiJiaoConfirmActivity.this,SiJiaoOrderActivity.class).putExtra("type","0").putExtra("itemContent",itemContent));
                    finish();
                }else if(state==1){
                    showPirce(deposit_course_min,deposit_course_max);
                }
            }
        });
        tv_branch_name = findViewById(R.id.tv_branch_name);
        tv_course_name = findViewById(R.id.tv_course_name);
        tv_category = findViewById(R.id.tv_category);
        tv_count = findViewById(R.id.tv_count);
        tv_price = findViewById(R.id.tv_price);

        if (itemContent!=null){
            //第一个是上下文，第二个是圆角的弧度
            RequestOptions options = new RequestOptions()
                    .transform(new GlideRoundTransform(SellSiJiaoConfirmActivity.this,10));

            Glide.with(this).load(itemContent.getImg_url()).apply(options).into(xc_img);
            tv_branch_name.setText(itemContent.getBranch_name());
            tv_course_name.setText(itemContent.getName());
            tv_category.setText(itemContent.getCourse_category_name());
            tv_count.setText(itemContent.getCount()+"节");
            tv_price.setText("￥："+itemContent.getPrice()+"元");
        }

    }

    private void showPirce(final Float min, final Float max) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.edit_name, null);
        TextView dialogTitleName = dialogView.findViewById(R.id.tv_nick_name);
        dialogTitleName.setText("预付定金金额");
        final EditText ed = dialogView.findViewById(R.id.edit_name);
        ed.setHint("请输入定金额：最小" + min + "元" + "，最大" + max+ "元");
        ed.setInputType(InputType.TYPE_CLASS_PHONE);
//        ed.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                Log.e("输入完点击确认执行该方法", "输入结束");
//                return false;
//            }
//        });
        builder.setView(dialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(ed.getText().toString()) ) {
                    if (Float.parseFloat(ed.getText().toString()) >=min && Float.parseFloat(ed.getText().toString()) <= max) {
                        startActivity(new Intent(SellSiJiaoConfirmActivity.this,SiJiaoOrderActivity.class).putExtra("type","1").putExtra("itemContent",itemContent).putExtra("deposit_days",deposit_days).putExtra("deposit_course_price",ed.getText().toString()));
                        finish();
                    } else {
                        ToastUtils.showToastShort("输入金额不在有效范围，请重新输入");


                    }
                }
            }
        });
        builder.show();

    }
}
