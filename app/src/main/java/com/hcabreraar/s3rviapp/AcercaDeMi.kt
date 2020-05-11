package com.hcabreraar.s3rviapp

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.vdx.designertoast.DesignerToast
import kotlinx.android.synthetic.main.activity_acerca_de_mi.*
import java.util.*

class AcercaDeMi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de_mi)
        //LayoutInflater inflater = LayoutInflater.from(AcercaDeMi.this)
        //val layoutInflater:LayoutInflater = LayoutInflater.from(this)



        /*profesionX221.setEndIconOnClickListener {
            // Respond to end icon presses
            //DesignerToast.Success(this, servicioTag.getText().toString(), Gravity.CENTER, Toast.LENGTH_SHORT)

            val text_array = servicioTag.text!!.toString().split(" ".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray()

            val inflater = LayoutInflater.from(this@AcercaDeMi)

            for (text in text_array){

                val chip_item2 = inflater.inflate(R.layout.chip_item,null,false) as Chip
                Log.d("this is my array", "arr: " + Arrays.toString(text_array));
                chip_item2.text = text

                chip_item2.setOnCloseIconClickListener{view ->

                    chip_group2.removeView(view)

                }
                chip_group2.addView(chip_item2)
                servicioTag.setText("");

            }
        }*/


    }
}
