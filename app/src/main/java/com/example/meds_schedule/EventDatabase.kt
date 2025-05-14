package com.example.meds_schedule

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import android.content.Context

@Database(entities= [EventData::class], version = 1, exportSchema = false)
abstract class EventDatabase: RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object{
        @Volatile private var Instance: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            if (Instance == null) {
                synchronized(this){
                    Instance = Room.databaseBuilder(
                        context.applicationContext,
                        EventDatabase::class.java,
                        "medEvents.db"
                    ).build()
                }

            }
            return Instance!!
        }
    }
}