package com.siba.searchmvvmpractice.ui.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.ActivitySearchBinding
import com.siba.searchmvvmpractice.databinding.SearchTermItemBinding
import com.siba.searchmvvmpractice.injection.Injection
import com.siba.searchmvvmpractice.ui.adapter.SearchTermAdapter
import com.siba.searchmvvmpractice.ui.adapter.ViewPagerAdapter
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel
import com.siba.searchmvvmpractice.utils.NetworkConnectionCheck

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    // ViewModel by로 한다음에 람다식으로 viewModel 선언해놓는 것으로 생각하는게 좋겠다.
    private val viewModel: SearchViewModel by viewModels { Injection.provideSearchViewModelFactory(this)  }
    private lateinit var networkConnectionCheck: NetworkConnectionCheck
    private lateinit var searchTermAdapter: SearchTermAdapter<SearchTermItemBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        initViews()
        networkConnectionCheck = NetworkConnectionCheck(this, viewModel)
        setViewPagerAdapter(supportFragmentManager)
        setSearchView(binding.searchviewMain)
        setSearchTermRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        networkConnectionCheck.registerNetworkCallback()
    }

    override fun onStop() {
        super.onStop()
        networkConnectionCheck.terminateNetworkCallback()
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
                if (viewModel.networkChecked) {
                    viewModel.insertRecentSearchTermToAppDatabase()
                    search()
                }
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
        } else {
            viewModel.insertGithubRepositoryToAppDatabase()
        }
    }

}