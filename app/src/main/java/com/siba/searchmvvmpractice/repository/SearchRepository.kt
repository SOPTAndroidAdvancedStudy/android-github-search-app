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
    // Fixme : insert RecentKeyword will do networking is success
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

    // Fixme: 여기 로직 문제 있음 , fetchUser(keyword)가 이미 네트워킹이 되는 상태라서 네트워크상태에서 AppDatabase에 넣는다는게 이상함.
    suspend fun insertGithubUserToAppDatabase(keyword: String) =
        searchDao.insertGithubUser(fetchUser(keyword).asDatabaseModel())

    suspend fun insertGithubRepositoryToAppDatabase(keyword : String) =
        searchDao.insertGithubRepository(fetchRepository(keyword).asDatabaseModel())

}