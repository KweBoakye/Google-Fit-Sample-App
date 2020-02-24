package com.kweku.googlefitsampleapp.domain


import org.threeten.bp.LocalDateTime


data class StepCountModel(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val dataPoint: Int,
    val day: LocalDateTime)