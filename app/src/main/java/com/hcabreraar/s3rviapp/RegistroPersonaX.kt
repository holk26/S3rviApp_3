package com.hcabreraar.s3rviapp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.vdx.designertoast.DesignerToast
import kotlinx.android.synthetic.main.activity_registro_persona_x.*
import java.util.*


class RegistroPersonaX : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    var userName = ""
    var userFoto = ""
    lateinit var db : FirebaseFirestore
    var registradoYa = false
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var text_array : Array<String>
    var mutableList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_persona_x)
        //val inputText = outlinedTextField.editText?.text.toString()
        showProgressDialog()
        registerBtn.isEnabled=false

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Access a Cloud Firestore instance from your Activity
       db = FirebaseFirestore.getInstance()

        registerBtn.setOnClickListener {
            if (verificarConexion() != null) {
                if (registradoYa){
                    //auteticado actualizar
                    validarDatos(true)
                }else if (!registradoYa){
                    //crear usuario
                    validarDatos(false)
                }



            }else{
            Toast.makeText(this@RegistroPersonaX, "Revisa tu conexion a internet", Toast.LENGTH_SHORT).show()
        }



        }

        /*imagePerfilX2.setOnClickListener{
            dispatchTakePictureIntent()
            val washingtonRef = db.collection("cities").document("DC")

// Atomically incrememnt the population of the city by 50.
            washingtonRef.update("population", FieldValue.increment(1))
        }*/

        serviciotagX221.setEndIconOnClickListener {
            // Respond to end icon presses

            text_array = servicioTag.text!!.toString().split(" ".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray()
            Log.d("homero", "Document:"+ text_array.contentToString())

            inflarChip(text_array)


        }



    }//onCreate fin

    private fun validarDatos(b: Boolean) {

        //true es autualizar
        if (b){
            actualizarDatosFirebase()
        }else if (!b){
            this.cargarFirebaseDatos()
        }

        nombreEmpres22.error = "Campo vacio"
        nombreEmpres22.isFocused
        //nombreEmpres22.error = null


    }

    private fun actualizarDatosFirebase() {
        chipServicio()

        val updateBd = db.collection("usuarios").document(convSt(auth.uid))

        val actualizarUsuario = hashMapOf(
            "profesionBd" to ProfesionField.text.toString(),
            "ServicioMatrizBd" to mutableList,
            "nombreEmpresaBd" to nombreEmpresaField.text.toString(),
            "ciudadBd" to ciudadField.text.toString(),
            "telefonoBd" to phoneField.text.toString(),
            "emailBd" to correoField.text.toString(),
            "descripcionBd" to descripcionField.text.toString(),
            "nombreBd" to userName,
            "fotoBd" to userFoto
        )


        updateBd
            .update(actualizarUsuario)
            .addOnSuccessListener {
                Log.d("TAG", "DocumentSnapshot successfully updated!")
                DesignerToast.Success(this@RegistroPersonaX, "Actualizacion Completada", Gravity.CENTER, Toast.LENGTH_SHORT)

                val intent = Intent(baseContext, Login::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }

    }

    private fun inflarChip(holk: Array<String>) {

        val inflater = LayoutInflater.from(this@RegistroPersonaX)

        for (text in holk){

            val chip_item2 = inflater.inflate(R.layout.chip_item,null,false) as Chip
            chip_item2.text = text

            chip_item2.setOnCloseIconClickListener{view ->

                chip_group2.removeView(view)

            }
            chip_group2.addView(chip_item2)
            chip_item2.text

            servicioTag.setText("")

        }

    }



    public override fun onStart() {
        super.onStart()
        showProgressDialog()
        // Check if user is signed in (non-null) and update UI accordingly.
       var currentUser = auth.currentUser
        updateUI(currentUser)
    }



    private fun updateUI(user2: FirebaseUser?) {

        if (user2 != null) {

            userName = user2.displayName.toString()
            userFoto = user2.photoUrl.toString()

            datosFireStone()



        }else{
            val intent = Intent(baseContext, Login::class.java)
            startActivity(intent)
        }
        registerBtn.isEnabled=true


    }



//Carga los datos a la base de datos
    private fun cargarFirebaseDatos(){

        chipServicio()

        convSt(auth.uid)
        //estadoXml.text = auth.uid
        //var otg: = auth.uid
        var x = ""

        val nullString: String? = auth.uid
        nullString?.let{
           x = it
        }

        val usuario33 = hashMapOf(
            "profesionBd" to ProfesionField.text.toString(),
            "ServicioMatrizBd" to mutableList,
            "nombreEmpresaBd" to nombreEmpresaField.text.toString(),
            "ciudadBd" to ciudadField.text.toString(),
            "telefonoBd" to phoneField.text.toString(),
            "emailBd" to correoField.text.toString(),
            "descripcionBd" to descripcionField.text.toString(),
            "nombreBd" to userName,
            "fotoBd" to userFoto,
            "fechaRegistro" to Timestamp(Date()),
            "estadoBd" to "verificado",
            "likeBd" to 0,
            "condicionesBd" to aceptaTerminos.isChecked,
            "dislikeBd" to 0,
            //"mostrarEnListaBd" to false,
            "idUsuarioBd" to x
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
        showProgressDialog()
        var x = ""

        val nullString: String? = auth.uid
        nullString?.let{
            x = it
        }
        val docRef = db.collection("usuarios").document(x)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    if (document.getString("estadoBd") == "verificado" ){
                        registradoYa = true
                        registerBtn.text = "Actualizar"
                        ciudadField.text = document.getString("ciudadBd")!!.toEditable()
                        correoField.text = document.getString("emailBd")!!.toEditable()
                        descripcionField.text = document.getString("descripcionBd")!!.toEditable()
                        nombreEmpresaField.text = document.getString("nombreEmpresaBd")!!.toEditable()
                        aceptaTerminos.visibility = View.GONE
                        condicionesXml.visibility = View.GONE

                        llamarMatriz(document)

                        phoneField.text = document.getString("telefonoBd")!!.toEditable()
                        ProfesionField.text = document.getString("profesionBd")!!.toEditable()
                       // Picasso.get().load(userFoto).into(imagePerfilX2)
                        hideProgressDialog()

                    }else{
                        aceptaTerminos.visibility = View.VISIBLE
                        condicionesXml.visibility = View.VISIBLE
                        registerBtn.text = "Registrate"
                        hideProgressDialog()

                    }



                } else {
                    //Log.d(TAG, "No such document")
                    DesignerToast.Success(this@RegistroPersonaX, "Falta registrar", Gravity.CENTER, Toast.LENGTH_SHORT)
                }
            }
            .addOnFailureListener { exception ->
                //Log.d(TAG, "get failed with ", exception)
            }
        hideProgressDialog()
    }

    private fun llamarMatriz(document: DocumentSnapshot) {
        chip_group2.removeAllViews()
        val listFb = document.get("ServicioMatrizBd")!! as List<String>
        //inflarChip(arrayOf("dfdf","msf"))
        inflarChip(listFb.toTypedArray())
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

    }


    private fun chipServicio(){

        for (i in 0 until chip_group2.childCount) {
                val chip = chip_group2.getChildAt(i) as Chip
                val vChip : Chip = chip.findViewById(chip.id)
                val chipNameX3 = vChip.text.toString()
                mutableList.add(chipNameX3)
        }
        Log.d("chipServicio", "mutableListO = $mutableList")

    }

}
