package com.siba.searchmvvmpractice.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm

@Dao
interface SearchTermDao {
    @Insert
    suspend fun insertKeyword(recentSearchTerm: RecentSearchTerm)

    @Query("DELETE FROM recent_search_term_table")
    suspend fun clear()

    @Query("SELECT * FROM recent_search_term_table")
    fun getAllKeyword() : LiveData<List<RecentSearchTerm>>
}