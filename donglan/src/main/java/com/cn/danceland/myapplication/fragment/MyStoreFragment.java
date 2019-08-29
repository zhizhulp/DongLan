package com.cn.danceland.myapplication.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.StoreCardActivity;
import com.cn.danceland.myapplication.activity.XiaoFeiRecordActivity;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.store.storeaccount.StoreAccount;
import com.cn.danceland.myapplication.bean.store.storeaccount.StoreAccountCond;
import com.cn.danceland.myapplication.bean.store.storeaccount.StoreAccountRequest;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.XCRoundRectImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by feng on 2018/3/14.
 */

public class MyStoreFragment extends BaseFragment {

    private StoreAccountRequest request;
    private Gson gson;
    private SimpleDateFormat sdf;
    Data info;
    TextView tv_mystore;
    StoreCardActivity storeCardActivity;
    XCRoundRectImageView card_img;
    TextView address_name,card_jine;
    RelativeLayout rl_chongzhi,rl_xiaofeijilu;


    @Override
    public View initViews() {

        View view = View.inflate(mActivity, R.layout.mystore, null);

        //tv_mystore = view.findViewById(R.id.tv_mystore);
        initHost();

        queryList();


        storeCardActivity = (StoreCardActivity)getActivity();
        card_img = view.findViewById(R.id.card_img);
        address_name = view.findViewById(R.id.address_name);
        card_jine = view.findViewById(R.id.card_jine);
        rl_chongzhi = view.findViewById(R.id.rl_chongzhi);
        rl_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeCardActivity.showFragment(1);
            }
        });

        rl_xiaofeijilu = view.findViewById(R.id.rl_xiaofeijilu);
        rl_xiaofeijilu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,XiaoFeiRecordActivity.class));
            }
        });

        return view;
    }

    private void initHost() {
        request = new StoreAccountRequest();

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
    }

    @Override
    public void onClick(View v) {

    }


    /**
     * @方法说明:按条件查询储值帐户列表
     **/
    public void queryList() {
        StoreAccountCond cond = new StoreAccountCond();
        // TODO 准备查询条件
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {

                LogUtil.e("zzf",json.toString());
                DLResult<StoreAccount> result = gson.fromJson(json.toString(), new TypeToken<DLResult<StoreAccount>>() {
                }.getType());
                if(result!=null){

                    StoreAccount data = result.getData();
                    Glide.with(mActivity).load(data.getImg_url()).into(card_img);
                    address_name.setText(data.getAddress_name());
                    if(data.getRemain() == 0 &&data.getGiving()==0){
                        card_jine.setText("￥ 0");
                    }else{
                        card_jine.setText("￥ "+new DecimalFormat("0.00").format((data.getRemain()+data.getGiving())));
                    }
                }else{
                    card_jine.setText("￥ 0");
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        });
    }
}
