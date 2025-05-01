package com.example.meds_schedule

import java.time.LocalDate
import java.time.LocalTime


class Event(eventName: String, selectedDate: LocalDate, time: LocalTime) {

    private var name: String? = null
    private var date: LocalDate? = null
    private var time: LocalTime? = null

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getDate(): LocalDate? {
        return date
    }

    fun setDate(date: LocalDate?) {
        this.date = date
    }

    fun getTime(): LocalTime? {
        return time
    }

    fun setTime(time: LocalTime?) {
        this.time = time
    }

    companion object {
        fun eventsForDate(date: LocalDate): ArrayList<Event> {
            val events = ArrayList<Event>()

            for (event in eventsList) {
                if (event.getDate() == date) events.add(event)
            }

            return events
        }

        val eventsList: ArrayList<Event> = ArrayList()
    }
}