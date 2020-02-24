package com.kweku.googlefitsampleapp.domain

interface OutputInterface {

    suspend fun sendStepCountDataSetToPresentation(stepCountDataSets: List<StepCountDataSet>?)

}