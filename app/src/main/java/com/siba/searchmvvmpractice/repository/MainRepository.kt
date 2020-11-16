package com.siba.searchmvvmpractice.repository

import com.siba.searchmvvmpractice.remote.api.RetrofitBuilder

// TODO : Make Offline cache
class MainRepository() {
    suspend fun fetchUser(userName : String) = RetrofitBuilder.retrofitService.getUsers(userName)

}