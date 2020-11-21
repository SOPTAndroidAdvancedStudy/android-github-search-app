package com.siba.searchmvvmpractice.ui.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.FragmentSearchRepoBinding
import com.siba.searchmvvmpractice.databinding.RepoItemBinding
import com.siba.searchmvvmpractice.remote.model.UserRepository
import com.siba.searchmvvmpractice.ui.adapter.RepoAdapter
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel

class SearchRepoFragment : Fragment() {
    private lateinit var binding: FragmentSearchRepoBinding

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var repoAdapter: RepoAdapter<RepoItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_repo, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        repoAdapter = RepoAdapter()
        setAdapter()
        setObserver()
    }

    private fun setObserver() {
        viewModel.githubRepo.observe(viewLifecycleOwner) {
            repoAdapter.data = it.userRepository as MutableList<UserRepository>
            repoAdapter.notifyDataSetChanged()
        }
    }

    private fun setAdapter() {
        binding.searchRepoRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repoAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

}
