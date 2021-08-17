package com.siba.searchmvvmpractice.ui.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.siba.searchmvvmpractice.databinding.FragmentSearchUserBinding
import com.siba.searchmvvmpractice.databinding.UserItemBinding
import com.siba.searchmvvmpractice.domain.DomainUsers
import com.siba.searchmvvmpractice.ui.adapter.UserAdapter
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel
import com.siba.searchmvvmpractice.utils.autoCleared

class SearchUserFragment : Fragment() {
    private var binding by autoCleared<FragmentSearchUserBinding>()

    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var userAdapter: UserAdapter<UserItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchUserBinding.inflate(inflater,container,false).also { FragmentSearchUserBinding ->
        binding = FragmentSearchUserBinding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        userAdapter = UserAdapter()
        configAdapter()
        setAdapterData()
    }

    private fun configAdapter() {
        binding.searchUserRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun setAdapterData() {
        // 여기 로직이 쓰레기 , 좋은 형태로 좀 바꿨으면 싶은데 생각이 안남 :(
        viewModel.keyword.observe(viewLifecycleOwner){
            viewModel.fetchGithubUserFromAppDatabase(it).observe(viewLifecycleOwner){data ->
                userAdapter.data = data as MutableList<DomainUsers>
                userAdapter.notifyDataSetChanged()
            }
        }
    }

}