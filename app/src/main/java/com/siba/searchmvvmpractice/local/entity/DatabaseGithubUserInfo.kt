package com.siba.searchmvvmpractice.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_user_info_table")
data class DatabaseGithubUserInfo(
    @PrimaryKey
    val userName : String,
    @ColumnInfo(name = "login")
    val login : String,
    @ColumnInfo(name = "url")
    val url : String
)
