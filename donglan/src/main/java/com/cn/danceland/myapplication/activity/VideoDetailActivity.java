package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseRecyclerViewRefreshActivity;
import com.cn.danceland.myapplication.adapter.recyclerview.CommonAdapter;
import com.cn.danceland.myapplication.adapter.recyclerview.base.ViewHolder;
import com.cn.danceland.myapplication.bean.RequestVideoListBean;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by shy on 2019/1/11 10:38
 * Email:644563767@qq.com
 */

public class VideoDetailActivity extends BaseRecyclerViewRefreshActivity {
    private JZVideoPlayerStandard jzVideoPlayer;
    private String url;
    private String name;
    private String desc;
    private String mark;
    private List<RequestVideoListBean.Data.Content> datas = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public CommonAdapter setAtapter() {
        setTitle("视频详情");
        View view = View.inflate(this, R.layout.headview_videodetails, null);
        jzVideoPlayer = view.findViewById(R.id.videoplayer);
        MyAdapter myAdapter = new MyAdapter(this, R.layout.item_video_list, datas);
//        jzVideoPlayer.setUp(
//                oneDynInfo.getVedioUrl(), JZVideoPlayer.SCREEN_WINDOW_NORMAL,
//                "");
//        Glide.with(this)
//                .load(oneDynInfo.getVedioImg())
//                .into(jzVideoPlayer.thumbImageView);
        return null;
    }

    @Override
    public void initDownRefreshData() {

    }

    @Override
    public void upDownRefreshData() {

    }

    class MyAdapter extends CommonAdapter<RequestVideoListBean.Data.Content> {

        public MyAdapter(Context context, int layoutId, List<RequestVideoListBean.Data.Content> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, RequestVideoListBean.Data.Content videoBean, int position) {
            holder.setText(R.id.tv_tiltle, videoBean.getName());
            holder.setText(R.id.tv_desc, videoBean.getRemark());
            Glide.with(VideoDetailActivity.this).load(videoBean.getImg_url()).into((ImageView) holder.getView(R.id.iv_video));
        }
    }
}
