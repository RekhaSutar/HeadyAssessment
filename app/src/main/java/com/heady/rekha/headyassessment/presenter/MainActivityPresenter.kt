package com.heady.rekha.headyassessment.presenter

import com.heady.rekha.headyassessment.base.MainApplication
import com.heady.rekha.headyassessment.data.RepositoryImpl
import com.heady.rekha.headyassessment.domain.Repository
import com.heady.rekha.headyassessment.domain.entity.Product
import javax.inject.Inject

/**
 * Created by rekha on 30/11/17.
 */
class MainActivityPresenter(private var view: MainActivityContract.View?) : MainActivityContract.Presenter {
    override fun getSortedProducts(sortType: String) {
        repository.getSortedProducts(sortType, object : Repository.DataCallBack{
            override fun onSuccess(any: List<Any>?) {
                view?.onProductsFetched(any as List<Product>)
            }
            override fun onError() {
                view?.onError()
            }

        })
    }

    init {
        MainApplication.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: RepositoryImpl

    override fun detach() {
        view = null
    }

    override fun getProducts(productIds: String) {
        repository.getProducts(productIds, object : Repository.DataCallBack{
            override fun onSuccess(any: List<Any>?) {
                view?.onProductsFetched(any as List<Product>)
            }
            override fun onError() {
                view?.onError()
            }

        })
    }

}