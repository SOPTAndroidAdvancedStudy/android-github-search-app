package com.siba.searchmvvmpractice.repo

import com.siba.searchmvvmpractice.api.GithubUserSearchHelper

class GithubAllUserRepository(private val githubUserSearchHelper: GithubUserSearchHelper) {
    suspend fun getGithubAllUser() = githubUserSearchHelper.getGithubAllUser()
}