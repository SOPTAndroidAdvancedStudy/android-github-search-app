package com.siba.searchmvvmpractice.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.remote.model.UserCatalog
import com.siba.searchmvvmpractice.remote.model.UserRepositoryCatalog
import com.siba.searchmvvmpractice.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
        private val repository: SearchRepository
) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: MutableLiveData<String>
        get() = _userName

    private val _githubUser = MutableLiveData<UserCatalog>()
    val githubUser: MutableLiveData<UserCatalog>
        get() = _githubUser

    private val _githubRepo = MutableLiveData<UserRepositoryCatalog>()
    val githubRepo: MutableLiveData<UserRepositoryCatalog>
        get() = _githubRepo

    var allSearch: LiveData<List<RecentSearchTerm>> = repository.getAll()

    fun searchUser() = viewModelScope.launch {
        try {
            _githubUser.value = repository.fetchUser(userName.value.toString())
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun searchRepo() = viewModelScope.launch {
        try {
            _githubRepo.value = repository.fetchRepo(userName.value.toString())
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun saveSearchTerm() = viewModelScope.launch(Dispatchers.IO) {
        val recentSearchTerm = RecentSearchTerm(keyword = userName.value.toString())
        repository.insert(recentSearchTerm)
    }

}