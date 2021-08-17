package com.siba.searchmvvmpractice.ui.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siba.searchmvvmpractice.databinding.FragmentSearchRepoBinding
import com.siba.searchmvvmpractice.databinding.RepoItemBinding
import com.siba.searchmvvmpractice.domain.DomainRepository
import com.siba.searchmvvmpractice.ui.adapter.RepoAdapter
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel
import com.siba.searchmvvmpractice.utils.autoCleared

class SearchRepoFragment : Fragment() {
    private var binding by autoCleared<FragmentSearchRepoBinding>()

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var repoAdapter: RepoAdapter<RepoItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchRepoBinding.inflate(inflater,container,false).also { FragmentSearchRepoBinding ->
        binding = FragmentSearchRepoBinding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        repoAdapter = RepoAdapter()
        configAdapter()
        setAdapterData()
    }

    private fun configAdapter() {
        binding.searchRepoRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repoAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    /**
     * TODO : 로직 교체 해야함
     */
    private fun setAdapterData(){
        viewModel.keyword.observe(viewLifecycleOwner){
            viewModel.fetchGithubRepositoryFromAppDatabase(it).observe(viewLifecycleOwner){data ->
                repoAdapter.data = data as MutableList<DomainRepository>
                repoAdapter.notifyDataSetChanged()
            }
        }
    }

}
