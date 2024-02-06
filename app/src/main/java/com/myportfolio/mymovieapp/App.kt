package com.myportfolio.mymovieapp

import android.app.Application
import com.myportfolio.mymovieapp.data.AppContainer

class App: Application() {
    // AppContainer instance used by the rest of classes to obtain dependencies
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}