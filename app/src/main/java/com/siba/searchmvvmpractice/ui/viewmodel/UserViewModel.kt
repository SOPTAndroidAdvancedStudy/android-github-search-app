package com.siba.searchmvvmpractice.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.siba.searchmvvmpractice.repository.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userRepository : UserRepository
) : ViewModel() {

}