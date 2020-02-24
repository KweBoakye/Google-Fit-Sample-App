package com.kweku.googlefitsampleapp.domain


import com.kweku.googlefitsampleapp.googlefitapi.HistoryApiClient
import kotlinx.coroutines.*
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneOffset
import timber.log.Timber


import javax.inject.Inject

class HistoryInteractor @Inject constructor(private val historyApiClient: HistoryApiClient,
                                            private val outputInterface: OutputInterface
) {



    private val coroutineContext = (Dispatchers.IO + Job())
    private val historyInteractorCoroutineScope: CoroutineScope = CoroutineScope(coroutineContext)




    fun getData() = historyInteractorCoroutineScope.launch {
        Timber.i("called")

        val dateNow = LocalDate.now(ZoneOffset.UTC)

        val startDateTime = dateNow.atStartOfDay().minusDays(6)
        val endDateTime = dateNow.atTime(LocalTime.MAX)

        val startDateTimeMillis = startDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()

        val endDateTimeTimeMillis = endDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()

        val result: Deferred<List<StepCountDataSet>?> = async {
            return@async historyApiClient.readStepData(startDateTimeMillis, endDateTimeTimeMillis) }
        outputInterface.sendStepCountDataSetToPresentation(result.await())

    }







}