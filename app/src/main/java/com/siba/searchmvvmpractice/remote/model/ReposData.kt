package com.siba.searchmvvmpractice.remote.model

data class ReposData (
        val total_count : Int,
        val incomplete_results : Boolean,
        val items : List<ReposItems>
)

data class ReposItems (
        val full_name : String,
        val html_url : String
)