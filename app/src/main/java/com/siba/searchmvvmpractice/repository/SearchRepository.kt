package com.siba.searchmvvmpractice.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.withTransaction
import com.siba.searchmvvmpractice.local.dao.SearchTermDao
import com.siba.searchmvvmpractice.local.database.SearchTermDatabase
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.model.SearchTermData
import com.siba.searchmvvmpractice.remote.RetrofitService
import com.siba.searchmvvmpractice.remote.api.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(
    private val retrofitService: RetrofitService,
    private val searchTermDao: SearchTermDao
) {
    suspend fun fetchUser(userName : String) = retrofitService.getUsers(userName)

    suspend fun fetchRepo(repoName : String) = retrofitService.getRepos(repoName)

    suspend fun insert(recentSearchTerm: RecentSearchTerm){
        searchTermDao.insertKeyword(recentSearchTerm)
    }

    fun getAll() = searchTermDao.getAllKeyword()
}