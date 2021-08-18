package com.siba.searchmvvmpractice.api.model.user

import com.google.gson.annotations.SerializedName
import com.siba.searchmvvmpractice.domain.DomainUsers
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubUserInfo

data class UserCatalog(
    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    @SerializedName("items")
    val users: List<Users>
)

fun UserCatalog.asDatabaseModel(): List<DatabaseGithubUserInfo> {
    return users.map {
        DatabaseGithubUserInfo(
            login = it.login,
            url = it.url
        )
    }
}

fun UserCatalog.asDomainModel(): List<DomainUsers> {
    return users.map {
        DomainUsers(
            login = it.login,
            url = it.url
        )
    }
}
