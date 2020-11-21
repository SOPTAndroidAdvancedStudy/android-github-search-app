package com.siba.searchmvvmpractice.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.siba.searchmvvmpractice.BR
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.databinding.UserItemBinding
import com.siba.searchmvvmpractice.remote.model.Users

class UserAdapter<B : UserItemBinding> : RecyclerView.Adapter<UserAdapter<B>.UserViewHolder<B>>() {
    var data = mutableListOf<Users>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder<B> =
        UserViewHolder<B>(
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        )

    override fun onBindViewHolder(holder: UserViewHolder<B>, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class UserViewHolder<B : UserItemBinding>(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding: B = DataBindingUtil.bind(itemView)!!
        fun bind(users: Users) {
            binding.setVariable(BR.users, users)
        }
    }

}