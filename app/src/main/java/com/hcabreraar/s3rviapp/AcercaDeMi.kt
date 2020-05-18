package com.hcabreraar.s3rviapp


import android.content.Context
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_acerca_de_mi.*


class AcercaDeMi : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de_mi)

        val code = BuildConfig.VERSION_CODE
        val name = BuildConfig.VERSION_NAME
        text_version.text = "Version: $code $name"





    }

    //llamar el menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)

        return true
    }
}
