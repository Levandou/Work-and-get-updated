package com.example.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    //suspend fun getNews(): News

    suspend fun getListNews(): List<News>

    suspend fun getPageOfNews(): Flow<PagingData<News>>

   // fun setNewsInDb(news: News)
}