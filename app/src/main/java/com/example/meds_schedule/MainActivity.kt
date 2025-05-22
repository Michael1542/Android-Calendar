package com.example.meds_schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import android.util.Log;
import android.widget.Spinner
import java.time.LocalDateTime
import java.util.Locale
import android.widget.ArrayAdapter


const val TAG = "MainCalendarActivity"

class MainActivity : AppCompatActivity() {
    // on below line we are creating
    // variables for text view and calendar view
    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView
    lateinit var eventsAdapter: EventsAdapter
    lateinit var recyclerView: RecyclerView
    var selectedDate: LocalDate = LocalDate.now()


    private val eventsList = mutableListOf(
        Event(category = "Meditate", description = "Plank one minute", date = LocalDate.now()),
        Event(category = "Exercise", description = "Gym", date = LocalDate.now())
    )
    val driver: SqlDriver = AndroidSqliteDriver(EventDatabase.Schema, this, "event.db")
    val database = EventDatabase(driver);
    val eventQueries = database.eventDatabaseQueries

    /*val eventsListDatabase = eventQueries.selectAllEvents().executeAsList().map{
        Event(
            category = it.category,
            description = it.description,
            date = LocalDate.parse(it.date)
        )
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(TAG, "Events: $eventsList");

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val eventsListDatabase = eventQueries.selectAllEvents().executeAsList().mapNotNull {
            try {
                val parsedDate = try {
                    LocalDate.parse(it.date) // If it's ISO "yyyy-MM-dd"
                } catch (e: Exception) {
                    LocalDateTime.parse(it.date, DateTimeFormatter.ofPattern("MMM dd, yyyy, HH:mm:ss", Locale.ENGLISH))
                        .toLocalDate() // Handle "May 19, 2025, 20:00:00"
                }

                Event(
                    id = it.id,
                    category = it.category,
                    description = it.description,
                    date = parsedDate
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Log.v(TAG, "issue");
                Log.v(TAG, e.toString());
                null // Skip broken entries
            }
        }

        Log.v(TAG, "Events Original: $eventsListDatabase");

        eventsList.addAll(eventsListDatabase);

        // initializing variables of
        // list view with their ids.
        dateTV = findViewById(R.id.idTVDate)
        calendarView = findViewById(R.id.calendarView)
        eventsAdapter = EventsAdapter(emptyList()
        ) { eventToDelete: Event ->
            AlertDialog.Builder(this)
                .setTitle("Delete Event")
                .setMessage("Are you sure you want to delete '${eventToDelete.description}'?")
                .setPositiveButton("Yes") { _, _ ->
                    eventQueries.deleteEvent(eventToDelete.id)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        recyclerView = findViewById(R.id.eventsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = eventsAdapter
        // on below line we are adding set on
        // date change listener for calendar view.
        val addButton = findViewById<Button>(R.id.addEventButton)
        addButton.setOnClickListener {
            showAddEventDialog(selectedDate)  // Use current selected date
        }

        calendarView
            .setOnDateChangeListener(
                OnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener we are getting values
                    // such as year, month and day of month
                    // on below line we are creating a variable
                    // in which we are adding all the variables in it.
                    val Date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)
                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    // set this date in TextView for Display
                    dateTV.setText(Date)
                    updateEventsForDate(LocalDate.of(year, month + 1, dayOfMonth))


                })



    }

    private fun updateEventsForDate(date : LocalDate) {
        val eventsForDate = eventsList.filter { it.date == date }
        eventsAdapter.updateEvents(eventsForDate)
    }

    private fun showAddEventDialog(selectedDate: LocalDate) {
        // Inflate custom layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_event, null)

        val categorySpinner: Spinner = dialogView.findViewById(R.id.inputCategory)
        val categories = listOf("Meditate", "Exercise", "Diet", "Sleep")
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = spinnerAdapter


        //val titleInput = dialogView.findViewById<EditText>(R.id.inputCategory)
        val descInput = dialogView.findViewById<EditText>(R.id.inputDescription)

        AlertDialog.Builder(this)
            .setTitle("Add Event for $selectedDate")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val title = categorySpinner.selectedItem.toString()
                val desc = descInput.text.toString()

                if (title.isNotBlank()) {
                    val newEvent = Event(category = title, description = desc, date = selectedDate)
                    eventsList.add(newEvent)
                    eventQueries.insertEvent(
                        date = selectedDate.toString(),
                        category = title,
                        description = desc
                    )
                    updateEventsForDate(selectedDate) // Refresh list
                } else {
                    Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}



//add



