package com.fph.lotteryanalyze.db;

import android.content.Context;

import java.util.List;


/**
 * Created by fengpeihao on 2018/1/30.
 */

public class ETCFDaoUtils {
    private static final String TAG = ETCFDaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public ETCFDaoUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成notice记录的插入，如果表未创建，先创建Notice表
     *
     * @param etcfBean
     * @return
     */
    public boolean insertData(ETCFBean etcfBean) {
        boolean flag = false;
        flag = mManager.getDaoSession().getETCFBeanDao().insert(etcfBean) == -1 ? false : true;
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param etcfBeans
     * @return
     */
    public boolean insertMultiData(final List<ETCFBean> etcfBeans) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (ETCFBean etcfBean : etcfBeans) {
                        mManager.getDaoSession().insertOrReplace(etcfBean);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     *
     * @param etcfBean
     * @return
     */
    public boolean updateData(ETCFBean etcfBean) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(etcfBean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param etcfBean
     * @return
     */
    public boolean deleteData(ETCFBean etcfBean) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(etcfBean);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(ETCFBean.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     *
     * @return
     */
    public List<ETCFBean> queryAllData() {
        return mManager.getDaoSession().loadAll(ETCFBean.class);
    }

    /**
     * 倒序查询所有记录
     *
     * @return
     */
    public List<ETCFBean> descQueryAllData() {
        return mManager.getDaoSession().queryBuilder(ETCFBean.class)
                .orderDesc(ETCFBeanDao.Properties.Periods).list();
    }

    /**
     * 正序查询所有记录
     *
     * @return
     */
    public List<ETCFBean> ascQueryAllData() {
        return mManager.getDaoSession().queryBuilder(ETCFBean.class)
                .orderAsc(ETCFBeanDao.Properties.Periods).list();
    }

    /**
     * 根据period查询记录
     *
     * @param key
     * @return
     */
    public ETCFBean queryDataByPeriod(String key) {
        return mManager.getDaoSession().queryBuilder(ETCFBean.class)
                .where(ETCFBeanDao.Properties.Periods.eq(key)).unique();
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public ETCFBean queryDataById(long key) {
        return mManager.getDaoSession().load(ETCFBean.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<ETCFBean> queryDataByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(ETCFBean.class, sql, conditions);
    }

    /**
     * 获取第近的期数
     */
    public String queryLastPeriods() {
        List<ETCFBean> list = mManager.getDaoSession().queryBuilder(ETCFBean.class)
                .orderDesc(ETCFBeanDao.Properties.Periods).limit(1).list();
        if (list != null && list.size() > 0) {
            return list.get(0).getPeriods();
        }
        return "";
    }

    /**
     * 查询最近几期结果
     *
     * @param limit 期数
     * @return
     */
    public List<ETCFBean> queryLimitData(int limit) {
        return mManager.getDaoSession().queryBuilder(ETCFBean.class)
                .orderDesc(ETCFBeanDao.Properties.Periods).limit(limit).list();
    }
}
