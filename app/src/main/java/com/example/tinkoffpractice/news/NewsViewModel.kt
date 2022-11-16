package com.example.tinkoffpractice.news

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

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    val newsPage = MutableLiveData<Flow<PagingData<News>>>()

    fun loadCategoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            newsPage.postValue(newsRepository.getPageOfNews().cachedIn(this))
        }
    }
}