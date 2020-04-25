package com.quess.catapp

import androidx.multidex.MultiDex
import com.quess.catapp.di.components.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class Application : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        MultiDex.install(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            if (LeakCanary.isInAnalyzerProcess(this)) return
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}