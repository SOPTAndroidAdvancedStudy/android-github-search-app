package com.siba.searchmvvmpractice.api

import com.siba.searchmvvmpractice.data.GithubUserData
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("users/{user}")
    suspend fun getUser(
            @Path("user") user : String
    ) : GithubUserData
}