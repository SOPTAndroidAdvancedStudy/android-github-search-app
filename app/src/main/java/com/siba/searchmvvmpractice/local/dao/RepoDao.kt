package com.siba.searchmvvmpractice.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.siba.searchmvvmpractice.vo.Contributor
import com.siba.searchmvvmpractice.vo.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg repos : Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContributors(contributors : List<Contributor>)

}