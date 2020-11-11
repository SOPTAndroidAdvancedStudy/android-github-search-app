package com.siba.searchmvvmpractice.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private val _userName = MutableLiveData<String>()
    val userName : MutableLiveData<String>
        get() = _userName

    // TODO : How to handle Path Value in repository
    fun getName() : LiveData<String>{
        return userName
    }

}