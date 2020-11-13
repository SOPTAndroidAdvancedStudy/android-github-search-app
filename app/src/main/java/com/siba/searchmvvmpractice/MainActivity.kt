package com.siba.searchmvvmpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.siba.searchmvvmpractice.adapter.TabLayoutAdapter
import com.siba.searchmvvmpractice.databinding.ActivityMainBinding
import com.siba.searchmvvmpractice.presentation.SearchFragment
import com.siba.searchmvvmpractice.presentation.SearchUserFragment
import com.siba.searchmvvmpractice.vm.SearchUserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var tabAdapter : TabLayoutAdapter

    private val viewModel : SearchUserViewModel by viewModels()

    // 기능이 다른 것이니까 viewModel도 하나 더 만들어야지
    // TODO : New Branch & New ViewModel
    // TODO : MainViewModel name Refactor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel = viewModel
        setSearchView()
        setObserver()
        setAdapter()
        setViewpager()
        setTab()
    }

    fun setSearchView(){
     binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.viewModel?.userName?.value = query!!
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun setObserver(){
        binding.viewModel?.userName?.observe(this, Observer {
            binding.viewModel?.searchUser()
            binding.viewModel?.searchAllUser()
        })
    }

    fun setAdapter(){
        tabAdapter = TabLayoutAdapter(supportFragmentManager)
        tabAdapter.fragments = listOf(
                SearchUserFragment(),
                SearchFragment()
        )
    }

    fun setViewpager(){
        binding.searchResult.apply {
            adapter = tabAdapter
        }
    }

    fun setTab(){
        binding.searchTab.apply {
            setupWithViewPager(binding.searchResult)
            getTabAt(0)?.text = "유저 검색"
            getTabAt(1)?.text = "레포지토리 검색"
        }
    }
}