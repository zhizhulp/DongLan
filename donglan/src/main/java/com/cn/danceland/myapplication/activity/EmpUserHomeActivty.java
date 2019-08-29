package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestPingFenBean;
import com.cn.danceland.myapplication.bean.RequestPingJiaBean;
import com.cn.danceland.myapplication.bean.RequestZzrzLIstBean;
import com.cn.danceland.myapplication.bean.RequsetUserDynInfoBean;
import com.cn.danceland.myapplication.evntbus.EventConstants;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyListView;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.willy.ratingbar.ScaleRatingBar;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.value;
import static com.cn.danceland.myapplication.R.id.ll_my_dyn;
import static com.cn.danceland.myapplication.R.id.ll_my_fans;
import static com.cn.danceland.myapplication.R.id.ll_my_guanzhu;
import static com.cn.danceland.myapplication.pictureviewer.PictureConfig.position;

/**
 * Created by shy on 2018/4/10 15:26
 * Email:644563767@qq.com
 */


public class EmpUserHomeActivty extends BaseActivity implements View.OnClickListener {
    private ImageView iv_avatar;
    private TextView tv_nick_name;

//    private TextView tv_dyn;
//    private TextView tv_gauzhu_num, tv_message, tv_guanzhu;
//    private TextView tv_fans;
    private ScaleRatingBar sr_pingfen;//评分
    private float pingfen;
    private MyListView lv_hypf;
    private MyListView lv_zzrz;
    MyPingJiaAdapter myPingJiaAdapter = new MyPingJiaAdapter();
    MyZzrzAdapter myZzrzAdapter = new MyZzrzAdapter();
    private TextView tv_pingfen;
    private ImageView iv_sex;
    private TextView tv_honor;
    private TextView tv_sign;
    private TextView tv_goodat;

