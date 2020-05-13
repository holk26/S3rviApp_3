package com.hcabreraar.s3rviapp

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.vdx.designertoast.DesignerToast
import kotlinx.android.synthetic.main.activity_acerca_de_mi.*


class AcercaDeMi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de_mi)

        btn_pruebaTag.setOnClickListener {

            for (i in 0 until chip_group_prueba.childCount) {
                val chip = chip_group_prueba.getChildAt(i) as Chip

                var vChip : Chip = chip.findViewById(chip.id)
                var chipNameX3 = vChip.getText().toString()
                Log.d("homero", "Document: "+chipNameX3)


            }

        }








    }
}
