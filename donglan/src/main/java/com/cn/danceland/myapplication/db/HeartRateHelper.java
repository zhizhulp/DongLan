package com.cn.danceland.myapplication.db;

import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shy on 2018/5/24 09:56
 * Email:644563767@qq.com
 */


public class HeartRateHelper {
    private HeartRateDao heartRateDao = MyApplication.getInstance().getHeartRateSessionSession().getHeartRateDao();

    public void insert(HeartRate heartRate) {
        if (heartRateDao==null){
            heartRateDao = MyApplication.getInstance().getHeartRateSessionSession().getHeartRateDao();
        }
//        heartRateDao.insertOrReplace(heartRate);
        List<HeartRate> allData = heartRateDao.loadAll();
        if (allData != null && allData.size() != 0) {
            Date date1 = new Date(allData.get(allData.size() - 1).getDate());
            Date date2 = new Date(heartRate.getDate());//对象时间
            if (date1.before(date2)) { //表示date1小于date2  最后一条数据日期小于手环date2
                heartRateDao.insertOrReplace(heartRate);
            }
        } else {
            heartRateDao.insertOrReplace(heartRate);
        }
        deleteData();//删除七天前的数据
    }

    public void insertList(ArrayList<HeartRate> heartRateList) {
        heartRateDao.insertOrReplaceInTx(heartRateList);
    }

    public List<HeartRate> queryByDay(Long day) {
        if (heartRateDao==null){
            heartRateDao = MyApplication.getInstance().getHeartRateSessionSession().getHeartRateDao();
        }
        List<HeartRate> heartRateArrayList =  heartRateDao.queryBuilder().where(HeartRateDao.Properties.Date.between(day,day+ 1 * 24 * 60 * 60 * 1000)).orderAsc(HeartRateDao.Properties.Date).list();
        return heartRateArrayList;
    }

    public void deleteAll() {
        heartRateDao.deleteAll();
    }

    //删除七天前的数据
    public void deleteData() {
        if (heartRateDao==null){
            heartRateDao = MyApplication.getInstance().getHeartRateSessionSession().getHeartRateDao();
        }
        //删除七天前的数据
        List<HeartRate> wearFitDatas = heartRateDao.queryBuilder().where(HeartRateDao.Properties.Date
                .between(TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 8)
                        , TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 6))).orderAsc(HeartRateDao.Properties.Date).list();
        for (HeartRate delBean : wearFitDatas) {
            heartRateDao.delete(delBean);
        }
    }
}
