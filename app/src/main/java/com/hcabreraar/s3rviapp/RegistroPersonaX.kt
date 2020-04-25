package com.hcabreraar.s3rviapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.vdx.designertoast.DesignerToast
import kotlinx.android.synthetic.main.activity_registro_persona_x.*

class RegistroPersonaX : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    var userName = ""
    var userFoto = ""
    lateinit var db : FirebaseFirestore
    val nombreR = ""
    val urlPhotoR = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_persona_x)
        //val inputText = outlinedTextField.editText?.text.toString()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Access a Cloud Firestore instance from your Activity
       db = FirebaseFirestore.getInstance()

        registerBtn.setOnClickListener(){
            //Toast.makeText(this@RegistroPersonaX, "logiado"+nameField.getText().toString(), Toast.LENGTH_SHORT).show()
            prueba()

        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user2: FirebaseUser?) {
        if (user2 != null) {
            Toast.makeText(this@RegistroPersonaX, "logiado", Toast.LENGTH_SHORT).show()
            userName = user2.displayName.toString()
            userFoto = user2.photoUrl.toString()
            datosFireStone()


        }else{
            val intent = Intent(baseContext, Login::class.java)
            startActivity(intent)
        }


    }




    fun prueba(){

        convSt(auth.uid)
        //estadoXml.text = auth.uid
        //var otg: = auth.uid
        var x = ""

        val nullString: String? = auth.uid
        nullString?.let{
           x = it
        }

        // Create a new user with a first, middle, and last name
        val city = hashMapOf(
            "telefono" to "3102796853",
            "servicio" to "Matenimiento",
            "ciudad" to "Garzón"
        )

        val usuario33 = hashMapOf(
            "Servicio" to ServicioField.getText().toString(),
            "ciudad" to ciudadField.getText().toString(),
            "telefono" to phoneField.getText().toString(),
            "nombre" to userName,
            "foto" to userFoto,
            "estado" to "verificado",
            "mostrarEnLista" to false
        )

        db.collection("usuarios").document(convSt(auth.uid))
            .set(usuario33)
            .addOnSuccessListener {
                //Log.d(TAG, "DocumentSnapshot successfully written!")
                DesignerToast.Success(this@RegistroPersonaX, "Registro Completo", Gravity.CENTER, Toast.LENGTH_SHORT)

                val intent = Intent(baseContext, Login::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                    //e -> Log.w(TAG, "Error writing document", e)
            }
    }

    private fun convSt(datoFi: String?): String {
        var puto:String = ""
        val nullString: String? = datoFi
        nullString?.let{
            puto = it
        }

        return puto
    }

    private fun datosFireStone() {
        var x = ""

        val nullString: String? = auth.uid
        nullString?.let{
            x = it
        }
        val docRef = db.collection("usuarios").document(x)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    if (document.getString("estado") == "verificado" ){
                        registerBtn.text = "Actualizar"
                        ciudadField.text = document.getString("ciudad")!!.toEditable()
                        ServicioField.text = document.getString("Servicio")!!.toEditable()
                        phoneField.text = document.getString("telefono")!!.toEditable()

                    }else{
                        registerBtn.text = "Registrate"

                    }



                } else {
                    //Log.d(TAG, "No such document")
                    DesignerToast.Success(this@RegistroPersonaX, "Falta registrar", Gravity.CENTER, Toast.LENGTH_SHORT)
                }
            }
            .addOnFailureListener { exception ->
                //Log.d(TAG, "get failed with ", exception)
            }
    }


}
