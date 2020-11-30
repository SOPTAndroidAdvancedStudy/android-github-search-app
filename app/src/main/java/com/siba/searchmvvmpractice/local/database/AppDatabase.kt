package com.siba.searchmvvmpractice.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.siba.searchmvvmpractice.local.dao.SearchDao
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubRepositoryInfo
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubUserInfo
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm

@Database(entities = [RecentSearchTerm::class , DatabaseGithubUserInfo::class , DatabaseGithubRepositoryInfo::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract val searchDao: SearchDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "search_keyword_history_database2"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}