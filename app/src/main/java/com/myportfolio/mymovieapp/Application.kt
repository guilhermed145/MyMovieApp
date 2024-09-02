package com.myportfolio.mymovieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Custom Application class integrated with Hilt for Dependency Injection.
@HiltAndroidApp
class Application: Application()