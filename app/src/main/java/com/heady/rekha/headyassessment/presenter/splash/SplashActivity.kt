package com.heady.rekha.headyassessment.presenter.splash

import android.content.Intent
import android.os.Bundle
import com.heady.rekha.headyassessment.R
import com.heady.rekha.headyassessment.base.BaseActivity
import com.heady.rekha.headyassessment.presenter.MainActivity

class SplashActivity : BaseActivity(), SplashContract.View {
    override fun onError() {
        showMessage(resources.getString(R.string.errorMsg))
    }

    override fun onDataFetched() {
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SplashPresenter(this).fetchData()
    }
}
