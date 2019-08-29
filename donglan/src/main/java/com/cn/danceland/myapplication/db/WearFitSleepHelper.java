package com.cn.danceland.myapplication.db;

import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 手环睡眠DB
 * Created by ${yxx} on 2018/8/5.
 */
public class WearFitSleepHelper {
    private WearFitSleepBeanDao wearFitSleepDao = MyApplication.getInstance().getWearFitSleepSession().getWearFitSleepBeanDao();

    public void insert(WearFitSleepBean wearFitSleepBean) {
        if (wearFitSleepDao == null) {
            wearFitSleepDao = MyApplication.getInstance().getWearFitSleepSession().getWearFitSleepBeanDao();
        }
//        wearFitSleepDao.insertOrReplace(wearFitSleepBean);
        List<WearFitSleepBean> allData = wearFitSleepDao.loadAll();
        if (allData != null && allData.size() != 0) {
            Date date1 = new Date(allData.get(allData.size() - 1).getTimestamp());
            Date date2 = new Date(wearFitSleepBean.getTimestamp());//对象时间
            if (date1.before(date2)) { //表示date1小于date2  最后一条数据日期小于手环date2
                wearFitSleepDao.insertOrReplace(wearFitSleepBean);
            }
        } else {
            wearFitSleepDao.insertOrReplace(wearFitSleepBean);
        }
        deleteData();//删除七天前的数据
    }

    public void insertList(ArrayList<WearFitSleepBean> wearFitSleepBeans) {
        wearFitSleepDao.insertOrReplaceInTx(wearFitSleepBeans);
    }

    public List<WearFitSleepBean> queryByDay(Long day) {
        if (wearFitSleepDao == null) {
            wearFitSleepDao = MyApplication.getInstance().getWearFitSleepSession().getWearFitSleepBeanDao();
        }
        List<WearFitSleepBean> wearFitSleepBeans = wearFitSleepDao.queryBuilder().where(WearFitSleepBeanDao.Properties.Timestamp.between(day, day + 1 * 24 * 60 * 60 * 1000)).orderAsc(WearFitSleepBeanDao.Properties.Timestamp).list();
        return wearFitSleepBeans;
    }

    public void deleteAll() {
        wearFitSleepDao.deleteAll();
    }

    //删除七天前的数据
    public void deleteData() {
        if (wearFitSleepDao == null) {
            wearFitSleepDao = MyApplication.getInstance().getWearFitSleepSession().getWearFitSleepBeanDao();
        }
        //删除七天前的数据
        List<WearFitSleepBean> wearFitDatas = wearFitSleepDao.queryBuilder().where(WearFitSleepBeanDao.Properties.Timestamp
                .between(TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 8)
                        , TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 6))).orderAsc(WearFitSleepBeanDao.Properties.Timestamp).list();
        for (WearFitSleepBean delBean : wearFitDatas) {
            wearFitSleepDao.delete(delBean);
        }
    }
}
