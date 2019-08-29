package com.cn.danceland.myapplication.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.MapActivity;
import com.cn.danceland.myapplication.activity.ShopDetailedActivity;
import com.cn.danceland.myapplication.bean.BranchBannerBean;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.StoreBean;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feng on 2018/4/9.
 */

public class ShopListFragment extends BaseFragment {

    ListView lv_shoplist;
    View headView;
    MZBannerView shop_banner;
    ArrayList<String> drawableArrayList;
    String jingdu, weidu;
    List<StoreBean.DataBean> itemsList;
    Gson gson;
    Data info;
    TextView tv_shopname, tv_shopAddress;
    ImageButton ibtn_gps, ibtn_call;
    LatLng startLng;
    List<StoreBean.DataBean> itemsList1;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;
    ArrayList<String> imgList = new ArrayList<>();
    private TextView tv_detail;//新增详情布局

    private ArrayList<BranchBannerBean.Data> backBannerList = new ArrayList<>();
    private TextView tv_time;

    @Override
    public View initViews() {
        View inflate = View.inflate(mActivity, R.layout.shoplist_fragment, null);
        headView = LayoutInflater.from(mActivity).inflate(R.layout.shoplist_fragment_head, null);
        shop_banner = headView.findViewById(R.id.shop_banner);

        tv_shopname = headView.findViewById(R.id.tv_shopname);
        tv_time = headView.findViewById(R.id.tv_time);
        tv_shopAddress = headView.findViewById(R.id.tv_shopAddress);
        tv_detail = headView.findViewById(R.id.tv_detail);
        ibtn_gps = headView.findViewById(R.id.ibtn_gps);
        ibtn_call = headView.findViewById(R.id.ibtn_call);
        drawableArrayList = new ArrayList<>();
        itemsList1 = new ArrayList<>();

        rl_error = inflate.findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(mActivity).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("您的附近没有门店");

        gson = new Gson();
        lv_shoplist = inflate.findViewById(R.id.lv_shoplist);
        lv_shoplist.addHeaderView(headView);
//        lv_shoplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), ShopDetailedActivity.class);
//                intent.putExtra("shopWeidu", itemsList1.get(position).getLat() + "");
//                intent.putExtra("shopJingdu", itemsList1.get(position).getLng() + "");
//                intent.putExtra("jingdu", jingdu);
//                intent.putExtra("weidu", weidu);
//                intent.putExtra("branchID", itemsList1.get(position).getBranch_id() + "");
//                intent.putStringArrayListExtra("imgList", drawableArrayList);
//                startActivityForResult(intent, 111);
//            }
//        });
     //   initData();

        return inflate;
    }


