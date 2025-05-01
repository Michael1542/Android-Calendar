package com.example.meds_schedule

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarAdapter(
    private final val days: ArrayList<LocalDate?>,
    private val onItemListener: OnItemListener
) :
    RecyclerView.Adapter<CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context);
        val view : View = inflater.inflate(R.layout.calendar_cell, parent, false);
        val layoutParams : ViewGroup.LayoutParams = view.layoutParams;

        if (days.size > 15){ //month
            layoutParams.height = ((parent.height * 0.166666666).toInt());
        }
        else{ //week
            layoutParams.height = parent.height;
        }

        return CalendarViewHolder(view, onItemListener, null);
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {

        var date: LocalDate? = days[position];
        if (date == null){
            holder.dayOfMonth.text = "";
        }
        else{
            holder.dayOfMonth.text = days[position]!!.dayOfMonth.toString();
            if (date == CalendarUtils.selectedDate){
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            }
        }

    }

    override fun getItemCount(): Int {
        return days.size;
    }

    interface OnItemListener {
        fun onItemClick(position: Int, date : LocalDate);
    }
}