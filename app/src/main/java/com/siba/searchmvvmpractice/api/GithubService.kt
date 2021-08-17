package com.siba.searchmvvmpractice.api

import androidx.lifecycle.LiveData
import com.siba.searchmvvmpractice.api.model.user.UserCatalog
import com.siba.searchmvvmpractice.remote.ApiResponse
import com.siba.searchmvvmpractice.vo.RepoSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") user: String
    ): LiveData<ApiResponse<UserCatalog>>

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") repositoryName: String
    ): LiveData<ApiResponse<RepoSearchResult>>
}