package com.example.data

import com.example.domain.News
import com.example.domain.NewsResponse
import com.example.domain.Points
import retrofit2.http.*

interface ApiService {
    //@FormUrlEncoded
    /*@POST("Posts")
    suspend fun getNews(
        @Body element: News = News(99, null, "лЁВИК ДОБАВИЛ", null, null, "2022-10-15T20:30:37.726Z")
        *//*    @Field("id") params1: Int = 99,
        @Field("title") params2: String? = null,
        @Field("text") paramsasd: String? = null,
        @Field("photoBase64") params: String? = null,
        @Field("officeId") params3: Int? = null,
        @Field("dateOfCreation") params4: String = "2022-10-15T20:30:37.726Z"*//*
    ): News*/

    @GET("Posts")
    suspend fun getNewsList(): List<News>

    @GET("GetPageOfNews")
    suspend fun getPageOfNews(
        @Query("offset_id") id: Int?,
        @Query("limit ") limit: Int
    ): List<News>

    @GET("Posts/get_page_of_news_2")
    suspend fun getPageOfNews2(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<NewsResponse>

    @GET("MapPoints")
    suspend fun getPoints(): List<Points>
}