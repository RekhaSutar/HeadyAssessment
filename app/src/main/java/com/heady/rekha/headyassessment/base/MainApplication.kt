package com.heady.rekha.headyassessment.base

import android.app.Application
import com.heady.rekha.headyassessment.data.RepositoryImpl

/**
 * Created by rekha on 30/11/17.
 */

class MainApplication : Application(){
    private val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .networkClientModule(NetworkClientModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(RepositoryImpl())
    }
}
