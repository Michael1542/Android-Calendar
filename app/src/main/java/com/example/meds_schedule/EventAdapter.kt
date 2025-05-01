package com.example.meds_schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class EventAdapter(
    context : Context,
    int : Int = 0,
    events : ArrayList<Event>
)  : ArrayAdapter<Event>(
    context,
    int,
    events
) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val event = getItem(position)

        if (convertView == null) convertView =
            LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)

        val eventCellTV = convertView!!.findViewById<TextView>(R.id.eventCellTV)

        val eventTitle = event!!.getName() + " " + CalendarUtils.formattedTime(
            event!!.getTime()!!
        )
        eventCellTV.text = eventTitle
        return convertView
    }

}