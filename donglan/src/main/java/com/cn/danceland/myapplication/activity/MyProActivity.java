package com.cn.danceland.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.HeadImageBean;
import com.cn.danceland.myapplication.bean.RequestCollectBean;
import com.cn.danceland.myapplication.db.DBData;
import com.cn.danceland.myapplication.db.Donglan;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.PictureUtil;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.UIUtils;
import com.cn.danceland.myapplication.utils.multipartrequest.MultipartRequest;
import com.cn.danceland.myapplication.utils.multipartrequest.MultipartRequestParams;
import com.cn.danceland.myapplication.view.ContainsEmojiEditText;
import com.cn.danceland.myapplication.view.DongLanTransparentTitleView;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * 我的资料
 * Created by feng on 2017/10/10.
 */

public class MyProActivity extends BaseActivity {
    CircleImageView circleImageView;
    MyAdapter arrayAdapter;

    PopupWindow head_image_window;
    View headView, rootview, sexView;
    ImageView back;
    int flag;
    Uri uri, mCutUri;
    ContentResolver resolver;
    public final static int ALBUM_REQUEST_CODE = 1;
    public final static int CROP_REQUEST = 2;
    public final static int CAMERA_REQUEST_CODE = 3;
//    public static String SAVED_IMAGE_DIR_PATH =
//            Environment.getExternalStorageDirectory().getPath()
//                    + "/donglan/camera/";// 拍照路径
    // public String SAVED_IMAGE_DIR_PATH = AppUtils.getDiskCachePath(MyProActivity.this)+ "/";// 拍照路径

    String cameraPath, gemder, selfAvatarPath, strHeight, strWeight, iden;
    Data infoData;
    Gson gson;
    RequestQueue queue;
    File cutfile;
    RelativeLayout height, weight, rl_zone, rl_phone, identity;
    TextView text_sex, photograph, photo_album, cancel, cancel1, male, female, tv_height, tv_weight, tv_zone, tv_phone, tv_identity, selecttitle, over, cancel_action, lo_cancel_action, over_action;
    View contentView;
    PopupWindow mPopWindow;
    ListView list_height;
    View locationView;
    PopupWindow locationWindow;
    LocationAdapter proAdapter, cityAdapter;
    int x = 999;
    ArrayList<String> proList, cityList1;
    ListView list_province, list_city;
    DBData dbData;
    String zoneCode, mZoneCode;
    List<Donglan> zoneArr, cityList;
    View inflate;
    AlertDialog.Builder alertdialog;
    LoopView loopview;
    TextView tv_start, over_time;
    private LinearLayout sex, hobbyLL, headimage;
    private DongLanTransparentTitleView dongLanTitleView;
    private EmojiconEditText text_name;
    private EditText tv_hobby, tv_sign;
    private String compath;
    private String self_avatar_path;//保存提交参数
    private String nick_name;//保存提交参数
    private String gender;//保存提交参数
    private String hobby;//保存提交参数
    private String sign;//保存提交参数
    private ImageView header_background_iv;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    Glide.with(getApplicationContext()).load(selfAvatarPath).into(circleImageView);
                    if (compath != null && compath.length() > 0) {//修改头像
                        if (file != null)
                            file.delete();
                    }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypro);
        initHost();
        initView();
        setClick();
    }

    public void initHost() {
        dbData = new DBData();
        gson = new Gson();
        resolver = getContentResolver();
        infoData = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        queue = Volley.newRequestQueue(MyProActivity.this);
        zoneCode = infoData.getPerson().getZone_code();
        zoneArr = new ArrayList<Donglan>();
        if (zoneCode != null && !"".equals(zoneCode)) {
            zoneArr = dbData.queryCityValue(zoneCode + ".0");
        }
        initLocationData();
    }

    public void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        inflate = LayoutInflater.from(MyProActivity.this).inflate(R.layout.timeselect, null);
        tv_start = inflate.findViewById(R.id.tv_start);
        tv_start.setVisibility(View.GONE);
        loopview = inflate.findViewById(R.id.loopview);
        over_time = inflate.findViewById(R.id.over_time);
        over_time.setVisibility(View.GONE);
        header_background_iv = findViewById(R.id.header_background_iv);
        header_background_iv = (ImageView) UIUtils.setViewRatio(MyProActivity.this, header_background_iv, (float) 187.5, 110);

        alertdialog = new android.support.v7.app.AlertDialog.Builder(MyProActivity.this);

        rootview = LayoutInflater.from(MyProActivity.this).inflate(R.layout.activity_mypro, null);
        circleImageView = findViewById(R.id.circleimageview);
        if (infoData.getPerson().getSelf_avatar_path() != null && !infoData.getPerson().getSelf_avatar_path().equals("") && !infoData.getPerson().getSelf_avatar_path().contains("null")) {
            Glide.with(MyProActivity.this).load(infoData.getPerson().getSelf_avatar_path()).into(circleImageView);
        } else {
            Glide.with(MyProActivity.this).load(R.drawable.img_my_avatar).into(circleImageView);
        }
        text_name = findViewById(R.id.text_name);
        text_sex = findViewById(R.id.text_sex);
        headimage = findViewById(R.id.head_image);
