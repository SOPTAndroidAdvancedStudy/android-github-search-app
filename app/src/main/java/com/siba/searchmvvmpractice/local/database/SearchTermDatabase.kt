package com.siba.searchmvvmpractice.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.siba.searchmvvmpractice.local.dao.SearchTermDao
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm

@Database(entities = [RecentSearchTerm::class], version = 1)
abstract class SearchTermDatabase : RoomDatabase() {
    abstract val searchTermDao: SearchTermDao

    companion object {
        @Volatile
        private var INSTANCE: SearchTermDatabase? = null

        fun getInstance(context: Context): SearchTermDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SearchTermDatabase::class.java,
                        "search_keyword_history_database2"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}