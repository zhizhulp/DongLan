package com.cn.danceland.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.CollectEntranceActivity;
import com.cn.danceland.myapplication.activity.NewsDetailsActivity;
import com.cn.danceland.myapplication.activity.PaiMingActivity;
import com.cn.danceland.myapplication.activity.UserHomeActivity;
import com.cn.danceland.myapplication.adapter.NewsListviewAdapter;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestCollectBean;
import com.cn.danceland.myapplication.bean.RequestImageNewsDataBean;
import com.cn.danceland.myapplication.bean.RequestNewsDataBean;
import com.cn.danceland.myapplication.bean.RequsetMyPaiMingBean;
import com.cn.danceland.myapplication.bean.ShareBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.UIUtils;
import com.cn.danceland.myapplication.view.ClassicsHeader;
import com.cn.danceland.myapplication.view.NumberAnimTextView;
import com.cn.danceland.myapplication.view.RoundImageView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yxx on 2018-10-18.
 */

public class HomeFragment extends BaseFragment {
    private static final int MSG_REFRESH_VIEW = 0;//刷新界面
    private static final int MSG_REFRESH_DATA = 0x100;//暂无数据
    private SmartRefreshLayout refreshLayout;
    private RecyclerView mRecycler;
    private ProgressDialog dialog;
    private List<RequestNewsDataBean.Data.Items> data = new ArrayList<>();
    private List<RequestImageNewsDataBean.Data> imagelist = new ArrayList<>();
    private NewsListviewAdapter newsListviewAdapter;
    private ViewPager mViewPager;
    private int mCurrentPage = 0;//起始请求页
    private int mCurrentIamgenews = 1;//轮播开始页
    private static final int TOP_NEWS_CHANGE_TIME = 4000;// 顶部新闻切换事件

    private static LinearLayout header_layout;//header布局 头像 姓名 俩按钮
    private CardView meun_cradview;//菜单 粉色整体布局 健身日记 打卡排行layout
    private TextView in_the_cumulative_tv;//进场累计
    private NumberAnimTextView cumulative_num_tv;//进场累计数
    private ImageView fitness_diary_white_iv;//健身日记 header布局 白色按钮
    private ImageView punch_list_white_iv;//打卡排行 header布局 白色按钮
    private ImageView fitness_diary_pink_iv;//打卡排行 header布局 粉色按钮
    private ImageView punch_list_pink_iv;//健身日记 header布局 粉色按钮
    private LinearLayout fitness_diary_pink_ll;//健身日记 菜单 粉色布局
    private LinearLayout punch_list_pink_ll;//打卡排行 菜单 粉色布局
    private ImageView header_background_iv;//打卡排行 菜单 粉色布局
    private ImageView iv_avatar;
    private TextView tv_nick_name;
    private RelativeLayout rl_error;
    private View header_fold_bg_view;
    private View header_fold_bg_view2;

    private View banner_header2;//临时布局 后面看要不要改刷新在某位置

    private int recLen = 99;//进场累计  99-0 0-x
    private int cumulative_num = 0;//进场累计  99-0 0-x
    private boolean isCumulative = false;//进场累计 0-x开关
    private Data mInfo;//登录对象

    private int alphaNum = 0;//透明度
    int i = 255;

    Handler handler3 = new Handler();

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = mViewPager.getCurrentItem();
            if (item < imagelist.size() - 1) {
                item++;
            } else {// 判断是否到达最后一个
                item = 0;
            }
            // Log.d(TAG, "轮播条:" + item);

            mViewPager.setCurrentItem(item);

