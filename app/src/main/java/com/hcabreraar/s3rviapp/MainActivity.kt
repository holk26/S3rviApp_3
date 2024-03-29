package com.hcabreraar.s3rviapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.vdx.designertoast.DesignerToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    var doubleBackToExitPressedOnce = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize Firebase Auth
        setSupportActionBar(findViewById(R.id.toolbarHome))
        auth = FirebaseAuth.getInstance()


        ActionHome()



        init()

        profile_image66.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)


        }



    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)

    }

    /*override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }*/

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed()
            finish()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Pulsa 2 veces para salir", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    //verifica si el usuario esta registrado

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            //se logeo
            nombreUsuarioBd.text = user.displayName!!.split(" ")[0]
            Glide.with(this).load(user.photoUrl).into(profile_image66)
            textBinvenido.visibility = View.VISIBLE

            //Toast.makeText(this@MainActivity, "logiado", Toast.LENGTH_SHORT).show()
            val listView: View? = findViewById(R.id.action_perfil)
            //listView?.visibility = View.VISIBLE ll

        }else{
            textBinvenido.visibility = View.GONE

           

        }


    }




    fun init(){






        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    ActionHome()
                    true

                }
                R.id.action_buscar -> {
                    ActionBuscar()
                    true

                }
                R.id.action_perfil -> {
                    //Toast.makeText(this@MainActivity, "perfil", Toast.LENGTH_SHORT).show()
                    ActionPerfil()
                    true

                }
                else -> false
            }
        }


    }

    private fun ActionPerfil() {
        val fragment = FragmentPerfil.newInstance("hola","hola")
        openFragment(fragment)



    }

    private fun ActionHome() {
        val fragment = FragmentInicio.newInstance()
        openFragment(fragment)

    }

    private fun ActionBuscar() {
        val fragment = FragmentBuscar.newInstance("hola","hola2")
        openFragment(fragment)


    }


    //llamar el menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)

        return true
    }




    fun Btn_inicio_sesion(){
        val intent = Intent(baseContext, Login::class.java)
        startActivity(intent)
    }

    //abrir fragmentos funcion
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contenedor3, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }




}

