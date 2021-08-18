package com.siba.searchmvvmpractice.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.siba.searchmvvmpractice.repository.RepoRepository
import javax.inject.Inject

class RepoViewModel @Inject constructor(
    private val repoRepository: RepoRepository
) : ViewModel() {

}