            mHandler.sendMessageDelayed(Message.obtain(),
                    TOP_NEWS_CHANGE_TIME);

        }
    };

    private Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    // 设置数据
                    mMZBanner.setPages(imagelist, new MZHolderCreator<BannerViewHolder>() {
                        @Override
                        public BannerViewHolder createViewHolder() {
                            return new BannerViewHolder();
                        }
                    });
                    mMZBanner.start();
                    break;
                case MSG_REFRESH_DATA:
                    ToastUtils.showToastShort("暂无更多数据");
                    break;
                default:
                    break;
            }
        }
    };

    private MZBannerView mMZBanner;


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View initViews() {
        EventBus.getDefault().register(this);
        LogUtil.i(Constants.HOST);
        View v = View.inflate(mActivity, R.layout.fragment_home_header_view, null);

        refreshLayout = v.findViewById(R.id.refreshLayout);
        mRecycler = (RecyclerView) v.findViewById(R.id.recycler);

        meun_cradview = v.findViewById(R.id.meun_cradview);
        header_layout = v.findViewById(R.id.header_layout);
        in_the_cumulative_tv = v.findViewById(R.id.in_the_cumulative_tv);
        cumulative_num_tv = v.findViewById(R.id.cumulative_num_tv);
        fitness_diary_white_iv = v.findViewById(R.id.fitness_diary_white_iv);
        punch_list_white_iv = v.findViewById(R.id.punch_list_white_iv);
        fitness_diary_pink_iv = v.findViewById(R.id.fitness_diary_pink_iv);
        punch_list_pink_iv = v.findViewById(R.id.punch_list_pink_iv);
        fitness_diary_pink_ll = v.findViewById(R.id.fitness_diary_pink_ll);
        punch_list_pink_ll = v.findViewById(R.id.punch_list_pink_ll);
        iv_avatar = v.findViewById(R.id.iv_avatar);
        tv_nick_name = v.findViewById(R.id.tv_nick_name);
        header_background_iv = v.findViewById(R.id.header_background_iv);
        rl_error = v.findViewById(R.id.rl_error);

        header_fold_bg_view = v.findViewById(R.id.header_fold_bg_view);
        header_fold_bg_view2 = v.findViewById(R.id.header_fold_bg_view2);
        banner_header2 = v.findViewById(R.id.banner_header2);

        mInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        //设置头像
        RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
        Glide.with(mActivity).load(mInfo.getPerson().getSelf_avatar_path()).apply(options).into(iv_avatar);
        tv_nick_name.setText("Hello " + mInfo.getPerson().getNick_name());

        fitness_diary_white_iv.setOnClickListener(onClickListener);
        punch_list_white_iv.setOnClickListener(onClickListener);
        fitness_diary_pink_ll.setOnClickListener(onClickListener);
        punch_list_pink_ll.setOnClickListener(onClickListener);
        dialog = new ProgressDialog(mActivity);
        dialog.setMessage("加载中……");

        if (newsListviewAdapter == null) {
            newsListviewAdapter = new NewsListviewAdapter(data, mActivity);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setHasFixedSize(true);//刷新最重要的这句
        header_background_iv = (ImageView) UIUtils.setViewRatio(mActivity, header_background_iv, (float) 187.5, 110);

        newsListviewAdapter.setHeaderView(initBanner());
        newsListviewAdapter.setOnItemClickListener(new NewsListviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, List<RequestNewsDataBean.Data.Items> data) {

                LogUtil.i(data.size() + "position" + position);
                setReadNum(data, data.get(position).getId(), position);

                ShareBean shareBean=new ShareBean();
                shareBean.bus_id=data.get(position).getId()+"";
                shareBean.img_url=data.get(position).getImg_url();
                shareBean.title=data.get(position).getTitle();
                shareBean.url=data.get(position).getUrl();
                shareBean.type= 3;// 首页新闻；
                mActivity.startActivity(new Intent(mActivity, NewsDetailsActivity.class)
                        .putExtra("url", data.get(position).getUrl())
                        .putExtra("shareBean", shareBean)
                        .putExtra("title", data.get(position).getTitle())
                        .putExtra("img_url", data.get(position).getImg_url()));

            }
        });
        mRecycler.setAdapter(newsListviewAdapter);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        dialog = new ProgressDialog(mActivity);
        dialog.setMessage("加载中……");

        refreshLayout.setPrimaryColorsId(R.color.home_top_bg_color, R.color.white_color80);//下拉刷新主题颜色 前面背景色 后面图色
