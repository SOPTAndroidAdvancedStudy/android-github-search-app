package com.siba.searchmvvmpractice.remote.model.repository

import com.google.gson.annotations.SerializedName

data class UserRepositoryCatalog(
        @SerializedName("total_count")
        val total_count: Int,
        @SerializedName("incomplete_results")
        val incomplete_results: Boolean,
        @SerializedName("items")
        val repository: List<Repository>
)
