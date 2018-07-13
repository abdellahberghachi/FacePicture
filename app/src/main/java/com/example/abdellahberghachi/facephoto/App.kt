package com.example.abdellahberghachi.facephoto

import android.app.Application
import com.example.abdellahberghachi.facephoto.di.AppComponent
import com.example.abdellahberghachi.facephoto.di.AppModule
import com.example.abdellahberghachi.facephoto.di.DaggerAppComponent
import timber.log.Timber

class App:Application() {

    companion object {
        lateinit var mAppComponent: AppComponent
    }

    init {
        initDagger()
        Timber.plant(Timber.DebugTree())
    }


    private fun initDagger() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}