//        refreshLayout.setRefreshHeader(new BezierRadarHeader(mActivity).setEnableHorizontalDrag(true));//设置 Header 为 贝塞尔雷达 样式
        ClassicsHeader header=new ClassicsHeader(mActivity);
        header.setHeaderTextColor(getResources().getColor(R.color.white));
        refreshLayout.setRefreshHeader(header);//设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);//取消内容不满一页时开启上拉加载功能
        refreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableHeaderTranslationContent(false);//拖动Header的时候是否同时拖动内容（默认true）
        refreshLayout.setEnableFooterTranslationContent(true);//拖动Footer的时候是否同时拖动内容（默认true）
        refreshLayout.setEnableOverScrollDrag(false);//禁止越界拖动（1.0.4以上版本）
        ClassicsFooter classicsFooter = new ClassicsFooter(mActivity);
        classicsFooter.setProgressDrawable(getResources().getDrawable(R.drawable.listview_loading_anim));
        refreshLayout.setRefreshFooter(classicsFooter);
//        refreshLayout.setRefreshFooter(new BallPulseFooter(mActivity).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                refreshlayout.setNoMoreData(false);//恢复加载更多的状态
                TimerTask task = new TimerTask() {
                    public void run() {
                        new DownRefresh().execute();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
//                TimerTask task = new TimerTask() {
//                    public void run() {
//
//                    }
//                };
//                Timer timer = new Timer();
//                timer.schedule(task, 1000);
                new UpRefresh().execute();
            }
        });

        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                offsetNum += (dy);

//                if (offsetNum != 0) {
//                    nullView.setVisibility(View.VISIBLE);
//                    banner_header2.setVisibility(View.GONE);
//                }
                setFoldView();
            }
        });
//        nullView.setVisibility(View.GONE);
//        banner_header2.setVisibility(View.VISIBLE);
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {
                super.onFooterMoving(footer, isDragging, percent, offset, footerHeight, maxDragHeight);
//                LogUtil.i("isDragging=" + isDragging+",percent=" + percent+",offset=" + offset+",footerHeight=" + footerHeight+",maxDragHeight=" + maxDragHeight);
//                if (isDragging){
//                    header_fold_bg_view2.setVisibility(View.VISIBLE);
//                    header_fold_bg_view.setVisibility(View.VISIBLE);
//                }else{
//                    if(offset!=0) {
//                        header_fold_bg_view.setVisibility(View.VISIBLE);
//                        header_fold_bg_view2.setVisibility(View.VISIBLE);
//                    }
//                    header_fold_bg_view.setVisibility(View.GONE);
//                    header_fold_bg_view2.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
                super.onStateChanged(refreshLayout, oldState, newState);
//                LogUtil.i("oldState=" + oldState);
//                LogUtil.i("newState=" + newState);
//                if(newState==RefreshState.None){//专治下拉  但上拉冲突
//                    header_fold_bg_view.setVisibility(View.GONE);
//                    header_fold_bg_view2.setVisibility(View.GONE);
//                }else{
//                    header_fold_bg_view.setVisibility(View.VISIBLE);
//                    header_fold_bg_view2.setVisibility(View.VISIBLE);
//                }
//                if(newState==RefreshState.PullUpToLoad
//                        ||newState==RefreshState.ReleaseToLoad
//                        ||newState==RefreshState.LoadReleased
//                        ||newState==RefreshState.Loading){//执行刷新一系列操作时
//                    header_fold_bg_view.setVisibility(View.VISIBLE);
//                    header_fold_bg_view2.setVisibility(View.VISIBLE);
//                }
            }
            //            @Override
