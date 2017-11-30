package com.heady.rekha.headyassessment

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.android.volley.RequestQueue

/**
 * Created by rekha on 30/11/17.
 */


private var sInstance: VolleySingleton? = null
fun initVolleySingleton() {
    if (sInstance == null) {
        synchronized(VolleySingleton::class) {
            if (sInstance == null) {
                sInstance = VolleySingleton()
            }
        }
    }
}

fun getVolleySingletonInstance(): VolleySingleton = sInstance!!

class VolleySingleton {

    private var mRequestQueue: RequestQueue? = null
    private fun getRequestQueue(context: Context): RequestQueue {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.applicationContext)
        }
        return mRequestQueue!!
    }

    fun <T> addToRequestQueue(context: Context, request: Request<T>) {
        getRequestQueue(context).add(request)
    }
}