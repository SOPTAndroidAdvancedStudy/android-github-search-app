package com.siba.searchmvvmpractice.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.siba.searchmvvmpractice.vo.Contributor
import com.siba.searchmvvmpractice.vo.Repo
import com.siba.searchmvvmpractice.vo.RepoSearchResult
import com.siba.searchmvvmpractice.vo.User

@Database(
    entities = [User::class, Repo::class, Contributor::class, RepoSearchResult::class],
    version = 2,
    exportSchema = false
)
abstract class GithubDb : RoomDatabase() {
    abstract val repoDao: RepoDao
    abstract val userDao: UserDao
}