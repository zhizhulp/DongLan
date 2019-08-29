package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.utils.LocationService;
import com.cn.danceland.myapplication.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by feng on 2017/10/27.
 */

public class LocationActivity extends BaseActivity {

    public LocationService mLocationClient;
    LocationClientOption option;
    public BDAbstractLocationListener myListener = new MyLocationListener();
    ListView location_list;
    String location;
    ImageView location_back;
    ArrayList<String> arrLocation;
    ImageView location_ok_pre=null;
    TextView location_success;
    ImageView location_first_ok;
    RelativeLayout location_first;
    int selectPosition = -1;
    MyLocationAdapter myLocationAdapter;
    TextView loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initHost();
        initView();
        setClick();


    }

    private void setClick() {
        location_back.setOnClickListener(onclick);
        location_success.setOnClickListener(onclick);
        location_first.setOnClickListener(onclick);
    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_back:
                    Intent intent = new Intent();
                    setResult(1,intent);
                    finish();
                    break;
                case R.id.donglan_right_tv:
                    Intent intent1 = new Intent();
                    intent1.putExtra("location",location);
                    setResult(1,intent1);
                    finish();
                    break;
                case R.id.location_first:
                    location = "";
                    location_first_ok.setVisibility(View.VISIBLE);
                    if(location_ok_pre!=null){
                        location_ok_pre.setVisibility(View.GONE);
                    }
                    selectPosition = -1;
                    break;
            }

        }
    };

    private void initHost() {
        arrLocation = new ArrayList<String>();


    }

    private void startLocation(){
        //声明LocationClient类
        mLocationClient = new LocationService(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient.registerListener(myListener);


        mLocationClient.start();
        LogUtil.i("mLocationClient_start");
    }


    @Override
    protected void onStart() {
        super.onStart();
        startLocation();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.unregisterListener(myListener);
        mLocationClient.stop();
    }

    private void initView() {

        location_first_ok = findViewById(R.id.location_first_ok);

        loading = findViewById(R.id.location_loading);
        location_first = findViewById(R.id.location_first);
        location_success = findViewById(R.id.donglan_right_tv);
        location_list = findViewById(R.id.location_list);
        myLocationAdapter = new MyLocationAdapter(LocationActivity.this,arrLocation);
        location_list.setAdapter(myLocationAdapter);
        location_back = findViewById(R.id.iv_back);

        location_success.setText("完成");
        location_success.setVisibility(View.VISIBLE);
        location_success.setTextColor(getResources().getColor(R.color.home_enter_total_text_color));

        location_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location_first_ok.setVisibility(View.GONE);
                if(selectPosition>=0&&selectPosition!=position){
                    location_ok_pre.setVisibility(View.GONE);
                    ImageView location_ok = view.findViewById(R.id.location_ok);
                    location_ok.setVisibility(View.VISIBLE);
                }
                location = arrLocation.get(position);
                location_ok_pre = view.findViewById(R.id.location_ok);
                if(selectPosition==-1){
                    location_ok_pre.setVisibility(View.VISIBLE);
                }
                selectPosition = position;
                //location_ok.setVisibility(View.VISIBLE);
            }

        });


    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //获取周边POI信息
            //POI信息包括POI ID、名称等，具体信息请参照类参考中POI类的相关说明
            //LogUtil.e("zzf",location.getLocType()+"");
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {

                loading.setVisibility(View.GONE);
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                arrLocation.clear();
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    int m = location.getPoiList().size();
                    for (int i = 0; i < m; i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                        arrLocation.add(poi.getName());
                    }
                }
                myLocationAdapter.notifyDataSetChanged();
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                String str = sb.toString();
            }else{
                if(arrLocation==null||arrLocation.size()<=0){
                    loading.setVisibility(View.VISIBLE);
                    loading.setText("正在定位...");
                }
            }
//            else{
//                mLocationClient.unRegisterLocationListener(myListener);
//                mLocationClient.stop();
//                startLocation();
//            }
        }

    }

    private class MyLocationAdapter extends BaseAdapter{
        ArrayList<String> arr;
        LayoutInflater mInflater;

        MyLocationAdapter (Context context,ArrayList<String> arr){
            mInflater = LayoutInflater.from(context);
            this.arr = arr;

        }

        @Override
        public int getCount() {
            return arr.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder=null;
            if(convertView==null){
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.locationliat_item, null);
                viewHolder.location_item = convertView.findViewById(R.id.location_item);
                viewHolder.location_ok =convertView.findViewById(R.id.location_ok);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

//            if(selectPosition == position){
//                viewHolder.location_ok.setVisibility(View.VISIBLE);
//            }
//            else{
//                viewHolder.location_ok.setVisibility(View.GONE);
//            }

            viewHolder.location_item.setText(arr.get(position));
            return convertView;
        }
    }
    class ViewHolder{
        TextView location_item;
        ImageView location_ok;
    }

}
