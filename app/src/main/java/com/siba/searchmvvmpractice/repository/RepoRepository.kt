package com.siba.searchmvvmpractice.repository

import com.siba.searchmvvmpractice.api.GithubService
import com.siba.searchmvvmpractice.application.AppExecutors
import com.siba.searchmvvmpractice.db.GithubDb
import com.siba.searchmvvmpractice.db.UserDao
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db : GithubDb,
    private val userDao : UserDao,
    private val githubService: GithubService
) {
}