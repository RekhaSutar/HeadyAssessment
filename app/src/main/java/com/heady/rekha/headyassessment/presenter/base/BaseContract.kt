package com.heady.rekha.headyassessment.presenter.base

/**
 * Created by rekha on 4/12/17.
 */

interface BaseContract{
    interface BaseView{
        fun onError()
    }
    interface BasePresenter{
        fun detach()
    }
}
