package com.siba.searchmvvmpractice.api.model.user

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("login")
    val login: String,
    @SerializedName("url")
    val url: String
)