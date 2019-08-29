package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.ShareBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.ShareUtils;
import com.cn.danceland.myapplication.utils.TintUitls;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.UMShareAPI;
//

/**
 * Created by shy on 2017/11/29 13:19
 * Email:644563767@qq.com
 * 新闻详情页
 */


public class NewsDetailsActivity extends BaseActivity implements View.OnClickListener {
    private String mUrl;
    private ProgressBar mProgress;
    private WebView mWebView;
    private String title;
    private String img_url;
    private String bus_id="";
    private ShareBean shareBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_news_detail, null);
        setContentView(view);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        mUrl = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        img_url = getIntent().getStringExtra("img_url");
        shareBean = (ShareBean) getIntent().getSerializableExtra("shareBean");
        if (shareBean==null){
            findViewById(R.id.tv_share).setVisibility(View.GONE);
        }
        ImageView tv_share =findViewById(R.id.tv_share);
        TintUitls.changeColor(this,R.drawable.dl_share,tv_share,R.color.color_dl_deep_blue);
        findViewById(R.id.tv_colse).setOnClickListener(this);
        findViewById(R.id.tv_share).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        TextView tv_tiltle = findViewById(R.id.tv_tiltle);
        tv_tiltle.setText(title);
        mProgress = findViewById(R.id.myProgressBar);

        mWebView = findViewById(R.id.wv_webview);

        if (!TextUtils.isEmpty(mUrl)) {
            WebSettings settings = mWebView.getSettings();

            settings.setJavaScriptEnabled(true);// 打开js功能
            settings.setBuiltInZoomControls(true);// 显示放大缩小的按钮
            settings.setUseWideViewPort(true);// 双击缩放

            mWebView.setWebChromeClient(new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        mProgress.setVisibility(View.INVISIBLE);
                    } else {
                        if (View.INVISIBLE == mProgress.getVisibility()) {
                            mProgress.setVisibility(View.VISIBLE);
                        }
                        mProgress.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }

            });


            mWebView.setWebViewClient(new WebViewClient() {

                // 监听网页加载结束的事件
                @Override
                public void onPageFinished(WebView view, String url) {
                    //  mProgress.setVisibility(View.GONE);
                }
            });

            mWebView.loadUrl(mUrl);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();// 返回前一个页面
                } else {
                    finish();
                }
                break;
            case R.id.tv_colse:
                finish();
                break;
            case R.id.tv_share://分享
           //     qqshare();
//                UMWeb web = new UMWeb(mUrl);
//                web.setTitle(title);//标题
//                UMImage image;
//                if (img_url!=null){
//                    image = new UMImage(NewsDetailsActivity.this, img_url);//网络图片
//                }else {
//                    image=new UMImage(NewsDetailsActivity.this,R.mipmap.ic_launcher);
//                }
//
//                web.setThumb(image);  //缩略图
//            //    web.setDescription("my description");//描述
//
//
//                new ShareAction(NewsDetailsActivity.this).withText("这是一条分享")
//                        .setDisplayList(SHARE_MEDIA.QQ
//                        , SHARE_MEDIA.WEIXIN
//                        , SHARE_MEDIA.WEIXIN_CIRCLE
//                        , SHARE_MEDIA.WEIXIN_FAVORITE)
//                        .setCallback(new UMShareListener() {
//                            @Override
//                            public void onStart(SHARE_MEDIA share_media) {
//                                LogUtil.i(share_media.toString());
//                            }
//
//                            @Override
//                            public void onResult(SHARE_MEDIA share_media) {
//                                LogUtil.i(share_media.toString());
//                            }
//
//                            @Override
//                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                                LogUtil.i(share_media.toString());
//                            }
//
//                            @Override
//                            public void onCancel(SHARE_MEDIA share_media) {
//                                LogUtil.i(share_media.toString());
//                            }
//                        }).open();

//                ShareUtils shareUtils=new ShareUtils(NewsDetailsActivity.this);
            //    shareUtils.shareWeb(mUrl, img_url, title, 0 ,"");
//                shareBean=new ShareBean();
//                shareBean.url=mUrl;
//                shareBean.img_url=img_url;
//                shareBean.title=title;
//                shareBean.type=0;
                ShareUtils.create(NewsDetailsActivity.this).shareWebInfo(shareBean);
                break;
            default:
                break;
        }

    }

    private void qqshare(){
     //   IUiListener     mShareListener = new ShareQQListener(mContext, item.channelName);
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
       // params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "hhhhhh");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, img_url);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "com.cn.danceland.myapplication");
        Tencent mTencent= Tencent.createInstance(Constants.APP_ID_QQ_ZONE,this);
        mTencent.shareToQQ(this, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                LogUtil.i("分享完成");
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
}
