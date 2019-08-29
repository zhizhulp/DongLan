package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.adapter.TuankeRecylerViewAdapter;
import com.cn.danceland.myapplication.bean.CourseEvaluateBean;
import com.cn.danceland.myapplication.bean.CourseFindPerson;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.KeChengBiaoBean;
import com.cn.danceland.myapplication.bean.ShareInfoFromServiceBean;
import com.cn.danceland.myapplication.bean.SiJiaoYuYueConBean;
import com.cn.danceland.myapplication.bean.TuanKeBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.CustomGridView;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.NestedExpandaleListView;
import com.cn.danceland.myapplication.utils.ShareUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.cn.danceland.myapplication.R.id.course_place;
import static com.cn.danceland.myapplication.R.id.pic_01;
import static com.cn.danceland.myapplication.R.id.pic_02;
import static com.cn.danceland.myapplication.R.id.pic_03;

/**
 * Created by feng on 2018/1/12.
 */

public class TuanKeDetailActivity extends BaseActivity {
    ImageView kecheng_img,img_1,img_2,img_3,tuanke_back,down_img,up_img;
    NestedExpandaleListView kecheng_ex;
    int groupId;
    Gson gson;
    TextView kecheng_name,kecheng_time,kecheng_place,tv_jieshao,course_renshu,tv_status,course_jiaolian_huiyuan_name,tv_kecheng_fenshu,
    kecheng_room,course_type,tv_tuanke_title,tv_jiaolian_fenshu,tv_changdi_fenshu;
    Data data;
    String yuyueStartTime;
    MyAdapter myAdapter;
    KeChengBiaoBean.Data item;
    CircleImageView course_jiaolian_huiyuan_circle;
    List<CourseFindPerson.DataBean> headList,childList;
    RelativeLayout rl_button_yuyue;
    private TuanKeBean.Data detailData;
    RecyclerView my_recycler_view;
    private TuankeRecylerViewAdapter mRecylerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smalltuankedetail_activity);
        initHost();
        initView();
        getPeople();
    }

    private void initHost() {

        gson = new Gson();

        groupId = getIntent().getIntExtra("groupId",999);
        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        yuyueStartTime = getIntent().getStringExtra("yuyueStartTime");

        item = (KeChengBiaoBean.Data)getIntent().getSerializableExtra("item");
        headList = new ArrayList<>();
        childList = new ArrayList<>();

    }

    private void initView() {
        my_recycler_view = findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        my_recycler_view.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        my_recycler_view.setHasFixedSize(true);



        course_renshu = findViewById(R.id.course_renshu);
        rl_button_yuyue = findViewById(R.id.rl_button_yuyue);
        tv_status = findViewById(R.id.tv_status);
        rl_button_yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Long.valueOf(yuyueStartTime) + item.getStart_time() * 60000 >= System.currentTimeMillis()){
                    if(item.getSelf_appoint_count()==0){
                        commitYuyue(item,rl_button_yuyue,tv_status);
                    }
                }else{
                    ToastUtils.showToastShort("不可预约过期课程");
                }
            }
        });
        course_jiaolian_huiyuan_name = findViewById(R.id.course_jiaolian_huiyuan_name);
        course_jiaolian_huiyuan_circle = findViewById(R.id.course_jiaolian_huiyuan_circle);


        tv_kecheng_fenshu = findViewById(R.id.tv_kecheng_fenshu);
        tv_jiaolian_fenshu = findViewById(R.id.tv_jiaolian_fenshu);
        tv_changdi_fenshu = findViewById(R.id.tv_changdi_fenshu);

        kecheng_img =  findViewById(R.id.course_img);
        kecheng_name = findViewById(R.id.course_name);
        kecheng_time = findViewById(R.id.course_length);
        kecheng_place = findViewById(course_place);
        kecheng_room = findViewById(R.id.course_room);
        tv_jieshao = findViewById(R.id.tv_content);
        course_type = findViewById(R.id.course_type);
        DongLanTitleView titleView=findViewById(R.id.dl_title);
      //  tv_tuanke_title = findViewById(R.id.tv_tuanke_title);
        titleView.setTitle("免费团课");
        course_type.setText("免费团课");
        titleView.setMoreIvVisible(true);
        titleView.setMoreIvImg(R.drawable.img_more_dyn);
        titleView.setMoreIvOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInfo();
            }
        });

        img_1 = findViewById(pic_01);
        img_2 = findViewById(pic_02);
        img_3 = findViewById(pic_03);


        kecheng_ex = findViewById(R.id.my_expanda);
        kecheng_ex.setGroupIndicator(null);
        kecheng_ex.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                down_img = v.findViewById(R.id.down_img);
                up_img = v.findViewById(R.id.up_img);
                if(down_img.getVisibility()==View.GONE){
                    down_img.setVisibility(View.VISIBLE);
                    up_img.setVisibility(View.GONE);
                }else{
                    down_img.setVisibility(View.GONE);
                    up_img.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });




        if(groupId!=999){
            getData(groupId);
        }
        queryAverage();
    }


    private void ShareInfo() {

        final ShareInfoFromServiceBean strbean = new ShareInfoFromServiceBean();
        strbean.share_type = "11";//免费团课
        if (item != null) {
            strbean.course_type_id = item.getCourse_type_id() + "";
            strbean.bus_id = item.getId() + "";
            strbean.employee_id = item.getEmployee_id() + "";
            strbean.room_id = item.getRoom_id()+ "";
        }
//        if (item1 != null) {
//            strbean.course_type_id = item1.getCourse_type_id() + "";
//            strbean.bus_id = item1.getId() + "";
//            strbean.employee_id = item1.getEmployee_id() + "";
//            strbean.room_id = item1.getRoom_id()+ "";
//        }
        ShareUtils.create(this).shareWebInfoFromService(strbean);
    }

    private void commitYuyue(KeChengBiaoBean.Data data, final RelativeLayout rl, final TextView tv) {
        if(data!=null){
            SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
            siJiaoYuYueConBean.setGroup_course_id(data.getId());
            siJiaoYuYueConBean.setCourse_type_id(data.getCourse_type_id());
            siJiaoYuYueConBean.setCourse_type_name(data.getCourse_type_name());
            siJiaoYuYueConBean.setDate(yuyueStartTime);

            siJiaoYuYueConBean.setStart_time(data.getStart_time());
            siJiaoYuYueConBean.setEnd_time(data.getEnd_time());
            String s = gson.toJson(siJiaoYuYueConBean);
            LogUtil.i(s);
            if (TimeUtils.date2TimeStamp(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "yyyy-MM-dd") + 6 * 24 * 60 * 60 * 1000 < Long.valueOf(yuyueStartTime)) {

                ToastUtils.showToastShort("不能预约七日后的课程");
                return;
            }

            MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FreeCourseApply), s,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if(jsonObject.toString().contains("true")){
                        rl.setBackground(getResources().getDrawable(R.drawable.btn_bg_gray));
                        tv.setTextColor(getResources().getColor(R.color.white));
                        tv.setText("已预约");
                        rl_button_yuyue.setClickable(false);
                        ToastUtils.showToastShort("预约成功！");
                        setResult(223);
                        finish();
                    }else{
                        ToastUtils.showToastShort("预约失败！请重新预约！");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ToastUtils.showToastShort("预约失败！请重新预约！");
                }
            });
            MyApplication.getHttpQueues().add(jsonObjectRequest);
        }
    }

    public void initData(final TuanKeBean.Data detailData){

        RequestOptions  options =new RequestOptions().placeholder(R.drawable.loading_img);

        course_jiaolian_huiyuan_name.setText(detailData.getEmployee_name());
        RequestOptions options1 = new RequestOptions()
                .transform(new GlideRoundTransform(TuanKeDetailActivity.this,10));
        Glide.with(TuanKeDetailActivity.this).load(detailData.getCover_img_url()).apply(options).into(kecheng_img);
        Glide.with(TuanKeDetailActivity.this).load(detailData.getCourse_img_url_1()).apply(options1).into(img_1);
        Glide.with(TuanKeDetailActivity.this).load(detailData.getCourse_img_url_2()).apply(options1).into(img_2);
        Glide.with(TuanKeDetailActivity.this).load(detailData.getCourse_img_url_3()).apply(options1).into(img_3);

        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TuanKeDetailActivity.this, AvatarActivity.class).putExtra("url", detailData.getCourse_img_url_1()));
            }
        });
        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TuanKeDetailActivity.this, AvatarActivity.class).putExtra("url", detailData.getCourse_img_url_2()));
            }
        });
        img_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TuanKeDetailActivity.this, AvatarActivity.class).putExtra("url", detailData.getCourse_img_url_3()));
            }
        });

        String startTime,endTime;
        if(detailData.getStart_time()%60==0){
            startTime = detailData.getStart_time()/60+":00";
        }else{
            startTime = detailData.getStart_time()/60+":"+detailData.getStart_time()%60;
        }

        if(detailData.getEnd_time()%60==0){
            endTime = detailData.getEnd_time()/60+":00";
        }else{
            endTime = detailData.getEnd_time()/60+":"+detailData.getEnd_time()%60;
        }
        kecheng_time.setText("上课时间:"+startTime+"-"+endTime);
        Data data= (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
    //    LogUtil.i(data.getMember().getBranch_name());
        kecheng_place.setText("上课场馆:"+data.getMember().getBranch_name());
        kecheng_room.setText("上课场地:"+detailData.getRoom_name());
        kecheng_name.setText(detailData.getCourse_type_name());
        tv_jieshao.setText("课程介绍："+detailData.getCourse_describe());
        if(item!=null){

            if(Long.valueOf(yuyueStartTime) + item.getStart_time() * 60000 >= System.currentTimeMillis()){
                if(item.getSelf_appoint_count()>0){
                    tv_status.setText("已预约");
                    rl_button_yuyue.setBackground(getResources().getDrawable(R.drawable.btn_bg_dl_bule));
                }
            }else{
                tv_status.setText("已过期");
                rl_button_yuyue.setBackground(getResources().getDrawable(R.drawable.btn_bg_dl_bule));
            }

        }
        course_jiaolian_huiyuan_name.setText(detailData.getEmployee_name());

    }

    private void queryAverage(){
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERYAVERAGE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                CourseEvaluateBean courseEvaluateBean = gson.fromJson(s, CourseEvaluateBean.class);
                if(courseEvaluateBean!=null && courseEvaluateBean.getData()!=null){
                    CourseEvaluateBean.Data data = courseEvaluateBean.getData();
                    if(data.getCourse_type_score()!=null){
                        tv_kecheng_fenshu.setText(data.getCourse_type_score());
                    }else{
                        tv_kecheng_fenshu.setText("暂无评分");
                    }
                    if(data.getEmployee_score()!=null){
                        tv_jiaolian_fenshu.setText(data.getEmployee_score());
                    }else{
                        tv_jiaolian_fenshu.setText("暂无评分");
                    }
                    if(data.getRoom_score()!=null){
                        tv_changdi_fenshu.setText(data.getRoom_score());
                    }else {
                        tv_changdi_fenshu.setText("暂无评分");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError!=null){
                    LogUtil.i(volleyError.toString());
                }else {
                    LogUtil.i("获取评分失败");
                }
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                if(item!=null){
                    map.put("employeeId",item.getEmployee_id()+"");
                    map.put("courseTypeId",item.getCourse_type_id()+"");
//                    if(item.get!=null){
//                        map.put("roomId",room_id);
//                    }
                    map.put("branchId",item.getBranch_id()+"");
                }


                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    CourseFindPerson courseMemberBean;
    private void getPeople(){

        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
        siJiaoYuYueConBean.setGroup_course_id(groupId);
        siJiaoYuYueConBean.setDate(yuyueStartTime);
//        siJiaoYuYueConBean.setPage(0);
//        siJiaoYuYueConBean.setSize(6);

        String s = gson.toJson(siJiaoYuYueConBean);
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FINDFREEGROUPCOURSEAPPLYPERSON),s ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                    courseMemberBean = gson.fromJson(jsonObject.toString(), CourseFindPerson.class);
                    if(courseMemberBean!=null){
                        List<CourseFindPerson.DataBean> totalList = courseMemberBean.getData();
                        if(totalList !=null){
                            if(totalList.size()>6){
                                for(int i = 0;i<6;i++){
                                    headList.add(totalList.get(i));
                                }
                            }else if(totalList.size()<1){
                                kecheng_ex.setVisibility(View.GONE);
                            }else if(totalList.size()<6 && totalList.size()>0){
                                headList.addAll(totalList);
                                kecheng_ex.setVisibility(View.VISIBLE);
                            }
                            course_renshu.setText("上课会员("+ totalList.size()+")");
                            kecheng_ex.setAdapter(new MyAdapter());
                            mRecylerViewAdapter = new TuankeRecylerViewAdapter(TuanKeDetailActivity.this, totalList);
                            my_recycler_view.setAdapter(mRecylerViewAdapter);
                        }

                    }else {
                        kecheng_ex.setVisibility(View.GONE);
                        course_renshu.setText("上课会员(0)");
                    }

                LogUtil.e("zzf",jsonObject.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf",volleyError.toString());

            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }


    private void getData(final int groupId){

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FREEGROUPCOURSE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                TuanKeBean tuanKeBean = gson.fromJson(s, TuanKeBean.class);
                if(tuanKeBean!=null){
                    detailData = tuanKeBean.getData();
                    initData(detailData);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf",volleyError.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id",groupId+"");
                return map;
            }


        };

        MyApplication.getHttpQueues().add(stringRequest);

    }

    private class MyAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return 1;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView = LayoutInflater.from(TuanKeDetailActivity.this).inflate(R.layout.kecheng_parent,null);
            }

            CircleImageView circle_1 = convertView.findViewById(R.id.circle_1);
            CircleImageView circle_2 = convertView.findViewById(R.id.circle_2);
            CircleImageView circle_3 = convertView.findViewById(R.id.circle_3);
            CircleImageView circle_4 = convertView.findViewById(R.id.circle_4);
            CircleImageView circle_5 = convertView.findViewById(R.id.circle_5);
            CircleImageView circle_6 = convertView.findViewById(R.id.circle_6);

            CircleImageView[] imgArr = {circle_1,circle_2,circle_3,circle_4,circle_5,circle_6};

            if(headList!=null&&headList.size()>0){
                for(int i = 0;i<headList.size();i++){
                    imgArr[i].setVisibility(View.VISIBLE);
                    if(headList.get(i).getSelf_avatar_path()==null||headList.get(i).getSelf_avatar_path().equals("")){
                        Glide.with(TuanKeDetailActivity.this).load(R.drawable.img_my_avatar).into(imgArr[i]);
                    }else{
                        Glide.with(TuanKeDetailActivity.this).load(headList.get(i).getSelf_avatar_path()).into(imgArr[i]);
                    }
                }
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView = LayoutInflater.from(TuanKeDetailActivity.this).inflate(R.layout.kecheng_child,null);
            }
            CustomGridView grid_view = convertView.findViewById(R.id.grid_view);
            if(childList!=null && childList.size()!=0){
                grid_view.setAdapter(new MyGridAdapter());
            }


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }


    private class MyGridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return childList==null? 0:childList.size();
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
            View inflate = LayoutInflater.from(TuanKeDetailActivity.this).inflate(R.layout.kecheng_grid_item, null);
            CircleImageView circle_item = inflate.findViewById(R.id.circle_item);
            if(childList!=null){
                if(childList.get(position).getSelf_avatar_path()==null||childList.get(position).getSelf_avatar_path().equals("")){
                    Glide.with(TuanKeDetailActivity.this).load(R.drawable.img_my_avatar).into(circle_item);
                }else{
                    Glide.with(TuanKeDetailActivity.this).load(childList.get(position).getSelf_avatar_path()).into(circle_item);
                }

            }

            return inflate;
        }
    }

}
