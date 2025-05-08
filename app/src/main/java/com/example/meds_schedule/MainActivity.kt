package com.example.meds_schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter



class MainActivity : AppCompatActivity() {
    // on below line we are creating
    // variables for text view and calendar view
    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView
    lateinit var eventsAdapter: EventsAdapter
    lateinit var recyclerView: RecyclerView
    var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    private val eventsList = mutableListOf(
        Event(title = "Meeting", description = "Discuss project", date = LocalDate.now()),
        Event(title = "Gym", description = "Leg day", date = LocalDate.now())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initializing variables of
        // list view with their ids.
        dateTV = findViewById(R.id.idTVDate)
        calendarView = findViewById(R.id.calendarView)
        eventsAdapter = EventsAdapter(emptyList())

        recyclerView = findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = eventsAdapter
        // on below line we are adding set on
        // date change listener for calendar view.

        calendarView
            .setOnDateChangeListener(
                OnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener we are getting values
                    // such as year, month and day of month
                    // on below line we are creating a variable
                    // in which we are adding all the variables in it.
                    val Date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)

                    // set this date in TextView for Display
                    dateTV.setText(Date)
                    updateEventsForDate(LocalDate.of(year, month + 1, dayOfMonth))


                })



    }

    private fun updateEventsForDate(date : LocalDate) {
        val eventsForDate = eventsList.filter { it.date == date }
        eventsAdapter.updateEvents(eventsForDate)
    }
}



//add
