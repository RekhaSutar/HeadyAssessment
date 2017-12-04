package com.heady.rekha.headyassessment.presenter.category

import com.heady.rekha.headyassessment.base.MainApplication
import com.heady.rekha.headyassessment.data.RepositoryImpl
import com.heady.rekha.headyassessment.domain.CategoryEntity
import com.heady.rekha.headyassessment.domain.Repository
import com.heady.rekha.headyassessment.presenter.MainActivityContract
import javax.inject.Inject

/**
 * Created by rekha on 30/11/17.
 */
class CategoryPresenter(private var view: CategoryContract.View?) : CategoryContract.Presenter {

    init {
        MainApplication.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: RepositoryImpl

    override fun detach() {
        view = null
    }

    override fun getCategories() {
        repository.getCategories(object : Repository.DataCallBack {
            override fun onSuccess(any: List<Any>?) {
                view!!.onCategoriesFetched(any as List<CategoryEntity>)
            }

            override fun onError() {
                view!!.onError()
            }
        })
    }
}