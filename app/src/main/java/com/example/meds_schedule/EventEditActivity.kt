package com.example.meds_schedule

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalTime


class EventEditActivity : AppCompatActivity() {

    private lateinit var eventNameET: EditText;
    private lateinit var eventDateTV: TextView;
    private lateinit var eventTimeTV: TextView;

    private lateinit var time: LocalTime;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initWidgets()
        time = LocalTime.now();
        eventDateTV.text = "Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        eventTimeTV.text = "Time: " + CalendarUtils.formattedTime(time);
    }


    private fun initWidgets() {
        eventNameET = findViewById<View>(R.id.eventNameET) as EditText
        eventDateTV = findViewById<View>(R.id.eventDateTV) as TextView
        eventTimeTV = findViewById<View>(R.id.eventTimeTV) as TextView
    }


    fun saveEventAction(view: View?) {
        val eventName: String = eventNameET.getText().toString()
        val newEvent: Event = Event(eventName, CalendarUtils.selectedDate, time)
        Event.eventsList.add(newEvent)
        finish()
    }


}