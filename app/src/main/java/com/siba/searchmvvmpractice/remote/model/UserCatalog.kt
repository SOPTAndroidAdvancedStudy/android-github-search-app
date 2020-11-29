package com.siba.searchmvvmpractice.remote.model

import com.google.gson.annotations.SerializedName

data class UserCatalog(
    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    @SerializedName("items")
    val users: List<Users>
)

data class Users(
    @SerializedName("login")
    val login: String,
    @SerializedName("url")
    val url: String
)

