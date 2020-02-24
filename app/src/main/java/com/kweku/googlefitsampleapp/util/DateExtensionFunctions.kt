package com.kweku.googlefitsampleapp.util

import com.google.android.gms.fitness.data.DataPoint
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.text.DateFormat

import java.util.*
import java.util.concurrent.TimeUnit

fun DataPoint.getStartTimeString(): String = DateFormat.getTimeInstance()
    .format(this.getStartTime(TimeUnit.MILLISECONDS))

fun DataPoint.getEndTimeString(): String = DateFormat.getTimeInstance()
    .format(this.getEndTime(TimeUnit.MILLISECONDS))

fun DataPoint.getStartTimeDate(): Date = Date(this.getStartTime(TimeUnit.MILLISECONDS))

fun DataPoint.getEndTimeDate(): Date = Date(this.getEndTime(TimeUnit.MILLISECONDS))

fun DataPoint.getStartTimeAsDateTime():LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochMilli(
        this.getStartTime(TimeUnit.MILLISECONDS)),
    ZoneId.systemDefault())

fun DataPoint.getEndTimeAsDateTime():LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochMilli(
        this.getEndTime(TimeUnit.MILLISECONDS)),
    ZoneId.systemDefault())

fun DataPoint.getDay() :LocalDateTime = LocalDateTime.ofInstant(
    Instant.ofEpochMilli(
        this.getTimestamp(TimeUnit.MILLISECONDS)),
        ZoneId.systemDefault())
