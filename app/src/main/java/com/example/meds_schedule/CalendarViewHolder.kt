package com.example.meds_schedule

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarViewHolder(itemView: View, private val onItemListener: CalendarAdapter.OnItemListener) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
    public val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    override fun onClick(v: View?) {
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text.toString());
    }

}