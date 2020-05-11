package com.hcabreraar.s3rviapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_perfil.*

class ViewPerfil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_perfil)

        //profile_image3.borderColor

        var bundle :Bundle ?=intent.extras
        var message = bundle!!.getString("ID") // 1
        var strUser: String = intent.getStringExtra("ID") // 2
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Glide.with(this).load("https://www.eltiempo.com/files/image_640_428/files/crop/uploads/2019/10/03/5d96703325768.r_1570198505068.0-0-1732-860.png").into(imageView344)

        like_persona3.setOnClickListener{
            like_persona3.playAnimation()
            //like_persona3.setProgress(90f)//para poner like full
        }

        favorito44.setOnClickListener{
            favorito44.playAnimation();
        }


         }
}
