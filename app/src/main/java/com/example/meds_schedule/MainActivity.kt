package com.example.meds_schedule

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), CalendarAdapter.OnItemListener {

    private lateinit var monthYearText : TextView;
    private lateinit var calendarRecyclerView : RecyclerView;
    private lateinit var selectedDate : LocalDate;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        val daysInMonth : ArrayList<String> = daysInMonthArray(selectedDate);

    }

    private fun daysInMonthArray(date: LocalDate?): ArrayList<String> {
        val daysInMonthArray: ArrayList<String> = arrayListOf<String>()
        val yearMonth: YearMonth = YearMonth.from(date);

        val daysInMonth: Int = yearMonth.lengthOfMonth();

        val firstOfMonth: LocalDate = selectedDate.withDayOfMonth(1);
        val dayOfWeek: Int = firstOfMonth.dayOfWeek.value;

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add("");
            }
            else{
                daysInMonthArray.add(((i-dayOfWeek).toString()))
            }
        }
        return daysInMonthArray;
    }


    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate);

        var daysInMonth : ArrayList<String> =  daysInMonthArray(selectedDate)
        var calendarAdapter : CalendarAdapter = CalendarAdapter(daysInMonth, this);
        var layoutManager : RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7);
        calendarRecyclerView.layoutManager = layoutManager;
        calendarRecyclerView.adapter = calendarAdapter;
    }

    private fun monthYearFromDate(date : LocalDate) : String {
        val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }


    public fun previousMonthAction(view : View){
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public fun nextMonthAction(view : View){
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }




    override fun onItemClick(position: Int, dayText: String) {
        if(dayText == ("")){
            val message : String = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}