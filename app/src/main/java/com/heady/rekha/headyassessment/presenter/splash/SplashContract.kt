package com.heady.rekha.headyassessment.presenter.splash

import com.heady.rekha.headyassessment.presenter.base.BaseContract

/**
 * Created by rekha on 4/12/17.
 */
interface SplashContract {
    interface View : BaseContract.BaseView {
        fun onDataFetched()
    }
    interface Presenter: BaseContract.BasePresenter{
        fun fetchData()
    }
}