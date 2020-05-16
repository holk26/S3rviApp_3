package com.hcabreraar.s3rviapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.MenuItem
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager


open class BaseActivity : AppCompatActivity() {

    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(this)
    }

    fun showProgressDialog() {
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    fun verificarConexion(): NetworkInfo? {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    public override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    // funcinamiento a el menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_inicio_sesion -> {
                val intent = Intent(baseContext, Login::class.java)
                startActivity(intent)
                true
            }
            /* R.id.help -> {
                 //showHelp()
                 //Toast.makeText(this@MainActivity, "You clicked me help.", Toast.LENGTH_SHORT).show()
                 DesignerToast.Success(this@MainActivity, "Success Toast", Gravity.CENTER, Toast.LENGTH_SHORT)


                 true
             }*/
            R.id.Acerca3 -> {
                val intent = Intent(baseContext, AcercaDeMi::class.java)
                startActivity(intent)
                true
            }
            R.id.salir12 -> {

                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