//            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
//                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
//                LogUtil.i("isDragging---" + isDragging);
//                LogUtil.i("percent--" + percent);
//                LogUtil.i("offsetNum--" + offsetNum);
//                if (isDragging == true) {
//
//                    nullView.setVisibility(View.VISIBLE);
//                    banner_header2.setVisibility(View.GONE);
//                    if (percent != 0.0) {
//                        nullView.setVisibility(View.GONE);
//                        banner_header2.setVisibility(View.VISIBLE);
//                    } else {
//                        nullView.setVisibility(View.VISIBLE);
//                        banner_header2.setVisibility(View.GONE);
//                    }
//                } else {
//
//                    if (percent != 0.0) {
//                        nullView.setVisibility(View.GONE);
//                        banner_header2.setVisibility(View.VISIBLE);
//                    } else {
//                        if (offsetNum != 0) {
//                            nullView.setVisibility(View.VISIBLE);
//                            banner_header2.setVisibility(View.GONE);
//                        } else {
//                            nullView.setVisibility(View.GONE);
//                            banner_header2.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
//            }

        });//设置多功能监听器

        //////////test




        return v;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isCumulative) {
                recLen++;
                if (recLen > cumulative_num) {
                    //倒计时结束
                    handler3.removeCallbacks(runnable);
                } else {
                    cumulative_num_tv.setText(recLen + "");
                    handler3.postDelayed(this, 50);
                }
            } else {
                recLen--;
                cumulative_num_tv.setText(recLen + "");
                handler3.postDelayed(this, 50);

                if (recLen <= 0) {
                    //倒计时结束
                    handler3.removeCallbacks(runnable);
                    if (cumulative_num > 0) {//0-x开始
                        isCumulative = true;
                        handler3.postDelayed(runnable, 50);
                    }
                }
            }
        }
    };

    private int offsetNum = 0;//总偏移量

    /**
     * 本Activity透明度刷新有问题，所以如下这么写
     */
    public void setFoldView() {
        RelativeLayout.LayoutParams lppTemp = (RelativeLayout.LayoutParams) header_layout.getLayoutParams();

        int listMaxOffset = DensityUtils.dp2px(mActivity, 94f);//listview 最大偏移
        int listOffset = listMaxOffset - offsetNum;
//        LogUtil.i("白色" + listOffset + "=============" + listMaxOffset + "-" + offsetNum);

        int headerMarginLeft = DensityUtils.dp2px(mActivity, 16f);//header MarginLeft
        int headerMarginRight = DensityUtils.dp2px(mActivity, 65f);//header MarginRight
        int headerMaxOffset = DensityUtils.dp2px(mActivity, 55f);//header 最大偏移
        int headerOffset = DensityUtils.dp2px(mActivity, 55f) - (offsetNum - listMaxOffset);
//        LogUtil.i("头部" + headerOffset + "=============" + headerMaxOffset + "-" + offsetNum + "-" + listMaxOffset);

        int ggg = listMaxOffset + headerMaxOffset;
        if (0 <= offsetNum && offsetNum <= ggg) {
            if (0 <= offsetNum && offsetNum <= listMaxOffset) {
                setMeunCradview();
            }
            if (listMaxOffset <= offsetNum && offsetNum <= ggg) {
                lppTemp.setMargins(headerMarginLeft, headerOffset, headerMarginRight, 0);
                in_the_cumulative_tv.setVisibility(View.GONE);
                cumulative_num_tv.setLayoutParams(new LinearLayout.LayoutParams(DensityUtils.dp2px(mActivity, 30f), DensityUtils.dp2px(mActivity, 30f)));
            } else if (offsetNum == 0) {
                lppTemp.setMargins(headerMarginLeft, headerMaxOffset, headerMarginRight, 0);
                in_the_cumulative_tv.setVisibility(View.VISIBLE);
                cumulative_num_tv.setLayoutParams(new LinearLayout.LayoutParams(DensityUtils.dp2px(mActivity, 21f), DensityUtils.dp2px(mActivity, 21f)));
            }
        } else {
            lppTemp.setMargins(headerMarginLeft, 0, headerMarginRight, 0);
            in_the_cumulative_tv.setVisibility(View.GONE);
            cumulative_num_tv.setLayoutParams(new LinearLayout.LayoutParams(DensityUtils.dp2px(mActivity, 30f), DensityUtils.dp2px(mActivity, 30f)));
            meun_cradview.setVisibility(View.GONE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.VISIBLE);
            punch_list_white_iv.setVisibility(View.VISIBLE);
            fitness_diary_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_white_img));
            punch_list_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_white_img));
        }
        header_layout.setLayoutParams(lppTemp);
