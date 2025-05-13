package com.example.meds_schedule

import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val id: Int = 0,
    val category: String,
    val description: String?,
    val date: LocalDate,
)