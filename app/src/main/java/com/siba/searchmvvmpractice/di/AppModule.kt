package com.siba.searchmvvmpractice.di

import android.app.Application
import androidx.room.Room
import com.siba.searchmvvmpractice.api.GithubService
import com.siba.searchmvvmpractice.local.dao.RepoDao
import com.siba.searchmvvmpractice.local.dao.SearchDao
import com.siba.searchmvvmpractice.local.dao.UserDao
import com.siba.searchmvvmpractice.local.database.GithubDb
import com.siba.searchmvvmpractice.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): GithubService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDb {
        return Room
            .databaseBuilder(app, GithubDb::class.java, "github-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: GithubDb): UserDao = db.userDao

    @Singleton
    @Provides
    fun provideRepoDao(db: GithubDb): RepoDao = db.repoDao

    @Singleton
    @Provides
    fun provideSearchDao(db: GithubDb): SearchDao = db.searchDao
}