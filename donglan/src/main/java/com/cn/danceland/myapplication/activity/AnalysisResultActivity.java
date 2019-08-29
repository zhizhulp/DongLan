package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.bca.bcaanalysis.BcaAnalysis;
import com.cn.danceland.myapplication.bean.bca.bcaanalysis.BcaAnalysisCond;
import com.cn.danceland.myapplication.bean.bca.bcaanalysis.BcaAnalysisRequest;
import com.cn.danceland.myapplication.bean.bca.bcaoption.BcaOption;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2018/4/20.
 */

public class AnalysisResultActivity extends BaseActivity {

    DongLanTitleView rl_bodybase_title;
    ListView lv_bodybase;
    private Gson gson;
    private BcaAnalysisRequest request;
    List<BcaAnalysis> list;
    ResultAdapter resultAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodybase);
        initHost();
        initView();
        queryList();
    }

    private void initHost() {

        request = new BcaAnalysisRequest();

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        list = new ArrayList<>();
    }

    private void initView() {

        rl_bodybase_title = findViewById(R.id.rl_bodybase_title);
        rl_bodybase_title.setTitle("结果汇总");

        lv_bodybase = findViewById(R.id.lv_bodybase);

        resultAdapter = new ResultAdapter(list);
        lv_bodybase.setAdapter(resultAdapter);

    }

    /**
     * @方法说明:按条件查询体测分析列表
     **/
    public void queryList() {
        BcaAnalysisCond cond = new BcaAnalysisCond();
        // TODO 准备查询条件
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                DLResult<List<BcaAnalysis>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<BcaAnalysis>>>() {
                }.getType());
                if (result.isSuccess()) {
                    list = result.getData();
                    resultAdapter.notifyDataSetChanged();
                    // TODO 请求成功后的代码
                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        });
    }

    private class ResultAdapter extends BaseAdapter{
        List<BcaAnalysis> list;
        ResultAdapter(List<BcaAnalysis> list){
            this.list = list;
        }

        @Override
        public int getCount() {
            return list==null? 0:list.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {

            View inflate = View.inflate(AnalysisResultActivity.this, R.layout.analysisresult_item, null);
            TextView tv_tigan = inflate.findViewById(R.id.tv_tigan);
            TextView tv_xuanxiang = findViewById(R.id.tv_xuanxiang);

            tv_tigan.setText(list.get(position).getqList().get(position).getCentent());
            List<BcaOption> options = list.get(position).getqList().get(position).getOptions();
            StringBuilder stringBuilder = new StringBuilder();
            for(BcaOption option:options){
                stringBuilder.append(option.getTitle());
            }
            tv_xuanxiang.setText(stringBuilder.toString());

            return inflate;
        }
    }
}
