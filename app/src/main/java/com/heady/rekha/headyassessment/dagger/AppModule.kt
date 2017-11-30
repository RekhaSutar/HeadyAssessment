package com.heady.rekha.headyassessment.dagger

import android.app.Application
import com.heady.rekha.headyassessment.VolleySingleton
import com.heady.rekha.headyassessment.data.RepositoryImpl
import com.heady.rekha.headyassessment.data.getRepositoryInstance
import com.heady.rekha.headyassessment.getVolleySingletonInstance
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rekha on 30/11/17.
 */
@Module
class AppModule(private val mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = mApplication

    @Provides
    fun providesNetworkClient(): VolleySingleton = getVolleySingletonInstance()

    @Provides
    fun getRepositoryModule() : RepositoryImpl = getRepositoryInstance()
}