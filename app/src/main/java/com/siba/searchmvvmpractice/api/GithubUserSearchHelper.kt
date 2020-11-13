package com.siba.searchmvvmpractice.api

class GithubUserSearchHelper(private val retrofitService: RetrofitService , private val userName : String) {
    suspend fun getGithubAllUser() = retrofitService.getAllUser(userName)
}