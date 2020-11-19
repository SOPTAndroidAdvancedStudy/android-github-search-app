package com.siba.searchmvvmpractice.remote.model

data class UserRepositoryCatalog (
        val total_count : Int,
        val incomplete_results : Boolean,
        val userRepository : List<UserRepository>
)
data class UserRepository (
        val full_name : String,
        val html_url : String
)