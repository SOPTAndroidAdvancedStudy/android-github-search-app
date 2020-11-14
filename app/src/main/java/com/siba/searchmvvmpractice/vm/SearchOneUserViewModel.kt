package com.siba.searchmvvmpractice.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siba.searchmvvmpractice.api.RetrofitBuilder
import com.siba.searchmvvmpractice.api.RetrofitHelper
import com.siba.searchmvvmpractice.data.GithubUserData
import com.siba.searchmvvmpractice.repo.GithubUserRepository
import kotlinx.coroutines.launch

class SearchOneUserViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName : MutableLiveData<String>
        get() = _userName

    private val _githubUser = MutableLiveData<GithubUserData>()
    val githubUser : MutableLiveData<GithubUserData>
        get() = _githubUser

    // TODO : ERROR Exception Handle
    fun searchUser() = viewModelScope.launch {
        val repo = GithubUserRepository(RetrofitHelper(RetrofitBuilder.retrofitService,_userName.value.toString()))
        _githubUser.value = repo.getGithubUser()
    }
}