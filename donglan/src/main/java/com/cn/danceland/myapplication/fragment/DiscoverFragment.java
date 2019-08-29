package com.cn.danceland.myapplication.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.AddFriendsActivity;
import com.cn.danceland.myapplication.activity.PublishActivity;
import com.cn.danceland.myapplication.adapter.TabAdapter;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.evntbus.IntEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.AutoLocatedPopup;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import org.greenrobot.eventbus.EventBus;

import static android.R.attr.value;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * 发现页面
 */
public class DiscoverFragment extends BaseFragment {
    public  String[] TITLES = new String[]{"精选动态", "关注动态"};
    private ViewPager mViewPager;
  //  private TabPageIndicator mTabPageIndicator;
    private TabAdapter mAdapter;
    private ImageButton iv_photo;
    private AutoLocatedPopup autoLocatedPopup;

    public int curentpage = 0;

    @Override
    public View initViews() {
        View v = View.inflate(mActivity, R.layout.fragment_discover, null);
        v.findViewById(R.id.iv_add_friends).setOnClickListener(this);
      //  autoLocatedPopup = new AutoLocatedPopup(mActivity);
        iv_photo = v.findViewById(R.id.iv_photo);
        iv_photo.setOnClickListener(this);
        mViewPager = v.findViewById(R.id.id_viewpager);
     //   mTabPageIndicator = v.findViewById(R.id.id_indicator);
        mAdapter = new TabAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);
        //mViewPager.setOffscreenPageLimit(3);
//        mTabPageIndicator.setViewPager(mViewPager, 0);
//
//        mTabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                curentpage = position;
//                EventBus.getDefault().post(new IntEvent(position, 8901));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        MagicIndicator magicIndicator = (MagicIndicator) v.findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(commonNavigatorAdapter);

        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
      mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

          }

          @Override
          public void onPageSelected(int position) {
              curentpage = position;
              EventBus.getDefault().post(new IntEvent(position, 8901));
          }

          @Override
          public void onPageScrollStateChanged(int state) {

          }
      });
        return v;
    }

    private TextView badgeTextView;
    private BadgePagerTitleView badgePagerTitleView;
    private CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {


        @Override
        public int getCount() {
            return TITLES == null ? 0 : TITLES.length;
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int index) {
            badgePagerTitleView = new BadgePagerTitleView(context);

            SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
            simplePagerTitleView.setText(TITLES[index]);

            simplePagerTitleView.setNormalColor(getResources().getColor(R.color.colorGray22));
            simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.home_enter_total_text_color));
            simplePagerTitleView.setTextSize(14);
            simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //badgePagerTitleView.setBadgeView(null); // cancel badge when click tab
                    mViewPager.setCurrentItem(index);
                }
            });

            badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
//


            return badgePagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(context);
         //   indicator.setColors(Color.parseColor("#40c4ff"));
            indicator.setColors(getResources().getColor(R.color.color_dl_yellow));
            return indicator;
        }
    };

//    private void initMagicIndicator1() {
//        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
//        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
//        commonNavigator.setAdjustMode(true);
//        commonNavigator.setAdapter(commonNavigatorAdapter);
//
//        magicIndicator.setNavigator(commonNavigator);
//        ViewPagerHelper.bind(magicIndicator, mViewPager);
//    }
//    private void showListDialog() {
//        final String[] items = {"发布图文", "发布视频"};
//        AlertDialog.Builder listDialog =
//                new AlertDialog.Builder(mActivity);
//        //listDialog.setTitle("我是一个列表Dialog");
//        listDialog.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                switch (which) {
//                    case 0:
//                        //ToastUtils.showToastShort("发布动态");
//                        Intent intent = new Intent(mActivity, PublishActivity.class);
//                        intent.putExtra("isPhoto", "0");
//                        startActivity(intent);
//                        break;
//                    case 1:
//                        Intent intent1 = new Intent(mActivity, PublishActivity.class);
//                        intent1.putExtra("isPhoto", "1");
//                        startActivity(intent1);
//                        //ToastUtils.showToastShort("发布视频");
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        });
//        listDialog.show();
//    }


    @Override
    public void initData() {

    }

//    //弹出下拉框
//    protected void showDroppyMenu() {
//        droppyMenu.show();
//
//    }

    //    DroppyMenuPopup droppyMenu;
//
//    //绑定下拉框
//    private void initDroppyMenu(ImageButton btn) {
//        DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(mActivity, btn);
//        droppyBuilder.addMenuItem(new DroppyMenuItem("发布动态"))
//                .addSeparator()
//                .addMenuItem(new DroppyMenuItem("发布视频"))
//                .setOnDismissCallback(this)
//                .setOnClick(this)
//                .setPopupAnimation(new DroppyFadeInAnimation())
//                .triggerOnAnchorClick(false);
//
//        droppyMenu = droppyBuilder.build();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_friends://添加好友

                startActivity(new Intent(mActivity, AddFriendsActivity.class));
                break;
            case R.id.iv_photo://发布动态
                Data data= (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);

                if (TextUtils.isEmpty(data.getPerson().getDefault_branch())){
                    ToastUtils.showToastShort("请先加人一个门店");
                    return;
                }

//                Intent intent = new Intent(mActivity,  QRCodeActivity.class);
//               startActivity(intent);
//
//                Intent intent = new Intent(mActivity, RecommendActivity.class);
//               startActivity(intent);

//
//                EMClient.getInstance().login("dlkj0002","q",new EMCallBack() {//回调
//                    @Override
//                    public void onSuccess() {
//                        EMClient.getInstance().groupManager().loadAllGroups();
//                        EMClient.getInstance().chatManager().loadAllConversations();
//                        Log.d("taginfo", "登录聊天服务器成功！");
//                        Data myinfo= (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
//                      //  EaseUserUtils.setUserAvatar();
////                        EaseUI.getInstance().getUserProfileProvider().getUser("dlkj0002").setAvatar(myinfo.getSelf_avatar_path());
////                        EaseUI.getInstance().getUserProfileProvider().getUser("dlkj0002").setNickname(myinfo.getNick_name());
//                        PreferenceManager.getInstance().setCurrentUserNick(myinfo.getNick_name());
//                        PreferenceManager.getInstance().setCurrentUserName("dlkj0002");
//                        PreferenceManager.getInstance().setCurrentUserAvatar(myinfo.getSelf_avatar_path());
//                        startActivity(new Intent(mActivity,MyChatActivity.class).putExtra("userId","dlkj0001").putExtra("chatType", EMMessage.ChatType.Chat));
//
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//
//                    }
//
//                    @Override
//                    public void onError(int code, String message) {
//                        Log.d("taginfo", "登录聊天服务器失败！");
//                    }
//                });


                // autoLocatedPopup.showPopupWindow(v);
                Intent intent = new Intent(mActivity, PublishActivity.class);
                startActivity(intent);
                break;
            case value:
                break;
            default:
                break;
        }
    }


}
