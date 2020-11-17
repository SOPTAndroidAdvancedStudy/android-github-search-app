package com.siba.searchmvvmpractice.remote

import com.siba.searchmvvmpractice.model.GithubUserData
import com.siba.searchmvvmpractice.model.ReposData
import com.siba.searchmvvmpractice.model.RetrofitData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("users/{user}")
    suspend fun getUser(
            @Path("user") user : String
    ) : GithubUserData

    @GET("search/users")
    suspend fun getUsers(
            @Query("q") user : String
    ) : RetrofitData

    @GET("search/repositories")
    suspend fun getRepos(
        @Query("q") repoName : String
    ) : ReposData

}