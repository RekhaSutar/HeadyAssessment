package com.heady.rekha.headyassessment.presenter.category

import com.heady.rekha.headyassessment.domain.CategoryEntity

/**
 * Created by rekha on 1/12/17.
 */
interface CategoryContract {
    interface View {
        fun onCategoriesFetched(categories: List<CategoryEntity>)
        fun onError()
    }

    interface Presenter {
        fun detach()
        fun getCategories()
    }
}