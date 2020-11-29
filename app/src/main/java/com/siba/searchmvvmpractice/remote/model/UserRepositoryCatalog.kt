package com.siba.searchmvvmpractice.remote.model

import com.google.gson.annotations.SerializedName
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubRepositoryInfo

data class UserRepositoryCatalog(
        @SerializedName("total_count")
        val total_count: Int,
        @SerializedName("incomplete_results")
        val incomplete_results: Boolean,
        @SerializedName("items")
        val repository: List<Repository>
)

data class Repository(
        @SerializedName("full_name")
        val full_name: String,
        @SerializedName("html_url")
        val html_url: String
)

fun UserRepositoryCatalog.toDatabaseGithubRepositoryInfo(keyword : String) = DatabaseGithubRepositoryInfo(
        repositoryName = keyword,
        full_name = repository[0].full_name,
        html_url = repository[0].html_url
)
