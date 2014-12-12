
package com.pedrocactus.trome;

import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract.CommonDataKinds.Note;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import java.util.Map;

public class DaoSession extends AbstractDaoSession {
    private final DaoConfig stationDaoConfig;
    private final StationDao stationDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type,
            Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);
        stationDaoConfig = daoConfigMap.get(StationDao.class).clone();
        stationDaoConfig.initIdentityScope(type);
        stationDao = new StationDao(stationDaoConfig, this);
        registerDao(Station.class, stationDao);
    }

    public void clear() {
        stationDaoConfig.getIdentityScope().clear();
    }

    public StationDao getNoteDao() {
        return stationDao;
    }

}
