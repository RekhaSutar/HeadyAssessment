package com.heady.rekha.headyassessment

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import me.dlkanth.stethovolley.StethoVolleyStack



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

    fun <T> addToRequestQueue(context: Context, request: com.android.volley.Request<T>) {
        getRequestVolleyQueue(context).add(request)
    }

    private fun getRequestVolleyQueue(context: Context): RequestQueue {
        return Volley.newRequestQueue(context, StethoVolleyStack())
    }

}