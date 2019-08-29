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
public class WearFitStepHelper {
    private WearFitStepBeanDao wearFitStepDao = MyApplication.getInstance().getWearFitStepSession().getWearFitStepBeanDao();

    public void insert(WearFitStepBean wearFitStepBean) {
        if (wearFitStepDao == null) {
            wearFitStepDao = MyApplication.getInstance().getWearFitStepSession().getWearFitStepBeanDao();
        }
//        wearFitStepDao.insertOrReplace(wearFitStepBean);

        List<WearFitStepBean> allData = wearFitStepDao.loadAll();
        if (allData != null && allData.size() != 0) {
            Date date1 = new Date(allData.get(allData.size() - 1).getTimestamp());
            Date date2 = new Date(wearFitStepBean.getTimestamp());//对象时间
            if (date1.before(date2)) { //表示date1小于date2  最后一条数据日期小于手环date2
                wearFitStepDao.insertOrReplace(wearFitStepBean);
            }
        } else {
            wearFitStepDao.insertOrReplace(wearFitStepBean);
        }
        deleteData();//删除七天前的数据
    }

    public void insertList(ArrayList<WearFitStepBean> wearFitStepBeans) {
        wearFitStepDao.insertOrReplaceInTx(wearFitStepBeans);
    }

    public List<WearFitStepBean> queryByDay(Long day) {
        if (wearFitStepDao == null) {
            wearFitStepDao = MyApplication.getInstance().getWearFitStepSession().getWearFitStepBeanDao();
        }
        List<WearFitStepBean> wearFitStepBeans = wearFitStepDao.queryBuilder().where(WearFitStepBeanDao.Properties.Timestamp.between(day, day + 1 * 24 * 60 * 60 * 1000)).orderAsc(WearFitStepBeanDao.Properties.Timestamp).list();
        return wearFitStepBeans;
    }

    public void deleteAll() {
        wearFitStepDao.deleteAll();
    }

    //删除七天前的数据
    public void deleteData() {
        if (wearFitStepDao == null) {
            wearFitStepDao = MyApplication.getInstance().getWearFitStepSession().getWearFitStepBeanDao();
        }
        //删除七天前的数据
        List<WearFitStepBean> wearFitDatas = wearFitStepDao.queryBuilder().where(WearFitStepBeanDao.Properties.Timestamp
                .between(TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 8)
                        , TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 6))).orderAsc(WearFitStepBeanDao.Properties.Timestamp).list();
        for (WearFitStepBean delBean : wearFitDatas) {
            wearFitStepDao.delete(delBean);
        }
    }
}
