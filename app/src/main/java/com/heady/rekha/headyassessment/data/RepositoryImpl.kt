package com.heady.rekha.headyassessment.data

import android.app.Application
import android.content.ContentValues
import android.content.Context
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.heady.rekha.headyassessment.VolleySingleton
import com.heady.rekha.headyassessment.base.MainApplication
import com.heady.rekha.headyassessment.data.db.AppContentProvider
import com.heady.rekha.headyassessment.data.db.DatabaseContract
import com.heady.rekha.headyassessment.domain.Repository
import com.heady.rekha.headyassessment.domain.entity.CategoriesData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
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

    constructor() {
        MainApplication.appComponent.inject(this)
    }

    @Inject
    lateinit var network: VolleySingleton

    @Inject
    lateinit var context: Application

    private val url: String = "https://stark-spire-93433.herokuapp.com/json"

    override fun fetchData(fetchCallback: Repository.DataFetchCallback) {
        val jsonObjectRequest = JsonObjectRequest(GET,
                url,
                null,
                Response.Listener<JSONObject> { response ->
                    if (response != null) {
                        putDataIntoDB(response)
                        fetchCallback.onSuccess()
                    }
                },
                Response.ErrorListener { error ->
                    if (error != null) {
                        fetchCallback.onError()
                    }
                }
        )
        network.addToRequestQueue(context, jsonObjectRequest)
    }

    private fun putDataIntoDB(jsonObject: JSONObject) {
        val adapter: JsonAdapter<CategoriesData> = Moshi.Builder().build().adapter(CategoriesData::class.java)
        val categoriesData = adapter.fromJson(jsonObject.toString())

        var cvs = arrayListOf<ContentValues>()
        categoriesData?.categories?.map {
            val cvCategories = ContentValues()
            cvCategories.put(DatabaseContract.Categories.COLUMN_CAT_ID, it.id)
            cvCategories.put(DatabaseContract.Categories.COLUMN_CAT_NAME, it.name)

            var listOfProductIds = arrayListOf<Int>()
            if (!it.products.isEmpty()){
                var productIds = it.products.map {
                    it.id

                    val cvProducts = ContentValues()
                    cvProducts.put(DatabaseContract.Products.COLUMN_PRODUCTS_ID, it.id)
                    cvProducts.put(DatabaseContract.Products.COLUMN_PRODUCTS_JSON, it.toString())

                    context.contentResolver.insert(AppContentProvider.CONTENT_URI_PRODUCTS, cvProducts)
                }
            }else if (!it.childCategories.isEmpty()){

            }
            //find how to break the current loop
            cvCategories.put(DatabaseContract.Categories.COLUMN_PRODUCT_IDS, listOfProductIds.toString())

            cvs.add(cvCategories)
        }
        context.contentResolver.bulkInsert(AppContentProvider.CONTENT_URI_CATEGORIES, cvs.toTypedArray())
    }
}