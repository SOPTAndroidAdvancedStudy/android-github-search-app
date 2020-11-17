package com.siba.searchmvvmpractice.ui.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.ui.adapter.ViewPagerAdapter
import com.siba.searchmvvmpractice.databinding.ActivitySearchBinding
import com.siba.searchmvvmpractice.local.database.SearchTermDatabase
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel
import com.siba.searchmvvmpractice.utils.Injection

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding
    private lateinit var viewModel : SearchViewModel
    private lateinit var mDatabase : SearchTermDatabase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        initViews()
        initViewModel()
        setViewPagerAdapter(supportFragmentManager)
        setSearchView(binding.searchviewMain)
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this,Injection.provideSearchViewModelFactory(this)).get(SearchViewModel::class.java)
    }

    private fun initViews() {
        mDatabase = SearchTermDatabase.getInstance(this)
    }


    fun setSearchView(searchView: SearchView){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.userName.value = query!!
                viewModel.searchUser()
                viewModel.saveSearchTerm()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun setViewPagerAdapter(fm : FragmentManager){
        binding.viewpagerMain.apply {
            adapter = ViewPagerAdapter(fm)
        }
        binding.tabMain.apply {
            setupWithViewPager(binding.viewpagerMain)
            getTabAt(0)?.text = "User"
            getTabAt(1)?.text = "Repository"
        }
    }
}