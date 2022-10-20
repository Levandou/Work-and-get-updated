package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    suspend fun getNewsList(): List<News>

    @Query("DELETE FROM news")
    suspend fun removeAllFromDbNews()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: News)
}