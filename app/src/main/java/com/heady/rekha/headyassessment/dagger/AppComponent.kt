package com.heady.rekha.headyassessment.dagger

import com.heady.rekha.headyassessment.data.RepositoryImpl
import com.heady.rekha.headyassessment.presenter.MainActivityPresenter
import com.heady.rekha.headyassessment.presenter.category.CategoryPresenter
import com.heady.rekha.headyassessment.presenter.splash.SplashPresenter
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
    fun inject(categoryPresenter: CategoryPresenter)
    fun inject(splashPresenter: SplashPresenter)
}