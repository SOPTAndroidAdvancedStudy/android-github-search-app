package com.siba.searchmvvmpractice.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.siba.searchmvvmpractice.ui.viewmodel.GithubViewModelFactory
import com.siba.searchmvvmpractice.ui.viewmodel.RepoViewModel
import com.siba.searchmvvmpractice.ui.viewmodel.SearchViewModel
import com.siba.searchmvvmpractice.ui.viewmodel.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Factory Class를 살펴보면 ViewModel을 Map의 형태로 담았고 Key로는 ViewModel을 Value로는 Provider로 넣어줬다.
 *
 * 그럼 이제 Dagger에서 이를 알아서 생성해줘야 하는데
 *
 * @IntoMap을 통해서는 Value로는 할 수 있다.
 *
 * 하지만 Key가 ViewModel의 class 형태이기 때문에 이를 @MapKey의 형태로 나타낼 수 있고
 *
 * 그것을 ViewModelKey라는 annotation class로 만들어서 알려줄 수 있도록 한다.
 *
 * @IntoMap : Dagger가 return Type을 Map의 value로 넣으라고 알려준다.
 * 그렇다면 Key가 필요하겠죠?
 * 이를 @ViewModelKey가 하게 된다.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel : SearchViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory : GithubViewModelFactory) : ViewModelProvider.Factory
}