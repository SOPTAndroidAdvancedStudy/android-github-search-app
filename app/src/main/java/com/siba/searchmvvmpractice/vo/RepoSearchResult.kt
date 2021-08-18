package com.siba.searchmvvmpractice.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.siba.searchmvvmpractice.local.GithubTypeConverters

@Entity(primaryKeys = ["query"])
@TypeConverters(GithubTypeConverters::class)
data class RepoSearchResult(
    val query: String,
    val repoIds: List<Int>,
    val totalCount: Int,
    val next: Int?
)
