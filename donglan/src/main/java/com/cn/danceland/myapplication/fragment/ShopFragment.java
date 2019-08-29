package com.cn.danceland.myapplication.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.AddFriendsActivity;
import com.cn.danceland.myapplication.activity.AdviseActivity;
import com.cn.danceland.myapplication.activity.AllReportActivity;
import com.cn.danceland.myapplication.activity.BuySiJiaoActivity;
import com.cn.danceland.myapplication.activity.CabinetActivity;
import com.cn.danceland.myapplication.activity.ClubDynActivity;
import com.cn.danceland.myapplication.activity.CourseActivity;
import com.cn.danceland.myapplication.activity.FitnessTestActivity;
import com.cn.danceland.myapplication.activity.HomeActivity;
import com.cn.danceland.myapplication.activity.HuiYuanTuiJianActivty;
import com.cn.danceland.myapplication.activity.JiaoLianCourseActivity;
import com.cn.danceland.myapplication.activity.LoginNumberActivity;
import com.cn.danceland.myapplication.activity.MapActivity;
import com.cn.danceland.myapplication.activity.MyCardActivity;
import com.cn.danceland.myapplication.activity.MyContactsActivity;
import com.cn.danceland.myapplication.activity.MyDepositListActivity;
import com.cn.danceland.myapplication.activity.MyOrderActivity;
import com.cn.danceland.myapplication.activity.MySijiaoActivity;
import com.cn.danceland.myapplication.activity.MyTrainingActivity;
import com.cn.danceland.myapplication.activity.NewsDetailsActivity;
import com.cn.danceland.myapplication.activity.PotentialCustomerRevisitActivity;
import com.cn.danceland.myapplication.activity.RecommendActivity;
import com.cn.danceland.myapplication.activity.ReportFormActivity;
import com.cn.danceland.myapplication.activity.ScanerCodeActivity;
import com.cn.danceland.myapplication.activity.SearchFriendsActivity;
import com.cn.danceland.myapplication.activity.SellCardActivity;
import com.cn.danceland.myapplication.activity.ShopDetailedActivity;
import com.cn.danceland.myapplication.activity.StoreCardActivity;
import com.cn.danceland.myapplication.activity.TextPushActivity;
import com.cn.danceland.myapplication.activity.TimeTableActivity;
import com.cn.danceland.myapplication.activity.UserHomeActivity;
import com.cn.danceland.myapplication.activity.VideoListActivity;
import com.cn.danceland.myapplication.activity.YeJiZhanBanActivity;
import com.cn.danceland.myapplication.activity.YeWuActivity;
import com.cn.danceland.myapplication.bean.BranchBannerBean;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.MenusBean;
import com.cn.danceland.myapplication.bean.RequestLoginInfoBean;
import com.cn.danceland.myapplication.bean.RolesBean;
import com.cn.danceland.myapplication.bean.ShareBean;
import com.cn.danceland.myapplication.bean.ShopDetailBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.im.ui.ConversationActivity;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.UIUtils;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends BaseFragment {
    ListView storelist;
    GridView mGridView;
    String jingdu, weidu, shopJingdu, shopWeidu, branchId;
    String PhoneNo;
    Gson gson;
    Data info;
    //List<StoreBean.Items> itemsList;
    ImageButton ibtn_call, ibtn_gps;
    List<MenusBean.Data> data;
    LinearLayout ll_top;
    LinearLayout shop_layout;
    TextView tv_shopname, tv_role;
    View v;
    ArrayList<String> roleList;
    ArrayAdapter arrayAdapter;
    String role;
    List<Data.Roles> roles;
    HashMap<String, String> roleMap, authMap;
    MZBannerView shop_banner;
    ArrayList<String> drawableArrayList;
    PopupWindow popupWindow;
    LinearLayout rl_role;
    ImageView down_img, up_img;
    private String role_id;
    private TextView tv_distance_km;

    private ArrayList<BranchBannerBean.Data> backBannerList = new ArrayList<>();
    private boolean isPermission;
    private TextView tv_detail;//新增详情布局
    private TextView tv_dcs;
    private TextView tv_time;
    private String shopName;//门店名

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册event事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        if (event.getEventCode() == 20001) {
            refresh();
        }
    }

    @Override
    public View initViews() {

        v = View.inflate(mActivity, R.layout.fragment_shop, null);

        info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        LogUtil.i(info.getRoles().toString());
        roleList = new ArrayList<String>();
        shop_banner = v.findViewById(R.id.shop_banner);
        tv_role = v.findViewById(R.id.tv_role);

        shop_layout = v.findViewById(R.id.shop_layout);

        rl_role = v.findViewById(R.id.rl_role);
        down_img = v.findViewById(R.id.down_img);
        up_img = v.findViewById(R.id.up_img);
        tv_detail = v.findViewById(R.id.tv_detail);
        tv_detail.setOnClickListener(this);
        setMap();
        addRoles();
        setPop();
        rl_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    dismissPop();
                    up_img.setVisibility(View.VISIBLE);
                    down_img.setVisibility(View.GONE);
                } else {
                    showPop();
                    up_img.setVisibility(View.GONE);
                    down_img.setVisibility(View.VISIBLE);

                }
            }
        });

