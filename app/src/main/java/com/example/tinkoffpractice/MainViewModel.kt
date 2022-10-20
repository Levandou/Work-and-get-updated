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
/*        newsRepository.setNewsInDb(News(
            1,
            "sda",
            "dasdasas",
            "",
            2,
            2,
            "asddas",
            "12,21,1221",
            2,
            "asddasdasdas"
        ))*/
        loadCategoryList()
    }

    fun loadCategoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            newsPage.postValue(newsRepository.getPageOfNews().cachedIn(this))
        }
    }
}