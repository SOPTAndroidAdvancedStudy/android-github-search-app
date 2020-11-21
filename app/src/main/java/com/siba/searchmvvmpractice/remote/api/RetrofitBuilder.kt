package com.siba.searchmvvmpractice.remote.api

import com.siba.searchmvvmpractice.remote.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val URL = "https://api.github.com"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val retrofitService: RetrofitService = getRetrofit().create(RetrofitService::class.java)

}