//        shop_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogUtil.i("shop_layout11");
//                if (popupWindow.isShowing()) {
//                    LogUtil.i("shop_layout22");
//                    dismissPop();
//                    up_img.setVisibility(View.GONE);
//                    down_img.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        //setSpinner();
        if (roleList != null && roleList.size() > 0) {
            tv_role.setText(roleList.get(0));
            role = roleList.get(0);
            SPUtils.setString("role_type", roleMap.get(role));
        }

        gson = new Gson();
        mGridView = v.findViewById(R.id.gridview);

        ibtn_call = v.findViewById(R.id.ibtn_call);
        ibtn_gps = v.findViewById(R.id.ibtn_gps);

        ll_top = v.findViewById(R.id.ll_top);
        tv_shopname = v.findViewById(R.id.tv_shopname);
        tv_time = v.findViewById(R.id.tv_time);

        tv_distance_km = v.findViewById(R.id.tv_distance_km);
        drawableArrayList = new ArrayList<>();
        mGridView.setOnItemClickListener(new MyOnItemClickListener());
        //storelist = v.findViewById(R.id.storelist);
        initData();

        v.findViewById(R.id.ibtn_call).setOnClickListener(this);
        v.findViewById(R.id.ibtn_gps).setOnClickListener(this);
        tv_shopname.setOnClickListener(this);

