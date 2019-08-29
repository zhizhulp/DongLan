package com.cn.danceland.myapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.BranchBannerBean;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestLoginInfoBean;
import com.cn.danceland.myapplication.bean.ShareBean;
import com.cn.danceland.myapplication.bean.ShareInfoFromServiceBean;
import com.cn.danceland.myapplication.bean.ShopDetailBean;
import com.cn.danceland.myapplication.bean.ShopJiaoLianBean;
import com.cn.danceland.myapplication.bean.ShopPictrueBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ShareUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.CommitButton;
import com.cn.danceland.myapplication.view.NoScrollGridView;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feng on 2017/11/29.
 */

public class ShopDetailedActivity extends BaseActivity {
    Button join_button;
    ImageView bt_back;
    RequestQueue requestQueue;
    Gson gson;
    TextView tv_adress, tv_time, store_name;
    TextView tv_detail;
    String phoneNo;
    ImageView detail_phone, detail_adress, img_kechenganpai;
    String jingdu, weidu, shopJingdu, shopWeidu, branchID, myBranchId;
    CommitButton s_button;
    Data myInfo;
    //    ExpandableListView jiaolian_grid, huiji_grid;
    ImageView down_img, up_img;
    ArrayList<String> imgList = new ArrayList<>();
    ArrayList<BranchBannerBean.Data> backBannerList = new ArrayList<>();//回传过来的bannerlist
    MZBannerView shop_banner;
    private String shopname;
    private TextView tv_shopAddress;
    private NoScrollGridView gv_huiji;
    private NoScrollGridView gv_jiaolian;
    private NoScrollGridView gv_shop_image;
    private TextView tv_more_huiji;
    private TextView tv_more_jiaolian;
    private List<ShopJiaoLianBean.Data> jiaolianList;

    private ImageView status_bar_iv;
    private TextView donglan_title;//新增详情布局
    private ScrollView sc_view;
    private RelativeLayout titleView;
    private String shopName="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopdetailed);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initHost();
        initView();
    }

    LatLng startLng;

    private void initHost() {

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        myInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        gson = new Gson();
        jingdu = getIntent().getStringExtra("jingdu");
        weidu = getIntent().getStringExtra("weidu");
        startLng = new LatLng(Double.valueOf(weidu), Double.valueOf(jingdu));

        shopJingdu = getIntent().getStringExtra("shopJingdu");
        shopWeidu = getIntent().getStringExtra("shopWeidu");
        branchID = getIntent().getStringExtra("branchID");
        LogUtil.i("门店id"+branchID);
//        imgList = getIntent().getStringArrayListExtra("imgList");
//        LogUtil.i(imgList.toString());
//        backBannerList = (ArrayList<BranchBannerBean.Data>) getIntent().getSerializableExtra("backBannerList");
////        if (backBannerList != null && backBannerList.size() > 0) {
////            for (int i = 0; i < backBannerList.size(); i++) {
////                imgList.add(backBannerList.get(i).getImg_url());
////            }
////        }
        //myBranchId = myInfo.getPerson().getDefault_branch();
        isJoinBranch(branchID);

    }

    private boolean isjion = false;

    private void ShareInfo() {//分享

        final ShareInfoFromServiceBean strbean = new ShareInfoFromServiceBean();
        strbean.share_type = "7";//门店详情
        strbean.bus_id=branchID;
        ShareUtils.create(this).shareWebInfoFromService(strbean);
    }
    private void isJoinBranch(final String branchId) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.ISJOINBRANCH), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);


                if (s.contains("1")) {
                    LogUtil.i(s);
                    s_button.setVisibility(View.GONE);
                    isjion = true;
                } else {
                    isjion = false;

                    s_button.setVisibility(View.VISIBLE);
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
                map.put("branchId", branchId);

                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void initView() {
ImageView iv_more=findViewById(R.id.iv_more);
        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInfo();
            }
        });
        shop_banner = findViewById(R.id.shop_banner);
        img_kechenganpai = findViewById(R.id.img_kechenganpai);
        img_kechenganpai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isjion == true) {
                    startActivity(new Intent(ShopDetailedActivity.this, TimeTableActivity.class).putExtra("shopName",shopName).putExtra("branchId",branchID));
                } else {
                    ToastUtils.showToastShort("请先加入门店");
                }

            }
        });

        gv_huiji = findViewById(R.id.gv_huiji);
        gv_shop_image = findViewById(R.id.gv_shop_image);
        gv_jiaolian = findViewById(R.id.gv_jiaolian);


            Glide.with(ShopDetailedActivity.this).load("http://img.dljsgw.com/app_dir/default_course.png").into(img_kechenganpai);



        tv_adress = findViewById(R.id.tv_adress);
        tv_time = findViewById(R.id.tv_time);
        tv_detail = findViewById(R.id.tv_detail);
        store_name = findViewById(R.id.store_name);
        tv_shopAddress = findViewById(R.id.tv_shopAddress);

        detail_phone = findViewById(R.id.detail_phone);
        detail_adress = findViewById(R.id.detail_adress);
        tv_more_jiaolian = findViewById(R.id.tv_more_jiaolian);
        tv_more_jiaolian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("huijiList", (Serializable) jiaolianList);
                bundle.putString("title", "会籍团队");
                startActivity(new Intent(ShopDetailedActivity.this, EmployeeListActivity.class).putExtras(bundle));
            }
        });
        tv_more_huiji = findViewById(R.id.tv_more_huiji);
        tv_more_huiji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("huijiList", (Serializable) huijiList);
                bundle.putString("title", "会籍团队");
                startActivity(new Intent(ShopDetailedActivity.this, EmployeeListActivity.class).putExtras(bundle));
            }
        });
        s_button = findViewById(R.id.dlbtn_commit);

