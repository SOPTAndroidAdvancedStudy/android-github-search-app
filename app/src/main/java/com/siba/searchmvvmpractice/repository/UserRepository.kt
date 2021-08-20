package com.siba.searchmvvmpractice.repository

import androidx.lifecycle.LiveData
import com.siba.searchmvvmpractice.api.GithubService
import com.siba.searchmvvmpractice.application.AppExecutors
import com.siba.searchmvvmpractice.local.dao.UserDao
import com.siba.searchmvvmpractice.remote.ApiResponse
import com.siba.searchmvvmpractice.vo.Resource
import com.siba.searchmvvmpractice.vo.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val userDao: UserDao,
    private val githubService: GithubService
) {

    fun loadUser(login: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User): Boolean = data == null

            override fun loadFromDb(): LiveData<User> = userDao.findByLogin(login)

            override fun createCall(): LiveData<ApiResponse<User>> = githubService.getUser(login)
        }.asLiveData()
    }
}