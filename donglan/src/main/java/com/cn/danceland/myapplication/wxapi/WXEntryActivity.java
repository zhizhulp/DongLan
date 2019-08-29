package com.cn.danceland.myapplication.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

/**
 * Created by yxx on 2018-10-08.
 */

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    /**
     * 微信登录相关
     */
    private IWXAPI api;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWechat();
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean result =  api.handleIntent(getIntent(), this);
            if(!result){
                LogUtil.i("参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化微信支付api
     */
    private void initWechat() {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WEIXIN, true);//通过WXAPIFactory工厂获取IWXApI的示例
        api.registerApp(Constants.APP_ID_WEIXIN); //将应用的appid注册到微信
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data,this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.i("baseReq:"+ baseReq);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.i("baseResp:"+baseResp);
        LogUtil.i("baseResp:"+baseResp.errStr+","+baseResp.openId+","+baseResp.transaction+","+baseResp.errCode);
        String result = "";
        switch(baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result ="发送成功";
LogUtil.i("result--"+result);
//                showMsg(1,result);
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                LogUtil.i("result--"+result);
//              showMsg(2,result);
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                LogUtil.i("result--"+result);
//               showMsg(1,result);
                finish();
                break;
            default:
                result = "发送返回";
                LogUtil.i("result--"+result);
//     showMsg(0,result);
                finish();
                break;
        }

    }

}