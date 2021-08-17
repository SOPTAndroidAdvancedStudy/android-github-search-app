package com.siba.searchmvvmpractice.application

import android.app.Application

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeSingleton()
    }

    private fun initializeSingleton() {
        appExecutors = AppExecutors()
    }

    companion object {
        lateinit var appExecutors : AppExecutors
    }
}