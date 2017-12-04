package com.heady.rekha.headyassessment.base

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by rekha on 1/12/17.
 */
open class BaseActivity : AppCompatActivity() {
    fun showMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}