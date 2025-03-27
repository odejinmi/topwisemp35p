package com.lonytech.topwisesdk

import android.app.Application
import com.lonytech.topwisesdk.emvreader.app.PosApplication

class PosApp : Application(){

    override fun onCreate() {
        super.onCreate()
        PosApplication.init(this@PosApp)
        PosApplication.initApp()
        PosApplication.getApp().setConsumeData()
    }

    override fun onTerminate() {
        super.onTerminate()
        PosApplication.cancelCheckCard()
    }
}