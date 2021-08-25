package com.siba.searchmvvmpractice.ui.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.siba.searchmvvmpractice.AppExecutors

/**
 * A Generic RecyclerView adapter that uses Data Binding & DiffUtil
 *
 * @param <T> Type of the items in the list
 * @param <V> The type of the ViewDataBinding
 *
 * SSong-develop
 * : AsyncDifferConfig가 뭘까?
 *
 * : recyclerview에서 Item의 position이나 혹은 데이터의 내용이 바뀐 경우 notifyDataSetChanged를 발생시킴으로써 데이터의 변경에 대처해왔다.
 *
 * : 하지만 만약 , 데이터가 100개 1000개가 된다면 어떨까? mainThread에서 작업하는데는 굉장히 많은 시간이 소요될 것이고 좋지 않을 것이다.
 *
 * : 그러기에 이를 backgroundThread로 작업환경을 변경해 처리하는데 그 방법중 하나가 AsyncDifferConfig이다.
 */
abstract class DataBoundListAdapter<T, V : ViewDataBinding>(
    appExecutors: AppExecutors,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundViewHolder<V>>(
    AsyncDifferConfig.Builder<T>(diffCallback)
        .setBackgroundThreadExecutor(appExecutors.diskIO())
        .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent)
        return DataBoundViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder.binding,getItem(position))
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding : V , item : T)
}