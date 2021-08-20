package com.siba.searchmvvmpractice.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    // two-way DataBinding
    val keyword = MutableLiveData<String>()

}