package com.siba.searchmvvmpractice.injection

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.siba.searchmvvmpractice.local.database.AppDatabase
import com.siba.searchmvvmpractice.api.GithubService
import com.siba.searchmvvmpractice.api.RetrofitBuilder
import com.siba.searchmvvmpractice.repository.SearchRepository
import com.siba.searchmvvmpractice.ui.factory.SearchViewModelFactory

object Injection {

    private fun provideRetrofitService(): GithubService {
        return RetrofitBuilder.GITHUB_SERVICE
    }

    private fun provideMainRepository(context: Context): SearchRepository {
        val database = AppDatabase.getInstance(context)
        return SearchRepository(provideRetrofitService(), database.searchDao)
    }

    fun provideSearchViewModelFactory(context: Context): ViewModelProvider.Factory {
        return SearchViewModelFactory(provideMainRepository(context))
    }

}