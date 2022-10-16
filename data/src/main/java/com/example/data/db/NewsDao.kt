package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.News

@Dao
interface NewsDao {
   /* @Query("SELECT * FROM news")
    fun getNewsList(): List<News>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: News)
}