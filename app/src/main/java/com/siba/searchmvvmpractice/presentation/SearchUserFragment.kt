package com.siba.searchmvvmpractice.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.FragmentSearchUserBinding
import com.siba.searchmvvmpractice.vm.SearchUserViewModel

class SearchUserFragment : Fragment() {

    private lateinit var binding : FragmentSearchUserBinding

    private val viewModel : SearchUserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_user,container,false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        setObserver()
    }

    fun setObserver(){
        binding.viewModel?.githubUser?.observe(viewLifecycleOwner, Observer {
            // TODO : 어떻게하면 한번에 모든 값을 보여줄 수 있을까
            // One Person Call
        })
        binding.viewModel?.githubAllUser?.observe(viewLifecycleOwner, Observer {
            Log.i("userFragment",it.items[2].toString())
        })
    }
}