//        LogUtil.i("总偏移量-----(" + offsetNum);

    }

    private void setMeunCradview() {
        if (0 <= offsetNum && offsetNum < 25) {
            meun_cradview.setVisibility(View.VISIBLE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.GONE);
            punch_list_white_iv.setVisibility(View.GONE);
            meun_cradview.setBackground(getResources().getDrawable(R.drawable.white_rounded_corners_bg));
            fitness_diary_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_pink_img));
            punch_list_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_pink_img));
        } else if (25 <= offsetNum && offsetNum < 50) {
            meun_cradview.setVisibility(View.VISIBLE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.VISIBLE);
            punch_list_white_iv.setVisibility(View.VISIBLE);
            meun_cradview.setBackground(getResources().getDrawable(R.drawable.white_rounded_corners_eight_bg));
            fitness_diary_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_pink_eight_img));
            punch_list_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_pink_eight_img));
            fitness_diary_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_white_two_img));
            punch_list_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_white_two_img));
        } else if (50 <= offsetNum && offsetNum < 75) {
            meun_cradview.setVisibility(View.VISIBLE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.VISIBLE);
            punch_list_white_iv.setVisibility(View.VISIBLE);
            meun_cradview.setBackground(getResources().getDrawable(R.drawable.white_rounded_corners_seven_bg));
            fitness_diary_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_pink_seven_img));
            punch_list_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_pink_seven_img));
            fitness_diary_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_white_three_img));
            punch_list_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_white_three_img));
        } else if (75 <= offsetNum && offsetNum < 100) {
            meun_cradview.setVisibility(View.VISIBLE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.VISIBLE);
            punch_list_white_iv.setVisibility(View.VISIBLE);
            meun_cradview.setBackground(getResources().getDrawable(R.drawable.white_rounded_corners_six_bg));
            fitness_diary_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_pink_six_img));
            punch_list_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_pink_six_img));
            fitness_diary_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_white_four_img));
            punch_list_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_white_four_img));
        } else if (100 <= offsetNum && offsetNum < 125) {
            meun_cradview.setVisibility(View.VISIBLE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.VISIBLE);
            punch_list_white_iv.setVisibility(View.VISIBLE);
            meun_cradview.setBackground(getResources().getDrawable(R.drawable.white_rounded_corners_five_bg));
            fitness_diary_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_pink_five_img));
            punch_list_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_pink_five_img));
            fitness_diary_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_white_five_img));
            punch_list_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_white_five_img));
        } else if (125 <= offsetNum && offsetNum < 150) {
            meun_cradview.setVisibility(View.VISIBLE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.VISIBLE);
            punch_list_white_iv.setVisibility(View.VISIBLE);
            meun_cradview.setBackground(getResources().getDrawable(R.drawable.white_rounded_corners_four_bg));
            fitness_diary_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_pink_four_img));
            punch_list_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_pink_four_img));
            fitness_diary_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_white_six_img));
            punch_list_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_white_six_img));
        } else if (150 <= offsetNum && offsetNum < 175) {
            meun_cradview.setVisibility(View.VISIBLE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.VISIBLE);
            punch_list_white_iv.setVisibility(View.VISIBLE);
            meun_cradview.setBackground(getResources().getDrawable(R.drawable.white_rounded_corners_three_bg));
            fitness_diary_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_pink_three_img));
            punch_list_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_pink_three_img));
            fitness_diary_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_white_seven_img));
            punch_list_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_white_seven_img));
        } else if (175 <= offsetNum && offsetNum < 200) {
            meun_cradview.setVisibility(View.VISIBLE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.VISIBLE);
            punch_list_white_iv.setVisibility(View.VISIBLE);
            meun_cradview.setBackground(getResources().getDrawable(R.drawable.white_rounded_corners_two_bg));
            fitness_diary_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_pink_two_img));
            punch_list_pink_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_pink_two_img));
            fitness_diary_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_white_eight_img));
            punch_list_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_white_eight_img));
        } else if (200 <= offsetNum && offsetNum <= 246) {
            meun_cradview.setVisibility(View.GONE);//改变日记、排行布局
            fitness_diary_white_iv.setVisibility(View.VISIBLE);
            punch_list_white_iv.setVisibility(View.VISIBLE);
            fitness_diary_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.fitness_diary_white_img));
            punch_list_white_iv.setImageDrawable(getResources().getDrawable(R.drawable.punch_list_white_img));
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fitness_diary_white_iv:
                    startActivity(new Intent(mActivity, UserHomeActivity.class)
                            .putExtra("id", SPUtils.getString(Constants.MY_USERID, null))
                            .putExtra("isdyn", true).putExtra("title", "健身日记"));
                    break;
                case R.id.punch_list_white_iv:
                    if (TextUtils.isEmpty(mInfo.getPerson().getDefault_branch())) {
                        ToastUtils.showToastShort("您还没有参加健身运动");
                        return;
                    }//2018-11-22 高威 只需要门店就可以
