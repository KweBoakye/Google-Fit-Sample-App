package com.kweku.googlefitsampleapp.di

import android.content.Context

import com.kweku.googlefitsampleapp.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component


@Component(modules = [GoogleFitModule::class, ViewModelModule::class])
interface MainComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context):Builder

        fun build(): MainComponent
    }
}