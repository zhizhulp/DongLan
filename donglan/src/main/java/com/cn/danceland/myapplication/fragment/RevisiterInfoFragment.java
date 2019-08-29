package com.cn.danceland.myapplication.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.AddRevisiterRecordActivity;
import com.cn.danceland.myapplication.activity.EditPotentialActivity;
import com.cn.danceland.myapplication.bean.PotentialInfo;
import com.cn.danceland.myapplication.bean.RequsetPotentialInfoBean;
import com.cn.danceland.myapplication.bean.RequsetSimpleBean;
import com.cn.danceland.myapplication.evntbus.IntEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragmentEventBus;
import com.cn.danceland.myapplication.utils.CallLogUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2018/1/11 17:09
 * Email:644563767@qq.com
 */


public class RevisiterInfoFragment extends BaseFragmentEventBus {
    private String id;
    private Gson gson = new Gson();
    private RequsetPotentialInfoBean potentialInfoBean;
    private PotentialInfo info;
    private ScaleRatingBar sr_fitness_level;//健身指数
    private ScaleRatingBar sr_follow_level;//关注程度

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setview();
                    break;
                default:
                    break;
            }
        }
    };
    private ImageView iv_avatar;
    private ImageView iv_sex;
    private TextView tv_name;
    private TextView tv_lasttime;
    private TextView tv_weixin_no;
    private TextView tv_company;
    private TextView tv_address;
    private TextView tv_email;
    private TextView tv_guest_aware_way;
    private TextView tv_target;
    private TextView tv_card_type;
    private TextView tv_like;
    private TextView tv_medical_history;
    private TextView tv_remark;
    private TextView tv_phone;

    private ImageView iv_callphone;
    private ImageView iv_send_msg;
    private TextView tv_nationality;
    private TextView tv_remark1;
    private TextView tv_certificate_type;
    private TextView tv_emergency_name;
    private TextView tv_certificate_no;
    private TextView tv_emergency_phone;
    private TextView tv_birthday;
    private TextView tv_height;
    private TextView tv_weight;
    private TextView tv_branch_name;
    private TextView tv_teach_name;
    private TextView tv_biaoqian;
    private TextView tv_admin_name;
    private TextView tv_guest_recom;
    private TextView tv_final_teach_name;
    private TextView tv_final_admin_name;
    private LinearLayout ll_final_admin;
    private LinearLayout ll_admin;
    private LinearLayout ll_tuijianren;
    private ImageView iv_push_set;

    public void setview() {
        if (!TextUtils.isEmpty(info.getSelf_avatar_url())) {
            RequestOptions options = new RequestOptions()
                    .transform(new GlideRoundTransform(mActivity, 10)).placeholder(R.drawable.img_avatar1).error(R.drawable.img_avatar1);
            String S = info.getAvatar_url();
            Glide.with(mActivity).load(S).apply(options).into(iv_avatar);
        }

        if (TextUtils.equals(info.getGender(), "1")) {
            iv_sex.setImageResource(R.drawable.img_sex1);
        }
        if (TextUtils.equals(info.getGender(), "2")) {
            iv_sex.setImageResource(R.drawable.img_sex2);
        }
        tv_phone.setText(info.getPhone_no());

//        if (TextUtils.isEmpty(info.getNick_name())) {
//            tv_name.setText(info.getCname());
//        } else {
//            tv_name.setText(info.getCname() + "(" + info.getNick_name() + ")");
//        }
        tv_name.setText(info.getCname());
        if (info.getLast_time() != null) {
            tv_lasttime.setText("最后维护时间：" + info.getLast_time());
        } else {
            tv_lasttime.setText("最后维护时间：" + "最近未维护");
        }

        //会籍或会籍主管
        if (SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_HUIJIGUWEN || SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_HUIJIZHUGUANG) {
            if (TextUtils.isEmpty(info.getAdmin_mark())) {
                tv_biaoqian.setText(info.getAdmin_mark());
            } else {
                tv_biaoqian.setText("(" + info.getAdmin_mark() + ")");
            }


        }
        //教练或教练主管
        if (SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIAN || SPUtils.getInt(Constants.ROLE_ID, 0) == Constants.ROLE_ID_JIAOLIANZHUGUAN) {
            if (TextUtils.isEmpty(info.getTeach_mark())) {
                tv_biaoqian.setText(info.getTeach_mark());
            } else {
                tv_biaoqian.setText("(" + info.getTeach_mark() + ")");
            }
        }


        tv_weixin_no.setText(info.getWeichat_no());
        tv_company.setText(info.getCompany());
        tv_address.setText(info.getAddress());
        tv_email.setText(info.getMail());
        tv_guest_aware_way.setText(info.getGuest_way());
        if (!TextUtils.isEmpty(info.getFitness_level())) {
            sr_fitness_level.setRating(Integer.parseInt(info.getFitness_level()));
        }
        if (!TextUtils.isEmpty(info.getFollow_level())) {
            sr_follow_level.setRating(Integer.parseInt(info.getFollow_level()));
        }


        tv_card_type.setText(info.getCard_type());
        tv_remark.setText(info.getRemark());


        //  List<String> list = new ArrayList<String>();
        if (info.getTargetList() != null && info.getTargetList().size() > 0) {
            String s = "";
            for (int j = 0; j < info.getTargetList().size(); j++) {

                if (s == "") {
                    s = info.getTargetList().get(j).getData_value();
                } else {
                    s = s + "," + info.getTargetList().get(j).getData_value();
                }
                //  list.add(mParamInfoList.get(j).getData_key() + "");
            }
            tv_target.setText(s);
        }
        if (info.getProjectList() != null && info.getProjectList().size() > 0) {
            String s1 = "";
            for (int j = 0; j < info.getProjectList().size(); j++) {
                LogUtil.i(j + "");
                if (s1 == "") {
                    s1 = info.getProjectList().get(j).getData_value();
                } else {

                    s1 = s1 + "," + info.getProjectList().get(j).getData_value();
                }
            }
            tv_like.setText(s1);
        }
        if (info.getChonicList() != null && info.getChonicList().size() > 0) {
            String s2 = "";
            for (int j = 0; j < info.getChonicList().size(); j++) {

                if (s2 == "") {
                    s2 = info.getChonicList().get(j).getData_value();
                } else {
                    s2 = s2 + "," + info.getChonicList().get(j).getData_value();
                }
            }
            tv_medical_history.setText(s2);
        }
        iv_callphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PermissionsUtil.hasPermission(mActivity, Manifest.permission.CALL_PHONE)) {
                    //有权限
                    showDialog(info.getPhone_no());
                } else {
                    PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            showDialog(info.getPhone_no());
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
        iv_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PermissionsUtil.hasPermission(mActivity, Manifest.permission.READ_SMS)) {
                    //有权限
                    doSendSMSTo(info.getPhone_no(), "");
                } else {
                    PermissionsUtil.requestPermission(mActivity, new PermissionListener() {
                        @Override
                        public void permissionGranted(@NonNull String[] permissions) {
                            doSendSMSTo(info.getPhone_no(), "");
                        }

                        @Override
                        public void permissionDenied(@NonNull String[] permissions) {
                            //用户拒绝了申请
                            ToastUtils.showToastShort("没有权限");
                        }
                    }, new String[]{Manifest.permission.READ_SMS}, false, null);
                }

            }
        });


        tv_nationality.setText(info.getNationality());
        tv_certificate_type.setText(info.getCertificate_type());

        if (info.getIdentity_card() != null && info.getIdentity_card().length() > 3) {
//            for(int i;i<info.getIdentity_card().length();i++){
//
//            }
            String a = info.getIdentity_card().substring(1, info.getIdentity_card().length() - 1);
            String b = info.getIdentity_card().replace(a, "**************");

            tv_certificate_no.setText(b);
        } else {
            tv_certificate_no.setText("");
        }


        tv_emergency_name.setText(info.getEmergency_name());
        tv_emergency_phone.setText(info.getEmergency_name());
        tv_birthday.setText(info.birthday);
        tv_height.setText(info.getHeight());
        tv_weight.setText(info.getWeight());

        tv_branch_name.setText(info.getBranch_name());
        tv_teach_name.setText(info.getTeach_name());
        tv_admin_name.setText(info.getAdmin_name());
        tv_guest_recom.setText(info.getGuest_recom());

        tv_final_admin_name.setText(info.getFinal_admin_name());
        tv_final_teach_name.setText(info.getFinal_teach_name());
        if (TextUtils.equals(info.getAuth(), "2")) {
            ll_final_admin.setVisibility(View.VISIBLE);
            ll_admin.setVisibility(View.GONE);
            ll_tuijianren.setVisibility(View.VISIBLE);
        }
        if (TextUtils.equals(info.getAuth(), "1")) {
            iv_push_set.setVisibility(View.GONE);
        }
        if (info.getPush_setting() == 0) {
            iv_push_set.setImageResource(R.drawable.img_isdone_up);
        } else {
            iv_push_set.setImageResource(R.drawable.img_isdone_off);
        }

    }

    /**
     * 调起系统发短信功能
     *
     * @param phoneNumber
     * @param message
     */
    public void doSendSMSTo(String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            startActivity(intent);
        }
    }

    @Override
    public View initViews() {
        View v = View.inflate(mActivity, R.layout.fragment_revisiter_details, null);
        if (TextUtils.equals("true", getArguments().getString("isyewu"))) {
            v.findViewById(R.id.iv_more).setVisibility(View.INVISIBLE);
        }
        iv_avatar = v.findViewById(R.id.iv_avatar);
        iv_callphone = v.findViewById(R.id.iv_callphone);
        iv_send_msg = v.findViewById(R.id.iv_send_msg);
        tv_phone = v.findViewById(R.id.tv_phone);
        tv_biaoqian = v.findViewById(R.id.tv_biaoqian);
        iv_sex = v.findViewById(R.id.iv_sex);
        tv_name = v.findViewById(R.id.tv_name);
        tv_lasttime = v.findViewById(R.id.tv_lasttime);
        tv_weixin_no = v.findViewById(R.id.tv_weixin_no);
        tv_company = v.findViewById(R.id.tv_company);
        tv_address = v.findViewById(R.id.tv_address);
        tv_email = v.findViewById(R.id.tv_email);
        tv_guest_aware_way = v.findViewById(R.id.tv_guest_aware_way);
        sr_fitness_level = v.findViewById(R.id.sr_fitness_level);
        sr_follow_level = v.findViewById(R.id.sr_follow_level);
        tv_target = v.findViewById(R.id.tv_target);
        tv_card_type = v.findViewById(R.id.tv_card_type);
        tv_like = v.findViewById(R.id.tv_like);
        tv_medical_history = v.findViewById(R.id.tv_medical_history);
        tv_remark = v.findViewById(R.id.tv_remark);
        tv_nationality = v.findViewById(R.id.tv_nationality);
        tv_certificate_type = v.findViewById(R.id.tv_certificate_type);
        tv_certificate_no = v.findViewById(R.id.tv_certificate_no);
        tv_emergency_name = v.findViewById(R.id.tv_emergency_name);
        tv_emergency_phone = v.findViewById(R.id.tv_emergency_phone);
        tv_birthday = v.findViewById(R.id.tv_birthday);
        tv_height = v.findViewById(R.id.tv_height);
        tv_weight = v.findViewById(R.id.tv_weight);

        tv_branch_name = v.findViewById(R.id.tv_branch_name);
        tv_teach_name = v.findViewById(R.id.tv_teach_name);
        tv_admin_name = v.findViewById(R.id.tv_admin_name);
        tv_guest_recom = v.findViewById(R.id.tv_guest_recom);

        tv_final_teach_name = v.findViewById(R.id.tv_final_teach_name);
        tv_final_admin_name = v.findViewById(R.id.tv_final_admin_name);
        ll_final_admin = v.findViewById(R.id.ll_final_admin);
        ll_tuijianren = v.findViewById(R.id.ll_tuijianren);

        ll_admin = v.findViewById(R.id.ll_admin);
        iv_push_set = v.findViewById(R.id.iv_push_set);
        iv_push_set.setOnClickListener(this);
        v.findViewById(R.id.iv_more).setOnClickListener(this);
        return v;
    }

    @Override
    public void initDta() {

        id = getArguments().getString("id");

        find_by_id_potential(id);

    }

    @Override
    public void onEventMainThread(IntEvent event) {

        switch (event.getEventCode()) {
            case 211://刷新页面
                find_by_id_potential(id);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_more:
                showListDialog();
                break;
            case R.id.iv_push_set:
                if (info != null) {
                    if (info.getPush_setting() == 0) {
                        setpush(info.getId(), 1);
                    }else {
                        setpush(info.getId(), 0);
                    }

                }

                break;
            default:
                break;
        }
    }


    /**
     * 是否添加回访记录
     */
    public void showDialogRrcord() {
        final ContentResolver cr;
        cr = getActivity().getContentResolver();
        AlertDialog.Builder dialog =
                new AlertDialog.Builder(mActivity);
        dialog.setTitle("提示");
        dialog.setMessage("是否读取通话记录，并添加到回访记录");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Bundle bundle = new Bundle();
                bundle.putString("time", CallLogUtils.getLastCallHistoryDuration(null, cr) + "");
                bundle.putString("id", info.getId());
                bundle.putString("auth", info.getAuth());
                bundle.putString("member_name", info.getCname());
                bundle.putString("member_no", info.getMember_no());
                startActivity(new Intent(mActivity, AddRevisiterRecordActivity.class)
                        .putExtras(bundle));

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }


    private void showListDialog() {
        final String[] items = {"编辑资料"};
        //final String[] items = {"编辑资料", "转让", "放弃维护"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(mActivity);
        //listDialog.setTitle("我是一个列表Dialog");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("info", potentialInfoBean.getData());
                        startActivity(new Intent(mActivity, EditPotentialActivity.class).putExtras(bundle));

                    case 1:


                        break;
                    case 2:


                        break;
                    default:
                        break;
                }
            }
        });
        listDialog.show();
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

                // call("13436907535");
                showDialogRrcord();

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

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        //   Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phone));
        //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void setpush(final String member_id, final int push_setting) {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.EMP_MEM_PUSHRECEIVE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequsetSimpleBean simpleBean = new Gson().fromJson(s, RequsetSimpleBean.class);
                if (simpleBean.isSuccess()) {
                    find_by_id_potential(id);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("member_id", member_id);
                map.put("push_setting", "" + push_setting);

                return map;

            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void find_by_id_potential(String id) {


        MyStringRequest request = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.FIND_BY_ID_POTENTIAL) + id, new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                potentialInfoBean = new RequsetPotentialInfoBean();
                potentialInfoBean = gson.fromJson(s, RequsetPotentialInfoBean.class);
                //    LogUtil.i(potentialInfoBean.toString());
                if (potentialInfoBean.getSuccess()) {
                    //    LogUtil.i(potentialInfoBean.getData().toString());
                    info = potentialInfoBean.getData();
                    //   LogUtil.i(info.toString());
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
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


}