//        drawableArrayList.add(R.drawable.img_man);
//        drawableArrayList.add(R.drawable.img_man);
//        drawableArrayList.add(R.drawable.img_man);
        // mGridView.setVisibility(View.VISIBLE);

        return v;
    }

    private void setPop() {

        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.shop_pop, null);

        popupWindow = new PopupWindow(inflate);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失

        ListView pop_lv = inflate.findViewById(R.id.pop_lv);
        pop_lv.setAdapter(new PopAdapter());
        pop_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                role = roleList.get(position);
                SPUtils.setString("role_type", roleMap.get(role));
                tv_role.setText(role);
                initData();
                dismissPop();
                up_img.setVisibility(View.VISIBLE);
                down_img.setVisibility(View.GONE);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                up_img.setVisibility(View.VISIBLE);
                down_img.setVisibility(View.GONE);
            }
        });

    }

    private void showPop() {
        popupWindow.showAsDropDown(tv_role);
    }

    private void dismissPop() {
        popupWindow.dismiss();
    }


    private class PopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return roleList == null ? 0 : roleList.size();
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
            View inflate = LayoutInflater.from(mActivity).inflate(R.layout.shop_pop_item, null);
            TextView tv_item = inflate.findViewById(R.id.tv_item);
            tv_item.setText(roleList.get(position));

            return inflate;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        shop_banner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        shop_banner.start();
        permissions();
    }

    private void setBannner() {

        shop_banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                ShareBean shareBean=new ShareBean();
                shareBean.bus_id=backBannerList.get(i).getId()+"";
                shareBean.img_url=backBannerList.get(i).getImg_url();
                shareBean.title=backBannerList.get(i).getTitle();
                shareBean.url=backBannerList.get(i).getUrl();
                shareBean.type=12;// 门店轮播图
                startActivity(new Intent(mActivity, NewsDetailsActivity.class)
                        .putExtra("url", backBannerList.get(i).getUrl())
                        .putExtra("title", backBannerList.get(i).getTitle())
                        .putExtra("img_url",backBannerList.get(i).getImg_url())
                        .putExtra("shareBean",shareBean));
//                startActivity(new Intent(mActivity, NewsDetailsActivity.class)
//                        .putExtra("url", backBannerList.get(i).getUrl())
//                        .putExtra("title", backBannerList.get(i).getTitle())
//                        .putExtra("img_url", backBannerList.get(i).getImg_url()));


//                Intent intent = new Intent(mActivity, ShopDetailedActivity.class);
//                intent.putExtra("shopWeidu", itemsList.get(0).getLat() + "");
//                intent.putExtra("shopJingdu", itemsList.get(0).getLng() + "");
//                intent.putExtra("jingdu", jingdu);
//                intent.putExtra("weidu", weidu);
//                intent.putExtra("branchID", itemsList.get(0).getBranch_id() + "");
//                startActivity(intent);
            }
        });
        if (drawableArrayList != null && drawableArrayList.size() == 0) {
            drawableArrayList.add("http://i3.hoopchina.com.cn/blogfile/201403/31/BbsImg139626653396762_620*413.jpg");
            BranchBannerBean.Data bbb = new BranchBannerBean.Data();
            bbb.setImg_url("http://i3.hoopchina.com.cn/blogfile/201403/31/BbsImg139626653396762_620*413.jpg");
            backBannerList.add(bbb);
//        drawableArrayList.add(R.drawable.img_man);
//        drawableArrayList.add(R.drawable.img_man);
        }
        // 设置数据
        shop_banner.setPages(drawableArrayList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        shop_banner.setIndicatorVisible(false);
        shop_banner.start();

    }

    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item1, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定


            Glide.with(context).load(data)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            mImageView.setBackground(resource);
                        }
                    });


        }
    }

    private void setMap() {
        roleMap = new HashMap<>();
        authMap = new HashMap<>();
        if (info != null) {
            roles = info.getRoles();
            if (roles != null && roles.size() > 0) {
                for (int i = 0; i < roles.size(); i++) {
                    roleMap.put(roles.get(i).getName(),roles.get(i).getRole_type()+"");

                }
            }
        }

//
//        roleMap.put("会籍顾问", "1");
//        roleMap.put("教练", "2");
//        roleMap.put("前台", "3");
//        roleMap.put("店长", "4");
//        roleMap.put("会籍主管", "5");
//        roleMap.put("教练主管", "6");
//        roleMap.put("前台主管", "7");
//        roleMap.put("操教", "8");
//        roleMap.put("出纳", "9");
//        roleMap.put("收银", "10");
//        roleMap.put("兼职教练", "11");
        authMap.put("准会员", "1");
        authMap.put("会员", "2");

    }

    public void refresh() {
        if (myAdapter != null) {
//            TranslateAnimation animation = new TranslateAnimation(0, -5, 0, 0);
//            animation.setInterpolator(new OvershootInterpolator());
//            animation.setDuration(100);
//            animation.setRepeatCount(3);
//            animation.setRepeatMode(Animation.REVERSE);
//            mGridView.startAnimation(animation);
//            myAdapter.notifyDataSetChanged();
            //     LogUtil.i("刷新");
        }

    }

    private void addRoles() {
        if (info != null) {
            roles = info.getRoles();
            if (roles != null && roles.size() > 0) {
                for (int i = 0; i < roles.size(); i++) {
                    roleList.add(roles.get(i).getName());
//                    if (roles.get(i).getRole_type() == 1) {
//                        roleList.add("会籍顾问");
//                    } else if (roles.get(i).getRole_type() == 2) {
//                        roleList.add("教练");
//                    } else if (roles.get(i).getRole_type() == 3) {
//                        roleList.add("前台");
//                    } else if (roles.get(i).getRole_type() == 4) {
//                        roleList.add("店长");
//                    } else if (roles.get(i).getRole_type() == 5) {
//                        roleList.add("会籍主管");
//                    } else if (roles.get(i).getRole_type() == 6) {
//                        roleList.add("教练主管");
//                    } else if (roles.get(i).getRole_type() == 7) {
//                        roleList.add("前台主管");
//                    } else if (roles.get(i).getRole_type() == 8) {
//                        roleList.add("操教");
//                    } else if (roles.get(i).getRole_type() == 9) {
//                        roleList.add("出纳");
//                    } else if (roles.get(i).getRole_type() == 10) {
//                        roleList.add("收银");
//                    } else if (roles.get(i).getRole_type() == 11) {
//                        roleList.add("兼职教练");
//                    }
                }
            }

            if (info.getMember() != null) {
                if ("1".equals(info.getMember().getAuth())) {
                    roleList.add("准会员");
                } else if ("2".equals(info.getMember().getAuth())) {
                    roleList.add("会员");
                }
            }
        }

    }

    public void initData() {
        branchId = info.getPerson().getDefault_branch();
        if (info.getPerson().getDefault_branch() != null && !info.getPerson().getDefault_branch().equals("")) {
            getMenus();
            getBanner(info.getPerson().getDefault_branch());
            getShop(info.getPerson().getDefault_branch());
        }
    }

    private void getBanner(final String branchId) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.BANNER), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                drawableArrayList.clear();
                backBannerList.clear();
                BranchBannerBean branchBannerBean = gson.fromJson(s, BranchBannerBean.class);
                if (branchBannerBean != null) {
                    List<BranchBannerBean.Data> data = branchBannerBean.getData();
                    if (data != null) {
                        for (int i = 0; i < data.size(); i++) {
                            drawableArrayList.add(data.get(i).getImg_url());
                            backBannerList.add(data.get(i));
                        }
                        setBannner();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("branchId", branchId);
                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限

    private void getShop(String shopID) {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.BRANCH) + "/" + shopID, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ShopDetailBean shopDetailBean = gson.fromJson(s, ShopDetailBean.class);
                ShopDetailBean.DataBean data = shopDetailBean.getData();
                if (data != null) {
                    tv_shopname.setText(data.getName());
                    shopName = data.getName();
                    shopWeidu = data.getLat() + "";
                    shopJingdu = data.getLng() + "";
                    PhoneNo = data.getTelphone();
                    String open_time;
                    String colse_time;
                    if (Integer.valueOf(data.getOpen_time()) % 60 == 0) {
                        open_time = Integer.valueOf(data.getOpen_time()) / 60 + ":00";
                    } else {
                        open_time = Integer.valueOf(data.getOpen_time()) / 60 + ":" + Integer.valueOf(data.getOpen_time()) % 60;
                    }
                    if (Integer.valueOf(data.getClose_time()) % 60 == 0) {
                        colse_time = Integer.valueOf(data.getClose_time()) / 60 + ":00";
                    } else {
                        colse_time = Integer.valueOf(data.getClose_time()) / 60 + ":" + Integer.valueOf(data.getClose_time()) % 60;
                    }


                    tv_time.setText(open_time + "-" + colse_time);

                    tv_detail.setVisibility(View.VISIBLE);
                    if (isPermission) {
                        LogUtil.i("data.getLat()" + data.getLat());
                        LogUtil.i("data.getLng()" + data.getLng());
                        LogUtil.i("getLngAndLat(mActivity).getLat()" + getLngAndLat(mActivity).getLat());
                        LogUtil.i("getLngAndLat(mActivity).getLng()" + getLngAndLat(mActivity).getLng());
                        double distanceKm = UIUtils.getDistance(data.getLat(), data.getLng(), getLngAndLat(mActivity).getLat(), getLngAndLat(mActivity).getLng());
                        double dis = distanceKm / 1000;
                        NumberFormat nf = new DecimalFormat("##.#");
                        String str = nf.format(dis);
                        tv_distance_km.setText("距我" + str + "km");
                        tv_distance_km.setVisibility(View.VISIBLE);
                    } else {
                        tv_distance_km.setVisibility(View.GONE);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {

        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    MyAdapter myAdapter;

    private void getMenus() {
        final String id;
        String url;
        RolesBean rolesBean = new RolesBean();
        if (role != null) {
            if (!"准会员".equals(role) && !"会员".equals(role)) {


                id = roleMap.get(role);
                rolesBean.setRole_type(roleMap.get(role));
                //id = roleMap.get(role);

                role_id = null;
                for (int i = 0; i < info.getRoles().size(); i++) {
                    LogUtil.i(id + "----" + info.getRoles().get(i).getRole_type() + "");
                    if (TextUtils.equals(role, info.getRoles().get(i).getName() + "")) {
                        rolesBean.setId(info.getRoles().get(i).getId());
                    }
                }


                url = Constants.plus(Constants.GETYUANGONGMENUS);
                String s = gson.toJson(rolesBean);
                LogUtil.i(s);
                LogUtil.i(roleMap.get(role));

                SPUtils.setInt(Constants.ROLE_ID, Integer.valueOf(roleMap.get(role)));//保存当前使用角色

                MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, url, s, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        LogUtil.i(jsonObject.toString());
                        if (jsonObject != null) {
                            if (jsonObject.toString().contains("true")) {

                                MenusBean menusBean = gson.fromJson(jsonObject.toString(), MenusBean.class);
                                data = menusBean.getData();
                                if (data != null) {
                                    LogUtil.i(data.toString());
                                    myAdapter = new MyAdapter(data);
                                    mGridView.setAdapter(myAdapter);
                                }
                            } else {
                                ToastUtils.showToastShort("请查看网络连接");
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        ToastUtils.showToastShort("请查看网络连接");
                        LogUtil.e("zzf", volleyError.toString());
                    }
                });
                MyApplication.getHttpQueues().add(jsonObjectRequest);
            } else {

                id = authMap.get(role);
                url = Constants.plus(Constants.GETHUIYUANMENUS);
                if (Integer.valueOf(id) == 1) {
                    SPUtils.setInt(Constants.ROLE_ID, Constants.ROLE_ID_QIANKE);
                } else {
                    SPUtils.setInt(Constants.ROLE_ID, Constants.ROLE_ID_HUIYUAN);
                }

                MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        LogUtil.e("zzf", s);
                        if (s.contains("true")) {
                            MenusBean menusBean = gson.fromJson(s, MenusBean.class);
                            data = menusBean.getData();
                            if (data != null) {
                                LogUtil.i(data.toString());
                                myAdapter = new MyAdapter(data);
                                mGridView.setAdapter(myAdapter);
                            }
                        } else {
                            ToastUtils.showToastShort("请查看网络连接");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // ToastUtils.showToastShort("请查看网络连接");
                        LogUtil.i(volleyError.toString());
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("role_type", id);
                        //  map.put("id", role_id);
                        LogUtil.i(map.toString());
                        return map;
                    }


                };
                MyApplication.getHttpQueues().add(stringRequest);
            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        String getlocationString = ((HomeActivity) activity).getlocationString();
        if (getlocationString != null) {
            jingdu = getlocationString.split(",")[0];
            weidu = getlocationString.split(",")[1];
        }
    }

//    public void getListData() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.BRANCH + "/0/" + weidu + "/" + jingdu, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                StoreBean storeBean = gson.fromJson(s, StoreBean.class);
//                if (storeBean != null && storeBean.getData() != null) {
//                    itemsList = storeBean.getData().getItems();
//                    if (itemsList != null && itemsList.size() > 0) {
//                        storelist.setAdapter(new MyStoreAdapter(getActivity(), itemsList));
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                LogUtil.e("zzf", volleyError.toString());
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("Authorization", SPUtils.getString(Constants.MY_TOKEN, ""));
//                return map;
//            }
//        };
//
//        MyApplication.getHttpQueues().add(stringRequest);
//
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
            roleList.clear();
            role = SPUtils.getString("role", "");
            tv_role.setText(role);
            setMap();
            addRoles();
            setPop();
            //setSpinner();
            initData();


        } else {
            SPUtils.setString("role", role);
            dismissPop();
            up_img.setVisibility(View.VISIBLE);
            down_img.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_call:
                if (PermissionsUtil.hasPermission(mActivity, Manifest.permission.CALL_PHONE)) {
                    //有权限
                    showDialog(PhoneNo);
                } else {
                    PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            showDialog(PhoneNo);
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            //用户拒绝了申请
                            ToastUtils.showToastShort("没有权限");
                        }
                    }, new String[]{Manifest.permission.CALL_PHONE}, false, null);
                }

                break;
            case R.id.ibtn_gps:
                Intent intent = new Intent(getActivity(), MapActivity.class);
                intent.putExtra("jingdu", jingdu);
                intent.putExtra("weidu", weidu);
                intent.putExtra("shopJingdu", shopJingdu);
                intent.putExtra("shopWeidu", shopWeidu);
                intent.putExtra("shopname", tv_shopname.getText().toString());
                startActivity(intent);

                break;
            case R.id.tv_shopname:
                Intent intent1 = new Intent(getActivity(), ShopDetailedActivity.class);
                intent1.putExtra("jingdu", jingdu);
                intent1.putExtra("weidu", weidu);
                intent1.putExtra("shopJingdu", shopJingdu);
                intent1.putExtra("shopWeidu", shopWeidu);
                intent1.putExtra("branchID", branchId);
                Bundle b = new Bundle();
                b.putSerializable("backBannerList", backBannerList);
                intent1.putExtras(b);
                startActivity(intent1);
                break;
            case R.id.tv_detail:
                Intent intent2 = new Intent(getActivity(), ShopDetailedActivity.class);
                intent2.putExtra("jingdu", jingdu);
                intent2.putExtra("weidu", weidu);
                intent2.putExtra("shopJingdu", shopJingdu);
                intent2.putExtra("shopWeidu", shopWeidu);
                intent2.putExtra("branchID", branchId);
                Bundle c = new Bundle();
                c.putSerializable("backBannerList", backBannerList);
                intent2.putExtras(c);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    /**
     * 提示
     */
    private void showDialog(final String phoneNo) {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(mActivity);
        dialog.setTitle("提示");
        dialog.setMessage("是否呼叫" + phoneNo);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                call(phoneNo);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (data != null) {
                switch (data.get(i).getId()) {
                    case 1://在线售卡
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "在线售卡");
                        startActivity(new Intent(mActivity, SellCardActivity.class));
                        break;
                    case 2://我的会员卡
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "我的会员卡");
                        startActivity(new Intent(mActivity, MyCardActivity.class));
                        break;
                    case 3://健身圈
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "健身圈");
                        startActivity(new Intent(mActivity, UserHomeActivity.class).putExtra("id", SPUtils.getString(Constants.MY_USERID, null)).putExtra("isdyn", true));
                        break;
                    case 4://购买私教
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "购买私教");
                        startActivity(new Intent(mActivity, BuySiJiaoActivity.class));
                        break;
                    case 5://会所动态
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "会所动态");
                        startActivity(new Intent(mActivity, ClubDynActivity.class));
                        break;
                    case 6://预约私教
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "预约私教");
                        Intent intent1 = new Intent(mActivity, CourseActivity.class);
                        intent1.putExtra("isTuanke", "1");
                        if (role != null && !role.equals("准会员") && !role.equals("会员")) {
                            intent1.putExtra("role", role);
                        } else {
                            intent1.putExtra("auth", role);
                        }
                        startActivity(intent1);
                        break;
                    case 9://意见反馈
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "意见反馈");
                        //startActivity(new Intent(mActivity, LoginNumberActivity.class));
                        startActivity(new Intent(mActivity, AdviseActivity.class));
                        break;
                    case 10://我的定金
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "我的定金");
                        startActivity(new Intent(mActivity, MyDepositListActivity.class));
                        break;
                    case 11://我的订单
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "我的订单");
                        startActivity(new Intent(mActivity, MyOrderActivity.class));
                        break;
                    case 12://客户体测
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "体侧");
                        Intent intent = new Intent(mActivity, AddFriendsActivity.class);
                        intent.putExtra("from", "体测");
                        startActivity(intent);
                        break;
                    case 13://潜客维护
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "潜客维护");

                        startActivity(new Intent(mActivity, PotentialCustomerRevisitActivity.class).putExtra("auth", "1"));
                        break;
                    case 14://会员维护
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "会员维护");
                        startActivity(new Intent(mActivity, PotentialCustomerRevisitActivity.class).putExtra("auth", "2"));

                        //  startActivity(new Intent(mActivity, MyOrderActivity.class));
                        break;
                    case 15://待办事项
                        //    startActivity(new Intent(mActivity, MyOrderActivity.class));
                        break;
                    case 16://我的租柜
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "我的租柜");
                        startActivity(new Intent(mActivity, CabinetActivity.class));
                        break;
                    case 25://推荐好友
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "推荐好友");
                        startActivity(new Intent(mActivity, RecommendActivity.class));
                        break;
                    case 17://会员推荐
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "会员推荐");
                        startActivity(new Intent(mActivity, HuiYuanTuiJianActivty.class));
                        break;
                    case 18://意见反馈
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "意见反馈");
                        startActivity(new Intent(mActivity, AdviseActivity.class));
                        break;
                    case 19://扫码入场
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "扫码入场");
                        if (PermissionsUtil.hasPermission(mActivity, Manifest.permission.CAMERA)) {
                            //有权限
                            startActivity(new Intent(mActivity, ScanerCodeActivity.class).putExtra("from", "entrance"));
                        } else {
                            PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                                @Override
                                public void permissionGranted(@NonNull String[] permissions) {
                                    //用户授予了权限
                                    startActivity(new Intent(mActivity, ScanerCodeActivity.class).putExtra("from", "entrance"));
                                }

                                @Override
                                public void permissionDenied(@NonNull String[] permissions) {
                                    //用户拒绝了申请
                                    ToastUtils.showToastShort("没有权限");
                                }
                            }, new String[]{Manifest.permission.CAMERA}, false, null);
                        }
                        break;
                    case 20://预约团课
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "预约团课");
                        startActivity(new Intent(mActivity, CourseActivity.class)
                                .putExtra("isTuanke", "0")
                        .putExtra("flag",2));
                        break;
                    case 21://储值卡
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "储值卡");
                        startActivity(new Intent(mActivity, StoreCardActivity.class));
                        break;
                    case 22://我要培训
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "我要培训");
                        startActivity(new Intent(mActivity, MyTrainingActivity.class));
