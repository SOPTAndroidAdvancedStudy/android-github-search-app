package com.siba.searchmvvmpractice.util

import android.widget.SearchView
import androidx.databinding.BindingAdapter
import com.siba.searchmvvmpractice.vm.SearchUserViewModel

object CustomBindingAdapter {

    @BindingAdapter("setOnQueryTextListener")
    @JvmStatic
    fun setOnQueryTextListener(searchView: SearchView , searchKey : String) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {keyWord ->

                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }
}