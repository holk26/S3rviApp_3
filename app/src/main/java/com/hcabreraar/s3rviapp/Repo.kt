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

                if (document.getBoolean("mostrarEnListaBd") == true){

                val imagenUrlUs = document.getString("fotoBd")
                val nombreUs = document.getString("nombreEmpresaBd")
                val profesionUs = document.getString("profesionBd")
                val ciudadUs = document.getString("ciudadBd")
                val idUsuarioUs = document.getString("idUsuarioBd")
                val usuario = Blog(imagenUrlUs!!,nombreUs!!,profesionUs!!,ciudadUs!!,idUsuarioUs!!)

                listData.add(usuario)
                }
            } //fin for
            mutableData.value = listData

        }
        return mutableData



    }
}