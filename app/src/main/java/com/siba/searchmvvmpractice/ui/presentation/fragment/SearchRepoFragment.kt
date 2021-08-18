package com.siba.searchmvvmpractice.ui.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siba.searchmvvmpractice.databinding.FragmentSearchRepoBinding
import com.siba.searchmvvmpractice.databinding.RepoItemBinding
import com.siba.searchmvvmpractice.di.DaggerAppComponent
import com.siba.searchmvvmpractice.ui.adapter.RepoAdapter
import com.siba.searchmvvmpractice.ui.viewmodel.RepoViewModel
import com.siba.searchmvvmpractice.utils.autoCleared
import javax.inject.Inject

class SearchRepoFragment : Fragment() {
    private var binding by autoCleared<FragmentSearchRepoBinding>()

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val viewModel: RepoViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var repoAdapter: RepoAdapter<RepoItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchRepoBinding.inflate(inflater,container,false).also { FragmentSearchRepoBinding ->
        binding = FragmentSearchRepoBinding
        DaggerAppComponent.builder().application(requireActivity().application).build().fragmentComponent().create().inject(this)
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        repoAdapter = RepoAdapter()
        configAdapter()
    }

    private fun configAdapter() {
        binding.searchRepoRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repoAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }


}
