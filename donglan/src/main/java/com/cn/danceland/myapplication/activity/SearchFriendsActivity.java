package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.cn.danceland.myapplication.app.AppManager;
import com.cn.danceland.myapplication.bean.RequestInfoBean;
import com.cn.danceland.myapplication.bean.RequsetFindUserBean;
import com.cn.danceland.myapplication.evntbus.EventConstants;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.RoundImageView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cn.danceland.myapplication.R.id.ll_result;

/**
 * 体侧分析 搜索会员
 * Created by yxx on 2017/10/30 09:32
 */
public class SearchFriendsActivity extends BaseActivity implements View.OnClickListener {
    EditText mEtPhone;
    private ImageButton iv_del;
    private LinearLayout ll_search;
    private TextView tv_search;
    private TextView tv_nickname;
    private ImageView iv_avatar;
    private TextView tv_guanzhu;
    private TextView tv_title;
    private List<RequsetFindUserBean.Data> dataList = new ArrayList<>();

    private String from;
    int memberId;
    int personId;
    String member_no;
    private ListView listView;
    private MyListAatapter myListAatapter = new MyListAatapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        AppManager.getAppManager().addActivity(this);
        from = getIntent().getStringExtra("from");
        iv_del = findViewById(R.id.iv_del);
        ll_search = findViewById(R.id.ll_search);
        listView = findViewById(R.id.listview);
        listView.setAdapter(myListAatapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("体测".equals(from)) {
                    if ("true".equals(getIntent().getStringExtra("isAnalysis"))) {
                        Intent intent = new Intent(SearchFriendsActivity.this, FitnessTestSearchResultActivity.class);
                        intent.putExtra("requsetInfo", dataList.get(position));
                        startActivity(intent);
                    }
                }
            }
        });
        tv_search = findViewById(R.id.tv_search);
        mEtPhone = findViewById(R.id.et_phone);

        tv_guanzhu = findViewById(R.id.tv_guanzhu);
        tv_title = findViewById(R.id.donglan_title);
        if ("体测".equals(from)) {
            tv_title.setText("搜索会员");
        }
        setListener();

        mEtPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if ("体测".equals(from)) {
                        searchMember(mEtPhone.getText().toString().trim());
                    } else {
                        findUser(mEtPhone.getText().toString().trim());
                    }
                }
                return false;
            }
        });
    }

    private void setListener() {
        ll_search.setOnClickListener(this);
        iv_del.setOnClickListener(this);
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            //监听edit
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
                    iv_del.setVisibility(View.VISIBLE);
                    ll_search.setVisibility(View.VISIBLE);
                    tv_search.setText("搜索：“" + mEtPhone.getText().toString().trim() + "”");

                } else {
                    iv_del.setVisibility(View.GONE);
                    ll_search.setVisibility(View.GONE);
                    tv_search.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_del:
                mEtPhone.setText("");
                if ("体测".equals(from)) {
                    searchMember(mEtPhone.getText().toString().trim());
                } else {
                    findUser(mEtPhone.getText().toString().trim());
                }
                break;
            case R.id.tv_guanzhu://加关注
                break;
            case R.id.ll_search://搜索
                if ("体测".equals(from)) {
                    searchMember(mEtPhone.getText().toString().trim());
                } else {
                    findUser(mEtPhone.getText().toString().trim());
                }
                break;
            case ll_result:
                if ("体测".equals(from)) {
                    if ("true".equals(getIntent().getStringExtra("isAnalysis"))) {
                        Intent intent = new Intent(SearchFriendsActivity.this, BodyBaseActivity.class);
                        intent.putExtra("id", personId + "");
                        intent.putExtra("memberId", memberId + "");
                        intent.putExtra("member_no", member_no);
                        startActivity(intent);
                    }
                }
                break;
            default:
                break;
        }
    }

    class StrBean1 {
        public boolean is_follower;
        public String user_id;
    }

    /**
     * 加关注
     *
     * @param id
     * @param b
     */
    private void addGuanzhu(final String id, final boolean b) {

        StrBean1 strBean1 = new StrBean1();
        strBean1.is_follower = b;
        strBean1.user_id = id;
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.ADD_GUANZHU), new Gson().toJson(strBean1), new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i(jsonObject.toString());
                Gson gson = new Gson();
                RequestInfoBean requestInfoBean = new RequestInfoBean();
                requestInfoBean = gson.fromJson(jsonObject.toString(), RequestInfoBean.class);
                if (requestInfoBean.getSuccess()) {
                    ToastUtils.showToastShort("关注成功");
                    tv_guanzhu.setText("已关注");
                    tv_guanzhu.setClickable(false);

                    EventBus.getDefault().post(new StringEvent(id, EventConstants.ADD_GUANZHU));

                } else {
                    ToastUtils.showToastShort("关注失败");
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort("请查看网络连接");
            }
        });
        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("addGuanzhu");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }

    private void searchMember(final String phone) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FINDMEMBER), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequsetFindUserBean infoBean = gson.fromJson(s, RequsetFindUserBean.class);

                if (infoBean.getSuccess()) {
                    dataList = infoBean.getData();
                    myListAatapter.notifyDataSetChanged();
                    if(dataList.size()==0){
                        ToastUtils.showToastShort("无结果");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("phone", phone);
                return map;
            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    /*** 查找用户加关注
     *
     * @param phone
     */

    private void findUser(final String phone) {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_ADD_USER_USRL), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequsetFindUserBean infoBean = gson.fromJson(s, RequsetFindUserBean.class);

                if (infoBean.getSuccess()) {
                    dataList = infoBean.getData();
                    myListAatapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogUtil.i(volleyError.toString() + volleyError.networkResponse.statusCode);

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", phone);
                return map;
            }
        };
        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("findUser");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }

    class MyListAatapter extends BaseAdapter {

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
            return 0;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();

                convertView = View.inflate(SearchFriendsActivity.this, R.layout.listview_item_search_user, null);
                vh.iv_avatar = convertView.findViewById(R.id.iv_avatar);
                vh.tv_name = convertView.findViewById(R.id.tv_nickname);
                vh.age_tv = convertView.findViewById(R.id.age_tv);
                vh.tel_tv = convertView.findViewById(R.id.tel_tv);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_avatar1);

            if ("体测".equals(from)) {
                vh.tv_name.setText(dataList.get(position).getCname());
                Glide.with(SearchFriendsActivity.this).load(dataList.get(position).getAvatar_url()).apply(options).into(vh.iv_avatar);
                if (dataList.get(position).getBirthday() != null) {
                    int age = TimeUtils.getAgeFromBirthTime(new Date(TimeUtils.date2TimeStamp(dataList.get(position).getBirthday(), "yyyy-MM-dd")));
                    vh.age_tv.setText(age + "岁");//年龄
                }
                if (dataList.get(position).getPhone_no() != null) {
                    vh.tel_tv.setText(dataList.get(position).getPhone_no());
                }
            }
            return convertView;

        }
    }

    class ViewHolder {
        public RoundImageView iv_avatar;
        public TextView tv_name;
        public TextView age_tv;
        public TextView tel_tv;
    }
}


