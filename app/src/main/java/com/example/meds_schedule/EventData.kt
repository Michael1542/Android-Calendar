package com.example.meds_schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "events")
data class EventData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val category : String,
    val date: LocalDate
)