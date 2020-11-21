package com.siba.searchmvvmpractice.injection

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.siba.searchmvvmpractice.local.database.SearchTermDatabase
import com.siba.searchmvvmpractice.remote.RetrofitService
import com.siba.searchmvvmpractice.remote.api.RetrofitBuilder
import com.siba.searchmvvmpractice.repository.SearchRepository
import com.siba.searchmvvmpractice.ui.base.SearchViewModelFactory

object Injection {

    private fun provideRetrofitService(): RetrofitService {
        return RetrofitBuilder.retrofitService
    }

    private fun provideMainRepository(context: Context): SearchRepository {
        val database = SearchTermDatabase.getInstance(context)
        return SearchRepository(provideRetrofitService(), database.searchTermDao)
    }

    fun provideSearchViewModelFactory(context: Context): ViewModelProvider.Factory {
        return SearchViewModelFactory(provideMainRepository(context))
    }

}