package com.heady.rekha.headyassessment.base

import android.app.Application
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

        initVolleySingleton()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        initRepository()

    }
}