//        name = findViewById(R.id.name);
        sex = findViewById(R.id.sex);
        back = findViewById(R.id.iv_back);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        rl_zone = findViewById(R.id.rl_zone);
        rl_phone = findViewById(R.id.rl_phone);
        identity = findViewById(R.id.identity);
        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_zone = findViewById(R.id.tv_zone);
        tv_phone = findViewById(R.id.tv_phone);
        tv_identity = findViewById(R.id.tv_identity);
        tv_hobby = findViewById(R.id.tv_hobby);
        tv_sign = findViewById(R.id.tv_sign);
        if (!TextUtils.isEmpty(infoData.getPerson().getPhone_no())) {
            tv_phone.setText(infoData.getPerson().getPhone_no());
        }
        if (infoData.getPerson().getIdentity_card() != null) {
            tv_identity.setText(infoData.getPerson().getIdentity_card());
        }

        if (infoData.getPerson().getHeight() != null) {
            tv_height.setText(infoData.getPerson().getHeight() + " cm");
        }
        if (infoData.getPerson().getWeight() != null) {
            tv_weight.setText(infoData.getPerson().getWeight() + " kg");
        }

        if (infoData.getPerson().getHobby() != null && infoData.getPerson().getHobby().length() > 0) {
            tv_hobby.setText(infoData.getPerson().getHobby());
        } else {
            tv_hobby.setText("未填写");
        }

        if (infoData.getPerson().getSign() != null && infoData.getPerson().getSign().length() > 0) {
            tv_sign.setText(infoData.getPerson().getSign());
        } else {
            tv_sign.setText("未填写");
        }


        if ("1".equals(infoData.getPerson().getGender())) {
            text_sex.setText("男");
        } else {
            text_sex.setText("女");
        }

        hobbyLL = findViewById(R.id.hobby);
//        rl_jianjie = findViewById(R.id.rl_jianjie);
        if (infoData.getPerson().getNick_name() != null && infoData.getPerson().getNick_name().length() > 0) {
            text_name.setText(infoData.getPerson().getNick_name());
        } else {
            text_name.setText("未填写");
        }

        headView = LayoutInflater.from(MyProActivity.this).inflate(R.layout.head_image_popwindow, null);

        photograph = headView.findViewById(R.id.photograph);
        photo_album = headView.findViewById(R.id.photo_album);
        cancel = headView.findViewById(R.id.cancel);

