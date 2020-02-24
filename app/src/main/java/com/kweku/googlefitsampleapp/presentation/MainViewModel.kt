package com.kweku.googlefitsampleapp.presentation

import androidx.lifecycle.*
import com.kweku.googlefitsampleapp.domain.OutputInterface
import com.kweku.googlefitsampleapp.domain.StepCountDataSet
import dagger.Reusable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@Reusable
class MainViewModel @Inject constructor(): ViewModel(),
    OutputInterface {

    private val stepCountDatasetLiveData: MutableLiveData<List<StepCountDataSet>> = MutableLiveData()

    private suspend fun setStepCountDatasetLiveDataValue(stepCountDataSets: List<StepCountDataSet>?)
            = withContext(Dispatchers.Main){

        stepCountDatasetLiveData.value = stepCountDataSets
        Timber.i("$stepCountDataSets")
        Timber.i("${stepCountDatasetLiveData.hasActiveObservers()}")
    }

    fun observestepCountDatasetLiveData(viewLifecycleOwner: LifecycleOwner, observerAction:(List<StepCountDataSet>)-> Unit ){
        (stepCountDatasetLiveData as LiveData<List<StepCountDataSet>>).observe(viewLifecycleOwner,
            Observer<List<StepCountDataSet>>{
                stepCountDataSet -> observerAction(stepCountDataSet)
            })

    }

    override suspend fun sendStepCountDataSetToPresentation(stepCountDataSets: List<StepCountDataSet>?) {
        setStepCountDatasetLiveDataValue(stepCountDataSets)
    }
}