package com.heady.rekha.headyassessment.base

import com.heady.rekha.headyassessment.data.RepositoryImpl
import dagger.Component
import javax.inject.Singleton

/**
 * Created by rekha on 30/11/17.
 */
@Singleton
@Component(modules = arrayOf(NetworkClientModule::class))
interface AppComponent {

    fun inject(repository: RepositoryImpl)
}