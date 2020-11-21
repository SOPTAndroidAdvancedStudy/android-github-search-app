package com.siba.searchmvvmpractice.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.siba.searchmvvmpractice.repository.SearchRepository
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel

class SearchViewModelFactory(private val repository: SearchRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}