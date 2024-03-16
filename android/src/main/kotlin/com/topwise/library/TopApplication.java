package com.topwise.library;


import android.app.Application;
import android.util.Log;
import androidx.annotation.Keep;
import com.topwise.sdk.emv.database.table.DBManager;

@Keep
public class TopApplication extends Application {
    private final String TAG = TopApplication.class.getSimpleName();
    public static TopApplication mPosApp;
    public static DeviceManager deviceManager;

    public TopApplication() {
    }

    public void onCreate() {
        super.onCreate();
        Log.i(this.TAG, "onCreate");
        mPosApp = this;
        deviceManager = new DeviceManager(mPosApp);
        DeviceManager.getInstance().bindService();
        DBManager.getInstance().init(this);
    }

    public static Application getApp() {
        return mPosApp;
    }
}
