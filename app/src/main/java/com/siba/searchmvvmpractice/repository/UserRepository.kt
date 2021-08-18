package com.siba.searchmvvmpractice.repository

import com.siba.searchmvvmpractice.api.GithubService
import com.siba.searchmvvmpractice.application.AppExecutors
import com.siba.searchmvvmpractice.local.dao.UserDao
import com.siba.searchmvvmpractice.local.database.GithubDb
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db : GithubDb,
    private val userDao : UserDao,
    private val githubService: GithubService
){

}