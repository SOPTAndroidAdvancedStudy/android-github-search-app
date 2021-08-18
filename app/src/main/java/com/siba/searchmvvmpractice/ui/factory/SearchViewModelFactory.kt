package com.siba.searchmvvmpractice.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.siba.searchmvvmpractice.repository.SearchRepository
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(private val repository: SearchRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(SearchViewModel::class.java)) { "Unknown class name" }
        return SearchViewModel(repository) as T
    }

}