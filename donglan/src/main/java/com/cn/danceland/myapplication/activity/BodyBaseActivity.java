package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.app.AppManager;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestInfoBean;
import com.cn.danceland.myapplication.bean.RequsetFindUserBean;
import com.cn.danceland.myapplication.bean.bca.bcaoption.BcaOption;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestion;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestionCond;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestionRequest;
import com.cn.danceland.myapplication.bean.bca.bcaresult.BcaResult;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.CustomGridView;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.NoScrollListView;
import com.cn.danceland.myapplication.view.RoundImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 身体详细情况111
 * Created by feng on 2018/3/29.
 */

public class BodyBaseActivity extends BaseActivity {

    NoScrollListView listView;
    //    View hearerView;
    private BcaQuestionRequest request;
    private Gson gson;
    List<BcaQuestion> list;
    BodyBaseAdapter bodyBaseAdapter;
    Data myInfo;
    CustomGridView gv_bodybase;
    BodyBaseGridAdapter bodyBaseGridAdapter;
    DongLanTitleView rl_bodybase_title;
    LinearLayout body_button;
    List<BcaResult> resultList;
    EditText editText;
    Long que_id;
    RoundImageView circle_image;
    TextView tv_nick_name, tv_male_age, tv_phone;
    private ImageView iv_sex;
    private TextView child_title_tv;//小标题

    private RequsetFindUserBean.Data requsetInfo;//前面搜索到的对象

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

        resultList = new ArrayList<>();
        request = new BcaQuestionRequest();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        list = new ArrayList<>();
        myInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        requsetInfo = (RequsetFindUserBean.Data) getIntent().getSerializableExtra("requsetInfo");//前面搜索到的对象
    }

    private void initHeaderData() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.QUERY_USER_DYN_INFO_URL) + requsetInfo.getPerson_id(), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                RequestInfoBean requestInfoBean = gson.fromJson(s, RequestInfoBean.class);
                if (requestInfoBean != null) {
                    Data data = requestInfoBean.getData();
                    if (data != null) {
                        Glide.with(BodyBaseActivity.this).load(data.getPerson().getSelf_avatar_path()).into(circle_image);
                        tv_nick_name.setText(data.getPerson().getNick_name());
                        if (TextUtils.equals(data.getPerson().getGender(), "1")) {
                            iv_sex.setImageResource(R.drawable.img_sex1);
                        } else if (TextUtils.equals(data.getPerson().getGender(), "2")) {
                            iv_sex.setImageResource(R.drawable.img_sex2);
                        } else {
                            iv_sex.setVisibility(View.INVISIBLE);
                        }

                        if (data.getPerson().getBirthday() != null) {
                            Time time = new Time();
                            time.setToNow();
                            int age = time.year - Integer.valueOf(data.getPerson().getBirthday().split("-")[0]);
                            tv_male_age.setText(age + " 岁");
                        }
                        tv_phone.setText(data.getPerson().getPhone_no());
                    }
                    //initDatas(data);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("请检查手机网络！");
            }
        }) {

        };

        MyApplication.getHttpQueues().add(stringRequest);
    }


    private void initView() {

        listView = findViewById(R.id.lv_bodybase);

//        hearerView = View.inflate(BodyBaseActivity.this, R.layout.bodybase_header, null);
//        listView.addHeaderView(hearerView);

        bodyBaseAdapter = new BodyBaseAdapter();
        listView.setAdapter(bodyBaseAdapter);

        circle_image = findViewById(R.id.circle_image);
        tv_nick_name = findViewById(R.id.tv_nick_name);
        tv_male_age = findViewById(R.id.tv_male_age);
        tv_phone = findViewById(R.id.tv_phone);
        rl_bodybase_title = findViewById(R.id.rl_bodybase_title);
        rl_bodybase_title.setTitle("体测分析");
        child_title_tv = findViewById(R.id.child_title_tv);
        child_title_tv.setText("身体基本情况");

        body_button = findViewById(R.id.body_button);
        iv_sex = findViewById(R.id.iv_sex);

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
                LogUtil.i(resultList.toString());

                startActivity(new Intent(BodyBaseActivity.this, BodyDeatilActivity.class)
                        .putExtra("resultList", (Serializable) resultList)
                        .putExtra("requsetInfo", requsetInfo)
                );
            }
        });

        initHeaderData();
    }


    /**
     * @方法说明:按条件查询问题题干列表
     **/
    public void queryList() {
        BcaQuestionCond cond = new BcaQuestionCond();
        cond.setType(Byte.valueOf("1"));
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                LogUtil.i("request" + json.toString());
                DLResult<List<BcaQuestion>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<BcaQuestion>>>() {
                }.getType());
                list.clear();
                if (result.isSuccess()) {
                    list = result.getData();
                    bodyBaseAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        });
    }

    private void setEditText(EditText editText, Long id) {
        this.editText = editText;
        this.que_id = id;
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
            View view = View.inflate(BodyBaseActivity.this, R.layout.bodybase_item, null);
            LinearLayout ll_ed_parent = view.findViewById(R.id.ll_ed_parent);

            gv_bodybase = view.findViewById(R.id.gv_bodybase);

            TextView tv_tigan = view.findViewById(R.id.tv_tigan);

            RelativeLayout item_layout = view.findViewById(R.id.item_layout);
            if (pos == list.size() - 1) {
                RelativeLayout.LayoutParams linearParams1 = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearParams1.setMargins(DensityUtils.dp2px(BodyBaseActivity.this, 16f), DensityUtils.dp2px(BodyBaseActivity.this, 16f), DensityUtils.dp2px(BodyBaseActivity.this, 16f), DensityUtils.dp2px(BodyBaseActivity.this, 16f));
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
                final EditText editText = new EditText(BodyBaseActivity.this);
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

        public BodyBaseGridAdapter(List<BcaOption> options, Long que_id, Long is_single, int fatherpos) {
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
            return options.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(BodyBaseActivity.this, R.layout.bodybase_grid_item, null);
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
