package com.kweku.googlefitsampleapp.googlefitapi

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.DataType.AGGREGATE_STEP_COUNT_DELTA
import javax.inject.Inject

class HistoryApiClient @Inject constructor(val context: Context, val googleSignInAccount: GoogleSignInAccount) {

    val  historyClient: HistoryClient = Fitness.getHistoryClient(context, googleSignInAccount)

    fun readDailyTotal() = historyClient.readDailyTotal(AGGREGATE_STEP_COUNT_DELTA).result
}