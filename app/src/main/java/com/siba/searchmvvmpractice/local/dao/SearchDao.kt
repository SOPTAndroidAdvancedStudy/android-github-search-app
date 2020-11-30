package com.siba.searchmvvmpractice.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubRepositoryInfo
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubUserInfo
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm

@Dao
interface SearchDao {
    // RecentSearchTerm Function
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearchTerm(recentSearchTerm: RecentSearchTerm)

    @Query("SELECT * FROM recent_search_term_table ORDER BY searchingTime DESC LIMIT 5")
    fun getAllKeyword(): LiveData<List<RecentSearchTerm>>

    // offline Cache
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGithubUser(users : List<DatabaseGithubUserInfo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGithubRepository(databaseGithubRepositoryInfo: DatabaseGithubRepositoryInfo)

    @Query("SELECT * FROM github_user_info_table WHERE login LIKE '%' || :keyword || '%' ")
    fun getAllGithubUser(keyword : String) : LiveData<List<DatabaseGithubUserInfo>>

    @Query("SELECT * FROM github_repository_info_table WHERE repositoryName = :keyword ")
    fun getAllGithubRepository(keyword : String) : LiveData<List<DatabaseGithubRepositoryInfo>>
}