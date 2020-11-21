package com.siba.searchmvvmpractice.repository

import com.siba.searchmvvmpractice.local.dao.SearchTermDao
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.remote.RetrofitService
import com.siba.searchmvvmpractice.remote.model.UserCatalog
import com.siba.searchmvvmpractice.remote.model.UserRepositoryCatalog

class SearchRepository(
        private val retrofitService: RetrofitService,
        private val searchTermDao: SearchTermDao
) {
    suspend fun fetchUser(userName: String): UserCatalog = retrofitService.getUsers(userName)

    suspend fun fetchRepo(repositoryName: String): UserRepositoryCatalog = retrofitService.getRepositories(repositoryName)

    suspend fun insert(recentSearchTerm: RecentSearchTerm) {
        searchTermDao.insertKeyword(recentSearchTerm)
    }

    fun getAll() = searchTermDao.getAllKeyword()
}