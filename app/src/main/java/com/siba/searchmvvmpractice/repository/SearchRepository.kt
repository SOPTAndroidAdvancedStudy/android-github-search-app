package com.siba.searchmvvmpractice.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.siba.searchmvvmpractice.domain.DomainRepository
import com.siba.searchmvvmpractice.domain.DomainUsers
import com.siba.searchmvvmpractice.local.dao.SearchDao
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.local.entity.asDomainRepository
import com.siba.searchmvvmpractice.local.entity.asDomainUsers
import com.siba.searchmvvmpractice.remote.RetrofitService
import com.siba.searchmvvmpractice.remote.model.repository.UserRepositoryCatalog
import com.siba.searchmvvmpractice.remote.model.repository.asDatabaseModel
import com.siba.searchmvvmpractice.remote.model.user.UserCatalog
import com.siba.searchmvvmpractice.remote.model.user.asDatabaseModel

class SearchRepository(
    private val retrofitService: RetrofitService,
    private val searchDao: SearchDao
) {
    private suspend fun fetchUser(userName: String): UserCatalog = retrofitService.getUsers(userName)

    private suspend fun fetchRepository(repositoryName: String): UserRepositoryCatalog =
        retrofitService.getRepositories(repositoryName)

    // Recent_Search_Term
    suspend fun insertRecentSearchTerm(recentSearchTerm: RecentSearchTerm) {
        searchDao.insertRecentSearchTerm(recentSearchTerm)
    }

    fun getAllSearchTerm() = searchDao.getAllKeyword()

    // offline Cache
    fun fetchDatabaseGithubUser(keyword : String) : LiveData<List<DomainUsers>>{
        return Transformations.map(searchDao.getAllGithubUser(keyword)) {
            it.asDomainUsers()
        }
    }

    suspend fun insertGithubUserToAppDatabase(keyword: String) =
        searchDao.insertGithubUser(fetchUser(keyword).asDatabaseModel())

    suspend fun insertGithubRepositoryToAppDatabase(keyword : String) =
        searchDao.insertGithubRepository(fetchRepository(keyword).asDatabaseModel())

    fun fetchDatabaseGithubRepository(keyword : String) : LiveData<List<DomainRepository>>{
        return Transformations.map(searchDao.getAllGithubRepository(keyword)){
            it.asDomainRepository()
        }
    }

}