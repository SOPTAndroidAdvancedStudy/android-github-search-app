package com.siba.searchmvvmpractice.remote

import com.siba.searchmvvmpractice.remote.model.UserCatalog
import com.siba.searchmvvmpractice.remote.model.UserRepositoryCatalog
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("search/users")
    suspend fun getUsers(
            @Query("q") user: String
    ): UserCatalog

    @GET("search/repositories")
    suspend fun getRepositories(
            @Query("q") repositoryName: String
    ): UserRepositoryCatalog
}