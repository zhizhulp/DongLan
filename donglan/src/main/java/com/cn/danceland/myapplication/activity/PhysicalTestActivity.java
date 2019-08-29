package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.app.AppManager;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.PhysicalTestBean;
import com.cn.danceland.myapplication.bean.RequsetFindUserBean;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestion;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestionCond;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestionRequest;
import com.cn.danceland.myapplication.bean.bca.bcaresult.BcaResult;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.ViewPagerNoSlide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 体能测试
 * Created by feng on 2018/4/8.
 */

public class PhysicalTestActivity extends BaseActivity {

    DongLanTitleView physical_title;
    ViewPagerNoSlide vp_physical;
    private BcaQuestionRequest request;
    private Gson gson;
    List<BcaQuestion> list;
    ArrayList<View> viewList;
    View inflate;
    TextView tv_zhubiaoti;
    Long select;
    List<BcaResult> resultList;
    private RequsetFindUserBean.Data requsetInfo;//前面搜索到的对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicaltest);
        AppManager.getAppManager().addActivity(this);
        initHost();
        initView();
        queryList();
    }

    private void initViewPager() {

        if (list != null) {
            vp_physical.setAdapter(new PhysicalPagerAdapter(viewList, list));
        }

    }

    private void initHost() {
        request = new BcaQuestionRequest();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        list = new ArrayList<>();

        resultList = (List<BcaResult>) getIntent().getSerializableExtra("resultList");
        requsetInfo = (RequsetFindUserBean.Data) getIntent().getSerializableExtra("requsetInfo");//前面搜索到的对象

        if (resultList == null) {
            resultList = new ArrayList<>();
        }
    }

    private void initView() {

        physical_title = findViewById(R.id.physical_title);
        physical_title.setTitle("体测分析");

        vp_physical = findViewById(R.id.vp_physical);
        vp_physical.setScroll(false);


        //inflate = LayoutInflater.from(PhysicalTestActivity.this).inflate(R.layout.physical_detail, null);
        //tv_zhubiaoti = inflate.findViewById(R.id.tv_zhubiaoti);

        viewList = new ArrayList<>();

    }


    /**
     * @方法说明:按条件查询问题题干列表
     **/
    public void queryList() {
        BcaQuestionCond cond = new BcaQuestionCond();
        cond.setType(Byte.valueOf("3"));
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                LogUtil.i(json.toString());
                DLResult<List<BcaQuestion>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<BcaQuestion>>>() {
                }.getType());
                list.clear();
                if (result.isSuccess()) {
                    list = result.getData();
                    for (int i = 0; i < list.size(); i++) {
                        viewList.add(LayoutInflater.from(PhysicalTestActivity.this).inflate(R.layout.physical_detail, null));
                    }
                    if (viewList != null && viewList.size() > 0) {
                        initViewPager();
                    }

                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        });
    }

    private class PhysicalPagerAdapter extends PagerAdapter {
        ArrayList<View> viewList;
        List<BcaQuestion> list;

        PhysicalPagerAdapter(ArrayList<View> viewList, List<BcaQuestion> datalist) {
            this.viewList = viewList;
            list = datalist;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View v = viewList.get(position);
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }

            container.addView(v);
            String test_content = list.get(position).getTest_content();
            PhysicalTestBean physicalTestBean = gson.fromJson(test_content, PhysicalTestBean.class);

            ImageView img_01 = v.findViewById(R.id.img_01);
            Glide.with(PhysicalTestActivity.this).load(physicalTestBean.getMain_pic_url()).into(img_01);

            TextView tv_zhubiaoti = v.findViewById(R.id.tv_zhubiaoti);
            tv_zhubiaoti.setText(physicalTestBean.getMain_title());

            TextView tv_fubiaoti = v.findViewById(R.id.tv_fubiaoti);
            tv_fubiaoti.setText(physicalTestBean.getSecond_title());

            TextView tv_buzhou = v.findViewById(R.id.tv_buzhou);
            ImageView img_02 = v.findViewById(R.id.img_02);
            ImageView img_03 = v.findViewById(R.id.img_03);
            ImageView img_04 = v.findViewById(R.id.img_04);
            ImageView[] arrImg = {img_02, img_03, img_04};
            TextView tv_buzhou_01 = v.findViewById(R.id.tv_buzhou_01);
            TextView tv_buzhou_02 = v.findViewById(R.id.tv_buzhou_02);
            TextView tv_buzhou_03 = v.findViewById(R.id.tv_buzhou_03);
            TextView[] arrText = {tv_buzhou_01, tv_buzhou_02, tv_buzhou_03};
            LogUtil.i("size="+physicalTestBean.getAction_detail().size());
            LogUtil.i("tos="+physicalTestBean.getAction_detail().toString());
            List<PhysicalTestBean.Action_detail> action_detail = physicalTestBean.getAction_detail();
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder1 = new StringBuilder();
            if (action_detail != null) {
                for (int j = 0; j < action_detail.size(); j++) {
                    if(j==action_detail.size()-1){
                        stringBuilder.append((j + 1) + "." + action_detail.get(j).getNote() );
                    }else{
                        stringBuilder.append((j + 1) + "." + action_detail.get(j).getNote() + "\n");
                    }
                    if (arrImg.length > j) {
                        Glide.with(PhysicalTestActivity.this).load(action_detail.get(j).getPic_url()).into(arrImg[j]);
                    }
                    if (arrText.length > j) {
                        arrText[j].setText(action_detail.get(j).getNote());
                    }
                }
//                tv_buzhou.setText(stringBuilder.toString());
                tv_buzhou.setText(physicalTestBean.getSteps()+"");
            }

            TextView tv_zhuyi = v.findViewById(R.id.tv_zhuyi);
            List<String> attention = physicalTestBean.getAttention();
            if (attention != null) {
                for (int n = 0; n < attention.size(); n++) {
                    if(n==attention.size()-1){
                        stringBuilder1.append((n + 1) + "." + attention.get(n) );
                    }else{
                        stringBuilder1.append((n + 1) + "." + attention.get(n) + "\n");
                    }
                }
                tv_zhuyi.setText(stringBuilder1.toString());
            }

            RadioGroup rg_result = v.findViewById(R.id.rg_result);
            RadioButton rb_result_01 = v.findViewById(R.id.rb_result_01);
            RadioButton rb_result_02 = v.findViewById(R.id.rb_result_02);
            RadioButton rb_result_03 = v.findViewById(R.id.rb_result_03);
            if(list.get(position).getOptions().size()>=3){
                rb_result_01.setVisibility(View.VISIBLE);
                rb_result_02.setVisibility(View.VISIBLE);
                rb_result_03.setVisibility(View.VISIBLE);
                rb_result_01.setText(list.get(position).getOptions().get(0).getTitle());
                rb_result_02.setText(list.get(position).getOptions().get(1).getTitle());
                rb_result_03.setText(list.get(position).getOptions().get(2).getTitle());
            }else if(list.get(position).getOptions().size()==2){
                rb_result_01.setVisibility(View.VISIBLE);
                rb_result_02.setVisibility(View.VISIBLE);
                rb_result_01.setText(list.get(position).getOptions().get(0).getTitle());
                rb_result_02.setText(list.get(position).getOptions().get(1).getTitle());
                rb_result_03.setVisibility(View.GONE);
            }else if(list.get(position).getOptions().size()==1){
                rb_result_01.setVisibility(View.VISIBLE);
                rb_result_02.setVisibility(View.GONE);
                rb_result_03.setVisibility(View.GONE);
                rb_result_01.setText(list.get(position).getOptions().get(0).getTitle());
            }else {
                rb_result_01.setVisibility(View.GONE);
                rb_result_02.setVisibility(View.GONE);
                rb_result_03.setVisibility(View.GONE);
            }
            rg_result.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (group.getCheckedRadioButtonId()) {
                        case R.id.rb_result_01:
//                            if (list.get(position).getOptions() != null && list.get(position).getOptions().size() >= 3) {
                                select = list.get(position).getOptions().get(0).getId();
//                            }
                            break;
                        case R.id.rb_result_02:
//                            if (list.get(position).getOptions() != null && list.get(position).getOptions().size() >= 3) {
                                select = list.get(position).getOptions().get(1).getId();
//                            }
                            break;
                        case R.id.rb_result_03:
//                            if (list.get(position).getOptions() != null && list.get(position).getOptions().size() >= 3) {
                                select = list.get(position).getOptions().get(2).getId();
//                            }
                            break;
                    }
                }
            });


            LinearLayout bt_next_layout = v.findViewById(R.id.bt_next_layout);
            TextView bt_next = v.findViewById(R.id.bt_next);
            bt_next.setText("下一步");
            bt_next_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (select != null) {
                        BcaResult bcaResult = new BcaResult();
                        bcaResult.setQuestion_id(list.get(position).getId());
                        bcaResult.setOpt_id(select);
                        resultList.add(bcaResult);
                    }
                        if (position < viewList.size() - 1) {
                            vp_physical.setCurrentItem(position + 1);
                        } else {
                            startActivity(new Intent(PhysicalTestActivity.this, BodyWeiDuActivity.class)
                                    .putExtra("resultList", (Serializable) resultList)
                                    .putExtra("requsetInfo", requsetInfo));
                        }

                    select = null;
                }
            });

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
            container.removeView(viewList.get(position));
        }

    }

}
