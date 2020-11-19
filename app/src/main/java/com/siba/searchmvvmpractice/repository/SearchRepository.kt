package com.siba.searchmvvmpractice.repository

import com.siba.searchmvvmpractice.local.dao.SearchTermDao
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.remote.RetrofitService

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