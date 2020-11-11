package com.siba.searchmvvmpractice.repo

import com.siba.searchmvvmpractice.api.RetrofitHelper

class GithubUserRepository(private val retrofitHelper: RetrofitHelper) {

    suspend fun getGithubUser() = retrofitHelper.getGithubUser()
}