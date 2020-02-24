package com.kweku.googlefitsampleapp.di

import com.kweku.googlefitsampleapp.presentation.MainViewModel
import com.kweku.googlefitsampleapp.domain.OutputInterface
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {


    @Binds
    fun bindsOutputInterfaceToMainViewModel(mainViewModel: MainViewModel): OutputInterface
}