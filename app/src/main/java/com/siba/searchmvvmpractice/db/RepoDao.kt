package com.siba.searchmvvmpractice.db

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siba.searchmvvmpractice.vo.Contributor
import com.siba.searchmvvmpractice.vo.Repo
import com.siba.searchmvvmpractice.vo.RepoSearchResult

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertContributors(contributors: List<Contributor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun createRepoIfNotExists(repo: Repo): Long

    @Query("SELECT * FROM repo WHERE owner_login = :ownerLogin AND name = :name")
    abstract fun load(ownerLogin: String, name: String): LiveData<Repo>

    @Query(
        """
            SELECT login , avatarUrl,repoName,repoOwner , contributions FROM contributor
            WHERE repoName = :name AND repoOwner = :owner
            ORDER BY contributions DESC"""
    )
    abstract fun loadContributors(owner: String, name: String): LiveData<List<Contributor>>

    @Query(
        """
            SELECT * FROM Repo
            WHERE owner_login = :owner
            ORDER BY stars DESC"""
    )
    abstract fun loadRepositories(owner: String): LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: RepoSearchResult)

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun search(query: String): LiveData<RepoSearchResult>

    fun loadOrdered(repoIds: List<Int>): LiveData<List<Repo>> {
        val order = SparseIntArray()
        repoIds.withIndex().forEach {
            order.put(it.value, it.index)
        }
        return loadById(repoIds).map { repositories ->
            repositories.sortedWith(compareBy { order.get(it.id) })
        }
    }

    /**
     * SSong-develop
     *
     * TODO : 왜 protected abstract를 사용해서 override 할 수 있게 만들어 놨을까?
     */
    @Query("SELECT * FROM Repo WHERE id in (:repoIds)")
    protected abstract fun loadById(repoIds: List<Int>): LiveData<List<Repo>>

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun findSearchResult(query: String): RepoSearchResult
}