package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.app.AppManager;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.RequsetFindUserBean;
import com.cn.danceland.myapplication.bean.bca.bcaanalysis.BcaAnalysis;
import com.cn.danceland.myapplication.bean.bca.bcaanalysis.BcaAnalysisRequest;
import com.cn.danceland.myapplication.bean.bca.bcaoption.BcaOption;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestion;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestionCond;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestionRequest;
import com.cn.danceland.myapplication.bean.bca.bcaresult.BcaResult;
import com.cn.danceland.myapplication.utils.CustomGridView;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.NoScrollListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2018/4/12.
 */

public class BodyTiXingActivity extends BaseActivity {

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
    private View child_title_line_v;//小标题 line

    private String saveId;
    private String frontal_path;
    private String side_path;
    private String behind_path;
    private String content;

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
        frontal_path = getIntent().getStringExtra("frontal_path");//前面搜索到的对象
        side_path = getIntent().getStringExtra("side_path");//前面搜索到的对象
        behind_path = getIntent().getStringExtra("behind_path");//前面搜索到的对象
        content = getIntent().getStringExtra("content");//前面搜索到的对象
        if (resultList == null) {
            resultList = new ArrayList<>();
        }
    }

    private void initView() {

        rl_bodybase_title = findViewById(R.id.rl_bodybase_title);
        rl_bodybase_title.setTitle("体型体态分析");
        listView = findViewById(R.id.lv_bodybase);
        body_button = findViewById(R.id.body_button);
        header_layout = findViewById(R.id.header_layout);
        child_title_tv = findViewById(R.id.child_title_tv);
        child_title_line_v = findViewById(R.id.child_title_line_v);

        header_layout.setVisibility(View.GONE);
        child_title_tv.setVisibility(View.GONE);
        child_title_line_v.setVisibility(View.GONE);

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
                save();
            }
        });

    }

    /**
     * @方法说明:新增体测分析
     **/
    public void save() {
        BcaAnalysis bcaAnalysis = new BcaAnalysis();
        bcaAnalysis.setMember_id(Long.valueOf(requsetInfo.getId()));
        bcaAnalysis.setMember_no(requsetInfo.getMember_no());
        bcaAnalysis.setFrontal_path(frontal_path);//正面照
        bcaAnalysis.setSide_path(side_path);//侧面照
        bcaAnalysis.setBehind_path(behind_path);//背面照
        bcaAnalysis.setResult(resultList);
        if (content != null && content.length() > 0) {
            bcaAnalysis.setContent(content);
        }
        LogUtil.i(new Gson().toJson(bcaAnalysis).toString());
        final BcaAnalysisRequest request = new BcaAnalysisRequest();
        request.save(bcaAnalysis, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                DLResult<String> result = gson.fromJson(json.toString(), new TypeToken<DLResult<String>>() {
                }.getType());
                if (result.isSuccess()) {
                    LogUtil.i("" + json.toString());
                    ToastUtils.showToastShort("提交成功！");
                    startActivity(new Intent(BodyTiXingActivity.this, FitnessResultsSummaryActivity.class)
                            .putExtra("requsetInfo", requsetInfo)
                            .putExtra("saveId", result.getData()));
                } else {
                    ToastUtils.showToastShort("保存数据失败,请检查手机网络！");
                }
            }
        });
    }

    /**
     * @方法说明:按条件查询问题题干列表
     **/
    public void queryList() {
        BcaQuestionCond cond = new BcaQuestionCond();
        cond.setType(Byte.valueOf("5"));
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                DLResult<List<BcaQuestion>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<BcaQuestion>>>() {
                }.getType());
                list.clear();
                LogUtil.i("result--" + json.toString());
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
            View view = View.inflate(BodyTiXingActivity.this, R.layout.body_tixing_item, null);
            LinearLayout ll_ed_parent = view.findViewById(R.id.ll_ed_parent);

            gv_bodybase = view.findViewById(R.id.gv_bodybase);

            TextView tv_tigan = view.findViewById(R.id.tv_tigan);

            View line_v = view.findViewById(R.id.line_v);
            if (pos == list.size() - 1) {
                line_v.setVisibility(View.GONE);
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
                final EditText editText = new EditText(BodyTiXingActivity.this);
                editText.setBackgroundResource(R.drawable.rect_body);
                editText.setHint(editList.get(n));
                editText.setHintTextColor(Color.parseColor("#dcdcdc"));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                lp.setMargins(0, 15, 0, 0);
                editText.setLayoutParams(lp);
                editText.setMaxLines(1);
                editText.setTag(options2.get(n));
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


            bodyBaseGridAdapter = new BodyBaseGridAdapter(options1, list.get(pos).getId());
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

        BodyBaseGridAdapter(List<BcaOption> options, Long que_id) {
            this.options = options;
            this.que_id = que_id;
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

            View view = View.inflate(BodyTiXingActivity.this, R.layout.bodybase_grid_item, null);
            final CheckBox rb_grid = view.findViewById(R.id.rb_grid);
            rb_grid.setText(options.get(position).getTitle());
            rb_grid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rb_grid.isChecked()) {
                        BcaResult bcaResult = new BcaResult();
                        bcaResult.setOpt_id(Long.valueOf(position + ""));
                        bcaResult.setQuestion_id(Long.valueOf(que_id + ""));
                        resultList.add(bcaResult);
                        //deleteEqualsItem();
                    }
                }
            });


            return view;
        }

    }
}
