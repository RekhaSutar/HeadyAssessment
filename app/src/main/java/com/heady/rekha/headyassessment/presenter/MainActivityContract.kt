package com.heady.rekha.headyassessment.presenter

import com.heady.rekha.headyassessment.domain.entity.Product

/**
 * Created by rekha on 30/11/17.
 */

class MainActivityContract {
    interface View {
        fun onProductsFetched(products: List<Product>)
        fun onError()
    }

    interface Presenter {
        fun detach()
        fun getProducts(productIds: String)
        fun getSortedProducts(sortType: String)
    }
}
