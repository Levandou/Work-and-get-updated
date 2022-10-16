package com.example.tinkoffpractice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.News
import com.example.domain.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    val news = MutableLiveData<News>()
    val newsList = MutableLiveData<List<News>>()

    val newsPage = MutableLiveData<Flow<PagingData<News>>>()

    init {
        setValue()
        loadCategoryList()
    }

    fun loadCategoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            newsPage.postValue(newsRepository.getPageOfNews().cachedIn(this))
        }
    }

    private fun setValue() {

        viewModelScope.launch(Dispatchers.IO) {
            news.postValue(newsRepository.getNews())

            newsList.postValue(newsRepository.getListNews())
        }
    }
}