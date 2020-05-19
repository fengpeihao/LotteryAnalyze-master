package com.fph.lotteryanalyze.db;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by fengpeihao on 2018/1/30.
 */

public class LotteryDaoUtils {
    private static final String TAG = LotteryDaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public LotteryDaoUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成notice记录的插入，如果表未创建，先创建Notice表
     *
     * @param lotteryEntity
     * @return
     */
    public boolean insertLottery(LotteryEntity lotteryEntity) {
        boolean flag = false;
        flag = mManager.getDaoSession().getLotteryEntityDao().insert(lotteryEntity) == -1 ? false : true;
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param lotteryEntities
     * @return
     */
    public boolean insertMultLottery(final List<LotteryEntity> lotteryEntities) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (LotteryEntity lotteryEntity : lotteryEntities) {
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
    public boolean updateLottery(LotteryEntity lotteryEntity) {
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
    public boolean deleteLottery(LotteryEntity lotteryEntity) {
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
            mManager.getDaoSession().deleteAll(LotteryEntity.class);
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
    public List<LotteryEntity> queryAllLottery() {
        return mManager.getDaoSession().loadAll(LotteryEntity.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public LotteryEntity queryLotteryById(long key) {
        return mManager.getDaoSession().load(LotteryEntity.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<LotteryEntity> queryLotteryByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(LotteryEntity.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public List<LotteryEntity> queryLotteryByQueryBuilder(String expect) {
        QueryBuilder<LotteryEntity> queryBuilder = mManager.getDaoSession().queryBuilder(LotteryEntity.class);
        return queryBuilder.where(LotteryEntityDao.Properties.Expect.eq(expect)).list();
    }

    public boolean queryLotteryByExpectExists(String expect) {
        QueryBuilder<LotteryEntity> queryBuilder = mManager.getDaoSession().queryBuilder(LotteryEntity.class);
        List<LotteryEntity> list = queryBuilder.where(LotteryEntityDao.Properties.Expect.eq(expect)).list();
        return list.size() != 0;
    }

    public List<LotteryEntity> queryLast(){
        QueryBuilder<LotteryEntity> queryBuilder = mManager.getDaoSession().queryBuilder(LotteryEntity.class);
        List<LotteryEntity> list = queryBuilder.orderDesc(LotteryEntityDao.Properties.Opentime).list();
        if (list != null && list.size() > 0) {

            return list.subList(0,5);
        }
        return null;
    }

    public String queryMaxOpenTime() {
        QueryBuilder<LotteryEntity> queryBuilder = mManager.getDaoSession().queryBuilder(LotteryEntity.class);
        List<LotteryEntity> list = queryBuilder.orderDesc(LotteryEntityDao.Properties.Opentime).list();
        if (list != null && list.size() > 0) {
            return list.get(0).getOpentime();
        }
        return "";
    }

    public String queryMaxExpect() {
        QueryBuilder<LotteryEntity> queryBuilder = mManager.getDaoSession().queryBuilder(LotteryEntity.class);
        List<LotteryEntity> list = queryBuilder.orderDesc(LotteryEntityDao.Properties.Expect).list();
        if (list != null && list.size() > 0) {
            return list.get(0).getExpect();
        }
        return "";
    }

    public List<LotteryEntity> ascQueryAllData(){
        return mManager.getDaoSession().queryBuilder(LotteryEntity.class)
                .orderAsc(LotteryEntityDao.Properties.Opentime).list();
    }

    /**
     * 根据主键expect查询记录
     *
     * @param key
     * @return
     */
    public LotteryEntity queryDataByExpect(String key) {
        return mManager.getDaoSession().queryBuilder(LotteryEntity.class)
                .where(LotteryEntityDao.Properties.Expect.eq(key)).unique();
    }
}
