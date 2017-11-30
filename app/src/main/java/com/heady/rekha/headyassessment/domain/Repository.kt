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
}