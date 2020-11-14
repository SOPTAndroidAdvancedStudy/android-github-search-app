package com.siba.searchmvvmpractice.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siba.searchmvvmpractice.api.GithubUserSearchHelper
import com.siba.searchmvvmpractice.api.RetrofitBuilder
import com.siba.searchmvvmpractice.api.RetrofitHelper
import com.siba.searchmvvmpractice.api.RetrofitService
import com.siba.searchmvvmpractice.data.GithubUserData
import com.siba.searchmvvmpractice.data.RetrofitData
import com.siba.searchmvvmpractice.repo.GithubAllUserRepository
import com.siba.searchmvvmpractice.repo.GithubUserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SearchUserViewModel : ViewModel(){
    private val _userName = MutableLiveData<String>()
    val userName : MutableLiveData<String>
        get() = _userName

    private val _githubAllUser = MutableLiveData<RetrofitData>()
    val githubAllUser : MutableLiveData<RetrofitData>
        get() = _githubAllUser

    // TODO : Error Exception 처리하기
    fun searchAllUser() = viewModelScope.launch {
        val repo = GithubAllUserRepository(GithubUserSearchHelper(RetrofitBuilder.retrofitService,_userName.value.toString()))
        _githubAllUser.value = repo.getGithubAllUser()
    }
}