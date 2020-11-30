package com.siba.searchmvvmpractice.ui.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.ActivitySearchBinding
import com.siba.searchmvvmpractice.databinding.SearchTermItemBinding
import com.siba.searchmvvmpractice.injection.Injection
import com.siba.searchmvvmpractice.ui.adapter.SearchTermAdapter
import com.siba.searchmvvmpractice.ui.adapter.ViewPagerAdapter
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel

    private lateinit var searchTermAdapter: SearchTermAdapter<SearchTermItemBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        initViewModel()
        initViews()
        setViewPagerAdapter(supportFragmentManager)
        setSearchView(binding.searchviewMain)
        setSearchTermRecyclerView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this@SearchActivity, Injection.provideSearchViewModelFactory(this)).get(
            SearchViewModel::class.java
        )
    }

    private fun initViews() {
        searchTermAdapter = SearchTermAdapter()
        viewModel.allRecentSearchTerm.observe(this) {
            searchTermAdapter.setData(it)
        }
        searchTermAdapter.notifyDataSetChanged()
    }

    private fun setSearchTermRecyclerView() {
        binding.searchTermRecyclerviewMain.apply {
            adapter = searchTermAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@SearchActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    private fun setViewPagerAdapter(fm: FragmentManager) {
        binding.viewpagerMain.apply {
            adapter = ViewPagerAdapter(fm)
        }
        binding.tabMain.apply {
            setupWithViewPager(binding.viewpagerMain)
            getTabAt(0)?.text = "User"
            getTabAt(1)?.text = "Repository"
        }
    }

    private fun setSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.keyword.value = query!!
                search()
                viewModel.insertRecentSearchTermToAppDatabase()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun search() {
        if (binding.tabMain.selectedTabPosition == 0) {
            viewModel.insertGithubUserToAppDatabase()
        }
        else {
            viewModel.insertGithubRepositoryToAppDatabase()
        }
    }

}