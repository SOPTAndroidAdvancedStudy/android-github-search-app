package com.siba.searchmvvmpractice.repository

import com.siba.searchmvvmpractice.local.dao.SearchDao
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.remote.RetrofitService
import com.siba.searchmvvmpractice.remote.model.UserCatalog
import com.siba.searchmvvmpractice.remote.model.UserRepositoryCatalog
import com.siba.searchmvvmpractice.remote.model.toDatabaseGithubRepositoryInfo
import com.siba.searchmvvmpractice.remote.model.toDatabaseGithubUserInfo

class SearchRepository(
    private val retrofitService: RetrofitService,
    private val searchDao: SearchDao
) {
    suspend fun fetchUser(userName: String): UserCatalog = retrofitService.getUsers(userName)

    suspend fun fetchRepo(repositoryName: String): UserRepositoryCatalog =
        retrofitService.getRepositories(repositoryName)


    // offline Cache
    suspend fun insertGithubUser(userName: String) =
        searchDao.insertGithubUser(fetchUser(userName).toDatabaseGithubUserInfo(userName))

    suspend fun insertGithubRepository(repositoryName: String) =
        searchDao.insertGithubRepository(
            fetchRepo(repositoryName).toDatabaseGithubRepositoryInfo(repositoryName)
        )

    fun fetchGithubUserDatabase(userName : String) =
        searchDao.getAllGithubUser(userName)



    // Recent_Search_Term
    suspend fun insert(recentSearchTerm: RecentSearchTerm) {
        searchDao.insertKeyword(recentSearchTerm)
    }

    fun getAll() = searchDao.getAllKeyword()
}