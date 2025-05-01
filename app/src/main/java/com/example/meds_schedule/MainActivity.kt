package com.example.meds_schedule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class MainActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {

    lateinit var monthYearText : TextView;
    lateinit var calendarRecyclerView : RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

    }




    private fun setMonthView() {
        monthYearText.text = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate);

        var daysInMonth : ArrayList<LocalDate?> =  CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate)
        var calendarAdapter : CalendarAdapter = CalendarAdapter(daysInMonth, this);
        var layoutManager : RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7);
        calendarRecyclerView.layoutManager = layoutManager;
        calendarRecyclerView.adapter = calendarAdapter;
    }



    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }


    public fun previousMonthAction(view : View){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public fun nextMonthAction(view : View){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    override fun onItemClick(position: Int, date : LocalDate){
        if(date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }

    }

    fun weeklyAction(view: View) {
        startActivity(Intent(this, WeekViewActivity::class.java))

    }
}