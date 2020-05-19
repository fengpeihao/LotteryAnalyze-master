package com.fph.lotteryanalyze.db;

import android.content.Context;

import java.util.List;


/**
 * Created by fengpeihao on 2018/1/30.
 */

public class ArrangeThreeDaoUtils {
    private static final String TAG = ArrangeThreeDaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public ArrangeThreeDaoUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成notice记录的插入，如果表未创建，先创建Notice表
     *
     * @param arrangeThreeEntity
     * @return
     */
    public boolean insertData(ArrangeThreeEntity arrangeThreeEntity) {
        boolean flag = false;
        flag = mManager.getDaoSession().getArrangeThreeEntityDao().insert(arrangeThreeEntity) == -1 ? false : true;
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param arrangeThreeEntitys
     * @return
     */
    public boolean insertMultiData(final List<ArrangeThreeEntity> arrangeThreeEntitys) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (ArrangeThreeEntity arrangeThreeEntity : arrangeThreeEntitys) {
                        mManager.getDaoSession().insertOrReplace(arrangeThreeEntity);
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
     * @param arrangeThreeEntity
     * @return
     */
    public boolean updateData(ArrangeThreeEntity arrangeThreeEntity) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(arrangeThreeEntity);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param arrangeThreeEntity
     * @return
     */
    public boolean deleteData(ArrangeThreeEntity arrangeThreeEntity) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(arrangeThreeEntity);
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
            mManager.getDaoSession().deleteAll(ArrangeThreeEntity.class);
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
    public List<ArrangeThreeEntity> queryAllData() {
        return mManager.getDaoSession().loadAll(ArrangeThreeEntity.class);
    }

    /**
     * 倒序查询所有记录
     *
     * @return
     */
    public List<ArrangeThreeEntity> descQueryAllData() {
        return mManager.getDaoSession().queryBuilder(ArrangeThreeEntity.class)
                .orderDesc(ArrangeThreeEntityDao.Properties.Periods).list();
    }

    /**
     * 正序查询所有记录
     *
     * @return
     */
    public List<ArrangeThreeEntity> ascQueryAllData() {
        return mManager.getDaoSession().queryBuilder(ArrangeThreeEntity.class)
                .orderAsc(ArrangeThreeEntityDao.Properties.Periods).list();
    }

    /**
     * 根据period查询记录
     *
     * @param key
     * @return
     */
    public ArrangeThreeEntity queryDataByPeriod(String key) {
        return mManager.getDaoSession().queryBuilder(ArrangeThreeEntity.class)
                .where(ArrangeThreeEntityDao.Properties.Periods.eq(key)).unique();
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public ArrangeThreeEntity queryDataById(long key) {
        return mManager.getDaoSession().load(ArrangeThreeEntity.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<ArrangeThreeEntity> queryDataByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(ArrangeThreeEntity.class, sql, conditions);
    }

    /**
     * 获取第近的期数
     */
    public String queryLastPeriods() {
        List<ArrangeThreeEntity> list = mManager.getDaoSession().queryBuilder(ArrangeThreeEntity.class)
                .orderDesc(ArrangeThreeEntityDao.Properties.Periods).limit(1).list();
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
    public List<ArrangeThreeEntity> queryLimitData(int limit) {
        return mManager.getDaoSession().queryBuilder(ArrangeThreeEntity.class)
                .orderDesc(ArrangeThreeEntityDao.Properties.Periods).limit(limit).list();
    }
}
