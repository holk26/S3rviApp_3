package com.hcabreraar.s3rviapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentInicioViewModel : ViewModel() {

    private val repo = Repo()

    fun fetchUserData():LiveData<MutableList<Blog>>{
       val mutableData = MutableLiveData<MutableList<Blog>>()
        repo.getUserData().observeForever{
            mutableData.value = it
        }

        return mutableData



    } //fin fetchUserData

}
