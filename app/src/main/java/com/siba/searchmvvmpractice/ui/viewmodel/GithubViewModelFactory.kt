package com.siba.searchmvvmpractice.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * SSong-develop
 *
 * 이거 재밌네 Map형태로 class를 가져와서 provider를 통해 viewModel을 생성해준다.
 *
 * 기존의 내코드랑 비교해보자
 *
 * 내 기존의 방법은 각 ViewModel마다 Factory class를 만들어줌으로써 이를 해결했다.
 * 물론 Repository를 Factory의 생성자에 넣어서 이를 해줘야 했기에 어쩔수 없는 방법이였다.
 * 하지만 ViewModel이 5개면 그에 맞게 리스트 혹은 다른 것을 만들어서 생성시킬 수 있지 않았을까?
 * 그럼 Factory class는 단 하나만 생성이 될 것이고 class가 확 줄것이다.
 *
 * 이 생각을 google에서는 이렇게 표현했다.
 *
 *
 * Creators는 Map Collection으로 key : ViewModel class , Value : ViewModelProvider로 구성되어 있다.
 * 이제 Factory의 create 메소드를 호출해 ViewModel을 생성할 텐데, key값인 modelClass로 ViewModel 를 찾고 있다면 , isAssingableFrom을 통해 생성한다.
 * 아니면 IllegalArgumentException을 호출해 에러를 터트린다.
 */
@Singleton
class GithubViewModelFactory @Inject constructor(
    private val creators : Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unKnown model class $modelClass")
        @Suppress("UNCHECKED_CAST")
        return creator.get() as T
    }

}