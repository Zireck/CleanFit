package com.zireck.projectk.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.zireck.projectk.model.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER.
*/
public class UserDao extends AbstractDao<User, Void> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", false, "NAME");
        public final static Property Gender = new Property(1, int.class, "gender", false, "GENDER");
        public final static Property Birthday = new Property(2, java.util.Date.class, "birthday", false, "BIRTHDAY");
        public final static Property Age = new Property(3, Integer.class, "age", false, "AGE");
        public final static Property MeasurementSystem = new Property(4, int.class, "measurementSystem", false, "MEASUREMENT_SYSTEM");
        public final static Property Weight = new Property(5, double.class, "weight", false, "WEIGHT");
        public final static Property Height = new Property(6, int.class, "height", false, "HEIGHT");
        public final static Property ActivityFactor = new Property(7, int.class, "activityFactor", false, "ACTIVITY_FACTOR");
        public final static Property Bmr = new Property(8, Double.class, "bmr", false, "BMR");
        public final static Property Goal = new Property(9, int.class, "goal", false, "GOAL");
        public final static Property Maintain = new Property(10, Double.class, "maintain", false, "MAINTAIN");
        public final static Property Burn = new Property(11, Double.class, "burn", false, "BURN");
        public final static Property Gain = new Property(12, Double.class, "gain", false, "GAIN");
    };


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER' (" + //
                "'NAME' TEXT," + // 0: name
                "'GENDER' INTEGER NOT NULL ," + // 1: gender
                "'BIRTHDAY' INTEGER NOT NULL ," + // 2: birthday
                "'AGE' INTEGER," + // 3: age
                "'MEASUREMENT_SYSTEM' INTEGER NOT NULL ," + // 4: measurementSystem
                "'WEIGHT' REAL NOT NULL ," + // 5: weight
                "'HEIGHT' INTEGER NOT NULL ," + // 6: height
                "'ACTIVITY_FACTOR' INTEGER NOT NULL ," + // 7: activityFactor
                "'BMR' REAL," + // 8: bmr
                "'GOAL' INTEGER NOT NULL ," + // 9: goal
                "'MAINTAIN' REAL," + // 10: maintain
                "'BURN' REAL," + // 11: burn
                "'GAIN' REAL);"); // 12: gain
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
        stmt.bindLong(2, entity.getGender());
        stmt.bindLong(3, entity.getBirthday().getTime());
 
        Integer age = entity.getAge();
        if (age != null) {
            stmt.bindLong(4, age);
        }
        stmt.bindLong(5, entity.getMeasurementSystem());
        stmt.bindDouble(6, entity.getWeight());
        stmt.bindLong(7, entity.getHeight());
        stmt.bindLong(8, entity.getActivityFactor());
 
        Double bmr = entity.getBmr();
        if (bmr != null) {
            stmt.bindDouble(9, bmr);
        }
        stmt.bindLong(10, entity.getGoal());
 
        Double maintain = entity.getMaintain();
        if (maintain != null) {
            stmt.bindDouble(11, maintain);
        }
 
        Double burn = entity.getBurn();
        if (burn != null) {
            stmt.bindDouble(12, burn);
        }
 
        Double gain = entity.getGain();
        if (gain != null) {
            stmt.bindDouble(13, gain);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
            cursor.getInt(offset + 1), // gender
            new java.util.Date(cursor.getLong(offset + 2)), // birthday
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // age
            cursor.getInt(offset + 4), // measurementSystem
            cursor.getDouble(offset + 5), // weight
            cursor.getInt(offset + 6), // height
            cursor.getInt(offset + 7), // activityFactor
            cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8), // bmr
            cursor.getInt(offset + 9), // goal
            cursor.isNull(offset + 10) ? null : cursor.getDouble(offset + 10), // maintain
            cursor.isNull(offset + 11) ? null : cursor.getDouble(offset + 11), // burn
            cursor.isNull(offset + 12) ? null : cursor.getDouble(offset + 12) // gain
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setGender(cursor.getInt(offset + 1));
        entity.setBirthday(new java.util.Date(cursor.getLong(offset + 2)));
        entity.setAge(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setMeasurementSystem(cursor.getInt(offset + 4));
        entity.setWeight(cursor.getDouble(offset + 5));
        entity.setHeight(cursor.getInt(offset + 6));
        entity.setActivityFactor(cursor.getInt(offset + 7));
        entity.setBmr(cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8));
        entity.setGoal(cursor.getInt(offset + 9));
        entity.setMaintain(cursor.isNull(offset + 10) ? null : cursor.getDouble(offset + 10));
        entity.setBurn(cursor.isNull(offset + 11) ? null : cursor.getDouble(offset + 11));
        entity.setGain(cursor.isNull(offset + 12) ? null : cursor.getDouble(offset + 12));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(User entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(User entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
