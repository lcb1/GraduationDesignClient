package com.example.helloviewtest

import android.app.Application
import com.example.helloviewtest.utils.AppUtil

class MainApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        AppUtil.applicationContext=applicationContext
    }
}