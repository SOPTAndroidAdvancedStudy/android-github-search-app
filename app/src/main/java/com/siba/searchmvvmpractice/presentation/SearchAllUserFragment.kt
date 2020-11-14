package com.siba.searchmvvmpractice.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.adapter.UserAdapter
import com.siba.searchmvvmpractice.data.Items
import com.siba.searchmvvmpractice.databinding.FragmentSearchAllUserBinding
import com.siba.searchmvvmpractice.databinding.UserItemBinding
import com.siba.searchmvvmpractice.vm.SearchUserViewModel

class SearchAllUserFragment : Fragment() {
    // TODO : Rename it ViewModel name is not match
    private lateinit var binding : FragmentSearchAllUserBinding

    private val viewModel : SearchUserViewModel by activityViewModels()
    private lateinit var userAdapter : UserAdapter<UserItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_all_user,container,false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        userAdapter = UserAdapter()
        setObserver()
        setAdapter()
    }

    private fun setAdapter() {
        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }

    fun setObserver(){
        binding.viewModel?.githubAllUser?.observe(viewLifecycleOwner, Observer {
            userAdapter.data = it.items as MutableList<Items>
            userAdapter.notifyDataSetChanged()
        })
    }
}