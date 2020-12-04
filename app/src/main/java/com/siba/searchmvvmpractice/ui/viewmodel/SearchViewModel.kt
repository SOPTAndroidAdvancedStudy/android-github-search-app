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
    var networkChecked: Boolean = false

    private val _keyword = MutableLiveData<String>()
    val keyword: MutableLiveData<String>
        get() = _keyword

    // RecentSearchTerm
    var allRecentSearchTerm: LiveData<List<RecentSearchTerm>> = repository.getAllSearchTerm()


    fun insertRecentSearchTermToAppDatabase() = viewModelScope.launch {
        val recentSearchTerm = RecentSearchTerm(keyword = _keyword.value.toString())
        repository.insertRecentSearchTerm(recentSearchTerm)
    }

    // GithubUser

    // this function must use Network connect state is on
    fun insertGithubUserToAppDatabase() = viewModelScope.launch {
        repository.insertGithubUserToAppDatabase(_keyword.value.toString())
    }

    // this function use Network connect state is off
    fun fetchGithubUserFromAppDatabase(keyword: String): LiveData<List<DomainUsers>> {
        return repository.fetchDatabaseGithubUser(keyword)
    }

    // GithubRepository

    // this function must use Network connect state is on
    fun insertGithubRepositoryToAppDatabase() = viewModelScope.launch {
        repository.insertGithubRepositoryToAppDatabase(_keyword.value.toString())
    }

    // this function use Network connect state is off
    fun fetchGithubRepositoryFromAppDatabase(keyword: String): LiveData<List<DomainRepository>> {
        return repository.fetchDatabaseGithubRepository(keyword)
    }

}