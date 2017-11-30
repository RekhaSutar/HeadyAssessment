package com.heady.rekha.headyassessment.domain

/**
 * Created by rekha on 30/11/17.
 */
interface Repository {
    fun getData(callback: DataCallback)
    interface DataCallback{
        fun onSuccess()
        fun onError(data: ByteArray)
    }
}