    @Override
    public void initData() {
        if(getArguments()!=null){
            jingdu = getArguments().getString("jingdu");
            weidu = getArguments().getString("weidu");
        }
        if(jingdu==null || weidu==null){
            jingdu="0";
            weidu="0";
            ToastUtils.showToastShort("定位失败，获取门店列表失败");
        }
         LogUtil.i(jingdu + weidu);

        startLng = new LatLng(Double.valueOf(weidu),Double.valueOf(jingdu));
        getListData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
            initData();
        }
    }

    public void getListData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.BRANCH )+ "/" + weidu + "/" + jingdu, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                StoreBean storeBean = gson.fromJson(s, StoreBean.class);
                if (storeBean != null && storeBean.getData() != null) {
                    itemsList = storeBean.getData();
                    if (itemsList != null && itemsList.size() > 0) {
                        tv_shopname.setText(itemsList.get(0).getName());
                        String  open_time;
                        String colse_time;
                        if (Integer.valueOf(itemsList.get(0).getOpen_time())%60==0){
                            open_time =Integer.valueOf(itemsList.get(0).getOpen_time())/60+":00";
                        }else {
                            open_time =Integer.valueOf(itemsList.get(0).getOpen_time())/60+":"+Integer.valueOf(itemsList.get(0).getOpen_time())%60;
                        }
                        if (Integer.valueOf(itemsList.get(0).getClose_time())%60==0){
                            colse_time=Integer.valueOf(itemsList.get(0).getClose_time())/60+":00";
                        }else {
                            colse_time=Integer.valueOf(itemsList.get(0).getClose_time())/60+":"+Integer.valueOf(itemsList.get(0).getClose_time())%60;
                        }


                        tv_time.setText(open_time+"-"+colse_time);

                        LatLng latLng = new LatLng(Double.valueOf(itemsList.get(0).getLat()),Double.valueOf(itemsList.get(0).getLng()));
                        double distance = DistanceUtil.getDistance(startLng, latLng);
                        Double aDouble = new Double(distance);
                        int i1 = aDouble.intValue();
                        if(i1>=1000){
                            int v = i1 / 1000;
                            int v1 = (i1 - v*1000) / 100;
                            tv_shopAddress.setText("距我 "+v+"."+v1+" km");
                        }else{
                            tv_shopAddress.setText("距我 "+i1+" m");
                        }
//                        tv_detail.setVisibility(View.VISIBLE);
                        //tv_shopAddress.setText(itemsList.get(0).getAddress());
                        getBanner(itemsList.get(0).getBranch_id() + "");

                        ibtn_gps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), MapActivity.class);
                                intent.putExtra("shopWeidu", itemsList.get(0).getLat() + "");
                                intent.putExtra("shopJingdu", itemsList.get(0).getLng() + "");
                                intent.putExtra("jingdu", jingdu);
                                intent.putExtra("weidu", weidu);
                                intent.putExtra("shopname",itemsList.get(0).getName());
                                startActivity(intent);
                            }
                        });

                        ibtn_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDialog(itemsList.get(0).getTelphone());
                            }
                        });


                        itemsList1 = new ArrayList<>();
                        if (itemsList.size() > 1) {
                            for (int i = 1; i < itemsList.size(); i++) {
                                itemsList1.add(itemsList.get(i));
                            }
                        }
                        lv_shoplist.setAdapter(new MyStoreAdapter(getActivity(), itemsList1));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());
                rl_error.setVisibility(View.VISIBLE);
                tv_error.setText("网络异常");
                Glide.with(mActivity).load(R.drawable.img_error7).into(iv_error);
            }
        }) {

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
                    drawableArrayList.clear();
                    backBannerList.clear();
                    List<BranchBannerBean.Data> data = branchBannerBean.getData();
                    if (data != null) {
                        for (int i = 0; i < data.size(); i++) {
                            imgList.add(data.get(i).getImg_url());
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


    public class MyStoreAdapter extends BaseAdapter {
        Context mContext;
        List<StoreBean.DataBean> itemsArrayList;

        MyStoreAdapter(Context context, List<StoreBean.DataBean> list) {
            mContext = context;
            itemsArrayList = list;
        }

        @Override
        public int getCount() {
            return itemsArrayList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            StoreBean.DataBean items;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.store_item, null);
                viewHolder.store_item_img = convertView.findViewById(R.id.store_item_img);
                viewHolder.store_address = convertView.findViewById(R.id.store_address);
                viewHolder.tv_item_time = convertView.findViewById(R.id.tv_item_time);
                viewHolder.distance = convertView.findViewById(R.id.distance);
                viewHolder.img_location = convertView.findViewById(R.id.img_location);
                viewHolder.img_phone = convertView.findViewById(R.id.img_phone);
                viewHolder.img_join = convertView.findViewById(R.id.img_join);
                viewHolder.clickitem = convertView.findViewById(R.id.clickitem);
                viewHolder.item_layout_cv = convertView.findViewById(R.id.item_layout_cv);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(mContext, 16f), DensityUtils.dp2px(mContext, 16f), DensityUtils.dp2px(mContext, 16f), DensityUtils.dp2px(mContext, 11f));
            } else if (position == itemsArrayList.size() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(mContext, 16f), DensityUtils.dp2px(mContext, 5f), DensityUtils.dp2px(mContext, 16f), DensityUtils.dp2px(mContext, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(mContext, 16f), DensityUtils.dp2px(mContext, 5f), DensityUtils.dp2px(mContext, 16f), DensityUtils.dp2px(mContext, 11f));
            }
            viewHolder.item_layout_cv.setLayoutParams(layoutParams);

            if (itemsArrayList != null) {
                items = itemsArrayList.get(position);
                double lat = Double.valueOf(items.getLat());
                double lng = Double.valueOf(items.getLng());
                LatLng latLng = new LatLng(lat, lng);
                double distance = DistanceUtil.getDistance(startLng, latLng);
                Double aDouble = new Double(distance);
                int i1 = aDouble.intValue();
                if(i1>=1000){
                    int v = i1 / 1000;
                    int v1 = (i1 - v*1000) / 100;
                    viewHolder.distance.setText("距我 "+v+"."+v1+" km");
                }else{
                    viewHolder.distance.setText("距我 "+i1+" m");
                }

                viewHolder.store_address.setText(items.getName());
                String  open_time;
                String colse_time;
                if (Integer.valueOf(itemsList.get(position).getOpen_time())%60==0){
                    open_time =Integer.valueOf(itemsList.get(position).getOpen_time())/60+":00";
                }else {
                    open_time =Integer.valueOf(itemsList.get(position).getOpen_time())/60+":"+Integer.valueOf(itemsList.get(position).getOpen_time())%60;
                }
                if (Integer.valueOf(itemsList.get(position).getClose_time())%60==0){
                    colse_time=Integer.valueOf(itemsList.get(position).getClose_time())/60+":00";
                }else {
                    colse_time=Integer.valueOf(itemsList.get(position).getClose_time())/60+":"+Integer.valueOf(itemsList.get(position).getClose_time())%60;
                }
                viewHolder.tv_item_time.setText(open_time+"-"+colse_time);

                Glide.with(getActivity()).load(items.getLogo_url()).into(viewHolder.store_item_img);
                //PhoneNo = items.getTelphone_no();
//                shopJingdu = items.getLat()+"";
//                shopWeidu = items.getLng()+"";
//                branchId = items.getBranch_id()+"";
            }
            viewHolder.img_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemsArrayList != null) {
                        showDialog(itemsArrayList.get(position).getTelphone());
                    }
                }
            });
            viewHolder.img_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemsArrayList != null) {
                        Intent intent = new Intent(getActivity(), MapActivity.class);
                        intent.putExtra("shopWeidu", itemsArrayList.get(position).getLat() + "");
                        intent.putExtra("shopJingdu", itemsArrayList.get(position).getLng() + "");
                        intent.putExtra("jingdu", jingdu);
                        intent.putExtra("weidu", weidu);
                        intent.putExtra("shopname",itemsList.get(position).getName());
                        startActivity(intent);
                    }
                }
            });

            viewHolder.clickitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ShopDetailedActivity.class);
                    intent.putExtra("shopWeidu", itemsArrayList.get(position).getLat() + "");
                    intent.putExtra("shopJingdu", itemsArrayList.get(position).getLng() + "");
                    intent.putExtra("jingdu", jingdu);
                    intent.putExtra("weidu", weidu);
                    intent.putExtra("branchID", itemsArrayList.get(position).getBranch_id() + "");

                    LogUtil.i(itemsArrayList.get(position).toString());


                    intent.putExtra("imgList", imgList);
                    Bundle b = new Bundle();


                    b.putSerializable("backBannerList", backBannerList);
                    intent.putExtras(b);
