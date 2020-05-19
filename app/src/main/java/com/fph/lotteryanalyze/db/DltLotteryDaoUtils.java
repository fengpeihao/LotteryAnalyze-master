package com.fph.lotteryanalyze.db;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by fengpeihao on 2018/1/30.
 */

public class DltLotteryDaoUtils{
    private static final String TAG = DltLotteryDaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public DltLotteryDaoUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成notice记录的插入，如果表未创建，先创建Notice表
     *
     * @param lotteryEntity
     * @return
     */
    public boolean insertLottery(DltLotteryEntity lotteryEntity) {
        boolean flag = false;
        flag = mManager.getDaoSession().getDltLotteryEntityDao().insert(lotteryEntity) == -1 ? false : true;
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param lotteryEntities
     * @return
     */
    public boolean insertMultLottery(final List<DltLotteryEntity> lotteryEntities) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (DltLotteryEntity lotteryEntity : lotteryEntities) {
                        mManager.getDaoSession().insertOrReplace(lotteryEntity);
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
     * @param lotteryEntity
     * @return
     */
    public boolean updateLottery(DltLotteryEntity lotteryEntity) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(lotteryEntity);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param lotteryEntity
     * @return
     */
    public boolean deleteLottery(DltLotteryEntity lotteryEntity) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(lotteryEntity);
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
            mManager.getDaoSession().deleteAll(DltLotteryEntity.class);
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
    public List<DltLotteryEntity> queryAllLottery() {
        return mManager.getDaoSession().loadAll(DltLotteryEntity.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public DltLotteryEntity queryLotteryById(long key) {
        return mManager.getDaoSession().load(DltLotteryEntity.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<DltLotteryEntity> queryLotteryByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(DltLotteryEntity.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public List<DltLotteryEntity> queryLotteryByQueryBuilder(String expect) {
        QueryBuilder<DltLotteryEntity> queryBuilder = mManager.getDaoSession().queryBuilder(DltLotteryEntity.class);
        return queryBuilder.where(DltLotteryEntityDao.Properties.Expect.eq(expect)).list();
    }

    public boolean queryLotteryByExpectExists(String expect) {
        QueryBuilder<DltLotteryEntity> queryBuilder = mManager.getDaoSession().queryBuilder(DltLotteryEntity.class);
        List<DltLotteryEntity> list = queryBuilder.where(DltLotteryEntityDao.Properties.Expect.eq(expect)).list();
        return list.size() != 0;
    }

    public List<DltLotteryEntity> queryLast(){
        QueryBuilder<DltLotteryEntity> queryBuilder = mManager.getDaoSession().queryBuilder(DltLotteryEntity.class);
        List<DltLotteryEntity> list = queryBuilder.orderDesc(DltLotteryEntityDao.Properties.Opentime).list();
        if (list != null && list.size() > 0) {

            return list.subList(0,5);
        }
        return null;
    }

    public String queryMaxExpect() {
        QueryBuilder<DltLotteryEntity> queryBuilder = mManager.getDaoSession().queryBuilder(DltLotteryEntity.class);
        List<DltLotteryEntity> list = queryBuilder.orderDesc(DltLotteryEntityDao.Properties.Expect).list();
        if (list != null && list.size() > 0) {
            return list.get(0).getExpect();
        }
        return "";
    }

    public List<DltLotteryEntity> ascQueryAllData(){
        return mManager.getDaoSession().queryBuilder(DltLotteryEntity.class)
                .orderAsc(DltLotteryEntityDao.Properties.Opentime).list();
    }

    /**
     * 根据主键expect查询记录
     *
     * @param key
     * @return
     */
    public DltLotteryEntity queryDataByExpect(String key) {
        return mManager.getDaoSession().queryBuilder(DltLotteryEntity.class)
                .where(DltLotteryEntityDao.Properties.Expect.eq(key)).unique();
    }
}
