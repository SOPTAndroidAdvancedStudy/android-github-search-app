package com.siba.searchmvvmpractice.adapter

import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.siba.searchmvvmpractice.BR
import com.siba.searchmvvmpractice.R
import com.siba.searchmvvmpractice.data.Items
import com.siba.searchmvvmpractice.databinding.UserItemBinding

// TODO : 코드 이해 , 내 코드가 아님 아직
class UserAdapter<B : UserItemBinding> : RecyclerView.Adapter<UserAdapter<B>.UserViewHolder<B>>() {
    var data  = mutableListOf<Items>()

    // 이 어댑터는 recyclerView item에 Databinding을 적용한 형태이다
    // 이 예시에서는 user_item.xml파일에 DataBinding을 적용한 형태이기 때문에
    // 제네릭은 UserItemBinding으로 생성되어 이를 사용해 어댑터를 적용한 것이다.

    // onCreateViewHolder function
    // 반환형은 ViewHolder를 반환하는데 , 어떤형태로 가공이 된 ViewHolder인지를 알아야 한다.
    // 가공의 형태는 ViewHolder(inflate,container,attachroot)를 통해 viewHolder에 user_item이 inflate
    // 이후 가공된 viewHolder를 반환함으로써 item이 inflate하도록 한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder<B> =
            UserViewHolder<B>(LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false))

    override fun onBindViewHolder(holder: UserViewHolder<B>, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    // UserViewHolder 클래스
    // inner class로 구현하였는데 이 클래스를 좀 자세히 보자
    // 첫번째로 제네릭으로 들어가는 UserItemBinding을 통해 ViewDataBinding를 받아준다.
    // 예시 어댑터에서는 UserItemBinding에 관한 ViewDataBinding을 제네릭으로 받음으로 데이터바인딩을 사용할 수 있도록합니다.
    // - Base Class 할때 기억을 되살리시면 될거 같아요
    inner class UserViewHolder<B : UserItemBinding>(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding : B = DataBindingUtil.bind(itemView)!! // DataBindingUtil를 bind해줌으로 데이터바인딩된 xml를 연결해준다

        fun bind(item : Items){ // 이 바인드는 onBindViewHolder function에서 사용할 bind function , 위에꺼랑 다름
            binding.setVariable(BR.ItemData,item)
        }
    }
}