//        join_button = findViewById(R.id.join_button);

        bt_back = findViewById(R.id.iv_back);
        status_bar_iv = findViewById(R.id.status_bar_iv);
        donglan_title = findViewById(R.id.donglan_title);
        titleView = findViewById(R.id.title);
        sc_view = findViewById(R.id.sc_view);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        detail_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionsUtil.hasPermission(ShopDetailedActivity.this, Manifest.permission.CALL_PHONE)) {
                    //有权限
                    showDialog();
                } else {
                    PermissionsUtil.requestPermission(ShopDetailedActivity.this, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            showDialog();
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            //用户拒绝了申请
                            ToastUtils.showToastShort("没有权限");
                        }
                    }, new String[]{Manifest.permission.CALL_PHONE}, false, null);
                }

            }
        });
        detail_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("shopJingdu", shopJingdu);
                intent.putExtra("shopWeidu", shopWeidu);
                intent.putExtra("jingdu", jingdu);
                intent.putExtra("weidu", weidu);
                intent.putExtra("shopname", shopname);
                startActivity(intent);
            }
        });
//        s_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),SellCardActivity.class);
//                startActivity(intent);
//            }
//        });

        s_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog =
                        new AlertDialog.Builder(ShopDetailedActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否加入此门店");
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        join(branchID);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
        sc_view.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LogUtil.i("scrollX=" + scrollX + ",scrollY=" + scrollY + ",oldScrollX=" + oldScrollX + ",oldScrollY=" + oldScrollY);
                setFoldView(oldScrollY);
            }
        });
       // setBannner();
        getShopDetail();
        getShopPictrue();
        getJiaolian(branchID);
        getHuiJi(branchID);
        getBanner(branchID);
    }

    /**
     * 本Activity透明度刷新有问题，所以如下这么写
     */
    public void setFoldView(int offsetNum) {
        int headerMaxOffset = DensityUtils.dp2px(ShopDetailedActivity.this, 130f);//header 最大偏移   220-24-44   banner-top-title-半个title（提前变换）
         LogUtil.i("headerMaxOffset="+headerMaxOffset);
        if (offsetNum <= headerMaxOffset) {
            status_bar_iv.setBackgroundColor(getResources().getColor(R.color.colorGray27));
            donglan_title.setTextColor(getResources().getColor(R.color.white));
            bt_back.setImageDrawable(getResources().getDrawable(R.drawable.img_white_back));
            titleView.setBackgroundColor(getResources().getColor(R.color.white_color80));
        } else {
            status_bar_iv.setBackgroundColor(getResources().getColor(R.color.colorGray0));
            donglan_title.setTextColor(getResources().getColor(R.color.colorGray21));
            bt_back.setImageDrawable(getResources().getDrawable(R.drawable.img_back));
            titleView.setBackgroundColor(getResources().getColor(R.color.white));
        }
//        if (0 <= offsetNum && offsetNum <= headerMaxOffset) {
//            setMeunCradview(offsetNum);
//        } else {
//            titleView.setBackgroundColor(getResources().getColor(R.color.white));
//            status_bar_iv.setBackgroundColor(getResources().getColor(R.color.colorGray27));
//        }
//        LogUtil.i("总偏移量-----(" + offsetNum);
    }

    private void setMeunCradview(int offsetNum) {
        if (0 <= offsetNum && offsetNum < 25) {
            titleView.setBackgroundColor(getResources().getColor(R.color.white_color80));
        } else if (24 <= offsetNum && offsetNum < 50) {
            titleView.setBackgroundColor(getResources().getColor(R.color.white_color80));
        } else if (50 <= offsetNum && offsetNum < 75) {
            titleView.setBackgroundColor(getResources().getColor(R.color.white_color70));
        } else if (75 <= offsetNum && offsetNum < 100) {
            titleView.setBackgroundColor(getResources().getColor(R.color.white_color60));
        } else if (100 <= offsetNum && offsetNum < 125) {
            titleView.setBackgroundColor(getResources().getColor(R.color.white_color50));
        } else if (125 <= offsetNum && offsetNum < 150) {
            titleView.setBackgroundColor(getResources().getColor(R.color.white_color40));
        } else if (150 <= offsetNum && offsetNum < 175) {
            titleView.setBackgroundColor(getResources().getColor(R.color.white_color30));
        } else if (175 <= offsetNum && offsetNum < 200) {
            titleView.setBackgroundColor(getResources().getColor(R.color.white_color20));
        } else {
            titleView.setBackgroundColor(getResources().getColor(R.color.white));
        }
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
                shareBean.type= 12;// 门店轮播图
                startActivity(new Intent(ShopDetailedActivity.this, NewsDetailsActivity.class)
                        .putExtra("url", backBannerList.get(i).getUrl())
                        .putExtra("title", backBannerList.get(i).getTitle())
                .putExtra("img_url",backBannerList.get(i).getImg_url())
                .putExtra("shareBean",shareBean));

//                Intent intent = new Intent(mActivity, ShopDetailedActivity.class);
//                intent.putExtra("shopWeidu", itemsList.get(0).getLat() + "");
//                intent.putExtra("shopJingdu", itemsList.get(0).getLng() + "");
//                intent.putExtra("jingdu", jingdu);
//                intent.putExtra("weidu", weidu);
//                intent.putExtra("branchID", itemsList.get(0).getBranch_id() + "");
//                startActivity(intent);
            }
        });
        if (imgList != null && imgList.size() == 0) {
            imgList.add("http://i3.hoopchina.com.cn/blogfile/201403/31/BbsImg139626653396762_620*413.jpg");
            BranchBannerBean.Data bbb = new BranchBannerBean.Data();
            bbb.setImg_url("http://i3.hoopchina.com.cn/blogfile/201403/31/BbsImg139626653396762_620*413.jpg");
            backBannerList.add(bbb);
//        drawableArrayList.add(R.drawable.img_man);
//        drawableArrayList.add(R.drawable.img_man);
        }


        // 设置数据
        shop_banner.setPages(imgList, new MZHolderCreator<BannerViewHolder>() {
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
            Glide.with(context).load(data)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            mImageView.setBackground(resource);
                        }
                    });
        }
    }

    private void join(final String shopID) {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.PUT, Constants.plus(Constants.JOINBRANCH), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.contains("true")) {
                    reloadInfo();
                } else {
                    ToastUtils.showToastShort("加入失败！请检查网络！");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());
                ToastUtils.showToastShort("加入失败！请检查网络！");
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

        requestQueue.add(stringRequest);
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
                    myInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
                    ToastUtils.showToastShort("加入成功！");
                    setResult(111);
                    finish();
                } else {
                    ToastUtils.showToastShort("加入失败！请检查网络！");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort("请求失败，请查看网络连接");
            }
        }) {


        };
        MyApplication.getHttpQueues().add(request);
    }

    private List<ShopJiaoLianBean.Data> huijiList;

    private void getHuiJi(final String shopID) {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_CONSULTANT_URL), new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {
                LogUtil.e("zzf", s);
                ShopJiaoLianBean shopJiaoLianBean = gson.fromJson(s, ShopJiaoLianBean.class);
                if (shopJiaoLianBean != null) {
                    huijiList = shopJiaoLianBean.getData();
                    if (huijiList.size() > 4) {
                        gv_huiji.setAdapter(new MyGridViewAdapter(huijiList.subList(0, 4)));
                    } else {
                        gv_huiji.setAdapter(new MyGridViewAdapter(huijiList));
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
                map.put("branch_id", shopID);

                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);

    }

    private void getJiaolian(final String shopID) {


        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_JIAOLIAN_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.e("zzf", s);
                ShopJiaoLianBean shopJiaoLianBean = gson.fromJson(s, ShopJiaoLianBean.class);
                if (shopJiaoLianBean != null) {

                    jiaolianList = shopJiaoLianBean.getData();
                    if (jiaolianList.size() > 4) {
                        gv_jiaolian.setAdapter(new MyGridViewAdapter(jiaolianList.subList(0, 4)));
                    } else {

                        gv_jiaolian.setAdapter(new MyGridViewAdapter(jiaolianList));

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
                map.put("branch_id", shopID);

                return map;
            }


        };

        MyApplication.getHttpQueues().add(stringRequest);

    }

    private void getShopPictrue() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.BRANCH_PICTURE_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                ShopPictrueBean pictrueBean = gson.fromJson(s, ShopPictrueBean.class);
                if (pictrueBean.getData().size() > 0) {
                    gv_shop_image.setAdapter(new MyGridViewImageAdapter(pictrueBean.getData()));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("branch_id", branchID);
                return map;
            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }
    private void getBanner(final String branchId) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.BANNER), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                BranchBannerBean branchBannerBean = gson.fromJson(s, BranchBannerBean.class);
                if (branchBannerBean != null) {
                    backBannerList.clear();
                    List<BranchBannerBean.Data> data = branchBannerBean.getData();
                    if (data != null) {
                        for (int i = 0; i < data.size(); i++) {
                            imgList.add(data.get(i).getImg_url());

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
    private void getShopDetail() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.BRANCH )+ "/" + branchID, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                ShopDetailBean shopDetailBean = gson.fromJson(s, ShopDetailBean.class);
                ShopDetailBean.DataBean data = shopDetailBean.getData();
                if (data != null) {

                    shopName=data.getName();
                    store_name.setText(data.getName());

                    LatLng latLng = new LatLng(Double.valueOf(data.getLat()), Double.valueOf(data.getLng()));
                    double distance = DistanceUtil.getDistance(startLng, latLng);
                    Double aDouble = new Double(distance);
                    int i1 = aDouble.intValue();
                    if (i1 >= 1000) {
                        int v = i1 / 1000;
                        int v1 = (i1 - v * 1000) / 100;
                        tv_shopAddress.setText("距我 " + v + "." + v1 + " km");
                    } else {
                        tv_shopAddress.setText("距我 " + i1 + " m");
                    }
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


                    shopname = data.getName();
                    tv_adress.setText(data.getAddress());
                    tv_detail.setText(data.getDescription());
                    phoneNo = data.getTelphone();
                    branchID = data.getBranch_id() + "";
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {

        };
        requestQueue.add(stringRequest);
    }

    /**
     * 提示
     */
    private void showDialog() {
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(ShopDetailedActivity.this);
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

    private class MyGridViewAdapter extends BaseAdapter {
        List<ShopJiaoLianBean.Data> jiaolianList;

        public MyGridViewAdapter(List<ShopJiaoLianBean.Data> jiaolianList) {
            this.jiaolianList = jiaolianList;
        }

        @Override
        public int getCount() {
            return jiaolianList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(ShopDetailedActivity.this, R.layout.gridview_item_jiaolian, null);
            TextView tv_name = convertView.findViewById(R.id.tv_name);
            ImageView iv_avatar = convertView.findViewById(R.id.iv_avatar);
            tv_name.setText(jiaolianList.get(position).getCname());
            RequestOptions options = new RequestOptions().transform(new GlideRoundTransform(ShopDetailedActivity.this, 10)).placeholder(R.drawable.img_avatar1).error(R.drawable.img_avatar1);
            Glide.with(ShopDetailedActivity.this).load(jiaolianList.get(position).getAvatar_url()).apply(options).into(iv_avatar);
            iv_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(ShopDetailedActivity.this, EmpUserHomeActivty.class)
                            .putExtra("person_id", jiaolianList.get(position).getPerson_id() + "")
                            .putExtra("employee_id", jiaolianList.get(position).getId() + "")
                            .putExtra("branch_id", jiaolianList.get(position).getBranch_id() + "")
                            .putExtra("avatar", jiaolianList.get(position).getAvatar_url())
                    );
                }
            });
            return convertView;
        }
    }

    private class MyGridViewImageAdapter extends BaseAdapter {
        List<ShopPictrueBean.Data> jiaolianList;

        public MyGridViewImageAdapter(List<ShopPictrueBean.Data> shop_pictrue) {
            this.jiaolianList = shop_pictrue;
        }

        @Override
        public int getCount() {
            return jiaolianList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(ShopDetailedActivity.this, R.layout.gridview_item_shop_pictrue, null);
            ImageView iv_shoppictrue = convertView.findViewById(R.id.iv_shoppictrue);
            StringBuilder sb = new StringBuilder(jiaolianList.get(position).getPicture_url());
            String houzhui = jiaolianList.get(position).getPicture_url().substring(jiaolianList.get(position).getPicture_url().lastIndexOf(".") + 1);
            sb.insert(jiaolianList.get(position).getPicture_url().length() - houzhui.length() - 1, "_400X400");

            Glide.with(ShopDetailedActivity.this).load(sb.toString()).into(iv_shoppictrue);
            iv_shoppictrue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ShopDetailedActivity.this, AvatarActivity.class).putExtra("url", jiaolianList.get(position).getPicture_url()));



                }
            });
            return convertView;
        }
    }


}
