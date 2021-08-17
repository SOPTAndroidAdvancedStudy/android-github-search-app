package com.siba.searchmvvmpractice.api.model.repository

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("full_name")
    val full_name: String,
    @SerializedName("html_url")
    val html_url: String
)