package com.siba.searchmvvmpractice.api.model.repository

import com.google.gson.annotations.SerializedName
import com.siba.searchmvvmpractice.domain.DomainRepository
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubRepositoryInfo

data class UserRepositoryCatalog(
    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    @SerializedName("items")
    val repository: List<Repository>
)

fun UserRepositoryCatalog.asDatabaseModel(): List<DatabaseGithubRepositoryInfo> {
    return repository.map {
        DatabaseGithubRepositoryInfo(
            full_name = it.full_name,
            html_url = it.html_url
        )
    }
}

fun UserRepositoryCatalog.asDomainModel(): List<DomainRepository> {
    return repository.map {
        DomainRepository(
            full_name = it.full_name,
            html_url = it.html_url
        )
    }
}