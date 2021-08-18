package com.siba.searchmvvmpractice.ui.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.ActivitySearchBinding
import com.siba.searchmvvmpractice.di.DaggerAppComponent
import com.siba.searchmvvmpractice.ui.adapter.SearchTermAdapter
import com.siba.searchmvvmpractice.ui.adapter.ViewPagerAdapter
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var searchTermAdapter: SearchTermAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent.builder().application(application).build().inject(this)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        searchTermAdapter = SearchTermAdapter()

        observeKeyword()
        setViewPagerAdapter(supportFragmentManager)
        configSearchTerm()
    }

    private fun observeKeyword() {
        viewModel.keyword.observe(this) {
            viewModel.insertRecentSearchTermToAppDatabase()
            search()
        }
    }

    private fun configSearchTerm() {
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

    private fun search() {
        if (binding.tabMain.selectedTabPosition == 0) {
            viewModel.insertGithubUserToAppDatabase()
        } else {
            viewModel.insertGithubRepositoryToAppDatabase()
        }
    }

}