package com.heady.rekha.headyassessment.base

import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho
import com.heady.rekha.headyassessment.BuildConfig
import com.heady.rekha.headyassessment.dagger.*
import com.heady.rekha.headyassessment.data.initRepository
import com.heady.rekha.headyassessment.initVolleySingleton

/**
 * Created by rekha on 30/11/17.
 */

class MainApplication : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
                Stetho.initialize(Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build())
                Log.d("MainApplication", "Stetho initialised with inspectors")
        }
        initVolleySingleton()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        initRepository()
    }

}
