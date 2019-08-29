package com.cn.danceland.myapplication.im.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.im.model.ProfileSummary;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.tencent.qcloud.ui.CircleImageView;

import java.util.List;

/**
 * 好友或群等资料摘要列表的adapter
 */
public class ProfileSummaryAdapter extends ArrayAdapter<ProfileSummary> {


    private int resourceId;
    private View view;
    private ViewHolder viewHolder;
    private List<ProfileSummary> objects ;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ProfileSummaryAdapter(Context context, int resource, List<ProfileSummary> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (CircleImageView) view.findViewById(R.id.avatar);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.des = (TextView) view.findViewById(R.id.description);
            viewHolder.item_layout_cv = view.findViewById(R.id.item_layout_cv);
            view.setTag(viewHolder);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(getContext(),80f));
        if (position == 0) {
            layoutParams.setMargins(DensityUtils.dp2px(getContext(), 16f), DensityUtils.dp2px(getContext(), 16f), DensityUtils.dp2px(getContext(), 16f), DensityUtils.dp2px(getContext(), 11f));
        } else if (position == objects.size() - 1) {
            layoutParams.setMargins(DensityUtils.dp2px(getContext(), 16f), DensityUtils.dp2px(getContext(), 11f), DensityUtils.dp2px(getContext(), 16f), DensityUtils.dp2px(getContext(), 16f));
        } else {
            layoutParams.setMargins(DensityUtils.dp2px(getContext(), 16f), DensityUtils.dp2px(getContext(), 11f), DensityUtils.dp2px(getContext(), 16f), DensityUtils.dp2px(getContext(), 11f));
        }
        viewHolder.item_layout_cv.setLayoutParams(layoutParams);
        ProfileSummary data = getItem(position);
        viewHolder.avatar.setImageResource(data.getAvatarRes());
        viewHolder.name.setText(data.getName());
        return view;
    }


    public class ViewHolder{
        public ImageView avatar;
        public TextView name;
        public TextView des;
        public CardView item_layout_cv;
    }
}
