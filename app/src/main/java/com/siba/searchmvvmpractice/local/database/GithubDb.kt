package com.siba.searchmvvmpractice.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.siba.searchmvvmpractice.local.dao.RepoDao
import com.siba.searchmvvmpractice.local.dao.SearchDao
import com.siba.searchmvvmpractice.local.dao.UserDao
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubRepositoryInfo
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubUserInfo
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm

@Database(
    entities = [RecentSearchTerm::class, DatabaseGithubUserInfo::class, DatabaseGithubRepositoryInfo::class],
    version = 2,
    exportSchema = false
)
abstract class GithubDb : RoomDatabase() {
    abstract val searchDao: SearchDao
    abstract val repoDao: RepoDao
    abstract val userDao: UserDao
}