package com.heady.rekha.headyassessment.data

import android.app.Application
import android.content.Context
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.heady.rekha.headyassessment.VolleySingleton
import com.heady.rekha.headyassessment.base.MainApplication
import com.heady.rekha.headyassessment.domain.Repository
import org.json.JSONObject
import javax.inject.Inject

/**
 * Created by rekha on 30/11/17.
 */

private var sInstance: RepositoryImpl? = null
fun initRepository() {
    if (sInstance == null) {
        synchronized(RepositoryImpl::class) {
            if (sInstance == null) {
                sInstance = RepositoryImpl()
            }
        }
    }
}

fun getRepositoryInstance(): RepositoryImpl = sInstance!!

class RepositoryImpl : Repository {

    constructor(){
        MainApplication.appComponent.inject(this)
    }

    @Inject
    lateinit var network: VolleySingleton

    @Inject
    lateinit var context : Application

    private val url: String = "https://stark-spire-93433.herokuapp.com/json"

    override fun fetchData(fetchCallback: Repository.DataFetchCallback) {
        val jsonObjectRequest = JsonObjectRequest(GET,
                url,
                null,
                Response.Listener<JSONObject> { response ->
                    if (response != null) {
                        fetchCallback.onSuccess()
                    }
                },
                Response.ErrorListener { error ->
                    if (error != null) {
                        fetchCallback.onError()
                    }
                }
        )
        network.addToRequestQueue(context,jsonObjectRequest)
    }
}