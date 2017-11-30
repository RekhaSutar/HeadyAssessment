package com.heady.rekha.headyassessment.presenter

/**
 * Created by rekha on 30/11/17.
 */

class MainActivityContract {
    interface View {
        fun onCategoriesFetched()
        fun onError()
    }

    interface Presenter {
        fun detach()
        fun getCategories()
    }
}
