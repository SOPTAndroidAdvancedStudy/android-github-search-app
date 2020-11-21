package com.siba.searchmvvmpractice.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.siba.searchmvvmpractice.BR
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.RepoItemBinding
import com.siba.searchmvvmpractice.remote.model.UserRepository

class RepoAdapter<B : RepoItemBinding> : RecyclerView.Adapter<RepoAdapter<B>.RepoViewHolder<B>>() {
    var data = mutableListOf<UserRepository>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder<B> =
        RepoViewHolder<B>(
            LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        )

    override fun onBindViewHolder(holder: RepoViewHolder<B>, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class RepoViewHolder<B : RepoItemBinding>(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding: B = DataBindingUtil.bind(itemView)!!
        fun bind(userRepository: UserRepository) {
            binding.setVariable(BR.userRepository, userRepository)
        }
    }

}