package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.ApiService
import com.example.data.db.NewsDao
import com.example.data.okHttp.NoConnectivityException
import com.example.data.okHttp.NoInternetException
import com.example.domain.News
import com.example.domain.mappers.NewsMapper

class PagingSourceNews(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val newsMapper: NewsMapper
) : PagingSource<Int, News>() {
    var isFirstOpened = true
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition)
        return if (page == null)
            null
        else
            page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val position = params.key ?: 0
        val pageSize = params.loadSize
        return try {
            var listNews: MutableList<News>
            try {
                listNews = newsMapper.createFromList(apiService.getPageOfNews2(pageSize * position, pageSize)) as MutableList<News>
                if (isFirstOpened) {
                    insertInDb(listNews)
                    isFirstOpened = false
                }
            } catch (e: Exception) {
                if (e is NoConnectivityException || e is NoInternetException)
                    listNews = newsDao.getNewsList() as MutableList<News>
                else throw e
            }
            LoadResult.Page(
                data = listNews,
                nextKey = if (pageSize > listNews.size) null else position + 1,
                prevKey = if (position == 0) null else position - 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun insertInDb(list: List<News>) {
        if (list.size >= 10) {
            newsDao.removeAllFromDbNews()
            for (i in 0..9)
                newsDao.insertNews(list[i])
        } else if (list.isNotEmpty()) {
            val listFromDb = newsDao.getNewsList() as MutableList
            val newList = mutableListOf<News>()
            newsDao.removeAllFromDbNews()

            list.forEachIndexed { index, news ->
                val a = listFromDb.firstOrNull { it.id == news.id }
                a?.let {
                    listFromDb.remove(it)
                }
                newList.add(news)
            }

            listFromDb.forEach {
                if (newList.size != 10)
                    newList.add(it)
            }
            newList.forEach {
                newsDao.insertNews(it)
            }
        }
    }
}