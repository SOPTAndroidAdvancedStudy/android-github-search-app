package com.siba.searchmvvmpractice.repository

import com.siba.searchmvvmpractice.application.AppExecutors
import com.siba.searchmvvmpractice.local.database.GithubDb
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoRepository @Inject constructor(
    private val appExecutors : AppExecutors,
    private val db : GithubDb,
    private val
) {
}