package com.jetbrains.dependoapp

import android.app.Application
import androidx.startup.AppInitializer
import com.jetbrains.dependoapp.koinDi.initKoin


class DependoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppInitializer.getInstance(this)
            .initializeComponent(KLocationInitializer::class.java)
          initKoin()
    }
}
