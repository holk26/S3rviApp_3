package com.hcabreraar.s3rviapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.squareup.picasso.Picasso
import com.vdx.designertoast.DesignerToast
import kotlinx.android.synthetic.main.activity_login.*


/**
 * Demonstrate Firebase Authentication using a Google ID Token.
 */
class Login : BaseActivity(), View.OnClickListener {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]
    var userId = ""
    var userName = ""
    lateinit var db : FirebaseFirestore




    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)





        db = FirebaseFirestore.getInstance()
        val Fast3 = MiCodigo()

        // Button listeners
        signInButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)
        aliado_app.setOnClickListener(this)

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // [END config_signin]

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]

        mostrarEnLista.setOnCheckedChangeListener { _, isChecked ->

            var ver5 : HashMap<String,Boolean>


             if (isChecked){
                 //"Switch1:ON"
                  ver5 = hashMapOf("mostrarEnLista" to true)

             } else {
                 //"Switch1:OFF"
                 ver5 = hashMapOf("mostrarEnLista" to false)

             }
            db.collection("usuarios").document(convSt(auth.uid))
                .set(ver5, SetOptions.merge())
                .addOnSuccessListener {
                    //Log.d(TAG, "DocumentSnapshot successfully written!")

                }
                .addOnFailureListener {
                    //e -> Log.w(TAG, "Error writing document", e)
                    DesignerToast.Error(this@Login, "Intenta mas tarde", Gravity.CENTER, Toast.LENGTH_SHORT)
                }

            //Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()

        }//Fin Switch1


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(baseContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }



    private fun verificarConexion(): NetworkInfo? {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed holk", e)
                //DesignerToast.Error(this@Login, ""+e.message, Gravity.CENTER, Toast.LENGTH_SHORT)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
        showProgressDialog()
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                hideProgressDialog()
                // [END_EXCLUDE]
            }
    }
    // [END auth_with_google]

    // [START signin]
    private fun signIn() {

            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

    }
    // [END signin]

    private fun signOut() {
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            updateUI(null)
        }
    }

    /*private fun revokeAccess() {
        // Firebase sign out
        auth.signOut()

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {
            updateUI(null)
        }
    }*/

    private fun updateUI(user: FirebaseUser?) {
        hideProgressDialog()
        if (user != null) {
            userId = user.uid
            userName = userName
            textInicioSesion.text = getString(R.string.bienvenido)
            status.text = getString(R.string.google_status_fmt, user.displayName)
            detail.text = getString(R.string.firebase_status_fmt, user.email)
            Picasso.get().load(user.photoUrl).into(googleIcon)
            //Picasso.get().setIndicatorsEnabled(true);
            vistaRegistrado()
            datosFireStone()



        } else {
            vistaNoRegistrado()



        }
    }

    private fun vistaNoRegistrado() {
        textInicioSesion.text = getString(R.string.google_title_text)
        status.setText(R.string.signed_out)
        detail.text = null
        googleIcon.setImageResource(R.drawable.persona)
        signInButton.visibility = View.VISIBLE
        signOutAndDisconnect.visibility = View.GONE
        mostrarEnLista.visibility = View.GONE
        conten_barra_bottom.visibility = View.GONE
        ciudad3.visibility = View.GONE
        servicio3.visibility = View.GONE
        telefono3.visibility = View.GONE
    }


    private fun vistaAliado() {
        ciudad3.visibility = View.VISIBLE
        servicio3.visibility = View.VISIBLE
        telefono3.visibility = View.VISIBLE
        mostrarEnLista.visibility = View.VISIBLE
    }

    private fun vistaRegistrado() {
        conten_barra_bottom.visibility = View.VISIBLE
        signOutAndDisconnect.visibility = View.VISIBLE //se mira
        signInButton.visibility = View.GONE //no se mira
        mostrarEnLista.visibility = View.GONE
        conten_barra_bottom.visibility = View.VISIBLE
        ciudad3.visibility = View.GONE
        servicio3.visibility = View.GONE
        telefono3.visibility = View.GONE

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
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    ciudad3.text = "Ciudad: "+document.getString("ciudad")
                    servicio3.text = "Profesion: "+document.getString("profesion")
                    telefono3.text = "Telefono: "+document.getString("telefono")
                    if (document.getString("estado") == "verificado" ){
                        //aliado_app.visibility = View.GONE
                        aliado_app.text = "Editar datos"
                        vistaAliado()
                    }else if (document.getString("estado") == "noVerificado"){
                        aliado_app.visibility = View.VISIBLE
                        aliado_app.text = "QUIERO SER ALIADO"
                    }

                    mostrarEnLista.isChecked = document.getBoolean("mostrarEnLista") == true



                } else {
                    Log.d(TAG, "No such document")
                    DesignerToast.Success(this@Login, "Falta registrar", Gravity.CENTER, Toast.LENGTH_SHORT)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }




    override fun onClick(v: View) {
        val i = v.id
        if (verificarConexion() != null) {
            // Si hay conexión a Internet en este momento
            when (i) {
                R.id.signInButton -> signIn() //bottom de google
                R.id.signOutButton -> signOut()
                R.id.aliado_app -> aliadoApp()
            }

        }else{
            Toast.makeText(this@Login, "Revisa tu conexion a internet", Toast.LENGTH_SHORT).show()

        }

    }

    private fun aliadoApp() {
        // Write a message to the database
       // val database = FirebaseDatabase.getInstance()
        //database.getReference("usuarios").child(userId).child("nombre").setValue("Homero Cabrera A")
        //database.getReference("usuarios").child(userId).child("telefono").setValue("3102796853")

        val intent = Intent(baseContext, RegistroPersonaX::class.java)
        startActivity(intent)
        //finish()

        //val myRef = database.getReference("message")
        //myRef.setValue("Hello, World!")

    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }


    private fun convSt(datoFi: String?): String {
        var puto:String = ""
        val nullString: String? = datoFi
        nullString?.let{
            puto = it
        }

        return puto
    }







}