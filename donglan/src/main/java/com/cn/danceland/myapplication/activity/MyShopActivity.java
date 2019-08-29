package com.cn.danceland.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.MyJionShopList;
import com.cn.danceland.myapplication.bean.RequestLoginInfoBean;
import com.cn.danceland.myapplication.bean.RequestShopListInfo;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.RoundImageView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shy on 2017/12/13 10:15
 * Email:644563767@qq.com
 */
public class MyShopActivity extends BaseActivity implements View.OnClickListener {
    private MyListViewAdapter listViewAdapter;
    private List<MyJionShopList.Data> data = new ArrayList<>();
    private PullToRefreshListView lv_myshop;
    private String defaultshopId;
    private Data userInfo;
    private DongLanTitleView title;
    private TextView right_tv;

    private TextView tv_error;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        initView();
//        initData();
    }

    private void initView() {
        userInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        defaultshopId = userInfo.getPerson().getDefault_branch();
        lv_myshop = findViewById(R.id.lv_myshop);
        title = findViewById(R.id.title);
        right_tv = title.findViewById(R.id.donglan_right_tv);
        right_tv.setText("附近门店");
        right_tv.setVisibility(View.VISIBLE);
        right_tv.setOnClickListener(this);

        View listEmptyView = findViewById(R.id.rl_no_info);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        imageView.setImageResource(R.drawable.img_error);
        tv_error.setText("没有数据");
        lv_myshop.getRefreshableView().setEmptyView(listEmptyView);
        lv_myshop.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉下拉阴影
        //设置下拉刷新模式both是支持下拉和上拉
        lv_myshop.setMode(PullToRefreshBase.Mode.DISABLED);//DISABLED和普通listView一样用

        listViewAdapter = new MyListViewAdapter();
        lv_myshop.setAdapter(listViewAdapter);

        lv_myshop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogUtil.i(i + "!!!!!!!!");
                LogUtil.i(defaultshopId + "-------" + data.get(i - 1).getBranch_id());
                if (!TextUtils.equals(defaultshopId, data.get(i - 1).getBranch_id())) {
                    showMYDialog(i - 1);
                }

            }
        });
    }

