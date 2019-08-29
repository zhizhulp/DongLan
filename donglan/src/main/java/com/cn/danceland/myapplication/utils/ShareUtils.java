package com.cn.danceland.myapplication.utils;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.RequestShareBean;
import com.cn.danceland.myapplication.bean.ShareBean;
import com.cn.danceland.myapplication.bean.ShareInfoFromServiceBean;
import com.google.gson.Gson;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2018/12/26 11:55
 * Email:644563767@qq.com
 * 分享
 */


public class ShareUtils {
    Activity context;
    private ShareBoardlistener shareBoardlistener;
    private UMWeb web;
    private String bus_id;
    private int type;
    private static ShareUtils shareUtils = null;

//    byte SHARE_TYPE_GROUP_COURSE_DETAIL = 1;// 团体私教课程详情
//    byte SHARE_TYPE_COURSE_DETAIL = 2;// 一对一课程详情
//    byte SHARE_TYPE_HOME_NEWS = 3;// 首页新闻
//    byte SHARE_TYPE_DISCOVERY = 4;// 发现精选
//    byte SHARE_TYPE_BRANCH_ATTENTION = 5;// 门店动态
//    byte SHARE_TYPE_BCA = 6;// 体测
//    byte SHARE_TYPE_BRANCH_DETAIL = 7;// 门店详情
//    byte SHARE_TYPE_PERSON_HOME = 8;// 个人主页
//    byte SHARE_TYPE_BANNER_HOME = 9;// 首页轮播图
//    byte SHARE_TYPE_COURSE = 10;// 课表
//    byte SHARE_TYPE_FREE_COURSE = 11;// 团课明细
//    byte SHARE_TYPE_BANNER_BRANCH = 12;// 门店轮播图
//    byte SHARE_TARGET_QQ = 2;// QQ
//    byte SHARE_TARGET_WX = 1;// 微信

    public ShareUtils(Activity context) {
        this.context = context;

    }

    public static ShareUtils create(Activity context) {
//        if (shareUtils == null) {
//            synchronized (ShareUtils.class) {
//                if (shareUtils == null) {
//                    shareUtils = new ShareUtils(context);
//                }
//            }
//
//        }
        shareUtils = new ShareUtils(context);

        return shareUtils;

    }

