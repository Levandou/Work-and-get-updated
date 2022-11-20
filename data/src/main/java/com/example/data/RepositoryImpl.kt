package com.example.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.db.NewsDao
import com.example.data.paging.PagingSourceNews
import com.example.domain.MapRepository
import com.example.domain.NewsRepository
import com.example.domain.Points

class RepositoryImpl(
    private val apiService: ApiService,
    private val pagingSource: PagingSourceNews,
    private val newsDao: NewsDao
) : NewsRepository, MapRepository {
    override suspend fun getListNews() = apiService.getNewsList()

    override fun getPageOfNews() = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 1,
            enablePlaceholders = false,
            initialLoadSize = 20,
            maxSize = 100
        ),
        pagingSourceFactory = { pagingSource }).flow

    override suspend fun getPoints() = apiService.getPoints()

    /*  override fun setNewsInDb(news: News) {
          newsDao.insertNews(news)
      }*/
}