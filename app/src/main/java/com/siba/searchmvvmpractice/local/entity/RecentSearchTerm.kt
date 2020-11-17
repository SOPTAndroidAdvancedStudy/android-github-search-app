package com.siba.searchmvvmpractice.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search_term_table")
data class RecentSearchTerm(
        @PrimaryKey(autoGenerate = true)
        val id : Int,
        val keyword : String
) {
}