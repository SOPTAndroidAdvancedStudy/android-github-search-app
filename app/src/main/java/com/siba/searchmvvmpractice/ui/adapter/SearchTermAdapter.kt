package com.siba.searchmvvmpractice.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.siba.searchmvvmpractice.BR
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.SearchTermItemBinding
import com.siba.searchmvvmpractice.local.entity.RecentSearchTerm

class SearchTermAdapter<B : SearchTermItemBinding> :
    RecyclerView.Adapter<SearchTermAdapter<B>.SearchTermViewHolder<B>>() {
    var data = emptyList<RecentSearchTerm>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTermViewHolder<B> =
        SearchTermViewHolder<B>(
            LayoutInflater.from(parent.context).inflate(R.layout.search_term_item, parent, false)
        )

    override fun onBindViewHolder(holder: SearchTermViewHolder<B>, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    internal fun setData(recentSearchTerm: List<RecentSearchTerm>) {
        this.data = recentSearchTerm
        notifyDataSetChanged()
    }

    inner class SearchTermViewHolder<B : SearchTermItemBinding>(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding: B = DataBindingUtil.bind(itemView)!!
        fun bind(recentSearchTerm: RecentSearchTerm) {
            binding.setVariable(BR.searchTerm, recentSearchTerm)
        }
    }

}