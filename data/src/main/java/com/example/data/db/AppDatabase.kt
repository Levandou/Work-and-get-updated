package com.example.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class AppDatabase : RoomDatabase() {
    abstract fun resultNewsDao()

    companion object {
        const val DB_NAME = "app.db"

        fun getInstance(context: Context): AppDatabase = synchronized(this) { buildDatabase(context) }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            ).allowMainThreadQueries().build()
    }
}