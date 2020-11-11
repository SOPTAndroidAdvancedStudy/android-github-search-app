package com.siba.searchmvvmpractice.api

class RetrofitHelper(private val retrofitService: RetrofitService , private val user : String) {
    suspend fun getGithubUser() = retrofitService.getUser(user)
}