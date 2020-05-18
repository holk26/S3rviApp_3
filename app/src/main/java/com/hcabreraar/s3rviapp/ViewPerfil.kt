package com.hcabreraar.s3rviapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_view_perfil.*

class ViewPerfil : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_perfil)
        showProgressDialog()
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        //profile_image3.borderColor
        var idUsuario : String?

        var bundle :Bundle ?=intent.extras
        idUsuario = bundle!!.getString("ID") // 1
        construirDatos(idUsuario)
        //var strUser: String = intent.getStringExtra("ID") // 2
        //Toast.makeText(this, strUser, Toast.LENGTH_SHORT).show()

        like_persona3.setOnClickListener{
            like_persona3.playAnimation()
            //like_persona3.setProgress(90f)
            dislikeXml.progress = 0f
            enviarLike(true,idUsuario)
            //like_persona3.setProgress(0f)//para poner like full
        }

        dislikeXml.setOnClickListener{
            dislikeXml.playAnimation();
            like_persona3.progress = 0f
            enviarLike(false, idUsuario)
            //dislikeXml.setProgress(90f)
        }


         }

    public override fun onStart() {
        super.onStart()
        showProgressDialog()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun enviarLike(like: Boolean, idUs: String?) {

        val likes = db.collection("likes").document(idUs.toString())


        likes.update("dislike", FieldValue.increment(1))
        likes.update("like", FieldValue.increment(-1))


    }

    private fun updateUI(user2: FirebaseUser?) {

        if (user2 != null) {



        }else{
            val intent = Intent(baseContext, Login::class.java)
            startActivity(intent)
        }


    }
    override fun onStop() {
        // call the superclass method first
        super.onStop()

       //finish()

    }

    private fun construirDatos(idUsuario: String?) {

        val docRef = db.collection("usuarios").document(idUsuario.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    datosBD(document)
                } else {
                    Log.d("TAG", "No such document")

                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
        hideProgressDialog()

    }

    private fun datosBD(document: DocumentSnapshot) {
        Log.d("enlace de la foto: ", document.getString("fotoBd")!!)
        Glide.with(this).load(document.getString("fotoBd")!!).into(imageView344)
        nombrePerfil.text = document.getString("nombreEmpresaBd")!!
        ubicacionPerfil.text = document.getString("ciudadBd")!!
        profesionPerfil.text = document.getString("profesionBd")!!



        hideProgressDialog()
    }


}
