package com.siba.searchmvvmpractice.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siba.searchmvvmpractice.local.entity.DatabaseGithubUserInfo
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm
import com.siba.searchmvvmpractice.remote.model.UserCatalog
import com.siba.searchmvvmpractice.remote.model.UserRepositoryCatalog
import com.siba.searchmvvmpractice.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
        private val repository: SearchRepository
) : ViewModel() {

    private val _keyword = MutableLiveData<String>()
    val keyword: MutableLiveData<String>
        get() = _keyword

    private val _githubUser = MutableLiveData<UserCatalog>()
    val githubUser: MutableLiveData<UserCatalog>
        get() = _githubUser

    private val _githubRepo = MutableLiveData<UserRepositoryCatalog>()
    val githubRepo: MutableLiveData<UserRepositoryCatalog>
        get() = _githubRepo


    var allSearch: LiveData<List<RecentSearchTerm>> = repository.getAll()

    var allData : LiveData<DatabaseGithubUserInfo> = repository.fetchGithubUserDatabase(keyword.value.toString())

    fun searchUser() = viewModelScope.launch {
        try {
            _githubUser.value = repository.fetchUser(keyword.value.toString())
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


    fun searchRepo() = viewModelScope.launch {
        try {
            _githubRepo.value = repository.fetchRepo(keyword.value.toString())
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun saveSearchTerm() = viewModelScope.launch(Dispatchers.IO) {
        val recentSearchTerm = RecentSearchTerm(keyword = keyword.value.toString())
        repository.insert(recentSearchTerm)
    }

    fun insertUserToDatabase() = viewModelScope.launch {
        repository.insertGithubUser(keyword.value.toString())
        Log.i("SearchViewModel","insert done")
    }
}