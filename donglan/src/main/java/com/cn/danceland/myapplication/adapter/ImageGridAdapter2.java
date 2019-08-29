package com.cn.danceland.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.DensityUtils;

import java.util.List;

/**
 * Created by Hankkin on 15/11/22.
 */
public class ImageGridAdapter2 extends BaseAdapter {

    private Context context;
    private List<String> imgUrls;
    private LayoutInflater inflater;

    public ImageGridAdapter2(Context context, List<String> imgUrls) {
        this.context = context;
        this.imgUrls = imgUrls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return imgUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.gridview_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_content);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                //  .placeholder(R.drawable.img_loading)//加载占位图
                .error(R.drawable.loading_img)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .priority(Priority.HIGH);
        StringBuilder sb = new StringBuilder(imgUrls.get(position));

        String houzhui = imgUrls.get(position).substring(imgUrls.get(position).lastIndexOf(".") + 1);
        sb.insert(imgUrls.get(position).length() - houzhui.length() - 1, "_400X400");
        //    LogUtil.i(sb.toString());

        int imageP = (DensityUtils.dp2px(context, AppUtils.getScreenWidth()) - DensityUtils.dp2px(context, 32f)) / 3
                - DensityUtils.dp2px(context, 1f);//32：gridView左右margin  10:设计图
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(imageP, imageP);
        int marginNum = DensityUtils.dp2px(context, 2.5f);
        if (imgUrls.size() == 4) {
            switch ((position + 1) % 2) {
                case 1://左面
                    linearParams.setMargins(0, marginNum, marginNum, marginNum);
                    break;
                case 0://右面
                    linearParams.setMargins(marginNum, marginNum, 0, marginNum);
                    break;
            }
        } else {
            imageP = (DensityUtils.dp2px(context, AppUtils.getScreenWidth()) - DensityUtils.dp2px(context, 32f)) / 3
                    - DensityUtils.dp2px(context, 3f);//32：gridView左右margin  10:设计图
            linearParams = new LinearLayout.LayoutParams(imageP, imageP);
            switch ((position + 1) % 3) {
                case 1://左面
                    linearParams.setMargins(0, marginNum, marginNum, marginNum);
                    break;
                case 2://中间
                    linearParams.setMargins(marginNum, marginNum, marginNum, marginNum);
                    break;
                case 0://右面
                    linearParams.setMargins(marginNum, marginNum, 0, marginNum);
                    break;
            }
        }
        imageView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件

        Glide
                .with(context)
                .load(sb.toString())
                .apply(options)
                .into(imageView);


        return view;
    }
}
