package com.cn.danceland.myapplication.im.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.MyContactsActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.GroupMemeberInfo;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.im.adapters.ProfileSummaryAdapter;
import com.cn.danceland.myapplication.im.model.GroupMemberProfile;
import com.cn.danceland.myapplication.im.model.ProfileSummary;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.DensityUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupManagerExt;
import com.tencent.imsdk.ext.group.TIMGroupMemberResult;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.tencent.qcloud.ui.CircleImageView;
import com.tencent.qcloud.ui.TemplateTitle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupMemberActivity extends Activity implements TIMValueCallBack<List<TIMGroupMemberInfo>> {

    ProfileSummaryAdapter adapter;
    MyAdapter myAdapter;
    List<ProfileSummary> list = new ArrayList();
    List<GroupMemeberInfo.MemberList> listGroupMember = new ArrayList<>();
    ListView listView;
    TemplateTitle title;
    String groupId, type;
    private final int MEM_REQ = 100;
    private final int CHOOSE_MEM_CODE = 200;
    private int memIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        title = (TemplateTitle) findViewById(R.id.group_mem_title);
        groupId = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        listView = (ListView) findViewById(R.id.list);
        //   adapter = new ProfileSummaryAdapter(this, R.layout.item_profile_summary, list);
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
//        TIMGroupManagerExt.getInstance().getGroupMembers(groupId, this);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                memIndex = position;
//                Intent intent = new Intent(GroupMemberActivity.this, GroupMemberProfileActivity.class);
//                GroupMemberProfile profile = (GroupMemberProfile) list.get(position);
//                intent.putExtra("data", profile);
//                intent.putExtra("groupId", groupId);
//                intent.putExtra("type", type);
//                startActivityForResult(intent, MEM_REQ);
//            }
//        });
//        if (type.equals(GroupInfo.privateGroup)) {
//            title.setMoreImg(R.drawable.ic_add);
//            title.setMoreImgAction(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(GroupMemberActivity.this, ChooseFriendActivity.class);
//                    ArrayList<String> selected = new ArrayList<>();
//                    for (ProfileSummary profile : list) {
//                        selected.add(profile.getIdentify());
//                    }
//                    intent.putStringArrayListExtra("selected", selected);
//                    startActivityForResult(intent, CHOOSE_MEM_CODE);
//                }
//            });
//        }

        getGroupMember(groupId);
    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
        list.clear();
        if (timGroupMemberInfos == null) return;
        for (TIMGroupMemberInfo info : timGroupMemberInfos) {
            list.add(new GroupMemberProfile(info));
//            LogUtil.i("user: " + info.getUser() +
//                    "join time: " + info.getJoinTime() +
//                    "role: " + info.getRole() + "getCustomInfo:" + info.getCustomInfo());
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (MEM_REQ == requestCode) {
            if (resultCode == RESULT_OK) {
                boolean isKick = data.getBooleanExtra("isKick", false);
                if (isKick) {
                    list.remove(memIndex);
                    adapter.notifyDataSetChanged();
                } else {
                    GroupMemberProfile profile = (GroupMemberProfile) data.getSerializableExtra("data");
                    if (memIndex < list.size() && list.get(memIndex).getIdentify().equals(profile.getIdentify())) {
                        GroupMemberProfile mMemberProfile = (GroupMemberProfile) list.get(memIndex);
                        mMemberProfile.setRoleType(profile.getRole());
                        mMemberProfile.setQuietTime(profile.getQuietTime());
                        mMemberProfile.setName(profile.getNameCard());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        } else if (CHOOSE_MEM_CODE == requestCode) {
            if (resultCode == RESULT_OK) {
                GroupManagerPresenter.inviteGroup(groupId, data.getStringArrayListExtra("select"),
                        new TIMValueCallBack<List<TIMGroupMemberResult>>() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(GroupMemberActivity.this, getString(R.string.chat_setting_invite_error), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(List<TIMGroupMemberResult> timGroupMemberResults) {
                                TIMGroupManagerExt.getInstance().getGroupMembers(groupId, GroupMemberActivity.this);
                            }
                        });

            }
        }
    }


    private void getGroupMember(final String groupId1) {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.GET_GROUP_MEMBERS), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestSimpleBean simpleBean = new Gson().fromJson(s, RequestSimpleBean.class);
                LogUtil.i(simpleBean.getData().toString());
                GroupMemeberInfo memeberInfo = new Gson().fromJson(simpleBean.getData().toString(), GroupMemeberInfo.class);
                listGroupMember = memeberInfo.getMemberList();
                myAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("groupId", groupId1);
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }

    public class MyAdapter extends BaseAdapter {
        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return listGroupMember.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view != null) {

                viewHolder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(GroupMemberActivity.this, R.layout.item_profile_summary, null);
                viewHolder = new ViewHolder();
                viewHolder.avatar = (CircleImageView) view.findViewById(R.id.avatar);
                viewHolder.name = (TextView) view.findViewById(R.id.name);
                viewHolder.des = (TextView) view.findViewById(R.id.description);
                viewHolder.item_layout_cv = view.findViewById(R.id.item_layout_cv);
                view.setTag(viewHolder);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(GroupMemberActivity.this,80f));
            if (position == 0) {
                layoutParams.setMargins(DensityUtils.dp2px(GroupMemberActivity.this, 16f), DensityUtils.dp2px(GroupMemberActivity.this, 16f), DensityUtils.dp2px(GroupMemberActivity.this, 16f), DensityUtils.dp2px(GroupMemberActivity.this, 11f));
            } else if (position == getCount() - 1) {
                layoutParams.setMargins(DensityUtils.dp2px(GroupMemberActivity.this, 16f), DensityUtils.dp2px(GroupMemberActivity.this, 11f), DensityUtils.dp2px(GroupMemberActivity.this, 16f), DensityUtils.dp2px(GroupMemberActivity.this, 16f));
            } else {
                layoutParams.setMargins(DensityUtils.dp2px(GroupMemberActivity.this, 16f), DensityUtils.dp2px(GroupMemberActivity.this, 11f), DensityUtils.dp2px(GroupMemberActivity.this, 16f), DensityUtils.dp2px(GroupMemberActivity.this, 11f));
            }
            viewHolder.item_layout_cv.setLayoutParams(layoutParams);
            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_my_avatar);
            Data data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        //    LogUtil.i(data.getImgUrl() + listGroupMember.get(position).getAppMemberDefinedData().get(0).getValue());

            Glide.with(GroupMemberActivity.this).load(data.getImgUrl() + listGroupMember.get(position).getAppMemberDefinedData().get(0).getValue()).apply(options).into(viewHolder.avatar);
            //  viewHolder.avatar.setImageResource(data.getAvatarRes());
            viewHolder.name.setText(listGroupMember.get(position).getAppMemberDefinedData().get(1).getValue());
            return view;
        }

        public class ViewHolder {
            public ImageView avatar;
            public TextView name;
            public TextView des;
            public CardView item_layout_cv;
        }
    }
}
