package com.siba.searchmvvmpractice.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siba.searchmvvmpractice.databinding.RepoItemBinding
import com.siba.searchmvvmpractice.domain.DomainRepository

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    private val items = mutableListOf<DomainRepository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RepoItemBinding.inflate(layoutInflater, parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<DomainRepository>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class RepoViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userRepository: DomainRepository) {
            binding.userRepository = userRepository
        }
    }

}