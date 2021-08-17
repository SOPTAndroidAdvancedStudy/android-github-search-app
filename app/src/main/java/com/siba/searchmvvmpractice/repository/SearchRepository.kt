package com.siba.searchmvvmpractice.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.siba.searchmvvmpractice.domain.DomainRepository
import com.siba.searchmvvmpractice.domain.DomainUsers
import com.siba.searchmvvmpractice.local.dao.SearchDao
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.local.entity.asDomainRepository
import com.siba.searchmvvmpractice.local.entity.asDomainUsers
import com.siba.searchmvvmpractice.api.GithubService
import com.siba.searchmvvmpractice.api.model.repository.UserRepositoryCatalog
import com.siba.searchmvvmpractice.api.model.repository.asDatabaseModel
import com.siba.searchmvvmpractice.api.model.user.UserCatalog
import com.siba.searchmvvmpractice.api.model.user.asDatabaseModel
import com.siba.searchmvvmpractice.application.AppExecutors
import com.siba.searchmvvmpractice.vo.Repo
import com.siba.searchmvvmpractice.vo.RepoSearchResponse
import com.siba.searchmvvmpractice.vo.RepoSearchResult
import com.siba.searchmvvmpractice.vo.Resource

class SearchRepository(
    private val appExecutors : AppExecutors,
    private val githubService: GithubService,
    private val searchDao: SearchDao,
) {
    suspend fun fetchUser(userName: String): UserCatalog = githubService.getUsers(userName)

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

    fun fetchDatabaseGithubRepository(keyword : String) : LiveData<List<DomainRepository>>{
        return Transformations.map(searchDao.getAllGithubRepository(keyword)){
            it.asDomainRepository()
        }
    }

    // 아니지 맞지 훈기야. 네트워킹이 되었을 떄만! AppDatabase에 데이터를 넣어놓을 수 있는거지
    suspend fun insertGithubUserToAppDatabase(keyword: String) =
        searchDao.insertGithubUser(fetchUser(keyword).asDatabaseModel())

    suspend fun insertGithubRepositoryToAppDatabase(keyword : String) =
        searchDao.insertGithubRepository(fetchRepository(keyword).asDatabaseModel())

}