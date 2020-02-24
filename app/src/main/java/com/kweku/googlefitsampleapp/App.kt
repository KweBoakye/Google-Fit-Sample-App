package com.kweku.googlefitsampleapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kweku.googlefitsampleapp.di.DaggerMainComponent
import com.kweku.googlefitsampleapp.di.MainComponent
import timber.log.Timber

class App: Application() {

    lateinit var mainComponent: MainComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Timber.plant(Timber.DebugTree())

        mainComponent = DaggerMainComponent.builder()
            .context(this)
            .build()
    }
}