//                    intent.putStringArrayListExtra("imgList", drawableArrayList);
                    startActivityForResult(intent, 111);
                }
            });

            return convertView;
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

    class ViewHolder {
        ImageView store_item_img, img_location, img_phone, img_join;
        TextView store_address, distance, unread_msg_number,tv_item_time;
        RelativeLayout clickitem;
        CardView item_layout_cv;
    }


    private void setBannner() {

        //监听事件必须在setpages之前
        shop_banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                Intent intent = new Intent(mActivity, ShopDetailedActivity.class);
                intent.putExtra("shopWeidu", itemsList.get(0).getLat() + "");
                intent.putExtra("shopJingdu", itemsList.get(0).getLng() + "");
                intent.putExtra("jingdu", jingdu);
                intent.putExtra("weidu", weidu);
                intent.putExtra("branchID", itemsList.get(0).getBranch_id() + "");
                Bundle b = new Bundle();
                b.putSerializable("backBannerList", backBannerList);
                intent.putExtras(b);
//                intent.putStringArrayListExtra("imgList", drawableArrayList);
                startActivityForResult(intent, 111);
            }
        });
        if (drawableArrayList != null && drawableArrayList.size() == 0) {
            drawableArrayList.add("http://i3.hoopchina.com.cn/blogfile/201403/31/BbsImg139626653396762_620*413.jpg");
            BranchBannerBean.Data bbb = new BranchBannerBean.Data();
            bbb.setImg_url("http://i3.hoopchina.com.cn/blogfile/201403/31/BbsImg139626653396762_620*413.jpg");
            backBannerList.add(bbb);
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

    @Override
    public void onPause() {
        super.onPause();
        shop_banner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
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

    @Override
    public void onClick(View v) {

    }
}
