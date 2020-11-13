package com.siba.searchmvvmpractice.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchUserRepoViewModel : ViewModel() {
    val focus = false

    private val _userName = MutableLiveData<String>()
    val userName : MutableLiveData<String>
        get() = _userName
}