//                    if (mInfo.getMember() == null || TextUtils.equals(mInfo.getMember().getAuth(), "1")) {
//                        ToastUtils.showToastShort("您还没有参加健身运动");
//                        return;
//                    }
//                    if (myPaiMingBean == null) {
//                        ToastUtils.showToastShort("您还没有参加健身运动");
//                        return;
//                    }
                    startActivity(new Intent(mActivity, PaiMingActivity.class).putExtra("paiming", myPaiMingBean.getData().getBranchRanking()).putExtra("cishu", myPaiMingBean.getData().getBranchScore()));

                    break;
                case R.id.fitness_diary_pink_ll:

                    //      startActivity(new Intent(mActivity, TestActivity.class));
                    startActivity(new Intent(mActivity, UserHomeActivity.class)
                            .putExtra("id", SPUtils.getString(Constants.MY_USERID, null))
                            .putExtra("isdyn", true).putExtra("title", "健身日记"));
                    break;
                case R.id.punch_list_pink_ll:
                    if (TextUtils.isEmpty(mInfo.getPerson().getDefault_branch())) {
                        ToastUtils.showToastShort("您还没有参加健身运动");
                        return;
                    }
//                    if (mInfo.getMember() == null || TextUtils.equals(mInfo.getMember().getAuth(), "1")) {
//                        ToastUtils.showToastShort("您还没有参加健身运动");
//                        return;
//                    }
//                    if (myPaiMingBean == null) {
//                        ToastUtils.showToastShort("您还没有参加健身运动");
//                        return;
//                    }
                    startActivity(new Intent(mActivity, PaiMingActivity.class).putExtra("paiming", myPaiMingBean.getData().getBranchRanking()).putExtra("cishu", myPaiMingBean.getData().getBranchScore()));
                    break;
            }
        }
    };

    /**
     * //     * 下拉刷新
     * //
     */
    private class DownRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            //  findSelectionDyn_Down(1);


            init();
            mCurrentPage = 0;
            isEnd = false;
            findNews(mCurrentPage);
            findImageNews();
            findPaiming();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            newsListviewAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh(0, false);//传入false表示刷新失败
        }
    }

    private boolean isEnd = false;

    private void setEnd() {
        isEnd = true;//没数据了

        refreshLayout.finishLoadMoreWithNoMoreData();

//        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
//                false, true);
//        endLabels.setPullLabel("—我是有底线的—");// 刚下拉时，显示的提示
//        endLabels.setRefreshingLabel("—我是有底线的—");// 刷新时
//        endLabels.setReleaseLabel("—我是有底线的—");// 下来达到一定距离时，显示的提示
//        endLabels.setLoadingDrawable(null);
        // pullToRefresh.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    /**
     * 上拉拉刷新
     */
    private class UpRefresh extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            if (!isEnd) {//还有数据请求
                findNews(mCurrentPage);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
            // newsListviewAdapter.notifyDataSetChanged();
            if (isEnd) {//没数据了
//                pullToRefresh.onRefreshComplete();
                refreshLayout.finishLoadMore(0);//传入false表示加载失败
            }
            refreshLayout.finishLoadMore(0);//传入false表示加载失败
        }
    }


    private void init() {
        // 设置下拉刷新文本
//        ILoadingLayout startLabels = pullToRefresh
//                .getLoadingLayoutProxy(true, false);
//        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
//        startLabels.setRefreshingLabel("正在加载...");// 刷新时
//        startLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示
//        // 设置上拉刷新文本
//        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
//                false, true);
//        endLabels.setPullLabel("上拉加载...");// 刚下拉时，显示的提示
//        endLabels.setRefreshingLabel("正在加载...");// 刷新时
//        endLabels.setReleaseLabel("放开刷新...");// 下来达到一定距离时，显示的提示


    }

    private View nullView;

    private View initBanner() {
        View v = View.inflate(mActivity, R.layout.headview_banner, null);
        CardView banner_cardview = v.findViewById(R.id.banner_cardview);
        LinearLayout collect_entrance_layout = v.findViewById(R.id.collect_entrance_layout);
        mMZBanner = (MZBannerView) v.findViewById(R.id.banner);
        nullView = v.findViewById(R.id.banner_header);
        mMZBanner.setIndicatorRes(R.drawable.home_banner_indicator_icon, R.drawable.home_banner_indicator_select_icon);
        banner_cardview = (CardView) UIUtils.setViewRatio(mActivity, banner_cardview, 155, 80);
        collect_entrance_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//收藏
                mActivity.startActivity(new Intent(mActivity, CollectEntranceActivity.class));
            }
        });
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                //   Toast.makeText(getContext(),"click page:"+position,Toast.LENGTH_LONG).show();
                ShareBean shareBean=new ShareBean();
                shareBean.bus_id=imagelist.get(i).getId()+"";
                shareBean.img_url=imagelist.get(i).getImg_url();
                shareBean.title=imagelist.get(i).getTitle();
                shareBean.url=imagelist.get(i).getUrl();
                shareBean.type=9;//首页轮播
                mActivity.startActivity(new Intent(mActivity, NewsDetailsActivity.class)
                        .putExtra("url", imagelist.get(i).getUrl())
                        .putExtra("shareBean", shareBean)
                        .putExtra("title", imagelist.get(i).getTitle())
                        .putExtra("img_url", imagelist.get(i).getImg_url()));


            }
        });

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();//开始轮播

    }

    public static class BannerViewHolder implements MZViewHolder<RequestImageNewsDataBean.Data> {
        private RoundImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (RoundImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, RequestImageNewsDataBean.Data data) {
            RequestOptions options = new RequestOptions().placeholder(R.drawable.loading_img);
            if (context != null)
                Glide.with(context).load(data.getImg_url()).apply(options).into(mImageView);
        }
    }

    @Override
    public void initData() {
        dialog.show();
        findNews(mCurrentPage);
        findImageNews();
        findPaiming();
    }

    @Override
    public void onClick(View view) {

    }

    private long starttime = 0;
    private long endtime = 0;
    float xDown, yDown, xUp;


    private class HeaderViewRefresh extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            LogUtil.i("透明度--" + alphaNum);
            if (alphaNum >= 0 && alphaNum <= 255) {
                meun_cradview.getBackground().mutate().setAlpha(alphaNum);//参数x为透明度，取值范围为0~255，数值越小越透明。
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_REFRESH_VIEW:
                    LogUtil.i("透明度--" + message.obj);
                    alphaNum = (int) message.obj;
                    if (alphaNum >= 0 && alphaNum <= 255) {
                        meun_cradview.getBackground().mutate().setAlpha(alphaNum);//参数x为透明度，取值范围为0~255，数值越小越透明。
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void findNews(final int page) {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FIND_NEWS_URL_NEW), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                //   pullToRefresh.onRefreshComplete();
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestNewsDataBean newsDataBean = gson.fromJson(s, RequestNewsDataBean.class);
                if (newsDataBean.getSuccess()) {
                    data = newsDataBean.getData().getItems();
                    //    LogUtil.i(data.toString());
                    if (mCurrentPage == 0) {

                        if (data.size() == 0) {
                            rl_error.setVisibility(View.VISIBLE);
                        } else {
                            rl_error.setVisibility(View.GONE);
                        }
                        newsListviewAdapter.setData(data);
                        newsListviewAdapter.notifyDataSetChanged();
                        //   pullToRefresh.setVisibility(View.VISIBLE);

                    } else {
                        LogUtil.i("addLastList");
                        newsListviewAdapter.addLastList(data);
                        newsListviewAdapter.notifyDataSetChanged();
                    }
                    LogUtil.i(data.size() + "");
                    if (data.size() > 0 && data.size() < 10) {
                        LogUtil.i(data.size() + "沒了");
                        setEnd();
                    } else {
                        mCurrentPage = mCurrentPage + 1;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("请求失败，请查看网络连接");
                volleyError.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("page", page + "");
                return map;
            }
        };

        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("findNews");
        // 设置超时时间
//        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);

    }

    private RequsetMyPaiMingBean myPaiMingBean;

    private void findPaiming() {

        MyStringRequest request = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.FIND_MYRANKING_URL), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                myPaiMingBean = new Gson().fromJson(s, RequsetMyPaiMingBean.class);
                if (myPaiMingBean.getSuccess()) {
                    cumulative_num = myPaiMingBean.getData().getBranchScore();
                    isCumulative = false;
                    recLen = 99;
                    handler3.postDelayed(runnable, 50);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MyApplication.getHttpQueues().add(request);
    }

    private void findImageNews() {
        MyStringRequest request = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.FIND_IMAGE_NEWS_URL), new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                LogUtil.i(s);
                Gson gson = new Gson();

                RequestImageNewsDataBean imageNewsDataBean = new RequestImageNewsDataBean();
                imageNewsDataBean = gson.fromJson(s, RequestImageNewsDataBean.class);
                if (imageNewsDataBean.getSuccess()) {

                    if (imageNewsDataBean.getData().size() > 0) {
                        imagelist = imageNewsDataBean.getData();

                        Message message = Message.obtain();
                        message.what = 1;
                        mHandler2.sendMessage(message);
                    } else {
                        ToastUtils.showToastShort("轮播图片为空");
                    }

                } else {
                    ToastUtils.showToastShort(imageNewsDataBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                dialog.dismiss();
                ToastUtils.showToastShort("请查看网络连接");
            }
        }
        );
        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("findImageNews");
        // 设置超时时间
//        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }

    //增加阅读数
    private void setReadNum(final List<RequestNewsDataBean.Data.Items> dataList, final String news_id, final int pos) {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_READ_NUMBER), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestCollectBean requestInfoBean = gson.fromJson(s, RequestCollectBean.class);
                if (requestInfoBean.getSuccess() && requestInfoBean.getCode() == 0) {
                    if (dataList.get(pos).getRead_number() != null && dataList.get(pos).getRead_number().length() > 0) {
                        dataList.get(pos).setRead_number((Integer.valueOf(dataList.get(pos).getRead_number()) + 1) + "");//增加阅读数
                    }
                    newsListviewAdapter.setData(dataList);
                    newsListviewAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("请求失败，请查看网络连接");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", news_id);
//                LogUtil.i("map--" + map.toString());
                return map;
            }
        };
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
          LogUtil.i("收到消息" + event.getEventCode());

        if (99 == event.getEventCode()) {
            String msg = event.getMsg();
            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
            Glide.with(mActivity).load(msg).apply(options).into(iv_avatar);

        }
        if (100 == event.getEventCode()) {
            tv_nick_name.setText("Hello " + event.getMsg());
        }
    }
}