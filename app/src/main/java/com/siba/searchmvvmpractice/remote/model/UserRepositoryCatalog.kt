package com.siba.searchmvvmpractice.remote.model

import com.google.gson.annotations.SerializedName

data class UserRepositoryCatalog(
        @SerializedName("total_count")
        val total_count: Int,
        @SerializedName("incomplete_results")
        val incomplete_results: Boolean,
        @SerializedName("items")
        val userRepository: List<UserRepository>
)

data class UserRepository(
        @SerializedName("full_name")
        val full_name: String,
        @SerializedName("html_url")
        val html_url: String
)