//                        startActivity(new Intent(mActivity, NewsDetailsActivity.class)
//                                .putExtra("url", "http://jiaolian.danceland.com.cn/")
//                                .putExtra("title", "动岚健身学院"));
                             //   .putExtra("img_url", backBannerList.get(i).getImg_url()));
                        break;
                    case 23://会籍报表
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "会籍报表");
                        startActivity(new Intent(mActivity, ReportFormActivity.class).putExtra("role_type", role).putExtra("target_role_type", "1"));
                        break;
                    case 24://全店报表
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "全店报表");
                        startActivity(new Intent(mActivity, AllReportActivity.class).putExtra("role_type", role).putExtra("target_role_type", "4"));
                        break;
                    case 26://体测分析
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "体侧分析");
                        startActivity(new Intent(mActivity, SearchFriendsActivity.class).putExtra("from", "体测").putExtra("isAnalysis", "true"));
                        break;
                    case 28://服务报表
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "服务报表");
                        startActivity(new Intent(mActivity, ReportFormActivity.class).putExtra("role_type", role).putExtra("target_role_type", "3"));
                        break;
                    case 29://私信 我的消息
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "私信");
                        startActivity(new Intent(mActivity, ConversationActivity.class));
                        //  startActivity(new Intent(mActivity, TXIMHomeActivity.class));
                        //  ToastUtils.showToastShort("功能正在开发中");
                        break;
                    case 30://教练报表
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "教练报表");
                        startActivity(new Intent(mActivity, ReportFormActivity.class).putExtra("role_type", role).putExtra("target_role_type", "2"));
                        break;
                    case 31://我的私教
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "我的私教");
                        startActivity(new Intent(mActivity, MySijiaoActivity.class));
                        break;
                    case 33://预约会员
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "预约会员");
                        Intent intent2 = new Intent(mActivity, JiaoLianCourseActivity.class);
                        intent2.putExtra("isTuanke", "1");
                        if (role != null && !role.equals("准会员") && !role.equals("会员")) {
                            intent2.putExtra("role", role);
                        } else {
                            intent2.putExtra("auth", role);
                        }
                        startActivity(intent2);
                        break;
                    case 34://我的体测
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "我的体侧");
                        startActivity(new Intent(mActivity, FitnessTestActivity.class));
                        break;
                    case 35://动态码
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "动态码");
                      startActivity(new Intent(mActivity, LoginNumberActivity.class));
                   //     startActivity(new Intent(mActivity, VideoListActivity.class));
                        break;
                    case 36://联系人
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "联系人");
                        startActivity(new Intent(mActivity, MyContactsActivity.class));
                        break;
                    case 37://会员业务
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "会员业务");
                        startActivity(new Intent(mActivity, YeWuActivity.class).putExtra("auth", "2"));
                        break;
                    case 38://扫码训练
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "扫码训练");
                        if (PermissionsUtil.hasPermission(mActivity, Manifest.permission.CAMERA)) {
                            //有权限
                            startActivity(new Intent(mActivity, ScanerCodeActivity.class).putExtra("from", "train"));
                        } else {
                            PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                                @Override
                                public void permissionGranted(@NonNull String[] permissions) {
                                    //用户授予了权限
                                    startActivity(new Intent(mActivity, ScanerCodeActivity.class).putExtra("from", "train"));
                                }

                                @Override
                                public void permissionDenied(@NonNull String[] permissions) {
                                    //用户拒绝了申请
                                    ToastUtils.showToastShort("没有权限");
                                }
                            }, new String[]{Manifest.permission.CAMERA}, false, null);
                        }
                        break;
                    case 41://业绩展板
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "业绩展板");
                        //教练或教练主管
                        if (SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIAN || SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIANZHUGUAN) {
                            startActivity(new Intent(mActivity, YeJiZhanBanActivity.class).putExtra("isjiaolian", true));
                        } else {
                            startActivity(new Intent(mActivity, YeJiZhanBanActivity.class));
                        }


                        break;
                    case 42://会籍推送
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "会籍推送");
                        //推送范围(100:店长推送;101:会籍推送;102:教练推送;103:服务部推送)
                        startActivity(new Intent(mActivity, TextPushActivity.class).putExtra("from", 101));
                        break;
                    case 43://教练推送
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "教练推送");
                        //推送范围(100:店长推送;101:会籍推送;102:教练推送;103:服务部推送)
                        startActivity(new Intent(mActivity, TextPushActivity.class).putExtra("from", 102));
                        break;
                    case 44://全店推送
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "全店推送");
                        //推送范围(100:店长推送;101:会籍推送;102:教练推送;103:服务部推送)
                        startActivity(new Intent(mActivity, TextPushActivity.class).putExtra("from", 100));
                        break;
                    case 45://服务部推送
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "服务部推送");
                        //推送范围(100:店长推送;101:会籍推送;102:教练推送;103:服务部推送)
                        startActivity(new Intent(mActivity, TextPushActivity.class).putExtra("from", 103));
                        break;
                    case 46://门店课表
                        MobclickAgent.onEvent(mActivity, "shop_list_btn", "门店课表");
                        startActivity(new Intent(mActivity, TimeTableActivity.class).putExtra("shopName", shopName));
                        break;
                    default:
                        ToastUtils.showToastShort("该功能正在研发中");
                        break;
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 111) {

            initViews();
        }

    }

    private void join(final String shopID) {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.PUT, Constants.plus(Constants.JOINBRANCH), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.contains("true")) {
                    reloadInfo();
                    //  login(shopID);
                } else {
                    ToastUtils.showToastShort("加入失败！请检查网络！");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("branchId", shopID);
                map.put("join", "true");
                return map;
            }


        };

        MyApplication.getHttpQueues().add(stringRequest);

    }

    class ViewHolder {
        ImageView store_item_img, img_location, img_phone, img_join;
        TextView store_address, distance, unread_msg_number;
        RelativeLayout clickitem;
    }


    class MyAdapter extends BaseAdapter {

        List<MenusBean.Data> menuList;


        MyAdapter(List<MenusBean.Data> list) {
            menuList = list;
        }

//        public void setAnim(){
//
//        }

        @Override
        public int getCount() {
            return menuList.size();
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

            view = View.inflate(mActivity, R.layout.gridview_item_shop, null);
            TextView tv_dcs = view.findViewById(R.id.tv_dcs);
            ImageView ibtn = view.findViewById(R.id.ibtn);
            RelativeLayout rl_item = view.findViewById(R.id.rl_item);

            TextView unread_msg_number = view.findViewById(R.id.unread_msg_number);
            if (menuList.get(i).getId() == 29) {

                long TotalUnreadNum = ((HomeActivity) mActivity).getTotalUnreadNum();
                if (TotalUnreadNum == 0) {
                    unread_msg_number.setVisibility(View.GONE);
                } else {
                    unread_msg_number.setVisibility(View.VISIBLE);
                    if (TotalUnreadNum > 99) {
                        unread_msg_number.setText("99+");
                    } else {
                        unread_msg_number.setText(TotalUnreadNum + "");
                    }

                }
            }

            //由小变大
            Animation scaleAnim = new ScaleAnimation(0.9f, 1.1f, 0.9f, 1.1f);
            //从左向右
            Animation rotateAnim = new RotateAnimation(-5, 5, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

            scaleAnim.setDuration(300);
            rotateAnim.setDuration(300 / 5);
            rotateAnim.setRepeatMode(Animation.REVERSE);
            rotateAnim.setRepeatCount(5);

            AnimationSet smallAnimationSet = new AnimationSet(false);
            smallAnimationSet.addAnimation(scaleAnim);
            smallAnimationSet.addAnimation(rotateAnim);


//            TranslateAnimation animation = new TranslateAnimation(0, -10, 0, 0);
//            animation.setInterpolator(new OvershootInterpolator());
//            animation.setDuration(150);
//            animation.setRepeatCount(3);
//            animation.setInterpolator(new AccelerateDecelerateInterpolator());
//            animation.setRepeatMode(Animation.REVERSE);

//            rl_item.startAnimation(smallAnimationSet);
//            tv_dcs.startAnimation(smallAnimationSet);


            tv_dcs.setText(menuList.get(i).getName());
            Glide.with(mActivity).load(menuList.get(i).getIcon()).into(ibtn);
            return view;
        }
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
                    info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
                    setMap();
                    addRoles();
                    setPop();
                    //setSpinner();
                    initData();
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

    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    private ShopDetailBean.DataBean getLngAndLat(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork(context);
            }
        } else {//从网络获取经纬度
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        ShopDetailBean.DataBean sdbean = new ShopDetailBean.DataBean();
        sdbean.setLat(latitude);
        sdbean.setLng(longitude);
        return sdbean;
    }

    //从网络获取经纬度
    public ShopDetailBean.DataBean getLngAndLatWithNetwork(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        ShopDetailBean.DataBean sdbean = new ShopDetailBean.DataBean();
        sdbean.setLat(latitude);
        sdbean.setLng(longitude);
        return sdbean;
    }

    LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
        }
    };

    public void permissions() {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "未授予位置和文件权限，应用将无法使用", "不了，谢谢", "打开权限");
        if (PermissionsUtil.hasPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION)) {
            //有权限
            isPermission = true;
        } else {
            PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    //用户授予了权限
                    isPermission = true;
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    //用户拒绝了申请
                    isPermission = false;

                }
            }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION}, true, tip);
        }

    }

}
