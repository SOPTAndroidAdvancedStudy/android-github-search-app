package com.siba.searchmvvmpractice.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.siba.searchmvvmpractice.repository.SearchRepository
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(private val repository: SearchRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    /*    if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")*/
        
        // SearchViewModel(repository) as T에서 T는 ViewModel 로 타입이 일치한다. 하지만 에러가 나타나 suppress 해주었다.
        require(modelClass.isAssignableFrom(SearchViewModel::class.java)){"Unknown class name"}
        return SearchViewModel(repository) as T
    }

}