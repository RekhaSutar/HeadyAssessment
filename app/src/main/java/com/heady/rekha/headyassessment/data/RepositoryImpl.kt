package com.heady.rekha.headyassessment.data

import android.app.Application
import android.content.ContentValues
import android.text.TextUtils
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.heady.rekha.headyassessment.VolleySingleton
import com.heady.rekha.headyassessment.base.MainApplication
import com.heady.rekha.headyassessment.data.db.AppContentProvider
import com.heady.rekha.headyassessment.data.db.DatabaseContract
import com.heady.rekha.headyassessment.domain.CategoryEntity
import com.heady.rekha.headyassessment.domain.Repository
import com.heady.rekha.headyassessment.domain.entity.CategoriesData
import com.heady.rekha.headyassessment.domain.entity.Product
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.json.JSONObject
import javax.inject.Inject

/**
 * Created by rekha on 30/11/17.
 */


const val SORT_VIEW_COUNT = "Most Viewed Products"
const val SORT_ORDER_COUNT = "Most OrdeRed Products"
const val SORT_SHARES = "Most ShaRed Products"

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
    override fun getSortedProducts(sortType: String, dataCallBack: Repository.DataCallBack) {

        val products: MutableList<Product> = mutableListOf()
        val selectionClause = null
        val selectionValue = null
        var sort : String?= null
        when (sortType) {
            SORT_VIEW_COUNT -> sort = DatabaseContract.Products.COLUMN_VIEW_COUNT + " DESC "
            SORT_ORDER_COUNT -> sort = DatabaseContract.Products.COLUMN_ORDER_COUNT + " DESC "
            SORT_SHARES -> sort = DatabaseContract.Products.COLUMN_SHARES + " DESC "
        }
        val cursor = context.contentResolver.query(AppContentProvider.CONTENT_URI_PRODUCTS,
                DatabaseContract.Products.mProjection,
                selectionClause, selectionValue, sort, null)
        var count = cursor.count
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val productJson = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Products.COLUMN_PRODUCTS_JSON))
                    val adapter: JsonAdapter<Product> = Moshi.Builder().build().adapter(Product::class.java)
                    val product = adapter.fromJson(productJson.toString())

                    products.add(product!!)
                } while (cursor.moveToNext())
            }
        }
        if (products.size > 0) dataCallBack.onSuccess(products) else dataCallBack.onError()
    }


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
            if (!it.products.isEmpty()) {
                it.products.map {
                    listOfProductIds.add(it.id)
                    val cvProducts = ContentValues()
                    cvProducts.put(DatabaseContract.Products.COLUMN_PRODUCTS_ID, it.id)

                    val adapter: JsonAdapter<Product> = Moshi.Builder().build().adapter(Product::class.java)
                    val productJson = adapter.toJson(it)
                    cvProducts.put(DatabaseContract.Products.COLUMN_PRODUCTS_JSON, productJson)

                    context.contentResolver.insert(AppContentProvider.CONTENT_URI_PRODUCTS, cvProducts)
                }
            } else if (!it.childCategories.isEmpty()) {
                it.childCategories.map { listOfProductIds.add(it) }
            }
            //find how to break the current loop
            cvCategories.put(DatabaseContract.Categories.COLUMN_PRODUCT_IDS, listOfProductIds.joinToString(separator = ","))
            context.contentResolver.insert(AppContentProvider.CONTENT_URI_CATEGORIES, cvCategories)
            cvs.add(cvCategories)
        }
//        context.contentResolver.bulkInsert(AppContentProvider.CONTENT_URI_CATEGORIES, cvs.toTypedArray())

        categoriesData?.rankings?.map {
            when (it.ranking) {
                SORT_VIEW_COUNT -> {
                    it.products.map {
                        updateProductTable(it.id.toString(), it.viewCount, DatabaseContract.Products.COLUMN_VIEW_COUNT, context)
                    }
                }
                SORT_ORDER_COUNT -> {
                    it.products.map {
                        updateProductTable(it.id.toString(), it.orderCount, DatabaseContract.Products.COLUMN_ORDER_COUNT, context)
                    }
                }
                SORT_SHARES -> {
                    it.products.map {
                        updateProductTable(it.id.toString(), it.shares, DatabaseContract.Products.COLUMN_SHARES, context)
                    }
                }
                else -> {
                }
            }
        }

    }

    private fun updateProductTable(id: String, value: Int, column: String, context: Application) {
        val contentValue = ContentValues()
        contentValue.put(column, value)
        val selectionClause = DatabaseContract.Products.COLUMN_PRODUCTS_ID + " = ?"
        val selectionValue = arrayOf(id)
        context.contentResolver.update(AppContentProvider.CONTENT_URI_PRODUCTS,
                contentValue, selectionClause, selectionValue)
    }

    override fun getCategories(dataCallBack: Repository.DataCallBack) {
        val categories: MutableList<CategoryEntity> = mutableListOf()
        val cursor = context.contentResolver.query(AppContentProvider.CONTENT_URI_CATEGORIES,
                null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val categoryEntity = CategoryEntity()
                    categoryEntity.id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Categories.COLUMN_CAT_ID))
                    categoryEntity.name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Categories.COLUMN_CAT_NAME))
//                    categoryEntity.products =
                    val s = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Categories.COLUMN_PRODUCT_IDS))
                    categoryEntity.products = s
                    categories.add(categoryEntity)
                } while (cursor.moveToNext())
            }
        }
        if (categories.size > 0) dataCallBack.onSuccess(categories) else dataCallBack.onError()
    }

    override fun getProducts(productIds: String, dataCallBack: Repository.DataCallBack) {

        val products: MutableList<Product> = mutableListOf()
        var list = productIds.split(",")
        var str = ""
        list.map {
            str += if (TextUtils.isEmpty(str)) "?" else ",?"
        }
        val selectionClause = DatabaseContract.Products.COLUMN_PRODUCTS_ID + " IN($str) "
        val selectionValue = list.toTypedArray()
        val cursor = context.contentResolver.query(AppContentProvider.CONTENT_URI_PRODUCTS,
                DatabaseContract.Products.mProjection,
                selectionClause, selectionValue, null, null)
        var count = cursor.count
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val productJson = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Products.COLUMN_PRODUCTS_JSON))
                    val adapter: JsonAdapter<Product> = Moshi.Builder().build().adapter(Product::class.java)
                    val product = adapter.fromJson(productJson.toString())

                    products.add(product!!)
                } while (cursor.moveToNext())
            }
        }
        if (products.size > 0) dataCallBack.onSuccess(products) else dataCallBack.onError()
    }
}