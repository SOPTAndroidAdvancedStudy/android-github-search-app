package com.siba.searchmvvmpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.siba.searchmvvmpractice.adapter.TabLayoutAdapter
import com.siba.searchmvvmpractice.databinding.ActivityMainBinding
import com.siba.searchmvvmpractice.presentation.SearchFragment
import com.siba.searchmvvmpractice.presentation.SearchUserFragment
import com.siba.searchmvvmpractice.vm.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var tabAdapter : TabLayoutAdapter

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        tabAdapter = TabLayoutAdapter(supportFragmentManager)
        tabAdapter.fragments = listOf(
            SearchFragment(),
            SearchUserFragment()
        )
        binding.searchResult.apply {
            adapter = tabAdapter
        }
        binding.searchTab.apply {
            setupWithViewPager(binding.searchResult)
            getTabAt(0)?.text = "첫 번쨰"
            getTabAt(1)?.text = "두 번쨰"
        }
    }
}