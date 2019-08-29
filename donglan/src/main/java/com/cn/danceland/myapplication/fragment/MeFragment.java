package com.cn.danceland.myapplication.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.ConsultListActivity;
import com.cn.danceland.myapplication.activity.MessageActivity;
import com.cn.danceland.myapplication.activity.MotionDataActivity;
import com.cn.danceland.myapplication.activity.MyConsumeActivity;
import com.cn.danceland.myapplication.activity.MyProActivity;
import com.cn.danceland.myapplication.activity.MyShopActivity;
import com.cn.danceland.myapplication.activity.RecommendListActivity;
import com.cn.danceland.myapplication.activity.SettingActivity;
import com.cn.danceland.myapplication.activity.UserHomeActivity;
import com.cn.danceland.myapplication.activity.UserListActivity;
import com.cn.danceland.myapplication.activity.UserSelfHomeActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.evntbus.EventConstants;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.shouhuan.activity.WearFitActivity;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.UIUtils;
import com.cn.danceland.myapplication.view.DongLanTransparentTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.leolin.shortcutbadger.ShortcutBadger;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    private ImageView iv_avatar;
    private Data mInfo;
    private TextView tv_dyn;
    private TextView tv_guanzhu, tv_message;
    private TextView tv_fans;
    Badge badge;

    private DongLanTransparentTitleView dongLanTitleView;
    private ImageView header_background_iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册event事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View initViews() {

        View v = View.inflate(mActivity, R.layout.fragment_me, null);
        v.findViewById(R.id.ll_setting).setOnClickListener(this);
        v.findViewById(R.id.ll_my_msg).setOnClickListener(this);
        v.findViewById(R.id.ll_my_dyn).setOnClickListener(this);
        v.findViewById(R.id.ll_my_guanzhu).setOnClickListener(this);
        v.findViewById(R.id.ll_my_fans).setOnClickListener(this);
        v.findViewById(R.id.ll_my_data).setOnClickListener(this);
        v.findViewById(R.id.ll_my_shop).setOnClickListener(this);
        v.findViewById(R.id.ll_my_xiaofei).setOnClickListener(this);
        v.findViewById(R.id.ll_my_shouhuan).setOnClickListener(this);
        v.findViewById(R.id.ll_consult).setOnClickListener(this);//我想咨询
        v.findViewById(R.id.ll_recommend).setOnClickListener(this);//我想推荐



        dongLanTitleView = v.findViewById(R.id.title);
        ImageView more_iv = dongLanTitleView.findViewById(R.id.iv_more);
        header_background_iv = v.findViewById(R.id.header_background_iv);
        header_background_iv = (ImageView) UIUtils.setViewRatio(mActivity, header_background_iv, (float) 187.5, 110);

        tv_dyn = v.findViewById(R.id.tv_dyn);
        tv_guanzhu = v.findViewById(R.id.tv_gauzhu_num);
        tv_fans = v.findViewById(R.id.tv_fans);
        iv_avatar = v.findViewById(R.id.iv_avatar);
        tv_message = v.findViewById(R.id.tv_message);

        more_iv.setVisibility(View.VISIBLE);
        more_iv.setImageDrawable(getResources().getDrawable(R.drawable.img_edit));
        more_iv.setOnClickListener(this);

        iv_avatar.setOnClickListener(this);

        badge = new QBadgeView(mActivity).bindTarget(tv_message);
        return v;
    }


    @Override
    public void initData() {
        //获取本地用户信息缓存
        mInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        LogUtil.i(mInfo.getPerson().getSelf_avatar_path());
        //设置关注数
        //  LogUtil.i(mInfo.getFollowNumber()+"");
        tv_guanzhu.setText(SPUtils.getInt(Constants.MY_FOLLOWS, 0) + "");
        //设置粉丝数
        tv_fans.setText(SPUtils.getInt(Constants.MY_FANS, 0) + "");
        //设置动态数
        tv_dyn.setText(SPUtils.getInt(Constants.MY_DYN, 0) + "");
        //设置头像
        RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);

        Glide.with(mActivity).load(mInfo.getPerson().getSelf_avatar_path()).apply(options).into(iv_avatar);
        dongLanTitleView.setTitle(mInfo.getPerson().getNick_name());

    }


    //更新用户信息
    private void updateUserInfo(Data mInfo) {
        //设置关注数
        tv_guanzhu.setText(SPUtils.getInt(Constants.MY_FOLLOWS, 0) + "");
        //设置粉丝数
        tv_fans.setText(SPUtils.getInt(Constants.MY_FANS, 0) + "");
        //设置动态数
        tv_dyn.setText(SPUtils.getInt(Constants.MY_DYN, 0) + "");
        //设置头像
        RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
        Glide.with(mActivity).load(mInfo.getPerson().getSelf_avatar_path()).apply(options).into(iv_avatar);
        dongLanTitleView.setTitle(mInfo.getPerson().getNick_name());

    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        //  LogUtil.i("收到消息" + event.getEventCode());

        if (99 == event.getEventCode()) {
            String msg = event.getMsg();
            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
            Glide.with(mActivity).load(msg).apply(options).into(iv_avatar);

        }
        if (100 == event.getEventCode()) {
            dongLanTitleView.setTitle(event.getMsg());
        }

        if (101 == event.getEventCode()) {
            if (Integer.valueOf(event.getMsg()) > 0) {
                badge.setBadgeNumber(Integer.valueOf(event.getMsg())).setBadgeGravity(Gravity.END | Gravity.TOP);
            } else {
                badge.hide(false);
            }

        }
//        if(102 == event.getEventCode()){
//            new QBadgeView(mActivity).bindTarget(tv_message).setBadgeNumber(Integer.valueOf(event.getMsg())).setBadgeGravity(Gravity.END);
//        }

        switch (event.getEventCode()) {
            case EventConstants.ADD_DYN:  //设置动态数+1
                LogUtil.i("动态加1");
                //mInfo.setDynMsgNumber(mInfo.getDynMsgNumber() + 1);
                //   DataInfoCache.saveOneCache(mInfo, Constants.MY_INFO);
                SPUtils.setInt(Constants.MY_DYN, SPUtils.getInt(Constants.MY_DYN, 0) + 1);
                tv_dyn.setText(SPUtils.getInt(Constants.MY_DYN, 0) + "");
                break;
            case EventConstants.DEL_DYN:
                //设置动态数-1
//                mInfo.setDynMsgNumber(mInfo.getDynMsgNumber() - 1);
//                tv_dyn.setText(mInfo.getDynMsgNumber() + "");
//                DataInfoCache.saveOneCache(mInfo, Constants.MY_INFO);
                SPUtils.setInt(Constants.MY_DYN, SPUtils.getInt(Constants.MY_DYN, 0) - 1);
                tv_dyn.setText(SPUtils.getInt(Constants.MY_DYN, 0) + "");

                break;
            case EventConstants.ADD_GUANZHU:
                LogUtil.i("设置关注数+1");
                //设置关注数+1
//                mInfo.setFollowNumber(mInfo.getFollowNumber() + 1);
//                tv_guanzhu.setText(mInfo.getFollowNumber() + "");
//                DataInfoCache.saveOneCache(mInfo, Constants.MY_INFO);
                SPUtils.setInt(Constants.MY_FOLLOWS, SPUtils.getInt(Constants.MY_FOLLOWS, 0) + 1);
                tv_guanzhu.setText(SPUtils.getInt(Constants.MY_FOLLOWS, 0) + "");

                break;
            case EventConstants.DEL_GUANZHU:
                LogUtil.i("设置关注数-1");
                //设置关注数-1
//                mInfo.setFollowNumber(mInfo.getFollowNumber() - 1);
//                tv_guanzhu.setText(mInfo.getFollowNumber() + "");
//                DataInfoCache.saveOneCache(mInfo, Constants.MY_INFO);
                SPUtils.setInt(Constants.MY_FOLLOWS, SPUtils.getInt(Constants.MY_FOLLOWS, 0) - 1);
                tv_guanzhu.setText(SPUtils.getInt(Constants.MY_FOLLOWS, 0) + "");
                break;

            case EventConstants.UPDATE_FANS:

                //设置粉丝数
                // tv_fans.setText(mInfo.getFansNum() + "");
                //      DataInfoCache.saveOneCache(mInfo, Constants.MY_INFO);

                //    SPUtils.setInt(Constants.MY_FOLLOWS,SPUtils.getInt(Constants.MY_FOLLOWS,0));
                tv_fans.setText(SPUtils.getInt(Constants.MY_FANS, 0) + "");

                break;

            case EventConstants.UPDATE_USER_INFO:
                Data myinfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);

                updateUserInfo(myinfo);

                //更新聊天头像和昵称
//                PreferenceManager.getInstance().setCurrentUserNick(myinfo.getPerson().getNick_name());
//                PreferenceManager.getInstance().setCurrentUserAvatar(myinfo.getPerson().getSelf_avatar_path());


                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_setting://设置页面
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            case R.id.iv_more://编辑资料页面
                Intent intent = new Intent(mActivity, MyProActivity.class);
                startActivityForResult(intent, 119);
                break;
            case R.id.ll_my_msg://我的消息
                ShortcutBadger.removeCount(mActivity.getApplicationContext());
                Intent intent1 = new Intent(mActivity, MessageActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_my_dyn://我的动态
                startActivity(new Intent(mActivity, UserHomeActivity.class).putExtra("id", SPUtils.getString(Constants.MY_USERID, null)).putExtra("isdyn", true));
                break;
            case R.id.ll_my_guanzhu://我的关注
                startActivity(new Intent(mActivity, UserListActivity.class).putExtra("id", SPUtils.getString(Constants.MY_USERID, null)).putExtra("type", 1));
                break;
            case R.id.ll_my_fans://我的粉丝
                startActivity(new Intent(mActivity, UserListActivity.class).putExtra("id", SPUtils.getString(Constants.MY_USERID, null)).putExtra("type", 2));
                break;
            case R.id.iv_avatar://头像
                startActivity(new Intent(mActivity, UserSelfHomeActivity.class).putExtra("id", SPUtils.getString(Constants.MY_USERID, null)));
                break;
            case R.id.ll_my_data://运动数据
                Intent intent2 = new Intent(mActivity, MotionDataActivity.class);
                startActivity(intent2);
//                Intent intent2 = new Intent(mActivity, FitnessTestActivity.class);//体测数据
//                startActivity(intent2);
                break;
            case R.id.ll_my_shop://我的门店
                mInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
                LogUtil.i(mInfo.getPerson().getDefault_branch());
                if (TextUtils.isEmpty(mInfo.getPerson().getDefault_branch())) {
                    ToastUtils.showToastShort("请先加入一个门店");
                    return;
                }
                startActivity(new Intent(mActivity, MyShopActivity.class));
                break;
            case R.id.ll_my_xiaofei://我的消费
                startActivity(new Intent(mActivity, MyConsumeActivity.class));
                break;
            case R.id.ll_my_shouhuan://我的手环
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    startActivity(new Intent(mActivity, WearFitActivity.class));
                } else {
                    ToastUtils.showToastShort("您的手机Android版本过低，手环功能需要Android4.4版本以上才能使用");
                }
                break;
            case R.id.ll_consult://我想咨询
                startActivity(new Intent(mActivity, ConsultListActivity.class));
                break;
            case R.id.ll_recommend://我想推荐
                startActivity(new Intent(mActivity, RecommendListActivity.class));
                break;
            default:
                break;
        }
    }
}
