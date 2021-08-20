package com.siba.searchmvvmpractice.api

import androidx.lifecycle.LiveData
import com.siba.searchmvvmpractice.remote.ApiResponse
import com.siba.searchmvvmpractice.vo.Contributor
import com.siba.searchmvvmpractice.vo.Repo
import com.siba.searchmvvmpractice.vo.RepoSearchResponse
import com.siba.searchmvvmpractice.vo.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Rest API access Point
 */
interface GithubService {
    @GET("users/{login}")
    fun getUser(@Path("login") login: String): LiveData<ApiResponse<User>>

    @GET("users/{login}/repos")
    fun getRepos(@Path("login") login: String): LiveData<ApiResponse<List<Repo>>>

    @GET("repos/{owner}/{name}")
    fun getRepo(
        @Path("owner") owner : String,
        @Path("name") name : String
    ) : LiveData<ApiResponse<Repo>>

    @GET("repos/{owner}/{name}/contributors")
    fun getContributors(
        @Path("owner") owner : String,
        @Path("name") name : String
    ) : LiveData<ApiResponse<List<Contributor>>>

    @GET("search/repositories")
    fun searchRepos(@Query("q") query : String) : LiveData<ApiResponse<RepoSearchResponse>>

    @GET("search/repositories")
    fun searchRepos(@Query("q") query : String , @Query("page") page : Int) : Call<RepoSearchResponse>
}