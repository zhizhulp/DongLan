package com.cn.danceland.myapplication.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.AddTuiJianRenActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.bean.RequstRecommendBean;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.PhoneFormatCheckUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.ContainsEmojiEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shy on 2018/3/14 10:39
 * Email:644563767@qq.com
 * 我推荐的人
 */


public class RecommendFragment extends BaseFragment {

    private List<RequstRecommendBean.Data> dataList = new ArrayList<>();
    private Myadapter myadapter = new Myadapter();
    private StrBean strBean1;
    private TextView tv_error;
    private ImageView imageView;

    @Override
    public View initViews() {
        View v = View.inflate(mActivity, R.layout.fragment_recommend, null);
        v.findViewById(R.id.btn_add).setOnClickListener(this);
        ListView listView = v.findViewById(R.id.listview);
        View listEmptyView = v.findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        imageView.setImageResource(R.drawable.img_error);
        tv_error.setText("您还没有推荐过任何人，点击右下角推荐好友");
        listView.setEmptyView(listEmptyView);


        listView.setAdapter(myadapter);
        return v;
    }

    @Override
    public void initData() {
        super.initData();
        strBean.gender = 1;
        strBean1 = new StrBean();
        Data data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        strBean1.member_no = data.getPerson().getMember_no();
        introduce_querylist(strBean1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                startActivity(new Intent(mActivity, AddTuiJianRenActivity.class));
              //  showCustomizeDialog();
                break;
            default:
                break;
        }
    }

    StrBean strBean = new StrBean();

    private void showCustomizeDialog() {
    /* @setView 装入自定义View ==> R.layout.dialog_customize
     * 由于dialog_customize.xml只放置了一个EditView，因此和图8一样
     * dialog_customize.xml可自定义更复杂的View
     */
        final AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(mActivity);
        final View dialogView = LayoutInflater.from(mActivity)
                .inflate(R.layout.dialog_recommend, null);
//        customizeDialog.setTitle("被推荐人资料");
        customizeDialog.setView(dialogView);

        final ContainsEmojiEditText et_name =
                (ContainsEmojiEditText) dialogView.findViewById(R.id.et_name);
        final EditText et_phone =
                (EditText) dialogView.findViewById(R.id.et_phone);
        RadioGroup rg_sex = dialogView.findViewById(R.id.rg_sex);
        strBean.gender = 1;
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbtn_man:
                        strBean.gender = 1;
                        LogUtil.i("选择了男");
                        break;
                    case R.id.rbtn_weomen:
                        strBean.gender = 2;
                        LogUtil.i("选择了女");
                        break;
                    default:
                        break;
                }

            }
        });
//
        customizeDialog.setPositiveButton("确定", null);
        customizeDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        final AlertDialog dialog = customizeDialog.create();
        dialog.show();
        if (dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 获取EditView中的输入内容
                    if (TextUtils.isEmpty(et_name.getText().toString())) {
                        ToastUtils.showToastShort("请填写姓名");
                        return;
                    }
                    if (TextUtils.isEmpty(et_phone.getText().toString())) {
                        ToastUtils.showToastShort("请填写电话");
                        return;
                    }

                    if (StringUtils.isFirstNumeric(et_name.getText().toString())) {//姓名不能以数字开头
                        ToastUtils.showToastShort(MyApplication.getContext().getResources().getString(R.string.name_no_numeric_first_text));
                        return;
                    }
                    if (StringUtils.isAllNumeric(et_name.getText().toString())) {//姓名不能全数字
                        ToastUtils.showToastShort(MyApplication.getContext().getResources().getString(R.string.name_no_numeric_text));
                        return;
                    }

                    strBean.name = et_name.getText().toString();
                    strBean.phone_no = et_phone.getText().toString();

                    if (!PhoneFormatCheckUtils.isPhoneLegal(et_phone.getText().toString())) {
                        ToastUtils.showToastShort("输入电话不合法请重新输入");
                    } else {


                        introduce_save(strBean);
                        dialog.dismiss();
                    }
                }
            });


        }
    }


    class StrBean {
        Integer gender;
        String name;
        String phone_no;
        String member_no;
    }

    Gson gson = new Gson();

    /**
     * 添加推荐好友
     *
     * @param strBean
     */
    public void introduce_save(final StrBean strBean) {

        LogUtil.i(gson.toJson(strBean));
        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.INTRODUCE_SAVE), gson.toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestSimpleBean requsetSimpleBean = gson.fromJson(jsonObject.toString(), RequestSimpleBean.class);
                if (requsetSimpleBean.getSuccess()) {
                    ToastUtils.showToastShort("成功推荐");
                    introduce_querylist(strBean1);
                    strBean.gender = 1;
                } else {
                    ToastUtils.showToastShort("推荐失败" + requsetSimpleBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }

    /**
     * 查找已推荐好友
     *
     * @param strBean
     */
    public void introduce_querylist(final StrBean strBean) {

//        StrBean strBean = new StrBean();
//        strBean.page = pageCount - 1 + "";
//        strBean.member_id = id;
//        String s = gson.toJson(strBean);

//        JSONObject jsonObject = new JSONObject(s.toString());
        LogUtil.i(gson.toJson(strBean));
        MyJsonObjectRequest stringRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.INTRODUCE_QUERYLIST), gson.toJson(strBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequstRecommendBean requstRecommendBean = gson.fromJson(jsonObject.toString(), RequstRecommendBean.class);
                dataList = requstRecommendBean.getData();
                myadapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                // ToastUtils.showToastShort(volleyError.toString());
                imageView.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        });
        MyApplication.getHttpQueues().add(stringRequest);
    }

    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = View.inflate(mActivity, R.layout.listview_item_recommend, null);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_phone = view.findViewById(R.id.tv_phone);
            TextView tv_time = view.findViewById(R.id.tv_time);
            TextView tv_status = view.findViewById(R.id.tv_status);
            ImageView iv_sex = view.findViewById(R.id.iv_sex);

            tv_name.setText(dataList.get(i).getName());
            if (dataList.get(i).getPhone_no()!=null){
                tv_phone.setText("联系电话:"+dataList.get(i).getPhone_no());
            }else {
                tv_phone.setText("联系电话:空");
            }

            tv_time.setText("推荐时间:"+TimeUtils.timeStamp2Date(dataList.get(i).getCreate_date() + "", new String("yyyy.MM.dd")));
            if (dataList.get(i).getGender() == 1) {

                iv_sex.setImageResource(R.drawable.img_sex1);
            } else {

                iv_sex.setImageResource(R.drawable.img_sex2);
            }
            if (dataList.get(i).getStatus() == 0) {
                tv_status.setText("推荐中");
                tv_status.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey));
                tv_status.setTextColor(getResources().getColor(R.color.white));
            } else if (dataList.get(i).getStatus() == 1) {
                tv_status.setText("推荐成功");
                tv_status.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey1));
                tv_status.setTextColor(getResources().getColor(R.color.color_dl_deep_blue));
            } else if (dataList.get(i).getStatus() == 2) {
                tv_status.setText("推荐失败");
                tv_status.setBackground(getResources().getDrawable(R.drawable.img_btn_bg_grey1));
                tv_status.setTextColor(getResources().getColor(R.color.color_dl_deep_blue));
            }

            return view;
        }
    }


}
