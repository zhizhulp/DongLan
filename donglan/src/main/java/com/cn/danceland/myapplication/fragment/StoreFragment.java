package com.cn.danceland.myapplication.fragment;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.fragment.base.BaseFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LocationService;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.ToastUtils;

/**
 * Created by feng on 2017/11/6.
 */

public class StoreFragment extends BaseFragment {
    TextView store_name;
    ListView storelist;
    RequestQueue requestQueue;
    public LocationService mLocationClient;
    LocationClientOption option;
    public BDAbstractLocationListener myListener = new MyLocationListener();
    double jingdu,weidu;

    @Override
    public View initViews() {
        View v  = View.inflate(mActivity, R.layout.activity_store,null);
        store_name = v.findViewById(R.id.store_name);
        storelist = v.findViewById(R.id.storelist);

        requestQueue = MyApplication.getHttpQueues();
        getData();
        return v;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mLocationClient = new LocationService(MyApplication.getInstance());
        SDKInitializer.initialize(MyApplication.getInstance());
        startLocation();
    }

    private void startLocation() {
        //声明LocationClient类
        mLocationClient.registerListener(myListener);

        mLocationClient.start();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //获取周边POI信息
            //POI信息包括POI ID、名称等，具体信息请参照类参考中POI类的相关说明
            //LogUtil.e("zzf",location.getLocType()+"");
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                //loading.setVisibility(View.GONE);
//                StringBuffer sb = new StringBuffer(256);
//                sb.append("time : ");
//                /**
//                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
//                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
//                 */
//                sb.append(location.getTime());
//                sb.append("\nlocType : ");// 定位类型
//                sb.append(location.getLocType());
//                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
//                sb.append(location.getLocTypeDescription());
//                sb.append("\nlatitude : ");// 纬度
//                sb.append(location.getLatitude());
//                sb.append("\nlontitude : ");// 经度
//                sb.append(location.getLongitude());
                jingdu = location.getLatitude();
                weidu = location.getLongitude();

            }else{
                ToastUtils.showToastShort("定位失败!");
            }
        }

    }
    public void getData(){
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.BRANCH)+"/1/"+jingdu+"/"+weidu, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.e("zzf",s);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf",volleyError.toString());
            }
        }){

        };

        requestQueue.add(stringRequest);

    }




//    public class MyStoreAdapter extends BaseAdapter{
//        Context mContext;
//        ArrayList<StoreBean.Items> itemsArrayList;
//
//        MyStoreAdapter(Context context, ArrayList<StoreBean.Items> list){
//            mContext = context;
//            itemsArrayList = list;
//        }
//
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            return null;
//        }
//    }
}
