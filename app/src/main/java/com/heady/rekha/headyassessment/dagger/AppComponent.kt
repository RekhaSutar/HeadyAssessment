package com.heady.rekha.headyassessment.dagger

import com.heady.rekha.headyassessment.data.RepositoryImpl
import com.heady.rekha.headyassessment.presenter.MainActivityPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by rekha on 30/11/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(repository: RepositoryImpl)
    fun inject(mainActivityPresenter: MainActivityPresenter)
}