package com.example.meds_schedule

import java.time.LocalDate

data class Event(
    val id: Long = 0,
    val category: String,
    val description: String?,
    val date: LocalDate,
)