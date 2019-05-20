package com.fph.lotteryanalyze.db;

import android.content.Context;

import java.util.List;

/**
 * Created by fengpeihao on 2018/1/30.
 */

public class BallDaoUtils {
    private static final String TAG = BallDaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public BallDaoUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成notice记录的插入，如果表未创建，先创建Notice表
     *
     * @param ballEntity
     * @return
     */
    public boolean insertData(BallEntity ballEntity) {
        boolean flag = false;
        flag = mManager.getDaoSession().getBallEntityDao().insert(ballEntity) == -1 ? false : true;
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param omitEntities
     * @return
     */
    public boolean insertMultiData(final List<BallEntity> omitEntities) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (BallEntity ballEntity : omitEntities) {
                        mManager.getDaoSession().insertOrReplace(ballEntity);
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
     * @param ballEntity
     * @return
     */
    public boolean updateData(BallEntity ballEntity) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(ballEntity);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param ballEntity
     * @return
     */
    public boolean deleteData(BallEntity ballEntity) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(ballEntity);
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
            mManager.getDaoSession().deleteAll(BallEntity.class);
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
    public List<BallEntity> queryAllData() {
        return mManager.getDaoSession().loadAll(BallEntity.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public BallEntity queryDataById(long key) {
        return mManager.getDaoSession().load(BallEntity.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<BallEntity> queryDataByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(BallEntity.class, sql, conditions);
    }
}
