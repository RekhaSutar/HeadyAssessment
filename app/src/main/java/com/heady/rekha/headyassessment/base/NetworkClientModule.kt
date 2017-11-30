package com.heady.rekha.headyassessment.base

import com.heady.rekha.headyassessment.VolleySingleton
import com.heady.rekha.headyassessment.getVolleySingletonInstance
import dagger.Module
import dagger.Provides

/**
 * Created by rekha on 30/11/17.
 */
@Module
class NetworkClientModule{
    @Provides
    fun providesNetworkClient(): VolleySingleton = getVolleySingletonInstance()
}