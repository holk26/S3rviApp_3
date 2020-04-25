package com.hcabreraar.s3rviapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.hcabreraar.s3rviapp.Blog

class Repo {

    fun getUserData():LiveData<MutableList<Blog>>{
        //val mDatabase = FirebaseDatabase.getInstance().getReference().child("data")
        val mutableData = MutableLiveData<MutableList<Blog>>()
        FirebaseFirestore.getInstance().collection("usuarios").get().addOnSuccessListener { result ->
            val listData = mutableListOf<Blog>()
            for(document in result){

                if (document.getBoolean("mostrarEnLista") == true){

                val imagenUrl = document.getString("foto")
                val nombreUs = document.getString("nombre")
                val servicioUs = document.getString("Servicio")
                val ciudadUs = document.getString("ciudad")
                val telefonUs = document.getString("telefono")
                val usuario = Blog(imagenUrl!!,nombreUs!!,servicioUs!!,ciudadUs!!,telefonUs!!)


                listData.add(usuario)
                }
            } //fin for
            mutableData.value = listData

        }
        return mutableData



    }
}