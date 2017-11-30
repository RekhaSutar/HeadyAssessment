package com.heady.rekha.headyassessment.data

import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.heady.rekha.headyassessment.VolleySingleton
import com.heady.rekha.headyassessment.domain.Repository
import javax.inject.Inject

/**
 * Created by rekha on 30/11/17.
 */
class RepositoryImpl : Repository {

    @Inject
    lateinit var network: VolleySingleton

    private val url: String = "https://stark-spire-93433.herokuapp.com/json"

    override fun getData(callback: Repository.DataCallback) {
        JsonObjectRequest(GET,
                url,
                null,
                Response.Listener { response ->
                    if (response != null) {
                        callback.onSuccess()
                    }
                },
                Response.ErrorListener { error ->
                    if (error != null) {
                        callback.onError(error.networkResponse.data)
                    }
                }
        )
    }
}