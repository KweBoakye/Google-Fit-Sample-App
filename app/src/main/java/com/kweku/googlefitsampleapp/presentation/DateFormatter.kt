package com.kweku.googlefitsampleapp.presentation

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class DateFormatter: ValueFormatter() {

    override fun getBarLabel(barEntry: BarEntry?): String {
        return "${barEntry?.y} Steps"

    }

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val localDate = LocalDate.ofEpochDay(value.toLong())
        return  "${localDate.dayOfWeek} ${localDate.dayOfMonth}"

    }
}