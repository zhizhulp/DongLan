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
import com.cn.danceland.myapplication.adapter.XiaoTuankeRecylerViewAdapter;
import com.cn.danceland.myapplication.bean.CourseEvaluateBean;
import com.cn.danceland.myapplication.bean.CourseMemberBean;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.JiaoLianCourseBean;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by feng on 2018/3/30.
 * 小团体私教详情
 */

public class SmallTuankeDetailActivity extends BaseActivity {

    String startTime,endTime,startTimeTv,endTimeTv;
    Data data;
    String role;
    String auth;
    KeChengBiaoBean.Data item;
    JiaoLianCourseBean.Content item1;
    TextView course_name,course_length,course_place,course_room,
            course_jiaolian_huiyuan_name,course_renshu,tv_kecheng_fenshu,tv_jiaolian_fenshu,tv_changdi_fenshu,
            tv_content,tv_status;
    ImageView course_img,small_back;
    CircleImageView course_jiaolian_huiyuan_circle;
    RelativeLayout rl_button_yuyue;
    NestedExpandaleListView my_expanda;
    ImageView down_img,up_img;
    Gson gson;
    CourseMemberBean courseMemberBean;
    List<CourseMemberBean.Content> headList,childList;
    MyAdapter myAdapter;
    ImageView pic_01,pic_02,pic_03;
    int member_course_id;
    String yuyueStartTime;
    //SiJiaoRecordBean.Content record;
    String record_id;
    String emp_id,room_id,courseTypeId,branchId;
    RecyclerView my_recycler_view;
    private XiaoTuankeRecylerViewAdapter mRecylerViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smalltuankedetail_activity);
        initHost();
        initView();
        getDeatil();
        getPeople();
        queryAverage();
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
                map.put("employeeId",emp_id);
                map.put("courseTypeId",courseTypeId);
                if(room_id!=null){
                    map.put("roomId",room_id);
                }
                map.put("branchId",branchId);

                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void initHost() {


        gson = new Gson();
        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        startTimeTv = getIntent().getStringExtra("startTime");
        startTime = TimeUtils.date2TimeStamp(startTimeTv+" 00:00:00", "yyyy-MM-dd 00:00:00")+"";
        endTimeTv = getIntent().getStringExtra("endTime");
        role = getIntent().getStringExtra("role");
        auth = getIntent().getStringExtra("auth");

       // record = (SiJiaoRecordBean.Content)getIntent().getSerializableExtra("record");
        record_id=getIntent().getStringExtra("record_id");
//        if(role!=null){
//            item1 = (JiaoLianCourseBean.Content)getIntent().getSerializableExtra("item");
//        }else{
        item = (KeChengBiaoBean.Data)getIntent().getSerializableExtra("item");
        emp_id = item.getEmployee_id()+"";
        courseTypeId = item.getCourse_type_id()+"";
        branchId = item.getBranch_id()+"";

        member_course_id = getIntent().getIntExtra("member_course_id",-1);

        yuyueStartTime = getIntent().getStringExtra("yuyueStartTime");
        //}
    }

    private void initView() {
        DongLanTitleView titleView=findViewById(R.id.dl_title);
        titleView.setMoreIvVisible(true);
        titleView.setMoreIvImg(R.drawable.img_more_dyn);
        titleView.setMoreIvOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInfo();
            }
        });
        my_recycler_view = findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        my_recycler_view.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        my_recycler_view.setHasFixedSize(true);


        my_expanda = findViewById(R.id.my_expanda);
        myAdapter = new MyAdapter();
        //my_expanda.setAdapter(myAdapter);

        pic_01 = findViewById(R.id.pic_01);
        pic_02 = findViewById(R.id.pic_02);
        pic_03 = findViewById(R.id.pic_03);


        course_name = findViewById(R.id.course_name);
        course_length = findViewById(R.id.course_length);
        course_place = findViewById(R.id.course_place);
        course_room = findViewById(R.id.course_room);
        course_img = findViewById(R.id.course_img);
        course_jiaolian_huiyuan_name = findViewById(R.id.course_jiaolian_huiyuan_name);
        course_jiaolian_huiyuan_circle = findViewById(R.id.course_jiaolian_huiyuan_circle);
        course_renshu = findViewById(R.id.course_renshu);
        rl_button_yuyue = findViewById(R.id.rl_button_yuyue);
        tv_kecheng_fenshu = findViewById(R.id.tv_kecheng_fenshu);
        tv_jiaolian_fenshu = findViewById(R.id.tv_jiaolian_fenshu);
        tv_changdi_fenshu = findViewById(R.id.tv_changdi_fenshu);
        tv_content = findViewById(R.id.tv_content);
        my_expanda.setGroupIndicator(null);
        my_expanda.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
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
        tv_status = findViewById(R.id.tv_status);

        tv_content.setText("课程介绍："+item.getCourse_describe());

        setclick();

        //initData();a
    }

    private void ShareInfo() {

        final ShareInfoFromServiceBean strbean = new ShareInfoFromServiceBean();
        strbean.share_type = "1";//小团课
        if (item != null) {
            strbean.course_type_id = item.getCourse_type_id() + "";
            strbean.bus_id = item.getId() + "";
            strbean.employee_id = item.getEmployee_id() + "";
            strbean.room_id = item.getRoom_id()+ "";
        }
        if (item1 != null) {
            strbean.course_type_id = item1.getCourse_type_id() + "";
            strbean.bus_id = item1.getId() + "";
            strbean.employee_id = item1.getEmployee_id() + "";
            strbean.room_id = item1.getRoom_id()+ "";
        }
        ShareUtils.create(this).shareWebInfoFromService(strbean);
    }
    private void getDeatil(){


        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FINDGROUP), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                TuanKeBean tuanKeBean = gson.fromJson(s, TuanKeBean.class);
                if(tuanKeBean!=null){
                    TuanKeBean.Data detailData = tuanKeBean.getData();
                    if(detailData!=null){
                        initData(detailData);
                    }
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
                if(item!=null){
                    map.put("id",item.getId()+"");
                }else{
                    map.put("id",record_id);
                }

                return map;
            }

        };

        MyApplication.getHttpQueues().add(stringRequest);


    }

    private void initData(final TuanKeBean.Data detailData){
        course_name.setText(detailData.getCourse_type_name());
        course_length.setText("上课时间:"+TimeUtils.timeStamp2Date(detailData.getCourse_date(),"yyyy-MM-dd")+" "+TimeUtils.MinuteToTime(detailData.getStart_time())
        +"-"+TimeUtils.MinuteToTime(detailData.getEnd_time()));
        Data data= (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
   //     LogUtil.i(data.getMember().getBranch_name());
        course_place.setText("上课场馆:"+data.getMember().getBranch_name());
        course_room.setText("上课场地:"+detailData.getRoom_name());
        Glide.with(SmallTuankeDetailActivity.this).load(detailData.getCover_img_url()).into(course_img);
        if(detailData.getEmployee_avatar_path()==null||detailData.getEmployee_avatar_path().equals("")){
            Glide.with(SmallTuankeDetailActivity.this).load(R.drawable.img_my_avatar).into(course_jiaolian_huiyuan_circle);
        }else{
            Glide.with(SmallTuankeDetailActivity.this).load(detailData.getEmployee_avatar_path()).into(course_jiaolian_huiyuan_circle);
        }

        course_jiaolian_huiyuan_name.setText(detailData.getEmployee_name());
        RequestOptions options = new RequestOptions()
                .transform(new GlideRoundTransform(SmallTuankeDetailActivity.this,10));

        Glide.with(SmallTuankeDetailActivity.this).load(detailData.getCourse_img_url_1()).apply(options).into(pic_01);
        Glide.with(SmallTuankeDetailActivity.this).load(detailData.getCourse_img_url_2()).apply(options).into(pic_02);
        Glide.with(SmallTuankeDetailActivity.this).load(detailData.getCourse_img_url_3()).apply(options).into(pic_03);
        pic_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SmallTuankeDetailActivity.this, AvatarActivity.class).putExtra("url", detailData.getCourse_img_url_1()));
            }
        });
        pic_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SmallTuankeDetailActivity.this, AvatarActivity.class).putExtra("url", detailData.getCourse_img_url_2()));
            }
        });
        pic_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SmallTuankeDetailActivity.this, AvatarActivity.class).putExtra("url", detailData.getCourse_img_url_3()));
            }
        });
    }


    private void setclick() {

//        rl_button_yuyue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(SmallTuankeDetailActivity.this,SiJiaoDetailActivity.class);
////                if(role!=null){
////                    intent.putExtra("item",item1);
////                }else{
//                    intent.putExtra("item",item);
//                //}
//                intent.putExtra("startTime",startTime);
//                intent.putExtra("endTime",endTime);
//                intent.putExtra("role",role);
//                intent.putExtra("auth",auth);
//
//                startActivity(intent);
//            }
//        });


        rl_button_yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String format = "yyyy-MM-dd";
                String dayStr = TimeUtils.timeStamp2Date(yuyueStartTime, format);//2019-3-22
                long hours = item.getStart_time() * 60 * 1000;
                String hourStr = TimeUtils.timeToStr(hours, "HH:mm:ss");//14:00:00
                Long time = TimeUtils.date2TimeStamp(dayStr + " " + hourStr, "yyyy-MM-dd HH:mm:ss");
                if(time >= System.currentTimeMillis()){
                    if(item.getSelf_appoint_count()==0){
                        commitYuyue();
                    }
                }else{
                    ToastUtils.showToastShort("不可预约过期课程");
                }
            }
        });

        if(item!=null){
            String format = "yyyy-MM-dd";
            String dayStr = TimeUtils.timeStamp2Date(yuyueStartTime, format);//2019-3-22
            long hours = item.getStart_time() * 60 * 1000;
            String hourStr = TimeUtils.timeToStr(hours, "HH:mm:ss");//14:00:00
            Long time = TimeUtils.date2TimeStamp(dayStr + " " + hourStr, "yyyy-MM-dd HH:mm:ss");

            if(time >= System.currentTimeMillis()){
                if(item.getSelf_appoint_count()>0){
                    tv_status.setText("已预约");
                    rl_button_yuyue.setBackground(getResources().getDrawable(R.drawable.btn_bg_dl_bule));
                }
            }else{
                tv_status.setText("已过期");
                rl_button_yuyue.setBackground(getResources().getDrawable(R.drawable.btn_bg_dl_bule));
            }

        }else{
            tv_status.setText("已结束");
            rl_button_yuyue.setClickable(false);
            rl_button_yuyue.setBackground(getResources().getDrawable(R.drawable.btn_bg_dl_bule));
        }


    }

    private void commitYuyue(){


        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
        siJiaoYuYueConBean.setGroup_course_id(item.getId());

        siJiaoYuYueConBean.setMember_course_id(member_course_id);

        siJiaoYuYueConBean.setCourse_type_id(item.getCourse_type_id());
        siJiaoYuYueConBean.setCourse_type_name(item.getCourse_type_name());
        siJiaoYuYueConBean.setDate(yuyueStartTime);
        siJiaoYuYueConBean.setStart_time(item.getStart_time());
        siJiaoYuYueConBean.setEnd_time(item.getEnd_time());

        String s = gson.toJson(siJiaoYuYueConBean);

        if (TimeUtils.date2TimeStamp(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "yyyy-MM-dd") + 6 * 24 * 60 * 60 * 1000 < Long.valueOf(yuyueStartTime)) {

            ToastUtils.showToastShort("不能预约七日后的课程");
            return;
        }

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.GROUPAPPOINT), s,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(jsonObject.toString().contains("true")){
//                    rl.setBackgroundColor(Color.parseColor("#ADFF2F"));
//                    tv.setText("已预约");
                    ToastUtils.showToastShort("预约成功！");
                    setResult(222);
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

    private void getPeople(){
        String url = null;

        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
        if(item!=null){
            siJiaoYuYueConBean.setGroup_course_id(item.getId());
            url = Constants.plus(Constants.QUERYGROUPCOURSE);
            siJiaoYuYueConBean.setPage(0);
            siJiaoYuYueConBean.setSize(100);
        }else{
            siJiaoYuYueConBean.setGroup_course_id(Integer.valueOf(record_id));
            url = Constants.plus(Constants.FINDGROUPCOURSEAPPOINTPERSON);
            siJiaoYuYueConBean.setDate(yuyueStartTime);
        }

        String s = gson.toJson(siJiaoYuYueConBean);
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, url,s ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(item!=null){
                    courseMemberBean = gson.fromJson(jsonObject.toString(), CourseMemberBean.class);
                    if(courseMemberBean!=null){
                        CourseMemberBean.Data data = courseMemberBean.getData();
                        if(data!=null){
                            if(data.getTotalElements()>6){
                                getTotlePeple();
                            }else if(data.getTotalElements()<1){
                                my_expanda.setVisibility(View.GONE);
                            }
                            if(item!=null){
                                course_renshu.setText("购买会员("+data.getContent().size()+")");
                            }else{
                                course_renshu.setText("上课会员("+data.getContent().size()+")");
                            }

                            headList = data.getContent();

                            my_expanda.setAdapter(myAdapter);
                            mRecylerViewAdapter = new XiaoTuankeRecylerViewAdapter(SmallTuankeDetailActivity.this, data.getContent());
                            my_recycler_view.setAdapter(mRecylerViewAdapter);
                        }

                    }
                }else{
                    my_expanda.setVisibility(View.GONE);
                    if(item!=null){
                        course_renshu.setText("购买会员(0)");
                    }else{
                        course_renshu.setText("上课会员(0)");
                    }

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


    private void getTotlePeple(){

        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
        siJiaoYuYueConBean.setCourse_type_id(item.getCourse_type_id());
        siJiaoYuYueConBean.setDate(yuyueStartTime);
        siJiaoYuYueConBean.setPage(0);
        siJiaoYuYueConBean.setSize(100);

        String s = gson.toJson(siJiaoYuYueConBean);
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERYGROUPCOURSE),s ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                courseMemberBean = gson.fromJson(jsonObject.toString(), CourseMemberBean.class);
                if(courseMemberBean!=null&&courseMemberBean.getData()!=null){
                    childList = courseMemberBean.getData().getContent();


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

    private class MyAdapter extends BaseExpandableListAdapter {

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
                convertView = LayoutInflater.from(SmallTuankeDetailActivity.this).inflate(R.layout.kecheng_parent,null);
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
                        Glide.with(SmallTuankeDetailActivity.this).load(R.drawable.img_my_avatar).into(imgArr[i]);
                    }else{
                        Glide.with(SmallTuankeDetailActivity.this).load(headList.get(i).getSelf_avatar_path()).into(imgArr[i]);
                    }
                }
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView = LayoutInflater.from(SmallTuankeDetailActivity.this).inflate(R.layout.kecheng_child,null);
            }
            CustomGridView grid_view = convertView.findViewById(R.id.grid_view);
            grid_view.setAdapter(new MyGridAdapter());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }


    private class MyGridAdapter extends BaseAdapter {

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
            View inflate = LayoutInflater.from(SmallTuankeDetailActivity.this).inflate(R.layout.kecheng_grid_item, null);
            CircleImageView circle_item = inflate.findViewById(R.id.circle_item);
            if(childList!=null){
                if(childList.get(position).getSelf_avatar_path()==null||childList.get(position).getSelf_avatar_path().equals("")){
                    Glide.with(SmallTuankeDetailActivity.this).load(R.drawable.img_my_avatar).into(circle_item);
                }else{
                    Glide.with(SmallTuankeDetailActivity.this).load(childList.get(position).getSelf_avatar_path()).into(circle_item);
                }

            }


            return inflate;
        }
    }
}
