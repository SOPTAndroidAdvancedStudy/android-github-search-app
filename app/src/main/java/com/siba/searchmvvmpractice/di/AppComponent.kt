package com.siba.searchmvvmpractice.di

import android.app.Application
import com.siba.searchmvvmpractice.application.MainApplication
import com.siba.searchmvvmpractice.ui.presentation.activity.SearchActivity
import com.siba.searchmvvmpractice.ui.presentation.fragment.FragmentComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(mainApplication: MainApplication)

    fun inject(searchActivity: SearchActivity)

    fun fragmentComponent(): FragmentComponent.Factory
}