    private String person_id,employee_id,branch_id;
    private String avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_user_home);
        initView();
        initData();
    }

    private void initData() {
        queryUserInfo(person_id);
        queryPingfen(employee_id, branch_id);
        queryPingJia(employee_id, branch_id);
        queryZzrz(person_id);
    }

    private List<RequestPingJiaBean.Data.Content> pingjia_data = new ArrayList<>();
    private List<RequestZzrzLIstBean.Data> zzrz_data = new ArrayList<>();

    private void initView() {

        person_id=getIntent().getStringExtra("person_id");
        employee_id=getIntent().getStringExtra("employee_id");
        branch_id=getIntent().getStringExtra("branch_id");
        avatar = getIntent().getStringExtra("avatar");
        sr_pingfen = findViewById(R.id.sr_pingfen);
//        tv_dyn = findViewById(R.id.tv_dyn);
//        tv_gauzhu_num = findViewById(R.id.tv_gauzhu_num);
//        tv_fans = findViewById(R.id.tv_fans);
        tv_nick_name = findViewById(R.id.tv_nick_name);
        iv_avatar = findViewById(R.id.iv_avatar);
        iv_sex = findViewById(R.id.iv_sex);
        iv_avatar.setOnClickListener(this);
        lv_hypf = findViewById(R.id.lv_hypf);
        lv_zzrz = findViewById(R.id.lv_zzrz);
        lv_hypf.setAdapter(myPingJiaAdapter);
        lv_zzrz.setAdapter(myZzrzAdapter);
        tv_pingfen = findViewById(R.id.tv_pingfen);
//        tv_guanzhu = findViewById(R.id.tv_guanzhu);
//        iv_guanzhu = findViewById(R.id.iv_guanzhu);
        tv_honor = findViewById(R.id.tv_honor);
        tv_sign = findViewById(R.id.tv_sign);
        tv_goodat = findViewById(R.id.tv_goodat);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case ll_my_dyn:
                break;
            case ll_my_guanzhu:
                break;
            case ll_my_fans:
                break;
            case value:
                break;
            case R.id.ll_guanzhu:
                break;
            case R.id.ll_sixin:
                break;
            case  R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
                    sr_pingfen.setRating(pingfen);
                    tv_pingfen.setText(pingfen + "");
                    break;
                case 2:
                    setData();
                    break;
                case 3:
                    break;
            }

        }
    };

    private void setData() {
//        tv_gauzhu_num.setText(userInfo.getFollow_no() + "");
//
//        tv_fans.setText(userInfo.getFanse_no() + "");
//        tv_dyn.setText(userInfo.getDyn_no() + "");
        tv_nick_name.setText(userInfo.getPerson().getCname());

        RequestOptions options = new RequestOptions().transform(new GlideRoundTransform(EmpUserHomeActivty.this, 10)).placeholder(R.drawable.img_avatar1).error(R.drawable.img_avatar1);
        Glide.with(EmpUserHomeActivty.this)
                .load(avatar)
                .apply(options)
                .into(iv_avatar);
//        if (userInfo.getIs_follow()){
//            tv_guanzhu.setText("已关注");
//            iv_guanzhu.setImageResource(R.drawable.img_xin1);
//        }else {
//            tv_guanzhu.setText("+关注");
//            iv_guanzhu.setImageResource(R.drawable.img_xin);
//        }
            if (TextUtils.equals(userInfo.getPerson().getGender(),"1")){
                iv_sex.setImageResource(R.drawable.img_sex1);
            }else {
                iv_sex.setImageResource(R.drawable.img_sex2);
            }
        if (!TextUtils.isEmpty(userInfo.getPerson().getSign())){
            tv_sign.setText(userInfo.getPerson().getSign());
        }
        if (!TextUtils.isEmpty(userInfo.getPerson().getHobby())){
            tv_honor.setText(userInfo.getPerson().getHobby());
        }
        if (!TextUtils.isEmpty(userInfo.getPerson().getGood_at())){
            tv_goodat.setText(userInfo.getPerson().getGood_at());
        }
    }


    class MyPingJiaAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pingjia_data.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
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
                view = View.inflate(EmpUserHomeActivty.this, R.layout.listview_item_pingjia, null);
                viewHolder.iv_avatar = view.findViewById(R.id.iv_avatar);
                viewHolder.tv_nickname = view.findViewById(R.id.tv_nickname);
                viewHolder.tv_time = view.findViewById(R.id.tv_time);
                viewHolder.tv_content = view.findViewById(R.id.tv_content);
                viewHolder.ll_item = view.findViewById(R.id.ll_item);
                viewHolder.sr_pingfen1 = view.findViewById(R.id.sr_pingfen1);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
            Glide.with(EmpUserHomeActivty.this)
                    .load(pingjia_data.get(position).getSelf_avatar_path())
                    .apply(options)
                    .into(viewHolder.iv_avatar);
            viewHolder.tv_nickname.setText(pingjia_data.get(position).getNick_name());
            viewHolder.tv_time.setText(TimeUtils.timeStamp2Date(pingjia_data.get(i).getCreate_date() + "", "yyyy-MM-dd HH:mm:ss"));

            viewHolder.tv_content.setText(pingjia_data.get(i).getContent());
            //   LogUtil.i( pingjia_data.get(i).getEmployee_score()+"");
            viewHolder.sr_pingfen1.setRating(pingjia_data.get(i).getEmployee_score());
            return view;
        }

        class ViewHolder {
            TextView tv_nickname;//昵称
            ImageView iv_avatar;//头像
            TextView tv_time;//时间
            hani.momanii.supernova_emoji_library.Helper.EmojiconTextView tv_content;//内容
            LinearLayout ll_item;
            ScaleRatingBar sr_pingfen1;//评分
        }

    }

    class MyZzrzAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return zzrz_data.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
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
                view = View.inflate(EmpUserHomeActivty.this, R.layout.listview_item_zzrz, null);
                viewHolder.tv_date = view.findViewById(R.id.tv_date);
                viewHolder.tv_org_name = view.findViewById(R.id.tv_org_name);
                viewHolder.tv_end_time = view.findViewById(R.id.tv_end_time);
                viewHolder.iv_pic = view.findViewById(R.id.iv_pic);
                viewHolder.tv_name = view.findViewById(R.id.tv_name);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
