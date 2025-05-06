package com.example.meds_schedule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meds_schedule.CalendarUtils.Companion.daysInWeekArray
import com.example.meds_schedule.CalendarUtils.Companion.monthYearFromDate
import java.time.LocalDate


class WeekViewActivity : AppCompatActivity(),CalendarAdapter.OnItemListener {

    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private lateinit var eventListView: ListView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_week_view)
        initWidgets()
        setWeekView()
    }

    private fun initWidgets() {
        calendarRecyclerView = findViewById<RecyclerView>(R.id.calendarRecyclerView)
        monthYearText = findViewById<TextView>(R.id.monthYearTV)
        eventListView = findViewById<ListView>(R.id.eventListView)
    }

    private fun setWeekView() {
        monthYearText!!.text = monthYearFromDate(CalendarUtils.selectedDate)
        val days: ArrayList<LocalDate?> = daysInWeekArray(CalendarUtils.selectedDate)

        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
        setEventAdapter()
    }


    fun previousWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1)
        setWeekView()
    }

    fun nextWeekAction(view: View?) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1)
        setWeekView()
    }

    override fun onResume() {
        super.onResume()
        setEventAdapter()
    }

    private fun setEventAdapter() {
        val dailyEvents: ArrayList<Event> = Event.eventsForDate(CalendarUtils.selectedDate)
        val eventAdapter = EventAdapter(applicationContext, 0, dailyEvents)
        eventListView.adapter = eventAdapter
    }

    fun newEventAction(view: View?) {
        startActivity(Intent(this, EventEditActivity::class.java))
    }

     override fun onItemClick(position: Int, date: LocalDate) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }
}