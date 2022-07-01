package com.sugarmaniac

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.sugarmaniac.timeSeries.AppOpenManager





class SeriesApplication : Application() {

    private lateinit var appOpenManager: AppOpenManager

    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(
            this
        ) { }

        appOpenManager = AppOpenManager(this)
    }
}