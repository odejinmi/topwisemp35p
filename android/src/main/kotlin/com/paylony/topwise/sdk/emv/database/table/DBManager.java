package com.paylony.topwise.sdk.emv.database.table;

import android.content.Context;


public final class DBManager {
    private static DBManager instance = new DBManager();
    private AidDaoImpl aidDao = null;
    private CapkDaoImpl capkDao = null;
    private boolean hasinit = false;

    private DBManager() {
    }

    public static DBManager getInstance() {
        return instance;
    }

    public void init(Context context) {
        if (!this.hasinit) {
            this.hasinit = true;
            this.initDaoImpl(context);
        }
    }

    public void initDaoImpl(Context context) {
        this.setAidDao(new AidDaoImpl(context));
        this.setCapkDao(new CapkDaoImpl(context));
    }

    public AidDaoImpl getAidDao() {
        return this.aidDao;
    }

    private void setAidDao(AidDaoImpl aidDao) {
        this.aidDao = aidDao;
    }

    public CapkDaoImpl getCapkDao() {
        return this.capkDao;
    }

    private void setCapkDao(CapkDaoImpl capkDao) {
        this.capkDao = capkDao;
    }
}
