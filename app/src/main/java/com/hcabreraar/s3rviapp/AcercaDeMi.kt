package com.hcabreraar.s3rviapp

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.vdx.designertoast.DesignerToast
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
