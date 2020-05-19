package com.fph.lotteryanalyze.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ARRANGE_THREE_ENTITY".
*/
public class ArrangeThreeEntityDao extends AbstractDao<ArrangeThreeEntity, Long> {

    public static final String TABLENAME = "ARRANGE_THREE_ENTITY";

    /**
     * Properties of entity ArrangeThreeEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Periods = new Property(1, String.class, "periods", false, "PERIODS");
        public final static Property Number = new Property(2, String.class, "number", false, "NUMBER");
        public final static Property FirstNumber = new Property(3, String.class, "firstNumber", false, "FIRST_NUMBER");
        public final static Property SecondNumber = new Property(4, String.class, "secondNumber", false, "SECOND_NUMBER");
        public final static Property ThreeNumber = new Property(5, String.class, "threeNumber", false, "THREE_NUMBER");
        public final static Property Date = new Property(6, String.class, "date", false, "DATE");
    }


    public ArrangeThreeEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ArrangeThreeEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ARRANGE_THREE_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"PERIODS\" TEXT UNIQUE ," + // 1: periods
                "\"NUMBER\" TEXT," + // 2: number
                "\"FIRST_NUMBER\" TEXT," + // 3: firstNumber
                "\"SECOND_NUMBER\" TEXT," + // 4: secondNumber
                "\"THREE_NUMBER\" TEXT," + // 5: threeNumber
                "\"DATE\" TEXT);"); // 6: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ARRANGE_THREE_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ArrangeThreeEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String periods = entity.getPeriods();
        if (periods != null) {
            stmt.bindString(2, periods);
        }
 
        String number = entity.getNumber();
        if (number != null) {
            stmt.bindString(3, number);
        }
 
        String firstNumber = entity.getFirstNumber();
        if (firstNumber != null) {
            stmt.bindString(4, firstNumber);
        }
 
        String secondNumber = entity.getSecondNumber();
        if (secondNumber != null) {
            stmt.bindString(5, secondNumber);
        }
 
        String threeNumber = entity.getThreeNumber();
        if (threeNumber != null) {
            stmt.bindString(6, threeNumber);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(7, date);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ArrangeThreeEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String periods = entity.getPeriods();
        if (periods != null) {
            stmt.bindString(2, periods);
        }
 
        String number = entity.getNumber();
        if (number != null) {
            stmt.bindString(3, number);
        }
 
        String firstNumber = entity.getFirstNumber();
        if (firstNumber != null) {
            stmt.bindString(4, firstNumber);
        }
 
        String secondNumber = entity.getSecondNumber();
        if (secondNumber != null) {
            stmt.bindString(5, secondNumber);
        }
 
        String threeNumber = entity.getThreeNumber();
        if (threeNumber != null) {
            stmt.bindString(6, threeNumber);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(7, date);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ArrangeThreeEntity readEntity(Cursor cursor, int offset) {
        ArrangeThreeEntity entity = new ArrangeThreeEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // periods
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // number
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // firstNumber
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // secondNumber
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // threeNumber
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // date
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ArrangeThreeEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPeriods(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNumber(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFirstNumber(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSecondNumber(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setThreeNumber(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDate(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ArrangeThreeEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ArrangeThreeEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ArrangeThreeEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
