package com.siba.searchmvvmpractice.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.FragmentSearchUserBinding
import com.siba.searchmvvmpractice.vm.MainViewModel

class SearchUserFragment : Fragment() {

    private lateinit var binding : FragmentSearchUserBinding

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_user,container,false)
        binding.viewModel = viewModel
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        setObserver()
    }

    fun setObserver(){
        binding.viewModel?.githubUser?.observe(viewLifecycleOwner, Observer {
            binding.userName.text = it.name
            binding.follower.text = it.followers.toString()
            binding.following.text = it.following.toString()
        })
    }
}