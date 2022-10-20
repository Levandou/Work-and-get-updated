package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.domain.News

@Database(entities = [News::class], exportSchema = false, version = 2)
abstract class AppDatabase : RoomDatabase() {
/*
    abstract fun resultNewsDao() :NewsDao
*/

    abstract val newsDao: NewsDao

    companion object {
        const val DB_NAME = "app.db"

        fun getInstance(context: Context): AppDatabase = synchronized(this) { buildDatabase(context) }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }
}