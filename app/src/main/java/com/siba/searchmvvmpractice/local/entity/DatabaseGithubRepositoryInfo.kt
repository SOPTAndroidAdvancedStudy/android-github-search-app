package com.siba.searchmvvmpractice.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repository_info_table")
data class DatabaseGithubRepositoryInfo(
    @PrimaryKey
    val repositoryName : String,
    @ColumnInfo(name = "full_name")
    val full_name : String,
    @ColumnInfo(name = "html_url")
    val html_url: String
)