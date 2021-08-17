package com.siba.searchmvvmpractice.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siba.searchmvvmpractice.domain.DomainRepository
import com.siba.searchmvvmpractice.domain.DomainUsers
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    // two-way DataBinding
    val keyword = MutableLiveData<String>()

    var allRecentSearchTerm: LiveData<List<RecentSearchTerm>> = searchRepository.getAllSearchTerm()

    fun insertRecentSearchTermToAppDatabase() = viewModelScope.launch {
        val recentSearchTerm = RecentSearchTerm(keyword = keyword.value.toString())
        searchRepository.insertRecentSearchTerm(recentSearchTerm)
    }

    fun insertGithubUserToAppDatabase() = viewModelScope.launch {
        searchRepository.insertGithubUserToAppDatabase(keyword.value.toString())
    }

    fun fetchGithubUserFromAppDatabase(keyword: String): LiveData<List<DomainUsers>> {
        return searchRepository.fetchDatabaseGithubUser(keyword)
    }

    fun insertGithubRepositoryToAppDatabase() = viewModelScope.launch {
        searchRepository.insertGithubRepositoryToAppDatabase(keyword.value.toString())
    }

    fun fetchGithubRepositoryFromAppDatabase(keyword: String): LiveData<List<DomainRepository>> {
        return searchRepository.fetchDatabaseGithubRepository(keyword)
    }

}