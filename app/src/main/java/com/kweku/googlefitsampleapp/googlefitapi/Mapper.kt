package com.kweku.googlefitsampleapp.googlefitapi

import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.Field
import com.kweku.googlefitsampleapp.domain.StepCountDataSet
import com.kweku.googlefitsampleapp.domain.StepCountModel
import com.kweku.googlefitsampleapp.util.*

object Mapper {
    fun fromDataPoint(dataPoint: DataPoint): StepCountModel {
        return with(dataPoint) {
            StepCountModel(
                getStartTimeAsDateTime(),
                getEndTimeAsDateTime(),
                getValue(Field.FIELD_STEPS).asInt(),
                getDay()
            )
        }
    }


    fun toStepCountDataSet(dataSet: DataSet): StepCountDataSet {

        return StepCountDataSet(
            dataSet.dataPoints.map { dataPoint ->
                fromDataPoint(dataPoint)
            }
        )
    }
}
