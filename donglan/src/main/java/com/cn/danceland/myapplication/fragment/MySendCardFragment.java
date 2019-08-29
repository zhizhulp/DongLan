package com.cn.danceland.myapplication.fragment;

import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.RequestSendCardBean;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.view.RoundImageView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.cn.danceland.myapplication.R.id.tv_cardtype;

/**
 * Created by shy on 2018/3/23 10:22
 * Email:644563767@qq.com
 */


public class MySendCardFragment extends BaseFragment {
    private ListView mListView;
    private List<RequestSendCardBean.Data> mCardList = new ArrayList<>();
    private MyListViewAdapter myListViewAdapter;
    Gson gson = new Gson();
    private TextView tv_error;
    private ImageView iv_error;

    @Override
    public View initViews() {
        View v = View.inflate(mActivity, R.layout.fragment_my_card, null);
        mListView = v.findViewById(R.id.listview);
        View listEmptyView = v.findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        iv_error = listEmptyView.findViewById(R.id.iv_error);
        iv_error.setImageResource(R.drawable.img_error);
        tv_error.setText("您还没有给他人送出会员卡");
        mListView.setEmptyView(listEmptyView);
        myListViewAdapter = new MyListViewAdapter();
        mListView.setAdapter(myListViewAdapter);
        return v;

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void initData() {
        findAllCard();
    }

    /**
     * 查找全部送出会员卡
     */
    private void findAllCard() {

        MyStringRequest request = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.FIND_ALL_OTHER_CARD_LIST), new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestSendCardBean myCardListBean = new RequestSendCardBean();
                myCardListBean = gson.fromJson(s, RequestSendCardBean.class);
                mCardList = myCardListBean.getData();
                myListViewAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                iv_error.setImageResource(R.drawable.img_error7);
                tv_error.setText("网络异常");
            }
        }) {

        };
        MyApplication.getHttpQueues().add(request);

    }


    class MyListViewAdapter extends BaseAdapter

    {

        @Override
        public int getCount() {
            return mCardList.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();

                view = View.inflate(mActivity, R.layout.listview_item_my_send_club_card, null);
                viewHolder.tv_name = view.findViewById(R.id.tv_cardname);
                viewHolder.tv_number = view.findViewById(R.id.tv_number);
                viewHolder.tv_time = view.findViewById(R.id.tv_time);

                viewHolder.tv_cardtype = view.findViewById(tv_cardtype);
                viewHolder.tv_order_name = view.findViewById(R.id.tv_order_name);
                viewHolder.tv_phone = view.findViewById(R.id.tv_phone);
                viewHolder.iv_card = view.findViewById(R.id.iv_card);
                viewHolder.item_layout_cv = view.findViewById(R.id.item_layout_cv);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(getActivity(), 100f));
            if (i == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 11f));
            } else if (i == mCardList.size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 5f), DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 5f), DensityUtils.dp2px(getActivity(), 16f), DensityUtils.dp2px(getActivity(), 11f));
            }
            viewHolder.item_layout_cv.setLayoutParams(layoutParams);
            if (mCardList.get(i).getCharge_mode() == 1) {//计时卡
                viewHolder.tv_cardtype.setText("卡类型：计时卡");
            }
            if (mCardList.get(i).getCharge_mode() == 2) {//计次卡
                viewHolder.tv_cardtype.setText("卡类型：计次卡");
                viewHolder.tv_cardtype.setText("卡类型：计次卡（剩余次数：" + mCardList.get(i).getTotal_count() + "次）");
            }
            if (mCardList.get(i).getCharge_mode() == 3) {//储值卡
                viewHolder.tv_cardtype.setText("卡类型：储值卡");
            }


            viewHolder.tv_name.setText(mCardList.get(i).getType_name());

//            if (!TextUtils.isEmpty(mCardList.get(i).getTotal_count())){
//                viewHolder.tv_number.setText("次数："+mCardList.get(i).getTotal_count() + "次");
//                viewHolder.tv_number.setVisibility(View.VISIBLE);
//            }else {
//                viewHolder.tv_number.setVisibility(View.GONE);
//            }
            if (TextUtils.isEmpty(mCardList.get(i).getEnd_date())) {
                viewHolder.tv_time.setText("未开卡");
            } else {
                StringBuilder sb = new StringBuilder(mCardList.get(i).getEnd_date());

                String[] b = sb.toString().split(" ");

                viewHolder.tv_time.setText(b[0] + "到期");
            }
            viewHolder.tv_phone.setText("好友电话：" + mCardList.get(i).getPhone_no());
            viewHolder.tv_order_name.setText("好友姓名：" + mCardList.get(i).getMember_name());


//            //设置图片圆角角度
//           RoundedCorners roundedCorners = new RoundedCorners(6);
////通过RequestOptions扩展功能
//            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300).placeholder(R.drawable.sijiao_card);
            //第一个是上下文，第二个是圆角的弧度
            RequestOptions options = new RequestOptions().placeholder(R.drawable.sijiao_card).transform(new GlideRoundTransform(mActivity, 6));

            Glide.with(mActivity).load(mCardList.get(i).getImg_url()).apply(options).into(viewHolder.iv_card);
            return view;
        }

        class ViewHolder {
            TextView tv_name;
            TextView tv_number;
            TextView tv_time;
            TextView tv_cardtype;
            TextView tv_order_name;
            TextView tv_phone;
            RoundImageView iv_card;
            CardView item_layout_cv;
        }

    }
}
