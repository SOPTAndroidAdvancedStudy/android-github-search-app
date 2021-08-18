package com.siba.searchmvvmpractice.application

import android.app.Application
import com.siba.searchmvvmpractice.di.DaggerAppComponent

class MainApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        initializeComponent()
    }

    private fun initializeComponent() {
        return DaggerAppComponent.builder().application(this).build().inject(this)
    }
}