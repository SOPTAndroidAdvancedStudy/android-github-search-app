package com.siba.searchmvvmpractice.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.siba.searchmvvmpractice.repository.RepoRepository
import com.siba.searchmvvmpractice.utils.AbsentLiveData
import com.siba.searchmvvmpractice.vo.Repo
import com.siba.searchmvvmpractice.vo.Resource
import javax.inject.Inject

class RepoViewModel @Inject constructor(repository: RepoRepository) : ViewModel() {
    private val _repoId: MutableLiveData<RepoId> = MutableLiveData()
    val repoId: LiveData<RepoId>
        get() = _repoId
    val repo : LiveData<Resource<Repo>> = _repoId.switchMap { input ->
        input.ifExists { owner, name ->
            repository.loadRepo(owner,name)
        }
    }

    data class RepoId(val owner: String, val name: String) {
        fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
            return if (owner.isBlank() || name.isBlank()) {
                AbsentLiveData.create()
            } else {
                f(owner, name)
            }
        }
    }
}