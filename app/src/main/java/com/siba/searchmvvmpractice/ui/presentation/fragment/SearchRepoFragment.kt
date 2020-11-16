package com.siba.searchmvvmpractice.ui.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.FragmentSearchRepoBinding
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel

class SearchRepoFragment : Fragment() {
    private lateinit var binding : FragmentSearchRepoBinding

    private val viewModel : SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_repo,container,false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
    }

}