    /**
     * 分享网页
     * <p>
     * type//类型[
     * 1:团体私教课程详情,
     * 2:一对一课程详情,
     * 4:发现精选,
     * 5:门店动态,
     * 6:体测,
     * 7:门店详情
     * ,8:个人主页],bus_id[当前业务对应的主键]
     * 9;// 轮播图
     * 10;// 课表
     * 11;// 团课明
     */
    private void shareWeb(final ShareBean info) {

        web = new UMWeb(info.url);
        web.setTitle(info.title);//标题
        web.setDescription(info.description);
        UMImage image;
        if (info.img_url != null) {
            image = new UMImage(context, info.img_url);//网络图片
        } else {
            image = new UMImage(context, R.mipmap.ic_launcher);
        }

        web.setThumb(image);  //缩略图

        shareBoardlistener = new ShareBoardlistener() {

            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                if (share_media == null) {
                    //根据key来区分自定义按钮的类型，并进行对应的操作
                    LogUtil.i(snsPlatform.mKeyword + "*****" + snsPlatform.mShowWord);

                    if (snsPlatform.mKeyword.equals("umeng_sharebutton_custom")) {
                        ///            Toast.makeText(context, snsPlatform.mKeyword + snsPlatform.mShowWord + "add button success", Toast.LENGTH_LONG).show();
                        qqshareWeb(info);
                    }


                } else {
                    LogUtil.i(share_media.getName() + "*****" + share_media.toString());
                    new ShareAction((Activity) context)
                            .setPlatform(share_media)
                            .setCallback(new UMShareListener() {
                                @Override
                                public void onStart(SHARE_MEDIA share_media) {

                                }

                                @Override
                                public void onResult(SHARE_MEDIA share_media) {
                                    saveShare(1 + "", info.type + "", info.bus_id);
                                }

                                @Override
                                public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                                }

                                @Override
                                public void onCancel(SHARE_MEDIA share_media) {

                                }
                            }).
                            withMedia(web)
                            .share();
                }
            }
        };

        new ShareAction((Activity) context)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)//分享平台
                .addButton("QQ", "umeng_sharebutton_custom", "umeng_socialize_qq", "umeng_socialize_qq")// 自定义按钮
                .setShareboardclickCallback(shareBoardlistener)//面板点击监听器
                .open();

    }

    private void qqshareWeb(final ShareBean info) {
        //   IUiListener     mShareListener = new ShareQQListener(mContext, item.channelName);
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, info.title);
        // params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "hhhhhh");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, info.url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, info.img_url);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "com.cn.danceland.myapplication");
        Tencent mTencent = Tencent.createInstance(Constants.APP_ID_QQ_ZONE, context);
        mTencent.shareToQQ((Activity) context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                LogUtil.i("分享完成");
                saveShare(2 + "", info.type + "", info.bus_id);
            }

            @Override
            public void onError(UiError uiError) {
                LogUtil.i("分享失败");
            }

            @Override
            public void onCancel() {
                LogUtil.i("分享取消");
            }
        });

    }

    private void qqshareImg(final ShareBean shareBean) {
        //   IUiListener     mShareListener = new ShareQQListener(mContext, item.channelName);
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareBean.img_url);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "com.cn.danceland.myapplication");
        Tencent mTencent = Tencent.createInstance(Constants.APP_ID_QQ_ZONE, context);
        mTencent.shareToQQ((Activity) context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                LogUtil.i("分享完成");
                saveShare(2 + "", shareBean.type + "", shareBean.bus_id);
            }

            @Override
            public void onError(UiError uiError) {
                LogUtil.i("分享失败");
            }

            @Override
            public void onCancel() {
                LogUtil.i("分享取消");
            }
        });

    }

    public void shareImg(final ShareBean shareBean) {

        final UMImage image = new UMImage(context, new File(shareBean.img_url));//本地文件


        shareBoardlistener = new ShareBoardlistener() {

            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                if (share_media == null) {
                    //根据key来区分自定义按钮的类型，并进行对应的操作
                    LogUtil.i(snsPlatform.mKeyword + "*****" + snsPlatform.mShowWord);

                    if (snsPlatform.mKeyword.equals("umeng_sharebutton_custom")) {
                        //                 Toast.makeText(context, snsPlatform.mKeyword + snsPlatform.mShowWord + "add button success", Toast.LENGTH_LONG).show();
                        qqshareImg(shareBean);
                    }


                } else {
                    LogUtil.i(share_media.getName() + "*****" + share_media.toString());
                    new ShareAction((Activity) context)
                            .setPlatform(share_media)
                            .setCallback(new UMShareListener() {
                                @Override
                                public void onStart(SHARE_MEDIA share_media) {

                                }

                                @Override
                                public void onResult(SHARE_MEDIA share_media) {
                                    saveShare(1 + "", shareBean.type + "", shareBean.bus_id);
                                }

                                @Override
                                public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                                }

                                @Override
                                public void onCancel(SHARE_MEDIA share_media) {

                                }
                            }).
                            withMedia(image)
                            .share();
                }
            }
        };

        new ShareAction((Activity) context)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)//分享平台
                .addButton("QQ", "umeng_sharebutton_custom", "umeng_socialize_qq", "umeng_socialize_qq")// 自定义按钮
                .setShareboardclickCallback(shareBoardlistener)//面板点击监听器
                .open();


    }


    public void shareWebInfo(ShareBean shareBean) {
        shareWeb(shareBean);
    }


    public void shareWebInfoFromService(final ShareInfoFromServiceBean shareInfoBean) {
        LogUtil.i( new Gson().toJson(shareInfoBean).toString());

        MyJsonObjectRequest myJsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.SHARERECORD_CREATESHARE), new Gson().toJson(shareInfoBean).toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                LogUtil.i(jsonObject.toString());
                RequestShareBean shareBean = new Gson().fromJson(jsonObject.toString(), RequestShareBean.class);
                LogUtil.i(shareBean.getData().url);
                shareBean.getData().bus_id = shareInfoBean.bus_id;
                shareWeb(shareBean.getData());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort("分享失败，请查看网络");
            }
        }
        ) {

        };
        MyApplication.getHttpQueues().add(myJsonObjectRequest);
    }

    public void saveShare(final String target, final String type, final String bus_id) {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.SHARE_RECORD_SAVE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
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
                map.put("target", target);//qq 微信
                map.put("type", type);//
                map.put("bus_id", bus_id);
                return map;
            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }
}
