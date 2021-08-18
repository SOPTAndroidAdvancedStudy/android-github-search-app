package com.siba.searchmvvmpractice.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.siba.searchmvvmpractice.local.dao.RepoDao
import com.siba.searchmvvmpractice.local.dao.SearchDao
import com.siba.searchmvvmpractice.local.dao.UserDao
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubRepositoryInfo
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubUserInfo
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm

@Database(entities = [RecentSearchTerm::class , DatabaseGithubUserInfo::class , DatabaseGithubRepositoryInfo::class], version = 2)
abstract class GithubDb : RoomDatabase() {
    abstract val searchDao: SearchDao
    abstract val repoDao : RepoDao
    abstract val userDao : UserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDb? = null

        fun getInstance(context: Context): GithubDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDb::class.java,
                        "search_keyword_history_database2"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}