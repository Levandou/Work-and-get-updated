package com.example.domain.mappers

import com.example.domain.News
import com.example.domain.NewsResponse

class NewsMapper : BaseMapper<NewsResponse, News> {
    override fun createFromList(list: List<NewsResponse>): List<News> {
        val newsList = mutableListOf<News>()
        list.forEach {
            newsList.add(createFromObject(it))
        }
        return newsList
    }

    override fun createFromObject(value: NewsResponse): News = News(
        id = value.id,
        title = value.title,
        text = value.text,
        urlPhoto = value.urlPhoto,
        officeId = value.office?.id,
        cityOfOffice = value.office?.city ?: 0,
        addressOfOffice = value.office?.address ?: "",
        dataOfCreation = value.dataOfCreation?:"",
        tagId = value.category?.id?:0,
        tagTitle = value.category?.tag?:""
    )
}