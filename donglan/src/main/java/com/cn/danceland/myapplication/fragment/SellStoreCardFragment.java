package com.cn.danceland.myapplication.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.SellStoreCardActivity;
import com.cn.danceland.myapplication.bean.DLResult;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.store.storetype.StoreType;
import com.cn.danceland.myapplication.bean.store.storetype.StoreTypeCond;
import com.cn.danceland.myapplication.bean.store.storetype.StoreTypeRequest;
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
import java.util.List;

/**
 * Created by feng on 2018/3/14.
 */

public class SellStoreCardFragment extends BaseFragment {
    ListView lv_storecard;
    private StoreTypeRequest request;
    private Gson gson;
    private SimpleDateFormat sdf;
    Data info;
    TextView tv_mystore;
    RelativeLayout rl_error;
    ImageView iv_error;
    TextView tv_error;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.sellstorecard, null);
        lv_storecard = view.findViewById(R.id.lv_storecard);
        rl_error = view.findViewById(R.id.rl_error);
        iv_error = rl_error.findViewById(R.id.iv_error);
        Glide.with(mActivity).load(R.drawable.img_error).into(iv_error);
        tv_error = rl_error.findViewById(R.id.tv_error);
        tv_error.setText("暂无储值卡");

        lv_storecard.setEmptyView(rl_error);

        initHost();
        queryList();


        return view;
    }

    private void initHost() {
        request = new StoreTypeRequest();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        info = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);

    }


    /**
     * @方法说明:按条件查询储值卡类型列表
     **/
    public void queryList() {
        StoreTypeCond cond = new StoreTypeCond();
        // TODO 准备查询条件

        cond.setBranch_id(Long.valueOf(info.getPerson().getDefault_branch()));
        cond.setEnable(1);
        request.queryList(cond, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json) {
                DLResult<List<StoreType>> result = gson.fromJson(json.toString(), new TypeToken<DLResult<List<StoreType>>>() {
                }.getType());
                if (result.isSuccess()) {
                    List<StoreType> list= result.getData();
                    LogUtil.e("zzf",json.toString());
                    // TODO 请求成功后的代码
                    if(list!=null){
                        lv_storecard.setAdapter(new StoreCardListAdapter(list));
                    }

                } else {
                    ToastUtils.showToastShort("查询分页列表失败,请检查手机网络！");
                }
            }
        },rl_error,iv_error,tv_error);
    }


    private class StoreCardListAdapter extends BaseAdapter {

        List<StoreType> list;
        StoreCardListAdapter(List<StoreType> list){
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View inflate = View.inflate(mActivity, R.layout.storecard_item, null);
            //TextView card_name = inflate.findViewById(R.id.card_name);
            TextView card_jine = inflate.findViewById(R.id.card_jine);
            TextView address_name = inflate.findViewById(R.id.address_name);
            TextView cardname = inflate.findViewById(R.id.cardname);
            TextView tv_jine = inflate.findViewById(R.id.tv_jine);
            TextView tv_zengsong = inflate.findViewById(R.id.tv_zengsong);
            //RelativeLayout chongzhicard = inflate.findViewById(R.id.chongzhicard);
            XCRoundRectImageView card_img=  (XCRoundRectImageView) inflate.findViewById(R.id.card_img);

            card_jine.setText("￥"+new DecimalFormat("0.00").format(list.get(position).getFace()));
            address_name.setText(list.get(position).getAddress_name());
            cardname.setText(new DecimalFormat("0.00").format(list.get(position).getFace())+"元储值卡");
            tv_jine.setText("￥"+new DecimalFormat("0.00").format(list.get(position).getFace()));
         //   LogUtil.i("qian--"+new DecimalFormat("0.00").format(list.get(position).getGiving()));
            tv_zengsong.setText("赠送：￥ "+new DecimalFormat("0.00").format(list.get(position).getGiving())+"元");

            Glide.with(mActivity).load(list.get(position).getImg_url()).into(card_img);


            RelativeLayout all_item = inflate.findViewById(R.id.all_item);
            all_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mActivity,SellStoreCardActivity.class).putExtra("item",list.get(position)));
                }
            });


            return inflate;
        }
    }

    @Override
    public void onClick(View v) {

    }


}
