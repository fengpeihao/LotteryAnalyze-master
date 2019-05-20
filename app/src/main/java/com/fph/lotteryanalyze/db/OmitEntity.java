package com.fph.lotteryanalyze.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.DaoException;

@Entity
public class OmitEntity {
    @Id(autoincrement = true)
    private Long id;
    /**
     * 期数
     */
    @Unique
    private String expect;
    /**
     * 开奖号码
     */
    private String opencode;
    /**
     * 开奖时间
     */
    private String opentime;
    /**
     * 球类型 ssq or dlt
     */
    private String ballType;

    @ToMany(referencedJoinProperty = "idForOmit")
    private List<BallEntity> ballEntities;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2106305053)
    private transient OmitEntityDao myDao;

    @Generated(hash = 456972200)
    public OmitEntity(Long id, String expect, String opencode, String opentime, String ballType) {
        this.id = id;
        this.expect = expect;
        this.opencode = opencode;
        this.opentime = opentime;
        this.ballType = ballType;
    }

    @Generated(hash = 195741066)
    public OmitEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpect() {
        return this.expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getOpencode() {
        return this.opencode;
    }

    public void setOpencode(String opencode) {
        this.opencode = opencode;
    }

    public String getOpentime() {
        return this.opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getBallType() {
        return this.ballType;
    }

    public void setBallType(String ballType) {
        this.ballType = ballType;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1046912504)
    public List<BallEntity> getBallEntities() {
        if (ballEntities == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BallEntityDao targetDao = daoSession.getBallEntityDao();
            List<BallEntity> ballEntitiesNew = targetDao._queryOmitEntity_BallEntities(id);
            synchronized (this) {
                if (ballEntities == null) {
                    ballEntities = ballEntitiesNew;
                }
            }
        }
        return ballEntities;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 489442014)
    public synchronized void resetBallEntities() {
        ballEntities = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1320775742)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOmitEntityDao() : null;
    }
}