//    private void initData() {
//        findJoinSHOP();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        findJoinSHOP();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.donglan_right_tv:
                startActivity(new Intent(MyShopActivity.this, ShopListActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 设置切换为当前门店
     */
    private void showMYDialog(final int pos) {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(MyShopActivity.this);
        dialog.setTitle("提示");
        dialog.setMessage("是否切换为当前门店");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                changeShop(data.get(pos).getBranch_id());
                //  LogUtil.i(data.get(pos).getBranch_id());
                //listViewAdapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listViewAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();
    }

    private void showdialog_xz(final int pos) {
        final String[] items = {"切换门店", "退出门店"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
        //listDialog.setTitle("我是一个列表Dialog");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:

                        showMYDialog(pos);

                        break;
                    case 1:

                        break;
                    default:
                        break;
                }
            }
        });
        listDialog.show();
    }


    private void reloadInfo() {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.RELOAD_LOGININFO), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestLoginInfoBean loginInfoBean = gson.fromJson(s, RequestLoginInfoBean.class);
                if (loginInfoBean.getSuccess()) {
                    DataInfoCache.saveOneCache(loginInfoBean.getData(), Constants.MY_INFO);
                    userInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
                    defaultshopId = userInfo.getPerson().getDefault_branch();
                    //    ToastUtils.showToastShort("加入成功！");
//                    setName(111);
                    //    finish();
                    listViewAdapter.notifyDataSetChanged();
                    //   lv_myshop.setAdapter(listViewAdapter);
                    //   lv_myshop.setAdapter(new MyListViewAdapter());
                    EventBus.getDefault().post(new StringEvent("",20002));//刷新shopfragment
                } else {
                    ToastUtils.showToastShort("加入失败！请检查网络！");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("请求失败，请查看网络连接");
            }
        }) {


        };
        MyApplication.getHttpQueues().add(request);
    }


    private void findJoinSHOP() {
        MyStringRequest request = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.FIND_JOIN_SHOP_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                MyJionShopList shopListInfo = new MyJionShopList();
                shopListInfo = gson.fromJson(s, MyJionShopList.class);
                if (shopListInfo.getSuccess()) {
                    if (shopListInfo.getData()!=null&&shopListInfo.getData().size() > 0) {
                        data = shopListInfo.getData();
                        listViewAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showToastShort("您还没有加入任何门店");
                    }
                } else {
                    ToastUtils.showToastShort(shopListInfo.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort(volleyError.toString());
            }
        }) {
        };
        MyApplication.getHttpQueues().add(request);
    }

    private void changeShop(final String BranchId) {
        MyStringRequest stringRequest1 = new MyStringRequest(Request.Method.PUT, Constants.plus(Constants.CHANGE_CURRENT_SHOP_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestShopListInfo shopListInfo = new RequestShopListInfo();
                shopListInfo = gson.fromJson(s, RequestShopListInfo.class);
                if (shopListInfo.getSuccess()) {
//                    defaultshopId = BranchId;
//                    userInfo.getPerson().setDefault_branch(defaultshopId);
//                    DataInfoCache.saveOneCache(userInfo, Constants.MY_INFO);
                    reloadInfo();

                } else {
                    ToastUtils.showToastShort(shopListInfo.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("branch_id", BranchId);
                return hashMap;
            }

        };
        MyApplication.getHttpQueues().add(stringRequest1);
    }

    class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = View.inflate(MyShopActivity.this, R.layout.listview_item_my_shop, null);
                viewHolder.iv_shop_logo = view.findViewById(R.id.iv_shop_logo);
                viewHolder.tv_name = view.findViewById(R.id.tv_name);
                viewHolder.tv_time = view.findViewById(R.id.tv_time);

                viewHolder.tv_role = view.findViewById(R.id.tv_role);
                viewHolder.iv_default = view.findViewById(R.id.iv_default);
                viewHolder.item_layout_cv = view.findViewById(R.id.item_layout_cv);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(MyShopActivity.this, 100f));
            if (i == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(MyShopActivity.this, 16f), DensityUtils.dp2px(MyShopActivity.this, 16f), DensityUtils.dp2px(MyShopActivity.this, 16f), DensityUtils.dp2px(MyShopActivity.this, 11f));
            } else if (i == data.size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(MyShopActivity.this, 16f), DensityUtils.dp2px(MyShopActivity.this, 5f), DensityUtils.dp2px(MyShopActivity.this, 16f), DensityUtils.dp2px(MyShopActivity.this, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(MyShopActivity.this, 16f), DensityUtils.dp2px(MyShopActivity.this, 5f), DensityUtils.dp2px(MyShopActivity.this, 16f), DensityUtils.dp2px(MyShopActivity.this, 11f));
            }
            viewHolder.item_layout_cv.setLayoutParams(layoutParams);

            Glide.with(MyShopActivity.this).load(data.get(i).getLogo()).into(viewHolder.iv_shop_logo);
            viewHolder.tv_name.setText(data.get(i).getName());
            LogUtil.i("defaultshopId="+defaultshopId);
            LogUtil.i("data.get(i).getBranch_id()="+data.get(i).getBranch_id());
            if (TextUtils.equals(defaultshopId, data.get(i).getBranch_id())) {
                viewHolder.iv_default.setImageResource(R.drawable.img_current_shop);
                viewHolder.iv_default.setVisibility(View.VISIBLE);
            } else {
                viewHolder.iv_default.setVisibility(View.INVISIBLE);
            }
            String[] b = data.get(i).getCreate_time().toString().split(" ");
            viewHolder.tv_time.setText("加入时间：" + b[0].replace("-","."));
            if (data.get(i).getAuths().size() == 1) {
                String s = "我的角色：";
                if (TextUtils.equals(data.get(i).getAuths().get(0), "1")) {
                    s = s + "准会员";
                }
                if (TextUtils.equals(data.get(i).getAuths().get(0), "2")) {
                    s = s + "会员";
                }
                if (TextUtils.equals(data.get(i).getAuths().get(0), "3")) {
                    s = s + "员工";
                }
                viewHolder.tv_role.setText(s);
            }
            if (data.get(i).getAuths().size() == 2) {
                String s = "我的角色：";
                for (int j = 0; j < data.get(i).getAuths().size(); j++) {
                    if (TextUtils.equals(data.get(i).getAuths().get(j), "1")) {
                        if (j == 0) {
                            s = s + "准会员";
                        } else {
                            s = s + "/准会员";
                        }

                    }
                    if (TextUtils.equals(data.get(i).getAuths().get(j), "2")) {
                        if (j == 0) {
                            s = s + "会员";
                        } else {
                            s = s + "/会员";
                        }
                    }
                    if (TextUtils.equals(data.get(i).getAuths().get(j), "3")) {
                        if (j == 0) {
                            s = s + "员工";
                        } else {
                            s = s + "/员工";
                        }
                    }
                }
                viewHolder.tv_role.setText(s);
            }
            return view;
        }
    }

    class ViewHolder {
        RoundImageView iv_shop_logo;
        TextView tv_name;
        TextView tv_time;
        TextView tv_role;
        ImageView iv_default;
        Button btn_commit;
        RoundImageView iv_card;
        CardView item_layout_cv;
    }

}
