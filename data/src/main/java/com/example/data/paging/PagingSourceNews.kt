package com.example.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.ApiService
import com.example.data.db.NewsDao
import com.example.domain.News

class PagingSourceNews(
    private val apiService: ApiService
) : PagingSource<Int, News>() {
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
            val listNews = apiService.getPageOfNews2(pageSize*position, pageSize)
            LoadResult.Page(
                data = listNews,
                nextKey = if (pageSize > listNews.size) null else position + 1,
                prevKey = if (position == 1) null else position - 1
            )

        } catch (e: Exception) {
            Log.d("dasdasdas", e.toString())
            LoadResult.Error(e)
        }
    }
}