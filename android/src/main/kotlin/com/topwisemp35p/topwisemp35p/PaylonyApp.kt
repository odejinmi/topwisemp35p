package com.example.paylony_pos

import android.app.Application
import com.paylony.topwise.emvreader.app.PosApplication

class PaylonyApp : Application(){
    override fun onCreate() {
        super.onCreate()

        PosApplication.init(this@PaylonyApp)
        PosApplication.initApp()
        PosApplication.getApp().setConsumeData()
    }
}