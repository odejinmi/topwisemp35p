package com.paylony.navigation

import android.app.Activity
import android.os.Bundle

interface Navigation {
    fun onLogin(activity: Activity, bundle: Bundle? = null)
    fun onDashBoard(activity: Activity, bundle: Bundle? = null)
    fun onAirtime(activity: Activity, bundle: Bundle? = null)
    fun onElectricity(activity: Activity, bundle: Bundle? = null)
    fun onTransfer(activity: Activity, bundle: Bundle? = null)
    fun onInternetData(activity: Activity, bundle: Bundle? = null)
    fun onSummary(activity: Activity, bundle: Bundle? = null)
    fun onCableTv(activity: Activity, bundle: Bundle? = null)
    fun onTransactions(activity: Activity, bundle: Bundle? = null)
    fun onSettings(activity: Activity, bundle: Bundle? = null)
    fun onWithdraw(activity: Activity, bundle: Bundle? = null)
}