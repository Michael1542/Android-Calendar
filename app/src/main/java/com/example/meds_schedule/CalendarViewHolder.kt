package com.example.meds_schedule

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate


class CalendarViewHolder(
    itemView: View,
    private val onItemListener: CalendarAdapter.OnItemListener,
    val days: ArrayList<LocalDate>?
) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
    public val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    public val parentView: View = itemView.findViewById(R.id.parentView)

    override fun onClick(v: View?) {
        onItemListener.onItemClick(adapterPosition, days!![adapterPosition]);
    }

}