//        contentView = LayoutInflater.from(MyProActivity.this).inflate(R.layout.selectorwindowsingle, null);
//        mPopWindow = new PopupWindow(contentView,
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        list_height = contentView.findViewById(R.id.list_height);
//        selecttitle = contentView.findViewById(R.id.selecttitle);
//        over = contentView.findViewById(R.id.over);
//        cancel_action = contentView.findViewById(R.id.cancel_action);

        locationView = LayoutInflater.from(MyProActivity.this).inflate(R.layout.selectorwindowlocation, null);
        locationWindow = new PopupWindow(locationView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        locationWindow.setOutsideTouchable(true);
        locationWindow.setBackgroundDrawable(new BitmapDrawable());
        lo_cancel_action = locationView.findViewById(R.id.lo_cancel_action);
        over_action = locationView.findViewById(R.id.over_action);
        list_province = locationView.findViewById(R.id.list_province);
        list_city = locationView.findViewById(R.id.list_city);

        dongLanTitleView = findViewById(R.id.title);
        TextView more_iv = dongLanTitleView.getRightTv();
        more_iv.setVisibility(View.VISIBLE);
        more_iv.setText("保存");
        more_iv.setTextColor(getResources().getColor(R.color.white));
//        text_name.setSelection(text.length());//光标位置在文字末尾
        if (zoneArr.size() > 0) {
            tv_zone.setText(zoneArr.get(0).getProvince() + " " + zoneArr.get(0).getCity());
            zoneArr.clear();
        }
        more_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (compath != null && compath.length() > 0) {//修改头像
                    if (TextUtils.equals(infoData.getPerson().getSelf_avatar_path(), compath)) {
                        selfAvatarPath = infoData.getPerson().getSelf_avatar_path();
                        self_avatar_path = infoData.getPerson().getSelf_avatar_path();
                    } else {
                        self_avatar_path = compath;
                    }
                } else {
                    selfAvatarPath = infoData.getPerson().getSelf_avatar_path();
                    self_avatar_path = infoData.getPerson().getSelf_avatar_path();
                }
                if (TextUtils.isEmpty(text_name.getText().toString())) {
                    ToastUtils.showToastShort("请输入昵称");
                    return;
                }
                nick_name = text_name.getText().toString();//修改昵称

                if ("男".equals(text_sex.getText().toString())) {//修改性别
                    gender = "1";
                } else {
                    gender = "2";
                }
                hobby = tv_hobby.getText().toString();//修改喜好
                sign = tv_sign.getText().toString();//修改个人简介
                submitUserData();
            }
        });
    }

    public void setClick() {
        headimage.setOnClickListener(onClickListener);
//        name.setOnClickListener(onClickListener);
        hobbyLL.setOnClickListener(onClickListener);
        sex.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        photo_album.setOnClickListener(onClickListener);
        photograph.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
        height.setOnClickListener(onClickListener);
        weight.setOnClickListener(onClickListener);
        //over.setOnClickListener(onClickListener);
        //cancel_action.setOnClickListener(onClickListener);
        rl_zone.setOnClickListener(onClickListener);
        lo_cancel_action.setOnClickListener(onClickListener);
        over_action.setOnClickListener(onClickListener);
        rl_phone.setOnClickListener(onClickListener);
        identity.setOnClickListener(onClickListener);
//        rl_jianjie.setOnClickListener(onClickListener);

    }

    private void submitUserData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_MODIFY_PERSON_DATA), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestCollectBean requestInfoBean = gson.fromJson(s, RequestCollectBean.class);
                if (requestInfoBean.getSuccess() && requestInfoBean.getCode() == 0) {
                    //修改昵称--开始
                    infoData.getPerson().setNick_name(nick_name);

                    FriendshipManagerPresenter.setMyNick(nick_name, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            LogUtil.i("昵称修改失败");
                        }

                        @Override
                        public void onSuccess() {
                            LogUtil.i("昵称修改成功");
                        }
                    });
                    EventBus.getDefault().post(new StringEvent(nick_name, 100)); //发送事件
                    //修改昵称--结束
                    //修改性别--开始
                    infoData.getPerson().setGender(gender);
                    //修改性别--结束
                    //修改喜好--开始
                    infoData.getPerson().setHobby(hobby);
                    //修改喜好--结束
                    //修改个人简介--开始
                    infoData.getPerson().setSign(sign);
                    DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
                    //修改个人简介--结束
                    ToastUtils.showToastShort("修改成功");
                } else {
                    ToastUtils.showToastShort("修改失败！");
                }
                MyProActivity.this.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                ToastUtils.showToastShort("修改失败！请检查网络");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("self_avatar_path", selfAvatarPath);
                map.put("nick_name", nick_name);
                map.put("gender", gender);
                map.put("hobby", hobby);
                map.put("sign", sign);
                LogUtil.i("Params" + map);
                return map;
            }
        };
        queue.add(stringRequest);
    }

    public void dismissWindow() {
        if (null != head_image_window && head_image_window.isShowing()) {
            head_image_window.dismiss();
        }
//        if (null != mPopWindow && mPopWindow.isShowing()) {
//            mPopWindow.dismiss();
//        }
        if (null != locationWindow && locationWindow.isShowing()) {
            locationWindow.dismiss();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.head_image: {
                    if (PermissionsUtil.hasPermission(MyProActivity.this, Manifest.permission.CAMERA)) {
                        //有权限
                        flag = 0;
                        dismissWindow();
                        showEditImage();
                        showPop();
                    } else {
                        PermissionsUtil.requestPermission(MyProActivity.this, new PermissionListener() {
                            @Override
                            public void permissionGranted(@NonNull String[] permissions) {
                                flag = 0;
                                dismissWindow();
                                showEditImage();
                                showPop();
                            }

                            @Override
                            public void permissionDenied(@NonNull String[] permissions) {
                                //用户拒绝了申请
                                ToastUtils.showToastShort("没有权限");
                            }
                        }, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, false, null);
                    }
                }
                break;
//                case R.id.rl_jianjie:
//                    Data info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
//                    startActivityForResult(new Intent(MyProActivity.this, EditProActivity.class).putExtra("hint", info.getPerson().getSign()), 13);
//                    break;
                case R.id.lo_cancel_action:
                    dismissWindow();
                    break;
                case R.id.rl_phone:
//                    showSettingPhoneDialog();
                    break;
                case R.id.height:
                    //修改身高
                    x = 0;
                    showWH(x);
                    //selecttitle.setText("选择身高");
                    break;
                case R.id.weight:
                    x = 1;
                    showWH(x);
                    //selecttitle.setText("选择体重");
                    break;
//                case R.id.hobby:
//                    showHobby(infoData.getPerson().getHobby());
//                    break;

//                case R.id.over:
//                    if (x == 0) {
//                        infoData.setHeight(strHeight);
//                        commitSelf(Constants.MODIFY_HEIGHT, "height", strHeight);
//                    } else if (x == 1) {
//                        infoData.setWeight(strWeight);
//                        commitSelf(Constants.MODIFY_WEIGHT, "weight", strWeight);
//                    }
//                    DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
//                    dismissWindow();
//                    break;
//                case R.id.cancel_action:
//                    dismissWindow();
//                    break;
//                case R.id.name: {
//                    showName(0, infoData.getPerson().getNick_name());
//                }
//                break;
                case R.id.sex: {
                    flag = 1;
                    dismissWindow();
                    showSex();
                    showPop();
                }
                break;
                case R.id.cancel:
                    if (flag == 0) {
                        dismissWindow();
                    } else if (flag == 1) {
                        dismissWindow();
                        text_sex.setText("女");
//                        infoData.getPerson().setGender(gender);
//                        DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
                        gender = "2";
//                        commitSelf(Constants.MODIFY_GENDER, "gender", "2");
                    }
                    break;
                case R.id.photo_album:
                    if (flag == 0) {
                        dismissWindow();
                        photoAlbum();
                    } else if (flag == 1) {
                        dismissWindow();
                        text_sex.setText("男");
                        gender = "1";
//                        infoData.getPerson().setGender(gender);
//                        DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
//                        commitSelf(Constants.MODIFY_GENDER, "gender", "1");
                    }
                    break;
                case R.id.photograph:
                    if (flag == 0) {
                        dismissWindow();
                        photoGraph();
                    } else {
                        break;
                    }
                    break;
                case R.id.iv_back:
                    Intent intent = new Intent();
                    intent.putExtra("selfAvatarPath", selfAvatarPath);
                    setResult(99, intent);
                    finish();
                    break;
                case R.id.over_action:
                    if ("".equals(mZoneCode) || mZoneCode == null) {
                        ToastUtils.showToastShort("请选择城市");
                    } else {
                        infoData.getPerson().setZone_code(mZoneCode);
                        DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
                        if (mZoneCode.contains(".0")) {
                            commitSelf(Constants.MODIFY_ZONE, "zoneCode", mZoneCode.replace(".0", ""));
                        } else {
                            commitSelf(Constants.MODIFY_ZONE, "zoneCode", mZoneCode);
                        }

                        dismissWindow();
                    }

                    break;
                case R.id.rl_zone:
                    showLocation();
                    break;
                case R.id.identity:
                    showName(1, infoData.getPerson().getIdentity_card());
                    break;
            }
        }
    };

    private void showWH(final int j) {
        int n;
        final ArrayList<String> arrayList = new ArrayList<String>();
        ViewGroup parent = (ViewGroup) inflate.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        if (j == 0) {
            for (int i = 0; i < 71; i++) {
                n = 150 + i;
                arrayList.add(n + "");
            }
        } else {
            for (int y = 0; y < 165; y++) {
                n = 35 + y;
                arrayList.add(n + "");
            }
        }
        loopview.setNotLoop();
        loopview.setItems(arrayList);
        //设置初始位置
        loopview.setInitPosition(0);
        loopview.setTextSize(18);
        loopview.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (j == 0) {
                    //tv_height.setText(arrayList.get(index)+" cm");
                    strHeight = arrayList.get(index) + "";
                } else {
                    //tv_weight.setText(arrayList.get(index)+" kg");
                    strWeight = arrayList.get(index) + "";
                }
            }
        });
        if (j == 0) {
            alertdialog.setTitle("选择身高");
        } else {
            alertdialog.setTitle("选择体重");
        }

        alertdialog.setView(inflate);
        alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (j == 0) {
                    tv_height.setText(strHeight + " cm");
                    infoData.getPerson().setHeight(strHeight);
                    commitSelf(Constants.plus(Constants.MODIFY_HEIGHT), "height", strHeight);
                } else if (j == 1) {
                    tv_weight.setText(strWeight + " kg");
                    infoData.getPerson().setWeight(strWeight);
                    commitSelf(Constants.plus(Constants.MODIFY_WEIGHT), "weight", strWeight);
                }
                DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
            }
        });
        alertdialog.show();
    }

    public void showLocation() {

        locationWindow.setContentView(locationView);
        proAdapter = new LocationAdapter(proList, this);
        list_province.setAdapter(proAdapter);

        list_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pro = proList.get(position);
                tv_zone.setText(pro);
                List<Donglan> queryPro = dbData.queryPro(pro);
                mZoneCode = "";
                cityList1 = new ArrayList<String>();
                for (int i = 0; i < queryPro.size(); i++) {
                    cityList1.add(queryPro.get(i).getCity());
                }
                //ArrayList<String> cityList = proCityMap.get(pro);
                if (cityList1 != null && cityList1.size() > 0) {
                    cityAdapter = new LocationAdapter(cityList1, MyProActivity.this);
                    list_city.setAdapter(cityAdapter);
                }
            }
        });
        list_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = cityList1.get(position);
                mZoneCode = dbData.queryCity(city).get(0).getCityValue();
                tv_zone.setText(tv_zone.getText().toString().split(" ")[0] + " " + city);
            }
        });


        locationWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        locationWindow.setAnimationStyle(R.style.selectorMenuAnim);

    }

    public void initLocationData() {
        cityList = dbData.getCityList();
        //省份列表
        proList = new ArrayList<String>();
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                //城市名字为key，城市代码为value
                String prokey = cityList.get(i).getProvince();
                proList.add(prokey);
                for (int m = 0; m < proList.size() - 1; m++) {
                    if (proList.get(m).equals(proList.get(m + 1))) {
                        proList.remove(m);
                        m--;
                    }
                }

            }
        }

    }

    public class LocationAdapter extends BaseAdapter {

        ArrayList<String> arrayList;
        LayoutInflater inflater = null;

        public LocationAdapter(ArrayList<String> list, Context context) {
            arrayList = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
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
            TextView item_text = null;
            if (view == null) {
                view = inflater.inflate(R.layout.selector_item, null);
            }
            item_text = view.findViewById(R.id.item_text);
            item_text.setText(arrayList.get(i));
            return view;
        }
    }

    public void showSelectorWindow(int x) {
        final int j = x;
        mPopWindow.setContentView(contentView);
        //显示PopupWindow

        String[] str = new String[71];
        Integer[] str1 = new Integer[165];
        final ArrayList<String> arHeight = new ArrayList<String>();
        int n;
        if (j == 0) {
            for (int i = 0; i < 71; i++) {
                n = 150 + i;
                str[i] = n + "";
            }
            Arrays.sort(str);
            for (int z = 0; z < str.length; z++) {
                arHeight.add(str[z]);
            }
        } else {
            for (int y = 0; y < 165; y++) {
                n = 35 + y;
                str1[y] = n;
            }
            Arrays.sort(str1);
            for (int z = 0; z < str1.length; z++) {
                arHeight.add(str1[z] + "");
            }
        }

        arrayAdapter = new MyAdapter(arHeight, this);
        list_height.setAdapter(arrayAdapter);

        //mPopWindow.showAsDropDown(identity,0,40);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        mPopWindow.setAnimationStyle(R.style.selectorMenuAnim);

        list_height.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (j == 0) {
                    tv_height.setText(arHeight.get(i) + " cm");
                    strHeight = arHeight.get(i) + "";
                } else {
                    tv_weight.setText(arHeight.get(i) + " kg");
                    strWeight = arHeight.get(i) + "";
                }
            }
        });

    }

    public class MyAdapter extends BaseAdapter {

        ArrayList<String> arrayList;
        LayoutInflater inflater = null;

        public MyAdapter(ArrayList<String> list, Context context) {
            arrayList = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return arrayList.size();
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
            TextView item_text = null;
            if (view == null) {
                view = inflater.inflate(R.layout.selector_item, null);
            }
            item_text = view.findViewById(R.id.item_text);
            item_text.setText(arrayList.get(i));
            return view;
        }

    }

    public void showPop() {
        head_image_window = new PopupWindow(headView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        head_image_window.setOutsideTouchable(true);
        head_image_window.setBackgroundDrawable(new BitmapDrawable());
        //显示PopupWindow
        rootview = LayoutInflater.from(MyProActivity.this).inflate(R.layout.activity_mypro, null);
        head_image_window.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        head_image_window.setAnimationStyle(R.style.selectorMenuAnim);

    }

    public void showEditImage() {
        if (null != head_image_window && head_image_window.isShowing()) {
            head_image_window.dismiss();
        }
        photograph.setText("拍照");
        photograph.setTextColor(Color.parseColor("#ff6600"));
        photo_album.setText("从手机相册选择");
        cancel.setText("取消");
        //cancel1.setVisibility(View.GONE);
    }


    private void showHobby(String hint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProActivity.this);
        View dialogView = LayoutInflater.from(MyProActivity.this)
                .inflate(R.layout.edit_name, null);
        TextView dialogTitleName = dialogView.findViewById(R.id.tv_nick_name);
        dialogTitleName.setText("我的喜好");
        final EditText ed = dialogView.findViewById(R.id.edit_name);
        ed.setText(hint);
        builder.setView(dialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ed.getText() != null) {
                    tv_hobby.setText(ed.getText().toString());

                    commitSelf(Constants.plus(Constants.MODIFY_HOBBY), "hobby", ed.getText().toString());
                    infoData.getPerson().setHobby(ed.getText().toString());
                    DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
                }
            }
        });
        builder.show();
    }


    public void showName(final int i, String hint) {
        //i==0是编辑昵称i==1表示身份证
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MyProActivity.this);
        View dialogView = LayoutInflater.from(MyProActivity.this)
                .inflate(R.layout.edit_name, null);

        TextView dialogTitleName = dialogView.findViewById(R.id.tv_nick_name);
        TextView dialogTitleIden = dialogView.findViewById(R.id.tv_ide);
        if (i == 0) {
            dialogTitleName.setVisibility(View.VISIBLE);
            dialogTitleIden.setVisibility(View.GONE);
        } else {
            dialogTitleName.setVisibility(View.GONE);
            dialogTitleIden.setVisibility(View.VISIBLE);
        }
        //normalDialog.setTitle("编辑昵称");
        final ContainsEmojiEditText ed = dialogView.findViewById(R.id.edit_name);
        ed.setText(hint);
        if (i == 1) {
            ed.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        normalDialog.setView(dialogView);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (i == 0) {
                            if (TextUtils.isEmpty(ed.getText().toString())) {
                                ToastUtils.showToastShort("请输入昵称");

                                return;
                            }
                            nick_name = ed.getText().toString();
                            text_name.setText(nick_name);
                            commitSelf(Constants.plus(Constants.MODIFY_NAME), "nickName", nick_name);
                            infoData.getPerson().setNick_name(nick_name);
                            DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);


                            FriendshipManagerPresenter.setMyNick(nick_name, new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    LogUtil.i("昵称修改失败");
                                }

                                @Override
                                public void onSuccess() {

                                    LogUtil.i("昵称修改成功");
                                }
                            });

                            //发送事件
                            EventBus.getDefault().post(new StringEvent(nick_name, 100));
                        } else {
                            iden = ed.getText().toString();
                            tv_identity.setText(iden);
                            commitSelf(Constants.plus(Constants.MODIFY_IDENTIFY), "identityCard", iden);
                            infoData.getPerson().setIdentity_card(iden);
                            DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
                        }
                    }
                });
        // 显示
        normalDialog.show();

    }

    public void commitSelf(String url, final String mapkey, final String mapvalue) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.contains("true")) {
                    ToastUtils.showToastShort("修改成功！");


                } else {
                    ToastUtils.showToastShort("修改失败！");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("修改失败！请检查网络");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(mapkey, mapvalue);
                return map;
            }
        };
        queue.add(stringRequest);
    }


    public void showSex() {
        if (null != head_image_window && head_image_window.isShowing()) {
            head_image_window.dismiss();
        }
        photograph.setText("修改性别");
        photograph.setTextColor(Color.rgb(153, 153, 153));
        photo_album.setText("男");
        cancel.setText("女");
    }

    public void photoGraph() {

        // 指定相机拍摄照片保存地址
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            cameraPath = AppUtils.getDiskCachePath(MyProActivity.this) + "/" + System.currentTimeMillis() + ".png";
            Intent intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            String out_file_path = AppUtils.getDiskCachePath(MyProActivity.this) + "/";
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            } // 把文件地址转换成Uri格式
            if (PictureUtil.getSDKV() < 24) {
                uri = Uri.fromFile(new File(cameraPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            } else {
                // 设置系统相机拍摄照片完成后图片文件的存放地址
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, cameraPath);
                uri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
            //uri = Uri.fromFile(new File(cameraPath));

        } else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡",
                    Toast.LENGTH_LONG).show();
        }
    }

    private File file = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            MultipartRequestParams params = new MultipartRequestParams();
            if (requestCode == CAMERA_REQUEST_CODE) {
                try {
                    startPhotoZoom(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //   startPhotoZoom();
            }
            if (requestCode == ALBUM_REQUEST_CODE) {
                startActivityForResult(CutForPhoto(data.getData()), 10010);

            }
            if (requestCode == 10010) {
                Glide.with(MyProActivity.this).load(mCutUri).into(circleImageView);
                file = cutfile;

            }
            if (requestCode == 222) {
                if (imagePath != null) {
                    //     Glide.with(MyProActivity.this).load(imagePath).into(circleImageView);
                    file = new File(imagePath);
                }


                //           Bundle extras = data.getExtras();

//                      if (extras != null) {
//                    Bitmap photo = extras.getParcelable("data");
//                    File fileCut = new File(SAVED_IMAGE_DIR_PATH + System.currentTimeMillis() + ".png");
//                    //LogUtil.e("zzf",fileCut.toString());
//                    file = fileCut;
//                    try{
//                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileCut));
//                        photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                        bos.flush();
//                        bos.close();
//                    }catch (IOException e){
//                        e.printStackTrace();
//                    }
//
//                    Drawable drawable = new BitmapDrawable(getResources(), photo);
//
//
//                Glide.with(MyProActivity.this).load(drawable).into(circleImageView);
//                   }
            }
            if (file != null) {
                params.put("file", file);
                LogUtil.i("开始上传文件");
                MultipartRequest request = new MultipartRequest(Request.Method.POST, params, Constants.plus(Constants.UPLOADFILE_URL), new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {
                        LogUtil.i(s);
                        HeadImageBean headImageBean = gson.fromJson(s, HeadImageBean.class);
                        if (headImageBean != null && headImageBean.getData() != null) {

                            compath = headImageBean.getData().getImgPath();
                            selfAvatarPath = headImageBean.getData().getImgUrl();
                            infoData.getPerson().setSelf_avatar_path(selfAvatarPath);
                            //发送事件
                            EventBus.getDefault().post(new StringEvent(selfAvatarPath, 99));
                            Message message = Message.obtain();
                            message.what = 1;
                            handler.sendMessage(message);
                            commitSelf(Constants.plus(Constants.MODIFYY_IMAGE), "self_Avatar_path", compath);


                            //初始化参数修改头像
                            TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
                            param.setFaceUrl(selfAvatarPath);

                            TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
                                @Override
                                public void onError(int code, String desc) {
                                    //错误码 code 和错误描述 desc，可用于定位请求失败原因
                                    //错误码 code 列表请参见错误码表
                                    LogUtil.i("modifyProfile failed: " + code + " desc" + desc);
                                }

                                @Override
                                public void onSuccess() {
                                    LogUtil.i("modifyProfile succ");
                                }
                            });

                            DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
                        }
                        //LogUtil.e("zzf",s);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //LogUtil.e("zzf",volleyError.toString());
                    }
                }
                );

                MyApplication.getHttpQueues().add(request);
            }

        } else if (resultCode == 13) {
            String sign = data.getStringExtra("sign");
            if (sign != null && sign.length() > 0) {
                tv_sign.setText(sign);
            } else {
                tv_sign.setText("未填写");
            }
        }

    }


    public void photoAlbum() {

//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, ALBUM_REQUEST_CODE);


        Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(albumIntent, ALBUM_REQUEST_CODE);

    }

    private Intent CutForPhoto(Uri uri) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            //随便命名一个

            cutfile = new File(AppUtils.getDiskCachePath(MyProActivity.this) + "/" + System.currentTimeMillis() + ".png");
            LogUtil.i(cutfile.getAbsolutePath());
            if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            //Log.d(TAG, "CutForPhoto: "+cutfile);
            outputUri = Uri.fromFile(cutfile);
            mCutUri = outputUri;
            //Log.d(TAG, "mCameraUri: "+mCutUri);
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", 500); //200dp
            intent.putExtra("outputY", 500);
            intent.putExtra("scale", true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
//            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String imagePath;

    public void startPhotoZoom(Uri uri) throws IOException {
        imagePath = AppUtils.getDiskCachePath(MyProActivity.this) + "/" + System.currentTimeMillis() + "cut.png";

        cutfile = new File(imagePath);
        if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
            cutfile.delete();
        }
        try {
            cutfile.createNewFile();
        } catch (Exception e) {
        }


        //Uri imageUri = Uri.parse(SAVED_IMAGE_DIR_PATH + System.currentTimeMillis() + ".png");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cutfile));//剪切后存文件
        //  intent.putExtra("return-data", true);
        intent.putExtra("return-data", false);//解决大图片oom
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());


        startActivityForResult(intent, 222);


    }


}
