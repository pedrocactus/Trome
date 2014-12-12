
package com.pedrocactus.trome;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

public class StationDao extends AbstractDao<Station, Long> {
    public static final String TABLENAME = "STATION";

    /**
     * Properties of entity Note.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Type = new Property(2, String.class, "type", false, "TYPE");
        public final static Property Latitude = new Property(3, Double.class, "latitude", false,
                "LATITUDE");
        public final static Property Longitude = new Property(4, Double.class, "longitude", false,
                "LONGITUDE");
    };

    public StationDao(DaoConfig config) {
        super(config);
    }

    public StationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "'STATION' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT NOT NULL ," + // 1: name
                "'TYPE' TEXT," + // 2: type
                "'LATITUDE' DOUBLE," + // 3: latitude
                "'LONGITUDE' DOUBLE);"); // 3: longitude
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'STATION'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Station entity) {
        stmt.clearBindings();
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(3, type);
        }
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(4, latitude);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    /** @inheritdoc */
    @Override
    public Station readEntity(Cursor cursor, int offset) {
        Station entity = new Station( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.getString(offset + 1), // text
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // type
                cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // latitude
                cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 4) //longitude
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Station entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLatitude(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setLongitude(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Station entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(Station entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}
