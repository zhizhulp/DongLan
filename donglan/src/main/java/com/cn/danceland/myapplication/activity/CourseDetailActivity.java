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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.adapter.SiJiaoRecylerViewAdapter;
import com.cn.danceland.myapplication.bean.CourseEvaluateBean;
import com.cn.danceland.myapplication.bean.CourseMemberBean;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.JiaoLianCourseBean;
import com.cn.danceland.myapplication.bean.MyCourseBean;
import com.cn.danceland.myapplication.bean.ShareInfoFromServiceBean;
import com.cn.danceland.myapplication.bean.SiJiaoYuYueConBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.CustomGridView;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ShareUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.view.CommitButton;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by feng on 2018/3/30.
 */

public class CourseDetailActivity extends BaseActivity {

    String startTime, endTime, startTimeTv, endTimeTv;
    Data data;
    String role;
    String auth;
    MyCourseBean.Data item;
    JiaoLianCourseBean.Content item1;
    TextView course_name, course_length, course_place, course_room,
            course_jiaolian_huiyuan_name, course_renshu, tv_kecheng_fenshu, tv_jiaolian_fenshu, tv_changdi_fenshu,
            tv_content;
    ImageView course_img, course_back;
    CircleImageView course_jiaolian_huiyuan_circle;
    CommitButton rl_button_yuyue;
    // NestedExpandaleListView my_expanda;
    ImageView down_img, up_img;
    Gson gson;
    CourseMemberBean courseMemberBean;
    List<CourseMemberBean.Content> headList, childList;
    MyAdapter myAdapter;
    String emp_id, room_id, courseTypeId, branchId;
    RecyclerView my_recycler_view;
    private SiJiaoRecylerViewAdapter mRecylerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.coursedetail_activity);
        initHost();

        initView();
        queryAverage();
    }

    private class Strbean {
        public String course_type_id;
        public String employee_id;
        public String id;
        public String share_type;

    }

    private void ShareInfo() {

        final ShareInfoFromServiceBean strbean = new ShareInfoFromServiceBean();
        strbean.share_type = "2";//一对一
        if (item != null) {
            strbean.course_type_id = item.getCourse_type_id() + "";
            strbean.bus_id = item.getId() + "";
            strbean.employee_id = item.getEmployee_id() + "";
        }
        if (item1 != null) {
            strbean.course_type_id = item1.getCourse_type_id() + "";
            strbean.bus_id = item1.getId() + "";
            strbean.employee_id = item1.getEmployee_id() + "";
        }
        ShareUtils.create(this).shareWebInfoFromService(strbean);
    }


    private void queryAverage() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERYAVERAGE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                CourseEvaluateBean courseEvaluateBean = gson.fromJson(s, CourseEvaluateBean.class);
                if (courseEvaluateBean != null && courseEvaluateBean.getData() != null) {
                    CourseEvaluateBean.Data data = courseEvaluateBean.getData();
                    if (data.getCourse_type_score() != null) {
                        tv_kecheng_fenshu.setText(data.getCourse_type_score());
                    } else {
                        tv_kecheng_fenshu.setText("暂无评分");
                    }
                    if (data.getEmployee_score() != null) {
                        tv_jiaolian_fenshu.setText(data.getEmployee_score());
                    } else {
                        tv_jiaolian_fenshu.setText("暂无评分");
                    }
                    if (data.getRoom_score() != null) {
                        tv_changdi_fenshu.setText(data.getRoom_score());
                    } else {
                        tv_changdi_fenshu.setText("暂无评分");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtil.i(volleyError.toString());
                } else {
                    LogUtil.i("获取评分失败");
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> map = new HashMap<>();
                map.put("employeeId", emp_id);
                map.put("courseTypeId", courseTypeId);
                if (room_id != null) {
                    map.put("roomId", room_id);
                }
                map.put("branchId", branchId);

                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }


    private void initHost() {

        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        startTimeTv = getIntent().getStringExtra("startTime");
        startTime = TimeUtils.date2TimeStamp(startTimeTv + " 00:00:00", "yyyy-MM-dd 00:00:00") + "";
        endTimeTv = getIntent().getStringExtra("endTime");
        role = getIntent().getStringExtra("role");
        auth = getIntent().getStringExtra("auth");
        if (role != null) {
            item1 = (JiaoLianCourseBean.Content) getIntent().getSerializableExtra("item");
            emp_id = item1.getEmployee_id() + "";
            courseTypeId = item1.getCourse_type_id() + "";
            branchId = item1.getBranch_id() + "";
            LogUtil.i(item1.toString());
        } else {
            item = (MyCourseBean.Data) getIntent().getSerializableExtra("item");
            emp_id = item.getEmployee_id() + "";
            courseTypeId = item.getCourse_type_id() + "";
            branchId = item.getBranch_id() + "";
            LogUtil.i(item.toString());
        }


        gson = new Gson();
        myAdapter = new MyAdapter();

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

            if (convertView == null) {
                convertView = LayoutInflater.from(CourseDetailActivity.this).inflate(R.layout.kecheng_parent, null);
            }

            CircleImageView circle_1 = convertView.findViewById(R.id.circle_1);
            CircleImageView circle_2 = convertView.findViewById(R.id.circle_2);
            CircleImageView circle_3 = convertView.findViewById(R.id.circle_3);
            CircleImageView circle_4 = convertView.findViewById(R.id.circle_4);
            CircleImageView circle_5 = convertView.findViewById(R.id.circle_5);
            CircleImageView circle_6 = convertView.findViewById(R.id.circle_6);


            CircleImageView[] imgArr = {circle_1, circle_2, circle_3, circle_4, circle_5, circle_6};

            if (headList != null && headList.size() > 0) {
                for (int i = 0; i < headList.size(); i++) {
                    imgArr[i].setVisibility(View.VISIBLE);
                    if (headList.get(i).getSelf_avatar_path() == null || headList.get(i).getSelf_avatar_path().equals("")) {
                        Glide.with(CourseDetailActivity.this).load(R.drawable.img_my_avatar).into(imgArr[i]);
                    } else {
                        Glide.with(CourseDetailActivity.this).load(headList.get(i).getSelf_avatar_path()).into(imgArr[i]);
                    }
                }
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(CourseDetailActivity.this).inflate(R.layout.kecheng_child, null);
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
            return childList == null ? 0 : childList.size();
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
            View inflate = LayoutInflater.from(CourseDetailActivity.this).inflate(R.layout.kecheng_grid_item, null);
            CircleImageView circle_item = inflate.findViewById(R.id.circle_item);
            if (childList != null) {
                if (childList.get(position).getSelf_avatar_path() == null || childList.get(position).getSelf_avatar_path().equals("")) {
                    Glide.with(CourseDetailActivity.this).load(R.drawable.img_my_avatar).into(circle_item);
                } else {
                    Glide.with(CourseDetailActivity.this).load(childList.get(position).getSelf_avatar_path()).into(circle_item);
                }

            }


            return inflate;
        }
    }

    private void initView() {
        DongLanTitleView titleView = findViewById(R.id.dl_title);
        ImageView imageView = titleView.getMoreIv();
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.img_more_dyn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareInfo();
            }
        });

        course_name = findViewById(R.id.course_name);
        course_length = findViewById(R.id.course_length);
        course_place = findViewById(R.id.course_place);
        course_room = findViewById(R.id.course_room);
        course_img = findViewById(R.id.course_img);

        course_jiaolian_huiyuan_name = findViewById(R.id.course_jiaolian_huiyuan_name);
        course_jiaolian_huiyuan_circle = findViewById(R.id.course_jiaolian_huiyuan_circle);
        course_renshu = findViewById(R.id.course_renshu);
        rl_button_yuyue = findViewById(R.id.dlbtn_commit);
        tv_kecheng_fenshu = findViewById(R.id.tv_kecheng_fenshu);
        tv_jiaolian_fenshu = findViewById(R.id.tv_jiaolian_fenshu);
        tv_changdi_fenshu = findViewById(R.id.tv_changdi_fenshu);
        tv_content = findViewById(R.id.tv_content);

        if (role != null) {

            tv_content.setText("课程介绍：" + item1.getCourse_type_describe());

        } else {

            tv_content.setText("课程介绍：" + item.getCourse_type_describe());
        }

        my_recycler_view = findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        my_recycler_view.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        my_recycler_view.setHasFixedSize(true);
        //创建并设置Adapter


        setclick();

        initData();
    }

    private void setclick() {

        rl_button_yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CourseDetailActivity.this, SiJiaoDetailActivity.class);
                if (role != null) {
                    intent.putExtra("item", item1);
                } else {
                    intent.putExtra("item", item);
                }
                intent.putExtra("startTime", startTimeTv);
                intent.putExtra("endTime", endTimeTv);
                intent.putExtra("role", role);
                intent.putExtra("auth", auth);

                startActivity(intent);
            }
        });


    }


    private void getPeople() {

        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();

        if (role != null) {

            siJiaoYuYueConBean.setCourse_type_id(item1.getCourse_type_id());

        } else {

            siJiaoYuYueConBean.setCourse_type_id(item.getCourse_type_id());
        }


        siJiaoYuYueConBean.setPage(0);
        siJiaoYuYueConBean.setSize(100);

        String s = gson.toJson(siJiaoYuYueConBean);
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERYBUYCOURSEPERSONINFO), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                courseMemberBean = gson.fromJson(jsonObject.toString(), CourseMemberBean.class);
                if (courseMemberBean != null) {
                    CourseMemberBean.Data data = courseMemberBean.getData();
                    if (data != null) {
                        if (data.getTotalElements() > 6) {
                            getTotlePeple();
                        } else if (data.getTotalElements() < 1) {
                            //       my_expanda.setVisibility(View.GONE);
                        }
                        course_renshu.setText("购买会员(" + data.getTotalElements() + ")");
                        headList = data.getContent();
//                        mRecylerViewAdapter = new SiJiaoRecylerViewAdapter(CourseDetailActivity.this, headList);
////                        my_recycler_view.setAdapter(mRecylerViewAdapter);
                        //     my_expanda.setAdapter(myAdapter);
                    }

                } else {
                    //      my_expanda.setVisibility(View.GONE);
                    course_renshu.setText("购买会员(0)");
                }

                LogUtil.e("zzf", jsonObject.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());

            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    private void getTotlePeple() {

        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();

        if (role != null) {

            siJiaoYuYueConBean.setCourse_type_id(item1.getCourse_type_id());

        } else {

            siJiaoYuYueConBean.setCourse_type_id(item.getCourse_type_id());
        }


//        siJiaoYuYueConBean.setCourse_type_id(item.getCourse_type_id());

        siJiaoYuYueConBean.setPage(0);
        siJiaoYuYueConBean.setSize(100);

        String s = gson.toJson(siJiaoYuYueConBean);
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERYGROUPCOURSE), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                courseMemberBean = gson.fromJson(jsonObject.toString(), CourseMemberBean.class);
                if (courseMemberBean != null && courseMemberBean.getData() != null) {
                    childList = courseMemberBean.getData().getContent();
                    mRecylerViewAdapter = new SiJiaoRecylerViewAdapter(CourseDetailActivity.this, headList);
                    my_recycler_view.setAdapter(mRecylerViewAdapter);
                }
                LogUtil.e("zzf", jsonObject.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());

            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    private void initData() {
        if (role != null) {
            course_name.setText(item1.getCourse_type_name());
            course_length.setText("课程时长：" + item1.getTime_length() + "分钟");
            course_place.setText("上课场馆：" + data.getMember().getBranch_name());
            course_room.setText("上课场地：私人教室");
            Glide.with(CourseDetailActivity.this).load(item1.getImg_url()).into(course_img);
            course_jiaolian_huiyuan_name.setText(item1.getEmployee_name());
        } else {
            course_name.setText(item.getCourse_type_name());
            course_length.setText("课程时长：" + item.getTime_length() + "分钟");
            course_place.setText("上课场馆：" + data.getMember().getBranch_name());
            course_room.setText("上课场地：私人教室");
            Glide.with(CourseDetailActivity.this).load(item.getImg_url()).into(course_img);
            course_jiaolian_huiyuan_name.setText(item.getEmployee_name());
        }

        getPeople();
    }

}
