package com.cn.danceland.myapplication.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.app.AppManager;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.HeadImageBean;
import com.cn.danceland.myapplication.bean.RequsetFindUserBean;
import com.cn.danceland.myapplication.bean.bca.bcaresult.BcaResult;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.PictureUtil;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.UIUtils;
import com.cn.danceland.myapplication.utils.multipartrequest.MultipartRequest;
import com.cn.danceland.myapplication.utils.multipartrequest.MultipartRequestParams;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by feng on 2018/4/12.
 */

public class BodyZongHeActivity extends BaseActivity {
    private static final int MSG_REFRESH_DATA = 0;//刷新数据
    RelativeLayout rl_01, rl_02, rl_03;
    Uri uri;
    String cameraPath;
    public static String SAVED_IMAGE_DIR_PATH =
            Environment.getExternalStorageDirectory().getPath()
                    + "/DCIM/camera/";// 拍照路径
    Gson gson;
    String num;//判断第几个图片
    HashMap<Integer, String> numMap;
    ImageView img_01, img_02, img_03;
    DongLanTitleView body_zonghe_title;
    List<BcaResult> resultList;
    EditText et_content;
    LinearLayout btn_commit;
    private boolean isClick = true;
    private RequsetFindUserBean.Data requsetInfo;//前面搜索到的对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodyzonghe);
        AppManager.getAppManager().addActivity(this);
        initHost();
        initView();
    }

    private void initHost() {
        resultList = (List<BcaResult>) getIntent().getSerializableExtra("resultList");
        requsetInfo = (RequsetFindUserBean.Data) getIntent().getSerializableExtra("requsetInfo");//前面搜索到的对象

        if (resultList == null) {
            resultList = new ArrayList<>();
        }

        numMap = new HashMap<>();

        gson = new Gson();

    }

    private void initView() {
        body_zonghe_title = findViewById(R.id.body_zonghe_title);
        body_zonghe_title.setTitle("体型体态分析");
        et_content = findViewById(R.id.et_content);
        btn_commit = findViewById(R.id.btn_commit);

        et_content.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(10)});//禁止输入表情以及限制输入长度为10
        rl_01 = findViewById(R.id.rl_01);
        rl_02 = findViewById(R.id.rl_02);
        rl_03 = findViewById(R.id.rl_03);

        img_01 = findViewById(R.id.img_01);
        img_02 = findViewById(R.id.img_02);
        img_03 = findViewById(R.id.img_03);

        rl_01 = (RelativeLayout) UIUtils.setViewRatio(BodyZongHeActivity.this, rl_01, 280, 350);
        rl_02 = (RelativeLayout) UIUtils.setViewRatio(BodyZongHeActivity.this, rl_02, 280, 350);
        rl_03 = (RelativeLayout) UIUtils.setViewRatio(BodyZongHeActivity.this, rl_03, 280, 350);
        img_01 = (ImageView) UIUtils.setViewRatio(BodyZongHeActivity.this, img_01, 280, 350);
        img_02 = (ImageView) UIUtils.setViewRatio(BodyZongHeActivity.this, img_02, 280, 350);
        img_03 = (ImageView) UIUtils.setViewRatio(BodyZongHeActivity.this, img_03, 280, 350);
        setOnClick();

    }

    private void setOnClick() {
        rl_01.setOnClickListener(onClickListener);
        rl_02.setOnClickListener(onClickListener);
        rl_03.setOnClickListener(onClickListener);
        img_01.setOnClickListener(onClickListener);
        img_02.setOnClickListener(onClickListener);
        img_03.setOnClickListener(onClickListener);
        btn_commit.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtil.i("num-" + num);
            LogUtil.i("numMap.size()-" + numMap.size());
            if (isClick) {
                switch (v.getId()) {
                    case R.id.rl_01:
                        num = "1";
                        getPermission();
                        break;
                    case R.id.rl_02:
                        num = "2";
                        getPermission();
                        break;
                    case R.id.rl_03:
                        num = "3";
                        getPermission();
                        break;
                    case R.id.img_01:
                        num = "1";
                        getPermission();
                        break;
                    case R.id.img_02:
                        num = "2";
                        getPermission();
                        break;
                    case R.id.img_03:
                        num = "3";
                        getPermission();
                        break;
                    case R.id.btn_commit:
                        if (numMap.size() != 3) {
                            ToastUtils.showToastShort("请先拍照！");
                        } else {
                            save();
                        }
                        break;
                }
            } else {
                ToastUtils.showToastShort("正在提交图片，请稍后...");
            }
        }
    };

    private void getPermission() {
        cameraPath = SAVED_IMAGE_DIR_PATH + System.currentTimeMillis() + ".png";
        String floatLayerImgType = "1";//浮层图片 1男正面 2男反面 3男侧面 4女正面 5女反面 6女侧面
        Data myInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);

        switch (num) {
            case "1"://正面
                if (myInfo.getPerson().getGender().equals("1")) {//1男
                    floatLayerImgType = "1";
                } else {
                    floatLayerImgType = "4";
                }
                break;
            case "2"://侧面
                if (myInfo.getPerson().getGender().equals("1")) {//1男
                    floatLayerImgType = "3";
                } else {
                    floatLayerImgType = "6";
                }
                break;
            case "3"://背面
                if (myInfo.getPerson().getGender().equals("1")) {//1男
                    floatLayerImgType = "2";
                } else {
                    floatLayerImgType = "5";
                }
                break;
        }
        if (PermissionsUtil.hasPermission(BodyZongHeActivity.this, Manifest.permission.CAMERA)) {
            isClick = false;
            //有权限
//            takePhoto();

            startActivityForResult(new Intent(BodyZongHeActivity.this, FloatingLayerCameraActivity.class)
                            .putExtra("isFloatLayer", "1")//isFloatLayer 是否有浮层 1有
                            .putExtra("floatLayerImgType", floatLayerImgType)//浮层图片 1男正面 2男反面 3男侧面 4女正面 5女反面 6女侧面
                            .putExtra("cameraPath", cameraPath)//图片保存地址
                    , 1);
        } else {
            final String finalFloatLayerImgType = floatLayerImgType;
            PermissionsUtil.requestPermission(BodyZongHeActivity.this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
//                    takePhoto();
                    startActivityForResult(new Intent(BodyZongHeActivity.this, FloatingLayerCameraActivity.class)
                                    .putExtra("isFloatLayer", "1")//isFloatLayer 是否有浮层 1有
                                    .putExtra("floatLayerImgType", finalFloatLayerImgType)//浮层图片 1男正面 2男反面 3男侧面 4女正面 5女反面 6女侧面
                                    .putExtra("cameraPath", cameraPath)//图片保存地址
                            , 1);
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    //用户拒绝了申请
                    ToastUtils.showToastShort("没有权限");
                }
            }, new String[]{Manifest.permission.CAMERA}, false, null);
        }
    }

    /**
     * @方法说明:新增体测分析
     **/
    public void save() {
        String content="";
        if (et_content.getText() != null) {
            content=et_content.getText().toString();
        }

        startActivity(new Intent(BodyZongHeActivity.this, BodyTiXingActivity.class)
                .putExtra("resultList",(Serializable)resultList)
                .putExtra("requsetInfo", requsetInfo)
                .putExtra("frontal_path", numMap.get(1))
                .putExtra("side_path", numMap.get(2))
                .putExtra("behind_path", numMap.get(3))
                .putExtra("content", content)
        );
//        BcaAnalysis bcaAnalysis = new BcaAnalysis();
//        bcaAnalysis.setMember_id(Long.valueOf(requsetInfo.getId()));
//        bcaAnalysis.setMember_no(requsetInfo.getMember_no());
//        bcaAnalysis.setFrontal_path(numMap.get(1));//正面照
//        bcaAnalysis.setSide_path(numMap.get(2));//侧面照
//        bcaAnalysis.setBehind_path(numMap.get(3));//背面照
//        bcaAnalysis.setResult(resultList);
//        if (et_content.getText() != null) {
//            bcaAnalysis.setContent(et_content.getText().toString());
//        }
//        final BcaAnalysisRequest request = new BcaAnalysisRequest();
//        request.save(bcaAnalysis, new Response.Listener<JSONObject>() {
//            public void onResponse(JSONObject json) {
//                DLResult<String> result = gson.fromJson(json.toString(), new TypeToken<DLResult<String>>() {
//                }.getType());
//                if (result.isSuccess()) {
//                    LogUtil.i("" + json.toString());
//                    ToastUtils.showToastShort("提交成功！");
////                    startActivity(new Intent(BodyZongHeActivity.this, BodyTiXingActivity.class)
////                            .putExtra("requsetInfo", requsetInfo)
////                            .putExtra("saveId", result.getData()));
//                } else {
//                    ToastUtils.showToastShort("保存数据失败,请检查手机网络！");
//                }
//            }
//        });

    }

    private void takePhoto() {

        // 指定相机拍摄照片保存地址
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            cameraPath = SAVED_IMAGE_DIR_PATH + System.currentTimeMillis() + ".png";
            Intent intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            String out_file_path = SAVED_IMAGE_DIR_PATH;
            File dir = new File(out_file_path);
            if (!dir.exists()) {
                dir.mkdirs();
            } // 把文件地址转换成Uri格式
            if (PictureUtil.getSDKV() < 24) {
                uri = Uri.fromFile(new File(cameraPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);
            } else {
                // 设置系统相机拍摄照片完成后图片文件的存放地址
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, cameraPath);
                uri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);
            }
            //uri = Uri.fromFile(new File(cameraPath));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i("requestCode--" + requestCode);
        LogUtil.i("resultCode--" + resultCode);
        if (resultCode == 1) {
            if (cameraPath != null) {
                String resultPicturePath = data.getStringExtra("picturePath");//是否有浮层 1有
                LogUtil.i("picturePath--");
                if (resultPicturePath != null) {
                    Bitmap bitmap = PictureUtil.FileCompressToBitmap(resultPicturePath);//压缩
                    if (bitmap != null) {
                        LogUtil.i("11--");
                    } else {
                        LogUtil.i("22--");

                    }
                    if ("1".equals(num)) {
                        rl_01.setVisibility(View.GONE);
                        img_01.setVisibility(View.VISIBLE);
                        Glide.with(BodyZongHeActivity.this).load(resultPicturePath).into(img_01);
                    } else if ("2".equals(num)) {
                        rl_02.setVisibility(View.GONE);
                        img_02.setVisibility(View.VISIBLE);
                        Glide.with(BodyZongHeActivity.this).load(resultPicturePath).into(img_02);
                    } else if ("3".equals(num)) {
                        rl_03.setVisibility(View.GONE);
                        img_03.setVisibility(View.VISIBLE);
                        Glide.with(BodyZongHeActivity.this).load(resultPicturePath).into(img_03);
                    }
                    //上传图片
                    MultipartRequestParams params = new MultipartRequestParams();
                    if (bitmap != null) {
                        File file = new File(resultPicturePath);
//                    File file = PictureUtil.SaveBitmapFile(bitmap, resultPicturePath);

                        params.put("file", file);
                        LogUtil.i("上传图片大小--" + file.length());
                        LogUtil.i("上传图片地址--" + Constants.BCAUPLOAD);

                        MultipartRequest request = new MultipartRequest(Request.Method.POST, params, Constants.plus(Constants.BCAUPLOAD), new Response.Listener<String>() {

                            @Override
                            public void onResponse(String s) {
                                LogUtil.i("上传图片接收--" + s.toString());
                                HeadImageBean headImageBean = gson.fromJson(s, HeadImageBean.class);
                                if (headImageBean != null && headImageBean.getData() != null) {
                                    String imgPath = headImageBean.getData().getImgPath();
                                    String imgUrl = headImageBean.getData().getImgUrl();
                                    Message message = new Message();
                                    message.what = MSG_REFRESH_DATA;
                                    message.obj = imgPath;
                                    handler.sendMessage(message);
                                    ToastUtils.showToastShort("上传图片成功！");
                                    LogUtil.i("上传图片成功");
                                } else {
                                    LogUtil.i("上传图片失败");
                                    ToastUtils.showToastShort("上传图片失败！请重新拍照！");
                                    isClick = true;
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                isClick = true;
                                ToastUtils.showToastShort("上传图片失败！请重新拍照！");
                                LogUtil.i("上传图片失败--" + volleyError.toString());
                            }
                        }
                        );
                        request.setRetryPolicy(new DefaultRetryPolicy(30000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MyApplication.getHttpQueues().add(request);
                    }
                }
            }
        } else {
            isClick = true;
        }
    }

    InputFilter inputFilter=new InputFilter() {

        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");
        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            Matcher matcher=  pattern.matcher(charSequence);
            if(!matcher.find()){
                return null;
            }else{
//                ToastUtils.showToastShort("只能输入汉字、英文、数字");
                return "";
            }

        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_REFRESH_DATA:
                    isClick = true;
                    if ("1".equals(num)) {
                        numMap.put(1, (String) message.obj);
                    } else if ("2".equals(num)) {
                        numMap.put(2, (String) message.obj);
                    } else if ("3".equals(num)) {
                        numMap.put(3, (String) message.obj);
                    }
                    LogUtil.i("imgUrl--" + (String) message.obj);
                    LogUtil.i("2num-" + num);
                    LogUtil.i("2numMap.size()-" + numMap.size());
                    break;
                default:
                    break;
            }
        }
    };
}
