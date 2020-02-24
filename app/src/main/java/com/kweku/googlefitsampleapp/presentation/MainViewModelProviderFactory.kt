package com.kweku.googlefitsampleapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kweku.googlefitsampleapp.domain.OutputInterface
import javax.inject.Inject
import javax.inject.Provider

class MainViewModelProviderFactory @Inject constructor(private val outputInterfaceProvider: Provider<OutputInterface>): ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val mainViewModel: MainViewModel

        if (modelClass == MainViewModel::class.java){

            mainViewModel = outputInterfaceProvider.get() as MainViewModel
        }
        else {
            throw RuntimeException("unsupported view model class: $modelClass ")
        }
        return mainViewModel as T
    }
}