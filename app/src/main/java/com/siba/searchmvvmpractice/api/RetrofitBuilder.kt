package com.siba.searchmvvmpractice.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val URL = "https://api.github.com"

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val retrofitService : RetrofitService = getRetrofit().create(RetrofitService::class.java)
}