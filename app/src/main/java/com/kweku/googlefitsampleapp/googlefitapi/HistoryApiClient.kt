package com.kweku.googlefitsampleapp.googlefitapi


import android.content.Context


import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.HistoryClient

import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.DataType.AGGREGATE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.request.DataReadRequest

import com.google.android.gms.tasks.Tasks
import com.kweku.googlefitsampleapp.domain.StepCountDataSet

import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HistoryApiClient @Inject constructor(context: Context, googleSignInAccount: GoogleSignInAccount) {

    private val  historyClient: HistoryClient = Fitness.getHistoryClient(context, googleSignInAccount)

    fun readStepData(startTime: Long, endTime: Long ):List<StepCountDataSet>? {
        Timber.i("total")

        val dataSource: DataSource = DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .build()

        val dataReadResponse = Tasks.await( historyClient
            .readData(
                DataReadRequest.Builder()
                    .aggregate(dataSource, AGGREGATE_STEP_COUNT_DELTA)
                    .bucketByTime(1, TimeUnit.DAYS)
                    .setTimeRange(startTime , endTime ,TimeUnit.MILLISECONDS)

                    .build()
            ))


        return List<StepCountDataSet>(dataReadResponse.buckets.size){
            index ->

            if(dataReadResponse.buckets[index].getDataSet(AGGREGATE_STEP_COUNT_DELTA) != null){
                Mapper.toStepCountDataSet(dataReadResponse.buckets[index].getDataSet(AGGREGATE_STEP_COUNT_DELTA)!!)
            } else {
                StepCountDataSet(
                    emptyList()
                )
            }
        }
    }


}

