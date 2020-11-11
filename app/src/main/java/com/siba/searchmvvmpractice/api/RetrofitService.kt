package com.siba.searchmvvmpractice.api

import com.siba.searchmvvmpractice.data.GithubUserData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    // https://api.github.com/search/SSong-develop
    @GET("search/users/{user}")
    suspend fun getUser(
            @Path("user") user : String
    ) : GithubUserData
}