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
import com.siba.searchmvvmpractice.databinding.FragmentSearchUserBinding
import com.siba.searchmvvmpractice.databinding.UserItemBinding
import com.siba.searchmvvmpractice.di.DaggerAppComponent
import com.siba.searchmvvmpractice.ui.adapter.UserAdapter
import com.siba.searchmvvmpractice.ui.viewmodel.UserViewModel
import com.siba.searchmvvmpractice.utils.autoCleared
import javax.inject.Inject

class SearchUserFragment : Fragment() {
    private var binding by autoCleared<FragmentSearchUserBinding>()

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    private val viewModel: UserViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var userAdapter: UserAdapter<UserItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchUserBinding.inflate(inflater,container,false).also { FragmentSearchUserBinding ->
        binding = FragmentSearchUserBinding
        DaggerAppComponent.builder().application(requireActivity().application).build().fragmentComponent().create().inject(this)
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        userAdapter = UserAdapter()
        configAdapter()
    }

    private fun configAdapter() {
        binding.searchUserRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }


}