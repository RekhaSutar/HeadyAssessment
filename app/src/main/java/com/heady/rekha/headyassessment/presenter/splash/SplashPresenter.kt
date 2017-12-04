package com.heady.rekha.headyassessment.presenter.splash

import com.heady.rekha.headyassessment.base.MainApplication
import com.heady.rekha.headyassessment.data.RepositoryImpl
import com.heady.rekha.headyassessment.domain.Repository
import javax.inject.Inject

/**
 * Created by rekha on 4/12/17.
 */
class SplashPresenter(private var view: SplashContract.View?) : SplashContract.Presenter {
    init {
        MainApplication.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: RepositoryImpl

    override fun detach() {
        view = null
    }

    override fun fetchData() {
        repository.fetchData(object : Repository.DataFetchCallback {
            override fun onSuccess() {
                view!!.onDataFetched()
            }

            override fun onError() {
                view!!.onError()
            }
        })
    }
}