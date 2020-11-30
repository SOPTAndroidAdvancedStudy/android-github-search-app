package com.siba.searchmvvmpractice.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.siba.searchmvvmpractice.domain.DomainUsers

@Entity(tableName = "github_user_info_table")
data class DatabaseGithubUserInfo constructor(
    @PrimaryKey
    @ColumnInfo(name = "login")
    val login : String,
    @ColumnInfo(name = "url")
    val url : String
)

fun List<DatabaseGithubUserInfo>.asDomainUsers() : List<DomainUsers>{
    return map {
        DomainUsers(
            login = it.login,
            url = it.url
        )
    }
}