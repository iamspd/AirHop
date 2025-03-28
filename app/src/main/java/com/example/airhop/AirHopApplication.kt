package com.example.airhop

import android.app.Application
import com.example.airhop.data.AppContainer
import com.example.airhop.data.DefaultContainer

/**
 * [AppContainer] instance used by the rest of the classes to obtain dependencies.
 */
class AirHopApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultContainer(context = this)
    }
}