package com.siba.searchmvvmpractice.api

import com.siba.searchmvvmpractice.api.model.repository.UserRepositoryCatalog
import com.siba.searchmvvmpractice.api.model.user.UserCatalog
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") user: String
    ): UserCatalog

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") repositoryName: String
    ): UserRepositoryCatalog
}