//            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
            Glide.with(EmpUserHomeActivty.this)
                    .load(zzrz_data.get(i).getImg_url())

                    .into(viewHolder.iv_pic);
            viewHolder.tv_org_name.setText("发证机构：" + zzrz_data.get(i).getAward_dept());
            viewHolder.tv_name.setText("证件名称：" + zzrz_data.get(i).getName());
            viewHolder.tv_date.setText("发证日期：" + zzrz_data.get(i).getGain_date());
            viewHolder.tv_end_time.setText("截止日期：" + zzrz_data.get(i).getIndate());
            return view;
        }

        class ViewHolder {
            TextView tv_org_name;
            ImageView iv_pic;
            TextView tv_date;
            TextView tv_end_time;
            TextView tv_name;
        }

    }


    private RequsetUserDynInfoBean.Data userInfo;
    Gson gson = new Gson();

    class PingjiaBean {
        public String branch_id;
        public String employee_id;
        public String page;
        public String size;
        private String person_id;

    }


    private void queryZzrz(String person_id) {
        PingjiaBean pingjiaBean = new PingjiaBean();
        pingjiaBean.person_id = person_id;
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.HOST + "/attestation/queryList", gson.toJson(pingjiaBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestZzrzLIstBean zzrzLIstBean = gson.fromJson(jsonObject.toString(), RequestZzrzLIstBean.class);
                if (zzrzLIstBean.getSuccess()) {
                    zzrz_data = zzrzLIstBean.getData();
                    myZzrzAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


            }
        });
        MyApplication.getHttpQueues().add(request);
    }

    private void queryPingJia(String employee_id, String branch_id) {
        PingjiaBean pingjiaBean = new PingjiaBean();
        pingjiaBean.branch_id = branch_id;
        pingjiaBean.employee_id = employee_id;
        pingjiaBean.page = "0";
        pingjiaBean.size = "3";
        LogUtil.i(gson.toJson(pingjiaBean));
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.HOST + "/evaluate/queryPage", gson.toJson(pingjiaBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                RequestPingJiaBean requestPingJiaBean = gson.fromJson(jsonObject.toString(), RequestPingJiaBean.class);
                if (requestPingJiaBean.getSuccess()) {
                    pingjia_data = requestPingJiaBean.getData().getContent();
                    myPingJiaAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MyApplication.getHttpQueues().add(request);

    }

    private void queryPingfen(final String employeeId, final String branchId) {
        final MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_PING_FEN), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestPingFenBean pingFenBean = gson.fromJson(s, RequestPingFenBean.class);
                if (pingFenBean.getSuccess()) {
                    Message msg = Message.obtain();
                    //   msg.obj = data;
                    msg.what = 1; //标志消息的标志
//                    msg.arg1=pingFenBean.getData();
                    pingfen = pingFenBean.getData();
                    handler.sendMessage(msg);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("employeeId", employeeId);
                map.put("branchId", branchId);
                LogUtil.i(map.toString());
                return map;
            }


        };
        MyApplication.getHttpQueues().add(request);
    }

    /***
     * 查找个人资料
     * @param id 用户id
     */
    private void queryUserInfo(final String id) {

        String params = id;

        String url = Constants.plus(Constants.QUERY_USER_DYN_INFO_URL) + params;

        MyStringRequest request = new MyStringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {

                LogUtil.i(s);
                Gson gson = new Gson();
                RequsetUserDynInfoBean requestInfoBean = gson.fromJson(s, RequsetUserDynInfoBean.class);


                userInfo = requestInfoBean.getData();


                if (TextUtils.equals(id, SPUtils.getString(Constants.MY_USERID, null))) {
                    //如果是本人更新本地缓存
                    Data data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
                    data.setPerson(userInfo.getPerson());
                    DataInfoCache.saveOneCache(data, Constants.MY_INFO);
                    SPUtils.setInt(Constants.MY_DYN, requestInfoBean.getData().getDyn_no());
                    SPUtils.setInt(Constants.MY_FANS, requestInfoBean.getData().getFanse_no());
                    SPUtils.setInt(Constants.MY_FOLLOWS, requestInfoBean.getData().getFollow_no());


                    EventBus.getDefault().post(new StringEvent("", EventConstants.UPDATE_USER_INFO));
                }


                Message msg = Message.obtain();
                //   msg.obj = data;
                msg.what = 2; //标志消息的标志
                handler.sendMessage(msg);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                //dialog.dismiss();
                ToastUtils.showToastShort("请查看网络连接");

            }

        }
        ) {

        };
        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("queryUserInfo");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);

    }

}
