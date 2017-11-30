package com.heady.rekha.headyassessment.presenter

import com.heady.rekha.headyassessment.base.MainApplication
import com.heady.rekha.headyassessment.data.RepositoryImpl
import com.heady.rekha.headyassessment.domain.Repository
import javax.inject.Inject

/**
 * Created by rekha on 30/11/17.
 */
class MainActivityPresenter : MainActivityContract.Presenter {

    private var view: MainActivityContract.View?

    constructor(view: MainActivityContract.View?) {
        this.view = view
        MainApplication.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: RepositoryImpl

    override fun detach() {
        view = null
    }

    override fun getCategories() {
        repository.fetchData(object : Repository.DataFetchCallback {
            override fun onSuccess() {

            }

            override fun onError() {

            }

        })
    }
}