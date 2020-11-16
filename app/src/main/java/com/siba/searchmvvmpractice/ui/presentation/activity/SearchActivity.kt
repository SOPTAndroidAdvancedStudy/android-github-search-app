package com.siba.searchmvvmpractice.ui.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.ui.adapter.ViewPagerAdapter
import com.siba.searchmvvmpractice.databinding.ActivitySearchBinding
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding
    private val viewModel : SearchViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this

        setAdapter(supportFragmentManager)
        setSearchView(binding.searchviewMain)
    }

    fun setSearchView(searchView: SearchView){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.userName.value = query!!
                viewModel.searchUser()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun setAdapter(fm : FragmentManager){
        binding.viewpagerMain.apply {
            adapter = ViewPagerAdapter(fm)
        }
        binding.tabMain.apply {
            setupWithViewPager(binding.viewpagerMain)
            getTabAt(0)?.text = "모든유저검색"
            getTabAt(1)?.text = "특정유저검색"
        }
    }
}