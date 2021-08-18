package com.siba.searchmvvmpractice.ui.presentation.fragment

import com.siba.searchmvvmpractice.ui.presentation.activity.SearchActivity
import dagger.Subcomponent

@Subcomponent
interface FragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FragmentComponent
    }

    fun inject(activity: SearchActivity)

    fun inject(searchRepoFragment: SearchRepoFragment)

    fun inject(searchUserFragment: SearchUserFragment)
}