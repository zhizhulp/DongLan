package com.cn.danceland.myapplication.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.FitnessTestNoticeActivity;
import com.cn.danceland.myapplication.bean.FitnessResultsSummaryBean;
import com.cn.danceland.myapplication.bean.bca.bcaoption.BcaOption;
import com.cn.danceland.myapplication.bean.bca.bcaquestion.BcaQuestion;
import com.cn.danceland.myapplication.shouhuan.adapter.StepAdapter;
import com.cn.danceland.myapplication.shouhuan.bean.StepBean;
import com.cn.danceland.myapplication.view.NoScrollListView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 体测分析-结果汇总 Adapter
 * Created by yxx on 2018-09-20.
 */

public class FitnessResultsSummaryAdapter extends BaseAdapter {
    private List<FitnessResultsSummaryBean.QuestionTypes> questionTypesList;
    private Context context;
    private ListAdapter adapter;

    public FitnessResultsSummaryAdapter() {
        super();
    }

    public FitnessResultsSummaryAdapter(Context context, List<FitnessResultsSummaryBean.QuestionTypes> questionTypesList) {
        super();
        this.questionTypesList = questionTypesList;
        this.context = context;
    }

    public void setData(ArrayList<FitnessResultsSummaryBean.QuestionTypes> questionTypesList) {
        this.questionTypesList = questionTypesList;
    }

    public void clear() {
        questionTypesList.clear();//先清除这个
    }

    @Override
    public int getCount() {
        return questionTypesList.size();
    }

    @Override
    public Object getItem(int i) {
        return questionTypesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listitem_fitness_results_summary, null);
            viewHolder = new ViewHolder();
            viewHolder.title_tv = view.findViewById(R.id.title_tv);
            viewHolder.child_listview = view.findViewById(R.id.child_listview);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String titleStr = "一、";
        switch (position) {
            case 0:
                titleStr = "一、";
                break;
            case 1:
                titleStr = "二、";
                break;
            case 2:
                titleStr = "三、";
                break;
            case 3:
                titleStr = "四、";
                break;
            case 4:
                titleStr = "五、";
                break;
        }
        viewHolder.title_tv.setText(titleStr + (questionTypesList.get(position).getTypeName()) + "");//一级
        adapter = new ListAdapter(questionTypesList.get(position).getQuestions());
        viewHolder.child_listview.setAdapter(adapter);
        return view;
    }

    static class ViewHolder {
        TextView title_tv;
        NoScrollListView child_listview;
    }

    class ListAdapter extends BaseAdapter {
        private List<FitnessResultsSummaryBean.Questions> questions;//二级

        public ListAdapter() {
            super();
        }

        public ListAdapter(List<FitnessResultsSummaryBean.Questions> questions) {
            this.questions = questions;
        }

        @Override
        public int getCount() {
            if (questions != null) {
                return questions.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            ListAdapter.ViewHolder vh = null;
            if (convertView == null) {
                vh = new ListAdapter.ViewHolder();
                convertView = View.inflate(context, R.layout.listitem_fitness_results_summary, null);
                vh.title_tv = convertView.findViewById(R.id.title_tv);
                vh.child_listview = convertView.findViewById(R.id.child_listview);
                convertView.setTag(vh);
            } else {
                vh = (ListAdapter.ViewHolder) convertView.getTag();
            }
            vh.title_tv.setTextColor(context.getResources().getColor(R.color.colorGray21));
            vh.title_tv.setTextSize(12);
            vh.title_tv.setText(questions.get(position).getOrder_no() + "." + questions.get(position).getCentent());
            ChildListAdapter childAdapter = new ChildListAdapter(questions.get(position).getOptions());
            vh.child_listview.setAdapter(childAdapter);
            return convertView;
        }

        class ViewHolder {
            public TextView title_tv;
            public NoScrollListView child_listview;
        }
    }

    class ChildListAdapter extends BaseAdapter {
        private List<BcaOption> options;//三级

        public ChildListAdapter() {
            super();
        }

        public ChildListAdapter(List<BcaOption> options) {
            this.options = options;
        }

        @Override
        public int getCount() {
            if (options != null) {
                return options.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = View.inflate(context, R.layout.listitem_fitness_results_summary, null);
                vh.title_tv = convertView.findViewById(R.id.title_tv);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.title_tv.setTextColor(context.getResources().getColor(R.color.colorGray21));
            vh.title_tv.setTextSize(12);
            BcaOption bcaOption = options.get(position);
            Byte type = bcaOption.getType();
            if (type == 1) {
                vh.title_tv.setText(bcaOption.getOrder_no() + "." + bcaOption.getResult());
            } else {
                vh.title_tv.setText(bcaOption.getOrder_no() + "." + bcaOption.getPerfix() + ":" + bcaOption.getContent());
            }

            return convertView;
        }

        class ViewHolder {
            public TextView title_tv;
        }
    }
}
