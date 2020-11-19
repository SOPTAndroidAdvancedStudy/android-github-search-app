package com.siba.searchmvvmpractice.remote.model

import com.google.gson.annotations.SerializedName

data class UserRepositoryCatalog (
        val total_count : Int,
        val incomplete_results : Boolean,
        @SerializedName("items")
        val userRepository : List<UserRepository>
)
data class UserRepository (
        val full_name : String,
        val html_url : String
)