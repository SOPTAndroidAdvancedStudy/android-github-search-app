package com.siba.searchmvvmpractice.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.siba.searchmvvmpractice.ui.presentation.fragment.SearchRepoFragment
import com.siba.searchmvvmpractice.ui.presentation.fragment.SearchUserFragment

class ViewPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SearchUserFragment()
            else -> SearchRepoFragment()
        }
    }

}