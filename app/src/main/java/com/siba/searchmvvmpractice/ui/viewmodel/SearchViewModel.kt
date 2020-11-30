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
        private val repository: SearchRepository
) : ViewModel() {

    private val _keyword = MutableLiveData<String>()
    val keyword: MutableLiveData<String>
        get() = _keyword

    var allRecentSearchTerm: LiveData<List<RecentSearchTerm>> = repository.getAllSearchTerm()

    fun insertRecentSearchTermToAppDatabase() = viewModelScope.launch {
        val recentSearchTerm = RecentSearchTerm(keyword = _keyword.value.toString())
        repository.insertRecentSearchTerm(recentSearchTerm)
    }

    fun insertGithubUserToAppDatabase() = viewModelScope.launch {
        repository.insertGithubUserToAppDatabase(_keyword.value.toString())
    }

    fun fetchGithubUserFromAppDatabase(keyword : String): LiveData<List<DomainUsers>> {
        return repository.fetchDatabaseGithubUser(keyword)
    }

    fun insertGithubRepositoryToAppDatabase() = viewModelScope.launch {
        repository.insertGithubRepositoryToAppDatabase(_keyword.value.toString())
    }

    fun fetchGithubRepositoryFromAppDatabase(keyword : String) : LiveData<List<DomainRepository>>{
        return repository.fetchDatabaseGithubRepository(keyword)
    }
}