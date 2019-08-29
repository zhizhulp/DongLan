package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.app.AppManager;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.RequsetFindUserBean;
import com.cn.danceland.myapplication.bean.bca.bcaoption.BcaOption;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestion;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestionCond;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestionRequest;
import com.cn.danceland.myapplication.bean.bca.bcaresult.BcaResult;
import com.cn.danceland.myapplication.utils.CustomGridView;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.NoScrollListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 身体详细情况222
 * Created by feng on 2018/4/8.
 */

public class BodyDeatilActivity extends BaseActivity {

    DongLanTitleView rl_bodybase_title;
    NoScrollListView listView;
    private BcaQuestionRequest request;
    private Gson gson;
    List<BcaQuestion> list;
    BodyBaseAdapter bodyBaseAdapter;
    CustomGridView gv_bodybase;
    BodyBaseGridAdapter bodyBaseGridAdapter;
    LinearLayout body_button;
    List<BcaResult> resultList;
    EditText editText;
    Long que_id;

    private RequsetFindUserBean.Data requsetInfo;//前面搜索到的对象
    private LinearLayout header_layout;
    private TextView child_title_tv;//小标题

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodybase);
        AppManager.getAppManager().addActivity(this);
        initHost();
        initView();
        queryList();
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

        rl_bodybase_title = findViewById(R.id.rl_bodybase_title);
        listView = findViewById(R.id.lv_bodybase);
        body_button = findViewById(R.id.body_button);
        header_layout = findViewById(R.id.header_layout);
        child_title_tv = findViewById(R.id.child_title_tv);

        rl_bodybase_title.setTitle("体测分析");
        child_title_tv.setText("身体详细情况");
        header_layout.setVisibility(View.GONE);
        bodyBaseAdapter = new BodyBaseAdapter();
        listView.setAdapter(bodyBaseAdapter);

        body_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText != null) {
                    BcaResult bcaResult = new BcaResult();
                    bcaResult.setQuestion_id(que_id);
                    bcaResult.setContent(editText.getText().toString());
                    bcaResult.setOpt_id((Long) editText.getTag());
                    resultList.add(bcaResult);
                }
                deleteEqualsItem();
                startActivity(new Intent(BodyDeatilActivity.this, PhysicalTestActivity.class)
                        .putExtra("resultList", (Serializable) resultList)
                        .putExtra("requsetInfo", requsetInfo));
            }
        });
    }

    /**
     * @方法说明:按条件查询问题题干列表
     **/
    public void queryList() {
        BcaQuestionCond cond = new BcaQuestionCond();
        cond.setType(Byte.valueOf("2"));
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                DLResult<List<BcaQuestion>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<BcaQuestion>>>() {
                }.getType());
                list.clear();
                if (result.isSuccess()) {
                    list = result.getData();
                    if (list != null && list.size() > 0) {
                        bodyBaseAdapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        });
    }


    private class BodyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int pos, View convertView, ViewGroup parent) {
            View view = View.inflate(BodyDeatilActivity.this, R.layout.bodybase_item, null);
            LinearLayout ll_ed_parent = view.findViewById(R.id.ll_ed_parent);


            gv_bodybase = view.findViewById(R.id.gv_bodybase);

            TextView tv_tigan = view.findViewById(R.id.tv_tigan);

            RelativeLayout item_layout = view.findViewById(R.id.item_layout);
            if (pos == list.size() - 1) {
                RelativeLayout.LayoutParams linearParams1 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearParams1.setMargins(DensityUtils.dp2px(BodyDeatilActivity.this, 16f), DensityUtils.dp2px(BodyDeatilActivity.this, 16f), DensityUtils.dp2px(BodyDeatilActivity.this, 16f), DensityUtils.dp2px(BodyDeatilActivity.this, 16f));
                item_layout.setLayoutParams(linearParams1); //使设置好的布局参数应用到控件
            }

            tv_tigan.setText(list.get(pos).getOrder_no() + ". " + list.get(pos).getCentent());

            List<BcaOption> options = list.get(pos).getOptions();
            List<BcaOption> options1 = new ArrayList<>();
            List<String> editList = new ArrayList<>();
            ArrayList<Long> options2 = new ArrayList<>();
            if (options != null) {
                for (int i = 0; i < options.size(); i++) {
                    String type = options.get(i).getType().toString();
                    if ("1".equals(type)) {
                        options1.add(options.get(i));
                    }
                    if ("2".equals(type)) {
                        editList.add(options.get(i).getTitle());
                        options2.add(options.get(i).getId());
                    }
                }
            }
            for (int n = 0; n < editList.size(); n++) {
                final EditText editText = new EditText(BodyDeatilActivity.this);
                editText.setBackgroundResource(R.drawable.rect_body);
                editText.setHint(editList.get(n));
                editText.setHintTextColor(getResources().getColor(R.color.home_menu_bg_color));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                editText.setLayoutParams(lp);
                editText.setMaxLines(1);
                editText.setTag(options2.get(n));
                lp.setMargins(0, 4, 0, 0);
                editText.setPadding(10, 20, 10, 20);
                editText.setTextSize(12);
                editText.setTextColor(getResources().getColor(R.color.colorGray22));
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            BcaResult bcaResult = new BcaResult();
                            bcaResult.setQuestion_id(list.get(pos).getId());
                            bcaResult.setContent(editText.getText().toString());
                            bcaResult.setOpt_id((Long) editText.getTag());
                            resultList.add(bcaResult);
                            //deleteEqualsItem();
                        } else {
                            setEditText(editText, list.get(pos).getId());
                        }
                    }
                });
                ll_ed_parent.addView(editText);
            }


            bodyBaseGridAdapter = new BodyBaseGridAdapter(options1, list.get(pos).getId(), list.get(pos).getIs_single(), pos);
            gv_bodybase.setAdapter(bodyBaseGridAdapter);

            return view;
        }
    }

    private void setEditText(EditText editText, Long id) {
        this.editText = editText;
        this.que_id = id;
    }

    private void deleteEqualsItem() {
        for (int i = 0; i < resultList.size(); i++) {
            for (int j = i + 1; j < resultList.size(); j++) {
                if (resultList.get(i).equals(resultList.get(j))) {
                    resultList.remove(i);
                    i--;
                    break;
                }
            }
        }
    }


    private class BodyBaseGridAdapter extends BaseAdapter {
        Long que_id;

        List<BcaOption> options;

        Long is_single;
        int fatherpos;

        BodyBaseGridAdapter(List<BcaOption> options, Long que_id, Long is_single, int fatherpos) {
            this.options = options;
            this.que_id = que_id;
            this.is_single = is_single;
            this.fatherpos = fatherpos;
        }

        @Override
        public int getCount() {
            return options.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = View.inflate(BodyDeatilActivity.this, R.layout.bodybase_grid_item, null);
            final CheckBox rb_grid = view.findViewById(R.id.rb_grid);
            final CheckBox rb_grid_round = view.findViewById(R.id.rb_grid_round);

            if (list.get(fatherpos).getOptions().get(position).getIsChecked()) {
                rb_grid.setChecked(true);
                rb_grid_round.setChecked(true);
            } else {
                rb_grid.setChecked(false);
                rb_grid_round.setChecked(false);
            }
            LogUtil.i(resultList.size() + "****");

            if (is_single == 0) {//1 单选 0 非单选
                rb_grid.setText(options.get(position).getTitle());

                rb_grid.setVisibility(View.VISIBLE);
                rb_grid_round.setVisibility(View.GONE);
            } else {
                rb_grid_round.setText(options.get(position).getTitle());

                rb_grid.setVisibility(View.GONE);
                rb_grid_round.setVisibility(View.VISIBLE);
            }
            rb_grid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (list.get(fatherpos).getOptions().get(position).getIsChecked()) {
                        deleteResultData(position);

                        rb_grid.setChecked(false);
                        list.get(fatherpos).getOptions().get(position).setIsChecked(false);
                    } else {
                        BcaResult bcaResult = new BcaResult();
                        bcaResult.setOpt_id(Long.valueOf(position + ""));
                        bcaResult.setQuestion_id(Long.valueOf(que_id + ""));
                        resultList.add(bcaResult);

                        rb_grid.setChecked(true);
                        list.get(fatherpos).getOptions().get(position).setIsChecked(true);
                    }
                }
            });

            rb_grid_round.setOnClickListener(new View.OnClickListener() {//单选
                @Override
                public void onClick(View v) {
                    LogUtil.i("" + list.get(fatherpos).getOptions().get(position).getIsChecked());
                    if (list.get(fatherpos).getOptions().get(position).getIsChecked()) {
                        deleteResultData(position);

                        rb_grid_round.setChecked(false);
                        list.get(fatherpos).getOptions().get(position).setIsChecked(false);
                    } else {
                        for (int i = 0; i < list.get(fatherpos).getOptions().size(); i++) {
                            if (i != position) {
                                deleteResultData(position);
                                list.get(fatherpos).getOptions().get(i).setIsChecked(false);
                            }
                        }
                        LogUtil.i(resultList.size() + "");
                        BcaResult bcaResult = new BcaResult();
                        bcaResult.setOpt_id(Long.valueOf(position + ""));
                        bcaResult.setQuestion_id(Long.valueOf(que_id + ""));
                        resultList.add(bcaResult);
                        rb_grid_round.setChecked(true);
                        list.get(fatherpos).getOptions().get(position).setIsChecked(true);
                        LogUtil.i(resultList.size() + "--" + list.get(fatherpos).getOptions().get(position).getIsChecked());

                    }
                    bodyBaseAdapter.notifyDataSetChanged();
                }
            });
//            rb_grid.setText(options.get(position).getTitle());
//            //TODO 单选多选
//            rb_grid.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (rb_grid.isChecked()) {
//                        BcaResult bcaResult = new BcaResult();
//                        bcaResult.setOpt_id(Long.valueOf(position + ""));
//                        bcaResult.setQuestion_id(Long.valueOf(que_id + ""));
//                        resultList.add(bcaResult);
//                        //deleteEqualsItem();
//                    }
//                }
//            });
            return view;
        }
        private void deleteResultData(final int idx) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < resultList.size(); i++) {
                        BcaResult bcaResult = resultList.get(i);
                        LogUtil.i("fdgjfsg" + bcaResult.getOpt_id() + Long.valueOf(idx + "") + bcaResult.getQuestion_id() + Long.valueOf(que_id + ""));

                        if (bcaResult.getOpt_id() == Long.valueOf(idx + "") && bcaResult.getQuestion_id() == Long.valueOf(que_id + "")) {
                            resultList.remove(bcaResult);
                            i--;// 注意
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
