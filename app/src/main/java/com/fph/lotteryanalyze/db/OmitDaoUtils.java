package com.fph.lotteryanalyze.db;

import android.content.Context;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by fengpeihao on 2018/1/30.
 */

public class OmitDaoUtils {
    private static final String TAG = OmitDaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public OmitDaoUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成notice记录的插入，如果表未创建，先创建Notice表
     *
     * @param omitEntity
     * @return
     */
    public boolean insertData(OmitEntity omitEntity) {
        boolean flag = false;
        flag = mManager.getDaoSession().getOmitEntityDao().insert(omitEntity) == -1 ? false : true;
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     *
     * @param omitEntities
     * @return
     */
    public boolean insertMultiData(final List<OmitEntity> omitEntities) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (OmitEntity omitEntity : omitEntities) {
                        mManager.getDaoSession().insertOrReplace(omitEntity);
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
     * @param omitEntity
     * @return
     */
    public boolean updateData(OmitEntity omitEntity) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(omitEntity);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     *
     * @param omitEntity
     * @return
     */
    public boolean deleteData(OmitEntity omitEntity) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(omitEntity);
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
            mManager.getDaoSession().deleteAll(OmitEntity.class);
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
    public List<OmitEntity> queryAllData() {
        return mManager.getDaoSession().loadAll(OmitEntity.class);
    }

    /**
     * 根据主键id查询记录
     *
     * @param key
     * @return
     */
    public OmitEntity queryDataById(long key) {
        return mManager.getDaoSession().load(OmitEntity.class, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<OmitEntity> queryDataByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(OmitEntity.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     *
     * @return
     */
    public List<OmitEntity> queryDataByQueryBuilder(String expect) {
        QueryBuilder<OmitEntity> queryBuilder = mManager.getDaoSession().queryBuilder(OmitEntity.class);
        return queryBuilder.where(OmitEntityDao.Properties.Expect.eq(expect)).list();
    }

    public boolean queryDataByExpectExists(String expect) {
        QueryBuilder<OmitEntity> queryBuilder = mManager.getDaoSession().queryBuilder(OmitEntity.class);
        List<OmitEntity> list = queryBuilder.where(OmitEntityDao.Properties.Expect.eq(expect)).list();
        return list.size() != 0;
    }

    public List<OmitEntity> queryLast(){
        QueryBuilder<OmitEntity> queryBuilder = mManager.getDaoSession().queryBuilder(OmitEntity.class);
        List<OmitEntity> list = queryBuilder.orderDesc(OmitEntityDao.Properties.Opentime).list();
        if (list != null && list.size() > 0) {

            return list.subList(0,5);
        }
        return null;
    }

    public String queryMaxOpenTime() {
        QueryBuilder<OmitEntity> queryBuilder = mManager.getDaoSession().queryBuilder(OmitEntity.class);
        List<OmitEntity> list = queryBuilder.orderDesc(OmitEntityDao.Properties.Opentime).list();
        if (list != null && list.size() > 0) {
            return list.get(0).getOpentime();
        }
        return "";
    }

    public String queryMaxExpect() {
        QueryBuilder<OmitEntity> queryBuilder = mManager.getDaoSession().queryBuilder(OmitEntity.class);
        List<OmitEntity> list = queryBuilder.orderDesc(OmitEntityDao.Properties.Expect).list();
        if (list != null && list.size() > 0) {
            return list.get(0).getExpect();
        }
        return "";
    }
}
