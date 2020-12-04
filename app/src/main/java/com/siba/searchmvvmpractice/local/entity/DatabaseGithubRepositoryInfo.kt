package com.siba.searchmvvmpractice.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.siba.searchmvvmpractice.domain.DomainRepository

@Entity(tableName = "github_repository_info_table")
data class DatabaseGithubRepositoryInfo(
    @PrimaryKey
    @ColumnInfo(name = "full_name")
    val full_name : String,
    @ColumnInfo(name = "html_url")
    val html_url: String
)

fun List<DatabaseGithubRepositoryInfo>.asDomainRepository() : List<DomainRepository>{
    return map {
        DomainRepository(
            full_name = it.full_name,
            html_url = it.html_url
        )
    }
}

