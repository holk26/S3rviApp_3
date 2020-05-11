package com.hcabreraar.s3rviapp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.vdx.designertoast.DesignerToast
import kotlinx.android.synthetic.main.activity_registro_persona_x.*
import java.util.*


class RegistroPersonaX : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    var userName = ""
    var userFoto = ""
    lateinit var db : FirebaseFirestore
    val nombreR = ""
    val urlPhotoR = ""
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var text_array : Array<String>

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
            chipServicio()
            Log.d("this is my array", "arr: " + Arrays.toString(text_array)+"-perro-"+text_array);

        }

        /*imagePerfilX2.setOnClickListener{
            dispatchTakePictureIntent()
            val washingtonRef = db.collection("cities").document("DC")

// Atomically incrememnt the population of the city by 50.
            washingtonRef.update("population", FieldValue.increment(1))
        }*/

        serviciotagX221.setEndIconOnClickListener {
            // Respond to end icon presses
            //DesignerToast.Success(this, servicioTag.getText().toString(), Gravity.CENTER, Toast.LENGTH_SHORT)

            text_array = servicioTag.text!!.toString().split(" ".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray()

            val inflater = LayoutInflater.from(this@RegistroPersonaX)

            for (text in text_array){

                val chip_item2 = inflater.inflate(R.layout.chip_item,null,false) as Chip
                chip_item2.text = text

                chip_item2.setOnCloseIconClickListener{view ->

                    chip_group2.removeView(view)
                    Log.d("this is my array", "arr: " + view)


                }
                chip_group2.addView(chip_item2)
                chip_item2.text

                servicioTag.setText("");
                Log.d("this is my array", "arr: " + Arrays.toString(text_array)+"-perro-"+text_array);
                //DesignerToast.Success(this@RegistroPersonaX, "Se Agrego el servicio ", Gravity.CENTER, Toast.LENGTH_SHORT)

            }
        }

        //chip_group2.setOnCheckedChangeListener()
        chip_group2.setOnCheckedChangeListener { group, checkedId ->
            // Handle the checked chip change.
            DesignerToast.Success(this@RegistroPersonaX, "cambiao"+checkedId, Gravity.CENTER, Toast.LENGTH_SHORT)

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
            "profesion" to ProfesionField.getText().toString(),
            "Servicio" to "arreglo lavadoras",
            "ciudad" to ciudadField.getText().toString(),
            "telefono" to phoneField.getText().toString(),
            "nombre" to userName,
            "foto" to userFoto,
            "estado" to "verificado",
            "mostrarEnLista" to false,
            "fecha_registro" to Timestamp(Date()),
            "likes" to 0,
            "dislike" to 0,
            "hizo_like" to false,
            "idUsuario" to x
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
                        //ServicioField.text = document.getString("Servicio")!!.toEditable()
                        phoneField.text = document.getString("telefono")!!.toEditable()
                        ProfesionField.text = document.getString("profesion")!!.toEditable()
                       // Picasso.get().load(userFoto).into(imagePerfilX2)

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




    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

    }


    private fun chipServicio(){
        lateinit var servicioAr : Array<String>
        for (i in 0 until chip_group2.childCount) {
            val chip = chip_group2.getChildAt(i)

            Log.d("Homero cabrera araque", "arr: "+chip.id)
            //chip.isClickable = chip.id != chip_group2.checkedChipId
            //servicioAr[i] = chip.id.toString()

        }



    }




}
