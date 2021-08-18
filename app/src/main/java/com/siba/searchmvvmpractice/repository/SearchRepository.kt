package com.siba.searchmvvmpractice.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.siba.searchmvvmpractice.api.GithubService
import com.siba.searchmvvmpractice.api.model.repository.UserRepositoryCatalog
import com.siba.searchmvvmpractice.api.model.repository.asDatabaseModel
import com.siba.searchmvvmpractice.api.model.user.UserCatalog
import com.siba.searchmvvmpractice.api.model.user.asDatabaseModel
import com.siba.searchmvvmpractice.application.AppExecutors
import com.siba.searchmvvmpractice.domain.DomainRepository
import com.siba.searchmvvmpractice.domain.DomainUsers
import com.siba.searchmvvmpractice.local.dao.SearchDao
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.local.entity.asDomainRepository
import com.siba.searchmvvmpractice.local.entity.asDomainUsers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val githubService: GithubService,
    private val searchDao: SearchDao,
) {
    suspend fun fetchUser(userName: String): UserCatalog = githubService.getUsers(userName)

    suspend fun fetchRepository(repoName : String) : UserRepositoryCatalog = githubService.getRepositories(repoName)

    suspend fun insertRecentSearchTerm(recentSearchTerm: RecentSearchTerm) {
        searchDao.insertRecentSearchTerm(recentSearchTerm)
    }

    fun getAllSearchTerm() = searchDao.getAllKeyword()

    fun fetchDatabaseGithubUser(keyword: String): LiveData<List<DomainUsers>> {
        return Transformations.map(searchDao.getAllGithubUser(keyword)) {
            it.asDomainUsers()
        }
    }

    fun fetchDatabaseGithubRepository(keyword: String): LiveData<List<DomainRepository>> {
        return Transformations.map(searchDao.getAllGithubRepository(keyword)) {
            it.asDomainRepository()
        }
    }

    suspend fun insertGithubUserToAppDatabase(keyword: String) =
        searchDao.insertGithubUser(fetchUser(keyword).asDatabaseModel())

    suspend fun insertGithubRepositoryToAppDatabase(keyword: String) =
        searchDao.insertGithubRepository(fetchRepository(keyword).asDatabaseModel())

}