package com.siba.searchmvvmpractice.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.SearchTermItemBinding
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm

class SearchTermAdapter :
    RecyclerView.Adapter<SearchTermAdapter.SearchTermViewHolder>() {
    private val searchTerm = mutableListOf<RecentSearchTerm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTermViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SearchTermItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.search_term_item, parent, false)
        return SearchTermViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchTermViewHolder, position: Int) {
        holder.bind(searchTerm[position])
    }

    override fun getItemCount(): Int = searchTerm.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: List<RecentSearchTerm>) {
        searchTerm.clear()
        searchTerm.addAll(item)
        notifyDataSetChanged()
    }

    inner class SearchTermViewHolder(private val binding: SearchTermItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recentSearchTerm: RecentSearchTerm) {
            binding.item = recentSearchTerm
        }
    }
}

/*
@BindingAdapter("search_term_item")
fun RecyclerView.setItems(items: List<RecentSearchTerm>) {
    (adapter as? SearchTermAdapter)?.run {
        submitList(items)
    }
}*/
