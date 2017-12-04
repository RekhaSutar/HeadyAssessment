package com.heady.rekha.headyassessment.domain

/**
 * Created by rekha on 30/11/17.
 */
interface Repository {
    fun fetchData(fetchCallback: DataFetchCallback)
    interface DataFetchCallback {
        fun onSuccess()
        fun onError()
    }

    fun getCategories(dataCallBack: DataCallBack)
    fun getProducts(productIds: String, dataCallBack: DataCallBack)
    fun getSortedProducts(sortType: String, dataCallBack: DataCallBack)
    interface DataCallBack{
        fun onSuccess(any: List<Any>?)
        fun onError()
    }
}